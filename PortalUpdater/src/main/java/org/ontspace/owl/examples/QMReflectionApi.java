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
package org.ontspace.owl.examples;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRepository;
import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;

/**
 * This class contains the code to create a new ont-space repository
 */
public class QMReflectionApi {

    /**
     * The main method is the starting point of the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SECTION 1: Configuration
        String contentDir;
        contentDir = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "metadata";
        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";
        System.out.println("Preconfigured paramenters: ");
        System.out.println(" - contentDir: " + contentDir);
        System.out.println(" - Ont-space configuration file: "
                + configFileOntSpace);

        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(CreateNewRepository.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }
            System.out.println("Public repository URI: "
                    + rep.getRepositoryURI());

            //QMs creation
            HashMap<String, Object> params = new HashMap<String, Object>(confOntSpace.
                    getOntologies());

            HashMap<QMConfiguration.QMType, Class> allQMClasses =
                    new HashMap<QMConfiguration.QMType, Class>();
            HashMap<QMConfiguration.QMType, Object> allQMObjects =
                    new HashMap<QMConfiguration.QMType, Object>();
            HashMap<QMConfiguration.QMType, QMConfiguration> allQMConfs = confOntSpace.
                    getQms();
            Iterator<QMConfiguration.QMType> qmconfsIt = allQMConfs.keySet().
                    iterator();

            while (qmconfsIt.hasNext()) {
                QMConfiguration.QMType type = qmconfsIt.next();
                QMConfiguration qmConf = allQMConfs.get(type);
                Class qmi = Class.forName(qmConf.getJavaClass());
                Object retobj;
                Class[] parType = new Class[2];
                Object[] argList = new Object[2];
                // QueryManager type to instantiate
                parType[0] = MetadataRepositoryImpl.class;
                parType[1] = HashMap.class;

                Constructor ct = qmi.getConstructor(parType);

                argList[0] = rep;
                argList[1] = params;

                // Instantiates the new QueryManager
                retobj = ct.newInstance(argList);

                allQMClasses.put(type, qmi);
                allQMObjects.put(type, retobj);
            }

            //Accessing to the QM
            Iterator<QMConfiguration.QMType> createdQMs = allQMObjects.keySet().
                    iterator();

            int cont = 0;
            while (createdQMs.hasNext()) {
                System.out.println("QM number " + cont + ": ");
                QMConfiguration.QMType type = createdQMs.next();

                System.out.println(" - Java class: " + allQMClasses.get(type).
                        getCanonicalName());
                //invoke method to get the required ontologies
                Method requiredOnts = allQMClasses.get(type).getMethod(
                        "getRequiredOntologies");
                List<URI> uriList2 = (List<URI>) requiredOnts.invoke(allQMObjects.
                        get(type));
                System.out.println(" - Stored ontlogies: ");
                Iterator<URI> storedURIsIt2 = uriList2.iterator();
                while (storedURIsIt2.hasNext()) {
                    System.out.println("   " + storedURIsIt2.next().toString());
                }

                cont++;
            }

            //SECTION 3: Closing the ont-space repository
            rep.close();
            
        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QMReflectionApi.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
