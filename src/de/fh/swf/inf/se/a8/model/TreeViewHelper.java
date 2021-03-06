package de.fh.swf.inf.se.a8.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class TreeViewHelper {

    ObservableList<Organisation> org;
    ObservableList<Ansprechpartner> ansp;

    public TreeViewHelper(ObservableList<Ansprechpartner> a, ObservableList<Organisation> o) {
        this.org = o;
        this.ansp = a;
    }

    public ArrayList<TreeItem> getUnternehmen() {
        ArrayList<TreeItem> unternehmen = new ArrayList<TreeItem>();
        for (Organisation u : org) {
            TreeItem unternehmenItem = new TreeItem(u);

            unternehmenItem.getChildren().addAll(getAnsprechpartner(u.getName()));

            unternehmen.add(unternehmenItem);
        }
        return unternehmen;
    }

    private ArrayList<TreeItem> getAnsprechpartner(String unternehmensname) {
        ArrayList<TreeItem> ansprechpartner = new ArrayList<TreeItem>();

        for (Ansprechpartner a : ansp) {
            if (a.getUnternehmen().getName().equals(unternehmensname))
                ansprechpartner.add(new TreeItem(a));
        }
        return ansprechpartner;
    }
}