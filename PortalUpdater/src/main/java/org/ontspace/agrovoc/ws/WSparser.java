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
package org.ontspace.agrovoc.ws;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.ontspace.agrovoc.impl.AgrovocConcept;

/**
 * SKOS parser for XML information on an Agrovoc concept
 *
 */
public class WSparser {

    /** SKOS namespace */
    private static Namespace _skosns;
    /** AOS namespace */
    private static Namespace _aosns;
    /** RDF namespace */
    private static Namespace _rdfns;
    /** XML namespace */
    private static Namespace _xmlns;
    /** FOAF namespace */
    private static Namespace _foafns;
    /** DC namespace */
    private static Namespace _dcns;
    /** DCterms namespace */
    private static Namespace _dctermsns;
    /** Ignored elements */
    private static ArrayList<String> _ignoredElements = new ArrayList<String>(
            Arrays.asList(
                "Thing",
                "nullc",
                "c_1284792302385",
                "c_1304611637926",
                "c_category"
                )
            );

    /**
     * Parses a SKOS response containing the information on an agrovoc concept
     * @param xml String with the sanitized XML SKOS representation
     * @return AgrovocConcept object with the parsed information
     * @throws JDOMException
     * @throws IOException
     */
    public static AgrovocConcept parseSKOS(String xml)
            throws JDOMException,
            IOException {
        AgrovocConcept concept = new AgrovocConcept();

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xml));
        Element root = doc.getRootElement();
        _skosns = root.getNamespace("skos");
        _aosns = root.getNamespace("aos");
        _rdfns = root.getNamespace("rdf");
        _xmlns = root.getNamespace("xml");
        _foafns = root.getNamespace("foaf");
        _dcns = root.getNamespace("dc");
        _dctermsns = root.getNamespace("dcterms");
        Element domConcept = root.getChild("Concept", _skosns);

        parseLabels(concept,domConcept);

        // broader elements
        List<Element> broaderElements = domConcept.getChildren("broader",_skosns);
        List<AgrovocConcept> broader = new ArrayList<AgrovocConcept>();
        for (Element e : broaderElements){
            String value = e.getAttributeValue("resource",_rdfns);
            if (value == null || !elementShouldBeIgnored(value)){
                AgrovocConcept child = new AgrovocConcept();
                Element domChild = e.getChild("Concept",_skosns);
                parseLabels(child,domChild);
                broader.add(child);
            }
        }
        if (broader.size() > 0){
            concept.setBroader(broader);
        }

        // narrower elements
        List<Element> narrowerElements = domConcept.getChildren("narrower",_skosns);
        List<AgrovocConcept> narrower = new ArrayList<AgrovocConcept>();
        for (Element e : narrowerElements){
            String value = e.getAttributeValue("resource", _rdfns);
            if (value == null || !elementShouldBeIgnored(value)) {
                AgrovocConcept child = new AgrovocConcept();
                Element domChild = e.getChild("Concept",_skosns);
                parseLabels(child,domChild);
                narrower.add(child);
            }
        }
        if (narrower.size() > 0){
            concept.setNarrower(narrower);
        }

        // related elements
        List<Element> allElements = domConcept.getChildren();
        HashMap<String,AgrovocConcept> related = new HashMap<String,AgrovocConcept>();
        for (Element e : allElements){
            if (e.getNamespace().equals(_aosns)){
                String relation = e.getName();
                AgrovocConcept child = new AgrovocConcept();
                Element domChild = e.getChild("Concept",_skosns);
                parseLabels(child,domChild);
                relation = relation + "::" + child.getAbout();
                related.put(relation,child);
            }
        }
        if (related.size() > 0){
            concept.setRelated(related);
        }

        return concept;
    }

    private static void parseLabels(AgrovocConcept concept, Element domElement){
        // about
        String about = domElement.getAttributeValue("about", _rdfns);
        concept.setAbout(about);

        // prefLabel
        List<Element> prefLabels = domElement.getChildren("prefLabel", _skosns);
        HashMap<String,String> prefLabelsMap = new HashMap<String,String>();
        for (Element e : prefLabels) {
            String language = e.getAttributeValue("lang", _xmlns);
            String prefLabel =  e.getTextTrim();
            prefLabelsMap.put(language, prefLabel);
        }
        concept.setPrefLabels(prefLabelsMap);

        // altLabel
        List<Element> altLabels = domElement.getChildren("altLabel", _skosns);
        HashMap<String,String> altLabelsMap = new HashMap<String,String>();
        for (Element e : altLabels) {
            String language = e.getAttributeValue("lang", _xmlns);
            String altLabel =  e.getTextTrim();
            altLabelsMap.put(language, altLabel);
        }
        concept.setAltLabels(altLabelsMap);

        // scopeNote
        List<Element> scopeNotes = domElement.getChildren("scopeNote", _skosns);
        HashMap<String,String> scopeNotesMap = new HashMap<String,String>();
        for (Element e : scopeNotes) {
            String language = e.getAttributeValue("lang", _xmlns);
            String scopeNote = e.getTextTrim();
            scopeNotesMap.put(language, scopeNote);
        }
        concept.setScopeNotes(scopeNotesMap);

        // changeNote
        Element changeNote = domElement.getChild("changeNote", _skosns);
        if (changeNote != null){
            Element changeNoteDate = changeNote.getChild("date", _dcns);
            if (changeNoteDate != null) {
                concept.setChangeNote(changeNoteDate.getTextTrim());
            }
        }
    }

    /**
     * Determins whether the element should be ignored
     * @param id Identifier of the element to check
     * @return Logic value determining whether the element should be ignored
     */
    private static boolean elementShouldBeIgnored(String id){
        for (String i : _ignoredElements){
            if (id.contains(i)){
                return true;
            }
        }
        return false;
    }
}
