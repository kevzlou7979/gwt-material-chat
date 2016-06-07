package gwt.material.design.chat.client.avatar;

import com.google.gwt.dom.client.Document;
import gwt.material.design.client.base.MaterialWidget;

/**
 * Created by Mark Kevin on 6/6/2016.
 */
public class MaterialAvatar extends MaterialWidget {

    private String name;

    public MaterialAvatar() {
        super(Document.get().createCanvasElement());
    }

    public MaterialAvatar(String name) {
        this();
        setName(name);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        getElement().setAttribute("data-jdenticon-hash", generateHashCode(getName()));
        update();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setWidth(String width) {
        getElement().setAttribute("width", width);
    }

    @Override
    public void setHeight(String height) {
        getElement().setAttribute("height", height);
    }

    private native String generateHashCode(String value) /*-{
        return $wnd.md5(value);
    }-*/;

    private native void update() /*-{
        $wnd.jdenticon();
    }-*/;
}
