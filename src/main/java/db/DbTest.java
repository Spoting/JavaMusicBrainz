/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package db;

import basics.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    //static String insert = "INSERT INTO ";
    //private static Connection con;

    public static void main(String args[]) throws SQLException {
        DbTest db = new DbTest();
        //Connection con = db.getConnection();
        ArrayList<String> aliases = new ArrayList();
        aliases.add("alias 1");
        aliases.add("alias 4");
        aliases.add("alias 5");

        ArrayList<String> tags = new ArrayList();
        tags.add("tag 1");
        tags.add("tag 5");
        tags.add("tag 3");
        //PERSON
        Artist person = new Person("1960", "2010", "male", "personID",
                "Fred", "US", "California", aliases, tags, "Person");
        // System.out.println(person.toString());
        //GROUP
        ArrayList<Person> members = new ArrayList();
        members.add((Person) person);
        Artist group = new Group("2000", "2009", members, "groupID",
                "GroupFred", "US", "California", aliases, tags, "Group");
        // System.out.println(group.toString());
        //ALBUM
        Release album = new Album(person, "albumID", "Fred's Album",
                "official", "EN", "2001", "CD", 10, "Album");
        //System.out.println(album.toString());
        //COMPILATION
        ArrayList<Artist> artists = new ArrayList();
        artists.add(person);
        artists.add(group);
        Release compilation = new Compilation(artists, "comID", "Mixtape", "unofficial",
                "EN", "2005", "LP", 5, "Compilation");
        // System.out.println(compilation.toString());

        db.insertArtist(person);
        db.insertArtist(group);
        //db.insertTagsAliases(person);
        //System.out.println(person.getClass());
        //db.closeConnection(con);
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
            Statement st = connection.createStatement();
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

        Connection conn = getConn();
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
                    // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            pstmt.close();

        } catch (SQLException ex) {
            // Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error Inserting Aliases&Tags");

        } finally {
            closeConn(conn);
        }
        System.out.println("Aliases&Tags In DB for Artist with ID: " + arid);
    }

    @Override
    public boolean insertArtist(Artist art) {
        Connection conn = getConn();
        PreparedStatement pstmt = null;
        String tableName = "artist";
        
        String gid = null;
        
        if (art.getClass().toString().equals("class basics.Person")) {
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
                pstmt.setString(9, gid);
                //pstmt.setString(9, null);
                pstmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Artist " + art.getId());
                return false;
            } finally {
                closeConn(conn);
                insertTagsAliases(art);
            }

            System.out.println("Success: Person" + art.getId());
            return true;

        } else if (art.getClass().toString().equals("class basics.Group")) {
            try {
                Group g = (Group) art;
                //String gid = g.getId();
                //System.out.println("sdqwqd," + p.getBirthDate());
                //tableName = "artist";
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
                pstmt.setString(9, gid);
                
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error Inserting Artist " + art.getId());
                return false;
            } finally {
                closeConn(conn);
                insertTagsAliases(art);
            }
            System.out.println("Success: Group " + art.getId());
            return true;
        }
        /**
         * if ((art.getClass == Person)) Insert into Artist(art)
         * insertTagsAliases() elseif (art.getClass == Group) gid = art.getId()
         * Insert into Artist(art) insertTagsAliases() for
         * (art.getMembers().size()) Insert into Artists
         * (art.getMembers().get(i), gid) |or|
         * insertArtist(art.getMembers().get(i))
         */
        return false;
    }

    @Override
    public boolean insertRelease(Release rel) {
        //  albid = rel.getId()
        /**
         * if (rel.getClass == Album) arid = rel.getArtist Insert into
         * Album(rel) insertArtist(rel.getArtist()) Insert into Release(arid,
         * albid) elseif (rel.getClass == Compilation) Insert into Album(rel)
         * for (Artist art : rel.getArtists()) arid = art.getId()
         * insertArtist(art) Insert into Release(arid, albid)
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
