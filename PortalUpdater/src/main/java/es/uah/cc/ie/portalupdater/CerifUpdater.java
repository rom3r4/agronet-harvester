/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.portalupdater;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.jdom.Element;

import org.ontspace.dc.translator.DublinCore;

/*
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.owl.util.AutomaticLangDetector;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
*/
/**
 * 
 * @author ivan
 */
public class CerifUpdater {

/*
	private int contentItemsStored = 0; // this counter will be used to call
										// garbage collector
	private final int CALL_GC = 400;
	private CerifHandler cerifHandler;
	private Logger fileLogger = null;
	private AutomaticLangDetector automaticLangDetector;
	private XMLMetadataFiles xmlFiles = new XMLMetadataFiles();
	private HashMap<String, ArrayList<File>> harvestedXMLFiles;
	private ArrayList<String> deletedRecords;
	private File oneYearFAOFolder;
	private PortalUpdaterConf portalUpdaterConf;

*/
	public static void main(String[] args) {

		System.out.println("\n Start of CerifUpdater program...");
/*		
		CerifUpdater instance = new CerifUpdater();
		instance.processConfiguration(instance.retrieveConfigFile(args));
		instance.removeResources();
		instance.persistDCResources();
		instance.persistVAP2Resources();
		instance.persistAgrisResources();
		instance.persistFAOXMLAgrisResources();
*/
		System.out.println("\n End of CerifUpdater program...");
	}

	/**
	 * Class Constructor for initializating log and lang
	 */
/*
	public CerifUpdater() {

		try {
			boolean append = true;
			String logFilePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "log"
					+ System.getProperty("file.separator") + "log.txt";
			FileHandler fh = new FileHandler(logFilePath, append);
			fh.setFormatter(new Formatter() {

				@Override
				public String format(LogRecord rec) {
					StringBuilder buf = new StringBuilder(1000);
					buf.append(new java.util.Date());
					buf.append(' ');
					buf.append(formatMessage(rec));
					buf.append('\n');
					return buf.toString();
				}
			});
			fileLogger = Logger.getLogger(MetadataSQLHelper.class.getName());
			fileLogger.addHandler(fh);
			// Stop forwarding log records to ancestor handlers
			fileLogger.setUseParentHandlers(false);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(-1);
		}

		String treeFolderPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "etc"
				+ System.getProperty("file.separator") + "europarl_corpus" + System.getProperty("file.separator");
		automaticLangDetector = new AutomaticLangDetector(treeFolderPath);

	}
*/
	/**
	 * Retrieve the config file
	 * 
	 * @param args
	 * @return
	 */
/*
	private String retrieveConfigFile(String[] args) {
		// Section 1: CONFIGURATION
		String configFileCMSUpdater;
		System.out.println("One parameter must been set up to use the program:");
		// it is possible to set up the parameters using the command line
		if (args.length == 1) {
			System.out.println("Using the values obtained in the command line.");
			configFileCMSUpdater = args[0];
		} else {
			System.out.println("Using the default values for the mandatory parameters.");
			configFileCMSUpdater = System.getProperty("user.dir") + System.getProperty("file.separator") + "etc"
					+ System.getProperty("file.separator") + "conf.xml";
		}

		System.out.println("  - CMSupdater Configuration File: " + configFileCMSUpdater);
		return configFileCMSUpdater;

	}
*/
	/**
	 * Open database and harvesting folder
	 * 
	 * @param configFileCMSUpdater
	 */
/*
	private void processConfiguration(String configFileCMSUpdater) {

		portalUpdaterConf = new PortalUpdaterConf(configFileCMSUpdater);

		// CERIF HANDLER
		try {
			cerifHandler = new CerifHandler(portalUpdaterConf);
			Logger.getLogger(Main.class.getName()).log(Level.INFO, "Connected to CERIF DB");
		} catch (SQLException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(-1);
		}

		// HARVESTED XML FILES
		harvestedXMLFiles = xmlFiles.readXMLFiles(portalUpdaterConf.getHarvestingFolder());

		oneYearFAOFolder = xmlFiles.readOneFAOYearFolder(portalUpdaterConf.getFaoAgrisFolder());

		deletedRecords = xmlFiles.getDeletedRecords();

	}
*/
	/**
	 * Remove publications from CERIF
	 */
	private void removeResources() {
		// TODO for (String deletedId : deletedRecords) {

	}

