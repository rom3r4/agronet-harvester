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
package org.ontspace.agrisap;

import java.util.ArrayList;
import org.ontspace.MetadataRecordReference;
import org.ontspace.agrisap.owl.AgrisapQueryResultImpl;
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.qdc.QDCQueryManager;

/**
 * Interface for a Dublin core Query Manager
 */
public interface AgrisapQueryManager extends QDCQueryManager {

    /**
     * Launches a query for getting the resources annotated with some of the
     * input values
     *
     * @param subjectThesaurus agrovoc values
     * @param limit maximum number of results
     * @param offset first result to be retrieved
     * @return AgrisapQueryResultImpl containing the MetadataRecordReferences
     */
    public AgrisapQueryResultImpl queryAgrisapBySubjectThesaurus(
            ArrayList<String> subjectThesaurus, int limit, int offset);

    
    AgrisapQueryResultImpl queryAgrisapBySubjectThesaurus(
            ArrayList<String> subjectThesaurus);
    
    /**
     * Stores a new Agrisap resource in the repository
     * @param the Agrisap class with the values filled in
     * @return a MetadataRecordReference refereed to the new resource stored
     * @throws Exception 
     */
    public MetadataRecordReference storeNewAgrisap(Agrisap agris) throws
            Exception;

    /**
     *  Gets the title value for a particular element in the ontology
     * @param resId the id to search
     * @param lang the language for the title values
     * @return ArrayList<String> with the values
     */
    
    ArrayList<String> getDCTitles(String resId, String lang);
    
    
    /**
     *  Gets the title value for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */

    ArrayList<String> getDCTitles(String resId);

            
    /**
     * Gets the date values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getQDCTitleAlternative(String resId);
    
    /**
     *  Gets the identifier values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    
    ArrayList<String> getDCIdentifiers(String resId);

    /**
     *  Gets the abstract values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getQDCAbstract(String resId);
    
    /**
     *  Gets the date values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getQDCDates(String resId);

    /**
     *  Gets the creator values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getAgrisCreators(String resId);

    /**
     *  Gets the classification values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getAgrisSubjectClassification(String resId);
    
     /**
     *  Gets the thesaurus values for a particular element in the ontology
     * @param resId the id to search
     *
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getAgrisSubjectThesauri(String resId);

}
