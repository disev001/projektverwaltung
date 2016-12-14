package de.fh.swf.inf.se.a8.model;

/**
 * Ansprechpartner sind einer Organisation zuzuweisen und auch Projekte, denen sie zugewiesen sind zu Speichern
 */
public class Ansprechpartner {

    private String name;
    private String vorname;
    private String email;
    private String telefon;
    private Organisation unternehmen;

    public Ansprechpartner(String name, String vorname, String email, String telefon, Organisation unternehmen) throws IllegalArgumentException {
        if (name != null && !name.equals(""))
            this.name = name;
        else throw new IllegalArgumentException("\n Ansprechpartner braucht einen Namen\n");
        this.name = name;
        this.vorname = vorname;
        this.email = email;
        this.telefon = telefon;

        if (unternehmen != null)
            this.unternehmen = unternehmen;
        else throw new IllegalArgumentException("\n Ansprechpartner wurde nicht angelegt\n");

    }

    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        if (name != null && !name.equals(""))
            this.name = name;
        else throw new IllegalArgumentException("\n Ansprechpartner braucht einen Namen\n");
    }

    public String getVorname() {
        return this.vorname;
    }

    /**
     * @param vorname
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getEmail() {
        return this.email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {


        this.email = email;
    }

    public String getTelefon() {
        return this.telefon;
    }

    /**
     * @param telefon
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Organisation getUnternehmen() {
        return this.unternehmen;
    }

    /**
     * @param unternehmen
     */
    public void setUnternehmen(Organisation unternehmen) throws IllegalArgumentException {
        if (unternehmen != null)
            this.unternehmen = unternehmen;
        else throw new IllegalArgumentException("\n Unternehmen wurde nicht gesetzt\n");
    }


    public Ansprechpartner() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return this.getName() + ", " + this.getVorname();
    }
}