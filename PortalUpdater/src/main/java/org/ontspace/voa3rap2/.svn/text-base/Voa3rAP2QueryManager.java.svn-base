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
package org.ontspace.voa3rap2;

import java.util.ArrayList;
import java.util.HashMap;
import org.ontspace.MetadataRecordReference;
import org.ontspace.dc.DCQueryManager;
import org.ontspace.resource.ResourceQueryManager;
import org.ontspace.voa3rap2.owl.Voa3rAP2QueryResultImpl;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 * Interface for a VOA3R AP Level 2 Query Manager
 */
public interface Voa3rAP2QueryManager extends DCQueryManager {

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param contributor the contributor to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByContributor(String contributor);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param format the format to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByFormat(String format);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param rights the rights to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByRights(String rights);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param coverage the coverage to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByCoverage(String coverage);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param identifier the identifier to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIdentifier(String identifier);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param source the source to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2BySource(String source);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param creator the creator to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByCreator(String creator);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param language the language to search
     * @return Voa3rAP2QueryResultImpl Voa3rAP2QueryManagerobject that have the
     * Set of MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByLanguage(String language);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param subject the subject to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2BySubject(String subject);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param date the date to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByDate(String date);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param publisher the publisher to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByPublisher(String publisher);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param title the title to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByTitle(String title);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param description the description to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByDescription(String description);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param relation the relation to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByRelation(String relation);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param type the type to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByType(String type);
    
    
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByAgrovocTerm(ArrayList<String> agrovocTerms);
    
    // QDC
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByConformsTo(String ct);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByDescriptionAbstract(String da);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByHasPart(String hp);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIdentifierBibliographicCitation(String ibc);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsPartOf(String ipo);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsReferencedBy(String irb);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByReferences(String ref);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByRightsAccessRights(String rar);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByRightsLicense(String rl);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByTitleAlternative(String ta);


    //ESE
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsShownAt(String isa);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsShownBy(String isb);
    
    
    //MARCREL
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByEdt(String edt);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByRev(String rev);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByTrl(String trl);
    
    
    //VOA3R
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByMeasuresVariable(String mv);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByObjectOfInterest(String ooi);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByPublicationStatus(String ps);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByReviewStatus(String rs);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesInstrument(String ui);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesMethod(String um);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesProtocol(String up);
    Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesTechnique(String ut);
    
    
    /**
     * Store the Dublin Core object in the owl Ontology
     *
     * @param dc Dublin core object with metadata
     * @return recored reference to object in the owl ontology
     * @throws Exception
     */
    MetadataRecordReference storeNewVoa3rAP2(Voa3rAP2 vap2) throws Exception;

//    /**
//     * Gets the contributor values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    ArrayList<String> getContributors(String resId);
//
//    /**
//     * Gets the format values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    ArrayList<String> getFormats(String resId);
//
//    /**
//     * Gets the right values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    ArrayList<String> getRights(String resId);
//
//    /**
//     * Gets the coverage values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getCoverages(String resId);
//
//    /**
//     * Gets the identifier values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getIdentifiers(String resId);
//
//    /**
//     * Gets the source values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getSources(String resId);
//
//    /**
//     * Gets the creator values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getCreators(String resId);
//
//    /**
//     * Gets the language values for a particular element in the ontology
//     *
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getLanguages(String resId);
//
//    /**
//     * Gets the subject values in the specified language for a particular
//     * element in the ontology
//     *
//     * @param resId the subject to search
//     * @param lang the language for the element
//     * @return Voa3rAP2QueryResultImpl object that have the Set of
//     * MetadataRecordReference
//     */
//    ArrayList<String> getSubjects(String resId, String lang);
//
//    /**
//     * Gets the subject values for a particular element in the ontology for the
//     * specified language
//     *
//     * @param resId the subject to search
//     * @return HashMap<String, String>with the values
//     */
//    HashMap<String, ArrayList<String>> getSubjects(String resId);
//
//    /**
//     * Gets the date values for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     *
//     */
//    ArrayList<String> getDates(String resId);
//
//    /**
//     * Gets the publisher values for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getPublishers(String resId);
//
//    /**
//     * Gets the title values for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    HashMap<String, String> getTitles(String resId);
//
//    /**
//     * Gets the title values for a particular element in the ontology in the
//     * specified language
//     *
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getTitles(String resId, String lang);
//
//    /**
//     * Gets the description values for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    HashMap<String, String> getDescriptions(String resId);
//
//    /**
//     * Gets the description values for a particular element in the ontology in
//     * the specified language
//     *
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getDescriptions(String resId, String lang);
//
//    /**
//     * Gets the relation value for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getRelations(String resId);
//
//    /**
//     * Gets the type values for a particular element in the ontology
//     *
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    ArrayList<String> getTypes(String resId);
    
    
    //QDC
    ArrayList<String> getConformsTo(String resId);
    HashMap<String, String> getDescriptionAbstract(String resId);
    ArrayList<String> getDescriptionAbstract(String resId, String lang);
    ArrayList<String> getHasPart(String resId);
    ArrayList<String> getIdentifierBibliographicCitation(String resId);
    ArrayList<String> getIsPartOf(String resId);
    ArrayList<String> getIsReferencedBy(String resId);
    ArrayList<String> getReferences(String resId);
    HashMap<String, String> getRightsAccessRights(String resId);
    ArrayList<String> getRightsAccessRights(String resId, String lang);
    HashMap<String, String> getRightsLicense(String resId);
    ArrayList<String> getRightsLicense(String resId, String lang);
    HashMap<String, String> getTitleAlternative(String resId);
    ArrayList<String> getTitleAlternative(String resId, String lang);

    //ESE
    ArrayList<String> getIsShownAt(String resId);
    ArrayList<String> getIsShownBy(String resId);
    
    
    //MARCREL
    ArrayList<String> getEdt(String resId);
    ArrayList<String> getRev(String resId);
    ArrayList<String> getTrl(String resId);
    
    
    //VOA3R
    ArrayList<String> getMeasuresVariable(String resId);
    ArrayList<String> getObjectOfInterest(String resId);
    ArrayList<String> getPublicationStatus(String resId);
    ArrayList<String> getReviewStatus(String resId);
    ArrayList<String> getUsesInstrument(String resId);
    ArrayList<String> getUsesMethod(String resId);
    ArrayList<String> getUsesProtocol(String resId);
    ArrayList<String> getUsesTechnique(String resId);
    
    @Override
    void close();
}
