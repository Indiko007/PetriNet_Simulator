package utilities.buttons;

import graphics.petriNetGUI.PetriCanvas;
import utilities.listeners.RemoveItemListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveButton extends Button implements ActionListener {
    private final PetriCanvas canvas;

    public RemoveButton(String label, PetriCanvas canvas) throws HeadlessException {
        super(label);
        this.canvas = canvas;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setMouseListener(new RemoveItemListener(canvas));
    }
}
