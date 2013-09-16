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
package org.ontspace.dc.owl;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.doomdark.uuid.UUIDGenerator;
import org.ontspace.MetadataRecordReference;
import org.ontspace.dc.DCQueryManager;
import org.ontspace.dc.translator.DublinCore;
import org.ontspace.owl.MetadataRecordReferenceFactory;
import org.ontspace.owl.MetadataRecordReferenceImpl;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.resource.owl.ResourceQueryManagerImpl;
import org.ontspace.util.Pagination;

public class DCQueryManagerImpl extends ResourceQueryManagerImpl
        implements DCQueryManager {

    private static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    //Ojo! Ultra-Hack!! Coger desde configuración
    private String _dc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/dc2owl";
    private String _resourceontNamespace =
            "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _dc2owlOntModel;

    /**
     * Creates a new instance of DCQueryManagerImpl
     *
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public DCQueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
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
                Logger.getLogger(DCQueryManagerImpl.class.getName()).
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
     * Gets the ontology model associated to queryManager
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
            System.out.println(DCQueryManagerImpl.class.toString() + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param contributor the contributor to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByContributor(String contributor) {
        return this.queryGeneralAttributeByLiteralValue("contributor", contributor);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param format the format to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByFormat(String format) {
        return this.queryGeneralAttributeByLiteralValue("format", format);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param rights the rights to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByRights(String rights) {

        return this.queryGeneralAttributeByLiteralValue("rights", rights);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param coverage the coverage to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByCoverage(String coverage) {

        return this.queryGeneralAttributeByLiteralValue("converage", coverage);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param identifier the identifier to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByIdentifier(String identifier) {

        return this.queryGeneralAttributeByLiteralValue("identifier", identifier);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param source the source to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCBySource(String source) {

        return this.queryGeneralAttributeByLiteralValue("source", source);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param creator the creator to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByCreator(String creator) {
        return this.queryGeneralAttributeByLiteralValue("creator", creator);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param language the language to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByLanguage(String language) {
        return this.queryGeneralAttribute("language", "langLabel", language);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param subject the subject to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCBySubject(String subject) {

        return this.queryGeneralAttributeByLiteralValue("subject", subject);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param agrovocTerms the agrovoc terms to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset) {
        ArrayList<MetadataRecordReference> instancesR =
                new ArrayList<MetadataRecordReference>();

        DCQueryResultImpl queryResult = new DCQueryResultImpl();

        ArrayList<String> auxThesaResults = new ArrayList<String>();
        ArrayList<String> auxIdResults = new ArrayList<String>();

        _dc2owlOntModel = this.getOntologyModel("dc2owl");


        //Query for the thesaurus instance matching the input values
        Integer recordsCounter = 0;
        for (Iterator<String> it = agrovocTerms.iterator(); it.hasNext();) {
            String subjectT = it.next();
            String Prefixes = "PREFIX dc: <" + _dc2owlNamespace + "#> \n";
            Prefixes += "PREFIX res: <" + _resourceontNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";
            String queryStringCount = Prefixes
                    + "\nSELECT (COUNT(DISTINCT(?resource)) as ?count) \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel \"" + subjectT + "\"^^xsd:string . \n"
                    + " }\n";
            recordsCounter += queryCountDC(queryStringCount);
            String queryString = Prefixes
                    + "\nSELECT ?resource \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel\"" + subjectT + "\"^^xsd:string \n"
                    + " }";

              if (limit > 0) {
                queryString += " LIMIT " + limit + "\n";
            }
            if (offset > 0) {
                queryString += " OFFSET " + offset + "\n";
            }

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

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
                Logger.getLogger(DCQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when getting the thesaurus values", e);
            } finally // QueryExecution objects should be closed to free any system resources
            {
                qe.close();
            }
        }

        //Get the resource identifier of each thesaurus and return not repeated values
        for (Iterator<String> it = auxThesaResults.iterator(); it.hasNext();) {
            String Prefixes = "PREFIX dc: <" + _dc2owlNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

            String queryString = Prefixes
                    + "\nSELECT ?resource \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  dc:subjectAgrovocTerm <" + it.next() + "> \n"
                    + " }";

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

            try {
                //Assumption: it's a SELECT query.
                ResultSet results = qe.execSelect();
                while (results.hasNext()) {

                    QuerySolution nextSolution = results.nextSolution();

                    Resource res = nextSolution.getResource("resource");
                    if (!auxIdResults.contains(res.getURI())) {
                        auxIdResults.add(res.getURI());
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception when getting the resource identifiers:" + e.getMessage());
                Logger.getLogger(DCQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when getting the resource identifiers", e);
            } finally // QueryExecution objects should be closed to free any system resources
            {
                qe.close();
            }
        }

        Collections.sort(auxIdResults);
        for (String id : auxIdResults) {
            MetadataRecordReference lor = new MetadataRecordReferenceImpl(_rep.getRepositoryURI(), id);
            instancesR.add(lor);
        }

        queryResult.setTotalResults(recordsCounter);
        queryResult.getMetadataRecordReferences().addAll(instancesR);
        return queryResult;
    }

    @Override
    public DCQueryResultImpl queryDCByAgrovocTerm(ArrayList<String> agrovocTerms) {
        DCQueryResultImpl qr = queryDCByAgrovocTerm(agrovocTerms, -1, -1);
        return qr;
    }

    private Integer queryCountDC(
            String queryString) {

        _dc2owlOntModel = this.getOntologyModel("dc2owl");


        String returnCount = "0";

        if (queryString != "") {

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

            ResultSet resultsCount;
            Resource resCount;

            try {
                resultsCount = qe.execSelect();
                if (resultsCount.hasNext()) {
                    returnCount = resultsCount.next().getLiteral("count").getValue().toString();
                }
            } catch (Exception e) {
                System.out.println("Exception when count the resources:" + e.getMessage());
                Logger.getLogger(DCQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when count the resources", e);
            } finally {
                qe.close();
            }

        }
        return Integer.parseInt(returnCount);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param date the date to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByDate(String date) {

        return this.queryGeneralAttribute("date", "value", date);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param publisher the publisher to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByPublisher(String publisher) {

        return this.queryGeneralAttributeByLiteralValue("publisher", publisher);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param title the title to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByTitle(String title) {

        return this.queryGeneralAttributeByLiteralValue("title", title);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param description the description to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByDescription(String description) {
        return this.queryGeneralAttributeByLiteralValue("description", description);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param relation the relation to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByRelation(String relation) {

        return this.queryGeneralAttributeByLiteralValue("relation", relation);
    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param type the type to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    @Override
    public DCQueryResultImpl queryDCByType(String type) {

        return this.queryGeneralAttributeByLiteralValue("type", type);
    }

    /**
     * Launches a query for a general attribute in the ontology
     *
     * @param elementType name of the element attribute
     * @param valueAttribute Attribute value
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    protected DCQueryResultImpl queryGeneralAttributeByString(String elementType,
            String valueAttribute) {

        Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

        DCQueryResultImpl queryResult =
                new DCQueryResultImpl();

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> ";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";

        String queryString =
                Prefixes + "SELECT ?resource " + "WHERE " + "{ "
                + "?resource  <" + _resourceontNamespace + "#" + elementType
                + "> ?value"
                + " FILTER (regex ( ?value,  \"" + valueAttribute + "\"))"
                + " }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

        //Execute the query and obtain results
        try {
            //Assumption: it's a SELECT query.
            ResultSet results = qe.execSelect();

            int i = 1;
            while (results.hasNext()) {
                QuerySolution sol = results.nextSolution();
                String resourceId = sol.getResource("resource").getLocalName();
                MetadataRecordReference lor =
                        new MetadataRecordReferenceImpl(
                        _rep.getRepositoryURI(),
                        resourceId);
                if (!(instancesR.contains(lor))) {
                    instancesR.add(lor);
                }

                i++;
            }

        } catch (Exception e) {
            System.out.println("A problem occurred when executing the query..."
                    + e.getMessage());
        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        queryResult.getMetadataRecordReferences().addAll(instancesR);

        System.out.println("Number of results: " + queryResult.getMetadataRecordReferences().size());
        return queryResult;

    }

    private DCQueryResultImpl queryGeneralAttribute(String elementType, String subProp,
            String valueAttribute) {
        DCQueryResultImpl queryResult = new DCQueryResultImpl();

        Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> ";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";
        String queryString =
                Prefixes + "SELECT ?object ?resource " + " WHERE " + "{ "
                + "?resource  <" + _resourceontNamespace + "#" + elementType
                + "> " + " ?object }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

        //Execute the query and obtain results
        try {
            //Assumption: it's a SELECT query.
            ResultSet results = qe.execSelect();

            int i = 1;
            while (results.hasNext()) {
                QuerySolution sol = results.nextSolution();
                Property propRes =
                        new PropertyImpl(_resourceontNamespace + "#" + subProp);
                StmtIterator stmtIt = sol.get("object").asResource().listProperties(propRes);
                Resource res = sol.get("resource").asResource();

                while (stmtIt.hasNext()) {
                    String val = stmtIt.nextStatement().getLiteral().toString();
                    if (val.contains(valueAttribute)) {


                        String resourceId = res.toString();
                        MetadataRecordReference lor =
                                new MetadataRecordReferenceImpl(
                                _rep.getRepositoryURI(),
                                resourceId);
                        if (!(instancesR.contains(lor))) {
                            instancesR.add(lor);
                        }

                        i++;
                    }
                }
            }

        } catch (Exception e) {

            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "A problem occurred when executing the query...", e);

        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        queryResult.getMetadataRecordReferences().addAll(instancesR);

        System.out.println("Number of results: " + queryResult.getMetadataRecordReferences().size());
        return queryResult;

    }

    /**
     * Launches a query for a particular element in the ontology
     *
     * @param rights the rights to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    public DCQueryResultImpl queryGeneralAttributeByLiteralValue(String elementType,
            String valueAttribute) {

        Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

        DCQueryResultImpl queryResult =
                new DCQueryResultImpl();

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> ";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";
        String queryString =
                Prefixes + "SELECT ?object ?resource " + " WHERE " + "{ "
                + "?resource  <" + _resourceontNamespace + "#" + elementType
                + "> " + " ?object }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, _dc2owlOntModel);

        //Execute the query and obtain results
        try {
            //Assumption: it's a SELECT query.
            ResultSet results = qe.execSelect();

            int i = 1;
            while (results.hasNext()) {
                QuerySolution sol = results.nextSolution();
                Property propRes =
                        new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");
                StmtIterator stmtIt = sol.get("object").asResource().listProperties(propRes);
                Resource res = sol.get("resource").asResource();

                while (stmtIt.hasNext()) {
                    String val = stmtIt.nextStatement().getLiteral().toString();
                    if (val.contains(valueAttribute)) {


                        String resourceId = res.toString();
                        MetadataRecordReference lor =
                                new MetadataRecordReferenceImpl(
                                _rep.getRepositoryURI(),
                                resourceId);
                        if (!(instancesR.contains(lor))) {
                            instancesR.add(lor);
                        }

                        i++;
                    }
                }
            }

        } catch (Exception e) {

            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "A problem occurred when executing the query...", e);

        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        queryResult.getMetadataRecordReferences().addAll(instancesR);

        System.out.println("Number of results: " + queryResult.getMetadataRecordReferences().size());
        return queryResult;

    }

    /**
     * Launches a query to get the value of the literal attribute of an
     * specified resource existing in the repository
     *
     * @param elementType the metadata element to be retrieved
     * @param resId the resource identifier
     * @return an ArrayList containing all the values for the element selected
     */
    protected ArrayList<String> getLiteralValue(String elementType, String resId) {

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + elementType);

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String value = new String();
            while (nodeIt.hasNext()) {

                Resource res = nodeIt.next().asResource();

                if (res.hasProperty(propVal)) {
                    value = res.getProperty(propVal).getLiteral().getString();
                    result.add(value);
                }
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }

    /**
     * Launches a query for an attribute value of an specified resource existing
     * in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified
     * attribute of the specified resource
     */
    protected HashMap<String, String> getLiteralValueWithLang(String elementType,
            String resId) {

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        HashMap<String, String> result = new HashMap<String, String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + elementType);

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String label = new String();
            String lang = new String();
            while (nodeIt.hasNext()) {

                Resource res = nodeIt.next().asResource();

                StmtIterator stmtIt = res.listProperties(propVal);

                while (stmtIt.hasNext()) {
                    Statement statement = stmtIt.next();

                    lang = statement.getLanguage();
                    label = statement.getString();
                    result.put(lang, label);
                }
            }

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            // System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        return result;
    }

    /**
     * Launches a query for an attribute value of an specified resource existing
     * in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified
     * attribute of the specified resource
     */
    protected HashMap<String, ArrayList<String>> getMultipleLiteralValueWithLang(String elementType,
            String resId) {
        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        ArrayList<String> subjectsLang = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + elementType);

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String label = new String();
            String lang = new String();
            while (nodeIt.hasNext()) {

                Resource res = nodeIt.next().asResource();

                StmtIterator stmtIt = res.listProperties(propVal);

                while (stmtIt.hasNext()) {
                    Statement statement = stmtIt.next();
                    lang = statement.getLanguage();
                    label = statement.getString();
                    subjectsLang = result.get(lang);
                    if (subjectsLang == null) {
                        subjectsLang = new ArrayList<String>();
                    }
                    subjectsLang.add(label);
                    result.put(lang, subjectsLang);
                    //subjectsInEn.add(statement.getObject().asLiteral().getString());

                }

            }

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        return result;
    }

    /**
     * Launches a query for an attribute value of an specified resource existing
     * in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @param lang the language for the attribute
     * @return the values for the specified attribute of the specified resource
     * with the specified language
     */
    protected ArrayList<String> getLiteralValueWithLang(String elementType,
            String resId, String lang) {

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();
        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + elementType);

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        }

        NodeIterator resPropIt = resInd.listPropertyValues(propRes);

        while (resPropIt.hasNext()) {
            Resource resProp = resPropIt.next().asResource();


            try {

                Property propVal =
                        new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");

                StmtIterator stmtIt = resProp.listProperties(propVal);
                while (stmtIt.hasNext()) {
                    Statement statement = stmtIt.next();
                    String auxLang = statement.getLanguage();
                    String label = statement.getString();
                    if (auxLang.compareTo(lang) == 0) {
                        result.add(label);
                    }
                }

            } catch (NullPointerException npe) {
                //System.out.println("Null pointer exception: " + propRes
                //        + " not assigned to the resource.");
                Logger.getLogger(DCQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

            }
        }

        return result;
    }

    /**
     * Gets the contributor values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    public ArrayList<String> getContributors(String resId) {
        return this.getLiteralValue("contributor", resId);
    }

    /**
     * Gets the format values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    public ArrayList<String> getFormats(String resId) {
        return this.getLiteralValue("format", resId);
    }

    /**
     * Gets the right values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    public ArrayList<String> getRights(String resId) {

        return this.getLiteralValue("rights", resId);
    }

    /**
     * Gets the coverage values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    public ArrayList<String> getCoverages(String resId) {

        return this.getLiteralValue("coverage", resId);
    }

    /**
     * Gets the identifier values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    public ArrayList<String> getIdentifiers(String resId) {

        return this.getLiteralValue("identifier", resId);
    }

    /**
     * Gets the source values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    public ArrayList<String> getSources(String resId) {

        return this.getLiteralValue("source", resId);
    }

    /**
     * Gets the creator values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getCreators(String resId) {
        return this.getLiteralValue("creator", resId);
    }

    /**
     * Gets the language values for a particular element in the ontology
     *
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getLanguages(String resId) {

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + "language");

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "langLabel");

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String value = new String();
            while (nodeIt.hasNext()) {

                Resource res = nodeIt.next().asResource();

                value = res.getProperty(propVal).getLiteral().getString();
                result.add(value);
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }

    /**
     * Gets the subject values in the specified language for a particular
     * element in the ontology
     *
     * @param resId the subject to search
     * @param lang the language for the element
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getSubjects(String resId, String lang) {

        return this.getLiteralValueWithLang("subject", resId, lang);
    }

    /**
     * Gets the subject values for a particular element in the ontology for the
     * specified language
     *
     * @param resId the subject to search
     * @return HashMap<String, String>with the values
     */
    @Override
    public HashMap<String, ArrayList<String>> getSubjects(String resId) {

        return this.getMultipleLiteralValueWithLang("subject", resId);
    }

    /**
     * Gets the agrovoc terms values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getAgrovocTerms(String resId) {

        return this.getLiteralValue("subjectAgrovocTerm", resId);
    }

    /**
     * Gets the date values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getDates(String resId) {

        this._dc2owlOntModel = this.getOntologyModel("dc2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + "date");

        try {
            resInd = _dc2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "value");

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String value = new String();
            while (nodeIt.hasNext()) {

                Resource res = nodeIt.next().asResource();

                value = res.getProperty(propVal).getLiteral().getString();
                result.add(value);
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(DCQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;
    }

    /**
     * Gets the publisher values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getPublishers(String resId) {

        return this.getLiteralValue("publisher", resId);
    }

    /**
     * Gets the title values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
    @Override
    public HashMap<String, String> getTitles(String resId) {

        return this.getLiteralValueWithLang("title", resId);
    }

    /**
     * Gets the title values for a particular element in the ontology in the
     * specified language
     *
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getTitles(String resId, String lang) {

        return this.getLiteralValueWithLang("title", resId, lang);
    }

    /**
     * Gets the description values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @return ArrayList<String> with the values
     */
    @Override
    public HashMap<String, String> getDescriptions(String resId) {

        return this.getLiteralValueWithLang("description", resId);
    }

    /**
     * Gets the description values for a particular element in the ontology in
     * the specified language
     *
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getDescriptions(String resId, String lang) {

        return this.getLiteralValueWithLang("description", resId, lang);
    }

    /**
     * Gets the relation value for a particular element in the ontology
     *
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getRelations(String resId) {

        return this.getLiteralValue("relation", resId);
    }

    /**
     * Gets the type values for a particular element in the ontology
     *
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getTypes(String resId) {

        return this.getLiteralValue("type", resId);
    }

    /**
     * Close repository
     */
    @Override
    public void close() {
        DCQueryManagerImpl._rep.close();
        DCQueryManagerImpl._rep = null;
    }

    /**
     * Gets a Dublin Core resource, inserts its information in a new instance,
     * and stores that instance in the repository.
     *
     * @param dc the learning object to store
     * @return the learning object reference of the stored learning object.
     * @throws Exception
     *
     */
    @Override
    public MetadataRecordReference storeNewDublinCore(DublinCore dc) throws
            Exception {

        this._dc2owlOntModel = _onts.get("dc2owl");
        // Create new DC:
        Individual newDCInstance = createNewDCInstance();
        //System.out.println("Creando instancia");
        addDCProperties(newDCInstance, dc);

        MetadataRecordReference newDCRef =
                new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newDCInstance.getURI());

        return newDCRef;
    }

    private void addDCProperties(Individual newDCInstance, DublinCore dc) {
        createLiteralProperty(newDCInstance, dc.getIdentifiers(), "identifier");
        createLiteralPropertyWithLang(newDCInstance, dc.getTitles(), "title");
        createLiteralPropertyWithLang(newDCInstance, dc.getDescriptions(), "description");
        createLiteralPropertyWithLang(newDCInstance, dc.getSubjects(), "subject");
        createLiteralProperty(newDCInstance, dc.getAgrovocTerms(), "subjectAgrovocTerm");
        createLiteralProperty(newDCInstance, dc.getTypes(), "type");
        createLanguageProperty(newDCInstance, dc.getLanguages(), "language");
        createLiteralProperty(newDCInstance, dc.getCoverages(), "coverage");
        createDateTimeProperty(newDCInstance, dc.getDates(), "date");
        createLiteralProperty(newDCInstance, dc.getCreators(), "creator");
        createLiteralProperty(newDCInstance, dc.getContributors(), "contributor");
        createLiteralProperty(newDCInstance, dc.getPublishers(), "publisher");
        createLiteralProperty(newDCInstance, dc.getFormats(), "format");
        createLiteralProperty(newDCInstance, dc.getRights(), "rights");
        createLiteralProperty(newDCInstance, dc.getRelations(), "relation");
        createLiteralProperty(newDCInstance, dc.getSources(), "source");
    }

    /**
     * Creates a new Learning Object instance.
     *
     * @return the learning object individual instance
     */
    protected Individual createNewDCInstance() {
        // Load the learningObject concept and the ontology:

        OntClass dcClass = _dc2owlOntModel.getOntClass(_dc2owlNamespace
                + "#DublinCoreResource");


        MetadataRecordReference newRef =
                MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());


        Individual dcInd = _dc2owlOntModel.createIndividual(_dc2owlNamespace
                + "#dc-" + newRef.getLocalMetadataRecordId(), dcClass);
        return dcInd;

    }

    /**
     * Creates a new LiteralValue property linked to a language. This method is
     * used to store the title, description and subject elements.
     *
     * @param newDCInstance - the dublin core instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    private void createLiteralPropertyWithLang(Individual newDCInstance,
            HashMap propValues, String prop) {

        Iterator itKeys = propValues.keySet().iterator();

        DatatypeProperty valueProp =
                _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                + "#hasLabel");
        ObjectProperty indProp = _dc2owlOntModel.getObjectProperty(_resourceontNamespace
                + "#" + prop);

        if (itKeys.hasNext()) {
            //obtains the literalValue OntClass
            OntClass literalValueClass = _dc2owlOntModel.getOntClass(_resourceontNamespace
                    + "#LiteralValue");
            String id =
                    UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
            Individual dcInd = literalValueClass.createIndividual(_dc2owlNamespace
                    + "#lv-" + id);

            while (itKeys.hasNext()) {
                String lang = itKeys.next().toString();
                //The language will be linked with the string value
                try {
                    ArrayList<String> valueString = (ArrayList<String>) propValues.get(lang);
                    if (!valueString.isEmpty()) {
                        Iterator<String> itValues = valueString.iterator();
                        while (itValues.hasNext()) {

                            RDFNode propValueLang = _dc2owlOntModel.createLiteral(itValues.next(), lang);

                            dcInd.addProperty(valueProp, propValueLang);
                        }
                    }
                } catch (ClassCastException cce) {
                    String valueString = (String) propValues.get(lang);
                    if (!valueString.isEmpty()) {

                        RDFNode propValueLang = _dc2owlOntModel.createLiteral(valueString, lang);
                        dcInd.addProperty(valueProp, propValueLang);
                    }
                }
            }
            //assign the individual to the property
            newDCInstance.addProperty(indProp, dcInd);
        }
    }

    /**
     *
     * Creates a new LiteralValue property. This method is used to store the
     * identifier, coverage, date, creator, contributor, publisher, format,
     * rights, relation and source elements.
     *
     * @param newDCInstance - the dublin core instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     *
     */
    private void createLiteralProperty(Individual newDCInstance,
            ArrayList<String> propValues,
            String prop) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {

                //obtains the literalValue OntClass
                OntClass literalValueClass = _dc2owlOntModel.getOntClass(_resourceontNamespace
                        + "#LiteralValue");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = literalValueClass.createIndividual(_dc2owlNamespace
                        + "#lv-" + id);
//              ExtendedIterator<OntProperty> ontProperties = _onts.get("resourceont").listAllOntProperties();
//        List<OntProperty> proplist = ontProperties.toList();
//        System.out.println("__________________PROPERTIES______________________________");
//                for(OntProperty op: proplist) {
//                  System.out.println(op.toString());
//                }
//                System.out.println("_____________________________________________________");
                DatatypeProperty valueProp =
                        _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                        + "#hasLabel");
                RDFNode propValue = _dc2owlOntModel.createTypedLiteral(val);
                dcInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _dc2owlOntModel.getObjectProperty(_resourceontNamespace
                        + "#" + prop);

                newDCInstance.addProperty(indProp, dcInd);

            }
        }
    }

    /**
     * Creates a new language property for the metadata
     *
     * @param newDCInstance - the dublin core instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    private void createLanguageProperty(Individual newDCInstance,
            ArrayList<String> propValues,
            String prop) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = _dc2owlOntModel.getOntClass(_resourceontNamespace
                        + "#Language");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = langValueClass.createIndividual(_dc2owlNamespace
                        + "#lang-" + id);
                DatatypeProperty valueProp =
                        _onts.get("resourceont").getDatatypeProperty(_resourceontNamespace
                        + "#langLabel");
                RDFNode propValue = _dc2owlOntModel.createTypedLiteral(val);
                dcInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _dc2owlOntModel.getObjectProperty(_resourceontNamespace
                        + "#" + prop);

                newDCInstance.addProperty(indProp, dcInd);

            }
        }
    }

    /**
     * Creates a new language property for the metadata
     *
     * @param newDCInstance - the dublin core instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    private void createDateTimeProperty(Individual newDCInstance,
            ArrayList<String> propValues,
            String prop) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = _dc2owlOntModel.getOntClass(_resourceontNamespace
                        + "#DateTime");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual dcInd = langValueClass.createIndividual(_dc2owlNamespace
                        + "#date-" + id);
                DatatypeProperty valueProp =
                        _dc2owlOntModel.getDatatypeProperty(_resourceontNamespace
                        + "#value");
                RDFNode propValue = _dc2owlOntModel.createTypedLiteral(val);
                dcInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _dc2owlOntModel.getObjectProperty(_resourceontNamespace
                        + "#" + prop);

                newDCInstance.addProperty(indProp, dcInd);

            }
        }
    }
}
