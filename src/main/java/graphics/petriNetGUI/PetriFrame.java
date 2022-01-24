package graphics.petriNetGUI;

import generated.ArcType;
import utilities.buttons.*;
import java.awt.*;
import java.awt.event.*;

public class PetriFrame extends Frame{

    public PetriFrame() {
        super("PetriNet simulator");
        setSize(1024, 768);

        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
                dispose();
                System.exit(0);
            }
          }
        );

        // CANVAS creation
        PetriCanvas canvas = new PetriCanvas();
        add(canvas, BorderLayout.CENTER);

        // ADDING BUTTONS TO A PANEL
        Panel panel = new Panel();
        panel.add(new ImportButton("Import", canvas));
        panel.add(new ExportButton("Export", canvas));
        panel.add(new AddPlaceButton("Add place", canvas));
        panel.add(new AddTransitionButton("Add transition", canvas));
        panel.add(new AddArcButton("Add basic arc", canvas, ArcType.REGULAR));
        panel.add(new AddArcButton("Add reset arc", canvas, ArcType.RESET));
        panel.add(new RemoveButton("Remove", canvas));
        panel.add(new PlayButton("Play", canvas));

        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

}
