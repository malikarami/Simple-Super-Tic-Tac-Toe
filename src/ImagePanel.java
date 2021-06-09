import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JLabel {

    private Image img;
    private Dimension size;
    public static final int ARTWORK_MODE = 200, ICON_MODE = 80, JSONG = 150;

    public ImagePanel(String name, Dimension size) {
        String imageName =  "src\\" + name;
        setVisible(true);
        //this.setPreferredSize(size);
        try {
            setIcon( new ImageIcon(ImageIO.read( new File(imageName) ) ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}