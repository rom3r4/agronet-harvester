package org.ontspace.agrovoc.cache;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 
 * @author abel
 */
public class AgrovocCacheHelper {

    private static HashMap<String,String> agrovoc = new HashMap<String,String>(665000); 
	private static Connection _con = null;
	private static Statement _stmt = null;

    private static AgrovocCacheHelper singleAH;
    
    private static String _configurationFile = System.getProperty("user.home") + "/VOA3R/etc/agrovoc_dbconf.xml";
    private static String db_uri;
    private static String db_user;
    private static String db_password;
    private static String db_schema;
    private static String db_table;
    
	private AgrovocCacheHelper() {
        createDBConnection();
        buildAgrovoc();
	}
    
    public static AgrovocCacheHelper getInstance() {
        if (singleAH == null) {
            singleAH = new AgrovocCacheHelper();
        }
        return singleAH;
    }
    
    public String getAgrovocId(String label) {
        return agrovoc.get(label);
    }

    
    public static void readConfFile() {
        try {
            // Reads the database configuration
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(_configurationFile));
            Element root = doc.getRootElement();
            db_uri = root.getChild("dbUri").getTextTrim();
            db_user = root.getChild("dbUser").getTextTrim();
            db_password = root.getChild("dbPassword").getTextTrim();
            db_schema = root.getChild("dbSchema").getTextTrim();
            db_table = root.getChild("dbTable").getTextTrim();
        } catch (JDOMException ex) {
            Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	/**
	 * Set up the conection with the mysql database
	 * 
	 * @param portalUpdaterconf
	 *            Information about the database installation
	 */
	private static void createDBConnection() {
        readConfFile();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(db_uri+db_schema, db_user, db_password);
			_stmt = _con.createStatement();
		} catch (ClassNotFoundException ex1) {
			Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE,
					"MySQL driver not found", ex1);
		} catch (SQLException ex) {
            Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE, "SQL Error connecting to Agrovoc db", ex);
        } catch (Exception ex) {
			Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE, "Error connecting to Agrovoc db", ex);
		}
	}
    
    private static void buildAgrovoc() {
    try {
      String query = "SELECT * FROM " + db_table;
      ResultSet rs = _stmt.executeQuery(query);
      String id, label;
      ResultSetMetaData rsmd = rs.getMetaData();
      ArrayList<String> languages = new ArrayList<String>();
      for (int i = 1; i < rsmd.getColumnCount(); i++) {
        languages.add(rsmd.getColumnName(i));
      }
      
      while (rs.next()) {
        id = rs.getString("id");
        for (String lang : languages) {
          label = rs.getString(lang).toLowerCase();
          agrovoc.put(label, id);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(AgrovocCacheHelper.class.getName()).log(Level.SEVERE,
          null, ex);
    }
  }
}