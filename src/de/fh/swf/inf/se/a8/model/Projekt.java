package de.fh.swf.inf.se.a8.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Projekte werden von Studenen bearbeitet
 */
public class Projekt {

    private String projekttitel;
    private String projektbeschreibung;
    private String vortrag1;
    private String vortrag2;
    private Collection<Student> projektgruppe = new ArrayList<Student>();
    private Ansprechpartner ansprechpartner;

    public Projekt(String projekttitel, String projektbeschreibung, String vortrag1, String vortrag2, Student student, Ansprechpartner ansprechpartner) throws IllegalArgumentException{
        this.projekttitel = projekttitel;
        this.projektbeschreibung = projektbeschreibung;

            if (student != null)
                this.addStudent(student);
            else throw new IllegalArgumentException("\n Projekt wurde nicht angelegt\n");

        this.vortrag1 = vortrag1;
        this.vortrag2 = vortrag2;
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

    public String getProjektbeschreibung() {
        return this.projektbeschreibung;
    }

    /**
     * @param projektbeschreibung
     */
    public void setProjektbeschreibung(String projektbeschreibung) {
        this.projektbeschreibung = projektbeschreibung;
    }

    public String getVortrag1() {
        return this.vortrag1;
    }

    /**
     * @param vortrag1
     */
    public void setVortrag1(String vortrag1) {
        this.vortrag1 = vortrag1;
    }

    public String getVortrag2() {
        return this.vortrag2;
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
     *
     * @param student
     */
    public void addStudent(Student student)  throws IllegalArgumentException {


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
        this.vortrag1 = vortrag1;
        this.vortrag2 = vortrag2;
        //this.projektgruppe = projektgruppe;
        String studenten = "";
        int i = 0;
        for (Student s : projektgruppe) {
            i++;
            if(i>1)
                studenten+=", ";
            studenten += s.getNachname() + " " + s.getVorname();

        }

        String aName = this.ansprechpartner.getName();
        String aVname = this.ansprechpartner.getVorname();

        return this.projekttitel+"("+studenten +")";

     /*   return "\nProjekttitel: " + this.projekttitel +
                "\n Projektbeschreibung: " + this.projektbeschreibung +
                "\n Vortrag nr1: " + this.vortrag1 +
                "\n Vortrag nr2: " + this.vortrag2 +
                "\n Ansprechpartner: " + aVname + " " + aVname +
                studenten + "\n";*/

    }
}