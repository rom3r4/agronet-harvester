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
package org.ontspace.voa3rap2.owl;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
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
import org.ontspace.owl.QueryResultImpl;
import org.ontspace.util.Pagination;
import org.ontspace.voa3rap2.Voa3rAP2QueryManager;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

public class Voa3rAP2QueryManagerImpl extends DCQueryManagerImpl
        implements Voa3rAP2QueryManager {

    protected static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    //Ojo! Ultra-Hack!! Coger desde configuración
    private String _voa3rap22owlNamespace = "http://voa3r.cc.uah.es/ontologies/voa3rap22owl";
    private String _qdc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/qdc2owl";
    private String _eseNamespace = "http://www.europeana.eu/schemas/ese";
    private String _marcrelNamespace = "http://www.loc.gov/marc.relators";
    private String _resourceontNamespace =
            "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _voa3rap22owlOntModel;
    private String _selfOntName;

    /**
     * Creates a new instance of Voa3rAP2QueryManagerImpl
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public Voa3rAP2QueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
            throws ClassNotFoundException, NoSuchMethodException {
        super(rep, params);
        _rep = rep;
        this._uriList = new ArrayList<URI>();
        addRequiredOntologies(params);
        _onts = new HashMap<String, OntModel>();
        setSelfOntName("voa3rap22owl");
    }

    /**
     * Adds the required ontology to the ontologies' list
     * @param ontName name of the ontology
     * @param ont Ontology to be added into the list
     */
    @Override
    public void addOntologyRef(String ontName, OntModel ont) {
        if (ont != null) {
            getAllOnts().put(ontName, ont);
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
                Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
     * Gets the ontology model associated to queryManager
     * @return the ontology model
     * @deprecated getOntologyModel(String ontName) should be used instead
     * this method
     */
    @Override
    @Deprecated
    public OntModel getOntologyModel() {
        OntModel ontmodel = null;
        Iterator<String> ontNamesIt = getAllOnts().keySet().iterator();
        String ontName;
        if (ontNamesIt.hasNext()) {
            ontName = ontNamesIt.next();
            ontmodel = getAllOnts().get(ontName);
        }
        return ontmodel;
    }

    /**
     * Gets the ontology model associated to queryManager
     * @param ontName the name of the ontology
     * @return the ontology model
     */
    @Override
    public OntModel getOntologyModel(String ontName) {
        OntModel ontModel = null;
        ontModel = getAllOnts().get(ontName);
        if (ontModel == null) {
            System.out.println(Voa3rAP2QueryManagerImpl.class.toString() + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }
    
    public OntModel getSelfOntModel() {
      return getOntologyModel(this.getSelfOntName());
    }
    
    protected String getSelfOntName() {
      return this._selfOntName;
    }
    
    protected final void setSelfOntName(String ontName) {
      this._selfOntName = ontName;
    }
    
    protected HashMap<String, OntModel> getAllOnts() {
      return this._onts;
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param contributor the contributor to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByContributor(String contributor) {
        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace,"contributor", contributor);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param format the format to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByFormat(String format) {
        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "format", format);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param rights the rights to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByRights(String rights) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "rights", rights);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param coverage the coverage to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByCoverage(String coverage) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "converage", coverage);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param identifier the identifier to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIdentifier(String identifier) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "identifier", identifier);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param source the source to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2BySource(String source) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "source", source);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param creator the creator to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByCreator(String creator) {
        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "creator", creator);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param language the language to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByLanguage(String language) {
        return this.queryGeneralAttribute("language", "langLabel", language);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param subject the subject to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2BySubject(String subject) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "subject", subject);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param date the date to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByDate(String date) {

        return this.queryGeneralAttribute("date", "value",  date);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param publisher the publisher to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByPublisher(String publisher) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "publisher", publisher);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param title the title to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByTitle(String title) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "title", title);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param description the description to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByDescription(String description) {
        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "description", description);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param relation the relation to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByRelation(String relation) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "relation", relation);
    }

    /**
     *  Launches a query for  a particular element in the ontology    
     * @param type the type to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of 
     * MetadataRecordReference
     */
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByType(String type) {

        return this.queryGeneralAttributeByLiteralValue(_resourceontNamespace, "type", type);
    }

    /**
     * Launches a query for a general attribute in the ontology
     * @param elementType name of the element attribute
     * @param valueAttribute Attribute value
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    
    
    @Override
    protected Voa3rAP2QueryResultImpl queryGeneralAttributeByString(String elementType,
            String valueAttribute) {

        Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

        Voa3rAP2QueryResultImpl queryResult =
                new Voa3rAP2QueryResultImpl();

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> ";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";

        String queryString =
                Prefixes + "SELECT ?resource " + "WHERE " + "{ "
                + "?resource  <" + _resourceontNamespace + "#" + elementType
                + "> ?value" +
                " FILTER (regex ( ?value,  \""+valueAttribute+"\"))"+
                " }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, this.getSelfOntModel());
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

    private Voa3rAP2QueryResultImpl queryGeneralAttribute(String elementType, String subProp,
            String valueAttribute) {
        Voa3rAP2QueryResultImpl queryResult = new Voa3rAP2QueryResultImpl();

          Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> ";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";
        String queryString =
                Prefixes + "SELECT ?object ?resource " + " WHERE " + "{ "
                + "?resource  <" + _resourceontNamespace + "#" + elementType
                + "> " + " ?object }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, this.getSelfOntModel());

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

            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
     *  Launches a query for  a particular element in the ontology
     * @param rights the rights to search
     * @return Voa3rAP2QueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
    
    public Voa3rAP2QueryResultImpl queryGeneralAttributeByLiteralValue(String ns, String elementType,
            String valueAttribute) {

        Set<MetadataRecordReference> instancesR =
                new HashSet<MetadataRecordReference>();

        Voa3rAP2QueryResultImpl queryResult =
                new Voa3rAP2QueryResultImpl();

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");

        String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> \n";
        Prefixes += "PREFIX voa3r: <" + _voa3rap22owlNamespace + "#> \n";
        Prefixes += "PREFIX ns: <" + ns + "#> \n";
        Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

        // A limit can be included in the query to retrieve only certain number
        // of resources as maximum, for example: LIMIT 100";
        String queryString =
                Prefixes + "SELECT ?object ?resource " + " WHERE " + "{ "
                + "?resource  <" + ns + "#" + elementType
                + "> " + " ?object }";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, this.getSelfOntModel());

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

            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "A problem occurred when executing the query...", e);

        } finally // QueryExecution objects should be closed to free any system resources
        {
            qe.close();
        }

        queryResult.getMetadataRecordReferences().addAll(instancesR);

        System.out.println("Number of results: " + queryResult.getMetadataRecordReferences().size());
        return queryResult;

    }

    @Override
    protected ArrayList<String> getLiteralValue(String elementType, String resId) {
        return this.getLiteralValueNS(_resourceontNamespace, elementType, resId);
    }
    
    /**
     * Launches a query to get the value of the literal attribute of an specified 
     * resource existing in the repository
     *
     * @param elementType the metadata element to be retrieved
     * @param resId the resource identifier
     * @return an ArrayList containing all the values for the element selected
     */
    protected ArrayList<String> getLiteralValueNS(String ns, String elementType, String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);
