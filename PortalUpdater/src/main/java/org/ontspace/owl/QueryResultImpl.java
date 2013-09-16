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

import java.util.ArrayList;
import java.util.Iterator;
import org.ontspace.MetadataRecordReference;

/**
 * Specifies the structure of the results obtained from a SLD query
 */
public class QueryResultImpl {

    private ArrayList<MetadataRecordReference> _mrr;

     //Attribute with the amount of results, for pagination purposes in the final
    //user interface
    private long _totalResults;

    /** Creates a new instance of QueryResultImpl */
    public QueryResultImpl() {
        _mrr = new ArrayList<MetadataRecordReference>();
    }

    /** 
     * Obtains a Set of MetadataRecordReference objects
     *and show each MetadataRecordReference
     * @return
     */
    public ArrayList<MetadataRecordReference> printResults() {

        Iterator iter = _mrr.iterator();
        while (iter.hasNext()) {

            MetadataRecordReference mrr =
                    (MetadataRecordReference) iter.next();
            System.out.println();
            System.out.println("MetadataRecordReference - localId -> " + mrr.getLocalMetadataRecordId());
            System.out.println("MetadataRecordReference - repositoryUri-> " + mrr.getMRRAccessInterface().toString());
        }
        return _mrr;

    }

    /** 
     * Obtains a Set of MetadataRecordReference objects
     * @return
     */
    public ArrayList<MetadataRecordReference> getMetadataRecordReferences() {
        return _mrr;
    }

    /**
     * @return the _totalResults
     */
    public long getTotalResults() {
        return _totalResults;
    }

    /**
     * @param totalResults the _totalResults to set
     */
    public void setTotalResults(long totalResults) {
        this._totalResults = totalResults;
    }
}
