package gwt.material.design.chat.client.shared;

import org.jboss.errai.common.client.api.annotations.Portable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;

@Portable
public class MyMessage {

    @Id
    @GeneratedValue
    private int id;
    @OneToMany
	private User author;
	private String message;
	private String recipient;
	private MessageType type;
	private Date date;

	public MyMessage() {}

	public MyMessage(User author, String message, String recipient) {
		this.author = author;
		this.message = message;
		this.recipient = recipient;
	}

	public MessageType getType() {
		return type;
	}

	public MyMessage setType(MessageType type) {
		this.type = type;
		return this;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
		return message;
	}

	public MyMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getRecipient() {
		return recipient;
	}

	public MyMessage setRecipient(String recipient) {
		this.recipient = recipient;
		return this;
	}

	public User getAuthor() {
		return author;
	}

	public MyMessage setAuthor(User author) {
		this.author = author;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public MyMessage setDate(Date date) {
		this.date = date;
		return this;
	}

	@Portable
	public enum MessageType {
		PRIVATE(), PUBLIC(),SYSTEM();
	}
}
