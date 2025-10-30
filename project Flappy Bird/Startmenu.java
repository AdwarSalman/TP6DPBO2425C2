import javax.swing.*;
import java.awt.*;

public class Startmenu {
    private BackgroundPanel mainPanel;
    private JButton mulaiButton;
    private JButton closeButton;

    public Startmenu() {
        mainPanel = new BackgroundPanel("src/assets/background.png");
        mainPanel.setLayout(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // --- STYLING BARU UNTUK TOMBOL MULAI ---

        // Tentukan warna
        Color mulaiDefaultBg = new Color(0, 150, 255); // Biru
        Color mulaiHoverBg = new Color(70, 180, 255); // Biru muda
        Color defaultFg = Color.WHITE;

        mulaiButton = new JButton("Mulai Game");
        mulaiButton.setFont(new Font("Poppins", Font.BOLD, 22));
        mulaiButton.setPreferredSize(new Dimension(220, 60)); // Sedikit lebih lebar
        mulaiButton.setMaximumSize(new Dimension(220, 60)); // Untuk BoxPanel

        // Ini bagian penting untuk styling
        mulaiButton.setBackground(mulaiDefaultBg);
        mulaiButton.setForeground(defaultFg);
        mulaiButton.setFocusPainted(false);
        mulaiButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        mulaiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mulaiButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mouse listener untuk efek hover
        mulaiButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mulaiButton.setBackground(mulaiHoverBg); // Ganti warna latar saat hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                mulaiButton.setBackground(mulaiDefaultBg); // Kembali ke warna asal
            }
        });


        // --- STYLING BARU UNTUK TOMBOL TUTUP ---

        // Tentukan warna
        Color closeDefaultBg = new Color(220, 50, 50); // Merah
        Color closeHoverBg = new Color(255, 80, 80); // Merah muda

        closeButton = new JButton("Tutup Program");
        closeButton.setFont(new Font("Poppins", Font.BOLD, 22));
        closeButton.setPreferredSize(new Dimension(220, 60));
        closeButton.setMaximumSize(new Dimension(220, 60));

        // Terapkan styling
        closeButton.setBackground(closeDefaultBg);
        closeButton.setForeground(defaultFg);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aksi untuk menutup
        closeButton.addActionListener(e -> System.exit(0));

        // Mouse listener untuk efek hover
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(closeHoverBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(closeDefaultBg);
            }
        });

        // --- Menambahkan tombol ke panel ---
        buttonPanel.add(mulaiButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getMulaiButton() {
        return mulaiButton;
    }
}