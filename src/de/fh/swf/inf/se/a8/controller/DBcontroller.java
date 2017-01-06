package de.fh.swf.inf.se.a8.controller;

import com.mysql.jdbc.PreparedStatement;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBcontroller {

    private static String dburl = "jdbc:mysql://82.165.156.52:3306/projektverwaltung";
    private static String userName = "disev001";
    private static String passWord = "7Qfk6T23";
    private Connection connection = null;
    Statement st = null;

    /**
     * Verbinde zu DB
     */
    public void connectDB() {

        try {
            connection = null;
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(dburl, userName, passWord);
            st = connection.createStatement();

            System.out.println("\nConnected to" + dburl);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Trenne von DB
     */
    public void disconnectDB() {
        if (connection != null) {
            try {
                connection.close();
                System.out.print("\nDisconnected");
            } catch (Exception e) {
            }
        }
    }

    /**
     * Lese Tabelle organisation
     *
     * @return Liste der ausgelesenen Tabelleneinträge für Organisationen
     */
    public ObservableList<Organisation> readOrgTable() {
        ObservableList<Organisation> orgList = FXCollections.observableArrayList();

        try {
            st = connection.createStatement();
            String sql = "SELECT id, name, plz,ort,strasse FROM organisation";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int plz = rs.getInt("plz");
                String ort = rs.getString("ort");
                String strasse = rs.getString("strasse");
                orgList.add(id - 1, new Organisation(name, plz, ort, strasse));
            }
            return orgList;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    /**
     * Lese Tabelle ansprechpartner
     *
     * @return Liste der ausgelesenen Tabelleneinträge für ansprechpartner
     */
    public ObservableList<Ansprechpartner> readAnspTable(ObservableList<Organisation> orgList) {
        ObservableList<Ansprechpartner> anspList = FXCollections.observableArrayList();

        try {
            st = connection.createStatement();
            String sql = "SELECT vorname,nachname, email,telefon,unternehmen FROM ansprechpartner";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //int id = rs.getInt("id");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");
                String email = rs.getString("email");
                String telefon = rs.getString("telefon");
                int unternehmen = rs.getInt("unternehmen");

                anspList.add(new Ansprechpartner(nachname, vorname, email, telefon, orgList.get(unternehmen)));
            }
            return anspList;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    /**
     * Fülle Tabelle organisation mit Listeninhalt
     *
     * @param organisationList Liste mit aktuellen Organisationen
     * @return erfolgs bool
     */
    public boolean fillOrgTable(ObservableList<Organisation> organisationList) {
        try {
            for (Organisation o : organisationList) {
                st = connection.createStatement();
                int index = organisationList.indexOf(o) + 1;
                String val = "'" + o.getName() + "','" + o.getOrt() + "','" + o.getStrasse() + "'," + o.getPlz() + "," + index;
                String sql = "INSERT INTO organisation(name,ort,strasse,plz,id)VALUES (" + val + ");";
                st.executeUpdate(sql);

            }
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }

    /**
     * Fülle Tabelle Ansprechpartner mit Listeninhalt
     *
     * @param organisationList Liste mit aktuellen Ansprechpartner
     * @return erfolgs bool
     */
    public boolean fillAnspTable(ObservableList<Ansprechpartner> ansprechpartnerList, ObservableList<Organisation> organisationList) {
        try {

            for (Ansprechpartner a : ansprechpartnerList) {
                st = connection.createStatement();
                int index = ansprechpartnerList.indexOf(a) + 1;
                int uIndex = organisationList.indexOf(a.getUnternehmen());
                String val = index + ",'" + a.getVorname() + "','" + a.getName() + "','" + a.getEmail() + "','"
                        + a.getTelefon() + "'," + uIndex;
                String sql = "INSERT INTO ansprechpartner(id,vorname,nachname,email,telefon,unternehmen)VALUES (" + val + ");";
                st.executeUpdate(sql);
            }
            return true;
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
    }


    public ObservableList<Student> readStudentTable() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        try {
            st = connection.createStatement();
            String sql = "SELECT matrikelnummer, nachname, vorname,email,password FROM student";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int matrikelnummer = rs.getInt("matrikelnummer");
                String nachname = rs.getString("nachname");
                String vorname = rs.getString("vorname");
                String email = rs.getString("email");
                studentList.add(new Student(nachname, vorname, email, matrikelnummer));
            }
            return studentList;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    public Student loginUser(String u, String p) throws IllegalAccessException {
        Student user = null;
        try {
            st = connection.createStatement();
            String sql = "SELECT matrikelnummer, nachname, vorname,email,password from student where email = ? and password = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, u);
            pst.setString(2, p);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int matrikelnummer = rs.getInt("matrikelnummer");
                String nachname = rs.getString("nachname");
                String vorname = rs.getString("vorname");
                String email = rs.getString("email");
                user = new Student(nachname, vorname, email, matrikelnummer);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return user;
    }

    public ObservableList<Projekt> readProjects(Student user, ObservableList<Student> studentenListe, ObservableList<Student> dozentenListe, ObservableList<Ansprechpartner> ansprechpartnerListe) throws IllegalAccessException {
        ObservableList<Projekt> projekts = FXCollections.observableArrayList();
        try {
            int mknr = user.getMatrikelnummer();
            st = connection.createStatement();
            String sql = "SELECT * from projekt where student1 = ? OR student2= ? OR student3 = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setInt(1, mknr);
            pst.setInt(2, mknr);
            pst.setInt(3, mknr);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String projekttitel = rs.getString("projekttitel");
                int student1 = rs.getInt("student1");
                int student2 = rs.getInt("student2");
                int student3 = rs.getInt("student3");
                String ansprechpartner = rs.getString("ansprechpartner");
                String dozent = rs.getString("dozent");
                int entscheidung = rs.getInt("entscheidung");
                String kommentar = rs.getString("kommentar");
                Student s1 = null;
                Student s2 = null;
                Student s3 = null;
                Student d = null;
                Ansprechpartner ap = null;
                for (Student s : studentenListe) {
                    if (s.getMatrikelnummer() == student1 && s1 == null ) {
                        s1 = s;
                    }
                    if (s.getMatrikelnummer() == student2 && s2 == null) {
                        s2 = s;
                    }
                    if (s.getMatrikelnummer() == student3 && s3 == null) {
                        s3 = s;
                    }
                }
                for (Student dz : dozentenListe) {
                    System.out.print(dz.getEmail());
                    System.out.print(dozent);
                    if (dz.getEmail().equals(dozent)) {
                        d = dz;
                        break;
                    }

                }
                for (Ansprechpartner a : ansprechpartnerListe) {
                    if (a.getEmail().equals(ansprechpartner)) {
                        ap = a;
                        break;
                    }

                }

                projekts.add(new Projekt(projekttitel, s1, s2, s3, ap, d,entscheidung,kommentar));
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return projekts;
    }

    /**
     * Lösche von Tabellen vor dem Speichern
     */
    public void trunkTable() {
        try {
            connection = null;
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(dburl, userName, passWord);
            st = connection.createStatement();


            String sql = "TRUNCATE  TABLE ansprechpartner";

            st.executeUpdate(sql);
            sql = "TRUNCATE  TABLE organisation";
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}