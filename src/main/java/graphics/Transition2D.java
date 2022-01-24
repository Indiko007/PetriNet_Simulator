package graphics;

import petriNet.petriNetExceptions.TransitionNotFirableException;
import petriNet.petriNetObjects.Transition;
import utilities.Convertable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Transition2D extends Rectangle2D.Float implements Drawable, Convertable<generated.Transition> {
    private final Transition transition;
    private Point position;

    public Transition2D(int x, int y, Transition transition) {
        super(x, y, radius, radius);
        this.position = new Point(x, y);
        this.transition = transition;
    }

    @Override
    public void draw2D(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setPaint(getColor());
        drawLabel(g2d);
        drawTransition(g2d);
        g2d.dispose();
    }

    private Paint getColor() {
        if(transition.canFire()) {
            return Color.GREEN;
        }
        return Color.RED;
    }

    private void drawTransition(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.draw3DRect(position.x, position.y, (int)this.getWidth(), (int)this.getHeight(), true);
        g2d.fill(this);
        g2d.dispose();
    }

    private void drawLabel(Graphics2D g) {
        Graphics2D g2d = (Graphics2D)g.create();
        String text = this.transition.getName();
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, getOffsetText(g2d, text) + position.x, position.y + title_offset);
        g2d.dispose();
    }

    @Override
    public boolean onClick(MouseEvent e) {
        if(this.contains(e.getX(), e.getY())){
            try {
                this.transition.fire(this.getID());
                System.out.println("can fire!");
            } catch(TransitionNotFirableException exception){
                System.out.println("can NOT fire!");
                return false;
            }
            return true;
        }
        return false;

    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getID() {
        return transition.getID();
    }

    @Override
    public generated.Transition toGenerated() {
        generated.Transition genTransition = new generated.Transition();
        genTransition.setId(transition.getID());
        genTransition.setX(position.x);
        genTransition.setY(position.y);
        genTransition.setLabel(transition.getName());
        return genTransition;
    }
}
