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

// package es.uah.cc.ie.portalupdater;
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
import es.uah.cc.ie.utils.*;
import es.uah.cc.ie.portalupdater.*;

import es.uah.cc.ie.utils.DrushUpdater;


import org.ontspace.dc.translator.DublinCore;
import org.ontspace.agrisap.translator.Agrisap;

/*
 import org.ontspace.MetadataRecordReference;
 import org.ontspace.mods.translator.Mods;
 import org.ontspace.owl.util.AutomaticLangDetector;
 import org.ontspace.voa3rap2.translator.Voa3rAP2;
 import org.ontspace.voa3rap4.translator.Voa3rAP4;
 */

/**
 * This class contains the code needed to keep up to date the files obtained
 * through the harvesting process, the ont-space repository and the web portal
 * installation.
 *
 * @version 0.8
 */
public class Main {

	/**
	 * main method used to keep synchronized the harvested files obtained from
	 * an institutional repository installation with the web portal CMS and the
	 * ont-space repository
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// 1-Read the configuration file and make some standar tasks like
		// stablish
		// the mysql connection.
		// 2-Read the XML files obtained from the harvesting process
		// 3-Remove the content items that are no longer available in the
		// original repositories
		// 4-Trnaslate and store each XML file into the ont-space repository
		// 5-If the previous step worked withour errors, then store the XML file
		// in the joomla database like an article.



		// Section 1: CONFIGURATION
		String configFileOntSpace, configFileCMSUpdater;
		System.out
		        .println("One parameter must been set up to use the program:");

		System.out.println("  - CMSupdater Configuration File");
		// it is possible to set up the parameters using the command line
		if (args.length == 1) {
			System.out
			        .println("Using the values obtained in the command line.");
			configFileCMSUpdater = args[0];
		} else {
			System.out
			        .println("Using the default values for the mandatory parameters.");
			configFileCMSUpdater = System.getProperty("user.dir")
			        + System.getProperty("file.separator") + "etc"
			        + System.getProperty("file.separator") + "conf.xml";
		}
		System.out.println("  - CMSupdater Configuration File: "
		        + configFileCMSUpdater);

		PortalUpdaterConf portalUpdaterConf = new PortalUpdaterConf(
		        configFileCMSUpdater);

		// configFileOntSpace = portalUpdaterConf.getOntSpaceConfFile().
		// getAbsolutePath();

		// System.out.println("  - Ont-Space Configuration File: None");

		// System.out.println("  - Ont-Space Configuration File: "
		// + configFileOntSpace);
		Logger fileLogger = null;

		try {
			boolean append = true;
			String logFilePath = System.getProperty("user.dir")
			        + System.getProperty("file.separator") + "log"
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

        System.out.println("Cleaning cache...");
        // Clean VOA3R DRUPAL's Cache
        DrushUpdater.cleanCache();

		/*
		 * DatabaseHandler dbHandler = null; try { dbHandler = new
		 * DatabaseHandler(portalUpdaterConf);
		 * Logger.getLogger(Main.class.getName()).log(Level.INFO,
		 * "Database schema and tables successfully created"); } catch
		 * (SQLException ex) {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		 * System.exit(-1); }
		 */

		// System.out.print("Disabling indexes from Database... ");
		// dbHandler.disableKeys();

		System.out.println("[DONE]");

		/*
		 * OntSpaceHandler oHandler = new OntSpaceHandler(configFileOntSpace);
		 * oHandler.printRepositoryURI();
		 */

		// System.out.println("1");
		String treeFolderPath = System.getProperty("user.dir")
		        + System.getProperty("file.separator") + "etc"
		        + System.getProperty("file.separator") + "europarl_corpus"
		        + System.getProperty("file.separator");
		// AutomaticLangDetector automaticLangDetector = new
		// AutomaticLangDetector(
		// treeFolderPath);
		// AutomaticLangDetector automaticLangDetector = null;

		// System.out.println("2");

		// this counter will be used to call the garbage collector
		int contentItemsStored = 0;
		final int CALL_GC = 400;

		// System.out.println("3");

		int invalidCount = 0;

		// Section 2: HARVESTED XML FILES
		XMLMetadataFiles xmlFiles = new XMLMetadataFiles();
		HashMap<String, ArrayList<File>> harvestedXMLFiles = xmlFiles
		        .readXMLFiles(portalUpdaterConf.getHarvestingFolder());

		// System.out.println("4");

		// Section 3: REMOVE CONTENT
		ArrayList<String> deletedRecords = xmlFiles.getDeletedRecords();

		// System.out.println("5");

		// for (String deletedId : deletedRecords) {

		// if (oHandler.remove(deletedId)) {
		// dbHandler.remove(deletedId);
		// System.out.println("6");

		/*
		 * if (dbHandler.remove(deletedId)) {
		 * System.out.println("removed OK from db"); } else {
		 * Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
		 * "ERROR in ont-space while removing {0}", deletedId); }
		 */
		// }

