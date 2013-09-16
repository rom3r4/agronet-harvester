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
package org.ontspace.voa3rap4.translator;

import org.ontspace.voa3rap4.translator.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.ontspace.agrovoc.cache.AgrovocCacheHelper;
import org.ontspace.owl.util.AutomaticLangDetector;
import org.ontspace.owl.util.LanguageISOHelper;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 * Java class to model the VOA3R AP level 4 resource
 *
 * @author Raquel
 */
public class Voa3rAP4 extends Voa3rAP2 {

    public static LanguageISOHelper langISOHelper = new LanguageISOHelper();
    public static AutomaticLangDetector langDetector;
    
    public static String normalizeLang(String lang, String text) {
        String iso_lang = null;
        if (lang != null) {
            iso_lang = langISOHelper.getISO_639_1_fromText(lang);
        }
        if (iso_lang == null) {
            //I try to autodetec the language
            if (langDetector != null) {
                iso_lang = langDetector.detectLang(text);
            } else {
                iso_lang = "en";
            }
        }
        
        return iso_lang;
    }
    
  public static LangValuePair getLangAndValue(Element statement) {
    String lang, value;

    Element el = (Element) statement.getChildren().get(0);
    lang = el.getAttributeValue("lang", xmlNS);
    value = el.getValue();
    
    lang = normalizeLang(lang, value);

    return new LangValuePair(lang, value);
  }
  
  public static String getLiteral(Element statement) {
    String lit = statement.getValue();
    return lit != null ? lit.trim() : ""; 
  }
  
  public static String getValueURI(Element statement) {
    String val = statement.getAttributeValue("valueURI", dcdsNS);
    if (val!=null) {
      try {
        URI valURI = new URI(val.trim());
      } catch (URISyntaxException ex) {
        logger.log(Level.SEVERE, "Illegal URI in valueURI", ex);
        return null;
      }
    }
    return val;
  }
  
  private Element findDCDSDescription(String valueRef) {
    if (this.root == null || valueRef == null) {
      return null;
    }
    Iterator<Element> allDCDSDescriptions = root.getDescendants(new Filter() {
      @Override
      public boolean matches(Object o) {
        if (! (o instanceof Element)) {
          return false;
        }
        Element e = (Element) o;
        return e.getName().equals("description");
      }
    } );
    while (allDCDSDescriptions.hasNext()) {
      Element description = allDCDSDescriptions.next();
      String resId = description.getAttributeValue("resourceId", dcdsNS);
      if (valueRef.equals(resId)) {
        return description;
      }
    }
//    List<Element> allDescriptions = root.getChildren("description", dcdsNS);
//    for (Element description : allDescriptions) {
//      String resId = description.getAttributeValue("resourceId", dcdsNS);
//      if (valueRef.equals(resId)) {
//        return description;
//      }
//    }
    return null;
  }

  public Vap4Agent getAgent(Element statement) {
    String valueRef = statement.getAttributeValue("valueRef", dcdsNS);
    if (valueRef == null) {
      return null;
    }
    
    Element agentDescription = findDCDSDescription(valueRef);

    if (agentDescription != null) {
      Vap4Agent agent = new Vap4Agent(agentDescription);
      agent.parse();
      return agent;
    } else {
     logger.log(Level.SEVERE, "Agent reference for " + valueRef + " not found in the XML.");
    }
    
    return null;
  }
  
  private Vap4MetaMetadata getMetaMetadata(Element statement) {
    String valueRef = statement.getAttributeValue("valueRef", dcdsNS);
    Element mmDescription = findDCDSDescription(valueRef);
    
    if (mmDescription != null) {
      Vap4MetaMetadata mm = new Vap4MetaMetadata(mmDescription, this);
      mm.parse();
      return mm;
    } else {
      logger.log(Level.SEVERE, "MetaMetadata reference for " + valueRef + " not found in the XML.");
    }
    
    return null;
  }
  
