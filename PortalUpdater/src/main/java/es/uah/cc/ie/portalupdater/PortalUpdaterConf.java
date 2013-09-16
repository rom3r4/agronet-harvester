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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Configuration class used to obtain the parameters stored in the configuration
 * file.
 */
public class PortalUpdaterConf {
    /*Example configuration file
    <cmsupdater>
    <joomlaMysql>
    <mysqlURI>jdbc:mysql://localhost/joomla_voa3r</mysqlURI>
    <user>root</user>
    <password>tragasables</password>
    </joomlaMysql>
    <harvestingFolder>/home/abian/harvested_files_voa3r/</harvestingFolder>
    OR
    <harvestingFolder>{HOME}/harvested_files_voa3r/</harvestingFolder>
    </cmsupdater>
     */

    private String _mysqlURI = null;
    private String _mysqlUser = null;
    private String _mysqlPassword = null;
    private String _cerifURI = null;
    private String _cerifUser = null;
    private String _cerifPassword = null;
    private File _harvestingFolder = null;
    private File _faoAgrisFolder = null;
    private File _ontspaceConf = null;

    /**
     * Creates a new instance of the CMSUpdater Configuration
     * @param configurationFile Path to the configuration file
     */
    public PortalUpdaterConf(String configurationFile) {
        try {
            SAXBuilder builder = new SAXBuilder();
            File confFile = new File(configurationFile);
            Document doc = builder.build(confFile);

            Element root = doc.getRootElement();
            Element joomlaOptions = root.getChild("mysql");
            this._mysqlURI = joomlaOptions.getChildTextTrim("mysqlURI");
            this._mysqlUser = joomlaOptions.getChildTextTrim("user");
            this._mysqlPassword = joomlaOptions.getChildTextTrim("password");
            Element cerifOptions = root.getChild("cerif");
            this._cerifURI = cerifOptions.getChildTextTrim("mysqlURI");
            this._cerifUser = cerifOptions.getChildTextTrim("user");
            this._cerifPassword = cerifOptions.getChildTextTrim("password");
            
            String path = root.getChild("harvestingFolder").getTextTrim();
            if (path.startsWith("{HOME}")) {
                String user_home = System.getProperty("user.home");
                path = path.replace("{HOME}", user_home);
            }
            this._harvestingFolder = new File(path);

            path = root.getChild("ontspaceConf").getTextTrim();
            if (path.startsWith("{HOME}")) {
                String user_home = System.getProperty("user.home");
                path = path.replace("{HOME}", user_home);
            }
            this._ontspaceConf = new File(path);

            path = root.getChild("faoAgrisFolder").getTextTrim();
            if (path.startsWith("{HOME}")) {
                String user_home = System.getProperty("user.home");
                path = path.replace("{HOME}", user_home);
            }
            this._faoAgrisFolder = new File(path);

            if (!this._harvestingFolder.exists()) {
                throw new Exception("Configuration: The harvesting folder does "
                    + "not exist");
            } else if (!this._harvestingFolder.isDirectory()) {
                throw new Exception("Configuration: The harvesting folder is "
                    + "not a directory");
            } else if (!this._harvestingFolder.canWrite()) {
                throw new Exception("Configuration: The harvesting folder can "
                    + "not be writen by this application");
            }
            if (!this._faoAgrisFolder.exists()) {
                throw new Exception("Configuration: The FAO Agris folder does "
                    + "not exist");
            } else if (!this._faoAgrisFolder.isDirectory()) {
                throw new Exception("Configuration: The FAO Agris folder is "
                    + "not a directory");
            } else if (!this._faoAgrisFolder.canWrite()) {
                throw new Exception("Configuration: The FAO Agris folder can "
                    + "not be writen by this application");
            }
            
            /*
            if (!this._ontspaceConf.exists()) {
                throw new Exception("Configuration: The ont-space configuration "
                    + "file does not exist");
            } else if (!this._ontspaceConf.isFile()) {
                throw new Exception("Configuration: The ont-space configuration "
                    + "file is not a file");
            }
            */
            
            
        } catch (IOException ex) {
            Logger.getLogger(PortalUpdaterConf.class.getName()).log(Level.SEVERE,
                null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(PortalUpdaterConf.class.getName()).log(Level.SEVERE,
                null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PortalUpdaterConf.class.getName()).log(Level.SEVERE,
                null, ex);
        }
    }

    /**
     * Gets the the local folder which stores the harvested files
     * @return File Local folder
     */
    public File getHarvestingFolder() {
        return this._harvestingFolder;
    }

    /**
     * Gets the the local folder which stores the FAO Agris files
     * @return File Local folder with the FAO Agris files
     */
    public File getFaoAgrisFolder() {
        return this._faoAgrisFolder;
    }

    /**
     * Gets the mysql URI where is the content database
     * @return the _mysqlURI
     */
    public String getMysqlURI() {
        return _mysqlURI;
    }

    /**
     * Gets the mysql user of the server containing the database installation
     * @return the mysql user
     */
    public String getMysqlUser() {
        return _mysqlUser;
    }

    /**
     * Gets the mysql password corresponding to the mysql user
     * @return the mysql password
     */
    public String getMysqlPassword() {
        return _mysqlPassword;
    }

    /**
     * Gets the ontspace configuration file
     * @return the ontspace configuration file
     */
    public File getOntSpaceConfFile() {
        return _ontspaceConf;
    }
    
    /**
     * Gets the mysql URI where is the CERIF database
     * @return the _mysqlURI
     */
    public String getCerifURI() {
        return _cerifURI;
    }

    /**
     * Gets the mysql user of the server containing the CERIF database installation
     * @return the mysql user
     */
    public String getCerifUser() {
        return _cerifUser;
    }

    /**
     * Gets the mysql password corresponding to the CERIF mysql user
     * @return the mysql password
     */
    public String getCerifPassword() {
        return _cerifPassword;
    }
}