		// SECTION 4 AND 5: STORE OR UPDATE IN ONT-SPACE AND JOOMLA
		// System.out.println("7 --> ");

		ArrayList<File> dcFiles = harvestedXMLFiles.get("DC");
		String fileName;
		DublinCore dc = null;
		// MetadataRecordReference recordReference;
		if (dcFiles != null) {
			// we need to check that there are some files to store or update
			for (File xml : dcFiles) {
				System.out
				        .println("-------------------------------------------------------------------");

				// Logger.getLogger(Main.class.getName()).log(Level.INFO,
				// "File {0}", xml.getAbsolutePath());
				fileName = xml.getName();
				// dc = new DublinCore(xml, fileLogger, automaticLangDetector);
				dc = new DublinCore(xml, fileLogger, "");

				dc.parseDCXML();

                DrushUpdater updater = new DrushUpdater(dc);

				/*
				 * if (!dbHandler.existLO(fileName)) { recordReference =
				 * oHandler.storeDC(dc); if (recordReference != null) {
				 * dbHandler.storeDC(dc, fileName, recordReference.
				 * getLocalMetadataRecordId()); } } else { if
				 * (oHandler.updateDC(dc)) {
				 */
				// dbHandler.updateDC(dc, fileName);

				// }
				// }
				contentItemsStored++;
				if (contentItemsStored % CALL_GC == 0) {
					System.gc();
				}
			}
		}

		/*
		 * ArrayList<File> modsFiles = harvestedXMLFiles.get("MODS"); Mods mods
		 * = null; if (modsFiles != null) { //we need to check that there are
		 * some files to store or update for (File xml : modsFiles) {
		 * Logger.getLogger(Main.class.getName()).log(Level.INFO, "File {0}",
		 * xml.getAbsolutePath()); fileName = xml.getName(); mods = new
		 * Mods(xml, fileLogger, automaticLangDetector); mods.parseModsXML(); if
		 * (!dbHandler.existLO(fileName)) { recordReference =
		 * oHandler.storeMODS(mods); if (recordReference != null) {
		 * dbHandler.storeMODS(mods, fileName, recordReference.
		 * getLocalMetadataRecordId()); } } else { if
		 * (oHandler.updateMODS(mods)) { dbHandler.updateMODS(mods, fileName); }
		 * } contentItemsStored++; if (contentItemsStored % CALL_GC == 0) {
		 * System.gc(); } } }
		 */
		/*
		 * ArrayList<File> vap2Files = harvestedXMLFiles.get("VAP2"); Voa3rAP2
		 * vap2 = null; if (vap2Files != null) { //we need to check that there
		 * are some files to store or update for (File xml : vap2Files) {
		 * Logger.getLogger(Main.class.getName()).log(Level.INFO, "File {0}",
		 * xml.getAbsolutePath()); fileName = xml.getName(); vap2 = new
		 * Voa3rAP2(xml, fileLogger, automaticLangDetector);
		 * vap2.parseVoa3rAP2XML(); if (vap2.validate()) { if
		 * (!dbHandler.existLO(fileName)) { recordReference =
		 * oHandler.storeVoa3rAP2(vap2); if (recordReference != null) {
		 * dbHandler.storeVoa3rAP2(vap2, fileName, recordReference.
		 * getLocalMetadataRecordId()); }
		 * System.out.println("*****ontspace id: "+
		 * recordReference.getLocalMetadataRecordId()); } else { if
		 * (oHandler.updateVoa3rAP2(vap2)) { dbHandler.updateVoa3rAP2(vap2,
		 * fileName); } } contentItemsStored++; if (contentItemsStored % CALL_GC
		 * == 0) { System.gc(); } } else { invalidCount++; } } }
		 */
		/*
		 *
		 * ArrayList<File> vap4Files = harvestedXMLFiles.get("VAP4"); Voa3rAP4
		 * vap4 = null; if (vap4Files != null) { //we need to check that there
		 * are some files to store or update for (File xml : vap4Files) {
		 * Logger.getLogger(Main.class.getName()).log(Level.INFO, "File {0}",
		 * xml.getAbsolutePath()); fileName = xml.getName(); vap4 = new
		 * Voa3rAP4(xml, fileLogger, automaticLangDetector);
		 * vap4.parseVoa3rAP4XML(); if (vap4.validate()) { if
		 * (!dbHandler.existLO(fileName)) { recordReference =
		 * oHandler.storeVoa3rAP4(vap4); if (recordReference != null) {
		 * dbHandler.storeVoa3rAP4(vap4, fileName, recordReference.
		 * getLocalMetadataRecordId()); }
		 * System.out.println("*****ontspace id: "+
		 * recordReference.getLocalMetadataRecordId()); } else { if
		 * (oHandler.updateVoa3rAP4(vap4)) { dbHandler.updateVoa3rAP4(vap4,
		 * fileName); } } contentItemsStored++; if (contentItemsStored % CALL_GC
		 * == 0) { System.gc(); } } else { invalidCount++; } } }
		 */