//            System.out.println("INDIVIDUAL: "+ resInd);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");
            
//            System.out.println("propres: "+ propRes);
            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String value = new String();
            while (nodeIt.hasNext()) {
                RDFNode rdfnode = nodeIt.next();
//                System.out.println("node: "+ rdfnode);

                Resource res = rdfnode.asResource();
                if (res.hasProperty(propVal)) {
                    value = res.getProperty(propVal).getLiteral().getString();
                    result.add(value);
                }
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }

    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified attribute of the specified resource
     */
    @Override
    protected HashMap<String, String> getLiteralValueWithLang(String elementType,
            String resId) {
        return getLiteralValueWithLangNS(_resourceontNamespace, elementType, resId);
    }
    
    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified attribute of the specified resource
     */
    @Override
    protected ArrayList<String> getLiteralValueWithLang(String elementType,
            String resId, String lang) {
        return getLiteralValueWithLangNS(_resourceontNamespace, elementType, resId, lang);
    }
    
    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified attribute of the specified resource
     */
    protected HashMap<String, String> getLiteralValueWithLangNS(String ns, String elementType,
            String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        HashMap<String, String> result = new HashMap<String, String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            // System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        return result;
    }

    
    protected ArrayList<String> getString(String ns, String elementType, String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            String value = new String();
            while (nodeIt.hasNext()) {

                Literal res = nodeIt.next().asLiteral();

                value = res.getString();
                result.add(value);
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }
    
    @Override
    protected HashMap<String, ArrayList<String>> getMultipleLiteralValueWithLang(String elementType,
            String resId) {
        return this.getMultipleLiteralValueWithLangNS(_resourceontNamespace, elementType, resId);
    }
    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified attribute of the specified resource
     */
    protected HashMap<String, ArrayList<String>> getMultipleLiteralValueWithLangNS(String ns, String elementType,
            String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        ArrayList<String> subjectsLang = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        return result;
    }

    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @param lang the language for the attribute
     * @return the values for the specified attribute of the specified resource with the specified language
     */
    protected ArrayList<String> getLiteralValueWithLangNS(String ns, String elementType,
            String resId, String lang) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();
        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
                Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

            }
        }

        return result;
    }

