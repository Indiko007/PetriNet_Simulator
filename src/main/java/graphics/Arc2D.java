package graphics;

import generated.ArcType;
import petriNet.petriNetObjects.Arc;
import utilities.Convertable;
import utilities.EndPointType;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Arc2D extends Line2D.Float implements Drawable, Convertable<generated.Arc> {
    private final int arrow_size = 5;
    private final Arc arc;
    private Point endPoint;
    private EndPointType endPointType;

    Arc2D(Point startPoint, Point endPoint, Arc arc, EndPointType endPointType) {
        super(startPoint.x + center_constant, startPoint.y + center_constant, endPoint.x + center_constant, endPoint.y + center_constant);
        this.arc = arc;
        this.endPoint = endPoint;
        this.endPointType = endPointType;
    }

    Line2D.Float drawArrow(Graphics2D g, Line2D.Float line) {
        Line2D.Float newLine = endPointType == EndPointType.PLACE? getLineToPlace(line) : getLineToTransition(g, line);
        g.draw(newLine);
        if(arc.getWeight() > 1){
            drawLabel(g, line);
        }
        return newLine;
    }

    private void drawLabel(Graphics2D g, Line2D.Float line) {
        Graphics2D g2d = (Graphics2D)g.create();
        String text = String.valueOf(arc.getWeight());
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, (line.x1 + line.x2)/2 - 5, (line.y1 + line.y2)/2 - 5);
        g2d.dispose();
    }

    private Line2D.Float getLineToTransition(Graphics2D g, Line2D.Float line) {
        Line2D.Float newLine = getOffsetLine(line);
        return getShortenedLine(newLine, arrow_size);
    }

    private Line2D.Float getOffsetLine(Line2D.Float line) {
        Point newPoint = getOffsetEndPoint(line);
        return new Line2D.Float(line.getP1(), newPoint);
    }

    private Point getOffsetEndPoint(Line2D.Float line) {
        Line2D.Float arcLine = new Line2D.Float();
        for (Line2D.Float borderLine : getTransitionBordersAsLines(new Rectangle2D.Float(endPoint.x, endPoint.y, radius, radius))) {
            if(this.intersectsLine(borderLine)){
                arcLine.setLine(borderLine);
            }
        }
        return lineIntersect((int)arcLine.getX1(), (int)arcLine.getY1(), (int)arcLine.getX2(), (int)arcLine.getY2(), (int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
    }

    private Point lineIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (denom == 0.0) {
            return new Point();
        }
        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3))/denom;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3))/denom;
        if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
            return new Point((int) (x1 + ua*(x2 - x1)), (int) (y1 + ua*(y2 - y1)));
        }

        return new Point();
    }

    private ArrayList<Line2D.Float> getTransitionBordersAsLines(Rectangle2D.Float transition) {
        ArrayList<Line2D.Float> lines = new ArrayList<>();
        lines.add(new Line2D.Float(transition.x, transition.y, transition.x + radius - 1, transition.y));
        lines.add(new Line2D.Float(transition.x, transition.y + 1, transition.x, transition.y + radius));
        lines.add(new Line2D.Float(transition.x + radius, transition.y, transition.x + radius, transition.y + radius - 1));
        lines.add(new Line2D.Float(transition.x + 1, transition.y + radius, transition.x + radius, transition.y + radius));
        return lines;
    }

    private Line2D.Float getLineToPlace(Line2D.Float line) {
        return getShortenedLine(line, center_constant + arrow_size);
    }

    private Line2D.Float getShortenedLine(Line2D.Float line, int distance){
        float dx = line.x2 - line.x1;
        float dy = line.y2 - line.y1;
        float length = (float)Math.sqrt(dx*dx + dy*dy);
        if(length>0){
            dx /= length;
            dy /= length;
        }
        dx *= (length - distance);
        dy *= (length - distance);
        return new Line2D.Float(line.x1, line.y1, dx + line.x1, dy + line.y1);
    }

    abstract void drawArrowHead(Graphics2D g, Line2D.Float line);

    public int getSourceID() {
        return arc.getStartPointID();
    }

    public int getDestinationID() {
        return arc.getEndPointID();
    }

    @Override
    public Point getPosition() {return null;}

    @Override
    public int getID() {
        return arc.getID();
    }

    @Override
    public boolean onClick(MouseEvent e) {return false;}

    @Override
    public generated.Arc toGenerated() {
        generated.Arc genArc = new generated.Arc();
        genArc.setSourceId(arc.getStartPointID());
        genArc.setDestinationId(arc.getEndPointID());
        genArc.setId(arc.getID());
        genArc.setMultiplicity(arc.getWeight());
        ArcType arcType = arc.isReset() ? ArcType.RESET : ArcType.REGULAR;
        genArc.setType(arcType);
        return genArc;
    }
}
