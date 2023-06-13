package networking.utils;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a message sent between the clients and the server
 *
 * @author sageTendo
 */
public class Message implements Serializable {

  /**
   * The type of the messages sent between the clients and the server
   */
  public enum MessageType {

    REGISTER,
    MOVE, JOIN_GAME, GAME_START, GAME_END,
    PING, PONG, ERR, GET_GAMES, CREATE_GAME
  }

  private MessageType type;
  private String from;
  private String to;
  private String content;
  @Serial
  private static final long serialVersionUID = 2357447863467474L;

  /**
   * Constructs a new message
   */
  public Message() {
  }

  /**
   * @return the type of the message to be sent
   */
  public MessageType getType() {
    return type;
  }

  /**
   * sets the type of the message to be sent
   *
   * @param type the type of the message to be sent
   * @return this message
   */
  public Message setType(MessageType type) {
    this.type = type;
    return this;
  }

  /**
   * @return the username of the user who sent the message
   */
  public String getFrom() {
    return from;
  }

  /**
   * sets the username of the user who sent the message
   *
   * @param from the username of the user who sent the message
   * @return this message
   */
  public Message setFrom(String from) {
    this.from = from;
    return this;
  }

  /**
   * @return the username of the user receiving the message
   */
  public String getTo() {
    return to;
  }

  /**
   * @param to the username of the user receiving the message
   * @return this message
   */
  public Message setTo(String to) {
    this.to = to;
    return this;
  }

  /**
   * @return the content of the message
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content the content of the message
   * @return this message
   */
  public Message setContent(String content) {
    this.content = content;
    return this;
  }
}
