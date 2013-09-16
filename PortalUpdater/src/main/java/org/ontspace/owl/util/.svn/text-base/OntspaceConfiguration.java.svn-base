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
package org.ontspace.owl.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.ontspace.util.Configuration;

public class OntspaceConfiguration implements Configuration {

    /**
     * General parameters for the SDBModel
     */
    private SDBConfiguration _sdbConf = null;
    /**
     * General parameters for the TDBModel
     */
    private TDBConfiguration _tdbConf = null;
    /**
     * Public repository URI *
     */
    private String _publicRepURI = null;
    /**
     * ontologies with HashMap<URI,OWLFile> *
     */
    private HashMap<String, String> _ontologies = null;
    /*
     * List of Query Managers configured for this repository
     */
    private HashMap<QMConfiguration.QMType, QMConfiguration> _qms = null;

    /**
     * Creates a new instance of the OntspaceConfiguration
     *
     * @param configurationFile Path to the configuration file
     * @throws MetadataRepositoryConfigException
     */
    public OntspaceConfiguration(String configurationFile) throws
            MetadataRepositoryConfigException {
        /*
         * <ontspace> <TDBStorageModel> <folder>/home/abian/test/</folder> OR
         * <folder>{HOME}/test/</folder> </TDBStorageModel> <SDBStorageModel>
         * <dbURI>jdbc:mysql://127.0.0.1/ontspace_v2</dbURI> <user>root</user>
         * <password>tragasables</password> <dbType>MySQL</dbType>
         * <dbDriver>com.mysql.jdbc.Driver</dbDriver> </SDBStorageModel>
         * <publicRepURI>http://ont-space.org/rep</publicRepURI> <ontologies>
         * <ontology> <uri>http://www.cc.uah.es/ie/ont/dc2owl</uri>
         * <file>/home/abian/msicilia/svn/ont-space4VOA3R/etc/dc2owl.owl</file>
         * </ontology> </ontologies> </ontspace>
         */
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(configurationFile));
            Element root = doc.getRootElement();
            Element elem;
            boolean sdbStorage = false;
            boolean tdbStorage = true;


            //Read the TDB parameters
            elem = root.getChild("TDBStorageModel");
            try {
                elem = elem.getChild("folder");
                this._tdbConf = new TDBConfiguration(elem.getTextTrim());
                tdbStorage = true;
            } catch (Exception e) {
                tdbStorage = false;
                System.out.println(
                        "TDBStorage disabled in the configuration file");
            }

            //Read the SDB parameters
            elem = root.getChild("SDBStorageModel");
            String dbURI, user, password, engine;
            try {
                dbURI = elem.getChild("dbURI").getTextTrim();
                user = elem.getChild("user").getTextTrim();
                password = elem.getChild("password").getTextTrim();
                engine = elem.getChild("engine").getTextTrim();
                this._sdbConf = new SDBConfiguration(dbURI, user, password,
                        engine);
                sdbStorage = true;
            } catch (Exception e) {
                sdbStorage = false;
                System.out.println(
                        "SDBStorage disabled in the configuration file");
            }

            if ((sdbStorage == false) && (tdbStorage == false)) {
                throw new MetadataRepositoryConfigException(
                        "There is a problem in the configuration file with"
                        + " the TDB or SDB storage model.");
            }

            //Read the repository URI
            elem = root.getChild("publicRepURI");
            try {
                this._publicRepURI = elem.getTextTrim();
            } catch (Exception e) {
                throw new MetadataRepositoryConfigException(
                        "There is a problem in the configuration file with"
                        + " the public repository URI parameter.");
            }

