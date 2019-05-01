/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herc
 */
public class DbTest {

    public static void main(String args[]) {
        Connection connection = null;
        Statement stmt = null;
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
            System.out.println("ok");
        } catch (SQLException ex) {
            Logger.getLogger("Server not found");
            ex.printStackTrace();
        }
        System.out.println("ok");
        try {
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = stmt.executeQuery("select arname,arid,artype from artist");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " "
                        + rs.getString(2) + ":"
                        + rs.getString(3));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
