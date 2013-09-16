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
package org.ontspace.mods.owl;

import org.ontspace.mods.translator.Mods;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.doomdark.uuid.UUIDGenerator;
import org.ontspace.owl.MetadataRepositoryImpl;
import org.ontspace.MetadataRecordReference;
import org.ontspace.mods.ModsQueryManager;
import org.ontspace.owl.MetadataRecordReferenceFactory;
import org.ontspace.owl.MetadataRecordReferenceImpl;
import org.ontspace.resource.owl.ResourceQueryManagerImpl;

public class ModsQueryManagerImpl extends ResourceQueryManagerImpl
        implements ModsQueryManager {

    private static MetadataRepositoryImpl _rep;
    private HashMap<String, OntModel> _onts = null;
    private List<URI> _uriList;
    private String _mods2owlNamespace = "http://voa3r.cc.uah.es/ontologies/mods2owl";
    private String _resourceontNamespace = "http://voa3r.cc.uah.es/ontologies/resourceont";
    private OntModel _mods2owlOntModel;

    /**
     * Creates a new instance of ModsQueryManagerImpl
     * @param rep
     * @param params
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public ModsQueryManagerImpl(MetadataRepositoryImpl rep, HashMap params)
            throws ClassNotFoundException, NoSuchMethodException {
        super(rep, params);
        _rep = rep;
        this._uriList = new ArrayList<URI>();
        addRequiredOntologies(params);
        _onts = new HashMap<String, OntModel>();

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
                Logger.getLogger(ModsQueryManagerImpl.class.getName()).
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
            System.out.println(ModsQueryManagerImpl.class.toString()
                    + " ERROR: "
                    + "the ontology '" + ontName
                    + "' is not present in the repository");
        }

        return ontModel;
    }

    /**
     * Close repository
     */
    @Override
    public void close() {
        ModsQueryManagerImpl._rep.close();
        ModsQueryManagerImpl._rep = null;
    }

    /**
     * Gets a LOM object, inserts its information in a created instance, and
     * stores that instance in the repository.
     * @param mods the learning object to store
     * @return the learning object reference of the stored learning object.
     *
     */
    @Override
    public MetadataRecordReference storeNewMods(Mods mods) {

        this._mods2owlOntModel = _onts.get("mods2owl");
        // Create new Mods:
        Individual newModsInstance = createNewModsInstance();


        createModsLiteralPropertyWithLang(newModsInstance, mods.getTitles(), "title", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getLanguages(), "language", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getIdentifiers(), "identifier", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getLocations(), "location", _mods2owlNamespace);
        createModsLiteralProperty(newModsInstance, mods.getDates(), "date", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getCreators(), "creator", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getPublishers(), "publisher", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getContributors(), "contributor", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getTypes(), "type", _resourceontNamespace);
        createModsLiteralPropertyWithLang(newModsInstance, mods.getAbstracts(), "description", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getTablesOfContents(), "tableOfContents", _mods2owlNamespace);
        createModsLiteralPropertyWithLang(newModsInstance, mods.getSubjects(), "subject", _resourceontNamespace);
        createModsLiteralProperty(newModsInstance, mods.getClassifications(), "classification", _mods2owlNamespace);
        createModsLiteralProperty(newModsInstance, mods.getRelatedItems(), "relation", _resourceontNamespace);

        MetadataRecordReference newModsRef =
                new MetadataRecordReferenceImpl(
                _rep.getRepositoryURI(), newModsInstance.getURI());

        return newModsRef;
    }

    /**
     * Creates a new Learning Object instance.
     * @return the learning object individual instance
     */
    protected Individual createNewModsInstance() {
        // Load the learningObject concept and the ontology:

        OntClass modsClass = _mods2owlOntModel.getOntClass(_mods2owlNamespace
                + "#modsResource");

        MetadataRecordReference newRef =
                MetadataRecordReferenceFactory.createLearningObjectReference(_rep.getRepositoryURI());

        Individual modsInd = _mods2owlOntModel.createIndividual(_mods2owlNamespace
                + "#mods-" + newRef.getLocalMetadataRecordId(), modsClass);
        return modsInd;

    }

    

    private void createModsLiteralProperty(Individual newInstance, ArrayList<String> propValues,
            String prop, String ns) {
        
        this._mods2owlOntModel = this.getOntologyModel("mods2owl");

        Iterator itValues = propValues.iterator();
        while (itValues.hasNext()) {
            String val = itValues.next().toString();

            if (val.compareTo("") != 0) {

                //obtains the literalValue OntClass
                OntClass literalValueClass = _mods2owlOntModel.getOntClass(_resourceontNamespace + "#LiteralValue");
                Random r = new Random();
                String id =
                        UUIDGenerator.getInstance().generateTimeBasedUUID().toString();

                Individual modsInd = literalValueClass.createIndividual(_mods2owlNamespace
                        + "#lv-" + id);
                DatatypeProperty valueProp = _mods2owlOntModel.getDatatypeProperty(_resourceontNamespace
                        + "#hasLabel");
                RDFNode propValue = _mods2owlOntModel.createTypedLiteral(val);
                modsInd.setPropertyValue(valueProp, propValue);

                //assign the individual to the property
                ObjectProperty indProp = _mods2owlOntModel.getObjectProperty(ns
                        + "#" + prop);
                
                newInstance.setPropertyValue(indProp, modsInd);

            }
        }
    }

       
    /**
     * Creates a new LiteralValue property linked to a language. This method is 
     * used to store the title, description and subject elements.
     * @param newInstance - the modsinstance
     * @param propValues - the values extracted from the xml
     * @param prop - the property related to the values
     */
    
    private void createModsLiteralPropertyWithLang(Individual newInstance,
            HashMap propValues, String prop, String ns) {

        Iterator itKeys = propValues.keySet().iterator();
        this._mods2owlOntModel = this.getOntologyModel("mods2owl");

        DatatypeProperty valueProp =
                _mods2owlOntModel.getDatatypeProperty(_resourceontNamespace
                + "#hasLabel");
        ObjectProperty indProp = _mods2owlOntModel.getObjectProperty(ns
                + "#" + prop);

        if (itKeys.hasNext()) {
            //obtains the literalValue OntClass
            OntClass literalValueClass = _mods2owlOntModel.getOntClass(_resourceontNamespace
                    + "#LiteralValue");
            String id =
                    UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
            Individual ind = literalValueClass.createIndividual(_mods2owlNamespace
                    + "#lv-" + id);

            while (itKeys.hasNext()) {
                String lang = itKeys.next().toString();
                //The language will be linked with the string value
                try {
                    ArrayList<String> valueString = (ArrayList<String>) propValues.get(lang);
                    if (!valueString.isEmpty()) {
                        Iterator<String> itValues = valueString.iterator();
                        while (itValues.hasNext()) {

                            RDFNode propValueLang = _mods2owlOntModel.createLiteral(itValues.next(), lang);

                            ind.addProperty(valueProp, propValueLang);
                        }
                    }
                } catch (ClassCastException cce) {
                    String valueString = (String) propValues.get(lang);
                    if (!valueString.isEmpty()) {

                        RDFNode propValueLang = _mods2owlOntModel.createLiteral(valueString, lang);
                        ind.addProperty(valueProp, propValueLang);
                    }
                }
            }
            //assign the individual to the property
            newInstance.addProperty(indProp, ind);
        }
    }

    /**
     * Launches a query for an attribute value of an specified resource existing in the repository
     *
     * @param elementType name of the element attribute
     * @param resId identifier of the resource
     * @return a HashMap<String, String> with the values for the specified attribute of the specified resource
     */
    private HashMap<String, String> getLiteralValueWithLang(String elementType,
            String resId, String ns) {

        this._mods2owlOntModel = this.getOntologyModel("mods2owl");
        Individual resInd = null;
        HashMap<String, String> result = new HashMap<String, String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = _mods2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
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
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            // System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving the result", e);
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
    private HashMap<String, ArrayList<String>> getMultipleLiteralValueWithLang(String elementType,
            String resId, String ns) {

        this._mods2owlOntModel = this.getOntologyModel("mods2owl");
        Individual resInd = null;
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        ArrayList<String> subjectsLang = new ArrayList<String>();

        Property propRes =
                new PropertyImpl(ns + "#" + elementType);

        try {
            resInd = _mods2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
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
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
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
    private ArrayList<String> getLiteralValueWithLang(String elementType,
            String resId, String lang, String ns) {

        this._mods2owlOntModel = this.getOntologyModel("mods2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();
        Property propRes =
                new PropertyImpl(_mods2owlOntModel + "#" + elementType);

        try {
            resInd = _mods2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
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
                Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                        log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

            }
        }

        return result;
    }
    
    
    /**
     * Launches a query to get the value of the literal attribute of an specified 
     * resource existing in the repository
     *
     * @param elementType the metadata element to be retrieved
     * @param resId the resource identifier
     * @return an ArrayList containing all the values for the element selected
     */
    private ArrayList<String> getLiteralValue(String elementType, String resId, String ns) {

        this._mods2owlOntModel = this.getOntologyModel("mods2owl");
        Individual resInd = null;
        ArrayList<String> result = new ArrayList<String>();

        Property propRes =
                new PropertyImpl( ns + "#" + elementType);

        try {
            resInd = _mods2owlOntModel.getIndividual(resId);

        } catch (NullPointerException npe) {
            //System.out.println("Null pointer exception: " + resId
            //        + " not in the repository.");
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Null pointer exception: " + resId
                    + " not in the repository.", npe);

        } catch (Exception e) {
            //System.out.println("Exception: " + e.getMessage());
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving te result", e);

        }

        try {
            Property propVal =
                    new PropertyImpl(_resourceontNamespace + "#" + "hasLabel");

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
            Logger.getLogger(ModsQueryManagerImpl.class.getName()).
                    log(Level.SEVERE, "Error when retrieving a property: property value not assigned ", npe);

        }

        return result;

    }
    
    /**
     *  Gets the title values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap <String, String> with the values
     *
     */
    @Override
    public HashMap<String, String> getTitles(String resId) {
        return this.getLiteralValueWithLang("title", resId, _resourceontNamespace);
    }

    /**
     *  Gets the title values for a particular element in the ontology in the
     * specified language
     * @param resId the subject to search
     * @param lang the language for the values
     * @return ArrayList<String> with the values
     */
    
    @Override
    public ArrayList<String> getTitles(String resId, String lang) {

        return this.getLiteralValueWithLang("title", resId, lang, _resourceontNamespace);
    }

     /**
     *  Gets the language values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getLanguages(String resId) {
        return this.getLiteralValue("language", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the identifier values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getIdentifiers(String resId) {
        return this.getLiteralValue("identifier", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the location values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */

    @Override
    public ArrayList<String> getLocations(String resId) {
        return this.getLiteralValue("location", resId, _mods2owlNamespace);
    }
    
     /**
     *  Gets the dates values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getDates(String resId) {
        return this.getLiteralValue("date", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the creator values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */

    @Override
    public ArrayList<String> getCreators(String resId) {
        return this.getLiteralValue("creator", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the contributor values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getContributors(String resId) {
        return this.getLiteralValue("contributor", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the publisher values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getPublishers(String resId) {
        return this.getLiteralValue("publisher", resId, _resourceontNamespace);
    }

     /**
     *  Gets the type values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getTypes(String resId) {
        return this.getLiteralValue("type", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the abstract values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
    @Override
    public HashMap<String, String> getAbstracts(String resId) {
        return this.getLiteralValueWithLang("description", resId, _resourceontNamespace);
    }
    
    
    @Override
    public ArrayList<String> getAbstracts(String resId, String lang) {

        return this.getLiteralValueWithLang("description", resId, lang, _resourceontNamespace);
    }

     /**
     *  Gets the table of contents values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    @Override
    public ArrayList<String> getTablesOfContents(String resId) {
        return this.getLiteralValue("tableOfContents", resId, _mods2owlNamespace);
    }
    
    
     /**
     *  Gets the subject values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
  
    @Override
    public HashMap<String, String> getSubjects(String resId) {
        return this.getLiteralValueWithLang("subject", resId, _resourceontNamespace);
    }
    
     /**
     *  Gets the subject values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return HashMap<String, String> with the values
     *
     */
  
    @Override
    public ArrayList<String> getSubjects(String resId, String lang) {
        return this.getLiteralValueWithLang("subject", resId, lang, _resourceontNamespace);
    }
    
     /**
     *  Gets the classification values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
  
    @Override
    public ArrayList<String> getClassifications(String resId) {
        return this.getLiteralValue("classification", resId, _mods2owlNamespace);
    }
    
    
     /**
     *  Gets the relation values for  a particular element in the ontology
     * @param resId the resource identifier
     * @return ArrayList<String> with the values
     *
     */
    
    @Override
    public ArrayList<String> getRelations(String resId) {
        return this.getLiteralValue("relation", resId, _resourceontNamespace);
    }

}