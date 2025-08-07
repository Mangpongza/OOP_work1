import javax.swing.*;
import java.awt.*;

// คลาสสำหรับสร้างและจัดการแถบด้านบน
class TopBarPanel extends JPanel {

    public TopBarPanel() {
        setBackground(Setting.PRIMARY_COLOR);
        setPreferredSize(new Dimension(0, 70));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 20);

        // Icon
        JLabel iconLabel = createIconLabel("src/dig.png", 48);
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 220, 0, 0);
        add(iconLabel, gbc);

        // Title
        JLabel title = createTitleLabel("Jewel Suite - Gas Volume Calculator");
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(title, gbc);

        // Spacer
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        add(Box.createHorizontalGlue(), gbc);

        // Exit Button
        JButton exitButton = createExitButton();
        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(exitButton, gbc);
    }

    private JLabel createIconLabel(String imagePath, int size) {
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        return label;
    }

    private JLabel createTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.setFont(Setting.TITLE_FONT);
        title.setForeground(Color.BLACK);
        return title;
    }

    private JButton createExitButton() {
        JButton button = new JButton("EXIT");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(214, 27, 27));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.addActionListener(e -> System.exit(0));
        return button;
    }
}