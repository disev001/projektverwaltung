package de.fh.swf.inf.se.a8.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Projekte werden von Studenen bearbeitet
 */
public class Projekt {

    private String projekttitel;
    private File projektskizze = null;
    private File projektbeschreibung = null;
    private Collection<Student> projektgruppe = new ArrayList<Student>();
    private Ansprechpartner ansprechpartner;
    private int mkNr1;
    private Student dozent;
    private int entscheidung;
    private String kommentar;

 public Projekt(String projekttitel, Student student, Student student2, Student student3, Ansprechpartner ansprechpartner, Student dozent, int entscheidung, String kommentar) throws IllegalArgumentException {
        this.projekttitel = projekttitel;
        if (student != null){
            this.addStudent(student);
            this.mkNr1 = student.getMatrikelnummer();
        }
        else throw new IllegalArgumentException("\n Projekt wurde nicht angelegt\n");
        if (student2 != null)
            this.addStudent(student2);
        if (student3 != null)
            this.addStudent(student3);
        this.ansprechpartner = ansprechpartner;
        if (dozent.getMatrikelnummer() == 0)
            this.dozent = dozent;
        else throw new IllegalArgumentException("\n Projekt wurde nicht angelegt\n");
        this.entscheidung = entscheidung;
        this.kommentar = kommentar;
    }
    public Projekt(String projekttitel, Student student,Ansprechpartner ansprechpartner, Student dozent) throws IllegalArgumentException {
        this.projekttitel = projekttitel;
        if (student != null)
            this.addStudent(student);
        this.ansprechpartner = ansprechpartner;
        if (dozent.getMatrikelnummer() == 0)
            this.dozent = dozent;
        else throw new IllegalArgumentException("\n Projekt wurde nicht angelegt\n");
        this.entscheidung = 0;
        this.kommentar = "";
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
    public int getProjetteilnehmer1(){
        return mkNr1;
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

    public String getEntscheidung() {
        switch (entscheidung) {
            case 1:
                return "Zugelassen";

            case 2:
                return "Abgelehnt";

            case 3:
                return "Bitte Ergänzen";

            default:
                return "Nicht bewertet";
        }
    }

    public void setEntscheidung(int entscheidung) {
        this.entscheidung = entscheidung;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
    public Student getDozent() {
        return dozent;
    }

    public void setDozent(Student dozent) {
        this.dozent = dozent;
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