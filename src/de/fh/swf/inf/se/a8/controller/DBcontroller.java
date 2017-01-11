package de.fh.swf.inf.se.a8.controller;

import com.mysql.jdbc.PreparedStatement;
import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;

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
            String sql = "SELECT name, plz,ort,strasse FROM organisation";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int plz = rs.getInt("plz");
                String ort = rs.getString("ort");
                String strasse = rs.getString("strasse");
                orgList.add(new Organisation(name, plz, ort, strasse));
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
                Organisation org = null;
                //int id = rs.getInt("id");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");
                String email = rs.getString("email");
                String telefon = rs.getString("telefon");
                String unternehmen = rs.getString("unternehmen");
                for (Organisation o : orgList) {
                    if (o.getName().equals(unternehmen)) {
                        org = o;
                        break;
                    }
                }
                anspList.add(new Ansprechpartner(nachname, vorname, email, telefon, org));
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
                String val = "'" + o.getName() + "','" + o.getOrt() + "','" + o.getStrasse() + "'," + o.getPlz() + ",";
                String sql = "INSERT INTO organisation(name,ort,strasse,plz)VALUES (" + val + ");";
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
                String val = "'" + a.getVorname() + "','" + a.getName() + "','" + a.getEmail() + "','"
                        + a.getTelefon() + "'," + a.getUnternehmen().getName();
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

    public String getSkizzeName(Projekt p) throws IOException, SQLException {
        String name = "";
        try {
            String sql = "SELECT skizzename FROM projekt WHERE projekt.projekttitel = ? AND projekt.student1= ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, p.getProjekttitel());
            pst.setInt(2, p.getProjetteilnehmer1());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
                if (rs.wasNull())
                    name= "";
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return name;
    }

    public File getSkizze(Projekt p, File f) throws IOException, SQLException {

        try {
            String sql = "SELECT projektskizze FROM projekt WHERE projekt.projekttitel = ? AND projekt.student1= ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, p.getProjekttitel());
            pst.setInt(2, p.getProjetteilnehmer1());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream(1);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return f;
    }

    public String getBeschreibungName(Projekt p) throws IOException, SQLException {
        String name = "";
        try {
            String sql = "SELECT beschreibungname FROM projekt WHERE projekt.projekttitel = ? AND projekt.student1= ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, p.getProjekttitel());
            pst.setInt(2, p.getProjetteilnehmer1());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
                if (rs.wasNull())
                    name= "";
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return name;
    }

    public File getBeschreibung(Projekt p, File f) throws IOException, SQLException {

        try {
            String sql = "SELECT projektbeschreibung FROM projekt WHERE projekt.projekttitel = ? AND projekt.student1= ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, p.getProjekttitel());
            pst.setInt(2, p.getProjetteilnehmer1());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream(1);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
                pst.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return f;
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

    public ObservableList<Projekt> readStudentProjects(Student user, ObservableList<Student> studentenListe, ObservableList<Student> dozentenListe, ObservableList<Ansprechpartner> ansprechpartnerListe) throws IllegalAccessException {
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
                    if (s.getMatrikelnummer() == student1 && s1 == null) {
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

                projekts.add(new Projekt(projekttitel, s1, s2, s3, ap, d, entscheidung, kommentar));
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return projekts;
    }

    public ObservableList<Projekt> readDozentProjects(Student user, ObservableList<Student> studentenListe, ObservableList<Student> dozentenListe, ObservableList<Ansprechpartner> ansprechpartnerListe) throws IllegalAccessException {
        ObservableList<Projekt> projekts = FXCollections.observableArrayList();
        try {
            String mail = user.getEmail();
            st = connection.createStatement();
            String sql = "SELECT * from projekt where dozent = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, mail);
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
                    if (s.getMatrikelnummer() == student1 && s1 == null) {
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

                projekts.add(new Projekt(projekttitel, s1, s2, s3, ap, d, entscheidung, kommentar));
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return projekts;
    }

    /**
     * Einfügen von neu angelegten projekten in die DB
     *
     * @param p neu angelegtes projekt
     * @param u user welcher das projekt anlegt
     * @return erfolgstatus
     */
    public boolean insertNewProjekt(Projekt p, Student u) {
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "INSERT INTO projekt (projekttitel,student1,ansprechpartner, dozent,entscheidung,kommentar) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);

            pst.setString(1, p.getProjekttitel());
            pst.setInt(2, u.getMatrikelnummer());
            pst.setString(3, p.getAnsprechpartner().getEmail());
            pst.setString(4, p.getDozent().getEmail());
            pst.setInt(5, 0);
            pst.setString(6, "");
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            System.out.print(e);
        }
        return status;
    }

    public boolean insertFile(Projekt p, String titel) {
        boolean status = false;
        try {

            st = connection.createStatement();
            String sql = "";
            FileInputStream fisSkizze = null;
            FileInputStream fisBeschreibung = null;

            if (p.getProjektskizze() != null && p.getProjektbeschreibung() != null) {
                fisSkizze = new FileInputStream(p.getProjektskizze());
                fisBeschreibung = new FileInputStream(p.getProjektbeschreibung());
                sql = "UPDATE projekt SET projektskizze = ? , skizzename = ?, projektbeschreibung = ? ,beschreibungname = ?, projekttitel = ? WHERE projekt.projekttitel = ? AND projekt.student1 = ?";
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
                pst.setBinaryStream(1, fisSkizze, (int) p.getProjektskizze().length());
                pst.setString(2, p.getProjektskizze().getName());
                pst.setBinaryStream(3, fisBeschreibung, (int) p.getProjektbeschreibung().length());
                pst.setString(4, p.getProjektbeschreibung().getName());
                pst.setString(5, p.getProjekttitel());
                pst.setString(6, titel);
                pst.setInt(7, p.getProjetteilnehmer1());
                pst.executeUpdate();
                status = true;
                pst.close();
                fisSkizze.close();
                fisBeschreibung.close();
            } else if (p.getProjektskizze() != null) {
                fisSkizze = new FileInputStream(p.getProjektskizze());
                sql = "UPDATE projekt SET projektskizze = ? , skizzename = ?, projekttitel = ? WHERE projekt.projekttitel = ?  AND projekt.student1 = ?";
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
                pst.setBinaryStream(1, fisSkizze, (int) p.getProjektskizze().length());
                pst.setString(2, p.getProjektskizze().getName());
                pst.setString(3, p.getProjekttitel());
                pst.setString(4, titel);
                pst.setInt(5, p.getProjetteilnehmer1());
                pst.executeUpdate();
                status = true;
                pst.close();
                fisSkizze.close();
            } else if (p.getProjektbeschreibung() != null) {
                fisBeschreibung = new FileInputStream(p.getProjektbeschreibung());
                sql = "UPDATE projekt SET projektbeschreibung = ? , beschreibungname = ?,projekttitel = ?  WHERE projekt.projekttitel = ? AND projekt.student1 = ?";
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
                pst.setBinaryStream(1, fisBeschreibung, (int) p.getProjektbeschreibung().length());
                pst.setString(2, p.getProjektbeschreibung().getName());
                pst.setString(3, p.getProjekttitel());
                pst.setString(4, titel);
                pst.setInt(5, p.getProjetteilnehmer1());
                pst.executeUpdate();
                status = true;
                fisBeschreibung.close();
            }

        } catch (Exception e) {
            status = false;
            System.out.print(e);
        } finally {
        }
        return status;
    }

    public boolean insertNewOrg(Organisation o) {

        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "INSERT INTO organisation(name,ort,strasse,plz)VALUES (?,?,?,?);";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);

            pst.setString(1, o.getName());
            pst.setString(2, o.getOrt());
            pst.setString(3, o.getStrasse());
            pst.setInt(4, o.getPlz());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteProject(Projekt p){
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql =  "DELETE FROM projekt WHERE projekt.projekttitel = ? AND projekt.student1 = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, p.getProjekttitel());
            pst.setInt(2,p.getProjetteilnehmer1());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
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

    public boolean inserNewAnsprechpartner(Ansprechpartner a) {

        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "INSERT INTO ansprechpartner (vorname,nachname,email,telefon,unternehmen)VALUES (?,?,?,?,?);";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);

            pst.setString(1, a.getVorname());
            pst.setString(2, a.getName());
            pst.setString(3, a.getEmail());
            pst.setString(4, a.getTelefon());
            pst.setString(5, a.getUnternehmen().getName());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteAnsprechpartner(Ansprechpartner a) {
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "DELETE FROM ansprechpartner WHERE ansprechpartner.email = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, a.getEmail());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteOrg(Organisation o) {
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "DELETE FROM organisation WHERE organisation.name = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, o.getName());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean updateAnsprechpartner(Ansprechpartner newAnsprechpartner, Ansprechpartner oldAnsprechpartner) {
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "UPDATE ansprechpartner SET vorname= ?,nachname=?,email=?,telefon=?,unternehmen=? WHERE ansprechpartner.email =?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, newAnsprechpartner.getVorname());
            pst.setString(2, newAnsprechpartner.getName());
            pst.setString(3, newAnsprechpartner.getEmail());
            pst.setString(4, newAnsprechpartner.getTelefon());
            pst.setString(5, newAnsprechpartner.getUnternehmen().getName());
            pst.setString(6, oldAnsprechpartner.getEmail());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean updateOrganisation(Organisation orgNew, Organisation orgOld) {
        boolean status = false;
        try {
            st = connection.createStatement();
            String sql = "UPDATE organisation SET name=?, ort = ?, strasse = ?, plz = ? WHERE organisation.name = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setString(1, orgNew.getName());
            pst.setString(2, orgNew.getOrt());
            pst.setString(3, orgNew.getStrasse());
            pst.setInt(4, orgNew.getPlz());
            pst.setString(5, orgOld.getName());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public boolean updateProject(Projekt selected, Projekt oldProject) {
        boolean status = false;
        try {
            st = connection.createStatement();

            String sql = "UPDATE projekt SET student1=?,student2=?,student3=?,entscheidung=?, kommentar = ?,  ansprechpartner= ? WHERE projekt.projekttitel=? AND projekt.student1 = ?";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            int student1 = 0;
            int student2 = 0;
            int student3 = 0;

            for (Student s : selected.getProjektgruppe()) {
                if (student1 == 0)
                    student1 = s.getMatrikelnummer();
                else if (student2 == 0)
                    student2 = s.getMatrikelnummer();
                else if (student3 == 0)
                    student2 = s.getMatrikelnummer();
            }
            pst.setInt(1, student1);
            if (student2 != 0)
                pst.setInt(2, student2);
            else pst.setNull(2, Types.INTEGER);
            if (student3 != 0)
                pst.setInt(3, student3);
            else pst.setNull(3, Types.INTEGER);

            pst.setInt(4, selected.getEntscheidung());
            pst.setString(5, selected.getKommentar());
            pst.setString(6, selected.getAnsprechpartner().getEmail());
            pst.setString(7, oldProject.getProjekttitel());
            pst.setInt(8, oldProject.getProjetteilnehmer1());
            pst.executeUpdate();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}