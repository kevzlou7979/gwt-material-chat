package gwt.material.design.chat.client.local;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.material.design.chat.client.local.widget.Header;
import gwt.material.design.chat.client.local.widget.Main;
import gwt.material.design.chat.client.local.widget.SideNav;
import gwt.material.design.chat.client.shared.User;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.Navigation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@EntryPoint
public class Client extends Composite{

    @Inject
    Navigation navigation;

    @Inject
    Header header;

    @Inject
    Main content;

    @Inject
    SideNav sideNav;

    @Inject
    User currentUser;

    @PostConstruct
    public void init() {
        content.getContainer().add(navigation.getContentPanel());
        RootPanel.get().add(header);
        RootPanel.get().add(sideNav);
        RootPanel.get().add(content);
    }
}
