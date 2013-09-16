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
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Ontology management class
 *
 */
public class NavigationalOntologyQuery {

    /** Ontologies used in the current navigational session */
    private HashMap<String, OntModel> _onts;
    /** Default ontology model to use in the navigation */
    private String _defaultOntModelGraph = "OE-OAAE";
    /** Ontology model for the ontology */
    private OntModel _defaultOntModel;
    /** Namespaces for the current ontologies */
    private List<String> _ontNS = new ArrayList<String>();
    /** Blacklisted terms for the relations' range */
    private ArrayList<String> _blacklistedTerms;

    /**
     * Creates a new instance of NavigationalOntologyQuery
     * @param onts Ontologies used in the current navigational session
     * @throws java.lang.ClassNotFoundException
     */
    public NavigationalOntologyQuery(HashMap<String, OntModel> onts,
            List<URI> uris)
            throws ClassNotFoundException {
        this._onts = onts;
        for (URI uri : uris) {
            _ontNS.add(uri.toString());
        }
        this._defaultOntModel = _onts.get(_defaultOntModelGraph);
        if (this._defaultOntModel == null) {
            Iterator iter = _onts.values().iterator();
            if (iter.hasNext()) {
                this._defaultOntModel = (OntModel) iter.next();
            }
        }
    }

    /**
     * Sets the blacklisted terms for relations' range
     * @param blacklistedTerms List containing the blacklisted terms
     */
    public void setBlacklistedTerms(List<String> blacklistedTerms) {
        this._blacklistedTerms = new ArrayList<String>();
        this._blacklistedTerms.addAll(blacklistedTerms);
    }

    /**
     * Gets the ontologies used in the current navigational session
     * @return Ontologies used in the current navigational session
     */
    public HashMap<String, OntModel> getOntModels() {
        return this._onts;
    }

    /**
     * Gets the namespaces for the current ontologies
     * @return Namespaces for the current ontologies
     */
    public List<String> getNS() {
        return this._ontNS;
    }

    /**
     * Gets the subelements for an arbitrary Ontology Class
     * @param ontClass Class which subelements will be fetched
     * @return List containing the subelements for the Ontology Class
     */
    public List<OntResource> getSubElements(OntClass ontClass) {
        List temp = new ArrayList();
        temp.addAll(getSubClasses(ontClass));
        temp.addAll(getIndividuals(ontClass));
        return temp;
    }

    /**
     * Gets the subclasses for an arbitrary Ontology Class
     * @param ontClass Class which subclasses will be fetched
     * @return List containing the subclasses for the ontology class
     */
    public List<OntClass> getSubClasses(OntClass ontClass) {
        return ontClass.listSubClasses().toList();
    }

    /**
     * Gets the size of the set containing the subclasses for an arbitrary
     * Ontology Class
     * @param ontClass Class which subclasses' set size will be fetched
     * @return Number of subclasses for an arbitrary Ontology Class
     */
    public int getSubClassesSize(OntClass ontClass) {
        return ontClass.listSubClasses().toList().size();
    }

    /**
     * Gets the parent classes for an arbitrary ontology resource
     * @param ontResource Ontology resource which parent classes will be fetched
     * @return List containing the parent classes for the ontology resource
     */
    public List<OntClass> getParentClasses(OntResource ontResource) {
        if (ontResource.isClass()) {
            return ontResource.asClass().listSuperClasses(true).toList();
        } else if (ontResource.isIndividual()) {
            return ontResource.asIndividual().listOntClasses(true).toList();
        } else {
            return null;
        }
    }

    /**
     * Gets the size of the set containing the parent classes for an arbitrary
     * ontology resource
     * @param ontResource Ontology resource which parent classes' set size will
     * be fetched
     * @return Number of parent classes for an arbitrary ontology resource
     */
    public int getParentClassesSize(OntResource ontResource) {
        return getParentClasses(ontResource).size();
    }

    /**
     * Gets the individuals for an arbitrary Ontology class
     * @param ontClass Class which individuals will be fetched
     * @return List containing the individuals for the ontology class
     */
    public List<Individual> getIndividuals(OntClass ontClass) {
        return (List<Individual>) ontClass.listInstances().toList();
    }

    /**
     * Gets the size of the set containing the individuals for an arbitrary
     * Ontology Class
     * @param ontClass Class which individuals' set size will be fetched
     * @return Number of individuals for an arbitrary Ontology Class
     */
    public int getIndividualsSize(OntClass ontClass) {
        return ontClass.listInstances().toList().size();
    }

    /**
     * Lists all the classes in the ontology
     * @return List containing the classes in the ontology
     */
    public List<OntClass> listClasses() {
        return _defaultOntModel.listClasses().toList();
    }

    /**
     * Lists all the individuals in the ontology
     * @return List containing the individuals in the ontology
     */
    public List<Individual> listIndividuals() {
        return _defaultOntModel.listIndividuals().toList();
    }

    /**
     * Gets the related OntResources for an arbitrary OntResource
     * @param ontResource OntResource which related OntResources will be fetched
     * @return List containing the related OntResources for the OntResource
     */
    public List<OntResource> getRelatedOntResources(OntResource ontResource) {
        if (ontResource.isClass()) {
            return getRelatedOntClasses(ontResource.asClass());
        } else if (ontResource.isIndividual()) {
            return getRelatedIndividuals(ontResource.asIndividual());
        } else {
            return null;
        }
    }

