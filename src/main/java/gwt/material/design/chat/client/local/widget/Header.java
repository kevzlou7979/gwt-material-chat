package gwt.material.design.chat.client.local.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.chat.client.local.events.LoadEvent;
import gwt.material.design.chat.client.local.events.UserJoinEvent;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.NavBarType;
import gwt.material.design.client.ui.*;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@Templated("Header.html#header")
public class Header extends Composite {

    @Inject
    @DataField
    MaterialNavBar navBar;

    @Inject
    MaterialNavBrand navBrand;

    @Inject
    MaterialNavSection navSection;

    @Inject
    MaterialLink iconUsers;

    @Inject
    MaterialBadge badgeUsers;

    @Inject
    MaterialLink iconRightSideNav;

    @PostConstruct
    public void init() {
        navBar.setActivates("sideNav");
        navBar.setType(NavBarType.FIXED);
        navBrand.setText("Chat App");
        navBar.add(navBrand);
        navSection.setFloat(Style.Float.RIGHT);
        navSection.setHideOn(HideOn.NONE);
        iconUsers.setIconType(IconType.ACCOUNT_CIRCLE);
        badgeUsers.setText("0");
        badgeUsers.setBackgroundColor("pink");
        badgeUsers.setRight(16);
        badgeUsers.setCircle(true);
        iconUsers.add(badgeUsers);

        navSection.add(iconUsers);
        navBar.add(navSection);
    }

    public void storeListChanged(@Observes UserJoinEvent event) {
        badgeUsers.setText(event.getUsers().size() + "");
    }

    public void onAppLoad(@Observes LoadEvent event) {
        if(event.isLoad()) {
            navBar.showProgress(event.getType());
        }else {
            navBar.hideProgress();
        }
    }

    public MaterialBadge getBadgeUsers() {
        return badgeUsers;
    }
}
