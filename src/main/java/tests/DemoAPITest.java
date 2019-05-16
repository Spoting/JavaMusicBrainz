/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.ArrayList;

import basics.*;
import files.*;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class DemoAPITest {

    public static void main(String[] args) throws ParseException, IOException, InterruptedException, ClassNotFoundException, java.text.ParseException {
        System.out.println("Start of PROGRAM");
        ArrayList<Artist> tmpArt;
        ArrayList<Artist> fileArt;
        ArrayList<Release> tmpRel;
        ArrayList<Release> fileRel;

        try {
            //Artist////////////////////////////////////////////////////////////////////////
            System.out.println("//////////////////////////////////////////////////////");
            System.out.println("Get Artists From Tags");
            System.out.println("//////////////////////////////////////////////////////");
            tmpArt = APIWrapper.getArtistsFromTags("fred", "");
            for (Artist x : tmpArt) {
                System.out.println(x.toString());
            }

            System.out.println("WRITING Artists TO FILE");
            FileWrapper.writeFileArtist("artistsTest", tmpArt);
            System.out.println("READING Artists FROM FILE");
            fileArt = FileWrapper.readArtistsFromFile("artistsTest");
            for (Artist a : fileArt) {
                a.toString();
            }

//            
//            System.out.println("//////////////////////////////////////////////////////");
//            System.out.println("Get Artists From Country");
//            System.out.println("//////////////////////////////////////////////////////");
//            tmpArt = APIWrapper.getArtistsFromCountry("nirvana", "us");
//            for (Artist x : tmpArt) {
//                System.out.println(x.toString());
//            }
            //Release////////////////////////////////////////////////////////////////////////
//            System.out.println("//////////////////////////////////////////////////////");
//            System.out.println("Get Releases From Status");
//            System.out.println("//////////////////////////////////////////////////////");
//
//            tmpRel = APIWrapper.getReleasesByStatus("Slim", "official");
//            for (Release x : tmpRel) {
//                System.out.println(x.toString());
//            }
//            //Γέμισμα όλων τον στοιχείων Artist ενός Album
//            Album y = (Album) tmpRel.get(1);
//            System.out.println("Before Fill :\n " + y.toString());
//            y.setArt(APIWrapper.fillArtistAlbum(y.getArt()));
//            System.out.println("After Fill :\n " + y.toString());
//            
            Thread.sleep(1000);
            System.out.println("//////////////////////////////////////////////////////");
            System.out.println("Get Releases From Type");
            System.out.println("//////////////////////////////////////////////////////");

            tmpRel = APIWrapper.getReleasesByType("We will rock you", "");
            for (Release x : tmpRel) {
                System.out.println(x.toString());

            }
            System.out.println("WRITING Releases TO FILE");
            FileWrapper.writeFileRelease("releasesFile", tmpRel);
            System.out.println("READING Releases FROM FILE");
            fileRel = FileWrapper.readReleasesFromFile("releasesFile");
            for (Release r : fileRel) {
                r.toString();
            }
            
            

//
//            //Γέμισμα όλων τον στοιχείων Artist ενός Album
//            Album y = (Album) tmpRel.get(1);
//            System.out.println("Before Fill :\n " + y.toString());
//            y.setArt(APIWrapper.fillArtistAlbum(y.getArt()));
//            System.out.println("After Fill :\n " + y.toString());
            //Thread.sleep(1000);
//            //Γέμισμα όλων τον στοιχείων Artists ενός Compilation
//            Compilation c = (Compilation) tmpRel.get(3);
//            System.out.println("Before Fill :\n " + c.toString());
//            ArrayList<Artist> n = new ArrayList();
//            for (Artist a : c.getArtists()) {
//                //y.setArt(APIWrapper.fillArtistAlbum(y.getArt()));
//                a = APIWrapper.fillArtistAlbum(a);
//                n.add(a);
//
//            }
//            c.setArtists(n);
//            System.out.println("After Fill :\n " + c.toString());
//            /////File////////////
//            FileWrapper.writeReleasesToFile("testRel1", tmpRel);
//            FileWrapper.writeArtistsToFile("testArtist1", tmpArt);
//        
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("");
        System.out.println("//////////////////////////////////////////////////////");

        System.out.println("");
        System.out.println("End of PROGRAM");
    }
}
