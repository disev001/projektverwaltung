package de.fh.swf.inf.se.a8.controller;

import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
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

    public static void main(String[] args) {
        DBcontroller db = new DBcontroller();
        db.connectDB();
        // db.readOrgTable();
        db.disconnectDB();
    }

    /**********************************************************************************/
    public void testConnection() {
        try {
            connectDB();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection terminated");
                } catch (Exception e) { /* ignore close errors */ }
            }
        }
    }

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

    public void disconnectDB() {
        if (connection != null) {
            try {
                connection.close();
                System.out.print("\nDisconnected");
            } catch (Exception e) {
            }
        }
    }

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
                orgList.add(id-1, new Organisation(name, plz, ort, strasse));
            }
            return orgList;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

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

    public boolean fillOrgTable(ObservableList<Organisation> organisationList) {
        try {
            for (Organisation o : organisationList) {
                st = connection.createStatement();
                int index = organisationList.indexOf(o)+1;
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

    public boolean fillAnspTable(ObservableList<Ansprechpartner> ansprechpartnerList, ObservableList<Organisation> organisationList) {
        try {

            for (Ansprechpartner a : ansprechpartnerList) {
                st = connection.createStatement();
                int index = ansprechpartnerList.indexOf(a)+1;
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

    public void trunkTable() {
        try {
            connection = null;
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(dburl, userName, passWord);
            st = connection.createStatement();


            String sql = "TRUNCATE  TABLE ansprechpartner";

            st.executeUpdate(sql);
            System.out.println("\nAnsprechpartner leer");

            sql = "TRUNCATE  TABLE organisation";

            st.executeUpdate(sql);
            System.out.println("\nOrganisation leer");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}