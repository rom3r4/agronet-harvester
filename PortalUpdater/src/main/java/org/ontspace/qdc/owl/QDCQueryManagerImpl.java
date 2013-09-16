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
package org.ontspace.qdc.owl;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.doomdark.uuid.UUIDGenerator;
import org.ontspace.MetadataRecordReference;
import org.ontspace.dc.owl.DCQueryManagerImpl;
import org.ontspace.owl.MetadataRecordReferenceFactory;
import org.ontspace.owl.MetadataRecordReferenceImpl;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.qdc.QDCQueryManager;
import org.ontspace.qdc.translator.QualifiedDublinCore;

public class QDCQueryManagerImpl extends DCQueryManagerImpl
        implements QDCQueryManager {

    private static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    private String _qdc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/qdc2owl";
    private String _dc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/dc2owl";
    private String _resourceontNamespace = "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _qdc2owlOntModel;

    /**
     * Creates a new instance of QDCQueryManagerImpl
     *
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public QDCQueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
            throws ClassNotFoundException, NoSuchMethodException {
        super(rep, params);
        _rep = rep;
        this._uriList = new ArrayList<URI>();
        addRequiredOntologies(params);
        _onts = new HashMap<String, OntModel>();

    }

    /**
     * Adds the required ontology to the ontologies' list
     *
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
     * Adds the required ontologies' URI to the object
     *
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
                Logger.getLogger(QDCQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Gets a list with the URIs of the ontologies required by the query manager
     *
     * @return A list with the USIs of the required ontologies
     */
    @Override
    public List<URI> getRequiredOntologies() {
        return this._uriList;
    }

    /**
     * Gets the ontology model asociated to queryManager
     *
     * @return the ontology model
     * @deprecated getOntologyModel(String ontName) should be used instead this
     * method
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
     *
     * @param ontName the name of the ontology
     * @return the ontology model
     */
    @Override
    public OntModel getOntologyModel(String ontName) {
        OntModel ontModel = null;
        ontModel = _onts.get(ontName);
        if (ontModel == null) {
            System.out.println(QDCQueryManagerImpl.class.toString() + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param bibliographicCitation the contributor to search
     * @return QDCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public QDCQueryResultImpl queryQDCByBibliographicCitation(
            String bibliographicCitation) {
        return this.queryGeneralAttribute("bibliographicCitation",
                bibliographicCitation);
    }

    /**
     * Launches a query for a general attribute in the ontology
     *
     * @param elementType name of the element attribute
     * @param valueAttribute Attribute value
     * @return QDCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    public QDCQueryResultImpl queryGeneralAttribute(String elementType,
            String valueAttribute) {

        _qdc2owlOntModel = this.getOntologyModel("qdc2owl");

        Resource resDC = new ResourceImpl(_qdc2owlNamespace + "#Resource");

        ExtendedIterator<Individual> extItRes = _qdc2owlOntModel.listIndividuals(
                resDC);

        QDCQueryResultImpl queryResult = new QDCQueryResultImpl();
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
        QDCQueryManagerImpl._rep.close();
        QDCQueryManagerImpl._rep = null;
    }

    /**
     * Gets a LOM object, inserts its information in a created instance, and
     * stores that instance in the repository.
     *
     * @param lo the learning object to store
     * @param confFile the configuratio file with repository options
     * @return the learning object reference of the stored learning object.
     *
     */
    public MetadataRecordReference storeNewQualifiedDublinCore(QualifiedDublinCore qdc) throws
            Exception {


        this._qdc2owlOntModel = _onts.get("qdc2owl");
        // Create new QDC:
        Individual newQDCInstance = createNewQDCInstance();


        createQDCLiteralProperty(newQDCInstance, qdc.getIdentifier(), "identifier", _resourceontNamespace);
        //  createQDCLiteralProperty(newQDCInstance, qdc.getTitle(), "title", _resourceontNamespace);
        createQDCLanguageProperty(newQDCInstance, qdc.getLanguage(), "language", _resourceontNamespace);
        // createQDCLiteralProperty(newQDCInstance, qdc.getDescription(), "description", _resourceontNamespace);
        // createQDCLiteralProperty(newQDCInstance, qdc.getSubject(), "subject", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getCoverage(), "coverage", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getType(), "type", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getDate(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getCreator(), "creator", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getContributor(), "contributor", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getPublisher(), "publisher", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getFormat(), "format", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getRights(), "rights", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getRelation(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getSource(), "source", _resourceontNamespace);

        //refinements
        //   createQDCLiteralProperty(newQDCInstance, qdc.getAlternative(), "titleAlternative", _qdc2owlNamespace);
        //   createQDCLiteralProperty(newQDCInstance, qdc.getTableOfContents(), "descriptionTableOfContents", _qdc2owlNamespace);
        //   createQDCLiteralProperty(newQDCInstance, qdc.getAbstract(), "descriptionAbstract", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getCreated(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getValid(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getAvailable(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIssued(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getModified(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getDateAccepted(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getDateCopyrighted(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getDateSubmited(), "date", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getExtent(), "formatExtent", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getMedium(), "formatMedium", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getBibliographicCitation(), "identifierBibliographicCitation", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsVersionOf(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getHasVersion(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsReplacedBy(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getReplaces(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsRequiredBy(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getRequires(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsPartOf(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getHasPart(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsReferencedBy(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getReferences(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getIsFormatOf(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getHasFormat(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getConformsTo(), "relation", _resourceontNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getSpatial(), "coverageSpatial", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getTemporal(), "coverageTemporal", _qdc2owlNamespace);
        //createQDCLiteralProperty(newQDCInstance, qdc.getAccessRights(), "rightsAccessRights", _qdc2owlNamespace);
        //createQDCLiteralProperty(newQDCInstance, qdc.getLicense(), "rightsLicense", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getMediator(), "audienceMediator", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getEducationLevel(), "audienceEducationLevel", _qdc2owlNamespace);
//
//
//         //qualifiers elements
        createQDCLiteralProperty(newQDCInstance, qdc.getAudience(), "audience", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getProvenance(), "provenance", _qdc2owlNamespace);
        createQDCLiteralProperty(newQDCInstance, qdc.getRightsHolder(), "rightsHolder", _qdc2owlNamespace);


        MetadataRecordReference newDCRef =
                new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newQDCInstance.getURI());

        return newDCRef;
    }

    /**
     * Creates a new Learning Object instance.
     *
     * @return the learning object individual instance
     */
    protected Individual createNewQDCInstance() {
        // Load the learningObject concept and the ontology:

        OntClass dcClass = _qdc2owlOntModel.getOntClass(_qdc2owlNamespace
                + "#QualifiedDublinCoreResource");


        MetadataRecordReference newRef =
                MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());


        Individual dcInd = _qdc2owlOntModel.createIndividual(_qdc2owlNamespace
                + "#qdc-" + newRef.getLocalMetadataRecordId(), dcClass);
        return dcInd;

    }

    private void createQDCLiteralProperty(Individual newDCInstance, ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {


                //obtains the literalValue OntClass
                OntClass literalValueClass = _qdc2owlOntModel.getOntClass(_resourceontNamespace + "#LiteralValue");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = literalValueClass.createIndividual(_qdc2owlNamespace
                        + "#lv-" + id);
                DatatypeProperty valueProp = _qdc2owlOntModel.getDatatypeProperty(_resourceontNamespace
                        + "#hasLabel");
                RDFNode propValue = _qdc2owlOntModel.createTypedLiteral(val);
                dcInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _qdc2owlOntModel.getObjectProperty(ns
                        + "#" + prop);

                newDCInstance.setPropertyValue(indProp, dcInd);

            }
        }
    }

    private void createQDCLanguageProperty(Individual newDCInstance, ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = _qdc2owlOntModel.getOntClass(_resourceontNamespace + "#Language");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = langValueClass.createIndividual(_qdc2owlNamespace
                        + "#lang-" + id);
                DatatypeProperty valueProp = _qdc2owlOntModel.getDatatypeProperty(_resourceontNamespace
                        + "#langLabel");
                RDFNode propValue = _qdc2owlOntModel.createTypedLiteral(val);
                dcInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _qdc2owlOntModel.getObjectProperty(ns
                        + "#" + prop);

                newDCInstance.setPropertyValue(indProp, dcInd);

            }
        }
    }
}
