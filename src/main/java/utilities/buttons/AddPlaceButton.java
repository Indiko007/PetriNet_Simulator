package utilities.buttons;

import graphics.petriNetGUI.PetriCanvas;
import utilities.listeners.AddPlaceListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlaceButton extends Button implements ActionListener {
    private final PetriCanvas canvas;

    public AddPlaceButton(String label, PetriCanvas canvas) throws HeadlessException {
        super(label);
        this.canvas = canvas;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setMouseListener(new AddPlaceListener(canvas));
    }
}
