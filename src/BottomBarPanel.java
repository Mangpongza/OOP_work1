import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// คลาสสำหรับสร้างและจัดการแถบด้านล่าง
class BottomBarPanel extends JPanel {
    private JTextField fluidContactField;
    private JButton loadFileButton;
    private JButton calculateButton;
    private JTextField fileStatusField;
    private ActionListener loadFileListener;
    private ActionListener calculateListener;

    public BottomBarPanel(ActionListener loadFileListener, ActionListener calculateListener) {
        super(new BorderLayout(20, 0));
        this.loadFileListener = loadFileListener;
        this.calculateListener = calculateListener;
        setBackground(Setting.SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));

        JPanel leftPanel = createLeftBottomPanel();
        JPanel rightPanel = createRightBottomPanel();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public JTextField getFluidContactField() {
        return fluidContactField;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

    public JTextField getFileStatusField() {
        return fileStatusField;
    }

    private JPanel createLeftBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false);

        loadFileButton = createLoadFileButton();
        fileStatusField = createFileStatusField();

        panel.add(loadFileButton);
        panel.add(fileStatusField);

        return panel;
    }

    private JPanel createRightBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);

        JLabel fluidLabel = new JLabel("Fluid Contact Depth:");
        fluidLabel.setFont(Setting.SUB_FONT);

        fluidContactField = createFluidContactField();

        JLabel meterLabel = new JLabel("meters");
        meterLabel.setFont(Setting.MAIN_FONT);

        calculateButton = createCalculateButton();

        panel.add(fluidLabel);
        panel.add(fluidContactField);
        panel.add(meterLabel);
        panel.add(calculateButton);

        return panel;
    }

    private JButton createLoadFileButton() {
        JButton button = new JButton("Open File");
        button.setFont(Setting.SUB_FONT);
        button.setBackground(Setting.PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(loadFileListener);
        return button;
    }

    private JTextField createFileStatusField() {
        JTextField textField = new JTextField("No file selected", 15);
        textField.setFont(Setting.MAIN_FONT);
        textField.setForeground(Color.GRAY);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return textField;
    }

    private JTextField createFluidContactField() {
        JTextField textField = new JTextField("2500", 8);
        textField.setFont(Setting.MAIN_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return textField;
    }

    private JButton createCalculateButton() {
        JButton button = new JButton("Calculate");
        button.setFont(Setting.SUB_FONT);
        button.setBackground(Setting.ACCENT_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.ACCENT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setEnabled(false);
        button.addActionListener(calculateListener);
        return button;
    }
}