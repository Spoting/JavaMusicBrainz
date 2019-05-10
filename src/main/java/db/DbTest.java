/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package db;

import basics.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * public abstract MyBaseDAO{
 *
 * //Connection settings, common behavior.
 *
 * }
 *
 * public interface CustomerPersistence{ //Defines the API Customer
 * saveCustomer(Customer customer);
 *
 * }
 *
 * public class CustomerPersistenceImpl extends MyBaseDAO implements
 * CustomerPersistence{
 *
 * //Implements the API public Customer saveCustomer(Customer customer){
 *
 * //Use some methods from MyBaseDao this.saveObject(customer)
 *
 * }
 *
 * }
 */
public class DbTest implements DBInterface {

    public static void main(String args[]) {

    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
//load database driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger("Driver not found");
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "it21502",
                    "it21502");
           
        } catch (SQLException ex) {
            Logger.getLogger("Server not found");
            ex.printStackTrace();
        }
        System.out.println("ok");
        return connection;
    }

    @Override
    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Error");
        }
    }

    @Override
    public void insertTagsAliases(Artist art) {
        // arid = art.getId()
        // aliases = art.getAliases()
        // tags    = art.getTags()
        /**for (aliases.size())
         *          Insert into Aliases(arid, aliases.get(i))
         * for (tags.size())
         *          Insert into Tags(arid, tags.get(i))
         */
        
    }

    @Override
    public boolean insertArtist(Artist art) {
        /** if ((art.getClass == Person))
         *      Insert into Artist(art)
         *      insertTagsAliases()
         *  elseif (art.getClass == Group)
         *      gid = art.getId()
         *      Insert into Artist(art)
         *      insertTagsAliases()
         *      for (art.getMembers().size())
         *          Insert into Artists (art.getMembers().get(i), gid)
         */
        return true;
    }

    @Override
    public boolean insertRelease(Release rel) {
        //  albid = rel.getId()
        /** if (rel.getClass == Album)
         *      arid = rel.getArtist
         *      Insert into Album(rel)
         *      insertArtist(rel.getArtist())      
         *      Insert into Release(arid, albid)
         *  elseif (rel.getClass == Compilation)
         *      Insert into Album(rel)
         *      for (Artist art : rel.getArtists())
         *          arid = art.getId()
         *          insertArtist(art)
         *      Insert into Release(arid, albid)
         */
        return true;
    }

    @Override
    public int insertMassArtists(ArrayList<Artist> arrArt) {
       //for ( Artist art : arrArt )
       //      insertArtist(art)
       // count++
        return 0;//count
    }

    @Override
    public int insertMassReleases(ArrayList<Release> arrRel) {
       //for ( Release rel : arrRel)
       //      insertRelease(rel)
       // count++
        return 0;//count
    }

    @Override
    public ArrayList<Artist> queryArtist() {
        return null;
    }

    @Override
    public ArrayList<Release> queryRelease() {
        return null;
    }
}
/**
 * Connection connection = null; Statement stmt = null; try { //load database
 * driver Class.forName("oracle.jdbc.driver.OracleDriver"); } catch
 * (ClassNotFoundException ex) { Logger.getLogger("Driver not found");
 * ex.printStackTrace(); } try { connection = DriverManager.getConnection(
 * "jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "it21502", "it21502");
 * System.out.println("ok"); } catch (SQLException ex) {
 * Logger.getLogger("Server not found"); ex.printStackTrace(); }
 * System.out.println("ok"); try { stmt = connection.createStatement(); } catch
 * (SQLException ex) {
 * Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex); } try {
 * ResultSet rs = stmt.executeQuery("select arname,arid,artype from artist");
 * while (rs.next()) { System.out.println(rs.getString(1) + " " +
 * rs.getString(2) + ":" + rs.getString(3)); }
 *
 * } catch (SQLException ex) {
 * Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
     *
 */