		ArrayList<File> agrisFiles = harvestedXMLFiles.get("AGRIS");
		Agrisap agrisap = null;
		if (agrisFiles != null) {
			// we need to check that there are some files to store or update
			for (File xml : agrisFiles) {
				System.out
				        .println("-------------------------------------------------------------------");
				// Logger.getLogger(Main.class.getName()).log(Level.INFO,
				// "File {0}", xml.getAbsolutePath());

				agrisap = new Agrisap(xml, fileLogger, "");
				agrisap.parseAgrisapXML();
				DrushUpdater updater = new DrushUpdater(agrisap);
				// fileName = xml.getName();
				// if (!dbHandler.existLO(fileName)) {
				// recordReference = oHandler.storeAgris(agrisap);
				// if (recordReference != null) {
				// dbHandler.storeAgris(agrisap, fileName, recordReference.
				// getLocalMetadataRecordId());
				// System.out.println("Main:: agrisap not saved to db");

				// dbHandler.storeAgris(agrisap, fileName,
				// DatabaseHandler.obtainGenericId(fileName));

				// }
				// } else {
				// if (oHandler.updateAgris(agrisap)) {
				// System.out.println("Main:: saving agrisap to db");
				// dbHandler.updateAgris(agrisap, fileName);
				// }
				// }
				contentItemsStored++;
				if (contentItemsStored % CALL_GC == 0) {
					System.gc();
				}
			}
		}

		// System.out.println("8");
		String faoHarvestingId;
		FAOMetadataHelper faoMetadataHelper;
		File oneYearFAOFolder = xmlFiles.readOneFAOYearFolder(portalUpdaterConf
		        .getFaoAgrisFolder());
		// when oneYearFAOFolder == null all the resources from FAO have been
		// stored
		if (oneYearFAOFolder != null) {
			// System.out.println("9");

			ArrayList<File> faoXMLFiles = xmlFiles
			        .readFAOXMLFiles(oneYearFAOFolder);
			for (File xml : faoXMLFiles) {
				// System.out.println("10");

				Logger.getLogger(Main.class.getName()).log(Level.INFO,
				        "File {0}", xml.getAbsolutePath());
				faoMetadataHelper = new FAOMetadataHelper(xml, fileLogger);
				faoMetadataHelper.parseAgrisapXML();
				HashMap<String, Element> agsResources = faoMetadataHelper
				        .getAgsResource();
				Iterator<String> agsResourceIt = agsResources.keySet()
				        .iterator();

				while (agsResourceIt.hasNext()) {
					String faoExtraId = agsResourceIt.next();
					// System.out.println("11");

					Element faoResource = agsResources.get(faoExtraId);
					// agrisap =
					// new Agrisap(xml, fileLogger, automaticLangDetector);
					agrisap = new Agrisap(xml, fileLogger, "");
					agrisap.parseAgrisapXML(faoResource);

					faoHarvestingId = xml.getAbsolutePath();
					faoHarvestingId = faoHarvestingId.replace(portalUpdaterConf
					        .getFaoAgrisFolder().getAbsolutePath(), "");
					faoHarvestingId = "fao:" + faoHarvestingId;
					faoHarvestingId = faoHarvestingId.replace(".xml", "_xml/"
					        + faoExtraId);
					// if (!dbHandler.existLO(faoHarvestingId)) {
					// recordReference = oHandler.storeAgris(agrisap);
					// if (recordReference != null) {
					// dbHandler.storeAgris(agrisap, faoHarvestingId,
					// recordReference.getLocalMetadataRecordId());
					// System.out.println("Main:: not storing FAO");
					// dbHandler.updateAgris(agrisap, faoHarvestingId);
					// }
					// } else {
					// if (oHandler.updateAgris(agrisap)) {
					// dbHandler.updateAgris(agrisap, faoHarvestingId);
					// }
					// }
					contentItemsStored++;
					if (contentItemsStored % CALL_GC == 0) {
						System.gc();
					}
				}
			}
		}

		System.out
		        .println("-------------------------------------------------------------------");

		Logger.getLogger(Main.class.getName()).log(Level.INFO,
		        "END: Number of content items stored or update {0}",
		        contentItemsStored);
		Logger.getLogger(Main.class.getName()).log(Level.INFO,
		        "END: Number of invalid content items {0}", invalidCount);

		if (contentItemsStored == 0) {
			try {
				File finishedFile = new File(System.getProperty("user.dir")
				        + System.getProperty("file.separator") + "etc"
				        + System.getProperty("file.separator")
				        + ".storageFinished");
				Logger.getLogger(Main.class.getName()).log(Level.INFO,
				        "All resources have been stored.");
				finishedFile.createNewFile();
			} catch (IOException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
				        ex);
			}
		}

		// System.out.print("Enabling indexes from Database... ");
		// dbHandler.enableKeys();
		System.out.println("[DONE]");

	}// end of main method
}
