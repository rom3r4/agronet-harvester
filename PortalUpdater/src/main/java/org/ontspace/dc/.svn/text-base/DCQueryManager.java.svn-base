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
package org.ontspace.dc;

import java.util.ArrayList;
import java.util.HashMap;
import org.ontspace.MetadataRecordReference;
import org.ontspace.dc.owl.DCQueryResultImpl;
import org.ontspace.dc.translator.DublinCore;
import org.ontspace.resource.ResourceQueryManager;

/**
 * Interface for a Dublin core Query Manager
 */
public interface DCQueryManager extends ResourceQueryManager {

    /**
     *  Launches a query for  a particular element in the ontology
     * @param contributor the contributor to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByContributor(String contributor);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param format the format to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByFormat(String format);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param rights the rights to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByRights(String rights);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param coverage the coverage to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByCoverage(String coverage);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param identifier the identifier to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByIdentifier(String identifier);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param source the source to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCBySource(String source);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param creator the creator to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByCreator(String creator);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param language the language to search
     * @return DCQueryResultImpl DCQueryManagerobject that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByLanguage(String language);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param subject the subject to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCBySubject(String subject);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param agrovocTerms the agrovoc terms to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset);
    
    
    DCQueryResultImpl queryDCByAgrovocTerm(ArrayList<String> agrovocTerms);
    
    /**
     *  Launches a query for  a particular element in the ontology
     * @param date the date to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByDate(String date);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param publisher the publisher to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByPublisher(String publisher);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param title the title to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByTitle(String title);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param description the description to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByDescription(String description);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param relation the relation to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByRelation(String relation);

    /**
     *  Launches a query for  a particular element in the ontology
     * @param type the type to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    DCQueryResultImpl queryDCByType(String type);

    /**
     * Store the Duclin Core object in the owl Ontology
     * @param dc Dublin core object with metadata
     * @return recored reference to object in the owl ontology
     * @throws Exception
     */
    MetadataRecordReference storeNewDublinCore(DublinCore dc) throws Exception;

    /**
     *  Gets the contributor values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getContributors(String resId);

    /**
     *  Gets the format values for a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getFormats(String resId);

    /**
     *  Gets the right values for a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
     ArrayList<String> getRights(String resId);

    /**
     *  Gets the coverage values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getCoverages(String resId);

    /**
     *  Gets the identifier values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getIdentifiers(String resId);

    /**
     *  Gets the source values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getSources(String resId);

    /**
     *  Gets the creator values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getCreators(String resId);

    /**
     *  Gets the language values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getLanguages(String resId);

    /**
     *  Gets the subject values in the specified language for a particular element in the ontology
     * @param resId the subject to search
     * @param lang the language for the element
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
     ArrayList<String> getSubjects(String resId, String lang);


    /**
     *  Gets the subject values for a particular element in the ontology for the specified language
     * @param resId the subject to search
     * @return HashMap<String, String>with the values
     */
     HashMap<String, ArrayList<String>> getSubjects(String resId);

     /**
     *  Gets the agrovoc terms values for  a particular element in the ontology
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getAgrovocTerms(String resId);
     
    /**
     *  Gets the date values for  a particular element in the ontology
     *  @param resId the subject to search
     * @return ArrayList<String> with the values
     *
     */
     ArrayList<String> getDates(String resId);

    /**
     *  Gets the publisher values for  a particular element in the ontology
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getPublishers(String resId);

    /**
     *  Gets the title values for a particular element in the ontology
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
     HashMap<String, String> getTitles(String resId);

    /**
     *  Gets the title values for a particular element in the ontology in the
     * specified language
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getTitles(String resId, String lang);

    /**
     *  Gets the description values for a particular element in the ontology
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
     HashMap<String, String> getDescriptions(String resId);

    /**
     *  Gets the description values for a particular element in the ontology in the
     * specified language
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getDescriptions(String resId, String lang);

    /**
     *  Gets the relation value for  a particular element in the ontology
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getRelations(String resId);

    /**
     *  Gets the type values for a particular element in the ontology
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
     ArrayList<String> getTypes(String resId);

    @Override
    void close();
}
