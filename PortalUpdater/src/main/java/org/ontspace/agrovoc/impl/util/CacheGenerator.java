/*
ont-space - The ontology-based resource metadata repository
Copyright (c) 2006-2011, Information Eng. Research Unit - Univ. of Alcalá
http://www.cc.uah.es/ie
This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.
This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.
You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.ontspace.agrovoc.impl.util;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.ontspace.agrovoc.impl.AgrovocConcept;
import org.ontspace.agrovoc.ws.WSinvoker;
import org.ontspace.agrovoc.ws.WSparser;

/**
 * Agrovoc cache generator
 *
 */
public class CacheGenerator {

    private static ArrayList<String> _languages = new ArrayList<String>();
    private static LinkedList<String> _unprocessed = new LinkedList<String>();
    private static LinkedList<String> _processed = new LinkedList<String>();
    private static String _configurationFile = "etc/agrovoc_dbconf.xml";
    private static String _db_uri = null;
    private static String _db_user = null;
    private static String _db_password = null;
    private static String _db_schema = null;
    private static String _db_table = null;
    private static Connection _db;
    private static Statement _st;
    private static String _query;
    private static boolean _cleanRun = false;

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException,
            JDOMException, IOException, Exception {

        // Reads the database configuration
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(_configurationFile));
        Element root = doc.getRootElement();
        _db_uri = root.getChild("dbUri").getTextTrim();
        _db_user = root.getChild("dbUser").getTextTrim();
        _db_password = root.getChild("dbPassword").getTextTrim();
        _db_schema = root.getChild("dbSchema").getTextTrim();
        _db_table = root.getChild("dbTable").getTextTrim();

        // Languages generated in the cache
        _languages.add("ar");
        _languages.add("cs");
        _languages.add("de");
        _languages.add("en");
        _languages.add("es");
        _languages.add("fa");
        _languages.add("fr");
        _languages.add("hi");
        _languages.add("hu");
        _languages.add("it");
        _languages.add("ja");
        _languages.add("ko");
        _languages.add("lo");
        _languages.add("pl");
        _languages.add("pt");
        _languages.add("ru");
        _languages.add("sk");
        _languages.add("th");
        _languages.add("zh");

        // Creates the connection
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        _db = (Connection) DriverManager.getConnection(_db_uri,_db_user, _db_password);
        _st = (Statement) _db.createStatement();

        if (_cleanRun){
            // Drops the schema
            _query = "DROP SCHEMA IF EXISTS `" + _db_schema + "`;";
            _st.execute(_query);

            // Creates the schema
            _query = "CREATE SCHEMA IF NOT EXISTS `" + _db_schema + "` "
                        + "CHARACTER SET utf8 COLLATE utf8_general_ci;";
            _st.execute(_query);

            // Creates the table
            _query = "CREATE TABLE "
                + "`" + _db_schema + "`.`" + _db_table + "`"
                + " (`id` varchar(70) COLLATE utf8_spanish_ci NOT NULL,";
            for (String l:_languages){
                _query = _query + " `" + l + "` "
                        + "varchar(100) COLLATE utf8_spanish_ci NOT NULL,";
            }
            _query = _query + " PRIMARY KEY (`id`)"
                + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;";
            _st.execute(_query);
        }

        // Initializes the unprocessed list
        _unprocessed.addLast("http://aims.fao.org/aos/common/c_category");

        // Processes the list
        int i = 1;
        while (!_unprocessed.isEmpty()){

            // Gets the concept information
            String uri = _unprocessed.removeFirst();
            String id = uri.substring(uri.lastIndexOf("/")+1);
            boolean processElement = true;

            // Checks if the term is already in the database
            // SELECT COUNT(*) FROM `agrovoc_cache`.`terms_cache` WHERE `terms_cache`.`id`="ID"
            if (!_cleanRun){
                _query = "SELECT COUNT(*) FROM `" + _db_schema + "`.`"
                        + _db_table + "` WHERE `" + _db_table + "`.`id`=\""
                        + uri +"\"";
                ResultSet rs = _st.executeQuery(_query);
                rs.next();
                int result = rs.getInt(1);
                if (result == 1){
                    processElement = false;
                    System.out.println("#" + i++ + " EXISTS: " + uri);
                }
            }

            try{
                AgrovocConcept agrc = WSparser.parseSKOS(WSinvoker.getTermInfo(uri));

                if (processElement){
                    // Adds information to database
                    _query = "INSERT INTO `" + _db_schema + "`.`" + _db_table + "`"
                            + " VALUES (\"" + uri + "\",";
                    for (String l:_languages){
                        String label = agrc.getPrefLabel(l);
                        if (label == null || label.length() < 1){
                            label = id;
                        }
                        _query = _query + "\"" + label + "\",";
                    }
                    _query = _query.substring(0,_query.length()-1) + ");";
                    _st.execute(_query);
                    // Prints status information
                    System.out.println("#" + i++ + " ADDED: " + uri);
                }

                // Marks the element as processed
                _processed.addLast(uri);

                // Adds the unprocessed children for the element
                for (AgrovocConcept br:agrc.getBroader()){
                    String brUri = br.getAbout();
                    if (!_processed.contains(brUri) && !_unprocessed.contains(brUri)){
                        _unprocessed.addLast(brUri);
                    }
                }
                for (AgrovocConcept nr:agrc.getNarrower()){
                    String nrUri = nr.getAbout();
                    if (!_processed.contains(nrUri) && !_unprocessed.contains(nrUri)){
                        _unprocessed.addLast(nrUri);
                    }
                }
                for (Iterator iter = agrc.getRelated().entrySet().iterator(); iter.hasNext(); ){
                    AgrovocConcept tmp = (AgrovocConcept) ((Entry) iter.next()).getValue();
                    String rlUri = tmp.getAbout();
                    if (!_processed.contains(rlUri) && !_unprocessed.contains(rlUri)){
                        _unprocessed.addLast(rlUri);
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }

        }

    }
}
