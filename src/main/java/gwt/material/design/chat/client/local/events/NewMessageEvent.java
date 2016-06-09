package gwt.material.design.chat.client.local.events;

import gwt.material.design.chat.client.shared.MyMessage;

public class NewMessageEvent {
    private final MyMessage message;

    public NewMessageEvent(MyMessage message) {
        this.message = message;
    }

    public MyMessage getMessage() {
        return message;
    }
}