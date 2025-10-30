import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new  JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);    
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Startmenu startmenu = new Startmenu();
        frame.add(startmenu.getMainPanel());
        frame.setVisible(true);

        startmenu.getMulaiButton().addActionListener(e -> {
            FlappyBird flappyBird = new FlappyBird();
            frame.remove(startmenu.getMainPanel());
            frame.add(flappyBird);
            frame.revalidate();
            frame.repaint();
            flappyBird.requestFocus();
            frame.pack();
        });
    }
}
