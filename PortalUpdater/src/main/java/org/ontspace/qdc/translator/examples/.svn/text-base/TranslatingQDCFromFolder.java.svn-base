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
package org.ontspace.qdc.translator.examples;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRecordReference;
import org.ontspace.MetadataRepository;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;
import org.ontspace.qdc.QDCQueryManager;
import org.ontspace.qdc.owl.QDCQueryManagerImpl;

import org.ontspace.qdc.translator.QualifiedDublinCore;

/**
 * This class contains the code to create a new ont-space repository and
 * to translate and store some content items that are present in XML in a local
 * folder
 */
public class TranslatingQDCFromFolder {

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
        contentDir = "/home/raquel/msicilia/harvested_files_voa3r/"
                + "2010_11_30_13_57/www.oceandocs.org_80_qdc/hdl_1834_1046/";

        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";
        System.out.println("Preconfigured paramenters: ");
        System.out.println(" - contentDir: " + contentDir);
        System.out.println(" - Ont-space configuration file: "
                + configFileOntSpace);

        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;
        QDCQueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(TranslatingQDCFromFolder.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.QDC);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (QDCQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(TranslatingQDCFromFolder.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(TranslatingQDCFromFolder.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TranslatingQDCFromFolder.class.getName()).log(
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
        InputStream dcXmlFile = null;


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
            String filePath = listXmlFiles.get(i).getAbsolutePath();


            try {
                dcXmlFile = new FileInputStream(filePath);
            } catch (Exception ex) {
                Logger.getLogger(TranslatingQDCFromFolder.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            try {
                QualifiedDublinCore qdc = new QualifiedDublinCore();
                qdc = qdc.parseQDCXML(dcXmlFile);
                MetadataRecordReference storeNewDublinCore = qm.storeNewQualifiedDublinCore(qdc);
                //   System.out.println("LO id: " + storeNewDublinCore.getLocalMetadataRecordId());
                dcXmlFile.close();







            } catch (Exception ex) {
                Logger.getLogger(TranslatingQDCFromFolder.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }



        String outFileName = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "output"
                + System.getProperty("file.separator")
                + "qdc2owl.owl";
        File outFile =
                new File(outFileName);
        OutputStream outStream;
        try {
            outStream = new FileOutputStream(outFile);
            qm.getOntologyModel("qdc2owl").write(outStream, "RDF/XML");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TranslatingQDCFromFolder.class.getName()).log(Level.SEVERE, null, ex);
        }


//        ExtendedIterator it= qm.getOntologyModel("qdc2owl").listIndividuals();

//
//        Property propRes = new PropertyImpl("http://voa3r.cc.uah.es/ontologies/dc2owl#title");
//        while (it.hasNext()){
//            Individual ind= (Individual) it.next();
//            System.out.println("Instancia "+ind.getLocalName().toString());
//            //System.out.println("Propiedades "+ind..listProperties().toList().toString());
//
//        }
//

        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
