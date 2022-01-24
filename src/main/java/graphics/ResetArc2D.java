package graphics;

import javafx.util.Pair;
import petriNet.petriNetObjects.Arc;
import utilities.EndPointType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class ResetArc2D extends Arc2D {
    public ResetArc2D(Point point, Point point1, Arc a) {
        super(point, point1, a, EndPointType.TRANSITION);
    }

    @Override
    public void draw2D(Graphics2D graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setPaint(Color.BLACK);
        Line2D.Float newLine = super.drawArrow(g2d, this);
        drawArrowHead(g2d, newLine);
        g2d.dispose();
    }

    @Override
    void drawArrowHead(Graphics2D g, Float line) {
        AffineTransform tx = new AffineTransform();
        tx.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle-Math.PI/2d));

        Pair<Polygon, Polygon> arrowShape = getShapes();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setTransform(tx);
        g2d.fill(arrowShape.getKey());
        g2d.fill(arrowShape.getValue());
        g2d.dispose();
    }

    private Pair<Polygon, Polygon> getShapes() {
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);
        Polygon arrowHead2 = new Polygon();
        arrowHead2.addPoint(0,-5);
        arrowHead2.addPoint(-5,-15);
        arrowHead2.addPoint(5,-15);
        return new Pair<>(arrowHead, arrowHead2);
    }
}
