
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
    private JLabel fileStatusLabel;

    // สีสำหรับแสดงระดับแก๊สตามโจทย์
    private final Color NO_GAS_COLOR = Color.RED;        // ไม่มีแก๊ส
    private final Color LOW_GAS_COLOR = Color.YELLOW;     // แก๊สน้อยกว่า 50%
    private final Color HIGH_GAS_COLOR = Color.GREEN;     // แก๊สมากกว่า 50%

    public JewelSuiteGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Jewel Suite - Gas Volume Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 700);

        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        setLocationRelativeTo(null);
    }

    private void createTopPanel() {
        //
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Legend Panel
        JPanel legendPanel = new JPanel();
        legendPanel.setBackground(Color.WHITE);
        legendPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Legend",
                TitledBorder.LEFT,
                TitledBorder.TOP,

                new Font("Arial", Font.BOLD, 12)
        ));

        legendPanel.setLayout(new GridBagLayout());

        legendPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // ทุกอย่างอยู่ในคอลัมน์แรก
        gbc.anchor = GridBagConstraints.WEST; // จัดชิดซ้าย
        gbc.insets = new Insets(2, 5, 2, 5);

        gbc.weighty = 0.0; // นี่คือส่วนสำคัญ: แถวนี้จะดูดซับพื้นที่แนวตั้งพิเศษทั้งหมด
        gbc.insets = new Insets(250, 6, 20, 5); // ตัวอย่าง: ขยับลงมา 20 pixels จากขอบบนของ legendPanel

        gbc.gridy = 0; // แถวที่ 0
        legendPanel.add(createLegendItem(NO_GAS_COLOR, "No Gas (0%)"), gbc);

        // insets สำหรับ Legend Item ตัวถัดไป (อาจจะให้ระยะห่างระหว่างกันเท่าเดิม)
        gbc.insets = new Insets(2, 6, 20, 5); // ระยะห่าง 2 pixels ด้านบน/ล่าง ระหว่าง item

        gbc.gridy = 1; // แถวที่ 1
        legendPanel.add(createLegendItem(LOW_GAS_COLOR, "Low Gas (<50%)"), gbc);

        gbc.gridy = 2; // แถวที่ 2
        legendPanel.add(createLegendItem(HIGH_GAS_COLOR, "High Gas (≥50%)"), gbc);

        // Grid Panel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Survey Area Grid",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)
        ));

        gridPanel = new JPanel();
        gridPanel.setBackground(Color.WHITE);

        JLabel instructionLabel = new JLabel("Please load dept.txt file to start", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.GRAY);
        gridPanel.add(instructionLabel);

        scrollPane.setViewportView(gridPanel);

        centerPanel.add(legendPanel, BorderLayout.EAST);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setBackground(Color.WHITE);

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));

        item.add(colorBox);
        item.add(label);

        return item;
    }

    private void createBottomPanel() {
        JPanel buttomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        buttomPanel.setBackground(new Color(240, 248, 255));
        buttomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. โหลดไฟล์ dept.txt
        loadFileButton = new JButton(Setting.loadFileButton);
        loadFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadFileButton.setBackground(new Color(70, 130, 180));
        loadFileButton.setForeground(Color.WHITE);
        loadFileButton.setPreferredSize(new Dimension(130, 35));
        loadFileButton.addActionListener(new LoadFileListener());

        fileStatusLabel = new JLabel("No file selected");
        fileStatusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        fileStatusLabel.setForeground(Color.GRAY);

        // 2. ปรับเปลี่ยนความลึก Fluid Contact
        JLabel fluidLabel = new JLabel("Fluid Contact Depth:");
        fluidLabel.setFont(new Font("Arial", Font.BOLD, 12));

        fluidContactField = new JTextField("2500", 8);
        fluidContactField.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel meterLabel = new JLabel("meters");
        meterLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // 3. คำนวณปริมาตร
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(34, 139, 34));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setPreferredSize(new Dimension(100, 35));
        calculateButton.setEnabled(false);
        calculateButton.addActionListener(new CalculateListener());

        buttomPanel.add(loadFileButton);
        buttomPanel.add(fileStatusLabel);
        buttomPanel.add(Box.createHorizontalStrut(20));
        buttomPanel.add(fluidLabel);
        buttomPanel.add(fluidContactField);
        buttomPanel.add(meterLabel);
        buttomPanel.add(Box.createHorizontalStrut(20));
        buttomPanel.add(calculateButton);

        add(buttomPanel, BorderLayout.SOUTH);
    }

    private class LoadFileListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

            if (fileChooser.showOpenDialog(JewelSuiteGUI.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                fileStatusLabel.setText("File: " + fileName);
                fileStatusLabel.setForeground(new Color(34, 139, 34));
                calculateButton.setEnabled(true);

                // สร้างตารางตัวอย่าง (จำลองการโหลดข้อมูล)
                createSampleGrid();
            }
        }
    }

    private void createSampleGrid() {
        gridPanel.removeAll();

        // สร้างตาราง 10x20 ตัวอย่าง (แต่ละช่อง 150x150 เมตรตามโจทย์)
        int rows = 10;
        int cols = 20;
        gridPanel.setLayout(new GridLayout(rows, cols, 1, 1));

        for (int i = 0; i < rows * cols; i++) {
            JPanel cell = createGridCell();
            gridPanel.add(cell);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createGridCell() {
        JPanel cell = new JPanel(new BorderLayout());

        // สุ่มสีตามเงื่อนไข 3 ระดับตามโจทย์
        double random = Math.random();
        Color cellColor;
        String percentage;
        String volume;

        if (random < 0.3) {
            cellColor = NO_GAS_COLOR;
            percentage = "0%";
            volume = "0";
        } else if (random < 0.65) {
            cellColor = LOW_GAS_COLOR;
            percentage = "25%";
            volume = "562";
        } else {
            cellColor = HIGH_GAS_COLOR;
            percentage = "75%";
            volume = "1688";
        }

        cell.setBackground(cellColor);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        cell.setPreferredSize(new Dimension(50, 40));

        // แสดงปริมาณแก๊สและเปอร์เซ็นต์ตามโจทย์
        Color textColor = (cellColor == LOW_GAS_COLOR) ? Color.BLACK : Color.WHITE;

        JLabel percentLabel = new JLabel(percentage, JLabel.CENTER);
        percentLabel.setFont(new Font("Arial", Font.BOLD, 8));
        percentLabel.setForeground(textColor);

        JLabel volumeLabel = new JLabel(volume, JLabel.CENTER);
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 7));
        volumeLabel.setForeground(textColor);

        cell.add(percentLabel, BorderLayout.CENTER);
        cell.add(volumeLabel, BorderLayout.SOUTH);

        return cell;
    }

    private class CalculateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double fluidContact = Double.parseDouble(fluidContactField.getText());

                // จำลองการคำนวณใหม่
                JOptionPane.showMessageDialog(
                        JewelSuiteGUI.this,
                        "Calculation completed!\nFluid Contact: " + fluidContact + " meters",
                        "Calculation Result",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // อัพเดตตารางใหม่
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
        new JewelSuiteGUI().setVisible(true);
    }
}