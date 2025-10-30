import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 74;
    int playerHeight = 54;
    Player player;
    // pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // game logic
    int score = 0;
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    boolean isGameOver = false;
    public FlappyBird(){
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
//        setBackground(Color.blue);

        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pipa");
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0,0, frameWidth, frameHeight, null);
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
        for(int i = 0; i< pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 40);

    }
    public void resetGame() {
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        pipes.clear();
        score = 0;
        isGameOver = false;
        gameLoop.start();
        pipesCooldown.start();
    }

    public void gameOver() {
        gameLoop.stop();
        pipesCooldown.stop();
        isGameOver = true;

        // --- Kumpulan Style ---
        Color backgroundColor = new Color(40, 40, 40); // Latar belakang abu-abu gelap
        Color textColor = Color.WHITE;
        Color restartColor = new Color(255, 100, 100); // Merah
        Color buttonDefaultBg = new Color(0, 150, 255); // Biru (sama seperti tombol mulai)
        Color buttonHoverBg = new Color(70, 180, 255); // Biru muda

        // --- Setup Dialog ---
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Game Over", false);
        dialog.setSize(300, 300); // Kita buat sedikit lebih besar
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        // dialog.setUndecorated(true); // Opsional: Hapus title bar (tapi jadi sulit ditutup)

        // --- Panel Utama (Pengganti messagePanel) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Susun ke bawah
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding (atas, kiri, bawah, kanan)

        // --- Label Game Over ---
        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(textColor);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Wajib untuk BoxLayout

        // --- Label Skor ---
        JLabel scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setForeground(textColor);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Label Restart (yang sudah Anda ubah) ---
        JLabel restartLabel = new JLabel("Press R for Restart", SwingConstants.CENTER);
        restartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        restartLabel.setForeground(restartColor);
        restartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Tombol Restart (++ dengan Style) ---
        // (Kita hapus buttonPanel dan gabung ke mainPanel)
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Poppins", Font.BOLD, 20));
        restartButton.setPreferredSize(new Dimension(180, 50));
        restartButton.setMaximumSize(new Dimension(180, 50)); // Penting untuk BoxLayout
        restartButton.setBackground(buttonDefaultBg);
        restartButton.setForeground(textColor);
        restartButton.setFocusPainted(false);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        restartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aksi tombol
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                resetGame();
            }
        });

        // Efek hover tombol
        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                restartButton.setBackground(buttonHoverBg);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                restartButton.setBackground(buttonDefaultBg);
            }
        });

        // --- Menata Komponen di Panel ---
        mainPanel.add(gameOverLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spasi
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
        mainPanel.add(restartLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25))); // Spasi
        mainPanel.add(restartButton);

        // Listener 'R' di dialog (Kode asli Anda, ini sudah bagus)
        dialog.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
                    dialog.dispose();
                    resetGame();
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        // --- Tampilkan Dialog ---
        dialog.add(mainPanel); // Tambahkan panel utama kita ke dialog
        dialog.setFocusable(true);
        dialog.setVisible(true);
        dialog.requestFocusInWindow();
    }

    public void checkCollision() {
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (playerRect.intersects(pipeRect)) {
                gameOver();
                break;
            }
        }

        if (player.getPosY() + player.getHeight() > frameHeight || player.getPosY() < 0) {
            gameOver();
        }

    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for(int i = 0; i< pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                if (pipe.getImage() == upperPipeImage) {
                    score++;
                    pipe.setPassed(true);
                }
            }

        }

    }

    public void placePipes() {
        int openingSpace = frameHeight / 4;

        int randomPosY = - (int)(Math.random() * (pipeHeight / 2));

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        upperPipe.setVelocityX(-4);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        lowerPipe.setVelocityX(-4);
        pipes.add(lowerPipe);
    }

    public void actionPerformed(ActionEvent e){
        move();
        checkCollision();
        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Hanya izinkan lompat jika game TIDAK over
            if (!isGameOver) {
                player.setVelocityY(-10);
            }
        }
        // Tambahkan logika 'R' di listener utama
        else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            // Hanya izinkan restart jika game SUDAH over
            if (isGameOver) {

                // Cek apakah ada dialog. Jika ada, tutup dulu.
                Window gameWindow = SwingUtilities.getWindowAncestor(this);
                for (Window window : gameWindow.getOwnedWindows()) {
                    if (window instanceof JDialog) {
                        window.dispose();
                    }
                }

                // Jalankan reset
                resetGame();
            }
        }
    }

    public void keyReleased(KeyEvent e){

    }
}