//    /**
//     *  Gets the contributor values for  a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    public ArrayList<String> getContributors(String resId) {
//        return this.getLiteralValue(_resourceontNamespace, "contributor", resId);
//    }
//
//    /**
//     *  Gets the format values for a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    public ArrayList<String> getFormats(String resId) {
//        return this.getLiteralValue(_resourceontNamespace, "format", resId);
//    }
//
//    /**
//     *  Gets the right values for a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     *
//     */
//    public ArrayList<String> getRights(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "rights", resId);
//    }
//
//    /**
//     *  Gets the coverage values for  a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    public ArrayList<String> getCoverages(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "coverage", resId);
//    }
//
//    /**
//     *  Gets the identifier values for  a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    public ArrayList<String> getIdentifiers(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "identifier", resId);
//    }
//
//    /**
//     *  Gets the source values for  a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    public ArrayList<String> getSources(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "source", resId);
//    }
//
//    /**
//     *  Gets the creator values for  a particular element in the ontology
//     * @param resId the resource identifier
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getCreators(String resId) {
//        return this.getLiteralValue(_resourceontNamespace, "creator", resId);
//    }

    /**
     *  Gets the language values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     */
    @Override
    public ArrayList<String> getLanguages(String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + "language");

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }

//    /**
//     *  Gets the subject values in the specified language for a particular element in the ontology
//     * @param resId the subject to search
//     * @param lang the language for the element
//     * @return ArrayList<String> with the values
//     * 
//     */
//    @Override
//    public ArrayList<String> getSubjects(String resId, String lang) {
//
//        return this.getLiteralValueWithLang(_resourceontNamespace, "subject", resId, lang);
//    }
//
//    /**
//     *  Gets the subject values for a particular element in the ontology for the specified language
//     * @param resId the subject to search
//     * @return HashMap<String, String>with the values
//     */
//    @Override
//    public HashMap<String, ArrayList<String>> getSubjects(String resId) {
//
//        return this.getMultipleLiteralValueWithLang(_resourceontNamespace, "subject", resId);
//    }

    /**
     *  Gets the date values for  a particular element in the ontology
     *  @param resId the subject to search
     * @return ArrayList<String> with the values
     * 
     */
    @Override
    public ArrayList<String> getDates(String resId) {

//        this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(_resourceontNamespace + "#" + "date");

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
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
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;
    }

