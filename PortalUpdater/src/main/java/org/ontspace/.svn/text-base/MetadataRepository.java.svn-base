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

import java.net.URI;
import java.util.HashMap;
import org.ontspace.owl.util.OntologyNotInRepositoryException;

/**
 * Defines the basic interface of ont-space repositories.
 */
public interface MetadataRepository {

    /**
     * Close the repository and free up resources held
     */
    public void close();

    /**
     * Gets a session for the given userId
     * @param userId The user identifier
     * @return The session for a given userId
     */
    public Session getSessionId(String userId);

    /**
     * Gets a new anonymous session
     * @return The anonymous session
     */
    public Session getAnonymousSession();

    /**
     * Gets the URI of the repository
     * @return The URI of the repository
     */
    public URI getRepositoryURI();

    /**
     * Gets a new instance of the selected QueryManager. 
     * @param type Specifies the type of QueryMananager
     * @return  A new instance QueryManager specified as type param
     * @throws OntologyNotInRepositoryException
     */
    public QueryManager getQueryManager(String type) throws
        OntologyNotInRepositoryException;

    /**
     * Gets a new instance of the selected QueryManager. 
     * @param type Specifies the type of QueryMananager
     * @param params Additional parameters for the QueryManager invocation
     * @return  A new instance QueryManager specified as type param
     * @throws OntologyNotInRepositoryException 
     */
    public QueryManager getQueryManager(String type,
        HashMap<String, Object> params) throws OntologyNotInRepositoryException;
}
