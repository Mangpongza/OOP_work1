import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class JewelSuiteGUI extends JFrame {
    private CenterPanel centerPanel;
    private BottomBarPanel bottomBar;
    private double[][] grid;
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
        // สร้าง Listener ใหม่สำหรับปุ่ม Clear
        ActionListener clearFileListener = new ClearFileListener();
        bottomBar = new BottomBarPanel(loadFileListener, calculateListener, clearFileListener);
        add(bottomBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private class LoadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("src"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

            if (fileChooser.showOpenDialog(JewelSuiteGUI.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName();
                bottomBar.getFileStatusField().setText(fileName);
                bottomBar.getFileStatusField().setForeground(Setting.FILE_COLOR);
                bottomBar.getCalculateButton().setEnabled(true);

                try {

                    Scanner scan = new Scanner(selectedFile);
                    StringBuilder allText = new StringBuilder();
                    while (scan.hasNextLine()) {
                        allText.append(scan.nextLine()).append(" ");
                    }
                    scan.close();


                    String[] parts = allText.toString().trim().split("\\s+"); // แยกด้วยช่องว่าง
                    double[] flatData = new double[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        flatData[i] = Double.parseDouble(parts[i]);
                        System.out.println(i+" "+ flatData[i]);
                    }

                    // สร้าง grid 2 มิติ
                    int rows = 10;
                    int cols = 20;
                    grid = new double[rows][cols];

                    int index = 0;
                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            if (index < flatData.length) {
                                grid[r][c] = flatData[index++];
                            } else {
                                grid[r][c] = 0.0; // กรอก 0 ถ้าข้อมูลไม่พอ
                            }
                            System.out.println("R"+r+"C"+c+" "+ grid[r][c]);
                        }
                    }

                    centerPanel.updateGridWithData(grid);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JewelSuiteGUI.this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double fluidContact = Double.parseDouble(bottomBar.getFluidContactField().getText());
                centerPanel.setFluidContact(fluidContact);
                if (grid != null) {
                    // ถ้าค่าเปลี่ยน ให้แสดงผล
                    JOptionPane.showMessageDialog(
                            JewelSuiteGUI.this,
                            fluidContact + " Meters",
                            "Calculated!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    centerPanel.updateGridWithData(grid);

                } else {
                    JOptionPane.showMessageDialog(
                            JewelSuiteGUI.this,
                            "Please load a file first!",
                            "No Data",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
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
    private class ClearFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. ล้างข้อมูลไฟล์ที่แสดงใน BottomBar
            bottomBar.getFileStatusField().setText("No file selected");
            bottomBar.getFileStatusField().setForeground(Setting.TEXT_COLOR);

            // 2. ล้างข้อมูลใน Grid
            grid = null; // เคลียร์ข้อมูล grid ใน JewelSuiteGUI
            centerPanel.createEmptyGrid(); // เรียกเมท็อดเพื่อวาด Grid เปล่าๆ

            // 3. ปิดการใช้งานปุ่ม Calculate
            bottomBar.getCalculateButton().setEnabled(false);
        }
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//บอก Swing ให้ใช้ ธีมของOS เครื่อง ถ้าเปิดใน Windows ก็จะหน้าตาเหมือน Windows
        } catch (Exception ignored) {}
        new JewelSuiteGUI().setVisible(true);
    }
}