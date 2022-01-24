package utilities.buttons;

import generated.ArcType;
import graphics.petriNetGUI.PetriCanvas;
import utilities.listeners.AddArcListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddArcButton extends Button implements ActionListener {
    private final PetriCanvas canvas;
    private final ArcType arcType;

    public AddArcButton(String label, PetriCanvas canvas, ArcType arcType) throws HeadlessException {
        super(label);
        this.canvas = canvas;
        this.arcType = arcType;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setMouseListener(new AddArcListener(canvas, arcType));
    }
}
