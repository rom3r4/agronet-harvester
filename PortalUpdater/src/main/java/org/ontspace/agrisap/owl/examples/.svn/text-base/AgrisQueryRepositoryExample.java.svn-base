
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
package org.ontspace.agrisap.owl.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRecordReference;
import org.ontspace.MetadataRepository;
import org.ontspace.agrisap.owl.AgrisapQueryManagerImpl;
import org.ontspace.agrisap.owl.AgrisapQueryResultImpl;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;

public class AgrisQueryRepositoryExample {

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
        AgrisapQueryManagerImpl qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(AgrisQueryRepositoryExample.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.AGRIS);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (AgrisapQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(AgrisQueryRepositoryExample.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(AgrisQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AgrisQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());

        //SECTION 3: Get metadata
        String loId1 = "http://voa3r.cc.uah.es/ontologies/agrisap2owl#agrisap-d9bc4298-7817-11e1-b009-d5dc507feb1f";
        
        String loId = "http://voa3r.cc.uah.es/ontologies/agrisap2owl#agrisap-d9f68c85-7817-11e1-b009-d5dc507feb1f";
        String lan = "en";
        
        
        Iterator<String> it;
//        ArrayList<String> dCTitles = qm.getDCTitles(loId);
//         it = dCTitles.iterator();
//        while (it.hasNext()) {
//            System.out.println("Title: " + it.next());
//
//        }
//        
//        ArrayList<String> qDCTitleA = qm.getQDCTitleAlternative(loId);
//        it = qDCTitleA.iterator();
//        while (it.hasNext()) {
//            System.out.println("TitleA: " + it.next());
//
//        }
//        
//        ArrayList<String> qdcAbstracts = qm.getQDCAbstract(loId);
//        it = qdcAbstracts.iterator();
//        while (it.hasNext()) {
//            System.out.println("Abstract: " + it.next());
//
//        }
//        ArrayList<String> dCIdentifiers = qm.getDCIdentifiers(loId1);
//        it = dCIdentifiers.iterator();
//        while (it.hasNext()) {
//            System.out.println("Identifier: " + it.next());
//
//        }
//        ArrayList<String> qDCDates = qm.getQDCDates(loId);
//        it = qDCDates.iterator();
//        while (it.hasNext()) {
//            System.out.println("Date: " + it.next());
//
//        }
//        ArrayList<String> agrisCreators = qm.getAgrisCreators(loId);
//        it = agrisCreators.iterator();
//        while (it.hasNext()) {
//            System.out.println("Creator: " + it.next());
//
//        }
//       
//        ArrayList<String> agrisSubjectClassifications = qm.getAgrisSubjectClassification(loId);
//        it = agrisSubjectClassifications.iterator();
//        while (it.hasNext()) {
//            System.out.println("Classification: " + it.next());
//
//        }
//        ArrayList<String> agrisSubjectThesauri = qm.getAgrisSubjectThesauri(loId);
//        it = agrisSubjectThesauri.iterator();
//        while (it.hasNext()) {
//            System.out.println("Thesaurus: " + it.next());
//
//        }

        //SECTION 4: Query for resources
        ArrayList<String> thesaurus = new ArrayList<String>();
        thesaurus.add("http://aims.fao.org/aos/agrovoc/c_1939");
//        thesaurus.add("http://aims.fao.org/aos/agrovoc/c_33237");
        AgrisapQueryResultImpl queryAgrisapBySubjectThesaurus = qm.queryAgrisapBySubjectThesaurus(thesaurus, 3, 0);

        //SECTION 5: Closing the ont-space repository
        ArrayList<MetadataRecordReference> metadataRecordReference = queryAgrisapBySubjectThesaurus.getMetadataRecordReferences();

        for (Iterator<MetadataRecordReference> itMRR = metadataRecordReference.iterator(); itMRR.hasNext();) {
            System.out.println("Resource ID:" + itMRR.next().getLocalMetadataRecordId());
        }
        long totalResults = queryAgrisapBySubjectThesaurus.getTotalResults();

        System.out.println("Total results: "+totalResults);
        rep.close();
    }
}
