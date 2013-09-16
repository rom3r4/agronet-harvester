/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.util;

import java.util.ArrayList;
import org.ontspace.MetadataRecordReference;
import org.ontspace.owl.QueryResultImpl;

/**
 *
 * @author abian
 */
public class Pagination {
    
    public static ArrayList paginate(ArrayList arr, int limit, int offset) {
        
        ArrayList res = new ArrayList();
        
        for (int i = offset;
                (i <= (arr.size() - 1) && i < offset + limit);
                i++) {
            res.add(arr.get(i));
        }
        
        return res;
    }
    
    public static QueryResultImpl paginate(QueryResultImpl qr, int limit, int offset) {
        
        QueryResultImpl pqr = new QueryResultImpl();
        
        ArrayList<MetadataRecordReference> mrrList = qr.getMetadataRecordReferences();
        
        pqr.setTotalResults(mrrList.size());
        
        ArrayList<MetadataRecordReference> paginatedMrrList = paginate(mrrList, limit, offset);
        
        pqr.getMetadataRecordReferences().addAll(paginatedMrrList);
        
        return pqr;
    }
}
