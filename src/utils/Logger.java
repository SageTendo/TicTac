package utils;

/**
 * Logger class to log messages
 */
public class Logger {

  /**
   * Formats a log message
   *
   * @param type The type of message to log
   * @param msg  The message to log
   * @return The formatted log
   */
  public static String formatLog(String type, String msg) {
    return String.format("[%s] => %s", type, msg);
  }

  /**
   * Logs a message to the console
   *
   * @param type The type of message to log
   * @param msg  The message to log
   */
  public static void console(String type, String msg) {
    System.out.println(formatLog(type, msg));
  }

}
