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
package org.ontspace.agrisap.translator.examples;

import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRecordReference;
import org.ontspace.MetadataRepository;
import org.ontspace.agrisap.AgrisapQueryManager;
import org.ontspace.agrisap.owl.AgrisapQueryManagerImpl;
import org.ontspace.agrisap.translator.Agrisap;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;

/**
 * This class contains the code to create a new ont-space repository and
 * to translate and store some content items that are present in XML in a local
 * folder
 */
public class TranslatingAgrisapFromFolder {

    /**
     * The main method is the starting point of the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SECTION 1: Configuration
        String contentDir;
//        contentDir = System.getProperty("user.dir")
//                + System.getProperty("file.separator") + "etc"
//                + System.getProperty("file.separator") + "metadata";
        contentDir = "/home/elena/msicilia/test_storing/agris";

        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";
        System.out.println("Preconfigured paramenters: ");
        System.out.println(" - contentDir: " + contentDir);
        System.out.println(" - Ont-space configuration file: "
                + configFileOntSpace);

        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;
        AgrisapQueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            //TODO: some configuration work must be done to avoid this "hardcodeo a saco"
            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.AGRIS);

            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (AgrisapQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(),
                    params);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());



        //SECTION 3: Reading the XML files with the metadata
        FileFilter xmlFilter = new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".xml"));
            }
        };

        File dir = new File(contentDir);

        List<File> listXmlFiles = new ArrayList();
        if (dir.isDirectory()) {
            if (!dir.exists()) {
                System.out.println("Error: The Directory not exists");
            }
            File[] files = dir.listFiles(xmlFilter);
            listXmlFiles.addAll(Arrays.asList(files));
        }



        //SECTION 4: Storing the content items in the ont-space repository
        for (int i = 0; i < listXmlFiles.size(); i++) {
          
            try {
                Agrisap agris = new Agrisap(listXmlFiles.get(i));
                agris.parseAgrisapXML();
                MetadataRecordReference storeNew = qm.storeNewAgrisap(agris);
                System.out.println("LO id: " + storeNew.getLocalMetadataRecordId());

            } catch (Exception ex) {
                Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).
                        log(
                        Level.SEVERE, null, ex);
            }
        }

//        ExtendedIterator it =
//                qm.getOntologyModel("agrisap2owl").listIndividuals();


//       // Property propRes = new PropertyImpl("http://voa3r.cc.uah.es/ontologies/agrisap2owl#title");
//        while (it.hasNext()){
//            Individual ind= (Individual) it.next();
//            System.out.println("Instancia "+ind.getLocalName().toString());
//            //System.out.println("Propiedades "+ind..listProperties().toList().toString());
//
//        }

        //STORE OWL FILE

//        String outFileName = System.getProperty("user.dir")
//                + System.getProperty("file.separator") + "etc"
//                + System.getProperty("file.separator") + "output"
//                + System.getProperty("file.separator")
//                + "agrisap2owl.owl";
//        File outFile =
//                new File(outFileName);
//        OutputStream outStream;
//        try {
//            outStream = new FileOutputStream(outFile);
//            qm.getOntologyModel("agrisap2owl").write(outStream, "RDF/XML");
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TranslatingAgrisapFromFolder.class.getName()).log(
//                    Level.SEVERE, null, ex);
//        }

        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
