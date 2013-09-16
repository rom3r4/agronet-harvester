/*
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.uah.cc.ie.portalupdater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ontspace.dc.translator.DublinCore;
import org.ontspace.agrisap.translator.Agrisap;

/*
import org.ontspace.mods.translator.Mods;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
import org.ontspace.voa3rap4.translator.Voa3rAP4;
*/

/**
 * This class contains the code needed to execute the ont-space related methods
 *
 * @author abian
 */
public class DatabaseHandler {

    private Connection _con = null;
    private Statement _stmt = null;
    private MetadataSQLHelper _metadataSQLHelper = null;

    /**
     * Public class constructor
     *
     * @param portalUpdaterconf CMSUpdate configuration infromation about the
     * database instllation
     * @throws SQLException SQL exception
     */
    public DatabaseHandler(PortalUpdaterConf portalUpdaterconf) throws
        SQLException {
        this._metadataSQLHelper = new MetadataSQLHelper();
        this.createDBConnection(portalUpdaterconf);
    }

    /**
     * Set up the conection with the mysql database
     *
     * @param portalUpdaterconf Inforation about the database installation
     */
    private void createDBConnection(PortalUpdaterConf portalUpdaterconf) throws
        SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex1) {
            Logger.getLogger(DatabaseHandler.class.getName()).
                log(Level.SEVERE, null, ex1);
        }
        try {
            _con = DriverManager.getConnection(portalUpdaterconf.getMysqlURI(),
                portalUpdaterconf.getMysqlUser(), portalUpdaterconf.
                getMysqlPassword());
//            _con.createStatement().executeQuery("SET NAMES UTF8").close();
            _stmt = _con.createStatement();
        } catch (Exception ex) {
            try {
                _metadataSQLHelper.createSchema(portalUpdaterconf.getMysqlURI(),
                    portalUpdaterconf.getMysqlUser(),
                    portalUpdaterconf.getMysqlPassword());
                _con = DriverManager.getConnection(
                    portalUpdaterconf.getMysqlURI(),
                    portalUpdaterconf.getMysqlUser(),
                    portalUpdaterconf.getMysqlPassword());
                _stmt = _con.createStatement();
            } catch (SQLException ex1) {
                throw new SQLException(ex1);
            }
        }
        try {
            _metadataSQLHelper.setCon(_con);
            _metadataSQLHelper.setStmt(_stmt);
            _metadataSQLHelper.createIdentifiersTable();
            _metadataSQLHelper.createContentTable();
            _metadataSQLHelper.createDeduplicationTable();
            _metadataSQLHelper.createExpertsAnnotationTable();
        } catch (SQLException ex1) {
            throw new SQLException(ex1);
        }
    }

    /**
     * This method removes an entry in joomla
     *
     * @param deletedId
     * @return
     */
    public boolean remove(String deletedId) {
        boolean result = true;
        System.out.println("joomla: trying to remove " + deletedId);

        return result;
    }


    public boolean storeDC(DublinCore dc, String fileName, String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = dc.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(dc);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }

    
    public boolean updateDC(DublinCore dc, String fileName) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        
        if (dc.getIdentifiers().size() > 0) {
          String metadataId = dc.getIdentifiers().get(0);
          HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(dc);
          boolean isNewResource = false;

          this.updateIdentifierTable(voa3rId, metadataId);
          this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);
	}
	
	if (!result) {
	    System.out.println("updateDC:: error updating database.");
	}
        return result;
    }

    /*
    public boolean storeMODS(Mods mods, String fileName, String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = mods.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(mods);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }*/

    /*
    public boolean updateMODS(Mods mods, String fileName) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = mods.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(mods);
        boolean isNewResource = false;

        this.updateIdentifierTable(voa3rId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }*/

/*
    public boolean storeVoa3rAP2(Voa3rAP2 vap2, String fileName, String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = vap2.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(vap2);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }

    public boolean updateVoa3rAP2(Voa3rAP2 vap2, String fileName) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = vap2.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(vap2);
        boolean isNewResource = false;

        this.updateIdentifierTable(voa3rId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }

    public boolean storeVoa3rAP4(Voa3rAP4 vap4, String fileName, String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = vap4.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(vap4);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }

    public boolean updateVoa3rAP4(Voa3rAP4 vap4, String fileName) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            //there would be some resources without ID in the metatata
            metadataId = vap4.getIdentifiers().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(vap4);
        boolean isNewResource = false;

        this.updateIdentifierTable(voa3rId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }*/
