package utilities.buttons;

import graphics.petriNetGUI.PetriCanvas;
import utilities.listeners.AddTransitionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTransitionButton extends Button implements ActionListener {

    private final PetriCanvas canvas;

    public AddTransitionButton(String label, PetriCanvas canvas) throws HeadlessException {
        super(label);
        this.canvas = canvas;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setMouseListener(new AddTransitionListener(canvas));
    }
}
