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

import ezvcard.Ezvcard;
import ezvcard.VCard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ontspace.dc.translator.DublinCore;
import org.ontspace.agrisap.translator.Agrisap;
/*
import org.ontspace.mods.translator.Mods;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
import org.ontspace.voa3rap4.translator.Vap4Agent;
import org.ontspace.voa3rap4.translator.Voa3rAP4;
*/


/**
 *Class used to contain the code related to obtain the metadata from the XML and
 * the creation of the SQL
 */
public class MetadataSQLHelper {

    private Connection _con = null;
    private Statement _stmt = null;
    private static final Map<String, String> DATE_FORMAT_REGEXPS =
        new HashMap<String, String>() {

            {
                put("^\\d{4}$", "yyyy");
                put("^\\d{8}$", "yyyyMMdd");
                put("^\\d{4}-\\d{1,2}$", "yyyy-MM");
                put("^[a-z]{3}\\s\\d{4}$", "MMM yyyy");//FAO date format
                put("^[a-z]{3}\\d{4}$", "MMMyyyy");//FAO date format
                put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
                put("^\\d{12}$", "yyyyMMddHHmm");
                put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
                put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$",
                    "dd-MM-yyyy HH:mm");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$",
                    "yyyy-MM-dd HH:mm");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$",
                    "MM/dd/yyyy HH:mm");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$",
                    "yyyy/MM/dd HH:mm");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$",
                    "dd MMM yyyy HH:mm");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$",
                    "dd MMMM yyyy HH:mm");
                put("^\\d{14}$", "yyyyMMddHHmmss");
                put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
                put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "dd-MM-yyyy HH:mm:ss");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "yyyy-MM-dd HH:mm:ss");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "MM/dd/yyyy HH:mm:ss");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "yyyy/MM/dd HH:mm:ss");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "dd MMM yyyy HH:mm:ss");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
                    "dd MMMM yyyy HH:mm:ss");
            }
        };

    /**
     * Default constructor
     */
    public MetadataSQLHelper() {
    }

    /**
     * Constructor with parameters
     * @param con DC connection
     * @param stmt DC statement
     */
    MetadataSQLHelper(Connection con, Statement stmt) {
        this._con = con;
        this._stmt = stmt;
    }

    /**
     * Create the Database Schema if it hasn't been date in advance.
     * @param uri URL of the mysql connection
     * @param user DB user
     * @param passw DB password
     */
    public void createSchema(String uri, String user, String passw) {
        try {
            //CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name
            com.mysql.jdbc.Connection conn = null;
            String url = null;
            //get the appropiate url without the schema name
            if (uri.lastIndexOf("/") > 8) {
                url = uri.substring(0, uri.lastIndexOf("/"));
            }
            String modelName = uri.substring(uri.lastIndexOf("/") + 1);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(url,
                user, passw);
            if (conn != null) {
                com.mysql.jdbc.Statement stmt = (com.mysql.jdbc.Statement) conn.
                    createStatement();
                stmt.execute("CREATE SCHEMA IF NOT EXISTS " + modelName
                    + " CHARACTER SET utf8 COLLATE utf8_general_ci;");
                Logger.getLogger(MetadataSQLHelper.class.getName()).log(
                    Level.INFO,
                    "Schema {0} successfully created", modelName);
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetadataSQLHelper.class.getName()).
                log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetadataSQLHelper.class.getName()).log(
                Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MetadataSQLHelper.class.getName()).log(
                Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetadataSQLHelper.class.getName()).log(
                Level.SEVERE, null, ex);
        }

    }

    public void createContentTable() throws SQLException {
        // Creates the table
        String query = "CREATE TABLE IF NOT EXISTS content_items (";
        query += "voa3rid VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "metadatalang CHAR(10) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null default 'en',";
        query += "title VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null default 'title',";
        query += "description TEXT  CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci NOT NULL ,";
        query += "url VARCHAR(250)  CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci NOT NULL DEFAULT 'url',";
        query += "keywords VARCHAR(250)  CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci NOT NULL DEFAULT 'keywords',";
        query += "authors VARCHAR(250)  CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci NOT NULL DEFAULT 'authors',";
        query += "resourcelang CHAR(10)  CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci NOT NULL DEFAULT 'en',";
        query += "date DATETIME NOT NULL,";
        query += "PRIMARY KEY (voa3rid, metadatalang)";
        query += ")";
        query += "ENGINE = MyISAM ";
        query += "CHARACTER SET utf8 COLLATE utf8_general_ci;";
        _stmt.execute(query);

    }

    public void createIdentifiersTable() throws SQLException {

        // Creates the table
        String query = "CREATE TABLE IF NOT EXISTS content_id (";
        query += "voa3rid VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "oaipmh VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "ontspace VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "metadata VARCHAR(350) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "PRIMARY KEY (voa3rid, oaipmh)";
        query += ")";
        query += "ENGINE = MyISAM ";
        query += "CHARACTER SET utf8 COLLATE utf8_general_ci;";
        //System.out.println("Query SQL: " + query);
        _stmt.execute(query);

    }

    public void createDeduplicationTable() throws SQLException {

        // Creates the table
        String query = "CREATE TABLE  IF NOT EXISTS deduplication (";
        query += "voa3rid VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "voa3rid2 VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "similarity double NOT NULL,";
        query += "checked TINYINT(1) NOT NULL DEFAULT '0',";
        query += "confirmed TINYINT(1) NOT NULL DEFAULT '0',";
        query += "PRIMARY KEY (voa3rid, voa3rid2)";
        query += ")";
        query += "ENGINE = MyISAM ";
        query += "CHARACTER SET utf8 COLLATE utf8_general_ci;";
        _stmt.execute(query);

    }

    public void createExpertsAnnotationTable() throws SQLException {

        // Creates the table
        String query =
            "CREATE TABLE IF NOT EXISTS content_experts_annotations (";
        query += "id INT NOT NULL AUTO_INCREMENT,";
        query += "voa3rid VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "joomlauserid INTEGER not null,";
        query += "date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,";
        query += "action VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        //dependentVariable is agrovoc so 150
        query += "subject VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        //dependentVariable is agrovoc so 150
        query += "dependentVariable VARCHAR(150) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "independentVariable VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "protocol VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "method VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "instrument VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "technique VARCHAR(250) CHARACTER SET utf8 "
            + "COLLATE utf8_general_ci not null,";
        query += "PRIMARY KEY (id)";
        query += ")";
        query += "ENGINE = MyISAM ";
        query += "CHARACTER SET utf8 COLLATE utf8_general_ci;";
        //System.out.println("Query SQL: " + query);
        //new-action
        //new-subject
        //new-dependentVariable
        //new-independentVariable
        //new-protocol
        //new-method
        //new-instrument
        //new-technique
        _stmt.execute(query);

    }


    public HashMap<String, String> obtainMetadataforSQL(DublinCore dc) {
        HashMap<String, String> result = new HashMap<String, String>();
        //the metadatalang will be always the title's language
        String metadatalang;
        String resourcelang;
        try {
            resourcelang = dc.getLanguages().get(0);
        } catch (Exception e) {
            resourcelang = "en";
        }
        String title;
        try {
            title = dc.getTitle(resourcelang);
            metadatalang = resourcelang;
            if (title == null) {
                HashMap<String, String> titles = dc.getTitles();
                Set<String> availableLang = titles.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    String lang = langIt.next();
                    title = titles.get(lang);
                    metadatalang = lang;
                } else {
                    title = "title not found in any language";
                    metadatalang = "en";
                }
            }
        } catch (Exception e) {
            title = "title";
            metadatalang = "en";
        }
        result.put("title", title);
        result.put("metadatalang", metadatalang);
        result.put("resourcelang", resourcelang);

        String description;
        try {
            description = dc.getDescription(resourcelang);
            if (description == null) {
                HashMap<String, String> descriptions = dc.getDescriptions();
                Set<String> availableLang = descriptions.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    description = descriptions.get(langIt.next());
                } else {
		   /*
                    if (dc instanceof Voa3rAP2) {
                        Voa3rAP2 vap2 = (Voa3rAP2) dc;
                        //no description, try with abstract...
                        HashMap<String, String> descAbstract = vap2.getAbstract();
                        description = descAbstract.get(resourcelang);
                        if (description == null) {
                            // not in resourcelang, try with another language...
                            availableLang = descAbstract.keySet();
                            langIt = availableLang.iterator();
                            if (langIt.hasNext()) {
                                description = descAbstract.get(langIt.next());
                            } else {
                                //no description and no abstract :(
                                description = "description not provided in any language";
                            }
                        }
                    } else {
                    */
                        description = "description not provided in any language";
                    // }
                }
            }
        } catch (Exception e) {
            description = "description";
        }
        result.put("description", description);

        String url = null;
        boolean url_found = false;
        Iterator<String> identifiersIt = dc.getIdentifiers().iterator();
        while (identifiersIt.hasNext() && !url_found) {
            url = identifiersIt.next();
            if (url.startsWith("http")) {
                url_found = true;
            }
        }
        if (url != null) {
            if (url.length() > 249) {
                url = url.substring(0, 249);
            }
        } else {
            url = "URL not provided";
        }
        result.put("url", url);

        String keywords = new String();
        try {
            ArrayList<String> keywordList = dc.getSubjects(resourcelang);
            //check if there are not keywords in the resources' language
            if (keywordList == null) {
                HashMap<String, ArrayList<String>> allSubjects =
                    dc.getSubjects();
                Set<String> availableLang = allSubjects.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    keywordList = allSubjects.get(langIt.next());
                } else {
                    keywordList = new ArrayList<String>();
                }
            }
            if (!keywordList.isEmpty()) {
                for (String keyword : keywordList) {
                    keyword = keyword.replaceAll(";", "|");
                    keyword = keyword.replaceAll(",", " |");
                    keywords += keyword.toLowerCase() + " | ";
                }
                keywords = keywords.substring(0, keywords.lastIndexOf("|") - 1);
                if (keywords.length() > 249) {
                    keywords = keywords.substring(0, 249);
                }
            }
        } catch (Exception e) {
            keywords = "";
        }
        result.put("keywords", keywords);

        String authors = new String();
        try {
            ArrayList<String> authorsList = dc.getCreators();
            for (String author : authorsList) {
                if (author.startsWith("vcard")) {
                    VCard vcard = Ezvcard.parse(author).first();
                    author = vcard.getFormattedName().getValue();
                }

                int firstParenthesis = author.indexOf("(");
                if (firstParenthesis != -1) {
                    author = author.substring(0, firstParenthesis);
                }
                authors += author + " | ";
            }
            authors = authors.substring(0, authors.lastIndexOf("|") - 1);
        } catch (Exception e) {
            authors = "";
        }
        if (authors.length() > 249) {
            authors = authors.substring(0, 249);
        }
        result.put("authors", authors);

        String dateString = new String();
        String datePattern = "unknown";
        try {
            ArrayList<String> allDates = dc.getDates();
            Iterator<String> allDatesIt = allDates.iterator();
            boolean found = false;
            while (allDatesIt.hasNext() && !found) {
                dateString = allDatesIt.next();
                dateString = dateString.replaceAll("\\.", "");
                dateString = dateString.replaceAll("\\(", "");
                dateString = dateString.replaceAll("\\)", "");
                for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
                    if (dateString.toLowerCase().matches(regexp)) {
                        datePattern = DATE_FORMAT_REGEXPS.get(regexp);
                        found = true;
                    }
                }
            }
        } catch (Exception e) {
            dateString = "date not available";
        }
        result.put("date", dateString);
        result.put("datePattern", datePattern);

        return result;
    }
    
    /*
    public HashMap<String, String> obtainMetadataforSQL(Mods mods) {
        HashMap<String, String> result = new HashMap<String, String>();
        //the metadatalang will be always the title's language
        String metadatalang;
        String resourcelang;
        try {
            resourcelang = mods.getLanguages().get(0);
        } catch (Exception e) {
            resourcelang = "en";
        }
        String title;
        try {
            title = mods.getTitle(resourcelang);
            metadatalang = resourcelang;
            if (title == null) {
                HashMap<String, String> titles = mods.getTitles();
                Set<String> availableLang = titles.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    String lang = langIt.next();
                    title = titles.get(lang);
                    metadatalang = lang;
                } else {
                    title = "title not found in any language";
                    metadatalang = "en";
                }
            }
        } catch (Exception e) {
            title = "title";
            metadatalang = "en";
        }
        result.put("title", title);
        result.put("metadatalang", metadatalang);
        result.put("resourcelang", resourcelang);

        String description;
        try {
            description = mods.getAbstract(resourcelang);
            if (description == null) {
                HashMap<String, String> descriptions = mods.getAbstracts();
                Set<String> availableLang = descriptions.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    description = descriptions.get(langIt.next());
                } else {
                    description = "description not provided in any language";
                }
            }
        } catch (Exception e) {
            description = "description";
        }
        result.put("description", description);

        String url = null;
        boolean url_found = false;
        ArrayList<String> locations = mods.getLocations();
        if (locations != null && locations.size() > 0) {
            url = locations.get(0);
            url_found = true;
        }
        Iterator<String> identifiersIt = mods.getIdentifiers().iterator();
        while (identifiersIt.hasNext() && !url_found) {
            url = identifiersIt.next();
            if (url.startsWith("http")) {
                url_found = true;
            }
        }
        if (url != null) {
            if (url.length() > 249) {
                url = url.substring(0, 249);
            }
        } else {
            url = "URL not provided";
        }
        result.put("url", url);

        String keywords = new String();
        try {
            ArrayList<String> keywordList = mods.getSubjects(resourcelang);
            //check if there are not keywords in the resources' language
            if (keywordList == null) {
                HashMap<String, ArrayList<String>> allSubjects =
                    mods.getSubjects();
                Set<String> availableLang = allSubjects.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    keywordList = allSubjects.get(langIt.next());
                } else {
                    keywordList = new ArrayList<String>();
                }
            }
            if (!keywordList.isEmpty()) {
                for (String keyword : keywordList) {
                    keyword = keyword.replaceAll(";", "|");
                    keyword = keyword.replaceAll(",", " |");
                    keywords += keyword.toLowerCase() + " | ";
                }
                keywords = keywords.substring(0, keywords.lastIndexOf("|") - 1);
                if (keywords.length() > 249) {
                    keywords = keywords.substring(0, 249);
                }
            }
        } catch (Exception e) {
            keywords = "";
        }
        result.put("keywords", keywords);

        String authors = new String();
        try {
            ArrayList<String> authorsList = mods.getCreators();
            for (String author : authorsList) {
                if (author.startsWith("vcard")) {
                    VCard vcard = Ezvcard.parse(author).first();
                    author = vcard.getFormattedName().getValue();
                }

                int firstParenthesis = author.indexOf("(");
                if (firstParenthesis != -1) {
                    author = author.substring(0, firstParenthesis);
                }
                authors += author + " | ";
            }
            authors = authors.substring(0, authors.lastIndexOf("|") - 1);
        } catch (Exception e) {
            authors = "";
        }
        if (authors.length() > 249) {
            authors = authors.substring(0, 249);
        }
        result.put("authors", authors);

        String dateString = new String();
        String datePattern = "unknown";
        try {
            ArrayList<String> allDates = mods.getDates();
            Iterator<String> allDatesIt = allDates.iterator();
            boolean found = false;
            while (allDatesIt.hasNext() && !found) {
                dateString = allDatesIt.next();
                dateString = dateString.replaceAll("\\.", "");
                dateString = dateString.replaceAll("\\(", "");
                dateString = dateString.replaceAll("\\)", "");
                for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
                    if (dateString.toLowerCase().matches(regexp)) {
                        datePattern = DATE_FORMAT_REGEXPS.get(regexp);
                        found = true;
                    }
                }
            }
        } catch (Exception e) {
            dateString = "date not available";
        }
        result.put("date", dateString);
        result.put("datePattern", datePattern);

        return result;
    }
*/
/*
    public HashMap<String, String> obtainMetadataforSQL(Voa3rAP4 vap4) {
        HashMap<String, String> result = new HashMap<String, String>();
        //the metadatalang will be always the title's language
        String metadatalang;
        String resourcelang;
        try {
            resourcelang = vap4.getLanguages().get(0);
        } catch (Exception e) {
            resourcelang = "en";
        }
        String title;
        try {
            title = vap4.getTitle(resourcelang);
            metadatalang = resourcelang;
            if (title == null) {
                HashMap<String, String> titles = vap4.getTitles();
                Set<String> availableLang = titles.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    String lang = langIt.next();
                    title = titles.get(lang);
                    metadatalang = lang;
                } else {
                    title = "title not found in any language";
                    metadatalang = "en";
                }
            }
        } catch (Exception e) {
            title = "title";
            metadatalang = "en";
        }
        result.put("title", title);
        result.put("metadatalang", metadatalang);
        result.put("resourcelang", resourcelang);

        String description;
        try {
            description = vap4.getDescription(resourcelang);
            if (description == null) {
                //not in resourcelang, try with other language...
                HashMap<String, String> descriptions = vap4.getDescriptions();
                Set<String> availableLang = descriptions.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    description = descriptions.get(langIt.next());
                } else {
                    //no description, try with abstract...
                    HashMap<String, String> descAbstract = vap4.getAbstract();
                    description = descAbstract.get(resourcelang);
                    if (description == null) {
                        // not in resourcelang, try with another language...
                        availableLang = descAbstract.keySet();
                        langIt = availableLang.iterator();
                        if (langIt.hasNext()) {
                            description = descAbstract.get(langIt.next());
                        } else {
                            //no description and no abstract :(
                            description = "description not provided in any language";
                        }
                    }
                }
            }
        } catch (Exception e) {
            description = "description not provided in any language";
        }
        result.put("description", description);

        String url = vap4.getURL();
        boolean url_found = (url != null) && ! url.isEmpty();
        Iterator<String> identifiersIt = vap4.getIdentifiers().iterator();
        while (identifiersIt.hasNext() && !url_found) {
            url = identifiersIt.next();
            if (url.startsWith("http")) {
                url_found = true;
            }
        }
        if (url != null) {
            if (url.length() > 249) {
                url = url.substring(0, 249);
            }
        } else {
            url = "URL not provided";
        }
        result.put("url", url);

        String keywords = new String();
        try {
            ArrayList<String> keywordList = vap4.getSubjects(resourcelang);
            //check if there are not keywords in the resources' language
            if (keywordList == null) {
                HashMap<String, ArrayList<String>> allSubjects =
                    vap4.getSubjects();
                Set<String> availableLang = allSubjects.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    keywordList = allSubjects.get(langIt.next());
                } else {
                    keywordList = new ArrayList<String>();
                }
            }
            if (!keywordList.isEmpty()) {
                for (String keyword : keywordList) {
                    keyword = keyword.replaceAll(";", "|");
                    keyword = keyword.replaceAll(",", " |");
                    keywords += keyword.toLowerCase() + " | ";
                }
                keywords = keywords.substring(0, keywords.lastIndexOf("|") - 1);
                if (keywords.length() > 249) {
                    keywords = keywords.substring(0, 249);
                }
            }
        } catch (Exception e) {
            keywords = "";
        }
        result.put("keywords", keywords);

        String authors = new String();
        try {
            ArrayList authorsList = vap4.getCreators();
            for (Object author : authorsList) {
                String author_str = "";
                if (author instanceof String) {
                    author_str = (String) author;
                    if (author_str.startsWith("vcard")) {
                        VCard vcard = Ezvcard.parse(author_str).first();
                        author_str = vcard.getFormattedName().getValue();
                    }
                } else if (author instanceof Vap4Agent) {
                    author_str = ((Vap4Agent) author).getFullName();
                }
                int firstParenthesis = author_str.indexOf("(");
                if (firstParenthesis != -1) {
                    author = author_str.substring(0, firstParenthesis);
                }
                authors += author_str + " | ";
            }
            authors = authors.substring(0, authors.lastIndexOf("|") - 1);
        } catch (Exception e) {
            authors = "";
        }
        if (authors.length() > 249) {
            authors = authors.substring(0, 249);
        }
        result.put("authors", authors);

        String dateString = new String();
        String datePattern = "unknown";
        try {
            ArrayList<String> allDates = vap4.getDates();
            Iterator<String> allDatesIt = allDates.iterator();
            boolean found = false;
            while (allDatesIt.hasNext() && !found) {
                dateString = allDatesIt.next();
                dateString = dateString.replaceAll("\\.", "");
                dateString = dateString.replaceAll("\\(", "");
                dateString = dateString.replaceAll("\\)", "");
                for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
                    if (dateString.toLowerCase().matches(regexp)) {
                        datePattern = DATE_FORMAT_REGEXPS.get(regexp);
                        found = true;
                    }
                }
            }
        } catch (Exception e) {
            dateString = "date not available";
        }
        result.put("date", dateString);
        result.put("datePattern", datePattern);

        return result;
    }
*/    
    public HashMap<String, String> obtainMetadataforSQL(Agrisap agrisap) {
        HashMap<String, String> result = new HashMap<String, String>();
        String metadatalang;
        String resourcelang;
        try {
            resourcelang = agrisap.getLanguage().get(0);
        } catch (Exception e) {
            resourcelang = "en";
        }

        String title;
        try {
            title = agrisap.getTitle(resourcelang);
            metadatalang = resourcelang;
            if (title == null) {
                HashMap<String, String> titles = agrisap.getTitles();
                Set<String> availableLang = titles.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    String lang = langIt.next();
                    title = titles.get(lang);
                    metadatalang = lang;
                } else {
                    title = "title not provided in any language";
                    metadatalang = "en";
                }
            }
        } catch (Exception e) {
            title = "default title";
            metadatalang = "en";
        }
        if (title.length() > 249) {
            title = title.substring(0, 249);
        }
        result.put("title", title);
        result.put("metadatalang", metadatalang);
        result.put("resourcelang", resourcelang);

        String description;
        try {
            description = agrisap.getAbstract(resourcelang);
            if (description == null) {
                HashMap<String, String> descriptions = agrisap.getAbstracts();
                Set<String> availableLang = descriptions.keySet();
                Iterator<String> langIt = availableLang.iterator();
                if (langIt.hasNext()) {
                    description = descriptions.get(langIt.next());
                } else {
                    description = "description not found in any language";
                }
            }
        } catch (Exception e) {
            description = "default abstract";
        }
        result.put("description", description);

        String url = null;
        boolean url_found = false;
        Iterator<String> identifiersIt = agrisap.getIdentifier().iterator();
        while (identifiersIt.hasNext() && !url_found) {
            url = identifiersIt.next();
            if (url.startsWith("http")) {
                url_found = true;
            }
        }
        if (url != null) {
            if (url.length() > 249) {
                url = url.substring(0, 249);
            }
        } else {
            url = "URL not provided";
        }
        result.put("url", url);

        String keywords = new String();
        try {
            ArrayList<String> keywordList = agrisap.getSubject();
            if (!keywordList.isEmpty()) {
                for (String keyword : keywordList) {
                    keyword = keyword.replaceAll(";", "|");
                    keyword = keyword.replaceAll(",", " |");
                    keywords += keyword.toLowerCase() + " | ";
                }
                keywords = keywords.substring(0, keywords.lastIndexOf("|") - 1);
                if (keywords.length() > 249) {
                    keywords = keywords.substring(0, 249);
                }
            }
        } catch (Exception e) {
            keywords = "";
        }
        result.put("keywords", keywords);

        String authors = new String();
        try {
            ArrayList<String> authorsList = agrisap.getCreatorPersonal();
            //Example author: Vutto, N.L. 
            //Example author: Volotovskij, I.D., Nationals, Minsk . Institute ...
            //I only want: Volotovskij, I.D.
            //Example author: Correa, Carla Maria Camargo(Universidade Federal do Parana) 
            //I only want: Correa, Carla Maria Camargo
            for (String author : authorsList) {
                int firstComma = author.indexOf(",");
                int secondComma = author.indexOf(",", firstComma + 1);
                if (secondComma != -1) {
                    author = author.substring(0, secondComma);
                }
                int firstParenthesis = author.indexOf("(");
                if (firstParenthesis != -1) {
                    author = author.substring(0, firstParenthesis);
                }
                authors += author + " | ";
            }
            authors = authors.substring(0, authors.lastIndexOf("|") - 1);
        } catch (Exception e) {
            authors = "";
        }
        if (authors.length() > 249) {
            authors = authors.substring(0, 249);
        }
        result.put("authors", authors);
        String dateString = new String();
        String datePattern = "unknown";
        try {
            ArrayList<String> allDates = agrisap.getDate();
            Iterator<String> allDatesIt = allDates.iterator();
            boolean found = false;
            while (allDatesIt.hasNext() && !found) {
                dateString = allDatesIt.next();
                //something like "Oct. 2009" is changed to 2009
                //   because the regular expresion MMM. yyyy is not working
                dateString = dateString.replaceAll("\\(", "");
                dateString = dateString.replaceAll("\\)", "");
                dateString = dateString.replaceAll(" ", "");
                dateString = dateString.substring(dateString.lastIndexOf(".")
                    + 1, dateString.length());
                for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
                    if (dateString.toLowerCase().matches(regexp)) {
                        datePattern = DATE_FORMAT_REGEXPS.get(regexp);
                        found = true;
                    }
                }
                if (datePattern.equals("MMMyyyy")) {
                    datePattern = "yyyy";
                    dateString = dateString.substring(dateString.length() - 4,
                        dateString.length());
                }
            }
        } catch (Exception e) {
            dateString = "date not available";
        }
        result.put("date", dateString);
        result.put("datePattern", datePattern);

        return result;
    }

    /**
     * @return the _con
     */
    public Connection getCon() {
        return _con;
    }

    /**
     * @param con the _con to set
     */
    public void setCon(Connection con) {
        this._con = con;
    }

    /**
     * @return the _stmt
     */
    public Statement getStmt() {
        return _stmt;
    }

    /**
     * @param stmt the _stmt to set
     */
    public void setStmt(Statement stmt) {
        this._stmt = stmt;
    }

    /**
     * @return the DATE_FORMAT_REGEXPS
     */
    public static Map<String, String> getDATE_FORMAT_REGEXPS() {
        return DATE_FORMAT_REGEXPS;
    }
}
