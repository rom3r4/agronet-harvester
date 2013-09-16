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
package org.ontspace.owl;

import com.hp.hpl.jena.ontology.OntModel;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.ontspace.QueryManager;
import org.ontspace.Session;

/**
 * General Implementation of Query Managers
 * to Manages repositories queries
 */
public class QueryManagerImpl implements QueryManager {

    private static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private static List<URI> _uriList = new ArrayList<URI>();

    public QueryManagerImpl(MetadataRepositoryImpl rep)
            throws ClassNotFoundException, NoSuchMethodException {
        _rep = rep;
        _onts=new HashMap<String,OntModel>();

    }

    /**
     * Add the ontology associated with the specified query manager
     * @param ont The ontology to add
     */
    @Override
    public void addOntologyRef(String ontName, OntModel ont) {
        this._onts.put(ontName, ont);
    }

    /**
     * Gets a list with the URIs of the ontologies required by the query manager
     * @return A list with the URIs of the required ontologies
     */
    @Override
    public List<URI> getRequiredOntologies() {
        return _uriList;
    }

    /**
     * Gets the session to which the Query Manager is associated,
     * assigning a anonymous userId
     * @return  The session with anonymous user identifier
     */
    @Override
    public Session getAnonymousSession() {
        return new SessionImpl(_rep);
    }

    /**
     * Gets the session to which the Query Manager is associated for the
     *  given userId
     * @param userId The user identifier to associate
     * @return The Session to which the Query Manager is associated for the
     *  given userId
     */
    @Override
    public Session getSessionId(String userId) {
        return new SessionImpl(_rep, userId);
    }

    /**
     * Gets the ontology model
     * @return the ontology model associated at query manager
     * @deprecated 
     */
    @Override
    @Deprecated
    public OntModel getOntologyModel() {
        OntModel ontmodel = null;
        Iterator<String> ontNamesIt = _onts.keySet().iterator();
        String ontName;
        if (ontNamesIt.hasNext()) {
            ontName = ontNamesIt.next();
            ontmodel = _onts.get(ontName);
        }
        return ontmodel;
    }

    /**
     * Obtain the ontologies used by this QueryManager
     * @return the ontlogies
     */
    public HashMap<String,OntModel> getOnts() {
        return _onts;
    }

    /**
     * Gets the ontology model asociated to queryManager
     * @param ontName the name of the ontology
     * @return the ontology model
     */
    @Override
    public OntModel getOntologyModel(String ontName) {
        OntModel ontModel = null;
        ontModel = _onts.get(ontName);
        if (ontModel == null) {
            System.out.println(QueryManagerImpl.class.toString() + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }
}
