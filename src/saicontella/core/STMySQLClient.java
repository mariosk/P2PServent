package saicontella.core;

/** 
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import saicontella.core.STLibrary.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class STMySQLClient {
    
    private Connection conn = null;
    private static Log logger = LogFactory.getLog("saicontella/core/STMySQLClient");
    
    public STMySQLClient(String database, String server, String username, String password) {               

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database, username, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            while(rs.next())
            {
                logger.debug("Name " + rs.getString(1));
                logger.debug("Id " + rs.getString(2));
            }
        } 
        catch(Exception e) {
            e.printStackTrace();
            logger.error("Exception: " + e.getMessage());
        } 
        finally {

            try {
                if (conn != null)
                    conn.close();
            } 
            catch(SQLException e) {
                logger.error("Exception: " + e.getMessage());                
            }
        }        
    }
}
