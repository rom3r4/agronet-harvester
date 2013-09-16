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
package org.ontspace.agrovoc;

import java.util.List;
import org.ontspace.agrovoc.impl.AgrovocConcept;

/**
 * Interface for an Agrovoc Query Manager
 *
 */
public interface AgrovocQueryManager {

    /**
     * Determins if a given agrovoc concept is already an interest point
     * @param concept Agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    public boolean existsInterestPoint(AgrovocConcept concept);

    /**
     * Determins if a given agrovoc concept is already an interest point
     * @param conceptURI URI for the agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    public boolean existsInterestPoint(String conceptURI);

    /**
     * Adds an interest point to the current session
     * @param agrovocConcept New interest point to be added
     */
    public void addInterestPoint(AgrovocConcept agrovocConcept);

    /**
     * Adds an interest point to the current session
     * @param agrovocConceptURI URI of the new interest point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     * @throws Exception
     */
    public boolean addInterestPoint(String agrovocConceptURI) throws Exception;

    /**
     * Adds several interest points to the current session
     * @param agrovocConcepts New interest points to be added
     */
    public void addInterestPoints(List<AgrovocConcept> agrovocConcepts);

    /**
     * Adds several interest points to the current session
     * @param agrovocConceptsURI URI of the new interests points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     * @throws Exception
     */
    public boolean addInterestPointsURI(List<String> agrovocConceptsURI)
            throws Exception;

    /**
     * Removes an interest point from the current session
     * @param agrovocConcept Interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeInterestPoint(AgrovocConcept agrovocConcept);

    /**
     * Removes an interest point from the current session
     * @param agrovocConceptURI URI of the interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeInterestPoint(String agrovocConceptURI)
            throws Exception;

    /**
     * Returns the list of the current interest points
     * @return List containing the current interest points
     */
    public List<AgrovocConcept> getInterestPoints();

    /**
     * Returns the list of URI of the current interest points
     * @return List containing the URI of the current interest points
     */
    public List<String> getInterestPointsURI();

    /**
     * Determins if a given agrovoc concept is already a search point
     * @param concept Agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    public boolean existsSearchPoint(AgrovocConcept concept);

    /**
     * Determins if a given agrovoc concept is already a search point
     * @param conceptURI URI for the agrovoc concept to test
     * @return Logical value determining the existence of the concept
     */
    public boolean existsSearchPoint(String conceptURI);

    /**
     * Adds a search point to the current session
     * @param agrovocConcept New search point to be added
     */
    public void addSearchPoint(AgrovocConcept agrovocConcept);

    /**
     * Adds a search point to the current session
     * @param agrovocConceptURI URI of the new search point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     * @throws Exception
     */
    public boolean addSearchPoint(String agrovocConceptURI) throws Exception;

    /**
     * Adds several search points to the current session
     * @param agrovocConcepts New search points to be added
     */
    public void addSearchPoints(List<AgrovocConcept> agrovocConcepts);

    /**
     * Adds several search points to the current session
     * @param agrovocConceptsURI URI of the new search points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     * @throws Exception
     */
    public boolean addSearchPointsURI(List<String> agrovocConceptsURI)
            throws Exception;

    /**
     * Removes an search point from the current session
     * @param agrovocConcept Search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeSearchPoint(AgrovocConcept agrovocConcept);

    /**
     * Removes an search point from the current session
     * @param agrovocConceptURI URI of the search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeSearchPoint(String agrovocConceptURI) throws Exception;

    /**
     * Returns the list of the current search points
     * @return List containing the current search points
     */
    public List<AgrovocConcept> getSearchPoints();

    /**
     * Returns the list of URI of the current search points
     * @return List containing the URI of the current search points
     */
    public List<String> getSearchPointsURI();

    /**
     * Gets the URI for the broader concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which broader concepts will be fetched
     * @return URI for the broader concepts for the given agrovoc concept
     */
    public List<String> getBroaderConceptsURI(AgrovocConcept concept);

    /**
     * Gets the URI for the broader concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which broader concepts
     * will be fetched
     * @return URI for the broader concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<String> getBroaderConceptsURI(String conceptURI)
            throws Exception;

    /**
     * Gets the broader concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which broader concepts will be fetched
     * @return Broader concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getBroaderConcepts(AgrovocConcept concept)
            throws Exception;

    /**
     * Gets the broader concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which broader concepts
     * will be fetched
     * @return Broader concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getBroaderConcepts(String conceptURI)
            throws Exception;

    /**
     * Gets the URI for the related concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which related concepts will be fetched
     * @return URI for the related concepts for the given agrovoc concept
     */
    public List<String> getRelatedConceptsURI(AgrovocConcept concept);

    /**
     * Gets the URI for the related concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which related concepts
     * will be fetched
     * @return URI for the related concepts for the given agrovoc concept
     */
    public List<String> getRelatedConceptsURI(String conceptURI)
            throws Exception;

    /**
     * Gets the related concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which related concepts will be fetched
     * @return Related concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getRelatedConcepts(AgrovocConcept concept)
            throws Exception;

    /**
     * Gets the related concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which related concepts
     * will be fetched
     * @return Related concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getRelatedConcepts(String conceptURI)
            throws Exception;

    /**
     * Gets the URI for the narrower concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which narrower concepts will be fetched
     * @return URI for the narrower concepts for the given agrovoc concept
     */
    public List<String> getNarrowerConceptsURI(AgrovocConcept concept);

    /**
     * Gets the URI for the narrower concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which narrower concepts
     * will be fetched
     * @return URI for the narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<String> getNarrowerConceptsURI(String conceptURI)
            throws Exception;

    /**
     * Gets the narrower concepts for a given agrovoc concept
     * @param concept Agrovoc Concept which narrower concepts will be fetched
     * @return Narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getNarrowerConcepts(AgrovocConcept concept)
            throws Exception;

    /**
     * Gets the narrower concepts for a given agrovoc concept
     * @param conceptURI URI for the agrovoc Concept which narrower concepts
     * will be fetched
     * @return Narrower concepts for the given agrovoc concept
     * @throws Exception
     */
    public List<AgrovocConcept> getNarrowerConcepts(String conceptURI)
            throws Exception;
}
