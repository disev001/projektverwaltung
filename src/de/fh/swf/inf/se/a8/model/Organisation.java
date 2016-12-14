package de.fh.swf.inf.se.a8.model;

/**
 * Organisationen werden Ansprechpartnern zugeteilt u.u
 */
public class Organisation {

    private String name;

    private int plz;
    private String ort;
    private String strasse;

    public String getName() {
        return this.name;
    }


    public Organisation(String name, int plz, String ort, String strasse) {
        if (name != null && !name.isEmpty())
            this.name = name;
        else throw new IllegalArgumentException("\nOrganisation Wurde nicht angelegt\n");

        this.name = name;
        this.plz = plz;

        this.ort = ort;
        this.strasse = strasse;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        if (name != null)
            this.name = name;
        else throw new IllegalArgumentException("\nOrganisation name wurde nicht geändert\n");

    }

    public int getPlz() {
        return this.plz;
    }

    /**
     * @param plz
     */
    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return this.ort;
    }

    /**
     * @param ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStrasse() {
        return this.strasse;
    }

    /**
     * @param strasse
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public Organisation() {
        throw new UnsupportedOperationException();
    }
    @Override
    public String toString() {
        return this.name;
    }
}