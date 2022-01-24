package utilities.buttons;

import graphics.petriNetGUI.PetriCanvas;
import utilities.Exporter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportButton extends Button implements ActionListener {
    private PetriCanvas canvas;

    public ExportButton(String label, PetriCanvas canvas) throws HeadlessException {
        super(label);
        addActionListener(this);
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Exporter exporter = new Exporter(canvas.getElements());
        exporter.exportNet();
        System.out.println("Was exported!");
    }
}