            //Read the ontologies
            try {
                List<Element> ontologies = root.getChild("ontologies").
                        getChildren("ontology");
                this._ontologies = new HashMap<String, String>();
                String uri, ontLocation;
                String user_home = System.getProperty("user.home");
                for (Element ont : ontologies) {
                    uri = ont.getChild("uri").getTextTrim();
                    ontLocation = ont.getChild("file").getTextTrim();
                    if (ontLocation.startsWith("{HOME}")) {
                        ontLocation = ontLocation.replace("{HOME}", user_home);
                    }
                    this._ontologies.put(uri, ontLocation);
                }
            } catch (Exception e) {
                throw new MetadataRepositoryConfigException(
                        "There is a problem in the configuration file with"
                        + " the ontologies parameters.");
            }
            //Read the QM configuration
            try {
                List<Element> qmParams = root.getChildren("QMParam");
                this._qms =
                        new HashMap<QMConfiguration.QMType, QMConfiguration>();
                QMConfiguration qmConf;
                QMConfiguration.QMType type = null;
                String typeString, javaClass;
                ArrayList<String> ontologyUris;
                for (Element qm : qmParams) {
                    typeString = qm.getAttributeValue("type");
                    if (typeString.equalsIgnoreCase("navigation")) {
                        type = QMConfiguration.QMType.NAVIGATION;
                    } else if (typeString.equalsIgnoreCase("resource")) {
                        type = QMConfiguration.QMType.RESOURCE;
                    } else if (typeString.equalsIgnoreCase("dc")) {
                        type = QMConfiguration.QMType.DC;
                    } else if (typeString.equalsIgnoreCase("qdc")) {
                        type = QMConfiguration.QMType.QDC;
                    } else if (typeString.equalsIgnoreCase("agris")) {
                        type = QMConfiguration.QMType.AGRIS;
                    } else if (typeString.equalsIgnoreCase("mods")) {
                        type = QMConfiguration.QMType.MODS;
                    } else if (typeString.equalsIgnoreCase("voa3rap2")) {
                        type = QMConfiguration.QMType.VOA3RAP2;
                    } else if (typeString.equalsIgnoreCase("voa3rap4")) {
                        type = QMConfiguration.QMType.VOA3RAP4;
                    }
                    if (type == null) {
                        throw new MetadataRepositoryConfigException(
                                "There is a problem in the configuration file with"
                                + " the Query Manager type attribute.");
                    }
                    javaClass = qm.getChild("QMClass").getTextTrim();
                    ontologyUris = new ArrayList<String>();
                    List<Element> qmUris = qm.getChild("QMOntologies").
                            getChildren("uri");
                    for (Element uri : qmUris) {
                        ontologyUris.add(uri.getTextTrim());
                    }
                    qmConf = new QMConfiguration(type, javaClass, ontologyUris);
                    Element confFilePathElement = qm.getChild("navConf");
                    if (confFilePathElement != null) {
                        String confFilePath = confFilePathElement.getTextTrim();
                        if (confFilePath.startsWith("{HOME}")) {
                            String user_home = System.getProperty("user.home");
                            confFilePath = confFilePath.replace("{HOME}",
                                    user_home);
                        }
                        qmConf.setConfFilePath(confFilePath);
                    }
                    this._qms.put(type, qmConf);
                }
            } catch (Exception e) {
                throw new MetadataRepositoryConfigException(
                        "There is a problem in the configuration file with"
                        + " the Query Manager parameters.");
            }

        } catch (JDOMException ex) {
            Logger.getLogger(OntspaceConfiguration.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OntspaceConfiguration.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Obtain the SDB Configuration parameters
     *
     * @return the SDB configuration parameters
     */
    public SDBConfiguration getSdbConf() {
        return _sdbConf;
    }

    /**
     * Obtain the TDB Configuration parameters
     *
     * @return the TDB configuration parameters
     */
    public TDBConfiguration getTdbConf() {
        return _tdbConf;
    }

    /**
     * Obtain the pubic Repository URI
     *
     * @return the public Repository URI
     */
    public String getPublicRepURI() {
        return _publicRepURI;
    }

    /**
     * Obtain the ontologies URIs plus locations
     *
     * @return the ontologies information
     */
    public HashMap<String, String> getOntologies() {
        return _ontologies;
    }

    /**
     * Obtain the Query Manager parameters
     *
     * @return the _qms
     */
    public HashMap<QMConfiguration.QMType, QMConfiguration> getQms() {
        return _qms;
    }
}
