import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// คลาสสำหรับสร้างและจัดการแถบด้านล่างของ GUI
class BottomBarPanel extends JPanel {

    private CenterPanel centerPanel; // อ้างอิง CenterPanel เพื่อเรียกใช้งาน grid และ legend
    private JTextField fluidContactField; // ช่องกรอก Fluid Contact Depth
    private JButton loadFileButton;       // ปุ่มเลือกไฟล์
    private JButton calculateButton;      // ปุ่มคำนวณ
    private JButton clearbutton = createButtonclearButton(); // ปุ่มล้างไฟล์และ reset
    private JTextField fileStatusField;   // แสดงสถานะไฟล์ที่เลือก
    private ActionListener loadFileListener;   // listener สำหรับปุ่มโหลดไฟล์
    private ActionListener calculateListener;  // listener สำหรับปุ่มคำนวณ

    // constructor รับ centerPanel และ listener สำหรับปุ่มต่าง ๆ
    public BottomBarPanel(CenterPanel centerPanel, ActionListener loadFileListener, ActionListener calculateListener) {
        super(new BorderLayout(20, 0));
        this.centerPanel = centerPanel;
        this.loadFileListener = loadFileListener;
        this.calculateListener = calculateListener;
        setBackground(Setting.SECONDARY_COLOR); // สีพื้นหลัง
        setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30)); // margin รอบ panel

        JPanel leftPanel = createLeftBottomPanel();  // สร้าง panel ซ้าย
        JPanel rightPanel = createRightBottomPanel(); // สร้าง panel ขวา

        add(leftPanel, BorderLayout.WEST); // วางซ้าย
        add(rightPanel, BorderLayout.EAST); // วางขวา
    }

    // getter สำหรับเข้าถึง field และปุ่มจาก class อื่น
    public JTextField getFluidContactField() { return fluidContactField; }
    public JButton getCalculateButton() { return calculateButton; }
    public JTextField getFileStatusField() { return fileStatusField; }

    // สร้าง panel ด้านซ้ายของ BottomBar
    private JPanel createLeftBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false); // ให้พื้นโปร่ง

        loadFileButton = createLoadFileButton(); // ปุ่มเปิดไฟล์
        fileStatusField = createFileStatusField(); // แสดงสถานะไฟล์
        clearbutton = createButtonclearButton(); // ปุ่มล้างไฟล์และ reset

        panel.add(loadFileButton);
        panel.add(fileStatusField);
        panel.add(clearbutton);

        return panel;
    }

    // สร้าง panel ด้านขวาของ BottomBar
    private JPanel createRightBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);

        JLabel fluidLabel = new JLabel("Fluid Contact Depth:"); // label ข้อความ
        fluidLabel.setFont(Setting.SUB_FONT);

        fluidContactField = createFluidContactField(); // ช่องกรอกค่า fluid

        JLabel meterLabel = new JLabel("meters"); // label หน่วย
        meterLabel.setFont(Setting.MAIN_FONT);

        calculateButton = createCalculateButton(); // ปุ่มคำนวณ

        panel.add(fluidLabel);
        panel.add(fluidContactField);
        panel.add(meterLabel);
        panel.add(calculateButton);

        return panel;
    }

    // สร้างปุ่มเปิดไฟล์
    private JButton createLoadFileButton() {
        JButton button = new JButton("Open File");
        button.setFont(Setting.SUB_FONT);
        button.setBackground(Setting.PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // เปลี่ยน cursor เป็นมือ
        button.addActionListener(loadFileListener); // เพิ่ม listener
        return button;
    }

    // สร้าง text field แสดงสถานะไฟล์
    private JTextField createFileStatusField() {
        JTextField textField = new JTextField("No file selected", 15);
        textField.setFont(Setting.MAIN_FONT);
        textField.setForeground(Color.GRAY);
        textField.setEditable(false); // ไม่ให้แก้ไขเอง
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return textField;
    }

    // สร้างปุ่มล้างไฟล์และ reset grid + legend
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
                fileStatusField.setText("No file selected"); // ล้างชื่อไฟล์
                fileStatusField.setForeground(Color.GRAY);
                getCalculateButton().setEnabled(false);   // ปิดปุ่มคำนวณ
                centerPanel.createEmptyGrid(); // ล้าง grid
                centerPanel.clearLegend();     // ล้าง legend
            }
        });

        return clear;
    }

    // สร้าง text field สำหรับ Fluid Contact Depth
    private JTextField createFluidContactField() {
        JTextField textField = new JTextField(String.valueOf(Setting.Fluid), 8);
        textField.setFont(Setting.MAIN_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return textField;
    }

    // สร้างปุ่มคำนวณ
    private JButton createCalculateButton() {
        JButton button = new JButton("Calculate");
        button.setFont(Setting.SUB_FONT);
        button.setBackground(Setting.ACCENT_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.ACCENT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setEnabled(false); // ปิดปุ่มเริ่มต้น
        button.addActionListener(calculateListener); // เพิ่ม listener
        return button;
    }
}
