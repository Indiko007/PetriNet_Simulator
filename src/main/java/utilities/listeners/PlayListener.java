package utilities.listeners;

import graphics.Drawable;
import graphics.petriNetGUI.PetriCanvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayListener extends MouseAdapter {
    private PetriCanvas canvas;

    public PlayListener(PetriCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        for (Drawable element : canvas.getElements()) {
            if(element.onClick(e)){
                canvas.repaint();
            }
        }
    }
}