    /**
     * Gets the number of related OntResources for an arbitrary OntResource
     * @param ontResource OntResource which related OntResources' size will be
     * fetched
     * @return Number of related OntResources for an arbitrary OntResource
     */
    public int getRelatedOntResourcesSize(OntResource ontResource) {
        if (getRelatedOntResources(ontResource) != null) {
            return getRelatedOntResources(ontResource).size();
        } else {
            return 0;
        }
    }

    /**
     * Gets the related OntClasses for an arbitrary OntClass
     * @param ontClass OntClass which related elements will be fetched
     * @return List containing the related OntClasses for the OntClass
     */
    public List<OntResource> getRelatedOntClasses(OntClass ontClass) {
        List temp = new ArrayList();
        String _ns = ontClass.getNameSpace();
        // Iterates through the properties of the OntClass
        for (ExtendedIterator iter = ontClass.listDeclaredProperties();
                iter.hasNext();) {
            OntProperty property = (OntProperty) iter.next();
            OntResource resource = property.getRange();
            // Checks if the range is properly declared
            if (resource != null) {
                String resourceName = resource.toString();
                // Checks wheter the resource is an OntClass in the ontology
                if (!this._blacklistedTerms.contains(resourceName.replace(_ns, ""))
                        && resourceName.startsWith(_ns)) {
                    OntClass relatedClass =
                            _defaultOntModel.getOntClass(resourceName);
                    if (!temp.contains(relatedClass)) {
                        temp.add(relatedClass);
                    }
                }
            }
        }
        // Checks if there is at least one related OntClass
        if (temp.size() > 0) {
            return temp;
        } else {
            return null;
        }
    }

    /**
     * Gets the related Individuals for an arbitrary Individual
     * @param individual Individual which related elements will be fetched
     * @return List containing the related Individuals for the Individual
     */
    public List<OntResource> getRelatedIndividuals(Individual individual) {
        List temp = new ArrayList();
        String _ns = individual.getNameSpace();
        // Iterates through the properties of the Individual
        for (StmtIterator iter = individual.listProperties(); iter.hasNext();) {
            Statement statement = iter.nextStatement();
            Property predicate = statement.getPredicate();
            // Checks if the property belongs to the selected ontology
            if (predicate.toString().startsWith(_ns)) {
                RDFNode object = statement.getObject();
                // Checks if the property object is an ontology's individual
                if (!this._blacklistedTerms.contains(object.toString().replace(_ns, ""))
                        && object.toString().startsWith(_ns)) {
                    Individual relatedIndividual =
                            _defaultOntModel.getIndividual(object.toString());
                    if (!temp.contains(relatedIndividual)) {
                        temp.add(relatedIndividual);
                    }
                }
            }
        }
        // Checks if there is at least one related individual
        if (temp.size() > 0) {
            return temp;
        } else {
            return null;
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
        if (resource.isClass()) {
            return getRelations(resource.asClass(), related.asClass());
        } else if (resource.isIndividual()) {
            return getRelations(resource.asIndividual(),
                    related.asIndividual());
        } else {
            return null;
        }
    }

    /**
     * Gets the names of the relationships between two OntClasses
     * @param ontClass First OntClass, the "parent" in the relationship
     * @param related Second OntClass, the "child" in the relationship
     * @return List containing the names of the relationships between the
     * OntClasses
     */
    private List<String> getRelations(OntClass ontClass, OntClass related) {
        List temp = new ArrayList();
        // Identifier of the related OnimprimieratClass
        String relatedID = related.toString();
        // Iterates through the properties of the OntClass
        for (ExtendedIterator iter = ontClass.listDeclaredProperties();
                iter.hasNext();) {
            OntProperty property = (OntProperty) iter.next();
            OntResource range = property.getRange();
            // Checks if the range is the related OntClass
            if (range != null && range.toString().equals(relatedID)) {
                String propString = property.toString();
                temp.add(propString.substring(propString.indexOf("#") + 1));
            }
        }
        return temp;
    }

    /**
     * Gets the names of the relationships between two Individuals
     * @param individual First Individual, the "parent" in the relationship
     * @param related Second Individual, the "child" in the relationship
     * @return List containing the names of the relationships between the
     * Individuals
     */
    private List<String> getRelations(Individual individual, Individual related) {
        List temp = new ArrayList();
        // Identifier of the related Individual
        String relatedID = related.toString();
        // Iterates through the properties of the Individual
        for (StmtIterator iter = individual.listProperties(); iter.hasNext();) {
            Statement statement = iter.nextStatement();
            RDFNode object = statement.getObject();
            // Checks if the object is the related Individual
            if (object != null && object.toString().equals(relatedID)) {
                Property predicate = statement.getPredicate();
                String predString = predicate.toString();
                temp.add(predString.substring(predString.indexOf("#") + 1));
            }
        }
        return temp;
    }

    /**
     * Gets an arbitrary OntResource by its URI
     * @param uri URI of the requested OntResource
     * @return Requested OntResource
     */
    public OntResource getOntResourceByURI(String uri) {
        return _defaultOntModel.getOntResource(uri);
    }

    /**
     * Gets an arbitrary OntResource by its ID
     * @param id ID of the requested OntResource
     * @return Requested OntResource
     */
    public OntResource getOntResourceByID(String id) {
        OntResource foundResource = null;
        for (String ns : this._ontNS) {
            if (foundResource == null) {
                foundResource = this.getOntResourceByURI(ns + "#" + id);
            }
        }
        return foundResource;
    }

    /**
     * Determines whether the OntResource is present in the current model or not
     * @param ontResource OntResource which presence will be checked
     * @return Logic constant determining if the OntResource is present
     */
    public boolean hasOntResource(OntResource ontResource) {
        return (_defaultOntModel.getOntResource(ontResource.toString()) != null);
    }
}
