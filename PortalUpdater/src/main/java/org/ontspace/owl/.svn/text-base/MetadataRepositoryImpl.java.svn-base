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
package org.ontspace.owl;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBException;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.MySQLEngineType;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.DatabaseType;
import com.hp.hpl.jena.sdb.store.LayoutType;
import com.hp.hpl.jena.sdb.util.StoreUtils;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRepository;
import org.ontspace.QueryManager;
import org.ontspace.Session;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.SDBConfiguration;

/**
 * Defines the basic interface of ont-space repositories.
 */
public class MetadataRepositoryImpl implements MetadataRepository {

    private String _publicRepURI = null;
    private Model _modelTDB = null;
    private HashMap<String, OntModel> _ontModels = null;
    //TODO
    private Model _modelSDB = null;
    private Store _store = null;

    /**
     * @param params The params needed to create repository
     * @param create Boolean value to know if the repository has been created
     * previously
     * @throws org.ont-space.owl.util.MetadataRepositoryConfigException
     */
    MetadataRepositoryImpl(OntspaceConfiguration conf)
        throws MetadataRepositoryConfigException {

        this._publicRepURI = conf.getPublicRepURI();
        this._ontModels = new HashMap<String, OntModel>();

        HashMap<String, String> ontologies = conf.getOntologies();
        Iterator<String> ontIt = ontologies.keySet().iterator();

        //Check if the model must me TDB or SDB
        if (conf.getTdbConf() != null) {
            String directory, modelName;
            directory = conf.getTdbConf().getFolder();

            Model auxModel;
            OntModel auxOntModel;

            while (ontIt.hasNext()) {
                String uri = ontIt.next();
                String ontFile = ontologies.get(uri);
                modelName =
                    uri.substring(uri.lastIndexOf("/") + 1, uri.length());
                this._modelTDB = TDBFactory.createNamedModel(modelName,
                    directory);
                if (this._modelTDB == null) {
                    throw new MetadataRepositoryConfigException(
                        "Error while creating the TDBmodel with the name "
                        + modelName);
                }
                loadSchema(this._modelTDB, ontFile);

                //this is the only way of obtaining one specific ontology stored
                //   in a TDB model
                //Each ontology must have a name to access to it in the future
//                System.out.println("CREATING auxONTMODEL: "+ modelName);
                auxModel = TDBFactory.createNamedModel(modelName, directory);
//                System.out.println("CREATING auxONTMODEL: "+ auxModel);
                auxOntModel = ModelFactory.createOntologyModel(
                    OntModelSpec.OWL_MEM, auxModel);
//                System.out.println("CREATING auxONTMODEL: "+ auxOntModel);
                this._ontModels.put(modelName, auxOntModel);
            }


        } else {
            //SDB creation
            SDBConfiguration sdbConf = conf.getSdbConf();
            StoreDesc storeDesc = new StoreDesc(LayoutType.LayoutTripleNodesHash,
                DatabaseType.MySQL);
            if (sdbConf.getEngine().equalsIgnoreCase("InnoDB")) {
                storeDesc.engineType = MySQLEngineType.InnoDB;
            } else {
                storeDesc.engineType = MySQLEngineType.MyISAM;
            }
            JDBC.loadDriverMySQL();
            SDBConnection conn = null;
            /*
            Hack...
            StoreUtils.isFormatted(_store) is not very good for performance, so
            I am using a previous test to see if the schema exists in the
            database.
            1- If the schema does not exist, it is the first time to open the
            repository, so we are creating it and we can use .create() and .trucate()
            2- If the schema exists, it can be previously added by hand using
            mysql admin so we are not sure if it is properly formatted. In this case
            I am using StoreUtils.isFormatted(_store). If the answer is no, I am
            cleaning the repository creating a new one.
             */
            boolean createRepository = true;
            try {
                //If the DB schema does not exist an exception is launched
                conn = new SDBConnection(sdbConf.getDbURI(), sdbConf.getUser(),
                    sdbConf.getPassword());
                createRepository = false;
            } catch (SDBException e) {
                createSchema(sdbConf.getDbURI(), sdbConf.getUser(), sdbConf.
                    getPassword());
                conn = new SDBConnection(sdbConf.getDbURI(), sdbConf.getUser(),
                    sdbConf.getPassword());
                createRepository = true;
            }
            _store = SDBFactory.connectStore(conn, storeDesc);

            /*
            _store.getTableFormatter().create() creates the tables
            _store.getTableFormatter().truncate() erases the content
             */
            if (createRepository) {
                _store.getTableFormatter().create();
                _store.getTableFormatter().truncate();
            } else {
                try {
                    if (!StoreUtils.isFormatted(_store)) {
                        _store.getTableFormatter().create();
                        _store.getTableFormatter().truncate();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MetadataRepositoryImpl.class.getName()).
                        log(Level.SEVERE, null, ex);
                }
            }

            _modelSDB = SDBFactory.connectDefaultModel(_store);
            if (_modelSDB == null) {
                throw new MetadataRepositoryConfigException(
                    "Error while creating the SDBmodel.");
            }

            while (ontIt.hasNext()) {
                String uri = ontIt.next();
                String ontFile = ontologies.get(uri);
                String modelName = uri.substring(uri.lastIndexOf("/") + 1, uri.
                    length());
                loadSchema(this._modelSDB, ontFile);

                //this is the only way of obtaining one specific ontology stored
                //   in a SDB model
                //Each ontology must have a name to access to it in the future
                OntModel ontologyModel = ModelFactory.createOntologyModel(
                    OntModelSpec.OWL_MEM, _modelSDB);
                //ERROR: the following line is not working
                OntModel auxOntModel = ontologyModel.getOntology(uri).
                    getOntModel();
                this._ontModels.put(modelName, auxOntModel);
            }
        }

    }

