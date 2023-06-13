package utils;

import java.awt.Color;
import java.nio.file.Paths;

/**
 * Constants
 */
public class Constants {

  public static final String MSG_DELIMITER = ",";
  public static final String ROOT_DIR = System.getProperty("user.dir")
      .replaceAll("/src", "");
  public static final String ASSETS_DIR = Paths.get(ROOT_DIR, "Assets").toString();
  public static final String DEFAULT_HOST = "localhost";
  public static final int DEFAULT_PORT = 5000;


  // GUI constants
  public static Color RED = new Color(0xFFE32A2A);
  public static Color GREEN = new Color(0x4DFF34);
  public static Color GRAY = new Color(0x5E5E5E);
  public static Color WHITE = new Color(0xFFFFFF);
}
