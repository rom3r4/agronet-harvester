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
package org.ontspace.agrovoc.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.ontspace.Session;
import org.ontspace.agrovoc.AgrovocQueryManager;
import org.ontspace.agrovoc.ws.WSinvoker;
import org.ontspace.agrovoc.ws.WSparser;

/**
 * Implementation for an Agrovoc Query Manager
 *
 */
public class AgrovocQueryManagerImpl implements AgrovocQueryManager {

    /** Associated NavigationalSession */
    private AgrovocSessionImpl _session;
    /** Interest points of the agrovoc manager */
    private ArrayList<AgrovocConcept> _interestPoints;
    /** Search points of the agrovoc manager */
    private ArrayList<AgrovocConcept> _searchPoints;

    /**
     * Creates a new instance of AgrovocQueryManagerImpl
     */
    public AgrovocQueryManagerImpl() {
        this._interestPoints = new ArrayList<AgrovocConcept>();
        this._searchPoints = new ArrayList<AgrovocConcept>();
    }

    /**
     * Binds the Agrovoc Query Manager to its associated Session
     * @param sess Session for the current agrovoc navigation
     */
    public void bindToSession(Session sess) {
        this._session = (AgrovocSessionImpl) sess;
    }

    /**
     * Determins if a given agrovoc concept is already an interest point
     * @param concept Agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    @Override
    public boolean existsInterestPoint(AgrovocConcept concept) {
        return this._interestPoints.contains(concept);
    }

    /**
     * Determins if a given agrovoc concept is already an interest point
     * @param conceptURI URI for the agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    @Override
    public boolean existsInterestPoint(String conceptURI) {
        for (AgrovocConcept ac : this._interestPoints) {
            if (ac.getAbout().equals(conceptURI)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an interest point to the current session
     * @param concept New interest point to be added
     */
    @Override
    public void addInterestPoint(AgrovocConcept concept) {
        if (!this.existsInterestPoint(concept)) {
            this._interestPoints.add(concept);
        }
    }

