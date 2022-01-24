package utilities;

import generated.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;

public class Importer {

    private Document unmarshal(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Document) unmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            return null;
        }
    }

    public Document importNet() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Petri net file selector!");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Documents (*.xml)", "xml"));
        int result = fileChooser.showOpenDialog(new Button());
        if (result == JFileChooser.APPROVE_OPTION) {
             return unmarshal(fileChooser.getSelectedFile().getPath());
        }
        return null;
    }
}