//    /**
//     *  Gets the publisher values for  a particular element in the ontology
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getPublishers(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "publisher", resId);
//    }
//
//    /**
//     *  Gets the title values for a particular element in the ontology
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public HashMap<String, String> getTitles(String resId) {
//
//        return this.getLiteralValueWithLang(_resourceontNamespace, "title", resId);
//    }
//
//    /**
//     *  Gets the title values for a particular element in the ontology in the
//     * specified language
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getTitles(String resId, String lang) {
//
//        return this.getLiteralValueWithLang(_resourceontNamespace, "title", resId, lang);
//    }
//
//    /**
//     *  Gets the description values for a particular element in the ontology
//     * @param resId the subject to search
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public HashMap<String, String> getDescriptions(String resId) {
//
//        return this.getLiteralValueWithLang(_resourceontNamespace, "description", resId);
//    }
//
//    /**
//     *  Gets the description values for a particular element in the ontology in the
//     * specified language
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getDescriptions(String resId, String lang) {
//
//        return this.getLiteralValueWithLang(_resourceontNamespace, "description", resId, lang);
//    }
//
//    /**
//     *  Gets the relation value for  a particular element in the ontology
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getRelations(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "relation", resId);
//    }
//
//    /**
//     *  Gets the type values for a particular element in the ontology
//     * @param resId the subject to search
//     * @param lang the language for the values
//     * @return ArrayList<String> with the values
//     */
//    @Override
//    public ArrayList<String> getTypes(String resId) {
//
//        return this.getLiteralValue(_resourceontNamespace, "type", resId);
//    }

    /**
     * Close repository
     */
    @Override
    public void close() {
        Voa3rAP2QueryManagerImpl._rep.close();
        Voa3rAP2QueryManagerImpl._rep = null;
    }

    /**
     * Gets a voa3r AP level2 resource, inserts its information in a new instance, and
     * stores that instance in the repository.
     * @param vap2 the learning object to store
     * @return the learning object reference of the stored learning object.
     * @throws Exception 
     *
     */
    @Override
    public MetadataRecordReference storeNewVoa3rAP2(Voa3rAP2 vap2) throws
            Exception {

//        this._voa3rap22owlOntModel = getOntologyModel("voa3rap22owl");
        // Create new Voa3rAP2:
        Individual newVoa3rAP2Instance = createNewVoa3rAP2Instance();
        //System.out.println("Creando instancia");
        createLiteralProperty(newVoa3rAP2Instance, vap2.getIdentifiers(), "identifier", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getTitles(), "title", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getDescriptions(), "description", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getSubjects(), "subject", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getAgrovocTerms(), "subjectAgrovocTerm", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getTypes(), "type", _resourceontNamespace);
        createLanguageProperty(newVoa3rAP2Instance, vap2.getLanguages(), "language");
        createLiteralProperty(newVoa3rAP2Instance, vap2.getCoverages(), "coverage", _resourceontNamespace);
        createDateTimeProperty(newVoa3rAP2Instance, vap2.getDates(), "date");
        createLiteralProperty(newVoa3rAP2Instance, vap2.getCreators(), "creator", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getContributors(), "contributor", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getPublishers(), "publisher", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getFormats(), "format", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getRights(), "rights", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getRelations(), "relation", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getSources(), "source", _resourceontNamespace);
        
        //qdc
        createLiteralProperty(newVoa3rAP2Instance, vap2.getConformsTo(), "conformsTo", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getHasPart(), "hasPart", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getBibliographicCitation(), "identifierBibliographicCitation", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getIsPartOf(), "isPartOf", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getIsReferencedBy(), "isReferencedBy", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getReferences(), "references", _qdc2owlNamespace);
        
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getAccessRights(), "rightsAccessRights", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getLicense(), "rightsLicense", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getAbstract(), "descriptionAbstract", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP2Instance, vap2.getAlternative(), "titleAlternative", _qdc2owlNamespace);
        
        //ese
        createLiteralProperty(newVoa3rAP2Instance, vap2.getIsShownAt(), "isShownAt", _eseNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getIsShownBy(), "isShownBy", _eseNamespace);
        
        //marcrel
        createLiteralProperty(newVoa3rAP2Instance, vap2.getEdt(), "edt", _marcrelNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getRev(), "rev", _marcrelNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getTrl(), "trl", _marcrelNamespace);
        //voa3r
        createLiteralProperty(newVoa3rAP2Instance, vap2.getMeasuresVariable(), "measuresVariable", _voa3rap22owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getObjectOfIterest(), "objectOfInterest", _voa3rap22owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getUsesIstrument(), "usesInstrument", _voa3rap22owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getUsesProtocol(), "usesProtocol", _voa3rap22owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getUsesMethod(), "usesMethod", _voa3rap22owlNamespace);
        createLiteralProperty(newVoa3rAP2Instance, vap2.getUsesTechnique(), "usesTechnique", _voa3rap22owlNamespace);
        
        createStringProperty(newVoa3rAP2Instance, vap2.getReviewStatus(), "reviewStatus", _voa3rap22owlNamespace);
        createStringProperty(newVoa3rAP2Instance, vap2.getPublicationStatus(), "publicationStatus", _voa3rap22owlNamespace);

        MetadataRecordReference newVoa3rAP2Ref =
                new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newVoa3rAP2Instance.getURI());

        return newVoa3rAP2Ref;
    }

    /**
     * Creates a new Learning Object instance.
     * @return the learning object individual instance
     */
    private Individual createNewVoa3rAP2Instance() {
        // Load the learningObject concept and the ontology:

        OntClass voa3rap2Class = this.getSelfOntModel().getOntClass(_voa3rap22owlNamespace
                + "#Voa3rap2Resource");


        MetadataRecordReference newRef =
                MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());


        Individual voa3rap2Ind = this.getSelfOntModel().createIndividual(_voa3rap22owlNamespace
                + "#voa3rap2-" + newRef.getLocalMetadataRecordId(), voa3rap2Class);
        return voa3rap2Ind;

    }

    
    protected void createStringProperty(Individual newVoa3rAP2Instance,
            ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                
                DatatypeProperty indProp = this.getSelfOntModel().getDatatypeProperty(ns + "#" + prop);
                
                RDFNode propValue = this.getSelfOntModel().createTypedLiteral(val);

                newVoa3rAP2Instance.addProperty(indProp, propValue);
            }
        }
    }
    
    /**
     * Creates a new LiteralValue property linked to a language. This method is 
     * used to store the title, description and subject elements.
     * @param newVoa3rAP2Instance - the voa3r AP level2 instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    protected void createLiteralPropertyWithLang(Individual newVoa3rAP2Instance,
            HashMap propValues, String prop, String ns) {

        Iterator itKeys = propValues.keySet().iterator();

        DatatypeProperty valueProp =
                this.getSelfOntModel().getDatatypeProperty(_resourceontNamespace
                + "#hasLabel");
        ObjectProperty indProp = this.getSelfOntModel().getObjectProperty(ns + "#" + prop);

        if (itKeys.hasNext()) {
            //obtains the literalValue OntClass
            OntClass literalValueClass = this.getSelfOntModel().getOntClass(_resourceontNamespace
                    + "#LiteralValue");
            String id =
                    UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
            Individual voa3rapInd = literalValueClass.createIndividual(_voa3rap22owlNamespace
                    + "#lv-" + id);

            while (itKeys.hasNext()) {
                String lang = itKeys.next().toString();
                //The language will be linked with the string value
                try {
                    ArrayList<String> valueString = (ArrayList<String>) propValues.get(lang);
                    if (!valueString.isEmpty()) {
                        Iterator<String> itValues = valueString.iterator();
                        while (itValues.hasNext()) {

                            RDFNode propValueLang = this.getSelfOntModel().createLiteral(itValues.next(), lang);

                            voa3rapInd.addProperty(valueProp, propValueLang);
                        }
                    }
                } catch (ClassCastException cce) {
                    String valueString = (String) propValues.get(lang);
                    if (!valueString.isEmpty()) {

                        RDFNode propValueLang = this.getSelfOntModel().createLiteral(valueString, lang);
                        voa3rapInd.addProperty(valueProp, propValueLang);
                    }
                }
            }
            //assign the individual to the property
            newVoa3rAP2Instance.addProperty(indProp, voa3rapInd);
        }
    }

    /**
     * 
     * Creates a new LiteralValue property. This method is 
     * used to store the identifier, coverage, date, creator, contributor, 
     * publisher, format, rights, relation and source elements. 
     * 
     * @param newVoa3rAP2Instance - the voa3r AP level2 instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     * 
     */
    protected void createLiteralProperty(Individual newVoa3rAP2Instance,
            ArrayList<String> propValues,
            String prop, String ns) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();
