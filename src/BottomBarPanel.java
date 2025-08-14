import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// คลาสสำหรับสร้างและจัดการแถบด้านล่าง
class BottomBarPanel extends JPanel {
    private JTextField fluidContactField;
    private JButton loadFileButton;
    private JButton calculateButton;
    private JButton clearbutton = createButtonclearButton();
    private JTextField fileStatusField;
    private ActionListener loadFileListener;
    private ActionListener calculateListener;
    private ActionListener clearFileListener;
    public BottomBarPanel(ActionListener loadFileListener, ActionListener calculateListener, ActionListener clearFileListener) {
        super(new BorderLayout(20, 0));
        this.loadFileListener = loadFileListener;
        this.calculateListener = calculateListener;
        this.clearFileListener = clearFileListener;
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
        clearbutton = createButtonclearButton();
        panel.add(loadFileButton);
        panel.add(fileStatusField);
        panel.add(clearbutton);

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
    private JButton createButtonclearButton() {
        JButton clear = new JButton("Clear file");
        clear.setFont(Setting.SUB_FONT);
        clear.setBackground(Setting.PRIMARY_COLOR);
        clear.setForeground(Color.BLACK);
        clear.setFocusPainted(false);
        clear.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear.addActionListener(clearFileListener);
            }
        });



        return clear;
    }


    private JTextField createFluidContactField() {
        JTextField textField = new JTextField(String.valueOf(Setting.Fluid), 8);
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