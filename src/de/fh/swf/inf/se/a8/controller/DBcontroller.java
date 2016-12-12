package de.fh.swf.inf.se.a8.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBcontroller
{

    private static String dburl = "jdbc:mysql://82.165.156.52:3306/projektverwaltung";
    private static String userName = "disev001";
    private static String passWord = "7Qfk6T23";
    public static void main (String[] args) {


        Connection connection = null;
        try {
            connection = null;
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(dburl, userName, passWord);
            Statement st = connection.createStatement();

            System.out.println("Hi");
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
}