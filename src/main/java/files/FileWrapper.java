/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import basics.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class FileWrapper {
    ///////// Serializable Read/Write to file

    public static void writeFileArtist(String filename, ArrayList<Artist> tmpArr) throws IOException, ParseException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tmpArr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Artist> readArtistsFromFile(String filename) {
        ArrayList<Artist> tmpArt = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(filename + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tmpArt = (ArrayList<Artist>) in.readObject();
            in.close();
            fileIn.close();
            for (Artist x : tmpArt) {
                System.out.println("From FILE : " + x.toString());

            }
        } catch (Exception e) {

        }
        return tmpArt;
    }

    public static void writeFileRelease(String filename, ArrayList<Release> tmpRel) throws IOException, ParseException {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tmpRel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Release> readReleasesFromFile(String filename) {
           ArrayList<Release> tmpRel = new ArrayList();
        try {
            FileInputStream fileIn = new FileInputStream(filename + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tmpRel = (ArrayList<Release>) in.readObject();
            in.close();
            fileIn.close();
            for (Release r : tmpRel) {
                System.out.println("From FILE : " + r.toString());

            }
        } catch (Exception e) {

        }
        return tmpRel;
    }
}
