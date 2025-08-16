import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class JewelSuiteGUI extends JFrame {
    private CenterPanel centerPanel; //panel ส่วนกลาง ใช้แสดง grid
    private BottomBarPanel bottomBar;  // panel ส่วนล่าง มีปุ่ม load, calculate, ช่อง text
    private double[][] grid; // เก็บค่าตารางข้อมูล (2 มิติ)
    public JewelSuiteGUI() {
        initializeGUI();// เรียก method สร้าง GUI
    }

    private void initializeGUI() {
        setTitle("Jewel Suite - Gas Volume Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ปิดโปรแกรมเมื่อกด EXIT
        setUndecorated(true); // เอา border/title bar ออก
        setLayout(new BorderLayout(0, 0)); // ใช้ layout แบบ BorderLayout
        setSize(1200, 800); // กำหนดขนาดหน้าต่าง
        getContentPane().setBackground(Setting.SECONDARY_COLOR);  // ตั้งสีพื้นหลัง

        // สร้างและเพิ่ม Panels ย่อย
        add(new TopBarPanel(), BorderLayout.NORTH);// แถบด้านบน (top bar)
        centerPanel = new CenterPanel(); //panel ส่วนกลาง
        add(centerPanel, BorderLayout.CENTER); // ใส่ panel ตรงกลาง
        // ส่ง Listener ให้ BottomBarPanel
        ActionListener loadFileListener = new LoadFileListener();  // กดปุ่ม Load File
        ActionListener calculateListener = new CalculateListener(); // กดปุ่ม Calculate

        bottomBar = new BottomBarPanel(centerPanel, loadFileListener, calculateListener); //  เพิ่ม bottom bar พร้อมปุ่ม
        add(bottomBar, BorderLayout.SOUTH);// ใส่ panel ล่างสุด
        setLocationRelativeTo(null);   // จัดให้อยู่กลางหน้าจอ
    }

    private class LoadFileListener implements ActionListener {  // คลาสย่อย Listener โหลดไฟล์
        @Override
        public void actionPerformed(ActionEvent e) {  // เมื่อกดปุ่ม
            JFileChooser fileChooser = new JFileChooser(); // สร้างตัวเลือกไฟล์
            fileChooser.setCurrentDirectory(new File("src"));  // โฟลเดอร์เริ่มต้น
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt")); // รับแค่ filter ไฟล์ .txt

            if (fileChooser.showOpenDialog(JewelSuiteGUI.this) == JFileChooser.APPROVE_OPTION) { // ถ้าเลือกไฟล์แล้วกด OK
                File selectedFile = fileChooser.getSelectedFile(); // เอาไฟล์ที่เลือกมา
                String fileName = selectedFile.getName(); // เก็บชื่อไฟล์
                bottomBar.getFileStatusField().setText(fileName);  // แสดงชื่อไฟล์ที่เลือก
                bottomBar.getFileStatusField().setForeground(Setting.FILE_COLOR); // เปลี่ยนสีข้อความไฟล์
                bottomBar.getCalculateButton().setEnabled(true);  // เปิดปุ่มคำนวณได้

                try {
                    Scanner scan = new Scanner(selectedFile);
                    List<double[]> rowsList = new ArrayList<>();

                    while (scan.hasNextLine()) {
                        String line = scan.nextLine().trim();
                        if (!line.isEmpty()) {
                            String[] parts = line.split("\\s+");
                            double[] rowValues = new double[parts.length];
                            for (int i = 0; i < parts.length; i++) {
                                rowValues[i] = Double.parseDouble(parts[i]);
                            }
                            rowsList.add(rowValues);
                        }
                    }
                    scan.close();

                    // จำนวน row = จำนวนบรรทัด
                    int rows = rowsList.size();
                    // จำนวน col = จำนวนตัวเลขในบรรทัดแรก
                    int cols = rowsList.get(0).length;

                    grid = new double[rows][cols];
                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            grid[r][c] = rowsList.get(r)[c];
                            System.out.println("R" + r + "C" + c + " " + grid[r][c]);
                        }
                    }

                    centerPanel.updateGridWithData(grid);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(JewelSuiteGUI.this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                    bottomBar.getFileStatusField().setText("No file selected");
                    bottomBar.getFileStatusField().setForeground(Color.GRAY);
                    bottomBar.getCalculateButton().setEnabled(false);
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


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//บอก Swing ให้ใช้ ธีมของOS เครื่อง ถ้าเปิดใน Windows ก็จะหน้าตาเหมือน Windows
        } catch (Exception ignored) {}
        new JewelSuiteGUI().setVisible(true);
    }
}