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
package org.ontspace.mods.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.ontspace.owl.util.AutomaticLangDetector;
import org.ontspace.owl.util.LanguageISOHelper;

/**
 *
 * @author Raquel
 */
public class Mods {

    private HashMap<String, String> _titles = null;
    private ArrayList<String> _languages = null;
    private ArrayList<String> _identifiers = null;
    private ArrayList<String> _locations = null;
    private ArrayList<String> _dates = null;
    private ArrayList<String> _creators = null;
    private ArrayList<String> _publishers = null;
    private ArrayList<String> _contributors = null;
    private ArrayList<String> _types = null;
    private HashMap<String, String> _abstracts = null;
    private ArrayList<String> _tablesOfContents = null;
    private HashMap<String, ArrayList<String>> _subjects = null;
    private ArrayList<String> _classifications = null;
    private ArrayList<String> _relatedItems = null;
    //Language ISO Helper
    private LanguageISOHelper _langISOHelper = null;
    private File _xmlFile = null;
    private Logger _logger = null;
    private AutomaticLangDetector _detector = null;

    /**
     * Default constructor
     * @param xmlFile XML file with the metadata (absolute path)
     */
    public Mods(File xmlFile) {
        _identifiers = new ArrayList<String>();
        _titles = new HashMap<String, String>();
        _languages = new ArrayList<String>();
        _abstracts = new HashMap<String, String>();
        _subjects = new HashMap<String, ArrayList<String>>();
        _classifications = new ArrayList<String>();
        _dates = new ArrayList<String>();
        _creators = new ArrayList<String>();
        _contributors = new ArrayList<String>();
        _publishers = new ArrayList<String>();
        _locations = new ArrayList<String>();
        _dates = new ArrayList<String>();
        _creators = new ArrayList<String>();
        _types = new ArrayList<String>();
        _tablesOfContents = new ArrayList<String>();
        _relatedItems = new ArrayList<String>();

        //Language ISO Helper
        _langISOHelper = new LanguageISOHelper();
        _xmlFile = xmlFile;
    }

    /**
     * Constructor with the language detector
     * @param xmlFile XML file with the metadata (absolute path)
     * @param logger Logger
     */
    public Mods(File xmlFile, Logger logger) {
        this(xmlFile);
        _logger = logger;
    }

    /**
     * Constructor with all the parameters
     * @param xmlFile XML file with the metadata (absolute path)
     * @param logger Logger
     * @param langDetector Language detector
     */
    public Mods(File xmlFile, Logger logger,
            AutomaticLangDetector langDetector) {
        this(xmlFile, logger);
        _detector = langDetector;
    }

