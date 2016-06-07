package gwt.material.design.chat.client.local;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.ui.*;
import gwt.material.design.chat.client.avatar.MaterialAvatar;
import gwt.material.design.chat.client.shared.MyMessage;
import gwt.material.design.chat.client.shared.User;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageBus;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by Mark Kevin on 6/6/2016.
 */
@EntryPoint
@Templated
@Page(path = "chat", role = DefaultPage.class)
public class ChatClient extends Composite{

    @Inject
    @DataField
    MaterialHeader header;

    @Inject
    @DataField
    MaterialCollection collection;

    @Inject
    @DataField
    MaterialButton btnSend;

    @Inject
    @DataField
    MaterialTextBox txtMessage;

    @Inject
    private MessageBus bus;

    private Random random = new Random();

    private MyMessage.MessageType conversationType = MyMessage.MessageType.PUBLIC;
    private User currentUser;
    private String recipient = "";

    private MyMessage assembleMessage() {
        return new MyMessage().setAuthor(this.currentUser).setMessage("Test")
                .setType(conversationType).setRecipient(recipient);
    }

    @PostConstruct
    public void init() {
        setCurrentUser(new User(String.valueOf(random.nextInt()) , "primary", "anonymous"));
        buildHeader();
        buildMain();
        buildFooter();
        initSubscribers();
    }

    private void buildMain() {
        addMessage(collection);
        addMessage(collection);
        addMessage(collection);
        addMessage(collection);
        addMessage(collection);
        addMessage(collection);
    }

    private void addMessage(MaterialCollection collection) {
        MaterialCollectionItem item = new MaterialCollectionItem();
        item.setType(CollectionType.AVATAR);
        collection.add(item);

        MaterialAvatar image = new MaterialAvatar(getCurrentUser().getUniqueId());
        image.setCircle(true);
        image.setHeight("80");
        image.setWidth("80");
        item.add(image);

        MaterialLabel user = new MaterialLabel(currentUser.getUsername());
        item.add(user);

        MaterialLabel message = new MaterialLabel();
        message.setText("TETESTSETESTSETSETSETSETSE");
        item.add(message);
    }

    private void buildFooter() {
        txtMessage.setPlaceholder("Type Message...");

        btnSend.setType(ButtonType.FLOATING);
        btnSend.setIconType(IconType.SEND);
        btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                sendMessage();
            }
        });
    }


    private void buildHeader() {
        header.add(getNavBar());
        header.add(getSideNav());
    }

    private MaterialNavBar getNavBar() {
        MaterialNavBar navBar = new MaterialNavBar();
        MaterialNavBrand navBrand = new MaterialNavBrand();
        navBrand.setMarginLeft(12);
        navBrand.setText("Chat App");
        navBar.setActivates("chat-sidenav");
        navBar.add(navBrand);
        return navBar;
    }

    private MaterialSideNav getSideNav() {
        MaterialSideNav sideNav = new MaterialSideNav();
        MaterialSideProfile sideProfile = new MaterialSideProfile();
        sideProfile.setUrl("https://graphicflip.com/wp-content/uploads/2016/02/40-backgrounds-material.jpg");
        sideNav.setType(SideNavType.FIXED);
        MaterialAvatar image = new MaterialAvatar(getCurrentUser().getUniqueId());
        image.setCircle(true);
        image.setBackgroundColor("white");
        image.setShadow(2);
        image.setWidth("80");
        image.setHeight("80");
        sideProfile.add(image);

        MaterialLabel name = new MaterialLabel(currentUser.getUsername());
        name.setFontSize(1.2, Style.Unit.EM);
        name.setTextColor("white");
        sideProfile.add(name);

        MaterialLabel id = new MaterialLabel(currentUser.getUniqueId());
        id.setTextColor("white");
        id.setFontSize(0.8, Style.Unit.EM);
        id.setMarginTop(24);
        id.setOpacity(0.6);
        sideProfile.add(id);

        sideNav.add(sideProfile);
        sideNav.setWidth(280);
        sideNav.setId("chat-sidenav");
        return sideNav;
    }

    private void initSubscribers() {
        bus.subscribe("ActiveUserList", new MessageCallback() {
            public void callback(Message message) {
                List<User> users = (List<User>) message.get(List.class, "users");
                for (User u : users) {
                    MaterialToast.fireToast(u.getUsername());
                }
            }
        });

        bus.subscribe("ChatClient", new MessageCallback() {
            public void callback(Message message) {
                MyMessage newMessage = message.get(MyMessage.class, "message");
                processMessage(newMessage);
            }
        });
    }

    private void processMessage(MyMessage newMessage) {
        if (newMessage.getType().equals(MyMessage.MessageType.SYSTEM)) {

        } else if (newMessage.getType().equals(MyMessage.MessageType.PUBLIC)) {

        } else {

        }

        MaterialToast.fireToast(" " + DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " "
                + newMessage.getAuthor().getUsername() + " says : " + newMessage.getMessage());
    }

    void sendMessage() {
        MessageBuilder.createMessage().toSubject("ChatService").with("message", assembleMessage())
                .errorsHandledBy(new ErrorCallback() {
                    @Override
                    public boolean error(Object message, Throwable throwable) {
                        MaterialToast.fireToast(throwable.getMessage());
                        Window.alert("ChatService can't send message, try late");
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

    private void sendPrivateMessage(String recipient) {
        conversationType = MyMessage.MessageType.PRIVATE;
        this.recipient = recipient;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
