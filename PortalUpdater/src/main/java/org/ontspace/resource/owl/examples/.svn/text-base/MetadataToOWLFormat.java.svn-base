
/*
ont-space - The ontology-based resource metadata repository
Copyright (c) 2006-2008, Information Eng. Research Unit - Univ. of Alcalá
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

package org.ontspace.resource.owl.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRepository;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;
import org.ontspace.resource.ResourceQueryManager;
import org.ontspace.resource.owl.ResourceQueryManagerImpl;



public class MetadataToOWLFormat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SECTION 1: Configuration
       
        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";
        System.out.println("Preconfigured paramenters: ");
        System.out.println(" - Ont-space configuration file: "
                + configFileOntSpace);

        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;
        ResourceQueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(MetadataToOWLFormat.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.RESOURCE);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (ResourceQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(MetadataToOWLFormat.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(MetadataToOWLFormat.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MetadataToOWLFormat.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());

        String outFileName = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "output"
                + System.getProperty("file.separator")
                + "resourceont.owl";
        File outFile =
                new File(outFileName);
        OutputStream outStream;
        try {
            outStream = new FileOutputStream(outFile);
            qm.getOntologyModel("resourceont").write(outStream, "RDF/XML");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetadataToOWLFormat.class.getName()).log(Level.SEVERE, null, ex);
        }

       // ExtendedIterator it = qm.getOntologyModel("resourceont").listIndividuals();


        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
