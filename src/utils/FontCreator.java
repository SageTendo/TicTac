package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * FontCreator class
 *
 * @author sageTendo
 */
public class FontCreator {

  File fontFile;
  int fontType;

  /**
   * Constructor
   *
   * @param filename the filename of the font file to be used
   * @param fontType the font type {@link Font}
   */
  public FontCreator(String filename, int fontType) {
    this.fontFile = new File(filename);
    try {
      System.out.println(fontFile.getCanonicalPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.fontType = fontType;
  }

  /**
   * Creates the font to be used on the GUI
   */
  public void createFont() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
      ge.registerFont(Font.createFont(fontType, fontFile));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }
}
