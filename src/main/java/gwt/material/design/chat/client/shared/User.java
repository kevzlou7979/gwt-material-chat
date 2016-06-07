package gwt.material.design.chat.client.shared;


import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class User{

	private String username;
	private String color;
	private String uniqueId;

	public User() {
	}

	public User(String name, String color) {
		setUsername(name);
		setColor(color);
	}

	public User(String uniqueId, String color, String username) {
		this.uniqueId = uniqueId;
		this.username = username;
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
