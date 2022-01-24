package graphics;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Drawable extends Shape {

    int radius = 30;
    int center_constant = radius / 2;
    int title_offset = (int)((1.5)*radius);

    Font font = new Font("Arial", Font.BOLD, 11);

    Point getPosition();

    int getID();

    void draw2D(Graphics2D graphics);

    boolean onClick(MouseEvent e);

    default int getOffsetText(Graphics2D g2d, String text) {
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return ((radius/2) - (stringLen/2));
    }
}
