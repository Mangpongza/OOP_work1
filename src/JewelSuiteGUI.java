import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JewelSuiteGUI extends JFrame {
    private CenterPanel centerPanel;
    private BottomBarPanel bottomBar;

    public JewelSuiteGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Jewel Suite - Gas Volume Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout(0, 0));
        setSize(1200, 800);
        getContentPane().setBackground(Setting.SECONDARY_COLOR);

        // สร้างและเพิ่ม Panels ย่อย
        add(new TopBarPanel(), BorderLayout.NORTH);

        centerPanel = new CenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // ส่ง Listener ให้ BottomBarPanel
        ActionListener loadFileListener = new LoadFileListener();
        ActionListener calculateListener = new CalculateListener();
        bottomBar = new BottomBarPanel(loadFileListener, calculateListener);
        add(bottomBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    // --- ส่วนของ Event Listeners ---
    private class LoadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
            if (fileChooser.showOpenDialog(JewelSuiteGUI.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                bottomBar.getFileStatusField().setText(fileName);
                bottomBar.getFileStatusField().setForeground(Setting.PRIMARY_COLOR);
                bottomBar.getCalculateButton().setEnabled(true);
                centerPanel.updateGridWithSampleData(); // อัปเดต grid
            }
        }
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double fluidContact = Double.parseDouble(bottomBar.getFluidContactField().getText());
                JOptionPane.showMessageDialog(
                        JewelSuiteGUI.this,
                        "Calculation completed!\nFluid Contact: " + fluidContact + " meters",
                        "Calculation Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
                centerPanel.updateGridWithSampleData();
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