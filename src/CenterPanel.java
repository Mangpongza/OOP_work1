import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// คลาสสำหรับสร้างและจัดการส่วนกลางของหน้าจอ
class CenterPanel extends JPanel {
    private JPanel gridPanel;
    private int rows = 10;
    private int cols = 20;
    private static JLabel info; //

    public static void setInfo(String text) {
        System.out.println(text);
        info.setText(text);
    }

    public CenterPanel() {
        setBackground(Setting.SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout(20, 0));

        gridPanel = createGridPanel();
        createEmptyGrid();
        add(gridPanel, BorderLayout.CENTER);

        JPanel legendPanel = createLegendPanel();
        add(legendPanel, BorderLayout.EAST);
    }

    public void updateGridWithData(double[][] grid) {
        gridPanel.removeAll();

        // ✅ ถ้าไม่มีข้อมูลเลย (grid == null หรือแถว = 0) → ไม่สร้าง grid
        if (grid == null || grid.length == 0) {
            gridPanel.revalidate();
            gridPanel.repaint();
    
        }
        int rows = grid.length;
        int maxCols = 0;
        for (double[] row : grid) {
            if (row.length > maxCols) maxCols = row.length;
        }
        gridPanel.setLayout(new GridLayout(rows, maxCols, 2, 2));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < maxCols; c++) {
                if (c >= grid[r].length) {
                    JPanel emptyCell = new JPanel();
                    emptyCell.setOpaque(false);
                    gridPanel.add(emptyCell);

                } else {
                    double value = grid[r][c];
                    JPanel cell = new JPanel(new BorderLayout());
                    JLabel label;
                    double percent = 0;
                    if (Double.isNaN(value)) {
                        label = new JLabel("", SwingConstants.CENTER); // ✅ ถ้าเป็น NaN → แสดงว่างเปล่า ""
                        label.setOpaque(true);
                        label.setBackground(Color.WHITE);
                    } else {
                        percent = Cal(value);
                        label = new JLabel(String.format("%.0f%%", percent), SwingConstants.CENTER);
                        label.setOpaque(true);

                        if (percent == 0) label.setBackground(Setting.NO_GAS_COLOR);
                        else if (percent < 50) label.setBackground(Setting.LOW_GAS_COLOR);
                        else label.setBackground(Setting.HIGH_GAS_COLOR);

                        double finalPercent = percent;
                        double finalValue = value;
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                double volum = Calvalum(finalValue);
                                if (finalPercent <= 0) volum = 0;
                                CenterPanel.setInfo(String.format("Percent: %.2f Volume: %.2f", finalPercent, volum));
                                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        });
                    }

                    cell.add(label, BorderLayout.CENTER);
                    gridPanel.add(cell);
                }
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private double Cal(double base){
        double top = base - Setting.Top;
        double total = base - top; // This will always equal Setting.Top
        double gas = Math.max(0, Math.min(Setting.Fluid, base) - top);
        return gas / total * 100;
    }
    private double Calvalum(double base){
        double top = base - Setting.Top;
        double dept = Math.min(Setting.Fluid, base) - top;
        double volume = Setting.CellSize * Setting.CellSize * dept;
        return volume;
    }

    public void createEmptyGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols, 2, 2));

        for (int i = 0; i < rows * cols; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE);
            cell.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
            gridPanel.add(cell);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel(new GridLayout(rows, cols, 2, 2));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 3, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        return panel;
    }

    private JPanel createLegendPanel() {
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        info = new JLabel("");
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

    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        item.setBackground(Setting.SECONDARY_COLOR);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(24, 24));
        colorBox.setBorder(new LineBorder(Color.DARK_GRAY, 1));

        JLabel label = new JLabel(text);
        label.setFont(Setting.MAIN_FONT);

        item.add(colorBox);
        item.add(label);
        return item;
    }

    public void clearLegend() {
        info.setText("");
    }

    public void setFluidContact(double fluidContact) {
        Setting.Fluid = fluidContact;
    }


}

