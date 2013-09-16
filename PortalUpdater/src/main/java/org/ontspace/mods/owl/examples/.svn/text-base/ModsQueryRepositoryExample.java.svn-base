
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
package org.ontspace.mods.owl.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRepository;
import org.ontspace.mods.ModsQueryManager;
import org.ontspace.mods.owl.ModsQueryManagerImpl;

import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;

public class ModsQueryRepositoryExample {

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
        ModsQueryManager qm = null;

        try {
            OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                    configFileOntSpace);

            try {
                rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(
                        confOntSpace);
            } catch (MetadataRepositoryConfigException ex1) {
                Logger.getLogger(ModsQueryRepositoryExample.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }

            HashMap<String, Object> params =
                    new HashMap<String, Object>(confOntSpace.getOntologies());
            QMConfiguration qmConf = confOntSpace.getQms().get(
                    QMConfiguration.QMType.MODS);
            Iterator<String> qmSpecificOntsIt = qmConf.getOntologyUris().
                    iterator();
            String specificOnt;
            HashMap<String, Object> qmParams = new HashMap<String, Object>();
            while (qmSpecificOntsIt.hasNext()) {
                specificOnt = qmSpecificOntsIt.next();
                qmParams.put(specificOnt, params.get(specificOnt));
            }
            System.out.println("QM java class: " + qmConf.getJavaClass());
            qm = (ModsQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), qmParams);

        } catch (MetadataRepositoryConfigException ex) {
            Logger.getLogger(ModsQueryRepositoryExample.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (OntologyNotInRepositoryException ex) {
            Logger.getLogger(ModsQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ModsQueryRepositoryExample.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        System.out.println("Public repository URI: " + rep.getRepositoryURI());

        //SECTION 3: Get metadata
        String loId = "http://voa3r.cc.uah.es/ontologies/mods2owl#mods-bda5bca6-79c1-11e1-ba22-9fe165b44868";
        String lan = "en";
        ArrayList<String> identifiers = qm.getIdentifiers(loId);
        Iterator<String> it = identifiers.iterator();
        while (it.hasNext()) {
            System.out.println("Identifier: " + it.next());
        }
        HashMap<String, String> titles = qm.getTitles(loId);
        it = titles.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Title: " + titles.get(it.next()));

        }
        

        HashMap<String, String> abstracts = qm.getAbstracts(loId);

        it = abstracts.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Description: " + abstracts.get(it.next()));

        }

        HashMap<String, String> subjects = qm.getSubjects(loId); 
        it = subjects.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("Subject: " + subjects.get(it.next()).toString());

        }
        
                
        ArrayList<String> dates = qm.getDates(loId);
        it = dates.iterator();
        while (it.hasNext()) {
            System.out.println("Date: " + it.next());
        }
        
        ArrayList<String> languages = qm.getLanguages(loId);
        it = languages.iterator();
        while (it.hasNext()) {
            System.out.println("Language: " + it.next());
        }

        ArrayList<String> contributors = qm.getContributors(loId);
        it = contributors.iterator();
        while (it.hasNext()) {
            System.out.println("Contributor: " + it.next());
        }

        ArrayList<String> creators = qm.getCreators(loId);
        it = creators.iterator();
        while (it.hasNext()) {
            System.out.println("Creator: " + it.next());
        }
        ArrayList<String> types = qm.getTypes(loId);
        it = types.iterator();
        while (it.hasNext()) {
            System.out.println("Types: " + it.next());
        }

        
        //SECTION 5: Closing the ont-space repository
        rep.close();
    }
}
