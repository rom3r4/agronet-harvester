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
package org.ontspace;

import com.hp.hpl.jena.ontology.OntModel;
import java.net.URI;
import java.util.List;

/**
 * Manages repositories queries
 */
public interface QueryManager {

    /**
     * Add the ontology asociated with the specified query manager
     * @param ontName name of the ontology
     * @param ont The ontology to add
     */
    public void addOntologyRef(String ontName, OntModel ont);

    /**
     * Gets a list with the URIs of the ontologies required by the query manager
     * @return A list whith the USIs of the required ontologies
     */
    public List<URI> getRequiredOntologies();

    /**
     * Gets the session to which the Query Manager is associated,
     * assigning a anonymous userId
     * @return  The sesion with anonymous user identifier
     */
    public Session getAnonymousSession();

    /**
     * Gets the session to which the Query Manager is associated for the
     *  given userId
     * @param userId The user identifier to asociate
     * @return The Session to which the Query Manager is associated for the
     *  given userId
     */
    public Session getSessionId(String userId);

    /**
     * Gets the ontModel asociated at queryManager
     * @return the ontModel
     */
    @Deprecated
    public OntModel getOntologyModel();

    /**
     * Gets the ontModel asociated at queryManager
     * @param ontName the name of the ontology
     * @return the ontModel
     */
    public OntModel getOntologyModel(String ontName);
}
