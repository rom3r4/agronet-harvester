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
package org.ontspace.qdc;

import org.ontspace.MetadataRecordReference;
import org.ontspace.dc.DCQueryManager;
import org.ontspace.qdc.owl.QDCQueryResultImpl;
import org.ontspace.qdc.translator.QualifiedDublinCore;

/**
 * Interface for a Dublin core Query Manager
 */
public interface QDCQueryManager extends DCQueryManager {

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param bibliographicCitation
     * @return QDCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    QDCQueryResultImpl queryQDCByBibliographicCitation(
            String bibliographicCitation);

    /**
     * Store the Qualified Dublin Core object in the owl Ontology
     * @param dc Qualified Dublin Core object with metadata
     * @return recored reference to object in the owl ontology
     * @throws Exception
     */
    public MetadataRecordReference storeNewQualifiedDublinCore(QualifiedDublinCore qdc) throws Exception;
}
