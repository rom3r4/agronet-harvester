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
package org.ontspace.nav;

import com.hp.hpl.jena.ontology.OntResource;
import java.util.List;
import org.ontspace.QueryManager;

/**
 * Interface for a Navigational Query Manager
 *
 */
public interface NavigationalQueryManager extends QueryManager {

    /**
     * Adds an interest point to the current session
     * @param ontResource New interest point to be added
     */
    public void addInterestPoint(OntResource ontResource);

    /**
     * Adds an interest point to the current session
     * @param ontResourceURI URI of the new interest point to be added
     * @return Boolean Logic constant determining whether the elements was found
     * and added or not
     */
    public boolean addInterestPoint(String ontResourceURI);

    /**
     * Adds several interest points to the current session
     * @param ontResources New interest points to be added
     */
    public void addInterestPoints(List<OntResource> ontResources);

    /**
     * Adds several interest points to the current session
     * @param ontResourcesURI URI of the new interests points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     */
    public boolean addInterestPointsURI(List<String> ontResourcesURI);

    /**
     * Removes an interest point from the current session
     * @param ontResource Interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeInterestPoint(OntResource ontResource);

    /**
     * Removes an interest point from the current session
     * @param ontResourceURI URI of the interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeInterestPoint(String ontResourceURI);

    /**
     * Returns the list of the current interest points
     * @return List containing the current interest points
     */
    public List<OntResource> getInterestPoints();

    /**
     * Returns the list of URI of the current interest points
     * @return List containing the URI of the current interest points
     */
    public List<String> getInterestPointsURI();

    /**
     * Adds a search point to the current session
     * @param ontResource New search point to be added
     */
    public void addSearchPoint(OntResource ontResource);

    /**
     * Adds a search point to the current session
     * @param ontResourceID ID of the new search point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     */
    public boolean addSearchPoint(String ontResourceID);

    /**
     * Adds several search points to the current session
     * @param ontResources New search points to be added
     */
    public void addSearchPoints(List<OntResource> ontResources);

    /**
     * Adds several search points to the current session
     * @param ontResourcesID ID of the new search points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     */
    public boolean addSearchPointsURI(List<String> ontResourcesID);

    /**
     * Removes a search point from the current session
     * @param ontResource Search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeSearchPoint(OntResource ontResource);

    /**
     * Removes a search point from the current session
     * @param ontResourceID ID of the search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    public boolean removeSearchPoint(String ontResourceID);

    /**
     * Returns the list of the current search points
     * @return List containing the current search points
     */
    public List<OntResource> getSearchPoints();

    /**
     * Returns the list of URI of the current search points
     * @return List containing the URI of the current search points
     */
    public List<String> getSearchPointsURI();
}
