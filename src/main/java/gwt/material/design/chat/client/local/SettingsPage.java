package gwt.material.design.chat.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.chat.client.avatar.MaterialAvatar;
import gwt.material.design.chat.client.local.events.LoadEvent;
import gwt.material.design.chat.client.local.events.SetCurrentUserEvent;
import gwt.material.design.chat.client.shared.User;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import org.jboss.errai.bus.client.ErraiBus;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageBus;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@Templated
@Page(path = "settings")
@ApplicationScoped
public class SettingsPage extends Composite{

    @Inject
    @DataField
    MaterialTextBox userName;

    @Inject
    @DataField
    MaterialButton btnUpdate;

    @Inject
    @DataField
    MaterialAvatar avatar;

    @Inject
    Event<SetCurrentUserEvent> setCurrentUserEventEvent;

    @Inject
    Event<LoadEvent> loadEvent;

    private MessageBus bus = ErraiBus.get();

    @Inject
    TransitionTo<ChatPage> chatPageTransitionTo;

    @PageShowing
    public void onShowing() {
        loadEvent.fire(new LoadEvent(true, ProgressType.INDETERMINATE));
    }

    @PageShown
    public void onShown() {
        loadEvent.fire(new LoadEvent(false));
    }

    @PostConstruct
    public void init() {
        avatar.setName("e2b1c5b279417c51e167c2dbf2b0d644");
        userName.setPlaceholder("User Name");
        btnUpdate.setText("Update");
        btnUpdate.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                MessageBuilder.createMessage().toSubject("ChangeUsernameSubject").signalling()
                        .with("username", userName.getText()).with("color", "primary")
                        .errorsHandledBy(new ErrorCallback<Object>() {
                            @Override
                            public boolean error(Object message, Throwable throwable) {
                                MaterialToast.fireToast(throwable.getMessage());
                                return true;
                            }
                        }).repliesTo(new MessageCallback() {
                    @Override
                    public void callback(Message message) {
                        String answer = message.get(String.class, "message");
                        Boolean result = message.get(Boolean.class, "result");
                        if (result) {
                            setCurrentUserEventEvent.fire(new SetCurrentUserEvent(new User(userName.getText(), "", userName.getText())));
                            chatPageTransitionTo.go();
                        }else {
                            MaterialToast.fireToast(answer);
                        }

                    }
                }).sendNowWith(bus);
            }
        });

        userName.addKeyUpHandler(keyUpEvent -> {
            avatar.setName(userName.getText());
            avatar.initialize();
        });
    }


    public void update(@Observes SetCurrentUserEvent event) {
        avatar.setName(event.getUser().getUniqueId());
        userName.setText(event.getUser().getUsername());
    }

}
