package resources;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontCreator {
    File fontFile;
    int fontType;
    public FontCreator(String filename, int fontType) {
        this.fontFile = new File(filename);
        try {
            System.out.println(fontFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fontType = fontType;
    }

    public void createFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(fontType, fontFile));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
