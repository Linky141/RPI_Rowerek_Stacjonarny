package GUI;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius=0;
    private Color color=Color.black;
    private int thickness=1;

    public RoundedBorder(int thickness) {
        this.thickness = thickness;
    }
    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    public RoundedBorder(int thickness, int radius) {
        this.radius = radius;
        this.thickness = thickness;
    }
    public RoundedBorder(int thickness, int radius, Color color) {
        this.radius = radius;
        this.thickness = thickness;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(color);
        g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
