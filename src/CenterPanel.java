import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

// คลาสสำหรับสร้างและจัดการส่วนกลางของหน้าจอ
class CenterPanel extends JPanel {
    private JPanel gridPanel;
    private int rows = 10;
    private int cols = 20;
    private static JLabel info; // ⭐ เปลี่ยนเป็น static

    public static void setInfo(String text) { // ⭐ เปลี่ยนเป็น static
        System.out.println(text);
        info.setText(text);
    }


    public CenterPanel() {
        setBackground(Setting.SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setLayout(new BorderLayout(20, 0));

        gridPanel = createGridPanel();
        createEmptyGrid();
        add(gridPanel, BorderLayout.CENTER);

        JPanel legendPanel = createLegendPanel();
        add(legendPanel, BorderLayout.EAST);
    }

    public void updateGridWithData(double[][] grid) {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(grid.length, grid[0].length, 2, 2));

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                double value = grid[r][c];
                double percent = Cal(value);
                String percentText = String.format("%.2f%%", percent);
                String percentUI = String.format("%.1f%%", percent);
                JLabel label = new JLabel(percentUI, SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if (percent == 0) {
                    label.setBackground(Setting.NO_GAS_COLOR);// สีพื้นหลังที่คุณกำหนด
                    label.setOpaque(true);
                }
                if (percent == 1) {
                    label.setBackground(Setting.LOW_GAS_COLOR);// สีพื้นหลังที่คุณกำหนด
                    label.setOpaque(true);
                }
                if (percent == 2) {
                    label.setBackground(Setting.HIGH_GAS_COLOR);// สีพื้นหลังที่คุณกำหนด
                    label.setOpaque(true);
                }

                gridPanel.add(label);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        double volum = Calvalum(value);
                        if (percent<=0) {
                            volum = 0;
                            String infoText = String.format("เปอร์เซ็นต์: %.2f ปริมาตร: %.2f", percent, volum);
                            CenterPanel.setInfo(infoText);
                        }
                        else {
                            String infoText = String.format("เปอร์เซ็นต์: %.2f ปริมาตร: %.2f", percent, volum);
                            CenterPanel.setInfo(infoText);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });

            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }


    private double Cal(double base){
        double top = base - Setting.Top; //2204

        double total = base - top; //2404-2204 =  200


        double gas = Math.max(0, Math.min(Setting.Fluid, base) - top);

        if (gas / total <= 0) return 0;
        if (gas / total < 0.5) return 1;
        return 2;
    }
    private double Calvalum(double base){
        double top = base - Setting.Top;
        double dept  = Math.min(Setting.Fluid,base)- top;
        double volume= Setting.CellSize*Setting.CellSize*dept;


        return volume;
    }


    public void updateGridWithSampleData() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(rows, cols, 2, 2));
        for (int i = 0; i < rows * cols; i++) {
            gridPanel.add(new GridCellPanel());
        }
        gridPanel.revalidate();
        gridPanel.repaint();

    }

    private void createEmptyGrid() {
        gridPanel.removeAll();
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

}

// คลาสสำหรับสร้างและจัดการแต่ละ Cell ใน Grid
class GridCellPanel extends JPanel {
    public GridCellPanel() {
        super(new BorderLayout());

        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setPreferredSize(new Dimension(50, 40));

        // Randomly determine cell properties
        double random = Math.random();
        Color cellColor;
        String percentage;
        String volume;
        if (random < 0.3) {
            cellColor = Setting.NO_GAS_COLOR;
            percentage = "0%";
            volume = "0";
        } else if (random < 0.65) {
            cellColor = Setting.LOW_GAS_COLOR;
            percentage = "25%";
            volume = "562";
        } else {
            cellColor = Setting.HIGH_GAS_COLOR;
            percentage = "75%";
            volume = "1688";
        }

        setBackground(cellColor);
        Color textColor = (cellColor.equals(Setting.LOW_GAS_COLOR)) ? Color.BLACK : Color.WHITE;

        JLabel percentLabel = new JLabel(percentage, JLabel.CENTER);
        percentLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        percentLabel.setForeground(textColor);

        JLabel volumeLabel = new JLabel(volume, JLabel.CENTER);
        volumeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        volumeLabel.setForeground(textColor);

        add(percentLabel, BorderLayout.CENTER);
        add(volumeLabel, BorderLayout.SOUTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                CenterPanel.setInfo("เปอร์เซ็นต์: " + percentage + ", ปริมาตร: " + volume);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

    }

}