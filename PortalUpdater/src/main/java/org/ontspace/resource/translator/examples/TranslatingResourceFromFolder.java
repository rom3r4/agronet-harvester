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
package org.ontspace.resource.translator.examples;

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
import org.ontspace.agrisap.AgrisapQueryManager;
import org.ontspace.agrisap.owl.AgrisapQueryManagerImpl;
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.dc.DCQueryManager;
import org.ontspace.dc.owl.DCQueryManagerImpl;
import org.ontspace.dc.translator.DublinCore;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;
import org.ontspace.qdc.QDCQueryManager;
import org.ontspace.qdc.owl.QDCQueryManagerImpl;
import org.ontspace.qdc.translator.QualifiedDublinCore;
import org.ontspace.resource.ResourceQueryManager;
import org.ontspace.resource.owl.ResourceQueryManagerImpl;

/**
 * This class contains the code to create a new ont-space repository and
 * to translate and store some content items that are present in XML in a local
 * folder
 */
public class TranslatingResourceFromFolder {

    /**
     * The main method is the starting point of the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SECTION 1: Configuration
        String contentDirDC;
        String contentDirQDC;
        String contentDirAGRIS;

//        contentDir = System.getProperty("user.dir")
//                + System.getProperty("file.separator") + "etc"
//                + System.getProperty("file.separator") + "metadata";
        contentDirDC = "/home/abian/harvested_files_voa3r_formats/"
                + "2010_11_30_13_58/www.oceandocs.org_80_oai_dc/hdl_1834_1046/";

        contentDirQDC = "/home/abian/harvested_files_voa3r_formats/"
                + "2010_11_30_13_57/www.oceandocs.org_80_qdc/hdl_1834_1046/";

        contentDirAGRIS = "/home/abian/harvested_files_voa3r_formats/"
                + "2010_11_30_14_00/www.oceandocs.org_80_agris/hdl_1834_1046/";

        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";


        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;
        AgrisapQueryManager qmAGRIS = null;
        DCQueryManager qmDC = null;
        QDCQueryManager qmQDC = null;

        ResourceQueryManager qmRES = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            QMConfiguration qmConfAgris = confOntSpace.getQms().get(
                    QMConfiguration.QMType.AGRIS);
            QMConfiguration qmConfQDC = confOntSpace.getQms().get(
                    QMConfiguration.QMType.QDC);
            QMConfiguration qmConfDC = confOntSpace.getQms().get(
                    QMConfiguration.QMType.DC);
            QMConfiguration qmConfRES = confOntSpace.getQms().get(
                    QMConfiguration.QMType.RESOURCE);

            HashMap<String, Object> dcparams =
                    new HashMap<String, Object>();
            Iterator<String> qmSpecificOntsIt = qmConfDC.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, dcparams.get(specificOnt));
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            qmSpecificOntsIt = qmConfQDC.getOntologyUris().iterator();
            HashMap<String, Object> qdcparams =
                    new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, qdcparams.get(specificOnt));
            }


            HashMap<String, Object> agrisparams = new HashMap<String, Object>();
            qmSpecificOntsIt = qmConfAgris.getOntologyUris().iterator();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, agrisparams.get(specificOnt));
            }


            qmDC = (DCQueryManagerImpl) rep.getQueryManager(qmConfDC.
                    getJavaClass(),
                    dcparams);
            qmQDC = (QDCQueryManagerImpl) rep.getQueryManager(qmConfQDC.
                    getJavaClass(),
                    qdcparams);

            qmAGRIS = (AgrisapQueryManagerImpl) rep.getQueryManager(qmConfAgris.
                    getJavaClass(),
                    agrisparams);

            qmRES = (ResourceQueryManagerImpl) rep.getQueryManager(qmConfRES.
                    getJavaClass(),
                    params);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(TranslatingResourceFromFolder.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TranslatingResourceFromFolder.class.getName()).log(
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
        InputStream qdcXmlFile = null;
        InputStream agrisXmlFile = null;



        //Start to translate DC
        File dirDC = new File(contentDirDC);

        List<File> listXmlFilesDC = new ArrayList();
        if (dirDC.isDirectory()) {
            if (!dirDC.exists()) {
                System.out.println("Error: The Directory not exists");
            }
            File[] filesDC = dirDC.listFiles(xmlFilter);
            listXmlFilesDC.addAll(Arrays.asList(filesDC));
        }

        // Storing the content items in the ont-space repository
        for (int i = 0; i < listXmlFilesDC.size(); i++) {

            try {
                DublinCore dc = new DublinCore(listXmlFilesDC.get(i));
                dc.parseDCXML();
                MetadataRecordReference storeNewDC = qmDC.storeNewDublinCore(dc);
                System.out.println("LO id: " + storeNewDC.
                        getLocalMetadataRecordId());

            } catch (Exception ex) {
                Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                        log(
                        Level.SEVERE, null, ex);
            }
        }


        //Start to translate QDC
        File dirQDC = new File(contentDirQDC);

        List<File> listXmlFilesQDC = new ArrayList();
        if (dirQDC.isDirectory()) {
            if (!dirQDC.exists()) {
                System.out.println("Error: The Directory not exists");
            }
            File[] filesQDC = dirQDC.listFiles(xmlFilter);
            listXmlFilesQDC.addAll(Arrays.asList(filesQDC));
        }

        // Storing the content items in the ont-space repository
        for (int i = 0; i < listXmlFilesQDC.size(); i++) {
            String filePathQDC = listXmlFilesQDC.get(i).getAbsolutePath();


            try {
                qdcXmlFile = new FileInputStream(filePathQDC);
            } catch (Exception ex) {
                Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                        log(
                        Level.SEVERE, null, ex);
            }

            try {
                QualifiedDublinCore qdc = new QualifiedDublinCore();
                qdc = qdc.parseQDCXML(qdcXmlFile);
                MetadataRecordReference storeNewQDC = qmQDC.
                        storeNewQualifiedDublinCore(qdc);
                System.out.println("LO id: " + storeNewQDC.
                        getLocalMetadataRecordId());
                qdcXmlFile.close();

            } catch (Exception ex) {
                Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                        log(
                        Level.SEVERE, null, ex);
            }
        }


        //Start to translate AGRISAP
        File dirAGRIS = new File(contentDirAGRIS);

        List<File> listXmlFilesAGRIS = new ArrayList();
        if (dirAGRIS.isDirectory()) {
            if (!dirAGRIS.exists()) {
                System.out.println("Error: The Directory not exists");
            }
            File[] filesAGRIS = dirAGRIS.listFiles(xmlFilter);
            listXmlFilesAGRIS.addAll(Arrays.asList(filesAGRIS));
        }

        // Storing the content items in the ont-space repository
        for (int i = 0; i < listXmlFilesAGRIS.size(); i++) {
            String filePathAGRIS = listXmlFilesAGRIS.get(i).getAbsolutePath();

            try {
                Agrisap agris = new Agrisap(listXmlFilesAGRIS.get(i));
                agris.parseAgrisapXML();
                MetadataRecordReference storeNewAgris = qmAGRIS.storeNewAgrisap(
                        agris);
                System.out.println("LO id: " + storeNewAgris.
                        getLocalMetadataRecordId());

            } catch (Exception ex) {
                Logger.getLogger(TranslatingResourceFromFolder.class.getName()).
                        log(
                        Level.SEVERE, null, ex);
            }
        }

        //STORE OWLs FILEs
        String outDCFileName = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "output"
                + System.getProperty("file.separator") + "dc2owl.owl";
        String outQDCFileName = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "output"
                + System.getProperty("file.separator") + "qdc2owl.owl";
        String outAGRISFileName = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "output"
                + System.getProperty("file.separator") + "agrisap2owl.owl";

        File outDCFile =
                new File(outDCFileName);
        File outQDCFile =
                new File(outQDCFileName);
        File outAGRISFile =
                new File(outAGRISFileName);


        OutputStream outDCStream;
        OutputStream outQDCStream;
        OutputStream outAGRISStream;
        try {
            outDCStream = new FileOutputStream(outDCFile);
            outQDCStream = new FileOutputStream(outQDCFile);
            outAGRISStream = new FileOutputStream(outDCFile);
            qmDC.getOntologyModel("dc2owl").write(outDCStream, "RDF/XML");
            qmQDC.getOntologyModel("qdc2owl").write(outDCStream, "RDF/XML");
            qmAGRIS.getOntologyModel("agrisap2owl").write(outDCStream, "RDF/XML");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TranslatingResourceFromFolder.class.getName()).log(
                    Level.SEVERE, null, ex);
        }







        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