  private Vap4Research getResearch(Element statement) {
    String valueRef = statement.getAttributeValue("valueRef", dcdsNS);
    Element resDescription = findDCDSDescription(valueRef);
    
    if (resDescription != null) {
      Vap4Research res = new Vap4Research(resDescription);
      res.parse();
      return res;
    } else {
      logger.log(Level.SEVERE, "Research reference for " + valueRef + " not found in the XML.");
    }
    
    return null;
  }

  private enum DesType {

    RESOURCE, AGENT, RESEARCH, METAMETADATA, UNKNOWN
  };
  
  private LanguageISOHelper _langISOHelper = new LanguageISOHelper();
  
  //resource url:
  private String _url = "";
  //qdc new in vap4
  private ArrayList<String> _hasVersion = new ArrayList();
  private ArrayList<String> _isVersionOf = new ArrayList();
  //voa3r new in vap4
  private ArrayList<String> _hasTranslation = new ArrayList();
  private ArrayList<String> _isTranslationOf = new ArrayList();
  private ArrayList<Vap4MetaMetadata> _hasMetametadata = new ArrayList();
  private ArrayList<Vap4Research> _hasResearch = new ArrayList();
  //different type that in vap2 or dc:
  // now creator contributor and publisher can be agents (Vap4Agent) or Strings
  private ArrayList _creators = new ArrayList();
  private ArrayList _contributors = new ArrayList();
  private ArrayList _publishers = new ArrayList();
  private HashMap<String, ArrayList<String>> _right = new HashMap<String, ArrayList<String>>(); 
  private static Namespace dcdsNS = Namespace.getNamespace("dcds", "http://purl.org/dc/xmlns/2008/09/01/dc-ds-xml/");
  private static Namespace xmlNS = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");
  private Element root;
  private static final Logger logger = Logger.getLogger(Voa3rAP4.class.getName());

  public Voa3rAP4(File xmlFile) {
    super(xmlFile);
//    Handler handler;
//    try {
//      handler = new FileHandler("/tmp/vap4.log");
//      logger.addHandler(handler);
//    } catch (IOException ex) {
//      logger.log(Level.SEVERE, null, ex);
//    } catch (SecurityException ex) {
//      logger.log(Level.SEVERE, null, ex);
//    }
  }

  public Voa3rAP4(File xmlFile, Logger logger, AutomaticLangDetector langDetector) {
    super(xmlFile, logger, langDetector);
  }

  public Voa3rAP4(File xmlFile, Logger logger) {
    super(xmlFile, logger);
  }

