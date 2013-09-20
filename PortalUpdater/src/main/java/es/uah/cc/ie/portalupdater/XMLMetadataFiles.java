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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * This class contains the code needed to deal with the harvested XML files
 * that contains the metadata
 * @author abian
 */
public class XMLMetadataFiles {

    private HashMap<String, ArrayList<File>> _metadataFiles = null;
    private ArrayList<String> _deletedRecordsId = null;
    private int _totalContentItems = 0;
    private int _totalFAOItems = 0;

    public XMLMetadataFiles() {
        _metadataFiles = new HashMap<String, ArrayList<File>>();
        _deletedRecordsId = new ArrayList<String>();
    }

    /**
     * Obtain a hashmap with all the harvested files to store or update
     * @param harvestingFolder the folder with the harvested files
     * @return a hashmap with all the new or changed XML files
     */
    public HashMap<String, ArrayList<File>> readXMLFiles(File harvestingFolder) {

        FileFilter folderFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        FileFilter xmlFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile()
                    && !file.getName().equalsIgnoreCase("deletedRecords.xml")
                    && file.getName().endsWith(".xml"));
            }
        };
        FileFilter delFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile()
                    && file.getName().equalsIgnoreCase("deletedRecords.xml"));
            }
        };


        ArrayList<File> dates = new ArrayList<File>();
        ArrayList<File> repositories = new ArrayList<File>();
        ArrayList<File> sets = new ArrayList<File>();
        ArrayList<File> deletedRecords = new ArrayList<File>();
        //Different metadata schemas supported by VOA3R
        ArrayList<File> dc = new ArrayList<File>();
        ArrayList<File> qdc = new ArrayList<File>();
        ArrayList<File> mods = new ArrayList<File>();
        ArrayList<File> agris = new ArrayList<File>();
        ArrayList<File> vap2 = new ArrayList<File>();
        ArrayList<File> vap4 = new ArrayList<File>();

        dates.addAll(Arrays.asList(harvestingFolder.listFiles(folderFilter)));

        //the program only stores one folder each time
        boolean alreadyStored = false;
        boolean finisExecution = false;
        File date;
        Iterator<File> datesIt = dates.iterator();
        while (datesIt.hasNext() && !finisExecution) {
            date = datesIt.next();
            alreadyStored = isFolderAlreadyStored(date);

            if (!alreadyStored) {
                repositories.addAll(Arrays.asList(date.listFiles(folderFilter)));
                System.out.println(date.toString() + " hasn't been stored yet.");
                File newStored = new File(date.getAbsolutePath() + System.
                    getProperty("file.separator") + ".stored");
                finisExecution = true;
                try {
                    newStored.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(XMLMetadataFiles.class.getName()).log(
                        Level.SEVERE, null, ex);
                }

            } else {
                System.out.println(date.toString() + " is already stored.");
            }
        }

        String metadataSchema;
        for (File repFolder : repositories) {
            sets.addAll(Arrays.asList(repFolder.listFiles(folderFilter)));
        }

        for (File setFolder : sets) {
            metadataSchema = setFolder.getParent();
            metadataSchema =
                metadataSchema.substring(metadataSchema.lastIndexOf("_") + 1,
                metadataSchema.length());
            System.out.println("************* Resources found in "+ metadataSchema + " format. ************");
            if (metadataSchema.equalsIgnoreCase("DC")) {
                dc.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put(metadataSchema.toUpperCase(), dc);
            } else if (metadataSchema.equalsIgnoreCase("QDC")) {
                qdc.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put(metadataSchema.toUpperCase(), qdc);
            } else if (metadataSchema.equalsIgnoreCase("MODS") || metadataSchema.equalsIgnoreCase("METS")) {
                mods.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put("MODS", mods);
            } else if (metadataSchema.equalsIgnoreCase("AGRIS")) {
                agris.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put(metadataSchema.toUpperCase(), agris);
            } else if (metadataSchema.equalsIgnoreCase("VOA3R")) {
                vap2.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put("VAP2", vap2);
            } else if (metadataSchema.equalsIgnoreCase("DCDS")) {
                vap4.addAll(Arrays.asList(setFolder.listFiles(xmlFilter)));
                _metadataFiles.put("VAP4", vap4);
            }
        }

        //Some numbers just to be sure about the number of content items harvestded
        Iterator<String> iterator = _metadataFiles.keySet().iterator();
        String key;
        System.out.println("Harvesting summary for this execution: ");
        while (iterator.hasNext()) {
            key = iterator.next();
            System.out.println("  - Number of harvested elements in " + key
                + ": " + _metadataFiles.get(key).size());
        }

        for (File deleted : sets) {
            deletedRecords.addAll(Arrays.asList(deleted.listFiles(delFilter)));
        }
        this.readDeletedRecordsIds(deletedRecords);

        return _metadataFiles;
    }

    /**
     * Obtain a list with all the years availables from FAO
     * @param faoXMLFolder folder with the XML files
     * @return a list with all the folders with the years
     * @deprecated There is a new function for this
     */
    @Deprecated
    public ArrayList<File> readFAOXMLDates(File faoXMLFolder) {
        ArrayList<File> result = new ArrayList<File>();
        ArrayList<File> allDates = new ArrayList<File>();
        FileFilter folderFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        allDates.addAll(Arrays.asList(faoXMLFolder.listFiles(folderFilter)));
        //the program only stores one folder each time
        boolean alreadyStored = false;
        boolean finisExecution = false;
        File date;
        Iterator<File> datesIt = allDates.iterator();
        while (datesIt.hasNext() && !finisExecution) {
            date = datesIt.next();
            alreadyStored = isFolderAlreadyStored(date);
            if (!alreadyStored) {
                result.add(date);
                System.out.println(date.toString() + " hasn't been stored yet.");
                File newStored = new File(date.getAbsolutePath() + System.
                    getProperty("file.separator") + ".stored");
                finisExecution = true;
                try {
                    newStored.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(XMLMetadataFiles.class.getName()).log(
                        Level.SEVERE, null, ex);
                }
            } else {
                System.out.println(date.toString() + " is already stored.");
            }
        }

        return result;
    }

    /**
     * Obtain a folder with one year to be stored (from FAO)
     * @param faoXMLFolder folder with the XML files
     * @return the folder to be stored, only one year folder
     */
    public File readOneFAOYearFolder(File faoXMLFolder) {
        File result = null;
        ArrayList<File> allDates = new ArrayList<File>();
        FileFilter folderFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        allDates.addAll(Arrays.asList(faoXMLFolder.listFiles(folderFilter)));
        //the program only stores one yeah folder each time
        boolean finishExcecution = false;
        File date;
        Iterator<File> datesIt = allDates.iterator();
        while (datesIt.hasNext() && !finishExcecution) {
            date = datesIt.next();
            if (!isFolderAlreadyStored(date)) {
                result = date;
                System.out.println(date.toString() + " hasn't been stored yet.");
                File newStored = new File(date.getAbsolutePath() + System.
                    getProperty("file.separator") + ".stored");
                finishExcecution = true;
                try {
                    newStored.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(XMLMetadataFiles.class.getName()).log(
                        Level.SEVERE, null, ex);
                }
            } else {
                System.out.println(date.toString() + " is already stored.");
            }
        }

        return result;
    }

    /**
     * Obtain a list with of all the files to store or update obtained from FAO
     * for a specific year
     * @param faoXMLDateFolder folder with the XML files
     * @return a list with all the new or changed XML files for the selected year
     */
    public ArrayList<File> readFAOXMLFiles(File faoXMLDateFolder) {

        FileFilter folderFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        FileFilter xmlFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile()
                    && !file.getName().equalsIgnoreCase("deletedRecords.xml")
                    && file.getName().endsWith(".xml"));
            }
        };

        ArrayList<File> dates = new ArrayList<File>();
        ArrayList<File> result = new ArrayList<File>();

        dates.addAll(Arrays.asList(faoXMLDateFolder.listFiles(folderFilter)));

        File subCategoryFolder;
        Iterator<File> subCategoryIt = dates.iterator();
        while (subCategoryIt.hasNext()) {
            subCategoryFolder = subCategoryIt.next();
            result.addAll(Arrays.asList(subCategoryFolder.listFiles(
                xmlFilter)));
            _totalFAOItems += result.size();
        }
        return result;
    }

    /**
     * This method checks if there is any file named .stored inside the folder
     * passed as a parameter.
     * @param folder folder to analyse
     * @return true if the content inside folder is already stored, false otherwise
     */
    private static boolean isFolderAlreadyStored(File folder) {
        boolean result = false;
        File[] storedFiles = null;
        FileFilter storedFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                //in the next line it should be ".stored"
                return file.getName().startsWith(".stored");
            }
        };
        storedFiles = folder.listFiles(storedFilter);
        if (storedFiles.length == 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private void readDeletedRecordsIds(ArrayList<File> deletedRecordsFiles) {
        Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        Element root = null;
        List<Element> identifiers = null;

        for (File delFile : deletedRecordsFiles) {
            try {
                doc = builder.build(new FileInputStream(delFile));
                // Get root element <deletedRecords>
                root = doc.getRootElement();
                // get the <id> children that contain the resources to delete
                identifiers = root.getChildren("id");
                for (Element child : identifiers) {
                    _deletedRecordsId.add(child.getTextTrim());
                }
            } catch (JDOMException ex) {
                Logger.getLogger(XMLMetadataFiles.class.getName()).log(
                    Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(XMLMetadataFiles.class.getName()).log(
                    Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Getter for the XML Metadata Files
     * @return the _metadataFiles
     */
    public HashMap<String, ArrayList<File>> getMetadataFiles() {
        return _metadataFiles;
    }

    /**
     * Getter for the deleted records files
     * @return the _deletedRecordsId
     */
    public ArrayList<String> getDeletedRecords() {
        return _deletedRecordsId;
    }

    /**
     * Getter fo the total number of content items harvested
     * @return the totalContentItems
     */
    public int getTotalContentItems() {
        return _totalContentItems;
    }

    /**
     * Getter for the total number of FAO items in Agris format read from the
     * local hard disk
     * @return the _totalFAOItems
     */
    public int getTotalFAOItems() {
        return _totalFAOItems;
    }
}
