package networking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Abstract thread class of a client thread
 *
 * @author sageTendo
 */
public abstract class AbstractThread extends Thread {

  private String username;

  private InetAddress ip;
  private final Socket clientSocket;
  private OutputStream outputStream;
  private InputStream inputStream;
  private ObjectOutputStream objectOutputStream;
  private ObjectInputStream objectInputStream;
  private boolean connected = false;

  /**
   * Constructor to connect to a client
   *
   * @param clientSocket the socket to the client
   * @throws IOException I/O related errors
   */
  public AbstractThread(Socket clientSocket) throws IOException {
    this.clientSocket = clientSocket;
    createIOStreams();
  }

  /**
   * Constructor to connect to a server
   *
   * @param host the host to connect to
   * @param port the port to connect to
   * @throws IOException I/O related errors
   */
  public AbstractThread(String host, int port) throws IOException {
    this.clientSocket = new Socket(host, port);
    this.ip = clientSocket.getLocalAddress();
    createIOStreams();
  }

  /**
   * Creates IO streams for reading and writing messages from/to the server
   *
   * @throws IOException I/O related errors
   */
  private void createIOStreams() throws IOException {
    inputStream = clientSocket.getInputStream();
    outputStream = clientSocket.getOutputStream();

    objectOutputStream = new ObjectOutputStream(outputStream);
    objectInputStream = new ObjectInputStream(inputStream);
  }

  /**
   * Gets the message sent from/to the server
   *
   * @return the message sent from/to the server
   * @throws IOException            I/O related errors
   * @throws ClassNotFoundException class not found
   */
  public Message getMessage() throws IOException, ClassNotFoundException {
    return (Message) objectInputStream.readObject();
  }

  /**
   * Sends a message to the server/client
   *
   * @param message the message to send
   * @throws IOException I/O related errors
   */
  public void sendMessage(Message message) throws IOException {
    objectOutputStream.writeObject(message);
    objectOutputStream.flush();
  }

  /**
   * Checks if the client is connected
   *
   * @return true if the client is connected false otherwise
   */
  public boolean isConnected() {
    return connected;
  }

  /**
   * Sets the connected state
   *
   * @param connected the new connected state
   */
  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  /**
   * @return the username of the client
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the new username of the client
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the ip of the client connected
   */
  public InetAddress getIp() {
    return ip;
  }

  /**
   * @param ip the ip of the client
   */
  public void setIp(InetAddress ip) {
    this.ip = ip;
  }

  /**
   * @return the socket of the client
   */
  public Socket getClientSocket() {
    return clientSocket;
  }

  /**
   * Handle messages from the server
   */
  @Override
  public abstract void run();

  /**
   * Disconnects the client
   *
   * @throws IOException I/O related errors
   */
  public void disconnect() throws IOException {
    connected = false;
    outputStream.close();
    inputStream.close();
    clientSocket.close();
  }
}