	/**
	 * Persist DC publications from CERIF
	 */
/*
	private void persistDCResources() {

		int count=0, total=0;
		ArrayList<File> dcFiles = harvestedXMLFiles.get("DC");		
		DublinCore dc = null;
		if (dcFiles != null) {
			total=dcFiles.size();
			// we need to check that there are some files to store or update
			for (File xml : dcFiles) {
				count++;
				Logger.getLogger(Main.class.getName()).log(Level.INFO, "File DC " + count + "/" + total + ": "+ xml.getAbsolutePath());
				dc = new DublinCore(xml, fileLogger, automaticLangDetector);
				dc.parseDCXML();

				String voa3rId = this.obtainVOA3RId(xml.getName());
				cerifHandler.storeResourceDC(dc, voa3rId);

				contentItemsStored++;
				if (contentItemsStored % CALL_GC == 0) {
					System.gc();
				}
			}
		}

	}
*/
	/**
	 * Persist AGRIS publications from CERIF
	 */
/*
	private void persistAgrisResources() {
		int count=0, total=0;
		ArrayList<File> agrisFiles = harvestedXMLFiles.get("AGRIS");		
		Agrisap agrisap = null;
		if (agrisFiles != null) {
			total=agrisFiles.size();
			// we need to check that there are some files to store or update
			for (File xml : agrisFiles) {
				count++;
				Logger.getLogger(Main.class.getName()).log(Level.INFO, "File AGRIS " + count + "/" + total + ": "+ xml.getAbsolutePath());				
				agrisap = new Agrisap(xml, fileLogger, automaticLangDetector);
				agrisap.parseAgrisapXML();

				String voa3rId = this.obtainVOA3RId(xml.getName());
				cerifHandler.storeResourceAgris(agrisap, voa3rId);

				contentItemsStored++;
				if (contentItemsStored % CALL_GC == 0) {
					System.gc();
				}
			}
		}
	}

	private void persistVAP2Resources() {
		int count=0, total=0;
		Voa3rAP2 vap2 = null;
		ArrayList<File> vap2Files = harvestedXMLFiles.get("VAP2");
		
		if (vap2Files != null) {
			total=vap2Files.size();
			// we need to check that there are some files to store or update
			for (File xml : vap2Files) {
				count++;
				Logger.getLogger(Main.class.getName()).log(Level.INFO, "File VAP2 " + count + "/" + total + ": "+ xml.getAbsolutePath());
				vap2 = new Voa3rAP2(xml, fileLogger, automaticLangDetector);
				vap2.parseVoa3rAP2XML();

				String voa3rId = this.obtainVOA3RId(xml.getName());
				cerifHandler.storeResourceVAP2(vap2, voa3rId);

				contentItemsStored++;
				if (contentItemsStored % CALL_GC == 0) {
					System.gc();
				}
			}
		}
	}

	private void persistFAOXMLAgrisResources() {
		int count=0, total=0;
		FAOMetadataHelper faoMetadataHelper;
		Agrisap agrisap = null;

		// when oneYearFAOFolder == null all the resources from FAO have been
		// stored
		if (oneYearFAOFolder != null) {
			ArrayList<File> faoXMLFiles = xmlFiles.readFAOXMLFiles(oneYearFAOFolder);
			total=faoXMLFiles.size();
			for (File xml : faoXMLFiles) {
				count++;
				Logger.getLogger(Main.class.getName()).log(Level.INFO, "File FAOXMLAgris " + count + "/" + total + ": "+ xml.getAbsolutePath());
				faoMetadataHelper = new FAOMetadataHelper(xml, fileLogger);
				faoMetadataHelper.parseAgrisapXML();
				HashMap<String, Element> agsResources = faoMetadataHelper.getAgsResource();
				Iterator<String> agsResourceIt = agsResources.keySet().iterator();

				while (agsResourceIt.hasNext()) {
					String faoExtraId = agsResourceIt.next();
					Element faoResource = agsResources.get(faoExtraId);
					agrisap = new Agrisap(xml, fileLogger, automaticLangDetector);
					agrisap.parseAgrisapXML(faoResource);

					String voa3rId = this.obtainVOA3RId(this.obtainFAOHarvestingId(xml, faoExtraId));
					
					cerifHandler.storeResourceAgris(agrisap, voa3rId);

					contentItemsStored++;
					if (contentItemsStored % CALL_GC == 0) {
						System.gc();
					}
				}
			}
		}
	}

	private String obtainFAOHarvestingId(File xml, String faoExtraId) {
		String faoHarvestingId = xml.getAbsolutePath();
		faoHarvestingId = faoHarvestingId.replace(portalUpdaterConf.getFaoAgrisFolder().getAbsolutePath(), "");
		faoHarvestingId = "fao:" + faoHarvestingId;
		faoHarvestingId = faoHarvestingId.replace(".xml", "_xml/" + faoExtraId);

		return faoHarvestingId;
	}
*/
	/**
	 * Get the VOA3Rid
	 * 
	 * @param fileName
	 * @return
	 */
/*
	private String obtainVOA3RId(String fileName) {
		String result = "voa3r:";
		result += this.obtainOapipmhId(fileName);
		return result;
	}
*/
	/**
	 * Get the nOapipmhId
	 * 
	 * @param fileName
	 * @return
	 */
/*
	private String obtainOapipmhId(String fileName) {
		String result;
		result = fileName.replace(".xml", "");
		result = result.replaceAll("_", "/");
		return result;
	}
*/
}
