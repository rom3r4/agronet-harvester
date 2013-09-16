/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.portalupdater;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ontspace.dc.translator.DublinCore;

/*
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
*/
/**
 * 
 * @author ivan
 */
public class CerifHandler {

	private Connection _con = null;
	private Statement _stmt = null;
	private MetadataRDFHelper _metadataRDFHelper = null;

	public CerifHandler(PortalUpdaterConf portalUpdaterConf)
			throws SQLException {
		this._metadataRDFHelper = new MetadataRDFHelper();
		this.createDBConnection(portalUpdaterConf);
	}

	/**
	 * Set up the conection with the mysql database
	 * 
	 * @param portalUpdaterconf
	 *            Information about the database installation
	 */
	private void createDBConnection(PortalUpdaterConf portalUpdaterConf)
			throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(portalUpdaterConf.getCerifURI(),
					portalUpdaterConf.getCerifUser(),
					portalUpdaterConf.getCerifPassword());

			_stmt = _con.createStatement();
		} catch (ClassNotFoundException ex1) {
			Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE,
					null, ex1);
		} catch (Exception ex) {
			throw new SQLException(ex);
		}

	}

	

	/**
	 * Store a DC resource
	 * 
	 * @param dc
	 * @param fileName
	 * @return
	 */
	 /*
	public boolean storeResourceDC(DublinCore dc, String voa3rId) {
		boolean result = true;

		Entry<String, String> langAndTitle = _metadataRDFHelper.retrieveTitleAndLangFromDC(dc);

		List<Triple> triples = _metadataRDFHelper.retrieveTriplesforDC(voa3rId,langAndTitle.getKey(),langAndTitle.getValue(),dc);

		this.storeResource(voa3rId, langAndTitle.getKey(),
				langAndTitle.getValue(), triples);

		return result;
	}*/

	/**
	 * Store a Agris resource
	 * 
	 * @param agris
	 * @param fileName
	 * @return
	 */
	 /*
	public boolean storeResourceAgris(Agrisap agris, String voa3rId) {
		boolean result = true;

		Entry<String, String> langAndTitle = _metadataRDFHelper.retrieveTitleAndLangFromAgris(agris);

		List<Triple> triples = _metadataRDFHelper.retrieveTriplesforAgris(voa3rId, langAndTitle.getKey(),langAndTitle.getValue(),agris);

		this.storeResource(voa3rId, langAndTitle.getKey(),
				langAndTitle.getValue(), triples);

		return result;
	}

	public boolean storeResourceVAP2(Voa3rAP2 vap2, String voa3rId) {
		//Exponer datos de Voa3rAP2, Ver clase Voa3rAP2QueryManagerImpl
		return storeResourceDC(vap2,voa3rId);
		
	}
	
	*/
	
	/**
	 * Internal method for storing resources
	 * 
	 * @param voa3rId
	 * @param langtitle
	 * @param title
	 * @param triples
	 */
	private void storeResource(String voa3rId, String langtitle, String title,
			List<Triple> triples) {

		Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"STORING Resource " + voa3rId);
		
        if(!this.existsResPublById(voa3rId)){
        	this.insertResPubl(voa3rId);
        } else {
        	Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"cfResPubl " + voa3rId + " already exists");
        }

        if(!this.existsResPublTitleById(voa3rId)){
        	this.insertResPublTitle(voa3rId, title, langtitle);
        } else {
        	Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"cfResPublTitle " + voa3rId + " already exists");
        }
        
		this.deleteTriples(voa3rId);
		this.insertTriples(triples);
		Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"cfResPublTitle " + voa3rId + " already exists");
		Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"STORED Resource " + voa3rId);
	}

	private boolean existsResPublById(String voa3rId) {
		boolean result=true;
		try {
			String query;
			PreparedStatement stmtP;

			query = "SELECT * FROM cfResPubl WHERE cfResPublId = ?;";
			stmtP = _con.prepareStatement(query);
			stmtP.setString(1, voa3rId);
			ResultSet s=stmtP.executeQuery();
			result=s.next();
		} catch (SQLException ex) {
			Logger.getLogger(CerifHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return result;
	}
	
	private boolean existsResPublTitleById(String voa3rId) {
		boolean result=true;
		try {
			String query;
			PreparedStatement stmtP;			
			query = "SELECT * FROM cfResPublTitle WHERE cfResPublId = ?;";
			stmtP = _con.prepareStatement(query);
			stmtP.setString(1, voa3rId);
			ResultSet s=stmtP.executeQuery();
			result=s.next();
		} catch (SQLException ex) {
			Logger.getLogger(CerifHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return result;
	}

	


	private void deleteTriples(String voa3rId) {
		try {
			String query;
			PreparedStatement stmtP;

			query = "DELETE FROM cfResPubl_Triples WHERE cfResPublId = ?;";
			stmtP = _con.prepareStatement(query);
			stmtP.setString(1, voa3rId);
			stmtP.executeUpdate();
			Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO, "cfResPubl_Triples: Deleted triples for " + voa3rId);

		} catch (SQLException ex) {
			Logger.getLogger(CerifHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
	
	private void insertResPubl(String voa3rId) {

		try {
			String query;
			PreparedStatement stmtP;

			query = "INSERT INTO cfResPubl (cfResPublId, cfResPublDate)"
					+ " VALUES (?,?);";
			stmtP = _con.prepareStatement(query);
			stmtP.setString(1, voa3rId);
			stmtP.setDate(2, java.sql.Date.valueOf("1970-01-01"));
			stmtP.executeUpdate();
			Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,
					"cfResPubl: " + voa3rId + " Inserted");
		} catch (SQLException ex) {
			Logger.getLogger(CerifHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private void insertResPublTitle(String voa3rId, String title,
			String langCode) {

		try {
			String query;
			PreparedStatement stmtP;

			query = "INSERT INTO cfResPublTitle (cfResPublId, cfLangCode, cfTrans, cfTitle)"
					+ " VALUES (?,?,?,?);";
			stmtP = _con.prepareStatement(query);
			stmtP.setString(1, voa3rId);
			stmtP.setString(2, langCode);
			stmtP.setString(3, "o");
			stmtP.setString(4, title);
			stmtP.executeUpdate();
			Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,"cfResPublTitle: " + voa3rId + " Inserted");
		} catch (SQLException ex) {
			Logger.getLogger(CerifHandler.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private void insertTriples(List<Triple> triples) {
		int triplesCount = 0;
		for (Triple triple : triples) {
			if (triple.getSubject() != null && triple.getPredicate() != null) {
				try {
					String query;
					PreparedStatement stmtP;

					query = "INSERT INTO cfResPubl_Triples (cfResPublId, predicate, stringObject, uriObject, dateObject, objectFormat)"
							+ " VALUES (?,?,?,?,?,?);";
					stmtP = _con.prepareStatement(query);
					stmtP.setString(1, triple.getSubject());
					stmtP.setString(2, triple.getPredicate());
					stmtP.setString(3, triple.getStringObject());
					stmtP.setString(4, triple.getUriObject());
					stmtP.setDate(
							5,
							obtainSQLDate(triple.getDateObject(),
									triple.getObjectFormat()));
					stmtP.setString(6, triple.getObjectFormat());
					stmtP.executeUpdate();
					triplesCount++;
					// Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,
					// "Triple Inserted for " + triple.getSubject());
				} catch (ParseException ex) {
					Logger.getLogger(CerifHandler.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(CerifHandler.class.getName()).log(
							Level.SEVERE, "Triple: " + triple.toString(), ex);
				}
			}
		}
		Logger.getLogger(CerifHandler.class.getName()).log(Level.INFO,
				"cfResPubl_Triples: " + triplesCount + " triples Inserted");

	}




	
	private java.sql.Date obtainSQLDate(String dateString, String datePattern)
			throws ParseException {
		Date date;
		DateFormat formatter;
		try {
			formatter = new SimpleDateFormat(datePattern);
			date = (Date) formatter.parse(dateString);
		} catch (Exception ex) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date) formatter.parse("1970-01-01");
		}
		return new java.sql.Date(date.getTime());
	}

	

	
	
}
