import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

// คลาสสำหรับสร้างและจัดการส่วนกลางของหน้าจอ
class CenterPanel extends JPanel {
    private JPanel gridPanel;
    private int rows = 10;
    private int cols = 20;

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
        panel.setBackground(Setting.SECONDARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Setting.PRIMARY_COLOR, 2, true),
                "Legend",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                Setting.SUB_FONT,
                Setting.TEXT_COLOR
        ));
        panel.setPreferredSize(new Dimension(200, 300));
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        panel.add(Box.createVerticalStrut(2));
        panel.add(createLegendItem(Setting.NO_GAS_COLOR, "No Gas (0%)"));

        panel.add(Box.createVerticalStrut(2));
        panel.add(createLegendItem(Setting.LOW_GAS_COLOR, "Low Gas (<50%)"));
        panel.add(Box.createVerticalStrut(2));
        panel.add(createLegendItem(Setting.HIGH_GAS_COLOR, "High Gas (≥50%)"));
        panel.add(Box.createVerticalGlue());

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
        setOpaque(true);
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
    }
}