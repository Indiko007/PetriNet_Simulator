package utilities;

import graphics.Drawable;

import java.awt.*;
import java.util.Comparator;

public class ElementComparator implements Comparator<Drawable> {
    @Override
    public int compare(Drawable o1, Drawable o2) {
        Point res1 = o1.getPosition();
        Point res2 = o2.getPosition();
        if(res1 == null && res2 != null) {
            return -1;
        }
        else if(res1 != null && res2 == null) {
            return 1;
        }
        return 0;
    }
}
