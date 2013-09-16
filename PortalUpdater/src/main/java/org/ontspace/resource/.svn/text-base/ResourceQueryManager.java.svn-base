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
package org.ontspace.resource;

import org.ontspace.QueryManager;
import org.ontspace.resource.owl.ResourceQueryResultImpl;

/**
 * Interface for a Resource core Query Manager
 */
public interface ResourceQueryManager extends QueryManager {

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param language the language to search
     * @return DCQueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    ResourceQueryResultImpl queryResourceByLanguage(String language);

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param subject the subject to search
     * @return DCQueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    ResourceQueryResultImpl queryResourceBySubject(String subject);

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param title the title to search
     * @return DCQueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    ResourceQueryResultImpl queryResourceByTitle(String title);

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param type the type to search
     * @return DCQueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    ResourceQueryResultImpl queryResourceByType(String type);

    void close();
}