//            System.out.println("Storing "+ ns + "#" + prop + ": " + val);
            if (val.compareTo("") != 0) {

                //obtains the literalValue OntClass
                OntClass literalValueClass = this.getSelfOntModel().getOntClass(_resourceontNamespace
                        + "#LiteralValue");
                //generate an uuid for the new literal value individual
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                //create the literal value individual with the uri: vap_ns#lv-uuid
                Individual voa3rap2Ind = literalValueClass.createIndividual(_voa3rap22owlNamespace
                        + "#lv-" + id);
                DatatypeProperty valueProp =
                        this.getSelfOntModel().getDatatypeProperty(_resourceontNamespace
                        + "#hasLabel");
                RDFNode propValue = this.getSelfOntModel().createTypedLiteral(val);
                //add the value to the lv individual #hasLabel property
                voa3rap2Ind.setPropertyValue(valueProp, propValue);

                //assign the lv individual to the property ns#prop
                ObjectProperty indProp = this.getSelfOntModel().getObjectProperty(ns
                        + "#" + prop);

                newVoa3rAP2Instance.addProperty(indProp, voa3rap2Ind);

            }
        }
    }

    /**
     * Creates a new language property for the metadata
     * @param newVoa3rAP2Instance - the voa3r AP level2 instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    protected void createLanguageProperty(Individual newVoa3rAP2Instance,
            ArrayList<String> propValues,
            String prop) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = this.getSelfOntModel().getOntClass(_resourceontNamespace
                        + "#Language");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual voa3rapInd = langValueClass.createIndividual(_voa3rap22owlNamespace
                        + "#lang-" + id);
                DatatypeProperty valueProp =
                        this.getSelfOntModel().getDatatypeProperty(_resourceontNamespace
                        + "#langLabel");
                RDFNode propValue = this.getSelfOntModel().createTypedLiteral(val);
                voa3rapInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = this.getSelfOntModel().getObjectProperty(_resourceontNamespace
                        + "#" + prop);

                newVoa3rAP2Instance.addProperty(indProp, voa3rapInd);

            }
        }
    }
    
     /**
     * Creates a new language property for the metadata
     * @param newVoa3rAP2Instance - the voa3r AP level2 instance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    protected void createDateTimeProperty(Individual newVoa3rAP2Instance,
            ArrayList<String> propValues,
            String prop) {

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {
                //obtains the languageValue OntClass
                OntClass langValueClass = this.getSelfOntModel().getOntClass(_resourceontNamespace
                        + "#DateTime");
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
                Individual voa3rapInd = langValueClass.createIndividual(_voa3rap22owlNamespace
                        + "#date-" + id);
                DatatypeProperty valueProp =
                        this.getSelfOntModel().getDatatypeProperty(_resourceontNamespace
                        + "#value");
                RDFNode propValue = this.getSelfOntModel().createTypedLiteral(val);
                voa3rapInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = this.getSelfOntModel().getObjectProperty(_resourceontNamespace
                        + "#" + prop);

                newVoa3rAP2Instance.addProperty(indProp, voa3rapInd);

            }
        }
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByConformsTo(String ct) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "conformsTo", ct);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByDescriptionAbstract(String da) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "descriptionAbstract", da);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByHasPart(String hp) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "hasPart", hp);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIdentifierBibliographicCitation(String ibc) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "identifierBibliographicCitation", ibc);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsPartOf(String ipo) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "isPartOf", ipo);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsReferencedBy(String irb) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "isReferencedBy", irb);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByReferences(String ref) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "references", ref);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByRightsAccessRights(String rar) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "rightsAccessRights", rar);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByRightsLicense(String rl) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "rightsLicense", rl);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByTitleAlternative(String ta) {
        return this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "titleAlternative", ta);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsShownAt(String isa) {
        return this.queryGeneralAttributeByLiteralValue(_eseNamespace, "isShownAt", isa);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByIsShownBy(String isb) {
        return this.queryGeneralAttributeByLiteralValue(_eseNamespace, "isShownBy", isb);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByEdt(String edt) {
        return this.queryGeneralAttributeByLiteralValue(_marcrelNamespace, "edt", edt);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByRev(String rev) {
        return this.queryGeneralAttributeByLiteralValue(_marcrelNamespace, "rev", rev);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByTrl(String trl) {
        return this.queryGeneralAttributeByLiteralValue(_marcrelNamespace, "trl", trl);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByMeasuresVariable(String mv) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "measuresVariable", mv);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByObjectOfInterest(String ooi) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "objectOfInterest", ooi);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByPublicationStatus(String ps) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "publicationStatus", ps);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByReviewStatus(String rs) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "reviewStatus", rs);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesInstrument(String ui) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "usesInstrument", ui);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesMethod(String um) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "usesMethod", um);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesProtocol(String up) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "usesProtocol", up);
    }

    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByUsesTechnique(String ut) {
        return this.queryGeneralAttributeByLiteralValue(_voa3rap22owlNamespace, "usesTechnique", ut);
    }
    
        /**
     *  Launches a query for  a particular element in the ontology
     * @param agrovocTerms the agrovoc terms to search
     * @return DCQueryResultImpl object that have the Set of
     * MetadataRecordReference
     */
   @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset) {
        ArrayList<MetadataRecordReference> instancesR =
                new ArrayList<MetadataRecordReference>();

        Voa3rAP2QueryResultImpl queryResult = new Voa3rAP2QueryResultImpl();

        ArrayList<String> auxThesaResults = new ArrayList<String>();
        ArrayList<String> auxIdResults = new ArrayList<String>();

//       this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");


        //Query for the thesaurus instance matching the input values
        Integer recordsCounter = 0;
        for (Iterator<String> it = agrovocTerms.iterator(); it.hasNext();) {
              String subjectT = it.next();
            String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> \n";
            Prefixes += "PREFIX voa3r: <" + _voa3rap22owlNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";
String queryStringCount = Prefixes
                    + "\nSELECT (COUNT(DISTINCT(?resource)) as ?count) \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel \"" + subjectT + "\"^^xsd:string . \n"
                    + " }\n";
            recordsCounter += queryCountAP2(queryStringCount);
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
            QueryExecution qe = QueryExecutionFactory.create(query, this.getSelfOntModel());

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
            String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> \n";
            Prefixes += "PREFIX voa3r: <" + _voa3rap22owlNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";

            String queryString = Prefixes
                    + "\nSELECT ?resource \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:subjectAgrovocTerm <" + it.next() + "> \n"
                    + " }";
            
            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, this.getSelfOntModel());

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
        for(String id : auxIdResults) {
            MetadataRecordReference lor = new MetadataRecordReferenceImpl(_rep.getRepositoryURI(), id);
            instancesR.add(lor);
        }
        
        queryResult.setTotalResults(recordsCounter);
        queryResult.getMetadataRecordReferences().addAll(instancesR);
        return queryResult;
    }
    
    @Override
    public Voa3rAP2QueryResultImpl queryVoa3rAP2ByAgrovocTerm(ArrayList<String> agrovocTerms) {
        Voa3rAP2QueryResultImpl qr = queryVoa3rAP2ByAgrovocTerm(agrovocTerms,-1,-1);
        return qr;
    }
    
    private Integer queryCountAP2(
            String queryString) {

        _voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");

        String returnCount = "0";

        if (queryString != "") {

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _voa3rap22owlOntModel);

            ResultSet resultsCount;
            Resource resCount;

            try {
                resultsCount = qe.execSelect();
                if (resultsCount.hasNext()) {
                    returnCount = resultsCount.next().getLiteral("count").getValue().toString();
                }
            } catch (Exception e) {
                System.out.println("Exception when count the resources:" + e.getMessage());
                Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when count the resources", e);
            } finally {
                qe.close();
            }

        }
        return Integer.parseInt(returnCount);
    }

    @Override
    public ArrayList<String> getConformsTo(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "conformsTo", resId);
    }

    @Override
    public HashMap<String, String> getDescriptionAbstract(String resId) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "descriptionAbstract", resId);
    }

    @Override
    public ArrayList<String> getDescriptionAbstract(String resId, String lang) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "descriptionAbstract", resId, lang);
    }

    @Override
    public ArrayList<String> getHasPart(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "hasPart", resId);
    }

    @Override
    public ArrayList<String> getIdentifierBibliographicCitation(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "identifierBibliographicCitation", resId);
    }

    @Override
    public ArrayList<String> getIsPartOf(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "isPartOf", resId);
    }

    @Override
    public ArrayList<String> getIsReferencedBy(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "isReferencedBy", resId);
    }

    @Override
    public ArrayList<String> getReferences(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "references", resId);
    }

    @Override
    public HashMap<String, String> getRightsAccessRights(String resId) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "rightsAccessRights", resId);
    }

    @Override
    public ArrayList<String> getRightsAccessRights(String resId, String lang) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "rightsAccessRights", resId, lang);
    }

    @Override
    public HashMap<String, String> getRightsLicense(String resId) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "rightsLicense", resId);
    }

    @Override
    public ArrayList<String> getRightsLicense(String resId, String lang) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "rightsLicense", resId, lang);
    }

    @Override
    public HashMap<String, String> getTitleAlternative(String resId) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "titleAlternative", resId);
    }

    @Override
    public ArrayList<String> getTitleAlternative(String resId, String lang) {
        return this.getLiteralValueWithLangNS(_qdc2owlNamespace, "titleAlternative", resId, lang);
    }

    @Override
    public ArrayList<String> getIsShownAt(String resId) {
        return this.getLiteralValueNS(_eseNamespace, "isShownAt", resId);
    }

    @Override
    public ArrayList<String> getIsShownBy(String resId) {
        return this.getLiteralValueNS(_eseNamespace, "isShownBy", resId);
    }

    @Override
    public ArrayList<String> getEdt(String resId) {
        return this.getLiteralValueNS(_marcrelNamespace, "edt", resId);
    }

    @Override
    public ArrayList<String> getRev(String resId) {
        return this.getLiteralValueNS(_marcrelNamespace, "rev", resId);
    }

    @Override
    public ArrayList<String> getTrl(String resId) {
        return this.getLiteralValueNS(_marcrelNamespace, "trl", resId);
    }

    @Override
    public ArrayList<String> getMeasuresVariable(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "measuresVariable", resId);
    }

    @Override
    public ArrayList<String> getObjectOfInterest(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "objectOfInterest", resId);
    }

    @Override
    public ArrayList<String> getPublicationStatus(String resId) {
        return this.getString(_voa3rap22owlNamespace, "publicationStatus", resId);
    }

    @Override
    public ArrayList<String> getReviewStatus(String resId) {
        return this.getString(_voa3rap22owlNamespace, "reviewStatus", resId);
    }

    @Override
    public ArrayList<String> getUsesInstrument(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "usesInstrument", resId);
    }

    @Override
    public ArrayList<String> getUsesMethod(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "usesMethod", resId);
    }

    @Override
    public ArrayList<String> getUsesProtocol(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "usesProtocol", resId);
    }

    @Override
    public ArrayList<String> getUsesTechnique(String resId) {
        return this.getLiteralValueNS(_voa3rap22owlNamespace, "usesTechnique", resId);
    }

    
}
