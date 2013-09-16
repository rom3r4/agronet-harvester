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
package org.ontspace.dc.translator;

import java.io.*;
import java.sql.*;
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
 * Java class to model the Dublin Core resource
 *
 * @author Raquel
 */
public class DublinCore {

    private ArrayList<String> _identifiers = null;
    private HashMap<String, String> _titles = null;
    private ArrayList<String> _languages = null;
    private HashMap<String, String> _descriptions = null;
    private HashMap<String, ArrayList<String>> _subjects = null;
    private ArrayList<String> _agrovocTerms = null;
    private ArrayList<String> _coverages = null;
    private ArrayList<String> _types = null;
    private ArrayList<String> _dates = null;
    private ArrayList<String> _creators = null;
    private ArrayList<String> _contributors = null;
    private ArrayList<String> _publishers = null;
    private ArrayList<String> _formats = null;
    private ArrayList<String> _rights = null;
    private ArrayList<String> _relations = null;
    private ArrayList<String> _sources = null;
    //Language ISO Helper
    private LanguageISOHelper _langISOHelper = null;
    protected File _xmlFile = null;
    protected Logger _logger = null;
    protected AutomaticLangDetector _detector = null;

    /**
     * Default constructor
     *
     * @param xmlFile XML file with the metadata (absolute path)
     */
    public DublinCore(File xmlFile) {
        _identifiers = new ArrayList();
        _titles = new HashMap<String, String>();
        _languages = new ArrayList();
        _descriptions = new HashMap<String, String>();
        _subjects = new HashMap<String, ArrayList<String>>();
        _agrovocTerms = new ArrayList();
        _coverages = new ArrayList();
        _types = new ArrayList();
        _dates = new ArrayList();
        _creators = new ArrayList();
        _contributors = new ArrayList();
        _publishers = new ArrayList();
        _formats = new ArrayList();
        _rights = new ArrayList();
        _relations = new ArrayList();
        _sources = new ArrayList();
        //Language ISO Helper
        _langISOHelper = new LanguageISOHelper();
        _xmlFile = xmlFile;
    }

    /**
     * Constructor with the language detector
     *
     * @param xmlFile XML file with the metadata (absolute path)
     * @param logger Logger
     */
    public DublinCore(File xmlFile, Logger logger) {
        this(xmlFile);
        _logger = logger;
    }

    /**
     * Constructor with all the parameters
     *
     * @param xmlFile XML file with the metadata (absolute path)
     * @param logger Logger
     * @param langDetector Language detector
     */
    public DublinCore(File xmlFile, Logger logger,
            AutomaticLangDetector langDetector) {
        this(xmlFile, logger);
        _detector = langDetector;
    }

