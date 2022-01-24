package utilities.buttons;

import generated.Document;
import graphics.Drawable;
import graphics.petriNetGUI.PetriCanvas;
import petriNet.PetriNet;
import petriNet.petriNetExceptions.ArcWeigthOutOfBoundsException;
import petriNet.petriNetExceptions.PetriNetCreationException;
import utilities.Graphics2DTransformer;
import utilities.Importer;
import utilities.PetriNetTransformer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class ImportButton extends Button implements ActionListener {
    private PetriNet pNet;
    private Document document;
    private PetriCanvas canvas;

    public ImportButton(String label, PetriCanvas canvas) throws HeadlessException {
        super(label);
        addActionListener(this);
        this.pNet = new PetriNet();
        this.document = new Document();
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pNet.reset();
        try {
            importPetriNetFromXML();
            transformPetriNet();
            transformGraphics();
        } catch (ArcWeigthOutOfBoundsException | PetriNetCreationException exception) {
            System.out.println("This PetriNet cannot exist!");
        }
    }

    private void importPetriNetFromXML() {
        Importer importer = new Importer();
        document = importer.importNet();
        if(document == null) {
            throw new PetriNetCreationException("PetriNet couldn't be imported!");
        }
    }

    private void transformPetriNet() {
        PetriNetTransformer transformer = new PetriNetTransformer();
        pNet = transformer.transform(document);
    }

    private void transformGraphics() {
        Graphics2DTransformer transformer = new Graphics2DTransformer(pNet);
        List<Drawable> elements = transformer.transform(document);
        Collections.reverse(elements);
        canvas.setElements(elements);
        canvas.setPetriNet(pNet);
        canvas.repaint();
    }
}
