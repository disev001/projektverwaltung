package de.fh.swf.inf.se.a8;

import de.fh.swf.inf.se.a8.model.Ansprechpartner;
import de.fh.swf.inf.se.a8.model.Organisation;
import de.fh.swf.inf.se.a8.model.Projekt;
import de.fh.swf.inf.se.a8.model.Student;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.Observable;

/**
 * Created by dsee on 09.12.2016.
 */
public class testrun {
    public ObservableList<Student> studentenListe ;
    public  ObservableList<Organisation> organisationsListe;
    public ObservableList<Ansprechpartner> ansprechpartnerListe;
    public  ObservableList<Projekt> projektListe;
    public testrun(){
    // write your code here
 /*   Student student1 = new Student("Severgin", "Dieter", "severgin.dieter@fh-swf.de", 10040845);
    Student student2 = new Student("Mustermann", "Max", "musterman.max@fh-swf.de", 10042334);
    Student student3 = new Student("Doe", "John", "doe.john@anon.de", 2933923);
    Student student4 = new Student("Mono", "Willi", "krassertypXXX@t-online.de", 32423423);

    Organisation org1 = new Organisation("Musterorg", 58762, "Altena", "Amselweg 6a");
    Organisation org2 = new Organisation("FHSWF", 55442, "Iserlohn", "Frauenstuhlweg 31");

    Ansprechpartner an1 = new Ansprechpartner("Katze", "Wasilisa", "dsee@doldrums.de", "02352/546521", org1);
    Ansprechpartner an2 = new Ansprechpartner("Klug", "Uwe", "klug.uwe@fh-swf.de", "02242/8087652", org1);
    File dummy = new File("C:/");
    Projekt p1 =new Projekt("Projektverwaltung", dummy, dummy, student1 ,null,null , an1);
    p1.addStudent(student2);

    studentenListe.addAll(student1,student2,student3,student4);
    organisationsListe.addAll(org1,org2);
    ansprechpartnerListe.addAll(an1,an2);
    projektListe.addAll(p1);*/
/*
    //test1
        System.out.print(p1.toString());
        p1.addStudent(student2);
    //test2
        System.out.print(p1.toString());

    //test3
        p1.addStudent(student3);
        System.out.print(p1.toString());
    //test4
        p1.addStudent(student4);
        System.out.print(p1.toString());



//test5
        try {

        Projekt p2 = new Projekt("ProjektverwaltungXYZ", "Projektbeschreibung mit Skizze usw...","20-03-2017", "10-04-2017" , null, an1);
        System.out.print(p2.toString());
    }
        catch (Exception e){
        System.out.print(e);
    }*/
    }
}