/*
    public boolean storeAgris(Agrisap agrisap, String fileName,
        String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            metadataId = agrisap.getIdentifier().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(agrisap);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }
*/

    public boolean storeAgris(Agrisap agrisap, String fileName,
        String ontspaceId) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            metadataId = agrisap.getIdentifier().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(agrisap);
        boolean isNewResource = true;

        this.insertNewIdentifier(voa3rId, oaipmhId, ontspaceId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }

    public boolean updateAgris(Agrisap agrisap, String fileName) {
        boolean result = false;

        String voa3rId = this.obtainVOA3RId(fileName);
        String oaipmhId = this.obtainOaipmhId(fileName);
        String metadataId;
        try {
            metadataId = agrisap.getIdentifier().get(0);
        } catch (Exception e) {
            metadataId = oaipmhId;
        }
        HashMap<String, String> metadataForSQL = _metadataSQLHelper.
            obtainMetadataforSQL(agrisap);
        boolean isNewResource = false;

        this.updateIdentifierTable(voa3rId, metadataId);
        this.insertOrUpdateMetadata(metadataForSQL, voa3rId, oaipmhId,
            isNewResource);

        return result;
    }
    

    private void insertNewIdentifier(String voa3rId, String oaipmhId,
        String ontspaceId, String metadataId) {

        String query = "INSERT INTO content_id (voa3rid, oaipmh, ontspace, "
            + "metadata) VALUES (?,?,?,?);";
        try {
            PreparedStatement stmtP = _con.prepareStatement(query);
            stmtP.setString(1, voa3rId);
            stmtP.setString(2, oaipmhId);
            stmtP.setString(3, ontspaceId);
            stmtP.setString(4, metadataId);

            stmtP.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
    }

    private void updateIdentifierTable(String voa3rId, String metadataId) {
        String query = "UPDATE content_id SET metadata = ? WHERE voa3rid = ?;";
        try {
            PreparedStatement stmtP = _con.prepareStatement(query);
            stmtP.setString(1, metadataId);
            stmtP.setString(2, voa3rId);

            stmtP.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
    }

    private void insertOrUpdateMetadata(HashMap<String, String> metadata,
        String voa3rId, String oaipmhId, boolean isNew) {

        String metadatalang = metadata.get("metadatalang");
        String title = metadata.get("title");
        String description = metadata.get("description");
        String url = metadata.get("url");
        String keywords = metadata.get("keywords");
        String authors = metadata.get("authors");
        String resourcelang = metadata.get("resourcelang");
        String dateString = metadata.get("date");
        String datePattern = metadata.get("datePattern");
        Date date;
        DateFormat formatter;
        if (!datePattern.equals("unknown")) {
            formatter = new SimpleDateFormat(datePattern);
            try {
                date = (Date) formatter.parse(dateString);
            } catch (ParseException ex) {
                date = GregorianCalendar.getInstance().getTime();
            }
        } else {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = GregorianCalendar.getInstance().getTime();
        }
        java.sql.Date now_sql = new java.sql.Date(date.getTime());
        try {
            String query;
            PreparedStatement stmtP;
            if (isNew) {
                query = "INSERT INTO content_items (voa3rid, metadatalang, title, "
                    + "description, url, keywords, authors, resourcelang, date)"
                    + " VALUES (?,?,?,?,?,?,?,?,?);";
                stmtP = _con.prepareStatement(query);
                System.out.println("title: " + title);
                stmtP.setString(1, voa3rId);
                stmtP.setString(2, metadatalang);
                stmtP.setString(3, title);
                stmtP.setString(4, description);
                stmtP.setString(5, url);
                stmtP.setString(6, keywords);
                stmtP.setString(7, authors);
                stmtP.setString(8, resourcelang);
                stmtP.setDate(9, now_sql);
                System.out.println("query: "+ query);
            } else {
                query =
                    "UPDATE content_items set metadatalang = ?, title = ?, "
                    + "description = ?, url = ?, keywords = ?, authors = ?, "
                    + "resourcelang = ?, date = ?"
                    + " WHERE voa3rid = (SELECT voa3rid from content_id where oaipmh = ?);";
                stmtP = _con.prepareStatement(query);
                stmtP.setString(1, metadatalang);
                stmtP.setString(2, title);
                stmtP.setString(3, description);
                stmtP.setString(4, url);
                stmtP.setString(5, keywords);
                stmtP.setString(6, authors);
                stmtP.setString(7, resourcelang);
                stmtP.setDate(8, now_sql);
                stmtP.setString(9, oaipmhId);
                System.out.println("query: "+ query);
            }
            stmtP.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
    }

    private String obtainVOA3RId(String fileName) {
        String result = "voa3r:";
        result += this.obtainOaipmhId(fileName);
        return result;
    }

    private String obtainOaipmhId(String fileName) {
        String result;
        result = fileName.replace(".xml", "");
        result = result.replaceAll("_", "/");
        return result;
    }

    public static String obtainGenericId(String fileName) {
        String result;
        result = fileName.replace(".xml", "");
        result = result.replaceAll("_", "");
        return result;
    }


    public boolean existLO(String fileName) {
        boolean result = false;
        String voa3rid = this.obtainVOA3RId(fileName);
        String query = "SELECT count(*) FROM content_id where voa3rid = ?";
        try {
            PreparedStatement stmtP = _con.prepareStatement(query);
            stmtP.setString(1, voa3rid);
            ResultSet rs = stmtP.executeQuery();
            rs.next();
            result = rs.getInt(1) > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return result;
    }

    public void disableKeys() {
        String query = "ALTER TABLE content_items DISABLE KEYS";
        try {
            PreparedStatement stmtP = _con.prepareStatement(query);
            stmtP.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enableKeys() {
        String query = "ALTER TABLE content_items ENABLE KEYS";
        try {
            PreparedStatement stmtP = _con.prepareStatement(query);
            stmtP.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
