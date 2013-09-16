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
package org.ontspace.mods;

import java.util.ArrayList;
import java.util.HashMap;
import org.ontspace.MetadataRecordReference;
import org.ontspace.mods.translator.Mods;
import org.ontspace.resource.ResourceQueryManager;

/**
 * Interface for a Mods core Query Manager
 */
public interface ModsQueryManager extends ResourceQueryManager {

    public MetadataRecordReference storeNewMods(Mods mods) throws
            Exception;

       /**
     *  Gets the title values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap <String, String> with the values
     *
     */
    HashMap<String, String> getTitles(String resId) ;

    
    /**
     *  Gets the title values for a particular element in the ontology in the
     * specified language
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getTitles(String resId, String lang);
    /**
     *  Gets the language values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getLanguages(String resId);
    
     /**
     *  Gets the identifier values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getIdentifiers(String resId);
    
     /**
     *  Gets the location values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */

    ArrayList<String> getLocations(String resId);
    
     /**
     *  Gets the dates values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
     ArrayList<String> getDates(String resId);
    
     /**
     *  Gets the creator values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */

     ArrayList<String> getCreators(String resId);
    
     /**
     *  Gets the contributor values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
     ArrayList<String> getContributors(String resId);
    
     /**
     *  Gets the publisher values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
     ArrayList<String> getPublishers(String resId);

     /**
     *  Gets the type values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getTypes(String resId);
    
     /**
     *  Gets the abstract values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
    HashMap<String, String> getAbstracts(String resId) ;
    
    
    /**
     *  Gets the title values for a particular element in the ontology in the
     * specified language
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    ArrayList<String> getAbstracts(String resId, String lang);
    
    /**
     *  Gets the table of contents values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    ArrayList<String> getTablesOfContents(String resId);
    
    
    /**
     *  Gets the subject values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
  
    ArrayList<String> getSubjects(String resId, String lang);
    
    /**
     *  Gets the subject values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
  
    HashMap<String, String> getSubjects(String resId);
    
   
     /**
     *  Gets the classification values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
  
    ArrayList<String> getClassifications(String resId);
    
    
     /**
     *  Gets the relation values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    
    ArrayList<String> getRelations(String resId);
    
    void close();
}
