package de.fh.swf.inf.se.a8.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Projekte werden von Studenen bearbeitet
 */
public class Projekt {

    private String projekttitel;
    private File projektskizze;
    private File projektbeschreibung;
    private Collection<Student> projektgruppe = new ArrayList<Student>();
    private Ansprechpartner ansprechpartner;

    public Projekt(String projekttitel, File projektskizze, File projektbeschreibung, Student student, Student student2, Student student3, Ansprechpartner ansprechpartner) throws IllegalArgumentException {
        this.projekttitel = projekttitel;
        this.projektbeschreibung = projektbeschreibung;

        if (student != null)
            this.addStudent(student);
        else throw new IllegalArgumentException("\n Projekt wurde nicht angelegt\n");
        if (student2 != null)
            this.addStudent(student2);
        if (student3 != null)
            this.addStudent(student3);
        this.ansprechpartner = ansprechpartner;
    }

    public String getProjekttitel() {
        return this.projekttitel;
    }

    /**
     * @param projekttitel
     */
    public void setProjekttitel(String projekttitel) {
        this.projekttitel = projekttitel;
    }

    public File getProjektbeschreibung() {
        return this.projektbeschreibung;
    }

    /**
     * @param projektbeschreibung
     */
    public void setProjektbeschreibung(File projektbeschreibung) {
        this.projektbeschreibung = projektbeschreibung;
    }

    public File getProjektskizze() {
        return projektskizze;
    }

    public void setProjektskizze(File projektskizze) {
        this.projektskizze = projektskizze;
    }

    public Collection<Student> getProjektgruppe() {
        return this.projektgruppe;
    }

    public void setProjektgruppe(Collection<Student> projektgruppe) {
        this.projektgruppe = projektgruppe;
    }

    public Ansprechpartner getAnsprechpartner() {
        return this.ansprechpartner;
    }

    /**
     * @param ansprechpartner
     */
    public void setAnsprechpartner(Ansprechpartner ansprechpartner) {
        this.ansprechpartner = ansprechpartner;
    }

    /**
     * @param student
     */
    public void addStudent(Student student) throws IllegalArgumentException {


        if (student != null)

            if (this.projektgruppe.size() < 3)
                this.projektgruppe.add(student);
            else System.out.print("\nGruppe voll!\n");
        else throw new IllegalArgumentException("\n Student wurde nicht hinzugefügt\n");

    }

    @Override
    public String toString() {
        this.projekttitel = projekttitel;
        this.projektbeschreibung = projektbeschreibung;
        //this.projektgruppe = projektgruppe;
        String studenten = "";
        int i = 0;
        for (Student s : projektgruppe) {
            i++;
            if (i > 1)
                studenten += ", ";
            studenten += s.getNachname() + " " + s.getVorname();

        }

        String aName = this.ansprechpartner.getName();
        String aVname = this.ansprechpartner.getVorname();

        return this.projekttitel + "(" + studenten + ")";
    }
}