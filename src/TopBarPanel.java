import javax.swing.*;
import java.awt.*;

// คลาสสำหรับสร้างและจัดการแถบด้านบน
class TopBarPanel extends JPanel {

    public TopBarPanel() {
        setBackground(Setting.PRIMARY_COLOR);
        setPreferredSize(new Dimension(0, 70));
        setLayout(new GridBagLayout()); // ใช้ GridBagLayout เพื่อจัดวาง component แบบยืดหยุ่น

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; //ยืดในแนวนอนเต็มคอลัมน์
        gbc.insets = new Insets(0, 20, 0, 20); // ระยะขอบรอบ component (บน,ซ้าย,ล่าง,ขวา)

        // Icon
        JLabel iconLabel = createIconLabel("src/dig.png", 48); // สร้าง label พร้อม icon ขนาด 48px
        gbc.gridx = 0; // อยู่คอลัมน์แรก
        gbc.insets = new Insets(0, 220, 0, 10); // ขยับมาทางขวา 220px
        add(iconLabel, gbc);  // เพิ่ม icon ลง panel

        // Title
        JLabel title = createTitleLabel("Jewel Suite - Gas Volume Calculator");
        gbc.gridx = 1;  // อยู่คอลัมน์ถัดไป
        gbc.insets = new Insets(0, 0, 0, 0); // เอาระยะห่างออก
        add(title, gbc); // เพิ่ม title ลง panel

        // Spacer
        gbc.gridx = 2; // อยู่คอลัมน์ที่ 2
        gbc.weightx = 1.0; // ให้กินพื้นที่ว่างที่เหลือ (ดันปุ่มไปขวาสุด)
        add(Box.createHorizontalGlue(), gbc); // ใส่กล่องเพือดันช่องว่าง

        // Exit Button
        JButton exitButton = createExitButton(); // สร้างปุ่ม Exit
        gbc.gridx = 3; // อยู่คอลัมน์สุดท้าย
        gbc.weightx = 0; //ความกว้างของปุ่ม
        gbc.insets = new Insets(5, 0, 0, 20);// ระยะขอบบน 5, ขวา 20
        gbc.anchor = GridBagConstraints.NORTHEAST; // ชิดมุมขวาบน
        add(exitButton, gbc); // เพิ่มปุ่ม Exit
    }

    private JLabel createIconLabel(String imagePath, int size) {  // สร้าง JLabel ที่มี icon
        JLabel label = new JLabel();  // สร้างlabel ว่าง
        ImageIcon icon = new ImageIcon(imagePath); // โหลดรูปจาก path
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);// ปรับขนาดรูป,เรียบร้อย
        label.setIcon(new ImageIcon(img)); // ใส่รูปเข้า label
        return label;
    }

    private JLabel createTitleLabel(String text) { // สร้าง JLabel สำหรับ title
        JLabel title = new JLabel(text); //สร้าง label แสดงข้อความ
        title.setFont(Setting.TITLE_FONT); // กำหนด font จาก Setting
        title.setForeground(Color.BLACK); // ตัวหนังสือสีดำ
        return title;
    }

    private JButton createExitButton() { // สร้างปุ่ม Exit
        JButton button = new JButton("EXIT"); // สร้างปุ่ม และ ข้อความบนปุ่ม
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // กำหนดฟอนต์
        button.setBackground(new Color(214, 27, 27)); // สีพื้นหลัง (แดง)
        button.setForeground(Color.WHITE); // ตัวหนังสือสีขาว
        button.setFocusPainted(false);  // ไม่ให้มีเส้นกรอบ focus
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18)); // ระยะขอบด้านใน
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // เปลี่ยนเมาส์เป็นรูปมือตอนลากไปโดนปุ่ม
        button.setOpaque(true);  // ให้สีพื้นหลังมองเห็น
        button.setBorderPainted(false); // ไม่แสดงเส้นขอบของปุ่ม
        button.addActionListener(e -> System.exit(0)); // กดแล้วปิดโปรแกรม
        return button;
    }
}