    /**
     * Adds an interest point to the current session
     * @param conceptURI URI of the new interest point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     * @throws Exception
     */
    @Override
    public boolean addInterestPoint(String conceptURI)
            throws Exception {
        if (!this.existsInterestPoint(conceptURI)) {
            AgrovocConcept concept = this.getAgrovocConcept(conceptURI);
            if (concept != null) {
                this.addInterestPoint(concept);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Adds several interest points to the current session
     * @param concepts New interest points to be added
     */
    @Override
    public void addInterestPoints(List<AgrovocConcept> concepts) {
        for (AgrovocConcept c : concepts) {
            addInterestPoint(c);
        }
    }

    /**
     * Adds several interest points to the current session
     * @param conceptsURI URI of the new interests points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     * @throws Exception
     */
    @Override
    public boolean addInterestPointsURI(List<String> conceptsURI)
            throws Exception {
        boolean successful = true;
        for (String conceptURI : conceptsURI) {
            successful = successful
                    && this.addInterestPoint(conceptURI);
        }
        return successful;
    }

    /**
     * Removes an interest point from the current session
     * @param concept Interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeInterestPoint(AgrovocConcept concept) {
        return this._interestPoints.remove(concept);
    }

    /**
     * Removes an interest point from the current session
     * @param conceptURI URI of the interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeInterestPoint(String conceptURI) throws Exception {
        if (this.existsInterestPoint(conceptURI)) {
            for (AgrovocConcept ac : this._interestPoints) {
                if (ac.getAbout().equals(conceptURI)) {
                    this.removeInterestPoint(ac);
                    return true;
                }
            }
            return false; // Should be unreachable
        } else {
            return true;
        }
    }

    /**
     * Returns the list of the current interest points
     * @return List containing the current interest points
     */
    @Override
    public List<AgrovocConcept> getInterestPoints() {
        return this._interestPoints;
    }

    /**
     * Returns the list of URI of the current interest points
     * @return List containing the URI of the current interest points
     */
    @Override
    public List<String> getInterestPointsURI() {
        List<String> interestPointsURI = new ArrayList<String>();
        for (AgrovocConcept concept : this._interestPoints) {
            interestPointsURI.add(concept.getAbout());
        }
        return interestPointsURI;
    }

    /**
     * Determins if a given agrovoc concept is already a search point
     * @param concept Agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    @Override
    public boolean existsSearchPoint(AgrovocConcept concept) {
        return this._searchPoints.contains(concept);
    }

    /**
     * Determins if a given agrovoc concept is already a search point
     * @param conceptURI URI for the agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    @Override
    public boolean existsSearchPoint(String conceptURI) {
        for (AgrovocConcept ac : this._searchPoints) {
            if (ac.getAbout().equals(conceptURI)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a search point to the current session
     * @param concept New search point to be added
     */
    @Override
    public void addSearchPoint(AgrovocConcept concept) {
        if (!this.existsSearchPoint(concept)) {
            this._searchPoints.add(concept);
        }
    }

    /**
     * Adds a search point to the current session
     * @param conceptURI URI of the new search point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     * @throws Exception
     */
    @Override
    public boolean addSearchPoint(String conceptURI) throws Exception {
        if (!this.existsSearchPoint(conceptURI)) {
            AgrovocConcept concept = this.getAgrovocConcept(conceptURI);
            if (concept != null) {
                this.addSearchPoint(concept);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Adds several search points to the current session
     * @param concepts New search points to be added
     */
    @Override
    public void addSearchPoints(List<AgrovocConcept> concepts) {
        for (AgrovocConcept c : concepts) {
            addSearchPoint(c);
        }
    }

    /**
     * Adds several search points to the current session
     * @param conceptsURI URI of the new search points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     * @throws Exception
     */
    @Override
    public boolean addSearchPointsURI(List<String> conceptsURI)
            throws Exception {
        boolean successful = true;
        for (String conceptURI : conceptsURI) {
            successful = successful
                    && this.addSearchPoint(conceptURI);
        }
        return successful;
    }

    /**
     * Removes an search point from the current session
     * @param concept Search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeSearchPoint(AgrovocConcept concept) {
        return this._searchPoints.remove(concept);
    }

    /**
     * Removes an search point from the current session
     * @param conceptURI URI of the search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeSearchPoint(String conceptURI) throws Exception {
        if (this.existsSearchPoint(conceptURI)) {
            for (AgrovocConcept ac : this._searchPoints) {
                if (ac.getAbout().equals(conceptURI)) {
                    this.removeSearchPoint(ac);
                    return true;
                }
            }
            return false; // Should be unreachable
        } else {
            return true;
        }
    }

    /**
     * Returns the list of the current search points
     * @return List containing the current search points
     */
    @Override
    public List<AgrovocConcept> getSearchPoints() {
        return this._searchPoints;
    }

    /**
     * Returns the list of URI of the current search points
     * @return List containing the URI of the current search points
     */
    @Override
    public List<String> getSearchPointsURI() {
        List<String> searchPointsURI = new ArrayList<String>();
        for (AgrovocConcept concept : this._searchPoints) {
            searchPointsURI.add(concept.getAbout());
        }
        return searchPointsURI;
    }

    /**
     * Gets the URI for the broader concepts for a given agrovoc concept
     * @param concept agrovoc concept which broader concepts will be fetched
     * @return URI for the broader concepts for the given agrovoc concept
     */
    @Override
    public List<String> getBroaderConceptsURI(AgrovocConcept concept) {
        List<String> returnList = new ArrayList<String>();
        for (AgrovocConcept ac : concept.getBroader()){
            returnList.add(ac.getAbout());
        }
        return returnList;
    }

    /**
     * Gets the URI for the broader concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which broader concepts
     * will be fetched
     * @return URI for the broader concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<String> getBroaderConceptsURI(String conceptURI)
            throws Exception {
        return this.getBroaderConceptsURI(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Gets the broader concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which broader concepts will be fetched
     * @return Broader concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getBroaderConcepts(AgrovocConcept concept){
        return concept.getBroader();
    }

    /**
     * Gets the broader concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which broader concepts
     * will be fetched
     * @return Broader concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getBroaderConcepts(String conceptURI)
            throws Exception {
        return this.getBroaderConcepts(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Gets the URI for the related concepts for a given agrovoc concept
     * @param concept agrovoc concept which related concepts will be fetched
     * @return URI for the related concepts for the given agrovoc concept
     */
    @Override
    public List<String> getRelatedConceptsURI(AgrovocConcept concept) {
        List<String> returnList = new ArrayList<String>();
        for (Iterator iter = concept.getRelated().entrySet().iterator(); iter.hasNext(); ){
            AgrovocConcept ac = (AgrovocConcept) ((Entry) iter.next()).getValue();
            returnList.add(ac.getAbout());
        }
        return returnList;
    }

    /**
     * Gets the URI for the related concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which related concepts
     * will be fetched
     * @return URI for the related concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<String> getRelatedConceptsURI(String conceptURI)
            throws Exception{
        return this.getRelatedConceptsURI(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Gets the related concepts for a given agrovoc concept
     * @param concept agrovoc concept which related concepts will be fetched
     * @return Related concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getRelatedConcepts(AgrovocConcept concept){
        List<AgrovocConcept> returnList = new ArrayList<AgrovocConcept>();
        for (Iterator iter = concept.getRelated().entrySet().iterator(); iter.hasNext(); ){
            AgrovocConcept ac = (AgrovocConcept) ((Entry) iter.next()).getValue();
            returnList.add(ac);
        }
        return returnList;
    }

    /**
     * Gets the related concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which related concepts
     * will be fetched
     * @return Related concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getRelatedConcepts(String conceptURI)
            throws Exception {
        return this.getRelatedConcepts(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Gets the URI for the narrower concepts for a given agrovoc concept
     * @param concept agrovoc concept which narrower concepts will be fetched
     * @return URI for the narrower concepts for the given agrovoc concept
     */
    @Override
    public List<String> getNarrowerConceptsURI(AgrovocConcept concept) {
        List<String> returnList = new ArrayList<String>();
        for (AgrovocConcept ac : concept.getNarrower()){
            returnList.add(ac.getAbout());
        }
        return returnList;
    }

    /**
     * Gets the URI for the narrower concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which narrower concepts
     * will be fetched
     * @return URI for the narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<String> getNarrowerConceptsURI(String conceptURI)
            throws Exception{
        return this.getNarrowerConceptsURI(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Gets the narrower concepts for a given agrovoc concept
     * @param concept agrovoc concept which narrower concepts will be fetched
     * @return Narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getNarrowerConcepts(AgrovocConcept concept){
       return concept.getNarrower();
    }

    /**
     * Gets the narrower concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc concept which narrower concepts
     * will be fetched
     * @return Narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    @Override
    public List<AgrovocConcept> getNarrowerConcepts(String conceptURI)
            throws Exception {
        return this.getNarrowerConcepts(this.getAgrovocConcept(conceptURI));
    }

    /**
     * Builds an Agrovoc Concept by its URI
     * @param conceptURI URI for the agrovoc concept to build
     * @return Agrovoc Concept built up from its URI
     * @throws Exception
     */
    private AgrovocConcept getAgrovocConcept(String conceptURI)
            throws Exception {
        // Is it already an interest point?
        for (AgrovocConcept ac : this._interestPoints) {
            if (ac.getAbout().equals(conceptURI)) {
                return ac;
            }
        }
        // Is it already a search point?
        for (AgrovocConcept ac : this._searchPoints) {
            if (ac.getAbout().equals(conceptURI)) {
                return ac;
            }
        }
        // Not in the session, must be fetched
        String response = WSinvoker.getTermInfo(conceptURI);
        AgrovocConcept concept = WSparser.parseSKOS(response);
        return concept;
    }

    /**
     * Gets all the subelements for a given Agrovoc Concept
     * @param concept Agrovoc Concept which subelements should be fetched
     * @return List containing all the subelements for a given Agrovoc Concept
     */
    private List<AgrovocConcept> getAllSubConcepts(AgrovocConcept concept){
        List<AgrovocConcept> returnList = new ArrayList<AgrovocConcept>();
        returnList.addAll(concept.getBroader());
        returnList.addAll(concept.getNarrower());
        for (Iterator iter = concept.getRelated().entrySet().iterator(); iter.hasNext(); ){
            AgrovocConcept tmp = (AgrovocConcept) ((Entry) iter.next()).getValue();
            returnList.add(tmp);
        }
        return returnList;
    }

    /**
     * Gets the associated Agrovoc Session for the Query Manager
     * @return Associated Agrovoc Session for the Query Manager
     */
    public AgrovocSessionImpl getSession() {
        return this._session;
    }
}
