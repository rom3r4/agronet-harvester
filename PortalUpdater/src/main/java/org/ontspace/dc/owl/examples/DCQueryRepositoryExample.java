
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
package org.ontspace.dc.owl.examples;

import java.util.ArrayList;
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
import org.ontspace.dc.DCQueryManager;
import org.ontspace.dc.owl.DCQueryManagerImpl;
import org.ontspace.dc.owl.DCQueryResultImpl;

public class DCQueryRepositoryExample {

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
        DCQueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(DCQueryRepositoryExample.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.DC);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (DCQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(DCQueryRepositoryExample.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(DCQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DCQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());

        //SECTION 3: Get metadata
        String loId = "http://voa3r.cc.uah.es/ontologies/dc2owl#dc-7ce8d737-7369-11e1-bcf3-23bbce64d2b6";
        String lan = "en";
        ArrayList<String> dCIdentifiers = qm.getIdentifiers(loId);
        Iterator<String> it = dCIdentifiers.iterator();
        while (it.hasNext()) {
            System.out.println("Identifier: " + it.next());
        }
        HashMap<String, String> dCTitles = qm.getTitles(loId);
        it = dCTitles.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Title: " + dCTitles.get(it.next()));

        }
        
        ArrayList<String> dCTitlesLang = qm.getTitles(loId, "en");
        it = dCTitlesLang.iterator();
        while (it.hasNext()) {
            System.out.println("Title lang: " + it.next());

        }

        HashMap<String, String> dCDescriptions = qm.getDescriptions(loId);

        it = dCDescriptions.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Description: " + dCDescriptions.get(it.next()));

        }

        HashMap<String, ArrayList<String>> dCSubjects = qm.getSubjects(loId);
        it = dCSubjects.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Subject: " + dCSubjects.get(it.next()).toString());

        }
        
         ArrayList<String> dCSubjectsLang = qm.getSubjects(loId, "en");
        it = dCSubjectsLang.iterator();
        while (it.hasNext()) {
            System.out.println("Subject lang: " + it.next());

        }
        
        ArrayList<String> dCDates = qm.getDates(loId);
        it = dCDates.iterator();
        while (it.hasNext()) {
            System.out.println("Date: " + it.next());
        }
        ArrayList<String> dCFormats = qm.getFormats(loId);
        it = dCFormats.iterator();
        while (it.hasNext()) {
            System.out.println("Format: " + it.next());
        }
        ArrayList<String> dCLanguages = qm.getLanguages(loId);
        it = dCLanguages.iterator();
        while (it.hasNext()) {
            System.out.println("Language: " + it.next());
        }

        ArrayList<String> dCContributors = qm.getContributors(loId);
        it = dCContributors.iterator();
        while (it.hasNext()) {
            System.out.println("Contributor: " + it.next());
        }

        ArrayList<String> dCCoverages = qm.getCoverages(loId);
        it = dCCoverages.iterator();
        while (it.hasNext()) {
            System.out.println("Coverage: " + it.next());
        }
        ArrayList<String> dCCreators = qm.getCreators(loId);
        it = dCCreators.iterator();
        while (it.hasNext()) {
            System.out.println("Creator: " + it.next());
        }
        ArrayList<String> dCTypes = qm.getTypes(loId);
        it = dCTypes.iterator();
        while (it.hasNext()) {
            System.out.println("Types: " + it.next());
        }
        ArrayList<String> dcSource = qm.getSources(loId);
        it = dcSource.iterator();
        while (it.hasNext()) {
            System.out.println("Source: " + it.next());
        }
        

        //SECTION 4: Query for resources
        DCQueryResultImpl result = qm.queryDCByTitle("Ekologisk");

        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