  public void parseVoa3rAP4XML() {
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      InputStream is = new FileInputStream(_xmlFile);
      try {
        doc = builder.build(is);
      } catch (JDOMException ex) {
        logger.log(Level.WARNING, "UTF8 encoding failed. Trying windows-1251");
        try {
          is.close();
          is = new FileInputStream(_xmlFile);
          doc = builder.build(new InputStreamReader(is, "windows-1251"));
        } catch (JDOMException ex1) {
          logger.log(Level.SEVERE, "Error parsing the file.", ex1);
        }
      }
      if (doc != null) {
        this.root = doc.getRootElement();
        translateNodes(root);
      }
      is.close();
    } catch (IOException ex) {
      logger.log(Level.SEVERE, null,
          ex);
    }
  }

  @Override
  public void translateNodes(Element root) {
    translateDCDSDescriptionNodes(root);
//    super.translateNodes(root);
//    translateQDCNodes(root);
//    translateMarcrelNodes(root);
//    translateEseNodes(root);
//    translateVap4Nodes(root);
    for (Object child: root.getChildren()) {
      translateNodes((Element)child);
    }
  }

  private void translateDCDSDescriptionNodes(Element root) {

    List<Element> children = root.getChildren("description", dcdsNS);

    for (Element dcdsDescription : children) {
      //Identify the dcds descrition type
      switch (identifyDCDSDescriptionType(dcdsDescription)) {
        case RESOURCE:
          parseResource(dcdsDescription);
          break;

        case AGENT:
          break;
      }

    }
  }

  private DesType identifyDCDSDescriptionType(Element dcdsDescription) {
    if (dcdsDescription == null) {
      return DesType.UNKNOWN;
    }

    if (dcdsDescription.getAttribute("resourceURI", dcdsNS) != null) {
      return DesType.RESOURCE;
    }

    return DesType.UNKNOWN;
  }

  private void parseResource(Element dcdsDescription) {
    System.out.println("New Resource found...");
    String resURI = dcdsDescription.getAttribute("resourceURI", dcdsNS).getValue().trim();
    System.out.println("[resourceURI] => " + resURI);
    if (resURI != null && ! resURI.isEmpty()) {
        this.setURL(resURI);
    }

    List<Element> statements = dcdsDescription.getChildren("statement", dcdsNS);
    for (Element statement : statements) {
      String propURIstr = statement.getAttributeValue("propertyURI", dcdsNS);

      if (propURIstr != null && !propURIstr.isEmpty()) {
        try {
          //test if the uri is valid and get the last segment (the property name)
          URI propURI = new URI(propURIstr.trim());
          String[] segments = propURI.getPath().split("/");
          String propName = segments[segments.length - 1];
          String methodName = "parse" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
          try {
            Method parseMethod = Voa3rAP4.class.getDeclaredMethod(methodName, Element.class);
            parseMethod.invoke(this, statement);

          } catch (NoSuchMethodException ex) {
            logger.log(Level.WARNING, "[Illegal property, "+propName+"] The method " + methodName + " does not exist.");
          } catch (SecurityException ex) {
            logger.log(Level.SEVERE, "The method " + methodName + " can not be called due to security reasons.", ex);
          } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error invoking method " + methodName + ".", ex);
          }


        } catch (URISyntaxException ex) {
          logger.log(Level.SEVERE, "Invalid property URI", ex);
        }
      }
    }
  }

  private void parseTitle(Element statement) {
    LangValuePair lv = getLangAndValue(statement);

    this.addTitle(lv.getLang(), lv.getValue());

  }

  private void parseAlternative(Element statement) {
    LangValuePair lv = getLangAndValue(statement);

    this.addAlternative(lv.getLang(), lv.getValue());

  }

  private void parseCreator(Element statement) {
    
    Vap4Agent agent = getAgent(statement);
    if (agent != null) {
      addCreator(agent);
    } else {
      String valURI = getValueURI(statement);
      if (valURI != null && ! valURI.isEmpty()) {
        addCreator(valURI);
      }
    }
    
  }

  private void parseContributor(Element statement) {
    
    Vap4Agent agent = getAgent(statement);
    if (agent != null) {
      addContributor(agent);
    } else {
      String valURI = getValueURI(statement);
      if (valURI != null && ! valURI.isEmpty()) {
        addContributor(valURI);
      }
    }
    
  }

  private void parsePublisher(Element statement) {
    
    Vap4Agent agent = getAgent(statement);
    if (agent != null) {
      addPublisher(agent);
    } else {
      String valURI = getValueURI(statement);
      if (valURI != null && ! valURI.isEmpty()) {
        addPublisher(valURI);
      }
    }
    
  }

  private void parseDate(Element statement) {
    String date = getLiteral(statement);
    if (! date.isEmpty()) {
      addDate(date);
    }
  }

  private void parseLanguage(Element statement) {
    String lang = getLiteral(statement);
    
    String iso_lang = new String();
    if (! lang.isEmpty()) {
        iso_lang = _langISOHelper.getISO_639_1_fromText(lang);
    } else {
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

  private void parseIdentifier(Element statement) {
    String valURI = getValueURI(statement);
    if (valURI != null) {
      addIdentifier(valURI);
    } else {
      String identifier = getLiteral(statement);
      if (! identifier.isEmpty()) {
        addIdentifier(identifier);
      }
    }
  }

  private void parseFormat(Element statement) {
    String format = getLiteral(statement);
    if (! format.isEmpty()) {
      addFormat(format);
    }
  }

  private void parseIsShownBy(Element statement) {
    String isb = getValueURI(statement);
    if (isb != null) {
      addIsShownBy(isb);
    }
  }

  private void parseIsShownAt(Element statement) {
    String isa = getValueURI(statement);
    if (isa != null) {
      addIsShownAt(isa);
    }
  }

  private void parseSubject(Element statement) {
    String subject = getValueURI(statement);
    if (subject != null) {
      this.addAgrovocTerm(subject);
//      addSubject("URI", subject);
    } else {
        boolean isAgrovocTerm = false;
        LangValuePair lv = getLangAndValue(statement);
        String vesURI = statement.getAttributeValue("vesURI", dcdsNS);
        if (vesURI != null && vesURI.equalsIgnoreCase("http://aims.fao.org/aos/agrovoc")) {
            AgrovocCacheHelper ah = AgrovocCacheHelper.getInstance();
            String agrovocURI = ah.getAgrovocId(lv.getValue());
            if (agrovocURI != null) {
                addAgrovocTerm(agrovocURI);
                isAgrovocTerm = true;
            }
        }
        if (!isAgrovocTerm) {
            addSubject(lv.getLang(), lv.getValue());
        }
    }
  }

  private void parseDescription(Element statement) {
    LangValuePair lv = getLangAndValue(statement);

    this.addDescription(lv.getLang(), lv.getValue());

  }

  private void parseAbstract(Element statement) {
    LangValuePair lv = getLangAndValue(statement);

    this.addAbstract(lv.getLang(), lv.getValue());

  }

  private void parseBibliographicCitation(Element statement) {
    String bc = getLiteral(statement);
    if (! bc.isEmpty()) {
      addBibliographicCitation(bc);
    }
  }

  private void parseType(Element statement) {
    String type = getLiteral(statement);
    if (! type.isEmpty()) {
      addType(type);
    }
  }

  private void parseRights(Element statement) {
    String rights = getValueURI(statement);
    if (rights != null) {
      addRight("URI", rights);
    } else {
      LangValuePair lv = getLangAndValue(statement);
      addRight(lv.getLang(), lv.getValue());
    }
  }

  private void parseAccessRights(Element statement) {
    String ar = getValueURI(statement);
    if (ar != null) {
      addAccessRights("URI", ar);
    } else {
      LangValuePair lv = getLangAndValue(statement);
      addAccessRights(lv.getLang(), lv.getValue());
    }
  }

  private void parseLicense(Element statement) {
    String lic = getValueURI(statement);
    if (lic != null) {
      addLicense("URI", lic);
    } else {
      LangValuePair lv = getLangAndValue(statement);
      addLicense(lv.getLang(), lv.getValue());
    }
  }

  private void parseReviewStatus(Element statement) {
    String rs = getLiteral(statement);
    if (! rs.isEmpty()) {
      addReviewStatus(rs);
    }
  }

  private void parsePublicationStatus(Element statement) {
    String ps = getLiteral(statement);
    if (! ps.isEmpty()) {
      addPublicationStatus(ps);
    }
  }

  private void parseRelation(Element statement) {
    String rel = getValueURI(statement);
    if (rel != null) {
      addRelation(rel);
    } else {
      rel = getLiteral(statement);
      if (! rel.isEmpty()) {
        addRelation(rel);
      }
    }
  }

  private void parseConformsTo(Element statement) {
    String ct = getValueURI(statement);
    if (ct != null) {
      addConformsTo(ct);
    } else {
      ct = getLiteral(statement);
      if (! ct.isEmpty()) {
        addConformsTo(ct);
      }
    }
  }

  private void parseReferences(Element statement) {
    String ref = getValueURI(statement);
    if (ref != null) {
      addReferences(ref);
    } else {
      ref = getLiteral(statement);
      if (! ref.isEmpty()) {
        addReferences(ref);
      }
    }
  }

  private void parseIsReferencedBy(Element statement) {
    String irb = getValueURI(statement);
    if (irb != null) {
      addIsReferecedBy(irb);
    } else {
      irb = getLiteral(statement);
      if (! irb.isEmpty()) {
        addIsReferecedBy(irb);
      }
    }
  }

  private void parseHasPart(Element statement) {
    String hp = getValueURI(statement);
    if (hp != null) {
      addHasPart(hp);
    } else {
      hp = getLiteral(statement);
      if (! hp.isEmpty()) {
        addHasPart(hp);
      }
    }
  }

  private void parseIsPartOf(Element statement) {
    String ipo = getValueURI(statement);
    if (ipo != null) {
      addIsPartOf(ipo);
    } else {
      ipo = getLiteral(statement);
      if (! ipo.isEmpty()) {
        addIsPartOf(ipo);
      }
    }
  }

  private void parseHasVersion(Element statement) {
    String hv = getValueURI(statement);
    if (hv != null) {
      addHasVersion(hv);
    } else {
      hv = getLiteral(statement);
      if (! hv.isEmpty()) {
        addHasVersion(hv);
      }
    }
  }

  private void parseIsVersionOf(Element statement) {
    String ivo = getValueURI(statement);
    if (ivo != null) {
      addIsVersionOf(ivo);
    } else {
      ivo = getLiteral(statement);
      if (! ivo.isEmpty()) {
        addIsVersionOf(ivo);
      }
    }
  }

  private void parseHasTranslation(Element statement) {
    String trans = getValueURI(statement);
    if (trans != null) {
      addHasTraslation(trans);
    } else {
      trans = getLiteral(statement);
      if (! trans.isEmpty()) {
        addHasTraslation(trans);
      }
    }
  }

  private void parseIsTranslationOf(Element statement) {
    String ito = getValueURI(statement);
    if (ito != null) {
      addIsTraslationOf(ito);
    } else {
      ito = getLiteral(statement);
      if (! ito.isEmpty()) {
        addIsTraslationOf(ito);
      }
    }
  }

  private void parseHasMetametadata(Element statement) {
    Vap4MetaMetadata mm = getMetaMetadata(statement);
    if (mm != null) {
      addHasMetametadata(mm);
    }
  }

  private void parseHasResearch(Element statement) {
    Vap4Research res = getResearch(statement);
    if (res != null) {
      addHasResearch(res);
    }
  }
  
  public String getURL(){
      return this._url;
  }
  
  public ArrayList getRights(String lang) {
    return _right.get(lang);
  }
  
  public HashMap getRightsHM() {
    return _right;
  }
  
  @Override
  public ArrayList getCreators() {
    return _creators;
  }

  @Override
  public ArrayList getContributors() {
    return _contributors;
  }

  @Override
  public ArrayList getPublishers() {
    return _publishers;
  }

  public ArrayList<String> getHasVersion() {
    return _hasVersion;
  }

  public ArrayList<String> getIsVersionOf() {
    return _isVersionOf;
  }

  public ArrayList<String> getHasTranslation() {
    return _hasTranslation;
  }

  public ArrayList<String> getIsTranslationOf() {
    return _isTranslationOf;
  }

  public ArrayList<Vap4MetaMetadata> getHasMetametadata() {
    return _hasMetametadata;
  }

  public ArrayList<Vap4Research> getHasResearch() {
    return _hasResearch;
  }

  public void setURL(String url) {
      this._url = url;
  }
  
  public void addRight(String lang, String r) {
    ArrayList<String> rightsInLang = this._right.get(lang);
    if (rightsInLang == null) {
      rightsInLang = new ArrayList<String>();
    }
    rightsInLang.add(r);
    this._right.put(lang, rightsInLang);
  }
  
  @Override
  public void addCreator(String creator) {
    _creators.add(creator);
  }

  public void addCreator(Vap4Agent creator) {
    _creators.add(creator);
  }

  @Override
  public void addContributor(String contributor) {
    _contributors.add(contributor);
  }

  public void addContributor(Vap4Agent contributor) {
    _contributors.add(contributor);
  }

  @Override
  public void addPublisher(String publisher) {
    _publishers.add(publisher);
  }

  public void addPublisher(Vap4Agent publisher) {
    _publishers.add(publisher);
  }

  public void addHasVersion(String hv) {
    _hasVersion.add(hv);
  }

  public void addIsVersionOf(String ivo) {
    _isVersionOf.add(ivo);
  }

  public void addHasTraslation(String t) {
    _hasTranslation.add(t);
  }

  public void addIsTraslationOf(String ito) {
    _isTranslationOf.add(ito);
  }

  public void addHasMetametadata(Vap4MetaMetadata m) {
    _hasMetametadata.add(m);
  }

  public void addHasResearch(Vap4Research r) {
    _hasResearch.add(r);
  }

  public boolean validate() {
    boolean isValid = true;
    if (getTitles().size() <= 0) {
      isValid = false;
      logger.log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'title'");
    }
    if (getDates().size() <= 0) {
      isValid = false;
      logger.log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'date'");
    }
    if (getLanguages().size() <= 0) {
      isValid = false;
      logger.log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'language'");
    }
    if (getTypes().size() <= 0) {
      isValid = false;
      logger.log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'type'");
    }
    return isValid;
  }
  
  @Override
  public String toString() {
    String ret = "Voa3rAP4 {\n" +
        "\tURL: " + this.getURL() + "\n" +
        "\ttitle: " + this.getTitles().toString() + "\n" +
        "\talternative: " + this.getAlternative().toString() + "\n" +
        "\tcreator: " + this.getCreators().toString() + "\n" +
        "\tcontributor: " + this.getContributors().toString() + "\n" +
        "\tpublisher: " + this.getPublishers().toString() + "\n" +
        "\tdate: " + this.getDates().toString() + "\n" +
        "\tlanguage: " + this.getLanguages().toString() + "\n" +
        "\tidentifier: " + this.getIdentifiers().toString() + "\n" +
        "\tformat: " + this.getFormats().toString() + "\n" +
        "\tisShownBy: " + this.getIsShownBy().toString() + "\n" +
        "\tisShownAt: " + this.getIsShownAt().toString() + "\n" +
        "\tsubject: " + this.getSubjects().toString() + "\n" +
        "\tdescription: " + this.getDescriptions().toString() + "\n" +
        "\tabstract: " + this.getAbstract().toString() + "\n" +
        "\tbibligraphicCitation: " + this.getBibliographicCitation().toString() + "\n" +
        "\ttype: " + this.getTypes().toString() + "\n" +
        "\trights: " + this.getRightsHM().toString() + "\n" +
        "\taccessRights: " + this.getAccessRights().toString() + "\n" +
        "\tlicense: " + this.getLicense().toString() + "\n" +
        "\treviewStatus: " + this.getReviewStatus().toString() + "\n" +
        "\tpublicationStatus: " + this.getPublicationStatus().toString() + "\n" +
        "\trelation: " + this.getRelations().toString() + "\n" +
        "\tconformsTo: " + this.getConformsTo().toString() + "\n" +
        "\treferences: " + this.getReferences().toString() + "\n" +
        "\tisReferencedBy: " + this.getIsReferencedBy().toString() + "\n" +
        "\thasPart: " + this.getHasPart().toString() + "\n" +
        "\tisPartOf: " + this.getIsPartOf().toString() + "\n" +
        "\thasVersion: " + this.getHasVersion().toString() + "\n" +
        "\tisVersionOf: " + this.getIsVersionOf().toString() + "\n" +
        "\thasTranslation: " + this.getHasTranslation().toString() + "\n" +
        "\tisTranslationOf: " + this.getIsTranslationOf().toString() + "\n" +
        "\thasMetametadata: " + this.getHasMetametadata().toString() + "\n" +
        "\thasResearch: " + this.getHasResearch().toString() + "\n" +
        "\tagrovocTerms: " + this.getAgrovocTerms().toString() + "\n}";
        
    return ret;
  }
  
  
  
  
}
