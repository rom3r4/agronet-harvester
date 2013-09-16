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
package org.ontspace.resource.owl;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.owl.QueryManagerImpl;
import org.ontspace.MetadataRecordReference;
import org.ontspace.owl.MetadataRecordReferenceFactory;
import org.ontspace.owl.MetadataRecordReferenceImpl;
import org.ontspace.resource.ResourceQueryManager;

/**
 *
 */
public class ResourceQueryManagerImpl extends QueryManagerImpl
    implements ResourceQueryManager {

    private static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    private String _resourece2owlNamespace =
        "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _resource2owlOntModel;

    /**
     * Creates a new instance of ResourceQueryManagerImpl
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public ResourceQueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
        throws ClassNotFoundException, NoSuchMethodException {
        super(rep);
        _rep = rep;
        this._uriList = new ArrayList<URI>();
        addRequiredOntologies(params);
        this._onts = new HashMap<String, OntModel>();

    }

    /**
     * Adds the required ontology to the ontologies' list
     * @param ontName name of the ontology
     * @param ont Ontology to be added into the list
     */
    @Override
    public void addOntologyRef(String ontName, OntModel ont) {
        if (ont != null) {
            this._onts.put(ontName, ont);
        }
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
                Logger.getLogger(ResourceQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Gets a list with the URIs of the ontologies required by the query manager
     * @return A list with the USIs of the required ontologies
     */
    @Override
    public List<URI> getRequiredOntologies() {
        return this._uriList;
    }

    /**
     * Gets the ontology model asociated to queryManager
     * @return the ontology model
     * @deprecated getOntologyModel(String ontName) should be used instead
     * this method
     */
    @Override
    @Deprecated
    public OntModel getOntologyModel() {
        OntModel ontmodel = null;
        Iterator<String> ontNamesIt = _onts.keySet().iterator();
        String ontName;
        if (ontNamesIt.hasNext()) {
            ontName = ontNamesIt.next();
            ontmodel = _onts.get(ontName);
        }
        return ontmodel;
    }

    /**
     * Gets the ontology model asociated to queryManager
     * @param ontName the name of the ontology
     * @return the ontology model
     */
    @Override
    public OntModel getOntologyModel(String ontName) {
        OntModel ontModel = null;
        ontModel = _onts.get(ontName);
        if (ontModel == null) {
            System.out.println(ResourceQueryManagerImpl.class.toString()
                + " ERROR: "
                + "the ontology '" + ontName
                + "' is not present in the repository");
        }

        return ontModel;
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param language the language to search
     * @return ResourceQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public ResourceQueryResultImpl queryResourceByLanguage(String language) {
        return this.queryGeneralAttribute("language", language);
    }

    @Override
    public ResourceQueryResultImpl queryResourceBySubject(String subject) {
        return this.queryGeneralAttribute("subject", subject);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param title the title to search
     * @return ResourceQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public ResourceQueryResultImpl queryResourceByTitle(String title) {
        return this.queryGeneralAttribute("title", title);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param type the type to search
     * @return ResourceQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public ResourceQueryResultImpl queryResourceByType(String type) {

        return this.queryGeneralAttribute("type", type);
    }

    /**
     * Launches a query for a general attribute in the ontology
     * @param elementType name of the element attribute
     * @param valueAttribute Attribute value
     * @return ResourceQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    private ResourceQueryResultImpl queryGeneralAttribute(String elementType,
        String valueAttribute) {

        _resource2owlOntModel = this.getOntologyModel("resourceont");

        Resource res = new ResourceImpl(_resourece2owlNamespace + "#Resource");

        ExtendedIterator<Individual> extItRes = _resource2owlOntModel.
            listIndividuals(res);

        ResourceQueryResultImpl queryResult =
            new ResourceQueryResultImpl();
        Set<MetadataRecordReference> instancesR =
            new HashSet<MetadataRecordReference>();

        while (extItRes.hasNext()) {
            Individual resInd = extItRes.next();
        }
        return queryResult;
    }

    /**
     * Close repository
     */
    @Override
    public void close() {
        ResourceQueryManagerImpl._rep.close();
        ResourceQueryManagerImpl._rep = null;
    }

    /**
     * Gets a LOM object, inserts its information in a created instance, and
     * stores that instance in the repository.
     * @param resource the learning object to store
     * @return the learning object reference of the stored learning object.
     *
     */
    public MetadataRecordReference storeNewDublinCore(
        org.ontspace.resource.owl.Resource resource) {

        this._resource2owlOntModel = _onts.get("resourceont");
        // Create new Resource:
        Individual newResourceInstance = createNewResourceInstance();

        //System.out.println("Creando instancia");
        createProperty(newResourceInstance, resource.getTitle(), "title");
        createProperty(newResourceInstance, resource.getLanguage(), "language");
        createProperty(newResourceInstance, resource.getSubject(), "subject");
        createProperty(newResourceInstance, resource.getType(), "type");

        MetadataRecordReference newResourceRef =
            new MetadataRecordReferenceImpl(
            _rep.getRepositoryURI(), newResourceInstance.getURI());

        return newResourceRef;
    }

    /**
     * Creates a new Learning Object instance.
     * @return the learning object individual instance
     */
    protected Individual createNewResourceInstance() {
        // Load the learningObject concept and the ontology:

        OntClass resourceClass = _resource2owlOntModel.getOntClass(_resourece2owlNamespace
            + "#Resource");

        MetadataRecordReference newRef =
            MetadataRecordReferenceFactory.createLearningObjectReference(_rep.
            getRepositoryURI());

        Individual resourceInd = _resource2owlOntModel.createIndividual(_resourece2owlNamespace
            + "#resource-" + newRef.getLocalMetadataRecordId(), resourceClass);
        return resourceInd;

    }

    private void createProperty(Individual newResourceInstance, String title,
        String prop) {

        if (title.compareTo("") != 0) {
            RDFNode titleValue = _resource2owlOntModel.createTypedLiteral(title);
            DatatypeProperty titleProp = _resource2owlOntModel.
                getDatatypeProperty(_resourece2owlNamespace
                + "#" + prop);
            newResourceInstance.setPropertyValue(titleProp, titleValue);
        }
    }
}