    /**
     * Close the repository and free up resources held
     */
    @Override
    public void close() {
        if (_modelTDB != null) {
            _modelTDB.close();
        }
        if (_modelSDB != null) {
            _modelSDB.close();
            _store.close();
        }
    }

    /**
     * Load the schema with model are stored in a specified path
     * @param path The path where are stored the model
     */
    private void loadSchema(Model model, String path) {
        //System.out.println("MetadataRepositoryImpl:"+path);
        //System.out.println("MetadataRepositoryImpl:"+model);
        FileManager.get().readModel(model, path);

    }

    /**
     * Gets a session for the given userId
     * @param userId The user identifier
     * @return The session for a given userId
     */
    @Override
    public Session getSessionId(String userId) {
        return new SessionImpl(this, userId);
    }

    /**
     * Gets a new anonymous session
     * @return The anonymous session
     */
    @Override
    public Session getAnonymousSession() {
        return new SessionImpl(this);
    }

    /**
     * Gets the URI of the repository
     * @return The URI of the repository
     */
    @Override
    public URI getRepositoryURI() {
        URI result = null;
        try {
            result = new URI(this._publicRepURI);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).
                log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Gets a new instance of the selected QueryManager.
     * @param type Specifies the type of QueryMananager
     * @return  A new instance QueryManager specified as type param
     * @throws OntologyNotInRepositoryException
     */
    @Override
    public QueryManager getQueryManager(String type)
        throws OntologyNotInRepositoryException {
        return getQueryManager(type, null);
    }

    /**
     * Gets a new instance of the selected QueryManager.
     * @param type Specifies the type of QueryMananager
     * @param params Additional parameters for the QueryManager invocation
     * @return  A new instance QueryManager specified as type param
     * @throws OntologyNotInRepositoryException
     */
    @Override
    public QueryManager getQueryManager(String type,
        HashMap<String, Object> params)
        throws OntologyNotInRepositoryException {
        List<URI> uriList = new ArrayList<URI>();
        Method requiredOnts, addOnt;
        Object retobj = null;
        // Creates the Query Manager
        try {
            // Loads the QueryManager class
            Class qmi = Class.forName(type);
            // Creates the instance of the QueryManager
            Class[] parType;
            Object[] argList;

            // Determines the number of parameters for the constructor
            if (params != null) {
                parType = new Class[2];
                argList = new Object[2];
            } else {
                parType = new Class[1];
                argList = new Object[1];
            }

            // QueryManager type to instantiate
            parType[0] = MetadataRepositoryImpl.class;

            // Adds the additional parameter (parameters HashMap)
            if (params != null) {
                parType[1] = HashMap.class;
            }
            Constructor ct = qmi.getConstructor(parType);

            MetadataRepositoryImpl rep = this;
            argList[0] = rep;
            if (params != null) {
                argList[1] = params;
            }
            // Instantiates the new QueryManager
            retobj = ct.newInstance(argList);

            //invoke method to get the required ontologies
            requiredOnts = qmi.getMethod("getRequiredOntologies");
            uriList = (List<URI>) requiredOnts.invoke(retobj);

            Iterator<String> ontNamesIt = _ontModels.keySet().iterator();
            String ontName;
            OntModel auxOntModel;
            while (ontNamesIt.hasNext()) {
                ontName = ontNamesIt.next();
                auxOntModel = _ontModels.get(ontName);
                //invoke method to add the ontology
                Class[] parAddOnt = new Class[2];
                Object[] ont = new Object[2];
                parAddOnt[0] = String.class;
                ont[0] = ontName;
                parAddOnt[1] = OntModel.class;
                ont[1] = auxOntModel;
                addOnt = qmi.getMethod("addOntologyRef", parAddOnt);
                addOnt.invoke(retobj, ont);
            }



            /* ADDITIONAL PARAMETERS FOR THE QUERY MANAGER
             * Checks wheter it is necessary or not to process additional
             * parameters in the instantiated QueryManager
             */
            if (params != null) {

                // Checks if there is a session to bind in the QueryManager
                Session sess = (Session) params.get("session");

                if (sess != null) {
                    Class[] parSessType = new Class[1];
                    Object[] parSess = new Object[1];
                    parSessType[0] = Session.class;
                    parSess[0] = sess;
                    Method bindToSession = qmi.getMethod("bindToSession",
                        parSessType);
                    bindToSession.invoke(retobj, parSess);
                }

                // Checks if there is a configuration file to read
                String confFilePath = (String) params.get("confFilePath");

                if (confFilePath != null) {

                    Class[] parConfType = new Class[1];
                    Object[] parConf = new Object[1];
                    parConfType[0] = String.class;
                    parConf[0] = confFilePath;
                    Method readConfigurationFile = qmi.getMethod(
                        "readConfigurationFile", parConfType);
                    readConfigurationFile.invoke(retobj, parConf);
                }
            }
            return (QueryManager) retobj;

        } catch (InstantiationException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE,
                null, ex);
        }

        return (QueryManager) retobj;
    }

    /**
     * Create the Database Schema if we are using SDB and the schema hasn't been
     * created in advance.
     * @param uri URL of the mysql connection
     * @param user DB user
     * @param passw DB password
     */
    private void createSchema(String uri, String user, String passw) {
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
                System.out.println("SCHEMA " + modelName
                    + "succesfully created.");
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).
                log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetadataRepositoryImpl.class.getName()).log(
                Level.SEVERE, null, ex);
        }

    }
}