    /**
     * This method works with the MODS XML FILE and parses it into a
     * Mods object
     * @return 
     */
    public void parseModsXML() {

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(_xmlFile);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            translateNodes(root);
            is.close();
        } catch (JDOMException ex) {
            Logger.getLogger(Mods.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (IOException ex) {
            Logger.getLogger(Mods.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * Translate each XML node
     * @param root DOM element
     */
    public void translateNodes(Element root) {


        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace modsNS = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
        Namespace xlinkNS = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");
        for (Element child : children) {
            //         System.out.println(child.getName());
            if (child.getNamespace().equals(modsNS)) {
                String childrenName = child.getName();

    //         <mods:identifier type="urn">urn:nbn:se:slu:epsilon-1-181</mods:identifier>
    //         <mods:identifier type="uri">http://pub.epsilon.slu.se/3892/</mods:identifier>
                if (childrenName.compareTo("identifier") == 0) {

                    this.addIdentifier(child.getTextTrim());
                }

    //          <mods:titleInfo>
    //             <mods:title>Maskinell skörd av äpplen</mods:title>
    //          </mods:titleInfo>
                else if (childrenName.compareTo("title") == 0) {
                    String titleValue = child.getTextTrim();
                    //I try to obtain the language from the attribute in the XML
                    String lang = child.getAttributeValue("lang", xmlNS);
                    String iso_lang = null;
                    if (lang != null) {
                        iso_lang = new String();
                        iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
                    }
                    if (iso_lang == null) {
                        //I try to autodetec the language
                        if (_detector != null) {
                            iso_lang = _detector.detectLang(titleValue);
                        } else {
                            iso_lang = "en";
                        }

                        if (_logger != null) {
                            String message = new String();
                            if (_xmlFile != null) {
                                message = _xmlFile.getName() + " ";
                            }
                            message += "TITLE -> ";
                            if (_detector != null) {
                                message += "language autodetected: " + iso_lang
                                        + " ";
                            } else {
                                message += "language by default: " + iso_lang;
                            }
                            _logger.log(Level.WARNING, message);
                        }
                    }
                    this.addTitle(iso_lang, titleValue);
                }
    //          <mods:language usage="primary">
    //              <mods:languageTerm type="code" authority="iso639-2b">swe</mods:languageTerm>
    //          </mods:language>
                else if (childrenName.compareTo("languageTerm") == 0) {

                    //I try to obtain the language from the attribute in the XML
                    String lang = child.getTextTrim().toLowerCase();
                    String iso_lang = null;
                    if (lang != null) {
                        iso_lang = new String();
                        iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
                    }
                    if (iso_lang == null) {
                        //TODO: error log
                        if (_logger != null) {
                            String message = new String();
                            if (_xmlFile != null) {
                                message = _xmlFile.getName() + " ";
                            }
                            message += "LANGUAGE not found.";
                            _logger.log(Level.WARNING, message);
                        }
                        //Eglish will be the default value
                        iso_lang = "en";
                    }
                    this.addLanguage(iso_lang);
                }
    //          <mods:abstract>Mechanical harvest oconsume.</mods:abstract>
                else if (childrenName.compareTo("abstract") == 0) {
                    String abstractValue = child.getTextTrim();
                    //I try to obtain the language from the attribute in the XML
                    String lang = child.getAttributeValue("lang", xmlNS);
                    String iso_lang = null;
                    if (lang != null) {
                        iso_lang = new String();
                        iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
                    }
                    if (iso_lang == null) {
                        //I try to autodetec the language
                        if (_detector != null) {
                            iso_lang = _detector.detectLang(abstractValue);
                        } else {
                            iso_lang = "en";
                        }
                        if (_logger != null) {
                            String message = new String();
                            if (_xmlFile != null) {
                                message = _xmlFile.getName() + " ";
                            }
                            message += "DESCRIPTION -> ";
                            if (_detector != null) {
                                message += "language autodetected: " + iso_lang
                                        + " ";
                            } else {
                                message += "language by default: " + iso_lang;
                            }
                            _logger.log(Level.WARNING, message);
                        }
                    }
                    this.addAbstract(iso_lang, abstractValue);
                }
    //            <mods:subject>
    //                <mods:topic>maskninell skörd</mods:topic>
    //                <mods:topic>äpplen</mods:topic>
    //                <mods:topic>trädfrukt</mods:topic>
    //            </mods:subject>

                else if (childrenName.compareTo("topic") == 0) {
                    String subjectValue = child.getTextTrim();
                    //I try to obtain the language from the attribute in the XML
                    String lang = child.getAttributeValue("lang", xmlNS);
                    String iso_lang = null;
                    if (lang != null) {
                        iso_lang = new String();
                        iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
                    }
                    if (iso_lang == null) {
                        //I try to autodetec the language
                        if (_detector != null) {
                            iso_lang = _detector.detectLang(subjectValue);
                        } else {
                            iso_lang = "en";
                        }
                        if (_logger != null) {
                            String message = new String();
                            if (_xmlFile != null) {
                                message = _xmlFile.getName() + " ";
                            }
                            message += "SUBJECT -> ";
                            if (_detector != null) {
                                message += "language autodetected: " + iso_lang
                                        + " ";
                            } else {
                                message += "language by default: " + iso_lang;
                            }
                            _logger.log(Level.WARNING, message);
                        }
                    }
                    this.addSubject(iso_lang, subjectValue);
                }


    //            <mods:originInfo>
    //                <mods:dateIssued encoding="iso8601">1991</mods:dateIssued>
    //            </mods:originInfo>

                else if (childrenName.compareTo("dateIssued") == 0) {
                    this.addDate(child.getTextTrim());
                }
    //          <mods:genre>Report</mods:genre>
    //          <mods:typeOfResource>Text</mods:typeOfResource>

                else if ((childrenName.compareTo("genre") == 0) ||
                        (childrenName.compareTo("typeOfResource") == 0) ||
                        (childrenName.compareTo("internetMediaType") == 0 )){
                    this.addType(child.getTextTrim());
                }

    //          <mods:name type="personal">
    //              <mods:namePart type="given">Sven</mods:namePart>
    //              <mods:namePart type="family">Olander</mods:namePart>
    //              <mods:role>
    //                  <mods:roleTerm type="text">author</mods:roleTerm>
    //              </mods:role>
    //          </mods:name>
                else if (childrenName.compareTo("name") == 0) {

                    String type = child.getAttributeValue("type");
                    if (type == null || type.compareTo("personal") == 0)
                    {
                        List<Element> names = child.getChildren("namePart", modsNS);
                        Element role = child.getChild("role", modsNS);

                        if (names != null)
                        {
                            String fullN = "";
                            for (Element el:names)
                            {
                            fullN = fullN +" "+ el.getTextTrim();
                            }

                            if (role != null)
                            {
                                String roleS = role.getChildText("roleTerm", modsNS);
                                if (roleS.equalsIgnoreCase("creator") || roleS.equalsIgnoreCase("author"))
                                {
                                    this.addCreator(fullN);
                                }
                                else 
                                {
                                    this.addContributor(fullN);
                                }
                            }
                            else {
                                this.addContributor(fullN);
                            }
                        }
                    }
                }

    //        <mods:classification authority="SLU">Agricultural engineering</mods:classification>
                else if (childrenName.compareTo("classification") == 0)
                {
                    this.addClassification(child.getTextTrim());
                }
                else if (childrenName.compareTo("url") == 0) {
                    this.addLocation(child.getTextTrim());
                }
                else if (childrenName.compareTo("relatedItem") == 0)
                {
                    Element relat = child.getChild("identifier", modsNS);

                    if (relat != null)
                    {
                        String type = relat.getAttributeValue("type"); 

                        this.addRelatedItem(type +" " + relat.getTextTrim());
                    }
                }
                else {
                    translateNodes(child);
                }
            } else {
                // url in mets
                //<mets:FLocat LOCTYPE="URL" xlink:type="simple" xlink:href="http://oatao.univ-toulouse.fr/138/1/picco_138.PDF" />
                String childrenName = child.getName();
                if (childrenName.equalsIgnoreCase("FLocat")) {
                    System.out.println("found flocat");
                    String loctype = child.getAttributeValue("LOCTYPE");
                    System.out.println("loctype:"+ loctype);
                    if (loctype != null && loctype.equalsIgnoreCase("URL")) {
                        String url = child.getAttributeValue("href", xlinkNS);
                        System.out.println("url:" + url);
                        if (url != null) {
                            this.addLocation(url);
                        }
                    }
                }
                translateNodes(child);
            }
        }
    }

    /**
     * @return the _titles
     */
    public HashMap<String, String> getTitles() {
        return _titles;
    }
    
    public String getTitle(String lang) {
        return _titles.get(lang);
    }

    /**
     * Add the title in the correct language
     * @param lang Language
     * @param title the _titles to set
     */
    public void addTitle(String lang, String title) {
        this._titles.put(lang, title);
    }

    /**
     * @return the _languages
     */
    public ArrayList<String> getLanguages() {
        return _languages;
    }

    /**
     * @param languages the _languages to set
     */
    public void addLanguage(String languages) {
        this._languages.add(languages);
    }

    /**
     * @return the _identifiers
     */
    public ArrayList<String> getIdentifiers() {
        return _identifiers;
    }

    /**
     * Add a new identifier
     * @param identifier the _identifiers to set
     */
    public void addIdentifier(String identifier) {
        this._identifiers.add(identifier);
    }

    /**
     * @return the _locations
     */
    public ArrayList<String> getLocations() {
        return _locations;
    }

    /**
     * @param locations the _locations to set
     */
    public void addLocation(String location) {
        this._locations.add(location);
    }

    /**
     * @return the _dates
     */
    public ArrayList<String> getDates() {
        return _dates;
    }

    /**
     * @param dates the _dates to set
     */
    public void addDate(String date) {
        this._dates.add(date);
    }

    /**
     * @return the _creators
     */
    public ArrayList<String> getCreators() {
        return _creators;
    }

    /**
     * @param creators the _creators to set
     */
    public void addCreator(String creator) {
        this._creators.add(creator);
    }

    /**
     * @return the _publishers
     */
    public ArrayList<String> getPublishers() {
        return _publishers;
    }

    /**
     * @param publishers the _publishers to set
     */
    public void addPublisher(String publisher) {
        this._publishers.add(publisher);
    }

    /**
     * @return the _contributors
     */
    public ArrayList<String> getContributors() {
        return _contributors;
    }

    /**
     * @param contributors the _contributors to set
     */
    public void addContributor(String contributor) {
        this._contributors.add(contributor);
    }

  
    /**
     * @return the _genres
     */
    public ArrayList<String> getTypes() {
        return _types;
    }

    /**
     * @param genres the _genres to set
     */
    public void addType(String type) {
        this._types.add(type);
    }

    /**
     * @return the _abstracts
     */
    public HashMap<String, String> getAbstracts() {
        return _abstracts;
    }
    
    public String getAbstract(String lang) {
        return _abstracts.get(lang);
    }

    /**
     * @param abstracts the _abstracts to set
     */
    public void addAbstract(String lang, String abst) {
        this._abstracts.put(lang, abst);
    }

    /**
     * @return the _tablesOfContents
     */
    public ArrayList<String> getTablesOfContents() {
        return _tablesOfContents;
    }

    /**
     * @param tablesOfContents the _tablesOfContents to set
     */
    public void addTableOfContents(String tableOfContents) {
        this._tablesOfContents.add(tableOfContents);
    }

    /**
     * @return the _subjects
     */
    public HashMap<String, ArrayList<String>> getSubjects() {
        return _subjects;
    }
    
    /**
     * @return the _subjects in the specified language
     */
    public ArrayList<String> getSubjects(String lang) {
        return _subjects.get(lang);
    }

    /**
     * @param subjects the _subjects to set
     */
    public void addSubject(String lang, String subject) {
        ArrayList<String> subjectsInLang = this._subjects.get(lang);
        if (subjectsInLang == null) {
            subjectsInLang = new ArrayList<String>();
        }
        subjectsInLang.add(subject);
        this._subjects.put(lang, subjectsInLang);

    }

    /**
     * @return the _classifications
     */
    public ArrayList<String> getClassifications() {
        return _classifications;
    }

    /**
     * @param classifications the _classifications to set
     */
    public void addClassification(String classification) {
        this._classifications.add(classification);
    }

    /**
     * @return the _relatedItems
     */
    public ArrayList<String> getRelatedItems() {
        return _relatedItems;
    }

    /**
     * @param relatedItems the _relatedItems to set
     */
    public void addRelatedItem(String relatedItem) {
        this._relatedItems.add(relatedItem);
    }

    /**
     * @return the _langISOHelper
     */
    public LanguageISOHelper getLangISOHelper() {
        return _langISOHelper;
    }
    
    @Override
    public String toString() {
        String ret = "MODS {\n"
            + "\ttitle: " + this.getTitles().toString() + "\n"
            + "\tcreator: " + this.getCreators().toString() + "\n"
            + "\tcontributor: " + this.getContributors().toString() + "\n"
            + "\tpublisher: " + this.getPublishers().toString() + "\n"
            + "\tdate: " + this.getDates().toString() + "\n"
            + "\tlanguage: " + this.getLanguages().toString() + "\n"
            + "\tidentifier: " + this.getIdentifiers().toString() + "\n"
            + "\tsubject: " + this.getSubjects().toString() + "\n"
            + "\tabstract: " + this.getAbstracts().toString() + "\n"
            + "\tclassifications: " + this.getClassifications() + "\n"
            + "\tlocations: " + this.getLocations() + "\n"
            + "\trelated items: " + this.getRelatedItems() + "\n"
            + "\ttables of contents: " + this.getTablesOfContents() + "\n"
            + "\ttype: " + this.getTypes().toString() + "\n";
        return ret;
    }
}
