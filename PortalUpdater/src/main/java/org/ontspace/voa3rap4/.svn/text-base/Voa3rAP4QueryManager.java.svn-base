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
package org.ontspace.voa3rap4;

import java.util.ArrayList;
import java.util.HashMap;
import org.ontspace.MetadataRecordReference;
import org.ontspace.voa3rap2.Voa3rAP2QueryManager;
import org.ontspace.voa3rap4.owl.Voa3rAP4QueryResultImpl;
import org.ontspace.voa3rap4.translator.Vap4Agent;
import org.ontspace.voa3rap4.translator.Vap4MetaMetadata;
import org.ontspace.voa3rap4.translator.Vap4Research;
import org.ontspace.voa3rap4.translator.Voa3rAP4;

public interface Voa3rAP4QueryManager extends Voa3rAP2QueryManager {
  
   MetadataRecordReference storeNewVoa3rAP4(Voa3rAP4 vap4) throws Exception;
  
  
  
  /**
     * Launches a query for a particular element in the ontology
     *
     * @param contributor the contributor to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByContributor(String contributor);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param format the format to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByFormat(String format);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param rights the rights to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByRights(String rights);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param coverage the coverage to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByCoverage(String coverage);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param identifier the identifier to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIdentifier(String identifier);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param source the source to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
//    Voa3rAP4QueryResultImpl queryVoa3rAP4BySource(String source);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param creator the creator to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByCreator(String creator);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param language the language to search
     * @return Voa3rAP4QueryResultImpl Voa3rAP4QueryManagerobject that have the
     * Set of MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByLanguage(String language);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param subject the subject to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4BySubject(String subject);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param date the date to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByDate(String date);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param publisher the publisher to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByPublisher(String publisher);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param title the title to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByTitle(String title);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param description the description to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByDescription(String description);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param relation the relation to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByRelation(String relation);

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param type the type to search
     * @return Voa3rAP4QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByType(String type);
    
    
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByAgrovocTerm(ArrayList<String> agrovocTerms);
    
    // QDC
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByConformsTo(String ct);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByDescriptionAbstract(String da);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasPart(String hp);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIdentifierBibliographicCitation(String ibc);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsPartOf(String ipo);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsReferencedBy(String irb);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByReferences(String ref);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByRightsAccessRights(String rar);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByRightsLicense(String rl);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByTitleAlternative(String ta);
    //qdc new in ap4
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasVersion(String hv);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsVersionOf(String hv);


    //ESE
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsShownAt(String isa);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsShownBy(String isb);
    
    
    //MARCREL
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByEdt(String edt);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByRev(String rev);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByTrl(String trl);
    
    
    //VOA3R
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByMeasuresVariable(String mv);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByObjectOfInterest(String ooi);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByPublicationStatus(String ps);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByReviewStatus(String rs);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByUsesInstrument(String ui);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByUsesMethod(String um);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByUsesProtocol(String up);
//    Voa3rAP4QueryResultImpl queryVoa3rAP4ByUsesTechnique(String ut);
    
    //voa3r new in ap4
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasTranslation(String ps);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsTranslationOf(String ps);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasMetadata(String ps);
    Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasResearch(String ps);
  
    
    //QDC
//    ArrayList<String> getConformsTo(String resId);
//    HashMap<String, String> getDescriptionAbstract(String resId);
//    ArrayList<String> getDescriptionAbstract(String resId, String lang);
//    ArrayList<String> getHasPart(String resId);
//    ArrayList<String> getIdentifierBibliographicCitation(String resId);
//    ArrayList<String> getIsPartOf(String resId);
//    ArrayList<String> getIsReferencedBy(String resId);
//    ArrayList<String> getReferences(String resId);
//    HashMap<String, String> getRightsAccessRights(String resId);
//    ArrayList<String> getRightsAccessRights(String resId, String lang);
//    HashMap<String, String> getRightsLicense(String resId);
//    ArrayList<String> getRightsLicense(String resId, String lang);
//    HashMap<String, String> getTitleAlternative(String resId);
//    ArrayList<String> getTitleAlternative(String resId, String lang);
    
    //qdc new in ap4
    ArrayList<String> getHasVersion(String resId);
    ArrayList<String> getIsVersionOf(String resId);
    

    //ESE
//    ArrayList<String> getIsShownAt(String resId);
//    ArrayList<String> getIsShownBy(String resId);
    
    
    //MARCREL
//    ArrayList<String> getEdt(String resId);
//    ArrayList<String> getRev(String resId);
//    ArrayList<String> getTrl(String resId);
    
    
    //VOA3R
//    ArrayList<String> getMeasuresVariable(String resId);
//    ArrayList<String> getObjectOfInterest(String resId);
    ArrayList<String> getPublicationStatus(String resId);
    ArrayList<String> getReviewStatus(String resId);
//    ArrayList<String> getUsesInstrument(String resId);
//    ArrayList<String> getUsesMethod(String resId);
//    ArrayList<String> getUsesProtocol(String resId);
//    ArrayList<String> getUsesTechnique(String resId);
    ArrayList<String> getHasTranslation(String resId);
    ArrayList<String> getIsTranslationOf(String resId);
    
    public ArrayList<Vap4MetaMetadata> getHasMetametadata(String resId);
    public ArrayList<Vap4Research> getHasResearch(String resId);
    
    public ArrayList<Vap4Agent> getAgentCreators(String resId);
    public ArrayList<Vap4Agent> getAgentPublishers(String resId);
    public ArrayList<Vap4Agent> getAgentContributors(String resId);
    
    @Override
    void close();
}