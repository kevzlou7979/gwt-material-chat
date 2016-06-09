package gwt.material.design.chat.client.local.events;

import gwt.material.design.chat.client.shared.User;

/**
 * Created by Mark Kevin on 6/8/2016.
 */
public class SetCurrentUserEvent {

    private User user;

    public SetCurrentUserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
