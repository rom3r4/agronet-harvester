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
package org.ontspace.nav.owl.util;

import com.hp.hpl.jena.ontology.OntResource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.ontspace.MetadataRepository;
import org.ontspace.nav.owl.NavigationalQueryManagerImpl;
import org.ontspace.nav.owl.NavigationalSessionImpl;
import org.ontspace.nav.owl.NavigationalSessionManager;
import org.ontspace.owl.MetadataRepositoryFactory;
import org.ontspace.owl.util.MetadataRepositoryConfigException;
import org.ontspace.owl.util.OntologyNotInRepositoryException;
import org.ontspace.owl.util.OntspaceConfiguration;
import org.ontspace.owl.util.QMConfiguration;

/**
 * Example on a simple console navigational interface
 *
 */
public class ConsoleNavigationalInterface {

    /**
     * Main method
     */
    public static void main(String[] args)
            throws MetadataRepositoryConfigException,
            OntologyNotInRepositoryException, IOException {

        // Creates a pseudorandom number generator for the session
        Random prng = new Random();
        // Generates the session identifier
        String sid = "ConsoleNavigationalInterface_"
                + String.valueOf(Math.abs(prng.nextLong()));
        // Creates an input for the user selections
        BufferedReader input =
                new BufferedReader(new InputStreamReader(System.in));

        // Configuration file location
        String configFileOntSpace = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "etc"
                + System.getProperty("file.separator") + "create.xml";

        MetadataRepository rep = null;
        NavigationalQueryManagerImpl qm = null;

        // Reads the configuration
        OntspaceConfiguration confOntSpace = new OntspaceConfiguration(
                configFileOntSpace);
        HashMap<String, Object> params =
                new HashMap<String, Object>(confOntSpace.getOntologies());
        // Gets specific parameters
        NavigationalSessionImpl sess = (NavigationalSessionImpl) NavigationalSessionManager.createSession(rep, sid);
        QMConfiguration qmConf = confOntSpace.getQms().get(
                QMConfiguration.QMType.NAVIGATION);
        rep = MetadataRepositoryFactory.createOrOpenMetadataRepository(confOntSpace);
        // Stores special parameters
        params.put("session", sess);
        params.put("confFilePath", qmConf.getConfFilePath());

        // Gets the query manager
        qm = (NavigationalQueryManagerImpl) rep.getQueryManager(qmConf.getJavaClass(), params);

        // Iterates while the user does not decide to exit
        boolean exit = false;
        while (!exit) {

            ArrayList<OntResource> ip =
                    (ArrayList<OntResource>) qm.getInterestPoints();
            System.out.println("|| Interest points so far ||");

            // Iterates through the interest points
            int i = 1;
            for (OntResource res : ip) {
                String resname = res.toString();
                System.out.println("\n" + i++ + "- " + resname + "\n");
                // Related elements
                int relSize =
                        qm.getRelatedOntResourcesSize(res);
                if (relSize > 0) {
                    ArrayList<OntResource> related = (ArrayList<OntResource>) qm.getRelatedOntResources(res);
                    for (OntResource rres : related) {
                        // Relations
                        List<String> relations = qm.getRelations(res, rres);
                        for (String relation : relations) {
                            System.out.println(resname + " "
                                    + relation + " " + rres.toString());
                        }
                    }
                }
            }

            // Ask for the next step
            System.out.println("Select the number of the element you "
                    + "wish to navigate (0 for exit): ");
            String option = input.readLine();
            int select = Integer.parseInt(option);
            if (select == 0) {
                exit = true;
                break;
            } else {
                // Gets the selected resource
                OntResource res = ip.get(select - 1);
                // Determines if the element has any subelement
                ArrayList<OntResource> subr =
                        (ArrayList<OntResource>) qm.getRelatedOntResources(res);
                if (subr != null && subr.size() > 0) {
                    qm.addInterestPoints(subr);
                    qm.removeInterestPoint(res);
                }
            }

        }

    }
}