    /**
     * This method receives a DC XML FILE and creates a DC Object that stores
     * its content.
     */
    public void parseDCXML() {

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(_xmlFile);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            translateNodes(root);
            is.close();
        } catch (JDOMException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (IOException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    /**
     * This method receives a DC XML FILE to generate stats about the
     * completeness of the AP.
     */
    public void parseDCXMLStats(String filename) {

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(_xmlFile);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            try {
                translateNodesStats(root, filename);
            } catch (SQLException ex) {
                Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null, ex);
            }
            is.close();
        } catch (JDOMException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Translate each XML node
     *
     * @param root DOM element
     */
    public void translateNodes(Element root) {
        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace dctermsNS = Namespace.getNamespace("dcterms",
                "http://purl.org/dc/terms/");
        Namespace xsiNS = Namespace.getNamespace("xsi",
                "http://www.w3.org/2001/XMLSchema-instance");
        Namespace strNS = Namespace.getNamespace("http://www.openarchives.org/OAI/2.0/");
        for (Element child : children) {
            //         System.out.println(child.getName());
            String childrenName = child.getName();
            Element childString = child.getChild("string", strNS);
            if (childString != null) {
                System.out.println("childstring found in " + childrenName);
                child = childString;
            }

            if (childrenName.compareTo("identifier") == 0) {
                this.addIdentifier(child.getTextTrim());
            }
            if (childrenName.compareTo("title") == 0) {
                String titleValue = child.getTextTrim();
                //I try to obtain the language from the attribute in the XML
                String lang = child.getAttributeValue("lang", xmlNS);
                if (lang == null) {
                    lang = child.getAttributeValue("language");
                }
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
            if (childrenName.compareTo("language") == 0) {
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
            if (childrenName.compareTo("description") == 0) {
                String descriptionValue = child.getTextTrim();
                //I try to obtain the language from the attribute in the XML
                String lang = child.getAttributeValue("lang", xmlNS);
                if (lang == null) {
                    lang = child.getAttributeValue("language");
                }
                String iso_lang = null;
                if (lang != null) {
                    iso_lang = new String();
                    iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
                }
                if (iso_lang == null) {
                    //I try to autodetec the language
                    if (_detector != null) {
                        iso_lang = _detector.detectLang(descriptionValue);
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
                this.addDescription(iso_lang, descriptionValue);
            }
            if (childrenName.compareTo("subject") == 0) {
                String subjectValue = child.getTextTrim();
                if (!subjectValue.isEmpty()) {
                    //I try to obtain the language from the attribute in the XML
                    String lang = child.getAttributeValue("lang", xmlNS);
                    if (lang == null) {
                        lang = child.getAttributeValue("language");
                    }
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
                    String type = child.getAttributeValue("type", xsiNS);
                    if (type != null && type.compareToIgnoreCase("dcterms:URI") == 0) {
                        this.addAgrovocTerm(subjectValue);
                    } else {
                        this.addSubject(iso_lang, subjectValue);
                    }
                }
            }
            if (childrenName.compareTo("coverage") == 0) {
                this.addCoverage(child.getTextTrim());
            }
            if (childrenName.compareTo("type") == 0) {
                this.addType(child.getTextTrim());
            }
            if (childrenName.compareTo("date") == 0) {
                //<dc:date>
                //  <dcterms:dateIssued>1996</dcterms:dateIssued>
                //</dc:date>
                Element dateIssued = child.getChild("dateIssued", dctermsNS);
                if (dateIssued != null) {
                    this.addDate(dateIssued.getTextTrim());
                } else {
                    this.addDate(child.getTextTrim());
                }
            }
            if (childrenName.compareTo("creator") == 0) {
                this.addCreator(child.getTextTrim());
            }
            if (childrenName.compareTo("contributor") == 0) {
                this.addContributor(child.getTextTrim());
            }
            if (childrenName.compareTo("publisher") == 0) {
                this.addPublisher(child.getTextTrim());
            }
            if (childrenName.compareTo("format") == 0) {
                this.addFormat(child.getTextTrim());
            }
            if (childrenName.compareTo("rights") == 0) {
                this.addRights(child.getTextTrim());
            }
            if (childrenName.compareTo("relation") == 0) {
                this.addRelation(child.getTextTrim());
            }
            if (childrenName.compareTo("source") == 0) {
                this.addSource(child.getTextTrim());
            }
            translateNodes(child);
        }
    }

    public void translateNodesStats(Element root, String xmlFile) throws IOException, SQLException {

        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace dctermsNS = Namespace.getNamespace("dcterms",
                "http://purl.org/dc/terms/");
        Namespace xsiNS = Namespace.getNamespace("xsi",
                "http://www.w3.org/2001/XMLSchema-instance");
//        String header = "repository,identifier,title,language,description,subject,coverage,type,date,creator,contributor,publicsher,format,rights,relation,source\n";
        for (Element child : children) {
            //         System.out.println(child.getName());
            String childrenName = child.getName();

            if (childrenName.compareTo("identifier") == 0) {
//                System.out.println("identifier");
                this.updateFieldInStats("identifier", xmlFile);
            }
            if (childrenName.compareTo("title") == 0) {
//                System.out.println("title");
                this.updateFieldInStats("title", xmlFile);
            }
            if (childrenName.compareTo("language") == 0) {
//                System.out.println("language");
                this.updateFieldInStats("language", xmlFile);
            }
            if (childrenName.compareTo("description") == 0) {
//                System.out.println("description");
                this.updateFieldInStats("description", xmlFile);
            }
            if (childrenName.compareTo("subject") == 0) {
                String type = child.getAttributeValue("type", xsiNS);
                if (type != null && type.compareToIgnoreCase("dcterms:URI") == 0) {
//                    System.out.println("agrovoc");
                } else {
//                    System.out.println("keywords");
                }
                this.updateFieldInStats("subject", xmlFile);
            }
            if (childrenName.compareTo("coverage") == 0) {
//                System.out.println("coverage");
                this.updateFieldInStats("coverage", xmlFile);
            }
            if (childrenName.compareTo("type") == 0) {
//                System.out.println("type");
                this.updateFieldInStats("type", xmlFile);
            }
            if (childrenName.compareTo("date") == 0) {
                //<dc:date>
                //  <dcterms:dateIssued>1996</dcterms:dateIssued>
                //</dc:date>
                Element dateIssued = child.getChild("dateIssued", dctermsNS);
                if (dateIssued != null) {
//                    System.out.println("date");
                }
                this.updateFieldInStats("date", xmlFile);
            }
            if (childrenName.compareTo("creator") == 0) {
//                System.out.println("creator");
                this.updateFieldInStats("creator", xmlFile);
            }
            if (childrenName.compareTo("contributor") == 0) {
//                System.out.println("contributor");
                this.updateFieldInStats("contributor", xmlFile);
            }
            if (childrenName.compareTo("publisher") == 0) {
//                System.out.println("publisher");
                this.updateFieldInStats("publisher", xmlFile);
            }
            if (childrenName.compareTo("format") == 0) {
//                System.out.println("format");
                this.updateFieldInStats("format", xmlFile);

            }
            if (childrenName.compareTo("rights") == 0) {
//                System.out.println("rights");
                this.updateFieldInStats("rights", xmlFile);
            }
            if (childrenName.compareTo("relation") == 0) {
//                System.out.println("relation");
                this.updateFieldInStats("relation", xmlFile);
            }
            if (childrenName.compareTo("source") == 0) {
//                System.out.println("source");
                this.updateFieldInStats("source", xmlFile);
            }
            translateNodes(child);
        }
    }

    public void updateFieldInStats(String field, String xmlFile) throws SQLException {
        Connection connection;
        Statement stmt;
        String fieldUpdated = field + "+ 1";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/voa3r_stats",
                    "root", "tragasables");
            stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE voa3r_completeness SET " + field + " = " + fieldUpdated + " where xmlFile = '" + xmlFile + "';");
            connection.close();
            stmt.close();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Obtain all the identifiers
     *
     * @return the _identifiers
     */
    public ArrayList<String> getIdentifiers() {
        return _identifiers;
    }

    /**
     * Obtain all the titles
     *
     * @return the _titles
     */
    public HashMap<String, String> getTitles() {
        return _titles;
    }

    /**
     * Returns the title in the appropriate language
     *
     * @param lang
     * @return the title in the right language, or null if the title is not
     * present in the given language
     */
    public String getTitle(String lang) {
        return _titles.get(lang);
    }

    /**
     * Obtain all the the languages
     *
     * @return the _languages
     */
    public ArrayList<String> getLanguages() {
        return _languages;
    }

    /**
     * Obtain all the descriptions
     *
     * @return the _descriptions
     */
    public HashMap<String, String> getDescriptions() {
        return _descriptions;
    }

    /**
     * Returns the description in the appropriate language
     *
     * @param lang
     * @return the description in the right language
     */
    public String getDescription(String lang) {
        return _descriptions.get(lang);
    }

    /**
     * Obtain all the subjects
     *
     * @return the _subject
     */
    public HashMap<String, ArrayList<String>> getSubjects() {
        return _subjects;
    }

    /**
     * Returns the subject in the appropriate language
     *
     * @param lang language
     * @return the subject in the right language, or null if there are not any
     * subjects in the given language
     */
    public ArrayList<String> getSubjects(String lang) {
        return _subjects.get(lang);
    }

    /**
     * Returns the agrovoc terms in the appropriate language
     *
     * @return the agrovoc terms
     */
    public ArrayList<String> getAgrovocTerms() {
        return _agrovocTerms;
    }

    /**
     * Obtain all the coverages
     *
     * @return the _coverage
     */
    public ArrayList<String> getCoverages() {
        return _coverages;
    }

    /**
     * Obtain all the types
     *
     * @return the _type
     */
    public ArrayList<String> getTypes() {
        return _types;
    }

    /**
     * Obtain all the dates
     *
     * @return the _date
     */
    public ArrayList<String> getDates() {
        return _dates;
    }

    /**
     * Obtain all the creators
     *
     * @return the _creator
     */
    public ArrayList<String> getCreators() {
        return _creators;
    }

    /**
     * Obtain all the contributors
     *
     * @return the _contributor
     */
    public ArrayList<String> getContributors() {
        return _contributors;
    }

    /**
     * Obtain all the publishers
     *
     * @return the _publisher
     */
    public ArrayList<String> getPublishers() {
        return _publishers;
    }

    /**
     * Obtain all the formats
     *
     * @return the _format
     */
    public ArrayList<String> getFormats() {
        return _formats;
    }

    /**
     * Obtain all the rights
     *
     * @return the _rights
     */
    public ArrayList<String> getRights() {
        return _rights;
    }

    /**
     * Obtain all the relations
     *
     * @return the _relation
     */
    public ArrayList<String> getRelations() {
        return _relations;
    }

    /**
     * Obtain all the sources
     *
     * @return the _source
     */
    public ArrayList<String> getSources() {
        return _sources;
    }

    /**
     * Add a new identifier
     *
     * @param identifier the _identifiers to set
     */
    public void addIdentifier(String identifier) {
        this._identifiers.add(identifier);
    }

    /**
     * Add the title in the correct language
     *
     * @param lang Language
     * @param title the _titles to set
     */
    public void addTitle(String lang, String title) {
        this._titles.put(lang, title);
    }

    /**
     * Add a new language
     *
     * @param language the _languages to set
     */
    public void addLanguage(String language) {
        this._languages.add(language);
    }

    /**
     * Add a description in the correct language
     *
     * @param lang Language of the description
     * @param description the _descriptions to set
     */
    public void addDescription(String lang, String description) {
        this._descriptions.put(lang, description);
    }

    /**
     * Add a new subject in the correct language
     *
     * @param lang language of the subject
     * @param subject the _subject to set
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
     * @param coverage the _coverage to set
     */
    public void addAgrovocTerm(String agrovocTerm) {
        this._agrovocTerms.add(agrovocTerm);
    }

    /**
     * @param coverage the _coverage to set
     */
    public void addCoverage(String coverage) {
        this._coverages.add(coverage);
    }

    /**
     * @param type the _type to set
     */
    public void addType(String type) {
        this._types.add(type);
    }

    /**
     * @param date the _date to set
     */
    public void addDate(String date) {
        this._dates.add(date);
    }

    /**
     * @param creator the _creator to set
     */
    public void addCreator(String creator) {
        this._creators.add(creator);
    }

    /**
     * @param contributor the _contributor to set
     */
    public void addContributor(String contributor) {
        this._contributors.add(contributor);
    }

    /**
     * @param publisher the _publisher to set
     */
    public void addPublisher(String publisher) {
        this._publishers.add(publisher);
    }

    /**
     * @param format the _format to set
     */
    public void addFormat(String format) {
        this._formats.add(format);
    }

    /**
     * @param rights the _rights to set
     */
    public void addRights(String rights) {
        this._rights.add(rights);
    }

    /**
     * @param relation the _relation to set
     */
    public void addRelation(String relation) {
        this._relations.add(relation);
    }

    /**
     * @param source the _source to set
     */
    public void addSource(String source) {
        this._sources.add(source);
    }

    /**
     * @return the _langISOHelper
     */
    public LanguageISOHelper getLangISOHelper() {
        return _langISOHelper;
    }
}
