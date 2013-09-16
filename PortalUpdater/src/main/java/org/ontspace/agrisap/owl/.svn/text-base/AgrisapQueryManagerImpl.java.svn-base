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
package org.ontspace.agrisap.owl;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.doomdark.uuid.UUIDGenerator;
import org.ontspace.MetadataRecordReference;
import org.ontspace.agrisap.AgrisapQueryManager;
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.owl.MetadataRecordReferenceFactory;
import org.ontspace.owl.MetadataRecordReferenceImpl;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.qdc.owl.QDCQueryManagerImpl;
import org.ontspace.util.Pagination;

public class AgrisapQueryManagerImpl extends QDCQueryManagerImpl
        implements AgrisapQueryManager {

    private static MetadataRepositoryImpl _rep;
    private static HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    private String _agrisap2owlNamespace =
            "http://voa3r.cc.uah.es/ontologies/agrisap2owl";
    private String _dc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/dc2owl";
    private String _qdc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/qdc2owl";
    private String _resourceontNamespace = "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _agrisap2owlOntModel;

    /**
     * Creates a new instance of AgrisapQueryManagerImpl
     *
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public AgrisapQueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
            throws ClassNotFoundException, NoSuchMethodException {
        super(rep, params);
        _rep = rep;
        this._uriList = new ArrayList<URI>();
        addRequiredOntologies(params);
        AgrisapQueryManagerImpl._onts = new HashMap<String, OntModel>();

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
                Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
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
     * Gets the ontology model associated to queryManager
     *
     * @param ontName the name of the ontology
     * @return the ontology model
     */
    @Override
    public OntModel getOntologyModel(String ontName) {
        OntModel ontModel = null;
        ontModel = _onts.get(ontName);
        if (ontModel == null) {
            System.out.println(AgrisapQueryManagerImpl.class.toString()
                    + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }

    /**
     * Launches a query for getting the resources annotated with some of the
     * input values
     *
     * @param subjectThesaurus agrovoc values
     * @param limit maximum number of results
     * @param offset first result to be retrieved
     * @return AgrisapQueryResultImpl containing the MetadataRecordReferences
     */
    @Override
    public AgrisapQueryResultImpl queryAgrisapBySubjectThesaurus(
            ArrayList<String> subjectThesaurus) {

        AgrisapQueryResultImpl unpaginatedQueryResult = queryAgrisapBySubjectThesaurus(subjectThesaurus, -1, -1);
        //return (AgrisapQueryResultImpl) Pagination.paginate(unpaginatedQueryResult, limit, offset);
        return unpaginatedQueryResult;
    }

    /**
     *
     * @param subjectThesaurus
     * @return
     */
    @Override
    public AgrisapQueryResultImpl queryAgrisapBySubjectThesaurus(
            ArrayList<String> subjectThesaurus, int limit, int offset) {

        ArrayList<MetadataRecordReference> instancesR =
                new ArrayList<MetadataRecordReference>();

        AgrisapQueryResultImpl queryResult =
                new AgrisapQueryResultImpl();

        ArrayList<String> auxThesaResults = new ArrayList<String>();
        ArrayList<String> auxIdResults = new ArrayList<String>();

        _agrisap2owlOntModel = this.getOntologyModel("agrisap2owl");


        //Query for the thesaurus instance matching the input values
        Integer recordsCounter = 0;
        for (Iterator<String> it = subjectThesaurus.iterator(); it.hasNext();) {
            String subjectT = it.next();
            
            String Prefixes = "PREFIX agris: <" + _agrisap2owlNamespace + "#> \n";
            Prefixes += "PREFIX res: <" + _resourceontNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";
            String queryStringCount = Prefixes
                    + "\nSELECT (COUNT(DISTINCT(?resource)) as ?count) \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel \"" + subjectT + "\"^^xsd:string . \n"
                    + " }\n";
            recordsCounter += queryCountAgrisap(queryStringCount);
            String queryString = Prefixes
                    + "\nSELECT ?resource \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel \"" + subjectT + "\"^^xsd:string . \n"
                    + " }\n";

            if (limit > 0) {
                queryString += " LIMIT " + limit + "\n";
            }
            if (offset > 0) {
                queryString += " OFFSET " + offset + "\n";
            }
            
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _agrisap2owlOntModel);

            try {
                //Assumption: it's a SELECT query.
                ResultSet results = qe.execSelect();
                while (results.hasNext()) {

                    QuerySolution nextSolution = results.nextSolution();
                    Resource res = nextSolution.getResource("resource");
                    //System.out.println("res:" + res.getURI());
                    if (!auxThesaResults.contains(res.getURI())) {
                        auxThesaResults.add(res.getURI());
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception when getting the thesaurus values:" + e.getMessage());
                Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when getting the thesaurus values", e);
            } finally // QueryExecution objects should be closed to free any system resources
            {
                qe.close();
            }
        }
        //Get the resource identifier of each thesaurus and return not repeated values
        for (Iterator<String> it = auxThesaResults.iterator(); it.hasNext();) {
            String subjectT2 = it.next();
            String Prefixes2 = "PREFIX agris: <" + _agrisap2owlNamespace + "#> \n";
            Prefixes2 += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";
           
            String queryString = Prefixes2
                    + "\nSELECT ?resource \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  agris:subjectThesaurus <" + subjectT2 + "> . \n"
                    + " }";


            Query query2 = QueryFactory.create(queryString);
            QueryExecution qe2 = QueryExecutionFactory.create(query2, _agrisap2owlOntModel);

            try {
                //Assumption: it's a SELECT query.
                ResultSet results = qe2.execSelect();
                while (results.hasNext()) {

                    QuerySolution nextSolution = results.nextSolution();

                    Resource res = nextSolution.getResource("resource");
                    if (!auxIdResults.contains(res.getURI())) {
                        auxIdResults.add(res.getURI());
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception when getting the resource identifiers:" + e.getMessage());
                Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when getting the resource identifiers", e);
            } finally // QueryExecution objects should be closed to free any system resources
            {
                qe2.close();
            }
        }

        Collections.sort(auxIdResults);
        for (String id : auxIdResults) {
            MetadataRecordReference lor = new MetadataRecordReferenceImpl(_rep.getRepositoryURI(), id);
            instancesR.add(lor);
        }
        queryResult.setTotalResults(recordsCounter);
        //queryResult.setTotalResults(auxIdResults.size());
        queryResult.getMetadataRecordReferences().addAll(instancesR);
        return queryResult;
    }
    
    
    private Integer queryCountAgrisap(
            String queryString) {

        _agrisap2owlOntModel = this.getOntologyModel("agrisap2owl");

        String returnCount = "0";

        if (queryString != "") {

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _agrisap2owlOntModel);

            ResultSet resultsCount;
            Resource resCount;

            try {
                resultsCount = qe.execSelect();
                if (resultsCount.hasNext()) {
                    returnCount = resultsCount.next().getLiteral("count").getValue().toString();
                }
            } catch (Exception e) {
                System.out.println("Exception when count the resources:" + e.getMessage());
                Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when count the resources", e);
            } finally {
                qe.close();
            }

        }
        return Integer.parseInt(returnCount);
    }

    /**
     * Launches a query for an attribute value of an specified resource existing
     * in the repository
     *
     * @param elementType the metadata element to be retrieved
     * @param resId the resource identifier
     * @return an ArrayList contaning all the values for the element selected
     */
    private ArrayList<String> getGeneralAttributeValue(String ns, String elementType, String resId) {


        this._agrisap2owlOntModel = this.getOntologyModel("agrisap2owl");

        ArrayList<String> result = new ArrayList<String>();

        //Query for the instance values matching the input elements

        String Prefixes = "PREFIX agris: <" + _agrisap2owlNamespace + "#> \n";
        Prefixes += "PREFIX res: <" + _resourceontNamespace + "#> \n";
        Prefixes += "PREFIX ns: <" + ns + "#> \n";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        String queryString = Prefixes
                + "\nSELECT ?obj ?label  \n"
                + "WHERE\n"
                + "{\n"
                + "<" + resId + "> <" + ns + "#" + elementType + "> ?obj .\n"
                + "?obj res:hasLabel ?label. \n"
                + " }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, _agrisap2owlOntModel);

        try {
            //Assumption: it's a SELECT query.
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {

                QuerySolution nextSolution = results.nextSolution();
                //Get the label
                RDFNode res = nextSolution.getLiteral("label");
                //RDFNode obj = nextSolution.get("obj");
                if (res != null) {
                    result.add(res.asLiteral().getString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception when getting the values:" + e.getMessage());
            Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Exception when getting the values", e);
        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        return result;

    }

    private ArrayList<String> getGeneralAttributeValueWithLang(String ns, String elementType, String resId, String lang) {

        this._agrisap2owlOntModel = this.getOntologyModel("agrisap2owl");

        ArrayList<String> result = new ArrayList<String>();

        //Query for the instance values matching the input elements

        String Prefixes = "PREFIX agris: <" + _agrisap2owlNamespace + "#> \n";
        Prefixes += "PREFIX res: <" + _resourceontNamespace + "#> \n";
        Prefixes += "PREFIX ns: <" + ns + "#> \n";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        String queryString = Prefixes
                + "\nSELECT ?obj ?label  \n"
                + "WHERE\n"
                + "{\n"
                + "<" + resId + "> <" + ns + "#" + elementType + "> ?obj .\n"
                + "?obj <" + ns + "#hasLabel> ?label. \n"
                + " }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, _agrisap2owlOntModel);

        try {
            //Assumption: it's a SELECT query.
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {

                QuerySolution nextSolution = results.nextSolution();
                //Get the label
                RDFNode res = nextSolution.getLiteral("label");
                String lan = nextSolution.getLiteral("label").getLanguage();
                if ((res != null) & (lan.compareToIgnoreCase(lang) == 0)) {
                    result.add(res.asLiteral().getString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception when getting the values:" + e.getMessage());
            Logger.getLogger(AgrisapQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Exception when getting the values", e);
        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        return result;


    }

    /**
     * Gets the title value for a particular element in the ontology in a given
     * language
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getDCTitles(String resId, String lang) {

        return this.getGeneralAttributeValueWithLang(_resourceontNamespace, "title", resId, lang);

    }

    /**
     * Gets the title value for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getDCTitles(String resId) {

        return this.getGeneralAttributeValue(_resourceontNamespace, "title", resId);

    }

    /**
     * Gets the identifier values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getDCIdentifiers(String resId) {

        return this.getGeneralAttributeValue(_resourceontNamespace, "identifier", resId);
    }

    /**
     * Gets the abstract values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getQDCAbstract(String resId) {

        return this.getGeneralAttributeValue(_qdc2owlNamespace, "descriptionAbstract", resId);
    }

    /**
     * Gets the date values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getQDCTitleAlternative(String resId) {

        return this.getGeneralAttributeValue(_qdc2owlNamespace, "titleAlternative", resId);
    }

    /**
     * Gets the date values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getQDCDates(String resId) {

        return this.getGeneralAttributeValue(_resourceontNamespace, "date", resId);
    }

    /**
     * Gets the creator values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getAgrisCreators(String resId) {

        return this.getGeneralAttributeValue(_resourceontNamespace, "creator", resId);
    }

    /**
     * Gets the classification values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getAgrisSubjectClassification(String resId) {

        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "subjectClassification", resId);

    }

    /**
     * Gets the thesaurus values for a particular element in the ontology
     *
     * @param resId the subject to search
     *
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getAgrisSubjectThesauri(String resId) {

        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "subjectThesaurus", resId);
    }

    public ArrayList<String> getSubjectClassification(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "subjectClassification", resId);
    }

    public ArrayList<String> getDescriptionNotes(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "descriptionNotes", resId);
    }

    public ArrayList<String> getDescriptionEdition(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "descriptionEdition", resId);
    }

    public ArrayList<String> getIsTranslationOf(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "isTranslationOf", resId);
    }

    public ArrayList<String> getHasTranslation(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "hasTranslation", resId);
    }

    public ArrayList<String> getAvailability(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "availability", resId);
    }

    public ArrayList<String> getAvailabilityLocation(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "availabilityLocation", resId);
    }

    public ArrayList<String> getAvailabilityNumber(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "availabilityNumber", resId);
    }

    public ArrayList<String> getRightsStatement(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "rightsStatement", resId);
    }

    public ArrayList<String> getTermsOfUse(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "termsOfUse", resId);
    }

    public ArrayList<String> getCitationTitle(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "citationTitle", resId);
    }

    public ArrayList<String> getCitation(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "citation", resId);
    }

    public ArrayList<String> getCitationIdentifier(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "citationIdentifier", resId);
    }

    public ArrayList<String> getCitationNumber(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "citationNumber", resId);
    }

    public ArrayList<String> getCitationChronology(String resId) {
        return this.getGeneralAttributeValue(_agrisap2owlNamespace, "citationChronology", resId);
    }

    @Override
    public ArrayList<String> getLanguages(String resId) {
        return this.getGeneralAttributeValue(_resourceontNamespace, "language", resId);
    }

    public ArrayList<String> getSubject(String resId) {
        return this.getGeneralAttributeValue(_resourceontNamespace, "subject", resId);
    }

    /**
     * Close repository
     */
    @Override
    public void close() {
        AgrisapQueryManagerImpl._rep.close();
        AgrisapQueryManagerImpl._rep = null;
    }

    /**
     * Gets a LOM object, inserts its information in a created instance, and
     * stores that instance in the repository.
     *
     * @param agris
     * @return the learning object reference of the stored learning object.
     * @throws Exception
     *
     */
    @Override
    public MetadataRecordReference storeNewAgrisap(Agrisap agris) throws
            Exception {

        this._agrisap2owlOntModel = _onts.get("agrisap2owl");
        // Create new AgrisAP:
        Individual newAgrisInstance = createNewAgrisapInstance();

        //System.out.println("Creando instancia");

        //Dublin Core elements
        createAgrisLiteralProperty(newAgrisInstance, agris.getIdentifier(), "identifier", _resourceontNamespace);

        // To-Do Change to literal property with Lang
        createAgrisLiteralPropertyWithLang(newAgrisInstance, agris.getTitles(), "title", _resourceontNamespace);

        //
        createAgrisLanguageProperty(newAgrisInstance, agris.getLanguage(), "language", _resourceontNamespace);
        createAgrisLiteralPropertyWithLang(newAgrisInstance, agris.getAbstracts(), "descriptionAbstract", _qdc2owlNamespace);
        //

        createAgrisLiteralProperty(newAgrisInstance, agris.getSubject(), "subject", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCoverage(), "coverage", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getType(), "type", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getDate(), "date", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCreator(), "creator", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCreatorPersonal(), "creator", _resourceontNamespace);

        createAgrisLiteralProperty(newAgrisInstance, agris.getPublisher(), "publisher", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getFormat(), "format", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getRights(), "rights", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getRelation(), "relation", _resourceontNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getSource(), "source", _resourceontNamespace);

        //Qualified Dublin Core refinements
        createAgrisLiteralProperty(newAgrisInstance, agris.getAlternative(), "titleAlternative", _qdc2owlNamespace);

        createAgrisLiteralProperty(newAgrisInstance, agris.getDateIssued(), "date", _resourceontNamespace);

//        createAgrisLiteralProperty(newAgrisInstance, agris.getExtent(), "formatExtent", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getMedium(), "formatMedium", _qdc2owlNamespace);

//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsVersionOf(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getHasVersion(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsReplacedBy(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getReplaces(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsRequiredBy(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getRequires(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsPartOf(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getHasPart(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsReferencedBy(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getReferences(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getIsFormatOf(), "relation", _qdc2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getHasFormat(), "relation", _qdc2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getSpatial(), "coverageSpatial", _qdc2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getTemporal(), "coverageTemporal", _qdc2owlNamespace);
//
//        //Agrisap specific metadata
//        createAgrisLiteralProperty(newAgrisInstance, agris.getCreatorCorporate(), "creatorCorporate", _agrisap2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getCreatorConference(), "creatorConference", _agrisap2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getPublisherName(), "publisherName", _agrisap2owlNamespace);
//        createAgrisLiteralProperty(newAgrisInstance, agris.getPublisherPlace(), "publisherPlace", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getSubjectClassification(), "subjectClassification", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getSubjectThesaurus(), "subjectThesaurus", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getDescriptionNotes(), "descriptionNotes", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getDescriptionEdition(), "descriptionEdition", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getIsTranslationOf(), "isTranslationOf", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getHasTranslation(), "hasTranslation", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getAvailability(), "availability", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getAvailabilityLocation(), "availabilityLocation", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getAvailabilityNumber(), "availabilityNumber", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getRightsStatement(), "rightsStatement", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getTermsOfUse(), "termsOfUse", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCitation(), "citation", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCitationIdentifier(), "citationIdentifier", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCitationNumber(), "citationNumber", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCitationChronology(), "citationChronology", _agrisap2owlNamespace);
        createAgrisLiteralProperty(newAgrisInstance, agris.getCitationTitle(), "citationTitle", _agrisap2owlNamespace);

        MetadataRecordReference newAgrisapRef = new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newAgrisInstance.getURI());

        return newAgrisapRef;
    }

    /**
     * Creates a new Learning Object instance.
     *
     * @return the learning object individual instance
     */
    protected Individual createNewAgrisapInstance() {
        // Load the learningObject concept and the ontology:

        OntClass agrisapClass = _agrisap2owlOntModel.getOntClass(_agrisap2owlNamespace
                + "#AgrisapResource");

        MetadataRecordReference newRef = MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());

        Individual agrisapInd = _agrisap2owlOntModel.createIndividual(_agrisap2owlNamespace
                + "#agrisap-" + newRef.getLocalMetadataRecordId(), agrisapClass);
        return agrisapInd;

    }

    private void createAgrisLiteralProperty(Individual newAgrisInstance, ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {


                //obtains the literalValue OntClass
                OntClass literalValueClass = _agrisap2owlOntModel.getOntClass(_resourceontNamespace + "#LiteralValue");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();

                Individual dcInd = literalValueClass.createIndividual(_agrisap2owlNamespace
                        + "#lv-" + id);
                DatatypeProperty valueProp = _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                        + "#hasLabel");
                RDFNode propValue = _agrisap2owlOntModel.createTypedLiteral(val);
                dcInd.addProperty(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _agrisap2owlOntModel.getObjectProperty(ns
                        + "#" + prop);

                newAgrisInstance.addProperty(indProp, dcInd);

            }
        }
    }

    private void createAgrisLanguageProperty(Individual newAgrisInstance, ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = _agrisap2owlOntModel.getOntClass(_resourceontNamespace + "#Language");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = langValueClass.createIndividual(_agrisap2owlNamespace
                        + "#lang-" + id);
                DatatypeProperty valueProp = _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                        + "#langLabel");
                RDFNode propValue = _agrisap2owlOntModel.createTypedLiteral(val);
                dcInd.addProperty(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _agrisap2owlOntModel.getObjectProperty(ns
                        + "#" + prop);

                newAgrisInstance.addProperty(indProp, dcInd);

            }
        }
    }

    /**
     * Creates a new LiteralValue property linked to a language. This method is
     * used to store the title, description and subject elements.
     *
     * @param newAgrisInstance - the dublin core instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     * @param ns - the namespace
     */
    private void createAgrisLiteralPropertyWithLang(Individual newAgrisInstance,
            HashMap propValues, String prop, String ns) {

        Iterator itKeys = propValues.keySet().iterator();
        DatatypeProperty valueProp = _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                + "#hasLabel");
        ObjectProperty indProp = _agrisap2owlOntModel.getObjectProperty(ns
                + "#" + prop);

        if (itKeys.hasNext()) {
            //obtains the literalValue OntClass
            OntClass literalValueClass = _agrisap2owlOntModel.getOntClass(_resourceontNamespace
                    + "#LiteralValue");
            String id =
                    UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
            Individual dcInd = literalValueClass.createIndividual(_agrisap2owlNamespace
                    + "#lv-" + id);

            while (itKeys.hasNext()) {
                String lang = itKeys.next().toString();
                //The language will be linked with the string value
                try {
                    ArrayList<String> valueString = (ArrayList<String>) propValues.get(lang);
                    if (!valueString.isEmpty()) {
                        Iterator<String> itValues = valueString.iterator();
                        while (itValues.hasNext()) {

                            RDFNode propValueLang = _agrisap2owlOntModel.createLiteral(itValues.next(), lang);

                            dcInd.addProperty(valueProp, propValueLang);
                        }
                    }
                } catch (ClassCastException cce) {
                    String valueString = (String) propValues.get(lang);
                    if (!valueString.isEmpty()) {
                        RDFNode propValueLang = _agrisap2owlOntModel.createLiteral(valueString, lang);
                        dcInd.addProperty(valueProp, propValueLang);
                    }
                }
            }
            //assign the individual to the property
            newAgrisInstance.addProperty(indProp, dcInd);
        }
    }
}
