package gwt.material.design.chat.client.local.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.chat.client.avatar.MaterialAvatar;
import gwt.material.design.chat.client.local.events.NewMessageEvent;
import gwt.material.design.chat.client.shared.MyMessage;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialLabel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
@Templated
public class MessageList extends Composite {

    @Inject
    @DataField
    MaterialCollection collection;

    @PostConstruct
    public void init() {

    }

    public void onNewMessage(@Observes NewMessageEvent event) {
        MyMessage newMessage = event.getMessage();

        MaterialCollectionItem item = new MaterialCollectionItem();
        item.setBackgroundColor("grey lighten-4");
        item.setType(CollectionType.AVATAR);
        collection.add(item);

        MaterialAvatar image = new MaterialAvatar(newMessage.getAuthor().getUniqueId());
        image.setCircle(true);
        image.setBackgroundColor("white");
        image.setShadow(1);
        image.setHeight("80");
        image.setWidth("80");
        item.add(image);

        MaterialLabel date = new MaterialLabel();
        date.setFontSize(0.8, Style.Unit.EM);
        date.setFloat(Style.Float.RIGHT);
        date.setTextColor("grey lighten-1");
        date.setText(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(newMessage.getDate()));
        item.add(date);

        MaterialLabel user = new MaterialLabel(newMessage.getAuthor().getUsername());
        item.add(user);

        MaterialLabel message = new MaterialLabel();
        message.setFontWeight(Style.FontWeight.LIGHTER);
        message.setText(newMessage.getMessage());
        item.add(message);


        int totalHeight = 0;
        for (int i = 0; i < collection.getWidgetCount(); i++) {
            Widget w = collection.getWidget(i);
            totalHeight = totalHeight + w.getOffsetHeight();
        }
        collection.getElement().setScrollTop(totalHeight);
    }


}
