package gwt.material.design.chat.client.local.events;

import gwt.material.design.chat.client.shared.User;

import java.util.List;

public class UserJoinEvent {
    private final List<User> users;

    public UserJoinEvent(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}