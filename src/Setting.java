import java.awt.*;

// คลาสสำหรับเก็บค่าคงที่ต่างๆ เช่น สีและฟอนต์
// ช่วยให้โค้ดหลักสะอาดขึ้นและแก้ไขค่าต่างๆ ได้ง่าย
public class Setting {
    public  static final Double Fluid  = 2500.0;
    public  static final int Top  = 200;
    public  static final int CellSize  = 150;


    public static final Color PRIMARY_COLOR = new Color(95, 169, 241);
    public static final Color SECONDARY_COLOR = new Color(138, 193, 230);
    public static final Color ACCENT_COLOR = new Color(138, 211, 227);
    public static final Color TEXT_COLOR = new Color(0, 0, 0);
    public static final Color FILE_COLOR = new Color(39, 66, 255);

    public static final Color NO_GAS_COLOR = new Color(255, 0, 0);
    public static final Color LOW_GAS_COLOR = new Color(237, 255, 0);
    public static final Color HIGH_GAS_COLOR = new Color(4, 255, 0);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUB_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
}