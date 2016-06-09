package gwt.material.design.chat.client.local.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.addins.client.sideprofile.MaterialSideProfile;
import gwt.material.design.chat.client.avatar.MaterialAvatar;
import gwt.material.design.chat.client.local.events.SetCurrentUserEvent;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialToast;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@Templated("SideNav.html#container")
public class SideNav extends Composite {

    @Inject
    @DataField
    MaterialSideNav sideNav;

    @Inject
    MaterialSideProfile sideProfile;

    @Inject
    MaterialAvatar image;

    @Inject
    MaterialLabel name;

    @Inject
    MaterialLabel id;

    @Inject
    MaterialLink linkRoom;

    @Inject
    MaterialLink linkSettings;

    @PostConstruct
    public void init() {
        sideNav.setId("sideNav");
        sideNav.setType(SideNavType.FIXED);
        sideNav.setWidth(280);
        sideNav.reinitialize();

        sideProfile.setUrl("https://graphicflip.com/wp-content/uploads/2016/02/40-backgrounds-material.jpg");
        sideNav.add(sideProfile);
    }

    public void onSetCurrentEvent(@Observes SetCurrentUserEvent event) {
        image.setName(event.getUser().getUniqueId());
        name.setText(event.getUser().getUsername());
        id.setText(event.getUser().getUniqueId());

        image.setCircle(true);
        image.setBackgroundColor("white");
        image.setShadow(2);
        image.setWidth("80");
        image.setHeight("80");
        sideProfile.add(image);

        name.setFontSize(1.2, Style.Unit.EM);
        name.setTextColor("white");
        sideProfile.add(name);

        id.setTextColor("white");
        id.setFontSize(0.8, Style.Unit.EM);
        id.setMarginTop(24);
        id.setOpacity(0.6);
        sideProfile.add(id);

        linkRoom.setText("Room");
        linkRoom.setIconType(IconType.MESSAGE);
        linkRoom.setHref("#chat");
        sideNav.add(linkRoom);

        linkSettings.setText("Settings");
        linkSettings.setIconType(IconType.SETTINGS);
        linkSettings.setHref("#settings");
        sideNav.add(linkSettings);
    }

}

