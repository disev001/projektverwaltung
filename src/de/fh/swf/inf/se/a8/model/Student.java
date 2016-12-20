package de.fh.swf.inf.se.a8.model;

/**
 * Studen welcher Teil einer Projektgruppe sein kann.
 * Studentengruppen stehen in beziehung zu einem von ihnen zu bearbeitenden Projekten
 */
public class Student {

	private String nachname;
	private String vorname;
	private String email;
	private int matrikelnummer;


	public Student(String nachname, String vorname, String email, int matrikelnummer) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.email = email;
		this.matrikelnummer = matrikelnummer;
	}

	public String getNachname() {
		return this.nachname;
	}

	/**
	 * 
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return this.vorname;
	}

	/**
	 * 
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getEmail() {
		return this.email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public int getMatrikelnummer() {
		return this.matrikelnummer;
	}

	/**
	 * 
	 * @param matrikelnummer
	 */
	public void setMatrikelnummer(int matrikelnummer) {
		this.matrikelnummer = matrikelnummer;
	}

	public Student() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return matrikelnummer+": "+nachname+", "+vorname;
	}
}