import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    private BufferedImage background;

    public BackgroundPanel(String imagePath) {
        try {
            background = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            System.out.println("Gagal" + e.getMessage());
        }
        setPreferredSize(new Dimension(360, 640));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
