
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JewelSuiteGUI extends JFrame {
    private JPanel gridPanel;
    private JTextField fluidContactField;
    private JButton loadFileButton;
    private JButton calculateButton;
    private JTextField fileStatusField;

    // Modern color palette
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    private final Font SUB_FONT = new Font("Segoe UI", Font.BOLD, 18);

    public JewelSuiteGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Jewel Suite - Gas Volume Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setSize(1200, 800);
        getContentPane().setBackground(Setting.SECONDARY_COLOR);

        createTopBar();
        createCenterPanel();
        createBottomBar();

        setLocationRelativeTo(null);
    }

    private void createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setBackground(Setting.PRIMARY_COLOR);
        topBar.setPreferredSize(new Dimension(getWidth(), 70));
        topBar.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        // เพิ่มไอคอนซ้ายบนสุด
        JLabel iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon("src/dig.png");
        Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(img));
        topBar.add(iconLabel, gbc);
        // ชื่อโปรแกรม
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel title = new JLabel("Jewel Suite - Gas Volume Calculator");
        title.setFont(TITLE_FONT);
        title.setForeground(Color.BLACK);
        topBar.add(title, gbc);

        add(topBar, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setBackground(Setting.SECONDARY_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Grid Panel (no JScrollPane)
        gridPanel = new JPanel();
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 3, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        gridPanel.setLayout(new GridLayout(10, 20, 2, 2));
        createEmptyGrid(); // สร้าง grid ว่างเปล่า (แต่มี cell)
        // เพิ่ม gridPanel ลงใน centerPanel ตรงๆ
        centerPanel.add(gridPanel, BorderLayout.CENTER);

        // Legend Panel
        JPanel legendPanel = new JPanel();
        legendPanel.setBackground(Setting.SECONDARY_COLOR);
        legendPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 2, true),
                "Legend",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                SUB_FONT,
                Setting.PRIMARY_COLOR
        ));
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.add(Box.createVerticalStrut(10));
        legendPanel.add(createLegendItem(Setting.NO_GAS_COLOR, "No Gas (0%)"));
        legendPanel.add(Box.createVerticalStrut(5));
        legendPanel.add(createLegendItem(Setting.LOW_GAS_COLOR, "Low Gas (<50%)"));
        legendPanel.add(Box.createVerticalStrut(5));
        legendPanel.add(createLegendItem(Setting.HIGH_GAS_COLOR, "High Gas (≥50%)"));
        legendPanel.add(Box.createVerticalGlue());
        legendPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        legendPanel.setPreferredSize(new Dimension(200, 300));

        centerPanel.add(legendPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        item.setBackground(Setting.SECONDARY_COLOR);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel colorBox = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, 24, 24, 6, 6); // สี่เหลี่ยมขอบโค้งมน
                g2.setColor(Color.DARK_GRAY);
                g2.drawRoundRect(0, 0, 24, 24, 6, 6);
            }
        };
        colorBox.setPreferredSize(new Dimension(26, 26));
        JLabel label = new JLabel(text);
        label.setFont(MAIN_FONT);
        item.add(colorBox);
        item.add(label);
        return item;
    }

    private void createBottomBar() {
        JPanel bottomBar = new JPanel(new BorderLayout(20, 0));
        bottomBar.setBackground(Setting.SECONDARY_COLOR);
        bottomBar.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));

        // Left: Load file + filename
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);
        loadFileButton = new JButton("Open File");
        loadFileButton.setFont(SUB_FONT);
        loadFileButton.setBackground(Setting.PRIMARY_COLOR);
        loadFileButton.setForeground(Color.BLACK);
        loadFileButton.setFocusPainted(false);
        loadFileButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        loadFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loadFileButton.addActionListener(new LoadFileListener());
        leftPanel.add(loadFileButton);

        fileStatusField = new JTextField("No file selected", 15);
        fileStatusField.setFont(MAIN_FONT);
        fileStatusField.setForeground(Color.GRAY);
        fileStatusField.setEditable(false);
        fileStatusField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        leftPanel.add(fileStatusField);

        // Right: Fluid contact + calculate
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);
        JLabel fluidLabel = new JLabel("Fluid Contact Depth:");
        fluidLabel.setFont(SUB_FONT);
        rightPanel.add(fluidLabel);
        fluidContactField = new JTextField("2500", 8);
        fluidContactField.setFont(MAIN_FONT);
        fluidContactField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        rightPanel.add(fluidContactField);
        JLabel meterLabel = new JLabel("meters");
        meterLabel.setFont(MAIN_FONT);
        rightPanel.add(meterLabel);
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(SUB_FONT);
        calculateButton.setBackground(Setting.ACCENT_COLOR);
        calculateButton.setForeground(Color.BLACK);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.ACCENT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculateButton.setEnabled(false);
        calculateButton.addActionListener(new CalculateListener());
        rightPanel.add(calculateButton);

        bottomBar.add(leftPanel, BorderLayout.WEST);
        bottomBar.add(rightPanel, BorderLayout.EAST);
        add(bottomBar, BorderLayout.SOUTH);
    }

    // เพิ่มเมธอดสร้าง grid ว่าง
    private void createEmptyGrid() {
        gridPanel.removeAll();
        int rows = 10;
        int cols = 20;
        for (int i = 0; i < rows * cols; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE);
            cell.setBorder(BorderFactory.createLineBorder(new Color(230,230,230), 1));
            // ไม่ต้อง setPreferredSize เพื่อให้ cell responsive
            gridPanel.add(cell);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void createSampleGrid() {
        gridPanel.removeAll();
        int rows = 10;
        int cols = 20;
        gridPanel.setLayout(new GridLayout(rows, cols, 2, 2));
        for (int i = 0; i < rows * cols; i++) {
            JPanel cell = createGridCell();
            // ไม่ต้อง setPreferredSize เพื่อให้ cell responsive
            gridPanel.add(cell);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createGridCell() {
        JPanel cell = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        double random = Math.random();
        Color cellColor;
        String percentage;
        String volume;
        if (random < 0.3) {
            cellColor = Setting.NO_GAS_COLOR;
            percentage = "0%";
            volume = "0";
        } else if (random < 0.65) {
            cellColor = Setting.LOW_GAS_COLOR;
            percentage = "25%";
            volume = "562";
        } else {
            cellColor = Setting.HIGH_GAS_COLOR;
            percentage = "75%";
            volume = "1688";
        }
        cell.setBackground(cellColor);
        cell.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        cell.setPreferredSize(new Dimension(50, 40));
        Color textColor = (cellColor == Setting.LOW_GAS_COLOR) ? Color.BLACK : Color.WHITE;
        JLabel percentLabel = new JLabel(percentage, JLabel.CENTER);
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        percentLabel.setForeground(textColor);
        JLabel volumeLabel = new JLabel(volume, JLabel.CENTER);
        volumeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        volumeLabel.setForeground(textColor);
        cell.add(percentLabel, BorderLayout.CENTER);
        cell.add(volumeLabel, BorderLayout.SOUTH);
        return cell;
    }

    private class LoadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
            if (fileChooser.showOpenDialog(JewelSuiteGUI.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                fileStatusField.setText(fileName);
                fileStatusField.setForeground(Setting.PRIMARY_COLOR);
                calculateButton.setEnabled(true);
                createSampleGrid(); // สร้าง grid หลังโหลดไฟล์
            }
        }
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double fluidContact = Double.parseDouble(fluidContactField.getText());
                JOptionPane.showMessageDialog(
                        JewelSuiteGUI.this,
                        "Calculation completed!\nFluid Contact: " + fluidContact + " meters",
                        "Calculation Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
                createSampleGrid();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        JewelSuiteGUI.this,
                        "Please enter a valid number for Fluid Contact depth!",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new JewelSuiteGUI().setVisible(true);
    }
}