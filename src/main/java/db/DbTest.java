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

    public static void main(String args[]) throws SQLException, ParseException, IOException, NullPointerException {
        DBInterface db = new DbTest();
        ArrayList<Release> tmpRel;
        ArrayList<Artist> tmpArt;
/////////////Get Artist From API
//        System.out.println("//////////////////////////////////////////////////////");
//        System.out.println("Get Artist From Tags");
//        System.out.println("//////////////////////////////////////////////////////");
//
//        tmpArt = APIWrapper.getArtistsFromTags("fred", "");
//        ////Insert Artist to DB
//        conn = getConn();
//        System.out.println("Number of Successfull Inserts : "
//                + db.insertMassArtists(tmpArt));
//
//        closeConn(conn);
/////////////Get Releases From API
//        System.out.println("//////////////////////////////////////////////////////");
//        System.out.println("Get Releases From Status");
//        System.out.println("//////////////////////////////////////////////////////");
//
//        tmpRel = APIWrapper.getReleasesByStatus("We will rock you", "official");
//        ////Insert Release to DB
//        conn = getConn();
//        System.out.println("Count of Releases "
//                + db.insertMassReleases(tmpRel));
//        closeConn(conn);
//////////QueryArtist
//        System.out.println("//////////////////////////////////////////////////////");
//        System.out.println("Queries For Artist");
//        System.out.println("//////////////////////////////////////////////////////");
//        conn = getConn();
//
//        System.out.println("/////Query By Country/////");
//        tmpArt = db.queryArtistByCountry("US");
//        for (Artist art : tmpArt) {
//            System.out.println(art.toString());
//        }
//
//        System.out.println("/////Query By Tag/////");
//        tmpArt = db.queryArtistByTag("american");
//        for (Artist art : tmpArt) {
//            System.out.println(art.toString());
//        }
//
//        System.out.println("/////Query By Alias/////");
//        tmpArt = db.queryArtistByAlias("Fred");
//        for (Artist art : tmpArt) {
//            System.out.println(art.toString());
//        }
//
//        closeConn(conn);
//////////QueryRelease
        System.out.println("//////////////////////////////////////////////////////");
        System.out.println("Queries For Release");
        System.out.println("//////////////////////////////////////////////////////");
        conn = getConn();

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
        closeConn(conn);

    }

