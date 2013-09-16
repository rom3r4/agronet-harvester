package org.ontspace.voa3rap4.owl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import org.ontspace.voa3rap2.owl.Voa3rAP2QueryManagerImpl;
import org.ontspace.voa3rap2.owl.Voa3rAP2QueryResultImpl;
import org.ontspace.voa3rap4.Voa3rAP4QueryManager;
import org.ontspace.voa3rap4.translator.Vap4Agent;
import org.ontspace.voa3rap4.translator.Vap4MetaMetadata;
import org.ontspace.voa3rap4.translator.Vap4Research;
import org.ontspace.voa3rap4.translator.Voa3rAP4;

public class Voa3rAP4QueryManagerImpl extends Voa3rAP2QueryManagerImpl
        implements Voa3rAP4QueryManager {

    private String _voa3rap42owlNamespace = "http://voa3r.cc.uah.es/ontologies/voa3rap42owl";
    private String _voa3rap22owlNamespace = "http://voa3r.cc.uah.es/ontologies/voa3rap22owl";
    private String _qdc2owlNamespace = "http://voa3r.cc.uah.es/ontologies/qdc2owl";
    private String _eseNamespace = "http://www.europeana.eu/schemas/ese";
//  private String _marcrelNamespace = "http://www.loc.gov/marc.relators";
    private String _resourceontNamespace = "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _voa3rap42owlOntModel;

    public Voa3rAP4QueryManagerImpl(MetadataRepositoryImpl rep, HashMap params) throws ClassNotFoundException, NoSuchMethodException {
        super(rep, params);
        this.setSelfOntName("voa3rap42owl");
    }

    /**
     * Gets a voa3r AP level4 resource, inserts its information in a new
     * instance, and stores that instance in the repository.
     *
     * @param vap4 the learning object to store
     * @return the learning object reference of the stored learning object.
     * @throws Exception
     *
     */
    @Override
    public MetadataRecordReference storeNewVoa3rAP4(Voa3rAP4 vap4) throws
            Exception {

//        OntModel voa3rap42owlOntModel = this.getSelfOntModel();
        // Create new Voa3rAP4:
        Individual newVoa3rAP4Instance = createNewVoa3rAP4Instance();
        //System.out.println("Creando instancia");
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIdentifiers(), "identifier", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getTitles(), "title", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getDescriptions(), "description", _resourceontNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getSubjects(), "subject", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getAgrovocTerms(), "subjectAgrovocTerm", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getTypes(), "type", _resourceontNamespace);
        createLanguageProperty(newVoa3rAP4Instance, vap4.getLanguages(), "language");
        createLiteralProperty(newVoa3rAP4Instance, vap4.getCoverages(), "coverage", _resourceontNamespace);
        createDateTimeProperty(newVoa3rAP4Instance, vap4.getDates(), "date");
        createAgentProperty(newVoa3rAP4Instance, vap4.getCreators(), "creator", _resourceontNamespace);
        createAgentProperty(newVoa3rAP4Instance, vap4.getContributors(), "contributor", _resourceontNamespace);
        createAgentProperty(newVoa3rAP4Instance, vap4.getPublishers(), "publisher", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getFormats(), "format", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getRights(), "rights", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getRelations(), "relation", _resourceontNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getSources(), "source", _resourceontNamespace);

        //qdc
        createLiteralProperty(newVoa3rAP4Instance, vap4.getConformsTo(), "conformsTo", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getHasPart(), "hasPart", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getBibliographicCitation(), "identifierBibliographicCitation", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsPartOf(), "isPartOf", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsReferencedBy(), "isReferencedBy", _qdc2owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getReferences(), "references", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getAccessRights(), "rightsAccessRights", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getLicense(), "rightsLicense", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getAbstract(), "descriptionAbstract", _qdc2owlNamespace);
        createLiteralPropertyWithLang(newVoa3rAP4Instance, vap4.getAlternative(), "titleAlternative", _qdc2owlNamespace);
        //qdc new in ap4
        createLiteralProperty(newVoa3rAP4Instance, vap4.getHasVersion(), "hasVersion", _voa3rap42owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsVersionOf(), "isVersionOf", _voa3rap42owlNamespace);

        //ese
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsShownAt(), "isShownAt", _eseNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsShownBy(), "isShownBy", _eseNamespace);

        //voa3r
        createStringProperty(newVoa3rAP4Instance, vap4.getReviewStatus(), "reviewStatus", _voa3rap22owlNamespace);
        createStringProperty(newVoa3rAP4Instance, vap4.getPublicationStatus(), "publicationStatus", _voa3rap22owlNamespace);
        //voa3r new in vap4
        createLiteralProperty(newVoa3rAP4Instance, vap4.getHasTranslation(), "hasTranslation", _voa3rap42owlNamespace);
        createLiteralProperty(newVoa3rAP4Instance, vap4.getIsTranslationOf(), "isTransaltionOf", _voa3rap42owlNamespace);
        createMetametadataProperty(newVoa3rAP4Instance, vap4.getHasMetametadata(), "hasMetametadata", _voa3rap42owlNamespace);
        createResearchProperty(newVoa3rAP4Instance, vap4.getHasResearch(), "hasResearch", _voa3rap42owlNamespace);

        MetadataRecordReference newVoa3rAP4Ref =
                new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newVoa3rAP4Instance.getURI());

        return newVoa3rAP4Ref;
    }

    /**
     * Creates a new Learning Object instance.
     *
     * @return the learning object individual instance
     */
    private Individual createNewVoa3rAP4Instance() {
        // Load the learningObject concept and the ontology:

        OntClass voa3rap4Class = this.getSelfOntModel().getOntClass(_voa3rap42owlNamespace
                + "#Voa3rap4Resource");


        MetadataRecordReference newRef =
                MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());


        Individual voa3rap4Ind = this.getSelfOntModel().createIndividual(_voa3rap42owlNamespace
                + "#voa3rap4-" + newRef.getLocalMetadataRecordId(), voa3rap4Class);
        return voa3rap4Ind;

    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByContributor(String contributor) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByContributor(contributor);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByFormat(String format) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByFormat(format);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByRights(String rights) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByRights(rights);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIdentifier(String identifier) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIdentifier(identifier);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByCreator(String creator) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByCreator(creator);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByLanguage(String language) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByLanguage(language);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4BySubject(String subject) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2BySubject(subject);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByDate(String date) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByDate(date);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByPublisher(String publisher) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByPublisher(publisher);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByTitle(String title) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByTitle(title);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByDescription(String description) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByDescription(description);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByRelation(String relation) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByRelation(relation);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByType(String type) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByType(type);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByAgrovocTerm(ArrayList<String> agrovocTerms, int limit, int offset) {
        ArrayList<MetadataRecordReference> instancesR =
                new ArrayList<MetadataRecordReference>();

        Voa3rAP4QueryResultImpl queryResult = new Voa3rAP4QueryResultImpl();

        ArrayList<String> auxThesaResults = new ArrayList<String>();
        ArrayList<String> auxIdResults = new ArrayList<String>();

//       this._voa3rap22owlOntModel = this.getOntologyModel("voa3rap22owl");


        //Query for the thesaurus instance matching the input values
        Integer recordsCounter = 0;
        for (Iterator<String> it = agrovocTerms.iterator(); it.hasNext();) {
            String subjectT = it.next();
            String Prefixes = "PREFIX res: <" + _resourceontNamespace + "#> \n";
            Prefixes += "PREFIX voa3r: <" + _voa3rap42owlNamespace + "#> \n";
            Prefixes += "PREFIX xsd:    <http://www.w3.org/2001/XMLSchema#>";
            String queryStringCount = Prefixes
                    + "\nSELECT (COUNT(DISTINCT(?resource)) as ?count) \n"
                    + "WHERE\n"
                    + "{\n"
                    + "?resource  res:hasLabel \"" + subjectT + "\"^^xsd:string . \n"
                    + " }\n";
            recordsCounter += queryCountAP4(queryStringCount);
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
            Prefixes += "PREFIX voa3r: <" + _voa3rap42owlNamespace + "#> \n";
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
        for (String id : auxIdResults) {
            MetadataRecordReference lor = new MetadataRecordReferenceImpl(_rep.getRepositoryURI(), id);
            instancesR.add(lor);
        }

        queryResult.setTotalResults(recordsCounter);
        queryResult.getMetadataRecordReferences().addAll(instancesR);
        return queryResult;
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByAgrovocTerm(ArrayList<String> agrovocTerms) {
        Voa3rAP4QueryResultImpl qr = queryVoa3rAP4ByAgrovocTerm(agrovocTerms, -1, -1);
        return qr;
    }

    private Integer queryCountAP4(
            String queryString) {

        _voa3rap42owlOntModel = this.getOntologyModel("voa3rap22owl");

        String returnCount = "0";

        if (queryString != "") {

            Query query = QueryFactory.create(queryString);
            QueryExecution qe = QueryExecutionFactory.create(query, _voa3rap42owlOntModel);

            ResultSet resultsCount;
            Resource resCount;

            try {
                resultsCount = qe.execSelect();
                if (resultsCount.hasNext()) {
                    returnCount = resultsCount.next().getLiteral("count").getValue().toString();
                }
            } catch (Exception e) {
                System.out.println("Exception when count the resources:" + e.getMessage());
                Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Exception when count the resources", e);
            } finally {
                qe.close();
            }

        }
        return Integer.parseInt(returnCount);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByConformsTo(String ct) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByConformsTo(ct);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByDescriptionAbstract(String da) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByDescriptionAbstract(da);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasPart(String hp) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByHasPart(hp);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIdentifierBibliographicCitation(String ibc) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIdentifierBibliographicCitation(ibc);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsPartOf(String ipo) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIsPartOf(ipo);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsReferencedBy(String irb) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIsReferencedBy(irb);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByReferences(String ref) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByReferences(ref);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByRightsAccessRights(String rar) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByRightsAccessRights(rar);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByRightsLicense(String rl) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByRightsLicense(rl);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByTitleAlternative(String ta) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByTitleAlternative(ta);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByPublicationStatus(String ps) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByPublicationStatus(ps);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByReviewStatus(String rs) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByReviewStatus(rs);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasVersion(String hv) {
        return (Voa3rAP4QueryResultImpl) this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "hasVersion", hv);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsVersionOf(String ivo) {
        return (Voa3rAP4QueryResultImpl) this.queryGeneralAttributeByLiteralValue(_qdc2owlNamespace, "isVersionOf", ivo);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsShownAt(String isa) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIsShownAt(isa);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsShownBy(String isb) {
        return (Voa3rAP4QueryResultImpl) this.queryVoa3rAP2ByIsShownBy(isb);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasTranslation(String ht) {
        return (Voa3rAP4QueryResultImpl) this.queryGeneralAttributeByLiteralValue(_voa3rap42owlNamespace, "hasTranslation", ht);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByIsTranslationOf(String ito) {
        return (Voa3rAP4QueryResultImpl) this.queryGeneralAttributeByLiteralValue(_voa3rap42owlNamespace, "isTranslationOf", ito);
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasMetadata(String ps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Voa3rAP4QueryResultImpl queryVoa3rAP4ByHasResearch(String ps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<String> getHasTranslation(String resId) {
        return this.getLiteralValueNS(_voa3rap42owlNamespace, "hasTranslation", resId);
    }

    @Override
    public ArrayList<String> getIsTranslationOf(String resId) {
        return this.getLiteralValueNS(_voa3rap42owlNamespace, "isTranslationOf", resId);
    }

    @Override
    public ArrayList<String> getHasVersion(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "hasVersion", resId);
    }

    @Override
    public ArrayList<String> getIsVersionOf(String resId) {
        return this.getLiteralValueNS(_qdc2owlNamespace, "isVersionOf", resId);
    }

    @Override
    public ArrayList<Vap4MetaMetadata> getHasMetametadata(String resId) {
        ArrayList res;
        res = this.getMetametadataNS(_voa3rap42owlNamespace, "hasMetametadata", resId);
        return res;
    }

    @Override
    public ArrayList<Vap4Research> getHasResearch(String resId) {
        ArrayList res;
        res = this.getResearchNS(_voa3rap42owlNamespace, "hasResearch", resId);
        return res;
    }

    private void createMetametadataProperty(Individual newVoa3rAP4Instance, ArrayList<Vap4MetaMetadata> propValues, String prop, String ns) {

        for (Vap4MetaMetadata val : propValues) {

            OntClass mmdClass = this.getSelfOntModel().getOntClass(_voa3rap42owlNamespace + "#MetaMetadata");

            //generate an uuid for the new mmd individual
            String id = UUIDGenerator.getInstance().generateTimeBasedUUID().toString();

            //create the mmd individual with the uri: vap_ns#lv-uuid
            Individual mmdInd = mmdClass.createIndividual(_voa3rap42owlNamespace + "#mmd-" + id);

            //fill in the mmd individual:
            ArrayList mmdType = new ArrayList();
            mmdType.add(val.getType());
            ArrayList mmdDate = new ArrayList();
            mmdDate.add(val.getDate());
            ArrayList mmdContributor = val.getContributor();
            createLiteralProperty(mmdInd, val.getIdentifier(), "identifier", _resourceontNamespace);
            createLiteralProperty(mmdInd, mmdType, "type", _resourceontNamespace);
            createDateTimeProperty(mmdInd, mmdDate, "date");
            createAgentProperty(mmdInd, mmdContributor, "contributor", _resourceontNamespace);

            //assign the mmd individual to the property ns#prop
            ObjectProperty mmdProp = this.getSelfOntModel().getObjectProperty(ns + "#" + prop);
            newVoa3rAP4Instance.addProperty(mmdProp, mmdInd);

        }
    }

    private void createAgentProperty(Individual ind, ArrayList<Object> propValues, String prop, String ns) {

        for (Object val : propValues) {
            if (val instanceof Vap4Agent) {
                Vap4Agent agentVal = (Vap4Agent) val;
                OntClass agentClass = this.getSelfOntModel().getOntClass(_voa3rap42owlNamespace + "#Agent");

                //generate an uuid for the new mmd individual
                String id = UUIDGenerator.getInstance().generateTimeBasedUUID().toString();

                //create the agent individual with the uri: vap_ns#lv-uuid
                Individual agentInd = agentClass.createIndividual(_voa3rap42owlNamespace + "#agent-" + id);

                //fill in the agent individual:
                ArrayList agentType = new ArrayList();
                agentType.add(agentVal.getType());
                ArrayList agentName = new ArrayList();
                agentName.add(agentVal.getName());
                ArrayList agentFirstName = new ArrayList();
                agentFirstName.add(agentVal.getFirstName());
                ArrayList agentLastName = new ArrayList();
                agentLastName.add(agentVal.getLastName());
                ArrayList agentMbox = new ArrayList();
                agentMbox.add(agentVal.getMbox());

                createLiteralProperty(agentInd, agentType, "type", _resourceontNamespace);
                createStringProperty(agentInd, agentName, "name", _voa3rap42owlNamespace);
                createStringProperty(agentInd, agentFirstName, "firstName", _voa3rap42owlNamespace);
                createStringProperty(agentInd, agentLastName, "lastName", _voa3rap42owlNamespace);
                createStringProperty(agentInd, agentMbox, "mbox", _voa3rap42owlNamespace);

                //assign the agent individual to the property ns#prop
                ObjectProperty agentProp = this.getSelfOntModel().getObjectProperty(ns + "#" + prop);
                ind.addProperty(agentProp, agentInd);

            } else if (val instanceof String) {
                ArrayList valList = new ArrayList();
                valList.add(val);
                createLiteralProperty(ind, valList, prop, ns);
            }
        }
    }

    private void createResearchProperty(Individual ind, ArrayList<Vap4Research> propValues, String prop, String ns) {

        for (Vap4Research research : propValues) {
            OntClass agentClass = this.getSelfOntModel().getOntClass(_voa3rap42owlNamespace + "#Research");

            //generate an uuid for the new mmd individual
            String id = UUIDGenerator.getInstance().generateTimeBasedUUID().toString();

            //create the agent individual with the uri: vap_ns#lv-uuid
            Individual researchInd = agentClass.createIndividual(_voa3rap42owlNamespace + "#res-" + id);

            //fill in the research individual

            createLiteralProperty(researchInd, research.getMeasuresVariable(), "measuresVariable", _voa3rap22owlNamespace);
            createLiteralProperty(researchInd, research.getObjectOfInterest(), "objectOfInterest", _voa3rap22owlNamespace);
            createLiteralProperty(researchInd, research.getUsesInstrument(), "usesInstrument", _voa3rap22owlNamespace);
            createLiteralProperty(researchInd, research.getUsesProtocol(), "usesProtocol", _voa3rap22owlNamespace);
            createLiteralProperty(researchInd, research.getUsesMethod(), "usesMethod", _voa3rap22owlNamespace);
            createLiteralProperty(researchInd, research.getUsesTechnique(), "usesTechnique", _voa3rap22owlNamespace);

            //assign the research individual to the property ns#prop
            ObjectProperty researchProp = this.getSelfOntModel().getObjectProperty(ns + "#" + prop);
            ind.addProperty(researchProp, researchInd);
        }

    }

    private String getLVlabelFromRes(Resource res) {
        Property propVal = new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");
        return res.getProperty(propVal).getLiteral().getString();
    }

    private Vap4Agent getAgentFromRes(Resource agentRes) {
        Vap4Agent agent = new Vap4Agent(null);

        Statement nameEst = agentRes.getProperty(new PropertyImpl(_voa3rap42owlNamespace + "#name"));
        Statement firstNameEst = agentRes.getProperty(new PropertyImpl(_voa3rap42owlNamespace + "#firstName"));
        Statement lastNameEst = agentRes.getProperty(new PropertyImpl(_voa3rap42owlNamespace + "#lastName"));
        Statement mboxEst = agentRes.getProperty(new PropertyImpl(_voa3rap42owlNamespace + "#mbox"));
        Statement typeEst = agentRes.getProperty(new PropertyImpl(_resourceontNamespace + "#type"));

        if (nameEst != null) {
            agent.setName(nameEst.getString());
        }
        if (firstNameEst != null) {
            agent.setFirstName(firstNameEst.getString());
        }
        if (lastNameEst != null) {
            agent.setLastName(lastNameEst.getString());
        }
        if (mboxEst != null) {
            agent.setMbox(mboxEst.getString());
        }
        if (typeEst != null) {
            agent.setType(getLVlabelFromRes(typeEst.getResource()));
        }

        return agent;
    }

    private ArrayList<Vap4MetaMetadata> getMetametadataNS(String ns, String elementType, String resId) {

        Individual resInd = null;
        ArrayList result = new ArrayList();

        Property propRes = new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);
        } catch (NullPointerException npe) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);
        } catch (Exception e) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        try {

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            while (nodeIt.hasNext()) {
                Vap4MetaMetadata mmd = new Vap4MetaMetadata(null, null);

                RDFNode rdfnode = nodeIt.next();
                Resource res = rdfnode.asResource();

                Statement dateEst = res.getProperty(new PropertyImpl(_resourceontNamespace + "#date"));

                Property propVal = new PropertyImpl(_resourceontNamespace + "#" + "value");

                String date = dateEst.getProperty(propVal).getLiteral().getString();

                Statement idst = res.getProperty(new PropertyImpl(_resourceontNamespace + "#identifier"));
                String id = "";
                if (idst != null) {
                    id = getLVlabelFromRes(idst.getResource());
                }

                Statement typest = res.getProperty(new PropertyImpl(_resourceontNamespace + "#type"));
                String type = "";
                if (typest != null) {
                    type = getLVlabelFromRes(typest.getResource());
                }

                StmtIterator contribestIt = res.listProperties(new PropertyImpl(_resourceontNamespace + "#contributor"));
                while (contribestIt.hasNext()) {
                    Statement contribEst = contribestIt.nextStatement();
                    if (contribEst != null) {
                        Resource contRes = contribEst.getResource();
                        if (contRes.hasProperty(new PropertyImpl(_resourceontNamespace + "#" + "hasLabel"))) {
                            //is literal value
                            mmd.addContributor(getLVlabelFromRes(contRes));
                        } else {
                            //is agent
                            mmd.addContributor(getAgentFromRes(contRes));
                        }
                    }
                }
                mmd.setType(type);
                mmd.setDate(date);
                mmd.addIdentifier(id);

                result.add(mmd);
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;
    }

    private ArrayList<Vap4Research> getResearchNS(String ns, String elementType, String resId) {
        Individual resInd = null;
        ArrayList result = new ArrayList();

        Property propRes = new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);
        } catch (NullPointerException npe) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);
        } catch (Exception e) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        try {

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            while (nodeIt.hasNext()) {
                RDFNode rdfnode = nodeIt.next();
                Resource res = rdfnode.asResource();
                Vap4Research research = new Vap4Research(null);

                String mv, ooi, ui, um, up, ut;

                Statement mvest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#measuresVariable"));
                if (mvest != null) {
                    mv = getLVlabelFromRes(mvest.getResource());
                    research.addMeasuresVariable(mv);
                }

                Statement ooiest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#objectOfInterest"));
                if (ooiest != null) {
                    ooi = getLVlabelFromRes(ooiest.getResource());
                    research.addObjectOfInterest(ooi);
                }

                Statement uiest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#usesInstrument"));
                if (uiest != null) {
                    ui = getLVlabelFromRes(uiest.getResource());
                    research.addUsesInstrument(ui);
                }

                Statement umest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#usesMethod"));
                if (umest != null) {
                    um = getLVlabelFromRes(umest.getResource());
                    research.addUsesMethod(um);
                }

                Statement upest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#usesProtocol"));
                if (upest != null) {
                    up = getLVlabelFromRes(upest.getResource());
                    research.addUsesProtocol(up);
                }

                Statement utest = res.getProperty(new PropertyImpl(_voa3rap22owlNamespace + "#usesTechnique"));
                if (utest != null) {
                    ut = getLVlabelFromRes(utest.getResource());
                    research.addUsesTechnique(ut);
                }

                result.add(research);
            }
        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + propRes
            //        + " not assigned to the resource.");
            Logger.getLogger(Voa3rAP2QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;
    }

    private ArrayList getAgentNS(String ns, String elementType, String resId) {
        Individual resInd = null;
        ArrayList result = new ArrayList();

        Property propRes = new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = this.getSelfOntModel().getIndividual(resId);
        } catch (NullPointerException npe) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);
        } catch (Exception e) {
            Logger.getLogger(Voa3rAP4QueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
        }

        try {

            NodeIterator nodeIt = resInd.listPropertyValues(propRes);

            while (nodeIt.hasNext()) {
                RDFNode rdfnode = nodeIt.next();
                Resource res = rdfnode.asResource();

                if (!res.hasProperty(new PropertyImpl(_resourceontNamespace + "#" + "hasLabel"))) {
                    Vap4Agent agent = this.getAgentFromRes(res);
                    result.add(agent);
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

    @Override
    public ArrayList<Vap4Agent> getAgentCreators(String resId) {
        return this.getAgentNS(_resourceontNamespace, "creator", resId);
    }

    @Override
    public ArrayList<Vap4Agent> getAgentPublishers(String resId) {
        return this.getAgentNS(_resourceontNamespace, "publisher", resId);
    }

    @Override
    public ArrayList<Vap4Agent> getAgentContributors(String resId) {
        return this.getAgentNS(_resourceontNamespace, "contributor", resId);
    }
}
