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
package org.ontspace.nav.owl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import org.ontspace.Session;
import org.ontspace.nav.NavigationalQueryManager;
import org.ontspace.nav.owl.util.NavigationalConfiguration;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.owl.QueryManagerImpl;

/**
 * Implementation for a Navigational Query Manager
 *
 */
public class NavigationalQueryManagerImpl
        extends QueryManagerImpl
        implements NavigationalQueryManager {

    /** MetadataRepository where the ontologies are stored */
    private MetadataRepositoryImpl _rep;
    /** Associated NavigationalSession */
    private NavigationalSessionImpl _session;
    /** Object used for the launching of queries */
    private NavigationalOntologyQuery _ontq;
    /** Ontologies' URI list */
    private List<URI> _uriList;
    /** Ontology models' list */
    private HashMap<String, OntModel> _onts = null;
    /** Interest points of the navigational manager */
    private ArrayList<OntResource> _interestPoints;
    /** Search points of the navigational manager */
    private ArrayList<OntResource> _searchPoints;

    /**
     * Creates a new instance of NavigationalQueryManagerImpl
     * @param rep Repository
     * @param params Parameters HashMap for the specific configuration
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.NoSuchMethodException
     * @throws org.jdom.JDOMException
     * @throws java.io.IOException
     */
    public NavigationalQueryManagerImpl(MetadataRepositoryImpl rep,
            HashMap params)
            throws ClassNotFoundException,
            NoSuchMethodException,
            JDOMException,
            IOException {
        super(rep);
        this._rep = rep;
        this._interestPoints = new ArrayList<OntResource>();
        this._searchPoints = new ArrayList<OntResource>();
        this._uriList = new ArrayList<URI>();
        this._onts = new HashMap<String, OntModel>();
        addRequiredOntologies(params);
    }

    /**
     * Adds the required ontology to the ontologies' list
     * @param ontName name of the ontology
     * @param ont Ontology to be added into the list
     */
    @Override
    public void addOntologyRef(String ontName, OntModel ont) {
        if (ont != null) {
            _onts.put(ontName, ont);
        }
    }

    /**
     * Gets the ontologies' list  required for this query manager
     * @return Ontologies URI required for this query manager
     */
    public List<URI> getOntologiesURI() {
        return _uriList;
    }

    /**
     * Adds the required ontologies' URI to the object
     * @param params ontologiesInfo HashMap<URI,OntFile.owl>
     */
    private void addRequiredOntologies(HashMap<String, String> ontologiesInfo) {
        String ontologyUri;
        Set<String> uris = ontologiesInfo.keySet();
        Iterator<String> urisIt = uris.iterator();
        while (urisIt.hasNext()) {
            ontologyUri = urisIt.next();
            try {
                this._uriList.add(new URI(ontologyUri));
            } catch (URISyntaxException ex) {
                Logger.getLogger(NavigationalQueryManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Gets a list with the URIs of the ontologies required by the query manager
     * @return A list whith the USIs of the required ontologies
     */
    @Override
    public List<URI> getRequiredOntologies() {
        return this._uriList;
    }

    /**
     * Binds the Navigational Query Manager to its associated Session
     * @param sess Session for the current navigation
     */
    public void bindToSession(Session sess) {
        this._session = (NavigationalSessionImpl) sess;
        this._session.bindToQueryManager(this);
    }

    /**
     * Gets the NavigationalOntologyQuery object associated to the ontology
     * @return NavigationalOntologyQuery object associated to the ontology
     */
    public NavigationalOntologyQuery getNavigationalOntologyQuery() {
        return this._ontq;
    }

    /**
     * Lists all the classes in the ontology
     * @return List containing the URI of the classes in the ontology
     */
    public List<String> listClasses() {
        List<OntClass> ontClasses = _ontq.listClasses();
        List<String> ontClassesURI = new ArrayList<String>();
        for (OntClass ontClass : ontClasses) {
            ontClassesURI.add(ontClass.toString());
        }
        return ontClassesURI;
    }

    /**
     * Lists all the individuals in the ontology
     * @return List containing the URI of the individuals in the ontology
     */
    public List<String> listIndividuals() {
        List<Individual> individuals = _ontq.listIndividuals();
        List<String> individualsURI = new ArrayList<String>();
        for (Individual individual : individuals) {
            individualsURI.add(individual.toString());
        }
        return individualsURI;
    }

    /**
     * Lists all the ontResources in the ontology
     * @return List containing the URI of the ontResources in the ontology
     */
    public List<String> listOntResources() {
        List<String> ontResources = new ArrayList<String>();
        ontResources.addAll(this.listClasses());
        ontResources.addAll(this.listIndividuals());
        return ontResources;
    }

    /**
     * Adds an interest point to the current session
     * @param ontResource New interest point to be added
     */
    @Override
    public void addInterestPoint(OntResource ontResource) {
        if (!this._interestPoints.contains(ontResource)) {
            this._interestPoints.add(ontResource);
        }
    }

    /**
     * Adds an interest point to the current session
     * @param ontResourceURI URI of the new interest point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     */
    @Override
    public boolean addInterestPoint(String ontResourceURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontResourceURI);
        if (ontResource != null && !this._interestPoints.contains(ontResource)) {
            this.addInterestPoint(ontResource);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds several interest points to the current session
     * @param ontResources New interest points to be added
     */
    @Override
    public void addInterestPoints(List<OntResource> ontResources) {
        for (OntResource or : ontResources) {
            this.addInterestPoint(or);
        }
    }

    /**
     * Adds several interest points to the current session
     * @param ontResourcesURI URI of the new interests points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     */
    @Override
    public boolean addInterestPointsURI(List<String> ontResourcesURI) {
        boolean successful = true;
        for (String ontResourceURI : ontResourcesURI) {
            successful = successful && this.addInterestPoint(ontResourceURI);
        }
        return successful;
    }

    /**
     * Removes an interest point from the current session
     * @param ontResource Interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeInterestPoint(OntResource ontResource) {
        return this._interestPoints.remove(ontResource);
    }

    /**
     * Removes an interest point from the current session
     * @param ontResourceURI URI of the interest point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeInterestPoint(String ontResourceURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontResourceURI);
        if (ontResource != null) {
            return this.removeInterestPoint(ontResource);
        } else {
            return false;
        }
    }

    /**
     * Returns the list of the current interest points
     * @return List containing the current interest points
     */
    @Override
    public List<OntResource> getInterestPoints() {
        return this._interestPoints;
    }

    /**
     * Returns the list of URI of the current interest points
     * @return List containing the URI of the current interest points
     */
    @Override
    public List<String> getInterestPointsURI() {
        List<String> interestPointsURI = new ArrayList<String>();
        for (OntResource resource : this._interestPoints) {
            interestPointsURI.add(resource.toString());
        }
        return interestPointsURI;
    }

    /**
     * Adds a search point to the current session
     * @param ontResource New search point to be added
     */
    @Override
    public void addSearchPoint(OntResource ontResource) {
        if (!this._searchPoints.contains(ontResource)) {
            this._searchPoints.add(ontResource);
        }
    }

    /**
     * Adds a search point to the current session
     * @param ontResourceID ID of the new search point to be added
     * @return Boolean Logic constant determining whether the element was found
     * and added or not
     */
    @Override
    public boolean addSearchPoint(String ontResourceID) {
        OntResource ontResource = this._ontq.getOntResourceByID(ontResourceID);
        if (ontResource != null) {
            this.addSearchPoint(ontResource);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds several search points to the current session
     * @param ontResources New search points to be added
     */
    @Override
    public void addSearchPoints(List<OntResource> ontResources) {
        this._searchPoints.addAll(ontResources);
    }

    /**
     * Adds several search points to the current session
     * @param ontResourcesID ID of the new search points to be added
     * @return Boolean Logic constant determining whether the elements were
     * found and added or not
     */
    @Override
    public boolean addSearchPointsURI(List<String> ontResourcesID) {
        boolean successful = true;
        for (String ontResourceID : ontResourcesID) {
            successful = successful && this.addSearchPoint(ontResourceID);
        }
        return successful;
    }

    /**
     * Removes a search point from the current session
     * @param ontResource Search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeSearchPoint(OntResource ontResource) {
        return this._searchPoints.remove(ontResource);
    }

    /**
     * Removes a search point from the current session
     * @param ontResourceID ID of the search point to be removed
     * @return Logic constant informing whether the operation was succesfully
     * performed or not
     */
    @Override
    public boolean removeSearchPoint(String ontResourceID) {
        OntResource ontResource = this._ontq.getOntResourceByID(ontResourceID);
        if (ontResource != null) {
            return this.removeSearchPoint(ontResource);
        } else {
            return false;
        }
    }

    /**
     * Returns the list of the current search points
     * @return List containing the current search points
     */
    @Override
    public List<OntResource> getSearchPoints() {
        return this._searchPoints;
    }

    /**
     * Returns the list of URI of the current search points
     * @return List containing the URI of the current search points
     */
    @Override
    public List<String> getSearchPointsURI() {
        List<String> searchPointsURI = new ArrayList<String>();
        for (OntResource resource : this._searchPoints) {
            searchPointsURI.add(resource.toString());
        }
        return searchPointsURI;
    }

    /**
     * Gets the subelements for an arbitrary Ontology Class
     * @param ontClass Class which subelements will be fetched
     * @return List containing the subelements for the Ontology Class
     */
    public List<OntResource> getSubElements(OntClass ontClass) {
        return this._ontq.getSubElements(ontClass);
    }

    /**
     * Gets the URI of the subelements for an arbitrary Ontology Class
     * @param ontClassURI URI of the class which subelements will be fetched
     * @return List containing the URI of the subelements for the Ontology Class
     */
    public List<String> getSubElements(String ontClassURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontClassURI);
        if (ontResource != null && ontResource.isClass()) {
            List<OntResource> ontResources =
                    this.getSubElements(ontResource.asClass());
            List<String> subElements = new ArrayList<String>();
            for (OntResource resource : ontResources) {
                subElements.add(resource.toString());
            }
            return subElements;
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Gets the parent classes for an arbitrary ontology resource
     * @param ontResource Ontology resource which parent classes will be fetched
     * @return List containing the parent classes for the ontology resource
     */
    public List<OntClass> getParentClasses(OntResource ontResource) {
        return this._ontq.getParentClasses(ontResource);
    }

    /**
     * Gets the URI of the parent classes for an arbitrary ontology resource
     * @param ontResourceURI URI of the ontology resource which parent classes
     * will be fetched
     * @return List containing the URI of the parent classes for the ontology
     * resource
     */
    public List<String> getParentClasses(String ontResourceURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontResourceURI);
        if (ontResource != null) {
            List<OntClass> ontClasses = this.getParentClasses(ontResource);
            List<String> parentClasses = new ArrayList<String>();
            for (OntClass ontClass : ontClasses) {
                parentClasses.add(ontClass.toString());
            }
            return parentClasses;
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Gets the related OntResources for an arbitrary OntResource
     * @param ontResource OntResource which related OntResources will be fetched
     * @return List containing the related OntResources for the OntResource
     */
    public List<OntResource> getRelatedOntResources(OntResource ontResource) {
        return this._ontq.getRelatedOntResources(ontResource);
    }

    /**
     * Gets the URI for the related OntResources for an arbitrary OntResource
     * @param ontResourceURI URI for the OntResource which related OntResources
     * will be fetched
     * @return List containing the URI for the related OntResources for the
     * OntResource
     */
    public List<String> getRelatedOntResources(String ontResourceURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontResourceURI);
        if (ontResource != null) {
            List<OntResource> ontResources =
                    this.getRelatedOntResources(ontResource);
            List<String> relatedResources = new ArrayList<String>();
            if (ontResources != null) {
                for (OntResource resource : ontResources) {
                    relatedResources.add(resource.toString());
                }
                return relatedResources;
            } else {
                return new ArrayList<String>();
            }
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Gets the number of related OntResources for an arbitrary OntResource
     * @param ontResource OntResource which related OntResources' size will be
     * fetched
     * @return Number of related OntResources for an arbitrary OntResource
     */
    public int getRelatedOntResourcesSize(OntResource ontResource) {
        return this._ontq.getRelatedOntResourcesSize(ontResource);
    }

    /**
     * Gets the related OntClasses for an arbitrary OntClass
     * @param ontClass OntClass which related elements will be fetched
     * @return List containing the related OntClasses for the OntClass
     */
    public List<OntResource> getRelatedOntClasses(OntClass ontClass) {
        return this._ontq.getRelatedOntClasses(ontClass);
    }

    /**
     * Gets the related OntClasses for an arbitrary OntClass URI
     * @param ontClassURI OntClass URI which related elements will be fetched
     * @return List containing the related OntClasses for the OntClass URI
     */
    public List<String> getRelatedOntClasses(String ontClassURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(ontClassURI);
        if (ontResource != null && ontResource.isClass()) {
            List<OntResource> ontResources =
                    this.getRelatedOntClasses(ontResource.asClass());
            List<String> relatedResources = new ArrayList<String>();
            if (ontResources != null) {
                for (OntResource resource : ontResources) {
                    relatedResources.add(resource.toString());
                }
                return relatedResources;
            } else {
                return new ArrayList<String>();
            }
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Gets the related Individuals for an arbitrary Individual
     * @param individual Individual which related elements will be fetched
     * @return List containing the related Individuals for the Individual
     */
    public List<OntResource> getRelatedIndividuals(Individual individual) {
        return this._ontq.getRelatedIndividuals(individual);
    }

    /**
     * Gets the related Individuals for an arbitrary Individual URI
     * @param individualURI Individual URI which related elements will be
     * fetched
     * @return List containing the related Individuals for the Individual URI
     */
    public List<String> getRelatedIndividuals(String individualURI) {
        OntResource ontResource = this._ontq.getOntResourceByURI(individualURI);
        if (ontResource != null && ontResource.isIndividual()) {
            List<OntResource> ontResources =
                    this.getRelatedIndividuals(ontResource.asIndividual());
            List<String> relatedResources = new ArrayList<String>();
            if (ontResources != null) {
                for (OntResource resource : ontResources) {
                    relatedResources.add(resource.toString());
                }
                return relatedResources;
            } else {
                return new ArrayList<String>();
            }
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Gets the names of the relationships between two OntResources
     * @param resource First resource, the "parent" in the relationship
     * @param related Second resource, the "child" in the relationship
     * @return List containing the names of the relationships between the
     * OntResources
     */
    public List<String> getRelations(OntResource resource, OntResource related) {
        return this._ontq.getRelations(resource, related);
    }

    /**
     * Gets the names of the relationships between two OntResources
     * @param resourceURI First resource URI, the "parent" in the relationship
     * @param relatedURI Second resource URI, the "child" in the relationship
     * @return List containing the names of the relationships between the
     * OntResources
     */
    public List<String> getRelations(String resourceURI, String relatedURI) {
        OntResource resource = this._ontq.getOntResourceByURI(resourceURI);
        OntResource related = this._ontq.getOntResourceByURI(relatedURI);
        if (resource != null && related != null) {
            return this.getRelations(resource, related);
        } else {
            return null;
        }
    }

    /**
     * Reads the entry points from the configuration file
     * @param confFilePath Path to the configuration file
     * @throws org.jdom.JDOMException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void readConfigurationFile(String confFilePath)
            throws JDOMException, IOException, ClassNotFoundException {

        this._ontq = new NavigationalOntologyQuery(this._onts, this._uriList);

        // Creates a new instance of configuration
        NavigationalConfiguration conf =
                new NavigationalConfiguration(confFilePath);

        // Gets the OntResources read in the configuration file
        ArrayList<String> entryOntResources =
                (ArrayList<String>) conf.getOntResources();
        for (Iterator iter = entryOntResources.iterator(); iter.hasNext();) {
            OntResource ontResource = this._ontq.getOntResourceByURI((String) iter.next());
            if (ontResource != null) {
                this._interestPoints.add(ontResource);
            }
        }

        // Gets the blacklisted terms for relations' range
        this._ontq.setBlacklistedTerms(conf.getBlacklistedTerms());

    }

    /**
     * Gets the associated Navigational Session for the Query Manager
     * @return Associated Navigational Session for the Query Manager
     */
    public NavigationalSessionImpl getSession() {
        return this._session;
    }
}