//Query Artist with Country argument
    @Override
    public ArrayList<Artist> queryArtistByCountry(String country) {
        ArrayList<Artist> artArr = new ArrayList();

        String tableName = "artist";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM " + tableName + "  WHERE country=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, country);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artArr.add(makeArtist(rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artArr;
    }
//Query Artist with Tag argument

    @Override
    public ArrayList<Artist> queryArtistByTag(String tag) {

        ArrayList<Artist> artArr = new ArrayList();
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM artist a "
                + "JOIN art_tag t "
                + "ON t.arid = a.arid "
                + "WHERE t.artag LIKE ?";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "%" + tag + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artArr.add(makeArtist(rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artArr;
    }
//Query Artist with Alias argument

    @Override
    public ArrayList<Artist> queryArtistByAlias(String alias) {

        ArrayList<Artist> artArr = new ArrayList();
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM artist a "
                + "JOIN art_alias t "
                + "ON t.arid = a.arid "
                + "WHERE t.aralias LIKE ?";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "%" + alias + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artArr.add(makeArtist(rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artArr;
    }

//get Tags of Artist
    private ArrayList<String> queryTags(String arid) {

        ArrayList<String> tagArr = new ArrayList();
        String tableName = "art_tag";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM " + tableName + "  WHERE arid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, arid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tagArr.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tagArr;
    }
//get Aliases of Artist    

    private ArrayList<String> queryAliases(String arid) {
        ArrayList<String> aliasArr = new ArrayList();

        String tableName = "art_alias";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM " + tableName + "  WHERE arid=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, arid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                aliasArr.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aliasArr;
    }

//get Query Members of Group
    private ArrayList<Person> queryGroupMembers(String arid) {
        ArrayList<Person> membArr = new ArrayList();

        String tableName = "artist";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM " + tableName
                + " WHERE gid=? ";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, arid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                membArr.add((Person) makeArtist(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return membArr;

    }

    @Override
    public ArrayList<Release> queryReleaseByStatus(String arg) {
        ArrayList<Release> relArr = new ArrayList();
        //String tableName = "album";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM album WHERE status=? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, arg);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relArr.add(makeRelease(rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relArr;
    }

    @Override
    public ArrayList<Release> queryReleaseByFormat(String arg) {
        ArrayList<Release> relArr = new ArrayList();
        //String tableName = "album";
        PreparedStatement pstmt = null;

        String sql = "SELECT * FROM album WHERE format=? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, arg);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relArr.add(makeRelease(rs));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relArr;
    }

    private ArrayList<Artist> findArtistsOfRelease(String albid) {
        ArrayList<Artist> artArr = new ArrayList();
        PreparedStatement pstmt = null;

        String sql = "SELECT a.arid, a.arname, a.artype FROM release r "
                + "JOIN artist a ON a.arid = r.arid "
                + "JOIN album b ON b.albid = r.albid "
                + "WHERE b.albid = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, albid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artArr.add(makeArtist(rs));
            }
            //relArr.add(makeRelease(rs));

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return artArr;
    }

    //Build Release From ResultSet of JDBC
    private Release makeRelease(ResultSet rs) throws SQLException {
        if (rs.getString("reltype").equals("Album")) {
            Album a = new Album();

            a.setId(rs.getString(1));
            a.setType(rs.getString(2));
            a.setTitle(rs.getString(3));
            a.setStatus(rs.getString(4));
            a.setLanguage(rs.getString(5));
            a.setReleaseDate(rs.getString(6));
            a.setFormat(rs.getString(7));
            a.setTrackCount(rs.getInt(8));

            ArrayList artsOfRelease = findArtistsOfRelease(a.getId());
            if (artsOfRelease.size() == 1) {
                a.setArt((Artist) artsOfRelease.get(0));
            } else {
                System.out.println("Error in ControlFlow");
            }

            return a;
        } else if (rs.getString("reltype").equals("Compilation")) {
            Compilation c = new Compilation();

            c.setId(rs.getString(1));
            c.setType(rs.getString(2));
            c.setTitle(rs.getString(3));
            c.setStatus(rs.getString(4));
            c.setLanguage(rs.getString(5));
            c.setReleaseDate(rs.getString(6));
            c.setFormat(rs.getString(7));
            c.setTrackCount(rs.getInt(8));

            ArrayList artsOfRelease = findArtistsOfRelease(c.getId());
            c.setArtists(artsOfRelease);
            return c;
        }
        return null;
    }

    //Build Artist From ResultSet of JDBC
    private Artist makeArtist(ResultSet rs) throws SQLException {
        //Artist art = null;

        if (rs.getString("artype").equals("Person") || rs.getString("artype").equals("member of band")) {
            Person p = new Person();

            p.setId(rs.getString(1));
            p.setName(rs.getString(2));
            p.setCountry(rs.getString(3));
            p.setCities(rs.getString(4));
            p.setBirthDate(rs.getString(5));
            p.setDeathDate(rs.getString(6));
            p.setType(rs.getString(7));
            p.setGender(rs.getString(8));

            p.setAliases(queryAliases(p.getId()));
            p.setTags(queryTags(p.getId()));

            return p;
            // artArr.add(p);
        } else if (rs.getString("artype").equals("Group")) {
            Group g = new Group();

            g.setId(rs.getString(1));
            g.setName(rs.getString(2));
            g.setCountry(rs.getString(3));
            g.setCities(rs.getString(4));
            g.setBeginDate(rs.getString(5));
            g.setEndDate(rs.getString(6));
            g.setType(rs.getString(7));
            //p.setGender(rs.getString(8));

            g.setAliases(queryAliases(g.getId()));
            g.setTags(queryTags(g.getId()));

            g.setMembers(queryGroupMembers(g.getId()));

            return g;
            //artArr.add(g);
        } else if (rs.getString("artype").equals("unknown")) {
            Artist a = new Artist();

            a.setId(rs.getString("arid"));
            a.setName(rs.getString("arname"));
            a.setType("artype");

            return a;
        }

        return null;
    }

////////////////Inserts//////////////////////////////////////////////////////////////////////////
    //Insert Many Artists
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
//Insert Many Releases

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

//InsertArtist
    @Override
    public boolean insertArtist(Artist art) {
        //Connection conn = getConn();

        PreparedStatement pstmt;
        String tableName = "artist";
        /*Αν ο Artist δεν εχει εξειδικευτει σε (Person, Group), δλδ στο Artist
            δεν εχουμε ανακτήσει πληροφοριες εκτος του id,name*/
        if (art.getClass().toString().equals("class basics.Artist")) {
            try {
                art.setType("unknown");
                /* Το βαζουμε unknown για να ξερουμε οτι 
                αυτο το Artist χρειαζεται γεμισμα απο το API, διοτι άν το γεμιζαμε 
                τωρα θα έχουμε HTTPerror 503 επειδη κανουμε πολλες κλήσεις στο server*/
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
                return false; // Αν υπαρχει Error, γυρνα false
            } finally {
                //closeConn(conn);

            }
            System.out.println("Success: Artist " + art.getId());
            return true; // Αν επιτυχει, γυρνα True
        }

        if (art.getClass().toString().equals("class basics.Person")) { //|| art.getType().equals("unknown")
            try {
                Person p = (Person) art;

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

//InsertRelease
    @Override
    public boolean insertRelease(Release rel) {

        PreparedStatement pstmt = null;
        String tableName = "album";

        //String albid = rel.getId();
        if (rel.getClass().toString().equals("class basics.Album")) {
            try {
                Album a = (Album) rel;

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

    //InsertTags+Aliases of Artist
    private void insertTagsAliases(Artist art) {

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

////////////////DATABASE CONNECTION//////////////////////////////////////////////////////////////////////////     
    //Open DB Connection
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

            //st.execute("ALTER SESSION SET NLS_DATE_FORMAT='DD-MM-YYYY'");
        } catch (SQLException ex) {
            Logger.getLogger("Server not found");
            ex.printStackTrace();
        }
        System.out.println("Connection Open");
        return connection;
    }

    //Close DB Connection
    private static void closeConn(Connection con) {
        try {
            con.commit(); //commit DB changes
            con.close();
            System.out.println("Connection Closed");
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Error");

        }
    }
}
