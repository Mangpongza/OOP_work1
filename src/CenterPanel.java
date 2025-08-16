import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class CenterPanel extends JPanel {
    private JPanel gridPanel; // พาเนลหลักสำหรับแสดง grid
    private int rows = 10;    // จำนวนแถว default ของ grid
    private int cols = 20;    // จำนวนคอลัมน์ default ของ grid
    private static JLabel info; // label แสดงข้อมูลเมื่อ hover cell

    // ฟังก์ชันตั้งค่า text ของ info label
    public static void setInfo(String text) {
        System.out.println(text); // แสดงใน console
        if (info != null) info.setText(text); // อัปเดตใน GUI
    }

    // constructor ของ CenterPanel
    public CenterPanel() {
        setBackground(Setting.SECONDARY_COLOR); // สีพื้นหลัง
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margin รอบ panel
        setLayout(new BorderLayout(20, 0)); // layout หลัก

        gridPanel = createGridPanel(); // สร้าง panel สำหรับ grid
        createEmptyGrid();             // สร้าง grid ว่างเริ่มต้น
        add(gridPanel, BorderLayout.CENTER); // วาง gridPanel ตรงกลาง

        JPanel legendPanel = createLegendPanel(); // สร้าง panel legend
        add(legendPanel, BorderLayout.EAST);      // วางทางขวา
    }

    // ฟังก์ชันอัปเดต grid ตามข้อมูล 2D array
    public void updateGridWithData(double[][] grid) {
        gridPanel.removeAll(); // ลบ grid เก่า

        if (grid == null || grid.length == 0) { // ถ้าไม่มีข้อมูล → ไม่สร้าง grid
            gridPanel.revalidate();
            gridPanel.repaint();
            return;
        }

        int rows = grid.length; // จำนวนแถวจากข้อมูล
        int maxCols = 0;        // หาจำนวนคอลัมน์สูงสุด
        for (double[] row : grid) {
            if (row.length > maxCols) maxCols = row.length;
        }

        gridPanel.setLayout(new GridLayout(rows, maxCols, 2, 2)); // กำหนด layout ใหม่

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < maxCols; c++) {
                if (c >= grid[r].length) { // ถ้า column ไม่มีค่า → cell ว่าง
                    JPanel emptyCell = new JPanel();
                    emptyCell.setOpaque(false);
                    gridPanel.add(emptyCell);
                } else {
                    double value = grid[r][c]; // ค่าจาก grid
                    JPanel cell = new JPanel(new BorderLayout());
                    JLabel label;
                    double percent = 0;
                    if (Double.isNaN(value)) { // ถ้าเป็น NaN → แสดง 0% สีแดง
                        label = new JLabel("0%", SwingConstants.CENTER);
                        label.setOpaque(true);
                        label.setBackground(Color.red);
                    } else {
                        percent = Cal(value); // คำนวณเปอร์เซ็นต์
                        label = new JLabel(String.format("%.0f%%", percent), SwingConstants.CENTER);
                        label.setOpaque(true);

                        // กำหนดสี cell ตามเปอร์เซ็นต์
                        if (percent == 0) label.setBackground(Setting.NO_GAS_COLOR);
                        else if (percent < 50) label.setBackground(Setting.LOW_GAS_COLOR);
                        else label.setBackground(Setting.HIGH_GAS_COLOR);

                        double finalPercent = percent;
                        double finalValue = value;
                        // เพิ่ม hover event แสดงข้อมูล
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                double volum = Calvalum(finalValue); // คำนวณ volume
                                if (finalPercent <= 0) volum = 0;
                                CenterPanel.setInfo(String.format("Percent: %.2f Volume: %.2f", finalPercent, volum));
                                label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // เปลี่ยน cursor
                                label.setBorder(new LineBorder(Color.BLACK,3));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // คืน cursor
                                label.setBorder(null);
                            }
                        });
                    }

                    cell.add(label, BorderLayout.CENTER); // ใส่ label ใน cell
                    gridPanel.add(cell); // เพิ่ม cell ลง gridPanel
                }
            }
        }
        gridPanel.revalidate(); // อัปเดต GUI
        gridPanel.repaint();
    }

    // ฟังก์ชันคำนวณเปอร์เซ็นต์ gas
    private double Cal(double base){
        double top = base - Setting.Top;
        double total = base - top; // จะได้ค่าเท่ากับ Setting.Top
        double gas = Math.max(0, Math.min(Setting.Fluid, base) - top);
        return gas / total * 100;
    }

    // ฟังก์ชันคำนวณ volume ของ cell
    private double Calvalum(double base){
        double top = base - Setting.Top;
        double dept = Math.min(Setting.Fluid, base) - top;
        double volume = Setting.CellSize * Setting.CellSize * dept;
        return volume;
    }

    // สร้าง grid ว่างเริ่มต้น
    public void createEmptyGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols, 2, 2));

        for (int i = 0; i < rows * cols; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE); // สีพื้นขาว
            cell.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1)); // เส้นขอบบาง
            gridPanel.add(cell);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // สร้าง panel สำหรับ grid
    private JPanel createGridPanel() {
        JPanel panel = new JPanel(new GridLayout(rows, cols, 2, 2));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 3, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return panel;
    }

    // สร้าง Legend panel ด้านข้าง
    private JPanel createLegendPanel() {
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        info = new JLabel(""); // label แสดงข้อมูล
        panel.setLayout(new BorderLayout());
        panel2.setLayout(new GridLayout(3,1));
        panel.setBackground(Setting.SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 2, true),
                "Legend",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                Setting.SUB_FONT,
                Setting.TEXT_COLOR
        ));
        panel.setPreferredSize(new Dimension(200, 300));
        panel2.add(createLegendItem(Setting.NO_GAS_COLOR, "No Gas (0%)"));
        panel2.add(createLegendItem(Setting.LOW_GAS_COLOR, "Low Gas (<50%)"));
        panel2.add(createLegendItem(Setting.HIGH_GAS_COLOR, "High Gas (≥50%)"));
        panel.add(panel2,BorderLayout.SOUTH);
        panel.add(info,BorderLayout.NORTH);
        return panel;
    }

    // สร้าง item ของ Legend
    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        item.setBackground(Setting.SECONDARY_COLOR);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel colorBox = new JPanel(); // กล่องสี
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(24, 24));
        colorBox.setBorder(new LineBorder(Color.DARK_GRAY, 1));

        JLabel label = new JLabel(text); // ข้อความ Legend
        label.setFont(Setting.MAIN_FONT);

        item.add(colorBox);
        item.add(label);
        return item;
    }

    public void clearLegend() {
        info.setText(""); // ล้างข้อความ info
    }

    public void setFluidContact(double fluidContact) {
        Setting.Fluid = fluidContact; // ปรับระดับ fluid
    }
}


