package gwt.material.design.chat.client.local;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import gwt.material.design.chat.client.local.events.NewMessageEvent;
import gwt.material.design.chat.client.local.events.SetCurrentUserEvent;
import gwt.material.design.chat.client.local.events.UserJoinEvent;
import gwt.material.design.chat.client.local.widget.MessageList;
import gwt.material.design.chat.client.shared.MyMessage;
import gwt.material.design.chat.client.shared.User;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialNoResult;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageBus;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@Templated
@Page( path = "chat" , role = DefaultPage.class)
@ApplicationScoped
public class ChatPage {

    @Inject
    @DataField
    MaterialButton btnSend;

    @Inject
    @DataField
    MaterialTextBox txtMessage;

    @Inject
    private MessageBus bus;

    @Inject
    @DataField
    MessageList messageList;

    @Inject
    @DataField
    MaterialNoResult noResult;

    @Inject
    private Event<SetCurrentUserEvent > setCurrentUserEventEvent;

    @Inject
    private Event<UserJoinEvent> userJoinEvent;

    @Inject
    private Event<NewMessageEvent> newMessageEvent;

    private Random random = new Random();

    private MyMessage.MessageType conversationType = MyMessage.MessageType.PUBLIC;
    private User currentUser;
    private String recipient = "";

    @PostConstruct
    public void init() {
        initUI();
        initSubscribers();
    }

    private void initUI() {
        txtMessage.setPlaceholder("Type Message...");
        txtMessage.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                if(keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendMessage();
                }
            }
        });

        btnSend.setType(ButtonType.FLOATING);
        btnSend.setIconType(IconType.SEND);
        btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                sendMessage();
            }
        });

        messageList.setVisible(false);
        noResult.setTextColor("grey");
        noResult.setIconType(IconType.MESSAGE);
        noResult.setTitle("Start chatting now");
        noResult.setDescription("A gwt-material chat system built with Errai");

    }

    private void initSubscribers() {
        String uid = String.valueOf(Math.abs(random.nextInt()));
        setCurrentUser(new User(uid , "primary", "anonymous"));

        setCurrentUserEventEvent.fire(new SetCurrentUserEvent(getCurrentUser()));

        bus.subscribe("ActiveUserList", new MessageCallback() {
            public void callback(Message message) {
                List<User> users = (List<User>) message.get(List.class, "users");
                MaterialToast.fireToast(users.size() + " from Chat Page");
                userJoinEvent.fire(new UserJoinEvent(users));
            }
        });

        bus.subscribe("ChatPage", new MessageCallback() {
            public void callback(Message message) {
                MyMessage newMessage = message.get(MyMessage.class, "message");
                newMessageEvent.fire(new NewMessageEvent(newMessage));
                MaterialToast.fireToast(" " + DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " "
                        + newMessage.getAuthor().getUsername() + " id " + newMessage.getAuthor().getUniqueId() + " says : " + newMessage.getMessage());
            }
        });
    }

    private MyMessage assembleMessage() {
        return new MyMessage().setAuthor(getCurrentUser()).setMessage(txtMessage.getText())
                .setType(conversationType).setRecipient(recipient).setDate(new Date());
    }

    private void sendMessage() {
        MessageBuilder.createMessage().toSubject("ChatService").with("message", assembleMessage())
                .errorsHandledBy(new ErrorCallback() {
                    @Override
                    public boolean error(Object message, Throwable throwable) {
                        MaterialToast.fireToast(throwable.getMessage());
                        return false;
                    }
                }).repliesTo(new MessageCallback() {
            public void callback(Message message) {
                Boolean result = message.get(Boolean.class, "result");
                if (result) {
                    restoreOnCancelOrDelivery();
                }
            }
        }).sendNowWith(bus);
    }

    private void restoreOnCancelOrDelivery() {
        if (conversationType.equals(MyMessage.MessageType.PRIVATE)) {
            conversationType = MyMessage.MessageType.PUBLIC;
            recipient = "";
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void onNewMessageEvent(@Observes NewMessageEvent event) {
        txtMessage.setText("");
        if(!messageList.isVisible()){
            noResult.setVisible(false);
            messageList.setVisible(true);
        }
    }

    public void onSetCurrentUserEvent(@Observes SetCurrentUserEvent event) {
        setCurrentUser(event.getUser());
    }

}
