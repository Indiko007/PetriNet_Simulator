package utilities;

import generated.Arc;
import generated.Document;
import generated.Place;
import graphics.Arc2D;
import graphics.Drawable;
import graphics.Place2D;
import graphics.Transition2D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Exporter {
    private List<Drawable> elements;
    private List<Transition2D> transitions;
    private List<Place2D> places;
    private List<Arc2D> arcs;

    public Exporter(List<Drawable> elements) {
        this.elements = elements;
//        divideElements();
//        System.out.println("arcs: " + arcs);
//        System.out.println("transitions: " + transitions);
//        System.out.println("places: " + places);
    }

    private void divideElements() {
        for (Drawable element : elements) {
            if(element instanceof Transition2D) {
                System.out.println("It is!");
                transitions.add(((Transition2D) element));
                System.out.println(element);
            }
        }
    }

    private void divideElements2() {
        for (Drawable element : elements) {
            System.out.println(element);
            if(element.getPosition() == null){
                arcs.add((Arc2D)element);
                System.out.println("Adding arc: " + element);
                continue;
            }
            try {
                Transition2D transition = (Transition2D) element;
                transitions.add(transition);
                System.out.println("Adding transition: " + transition);
            } catch(ClassCastException exception) {
                try {
                    places.add((Place2D)element);
                    System.out.println("Adding place: " + element);
                } catch(ClassCastException ignored) {
                    System.out.println("da hek?");
                }
            }
        }
    }

    private void marshal(File file) {
        checkFileExtension(file);
        try {
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(createDocument(), file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void checkFileExtension(File file) { // TODO: Prepracovat!
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if(extension.equals("xml")){
                return;
            }
        }
        file.renameTo(new File(file.getAbsolutePath() + ".xml"));
    }

    private void addExtension(File file) {
    }

    private Document createDocument() {
        Document document = new Document();
//        document.setArcs(convertArcs());
//        document.setPlaces(convertPlaces());
//        document.setTransitions(convertTransitions());
        return document;
    }

//    private List<Transition> convertTransitions() {
////        ArrayList<petriNet.petriNetObjects.Transition> transitions = getTransitions();
//
//    }

    private List<Place> convertPlaces() {
        return null;
    }

    private List<Arc> convertArcs() {
        return null;
    }

    public boolean exportNet() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Petri net file selector!");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Documents (*.xml)", "xml"));
//        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false); //optional
        int result = fileChooser.showSaveDialog(new Button());
        if (result == JFileChooser.APPROVE_OPTION) {
            marshal(fileChooser.getSelectedFile());
            return true;
        }
        return false;
    }



}
