/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package db;

import basics.*;
import files.APIWrapper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

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
//CONNECTION VARIABLE Connection conn , should open/close connection in function or on main???
public class DbTest implements DBInterface {

    //private String gid = null; // groupId
    //static String insert = "INSERT INTO ";
    private static Connection conn;

    public static void main(String args[]) throws SQLException, ParseException, IOException {
        DbTest db = new DbTest();
        ArrayList<Release> tmpRel;
        ArrayList<Artist> tmpArt;
///////////Get Artist From API
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Get Artist From Tags");
        System.out.println("//////////////////////////////////////////////////////");

        tmpArt = APIWrapper.getArtistsFromTags("fred", "");
        //Insert Artist to DB
        conn = getConn();
        System.out.println("Number of Successfull Inserts : "
                + db.insertMassArtists(tmpArt));

        closeConn(conn);
 /////////Get Releases From API
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Get Releases From Status");
        System.out.println("//////////////////////////////////////////////////////");

        tmpRel = APIWrapper.getReleasesByStatus("We will rock you", "official");
        ////Insert Release to DB
        conn = getConn();
        System.out.println("Count of Releases "
                + db.insertMassReleases(tmpRel));
        closeConn(conn);

    }

    private static Connection getConn() {
        Connection connection = null;
        // con = getConn();
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
            connection.setAutoCommit(false);// Auto-commit off
            //Statement st = connection.createStatement();
            //st.execute("ALTER SESSION SET NLS_DATE_FORMAT='DD-MM-YYYY'");
        } catch (SQLException ex) {
            Logger.getLogger("Server not found");
            ex.printStackTrace();
        }
        System.out.println("Connection Open");
        return connection;
    }

    private static void closeConn(Connection con) {
        try {
            con.commit();
            con.close();
            System.out.println("Connection Closed");
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Error");

        }
    }

    @Override
    public void insertTagsAliases(Artist art) {

        String arid = art.getId();

        ArrayList<String> aliases = art.getAliases();
        ArrayList<String> tags = art.getTags();

        //Connection conn = getConn();
        String tableName = "";
        String sql = "";
        PreparedStatement pstmt = null;

        try {

            tableName = "art_alias";
            sql = "insert into " + tableName + " values (?,?)";
            pstmt = conn.prepareStatement(sql);
            for (String x : aliases) {
                //String query1 = "INSERT INTO art_alias VALUES ('" + arid + "', '" + x + "')";
                try {
                    pstmt.setString(1, arid);
                    pstmt.setString(2, x);

                    pstmt.executeUpdate();
                } catch (SQLException ex) {
                    // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            tableName = "art_tag";
            sql = "insert into " + tableName + " values (?,?)";
            pstmt = conn.prepareStatement(sql);
            for (String x : tags) {
                //String query2 = "INSERT INTO art_tag VALUES ('" + arid + "', '" + x + "')";
                try {
                    pstmt.setString(1, arid);
                    pstmt.setString(2, x);

                    pstmt.executeUpdate();

                } catch (SQLException ex) {
                    //Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            pstmt.close();

        } catch (Exception ex) {
            // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error Inserting Aliases&Tags");

        } finally {
            //closeConn(conn);
        }
        //System.out.println("Aliases&Tags In DB for Artist with ID: " + arid);
    }

    @Override
    public boolean insertArtist(Artist art) {
        //Connection conn = getConn();

        PreparedStatement pstmt;
        String tableName = "artist";

        if (art.getClass().toString().equals("class basics.Artist")) {
            try {
                art.setType("unknown");
                String sql = "insert into " + tableName + " (arid, arname, artype) values (?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, art.getId());
                pstmt.setString(2, art.getName());
                pstmt.setString(3, art.getType());

                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Artist " + art.getId());
                return false;
            } finally {
                //closeConn(conn);

            }
            System.out.println("Success: Artist " + art.getId());
            return true;
        }

        if (art.getClass().toString().equals("class basics.Person")) { //|| art.getType().equals("unknown")
            try {
                Person p = (Person) art;

                //System.out.println("sdqwqd," + p.getBirthDate());
                //tableName = "artist";
                String sql = "insert into " + tableName + " values (?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, p.getId());
                pstmt.setString(2, p.getName());
                pstmt.setString(3, p.getCountry());
                pstmt.setString(4, p.getCities());
                pstmt.setString(5, p.getBirthDate());
                pstmt.setString(6, p.getDeathDate());
                pstmt.setString(7, p.getType());
                pstmt.setString(8, p.getGender());

                pstmt.setString(9, null);
                //pstmt.setString(9, null);
                pstmt.executeUpdate();
                pstmt.close();

                insertTagsAliases(art);
            } catch (SQLException ex) {
                //  Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Artist " + art.getId());
                return false;
            } finally {
                //closeConn(conn);

            }

            System.out.println("Success: Person " + art.getId());
            return true;

        } else if (art.getClass().toString().equals("class basics.Group")) {

            try {

                Group g = (Group) art;

                String sql = "insert into " + tableName + " values (?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, g.getId());
                pstmt.setString(2, g.getName());
                pstmt.setString(3, g.getCountry());
                pstmt.setString(4, g.getCities());
                pstmt.setString(5, g.getBeginDate());
                pstmt.setString(6, g.getEndDate());
                pstmt.setString(7, g.getType());
                pstmt.setString(8, null);
                pstmt.setString(9, null);

                pstmt.executeUpdate();
                pstmt.close();
                //insertMassArtists(g.getMembers());
                String gid = g.getId();
                //sql = "insert into " + tableName + " (arid, arname, gid, artype) values (?,?,?.?)";
                sql = "insert into " + tableName + " values (?,?,?,?,?,?,?,?,?)";
                // System.out.println(sql);
                pstmt = conn.prepareStatement(sql);
                for (Person p : g.getMembers()) {
                    System.out.print("Adding Members of Group : " + gid);
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, p.getId());
                    pstmt.setString(2, p.getName());
                    pstmt.setString(3, p.getCountry());
                    pstmt.setString(4, p.getCities());
                    pstmt.setString(5, p.getBirthDate());
                    pstmt.setString(6, p.getDeathDate());
                    pstmt.setString(7, p.getType());
                    pstmt.setString(8, p.getGender());

                    pstmt.setString(9, gid);
                    pstmt.executeUpdate();
                    //insertTagsAliases(art);
                }
                pstmt.close();
                insertTagsAliases(art);
            } catch (SQLException ex) {
                // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Artist " + art.getId());

                return false;
            } finally {
                //closeConn(conn);    
            }
            System.out.println("Success: Group " + art.getId());
            return true;
            //} else if (){

        }
        return false;
    }

    @Override
    public boolean insertRelease(Release rel) {

        PreparedStatement pstmt = null;
        String tableName = "album";

        //String albid = rel.getId();
        if (rel.getClass().toString().equals("class basics.Album")) {
            try {
                Album a = (Album) rel;

                //System.out.println("sdqwqd," + p.getBirthDate());
                //tableName = "artist";
                String sql = "insert into " + tableName + " values (?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, a.getId());
                pstmt.setString(2, a.getType());
                pstmt.setString(3, a.getTitle());
                pstmt.setString(4, a.getStatus());
                pstmt.setString(5, a.getLanguage());
                pstmt.setString(6, a.getReleaseDate());
                pstmt.setString(7, a.getFormat());
                pstmt.setInt(8, a.getTrackCount());

                pstmt.executeUpdate();
                pstmt.close();
                //System.out.println("" + a.getArt().getClass().toString());
                insertArtist(a.getArt());

                tableName = "release";
                sql = "insert into " + tableName + " values (?,?)";
                pstmt = conn.prepareStatement(sql);
                //pstmt.setNull(1, 0);
                pstmt.setString(1, a.getArt().getId());
                pstmt.setString(2, a.getId());

                pstmt.executeUpdate();
                pstmt.close();

            } catch (SQLException ex) {
                //  Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Release " + rel.getId());
                return false;
            } finally {
                //closeConn(conn);

            }

            System.out.println("Success: Album " + rel.getId());
            return true;
        } else if (rel.getClass().toString().equals("class basics.Compilation")) {
            try {
                Compilation comp = (Compilation) rel;

                //System.out.println("sdqwqd," + p.getBirthDate());
                //tableName = "artist";
                String sql = "insert into " + tableName + " values (?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, comp.getId());
                pstmt.setString(2, comp.getType());
                pstmt.setString(3, comp.getTitle());
                pstmt.setString(4, comp.getStatus());
                pstmt.setString(5, comp.getLanguage());
                pstmt.setString(6, comp.getReleaseDate());
                pstmt.setString(7, comp.getFormat());
                pstmt.setInt(8, comp.getTrackCount());

                pstmt.executeUpdate();
                pstmt.close();
                //System.out.println("" + a.getArt().getClass().toString());
                int artCount = insertMassArtists(comp.getArtists());
                System.out.println("Artists Inserted : " + artCount);
                for (Artist art : comp.getArtists()) {
                    //insertArtist(art);
                    tableName = "release";
                    sql = "insert into " + tableName + " values (?,?)";
                    pstmt = conn.prepareStatement(sql);
                    //pstmt.setNull(1, 0);
                    pstmt.setString(1, art.getId());
                    pstmt.setString(2, comp.getId());

                    pstmt.executeUpdate();
                    pstmt.close();
                }
            } catch (SQLException ex) {
                // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Release " + rel.getId());
                return false;
            } finally {
                //closeConn(conn);

            }
            System.out.println("Success: Compilation " + rel.getId());
            return true;
        }

        return false;
    }

    @Override
    public int insertMassArtists(ArrayList<Artist> arrArt) {
        int count = 0;

        for (Artist art : arrArt) {
            if (insertArtist(art) == true) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int insertMassReleases(ArrayList<Release> arrRel) {
        int count = 0;

        for (Release rel : arrRel) {
            if (insertRelease(rel) == true) {
                count++;
            }
        }
        return count;
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
