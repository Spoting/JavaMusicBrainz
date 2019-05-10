/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import basics.*;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author spoting
 */
////Could Use Generic Type
public interface DBInterface {
    Connection getConnection();
    void closeConnection(Connection con);
    
    void insertTagsAliases(Artist art);
    //Could Use Generic Types
    boolean insertArtist(Artist art);
    boolean insertRelease(Release rel);
    
    int insertMassArtists(ArrayList<Artist> arrArt);
    int insertMassReleases(ArrayList<Release> arrRel);
    
    ArrayList<Artist> queryArtist();
    ArrayList<Release> queryRelease();
}
