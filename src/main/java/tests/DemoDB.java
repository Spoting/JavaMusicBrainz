/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import basics.Artist;
import basics.Release;

import db.Database;
import files.APIWrapper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

public class DemoDB {
    
    public static void main(String args[]) throws SQLException, ParseException, IOException, NullPointerException {
        Database db = new Database();
        
        ArrayList<Release> tmpRel;
        ArrayList<Artist> tmpArt;
        
        //Open DBConnection
        db.getConn();
        
/////////////Get Artist From API
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Get Artist From Tags");
        System.out.println("//////////////////////////////////////////////////////");

        tmpArt = APIWrapper.getArtistsFromTags("fred", "");
        ////Insert Artist to DB
        
        System.out.println("Number of Successfull Inserts : "
                + db.insertMassArtists(tmpArt));

 
/////////////Get Releases From API
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Get Releases From Status");
        System.out.println("//////////////////////////////////////////////////////");

        tmpRel = APIWrapper.getReleasesByStatus("We will rock you", "official");
        ////Insert Release to DB
        
        System.out.println("Count of Releases "
                + db.insertMassReleases(tmpRel));
       
//////////QueryArtist
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Queries For Artist");
        System.out.println("//////////////////////////////////////////////////////");
       

        System.out.println("/////Query By Country/////");
        tmpArt = db.queryArtistByCountry("US");
        for (Artist art : tmpArt) {
            System.out.println(art.toString());
        }

        System.out.println("/////Query By Tag/////");
        tmpArt = db.queryArtistByTag("american");
        for (Artist art : tmpArt) {
            System.out.println(art.toString());
        }

        System.out.println("/////Query By Alias/////");
        tmpArt = db.queryArtistByAlias("Fred");
        for (Artist art : tmpArt) {
            System.out.println(art.toString());
        }

        
//////////QueryRelease
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Queries For Release");
        System.out.println("//////////////////////////////////////////////////////");
        

        System.out.println("/////Query By Status/////");
        tmpRel = db.queryReleaseByStatus("Official");
        System.out.println();
        for (Release rel : tmpRel) {
            System.out.println(rel.toString());
        }

        System.out.println("/////Query By Format/////");
        tmpRel = db.queryReleaseByFormat("CD");
        System.out.println();
        for (Release rel : tmpRel) {
            System.out.println(rel.toString());
        }
        //Close DBConnection
       db.closeConn();
    }

}
