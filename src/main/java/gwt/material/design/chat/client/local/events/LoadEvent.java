package gwt.material.design.chat.client.local.events;

import gwt.material.design.client.constants.ProgressType;

/**
 * Created by Mark Kevin on 6/9/2016.
 */
public class LoadEvent {

    private ProgressType type;

    private boolean load;

    public LoadEvent(boolean load, ProgressType type) {
        this.load = load;
        this.type = type;
    }

    public LoadEvent(boolean load) {
        this.load = load;
    }

    public boolean isLoad() {
        return load;
    }

    public ProgressType getType() {
        return type;
    }
}
