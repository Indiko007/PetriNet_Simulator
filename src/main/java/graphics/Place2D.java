package graphics;

import petriNet.petriNetObjects.Place;
import utilities.Convertable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class Place2D extends Ellipse2D.Float implements Drawable, Convertable<generated.Place> {
    private final Place place;
    private Point position;

    public Place2D(int x, int y, Place place){
        super(x, y, radius, radius);
        this.position = new Point(x, y);
        this.place = place;
    }

    @Override
    public void draw2D(Graphics2D g) {
        drawPlace(g);
        drawLabels(g);
    }

    private void drawPlace(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawOval(position.x, position.y, (int)this.getWidth(), (int)this.getHeight());
        g2d.setPaint(Color.WHITE);
        g2d.fill(this);
        g2d.dispose();
    }

    private void drawLabels(Graphics2D g) {
        Graphics2D g2d = (Graphics2D)g.create();
        String text = this.place.getName();
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, getOffsetText(g2d, text) + position.x, position.y + title_offset);
        String tokenNumber =  String.valueOf(this.place.getTokens());
        g2d.drawString(tokenNumber, getOffsetText(g2d, tokenNumber) + position.x, (int)this.getCenterY() + 5);
        g2d.dispose();
    }

    @Override
    public boolean onClick(MouseEvent e) {
        if(this.contains(e.getX(), e.getY())) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                place.setTokens(place.getTokens() + 1);
                return true;
            }
            else if(e.getButton() == MouseEvent.BUTTON3) {
                if ((place.getTokens() - 1) >= 0) {
                    place.setTokens(place.getTokens() - 1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getID() {
        return place.getID();
    }

    @Override
    public generated.Place toGenerated() {
        generated.Place genPlace = new generated.Place();
        genPlace.setId(place.getID());
        genPlace.setLabel(place.getName());
        genPlace.setTokens(place.getTokens());
        genPlace.setX(position.x);
        genPlace.setY(position.y);
        genPlace.setStatic(false);
        genPlace.setIsStatic(false);
        return genPlace;
    }
}
