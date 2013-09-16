
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
package org.ontspace.voa3rap4.owl.examples;

import java.io.File;
import org.ontspace.voa3rap2.owl.examples.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRecordReference;
import org.ontspace.MetadataRepository;
import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;
import org.ontspace.voa3rap2.Voa3rAP2QueryManager;
import org.ontspace.voa3rap2.owl.Voa3rAP2QueryManagerImpl;
import org.ontspace.voa3rap2.owl.Voa3rAP2QueryResultImpl;
import org.ontspace.voa3rap4.Voa3rAP4QueryManager;
import org.ontspace.voa3rap4.owl.Voa3rAP4QueryManagerImpl;
import org.ontspace.voa3rap4.translator.Vap4Agent;
import org.ontspace.voa3rap4.translator.Vap4MetaMetadata;
import org.ontspace.voa3rap4.translator.Vap4Research;
import org.ontspace.voa3rap4.translator.Voa3rAP4;

public class Voa3rAP4QueryRepositoryExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SECTION 1: Configuration

        String configFileOntSpace = "/home/link87/mycreate.xml";
        System.out.println("Preconfigured paramenters: ");
        System.out.println(" - Ont-space configuration file: "
                + configFileOntSpace);

        //SECTION 2: Repository and QueryManagers creation
        MetadataRepository rep = null;
        Voa3rAP4QueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(Voa3rAP2QueryRepositoryExample.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.VOA3RAP4);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (Voa3rAP4QueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(Voa3rAP2QueryRepositoryExample.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(Voa3rAP2QueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Voa3rAP2QueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());

        Voa3rAP4 vap4 = new Voa3rAP4(new File("/home/link87/job/VOA3R AP/VOA3R-level4-example.xml"));
        vap4.parseVoa3rAP4XML();
        System.out.println("Meta pre TDB: "+ vap4.getHasMetametadata());
        System.out.println("Research pre TDB: "+ vap4.getHasResearch());
        
        try{
            MetadataRecordReference vap4inTDB = qm.storeNewVoa3rAP4(vap4);
            String resid = vap4inTDB.getLocalMetadataRecordId();
            System.out.println("vap4 stored, id: "+ resid);
            ArrayList<Vap4MetaMetadata> metaList = qm.getHasMetametadata(resid);
            for (Vap4MetaMetadata meta: metaList) {
                System.out.println("Meta in TDB: " + meta.toString());
            }
            
            ArrayList<Vap4Research> resList = qm.getHasResearch(resid);
            for (Vap4Research res: resList) {
                System.out.println("Research in TDB: " + res.toString());
            }
            
            ArrayList<Vap4Agent> authors = qm.getAgentCreators(resid);
            for (Vap4Agent ag: authors) {
                System.out.println("Creator in TDB: " + ag.toString());
            }
            
            ArrayList<Vap4Agent> contributors = qm.getAgentContributors(resid);
            for (Vap4Agent con: contributors) {
                System.out.println("Contributor in TDB: " + con.toString());
            }
            
            ArrayList<Vap4Agent> publishers = qm.getAgentPublishers(resid);
            for (Vap4Agent pub: publishers) {
                System.out.println("Publisher in TDB: " + pub.toString());
            }
            
            System.out.println("URI contributors in TDB: " + qm.getContributors(resid));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error insertando vap4 en TDB");
        }
        
        
        
//        //SECTION 3: Get metadata
//        String loId = "http://voa3r.cc.uah.es/ontologies/voa3rap42owl#voa3rap4-a5e68336-7c0e-11e2-86af-63851e319ac3";
//        String lan = "en";
//
//        //DC
//        System.out.println("[DC]");
//        ArrayList<String> dCIdentifiers = qm.getIdentifiers(loId);
//        Iterator<String> it = dCIdentifiers.iterator();
//        while (it.hasNext()) {
//            System.out.println("Identifier: " + it.next());
//        }
//        HashMap<String, String> dCTitles = qm.getTitles(loId);
//        it = dCTitles.keySet().iterator();
//        while (it.hasNext()) {
//            System.out.println("Title: " + dCTitles.get(it.next()));
//
//        }
//
//        ArrayList<String> dCTitlesLang = qm.getTitles(loId, "en");
//        it = dCTitlesLang.iterator();
//        while (it.hasNext()) {
//            System.out.println("Title lang: " + it.next());
//
//        }
//
//        HashMap<String, String> dCDescriptions = qm.getDescriptions(loId);
//
//        it = dCDescriptions.keySet().iterator();
//        while (it.hasNext()) {
//            System.out.println("Description: " + dCDescriptions.get(it.next()));
//
//        }
//
//        HashMap<String, ArrayList<String>> dCSubjects = qm.getSubjects(loId);
//        it = dCSubjects.keySet().iterator();
//        while (it.hasNext()) {
//            System.out.println("Subject: " + dCSubjects.get(it.next()).toString());
//
//        }
//
//        ArrayList<String> dCSubjectsLang = qm.getSubjects(loId, "en");
//        it = dCSubjectsLang.iterator();
//        while (it.hasNext()) {
//            System.out.println("Subject lang en: " + it.next());
//
//        }
//
//        ArrayList<String> dCDates = qm.getDates(loId);
//        it = dCDates.iterator();
//        while (it.hasNext()) {
//            System.out.println("Date: " + it.next());
//        }
//        ArrayList<String> dCFormats = qm.getFormats(loId);
//        it = dCFormats.iterator();
//        while (it.hasNext()) {
//            System.out.println("Format: " + it.next());
//        }
//        ArrayList<String> dCLanguages = qm.getLanguages(loId);
//        it = dCLanguages.iterator();
//        while (it.hasNext()) {
//            System.out.println("Language: " + it.next());
//        }
//
//        ArrayList<String> dCContributors = qm.getContributors(loId);
//        it = dCContributors.iterator();
//        while (it.hasNext()) {
//            System.out.println("Contributor: " + it.next());
//        }
//
//        ArrayList<String> dCCoverages = qm.getCoverages(loId);
//        it = dCCoverages.iterator();
//        while (it.hasNext()) {
//            System.out.println("Coverage: " + it.next());
//        }
//        ArrayList<String> dCCreators = qm.getCreators(loId);
//        it = dCCreators.iterator();
//        while (it.hasNext()) {
//            System.out.println("Creator: " + it.next());
//        }
//        ArrayList<String> dCTypes = qm.getTypes(loId);
//        it = dCTypes.iterator();
//        while (it.hasNext()) {
//            System.out.println("Types: " + it.next());
//        }
//        ArrayList<String> dcSource = qm.getSources(loId);
//        it = dcSource.iterator();
//        while (it.hasNext()) {
//            System.out.println("Source: " + it.next());
//        }
//        
//        ArrayList<String> dcAgrovocTerms = qm.getAgrovocTerms(loId);
//        System.out.println("Agrovoctemrs list:" + dcAgrovocTerms);
//        it = dcAgrovocTerms.iterator();
//        while (it.hasNext()) {
//            System.out.println("Agrovoc term: " + it.next());
//        }
//
//        //QDC
//        System.out.println("[QDC]");
//
//        HashMap<String, String> qdcAlternative = qm.getTitleAlternative(loId);
//        it = qdcAlternative.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("[qdc]alternative[" + key + "]:" + qdcAlternative.get(key));
//        }
//
//        ArrayList<String> qdcBibliographicCitation = qm.getIdentifierBibliographicCitation(loId);
//        it = qdcBibliographicCitation.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]Bibliographic citation: " + it.next());
//        }
//
//        HashMap<String, String> qdcAbstract = qm.getDescriptionAbstract(loId);
//        it = qdcAbstract.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("[qdc]abstract[" + key + "]:" + qdcAbstract.get(key));
//        }
//
//        HashMap<String, String> qdcAccessRights = qm.getRightsAccessRights(loId);
//        it = qdcAccessRights.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("[qdc]access rights[" + key + "]:" + qdcAccessRights.get(key));
//        }
//
//        HashMap<String, String> qdcLicense = qm.getRightsLicense(loId);
//        it = qdcLicense.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("[qdc]license[" + key + "]:" + qdcLicense.get(key));
//        }
//
//        ArrayList<String> qdcConformsto = qm.getConformsTo(loId);
//        it = qdcConformsto.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]Conforms to: " + it.next());
//        }
//
//        ArrayList<String> qdcReferences = qm.getReferences(loId);
//        it = qdcReferences.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]References: " + it.next());
//        }
//        ArrayList<String> qdcIsReferencedBy = qm.getIsReferencedBy(loId);
//        it = qdcIsReferencedBy.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]is referenced by: " + it.next());
//        }
//        ArrayList<String> qdcHasPart = qm.getHasPart(loId);
//        it = qdcHasPart.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]has part: " + it.next());
//        }
//
//        ArrayList<String> qdcIsPartOf = qm.getIsPartOf(loId);
//        it = qdcIsPartOf.iterator();
//        while (it.hasNext()) {
//            System.out.println("[qdc]is part of: " + it.next());
//        }
//        //marcrel
//        System.out.println("[MARCREL]");
//
//        ArrayList<String> marcrelEdt = qm.getEdt(loId);
//        it = marcrelEdt.iterator();
//        while (it.hasNext()) {
//            System.out.println("[marcrel]edt: " + it.next());
//        }
//        
//
//        ArrayList<String> marcrelRev = qm.getRev(loId);
//        it = marcrelRev.iterator();
//        while (it.hasNext()) {
//            System.out.println("[marcrel]rev: " + it.next());
//        }
//        
//        ArrayList<String> marcrelTrl = qm.getTrl(loId);
//        it = marcrelTrl.iterator();
//        while (it.hasNext()) {
//            System.out.println("[marcrel]trl: " + it.next());
//        }
//
//        //ese
//        System.out.println("[ESE]");
//
//        ArrayList<String> eseIsShownBy = qm.getIsShownBy(loId);
//        it = eseIsShownBy.iterator();
//        while (it.hasNext()) {
//            System.out.println("[ese]isshownby: " + it.next());
//        }
//
//        ArrayList<String> eseIsShownAt = qm.getIsShownAt(loId);
//        it = eseIsShownAt.iterator();
//        while (it.hasNext()) {
//            System.out.println("[ese]isshownat: " + it.next());
//        }
//
//        //voa3r
//        System.out.println("[VOA3R]");
//
//        ArrayList<String> voa3rReviewStatus = qm.getReviewStatus(loId);
//        it = voa3rReviewStatus.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]Review status: " + it.next());
//        }
//
//        ArrayList<String> voa3rPublicationStatus = qm.getPublicationStatus(loId);
//        it = voa3rPublicationStatus.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]Publication status: " + it.next());
//        }
//
//        ArrayList<String> voa3rObjectOfInterest = qm.getObjectOfInterest(loId);
//        it = voa3rObjectOfInterest.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]Object of interest: " + it.next());
//        }
//
//        ArrayList<String> voa3rMeasuresVariable = qm.getMeasuresVariable(loId);
//        it = voa3rMeasuresVariable.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]Measures variable: " + it.next());
//        }
//
//        ArrayList<String> voa3rUsesMethod = qm.getUsesMethod(loId);
//        it = voa3rUsesMethod.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]uses method: " + it.next());
//        }
//
//        ArrayList<String> voa3rUsesProtocol = qm.getUsesProtocol(loId);
//        it = voa3rUsesProtocol.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]uses Protocol: " + it.next());
//        }
//
//        ArrayList<String> voa3rUsesInstrument = qm.getUsesInstrument(loId);
//        it = voa3rUsesInstrument.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]uses Instrument: " + it.next());
//        }
//
//        ArrayList<String> voa3rUsesTechnique = qm.getUsesTechnique(loId);
//        it = voa3rUsesTechnique.iterator();
//        while (it.hasNext()) {
//            System.out.println("[voa3r]uses Technique: " + it.next());
//        }
//
//
//        //SECTION 4: Query for resources
//        System.out.println("\n\nQUERIES:");
//        Voa3rAP2QueryResultImpl result = qm.queryVoa3rAP2ByTitle("Organic");
//        System.out.println("id: " +result.getMetadataRecordReferences().get(0).getLocalMetadataRecordId());
//
//        System.out.println("QUERY by agrovoc term: ");
//        ArrayList<String> agrovocTerms = new ArrayList<String>();
//        agrovocTerms.add("http://aims.fao.org/aos/agrovoc/c_6599");
//        System.out.println("agrovoc terms: "+ agrovocTerms);
//        Voa3rAP2QueryResultImpl result2 = qm.queryVoa3rAP2ByAgrovocTerm(agrovocTerms, 10, 0);
//        System.out.println("num res: " + result2.getTotalResults());
//        for (MetadataRecordReference mrr : result2.getMetadataRecordReferences())
//        {
//            System.out.println("id: " +mrr.getLocalMetadataRecordId());
//        }
        
        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
