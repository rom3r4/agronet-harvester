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
package org.ontspace.voa3rap2.translator;

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
import org.ontspace.dc.translator.DublinCore;
import org.ontspace.owl.util.AutomaticLangDetector;
import org.ontspace.owl.util.LanguageISOHelper;
import org.ontspace.qdc.translator.QualifiedDublinCore;

/**
 * Java class to model the VOA3R AP level 2 resource
 *
 * @author Raquel
 */
public class Voa3rAP2 extends DublinCore {

    //qdc
    private HashMap<String, String> _alternative = new HashMap<String, String>();
    private HashMap<String, String> _abstract = new HashMap<String, String>();
    private ArrayList<String> _bibliographicCitation = new ArrayList();
    private HashMap<String, String> _accessRights = new HashMap<String, String>();
    private HashMap<String, String> _license = new HashMap<String, String>();
    private ArrayList<String> _conformsTo = new ArrayList();
    private ArrayList<String> _isReferencedBy = new ArrayList();
    private ArrayList<String> _references = new ArrayList();
    private ArrayList<String> _isPartOf = new ArrayList();
    private ArrayList<String> _hasPart = new ArrayList();
    //marcrel
    private ArrayList<String> _edt = new ArrayList();
    private ArrayList<String> _rev = new ArrayList();
    private ArrayList<String> _trl = new ArrayList();
    //ese
    private ArrayList<String> _isShownBy = new ArrayList();
    private ArrayList<String> _isShownAt = new ArrayList();
    //vap2
    private ArrayList<String> _reviewStatus = new ArrayList();
    private ArrayList<String> _publicationStatus = new ArrayList();
    private ArrayList<String> _objectOfIterest = new ArrayList();
    private ArrayList<String> _measuresVariable = new ArrayList();
    private ArrayList<String> _usesMethod = new ArrayList();
    private ArrayList<String> _usesProtocol = new ArrayList();
    private ArrayList<String> _usesIstrument = new ArrayList();
    private ArrayList<String> _usesTechnique = new ArrayList();

    public Voa3rAP2(File xmlFile) {
        super(xmlFile);
    }

    public Voa3rAP2(File xmlFile, Logger logger, AutomaticLangDetector langDetector) {
        super(xmlFile, logger, langDetector);
    }

    public Voa3rAP2(File xmlFile, Logger logger) {
        super(xmlFile, logger);
    }
    
    
    public void parseVoa3rAP2XML() {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(_xmlFile);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            translateNodes(root);
            translateQDCNodes(root);
            translateMarcrelNodes(root);
            translateEseNodes(root);
            translateVap2Nodes(root);
            is.close();
        } catch (JDOMException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (IOException ex) {
            Logger.getLogger(DublinCore.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

//    @Override
//    public void translateNodes(Element root) {
//        super.translateNodes(root);
//        
//    }
    
    private void translateQDCNodes(Element root) {
        QualifiedDublinCore qdc = new QualifiedDublinCore();
        qdc = qdc.translateNodes(root, qdc);
        this._alternative = qdc.getAlternative();
        this._abstract = qdc.getAbstract();
        this._bibliographicCitation = qdc.getBibliographicCitation();
        this._accessRights = qdc.getAccessRights();
        this._license = qdc.getLicense();
        this._conformsTo = qdc.getConformsTo();
        this._references = qdc.getReferences();
        this._isReferencedBy = qdc.getIsReferencedBy();
        this._hasPart = qdc.getHasPart();
        this._isPartOf = qdc.getIsPartOf();
    }
    
    private void translateMarcrelNodes(Element root) {
        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace marcrelNS = Namespace.getNamespace("marcrel",
                "http://www.loc.gov/marc.relators/");
        for (Element child : children) {
            //         System.out.println(child.getName());
            String childrenName = child.getName();

            if (childrenName.compareTo("edt") == 0) {
                this.addEdt(child.getTextTrim());
            } else if (childrenName.compareTo("rev") == 0) {
                this.addRev(child.getTextTrim());
            } else if (childrenName.compareTo("trl") == 0) {
                this.addTrl(child.getTextTrim());
            }
            translateMarcrelNodes(child);
        }
    }

    private void translateEseNodes(Element root) {
        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace eseNS = Namespace.getNamespace("ese",
                "http://www.europeana.eu/schemas/ese/");
        for (Element child : children) {
            //         System.out.println(child.getName());
            String childrenName = child.getName();

            if (childrenName.compareTo("isShownBy") == 0) {
                this.addIsShownBy(child.getTextTrim());
            } else if (childrenName.compareTo("isShownAt") == 0) {
                this.addIsShownAt(child.getTextTrim());
            }
            translateEseNodes(child);
        }
    }

    private void translateVap2Nodes(Element root) {
        List<Element> children = root.getChildren();
        Namespace xmlNS = Namespace.getNamespace("xml",
                "http://www.w3.org/XML/1998/namespace");
        Namespace voa3rNS = Namespace.getNamespace("voa3r",
                "http://www.voa3r.eu/terms/");
        for (Element child : children) {
            //         System.out.println(child.getName());
            String childrenName = child.getName();

            if (childrenName.compareTo("reviewStatus") == 0) {
                this.addReviewStatus(child.getTextTrim());
            } else if (childrenName.compareTo("publicationStatus") == 0) {
                this.addPublicationStatus(child.getTextTrim());
            } else if (childrenName.compareTo("objectOfInterest") == 0) {
                this.addObjectOfInterest(child.getTextTrim());
            } else if (childrenName.compareTo("measuresVariable") == 0) {
                this.addMeasuresVariable(child.getTextTrim());
            } else if (childrenName.compareTo("usesMethod") == 0) {
                this.addUsesMethod(child.getTextTrim());
            } else if (childrenName.compareTo("usesProtocol") == 0) {
                this.addUsesProtocol(child.getTextTrim());
            } else if (childrenName.compareTo("usesInstrument") == 0) {
                this.addUsesInstrument(child.getTextTrim());
            } else if (childrenName.compareTo("usesTechnique") == 0) {
                this.addUsesTechnique(child.getTextTrim());
            }
            
            translateVap2Nodes(child);
        }
    }
    

    public HashMap<String, String> getAbstract() {
        return _abstract;
    }

    public void addAbstract(String lang, String abs) {
        this._abstract.put(lang, abs);
    }

    public HashMap<String, String> getAccessRights() {
        return _accessRights;
    }

    public void addAccessRights(String lang, String ar) {
        this._accessRights.put(lang, ar);
    }

    public HashMap<String, String> getAlternative() {
        return _alternative;
    }

    public void addAlternative(String lang, String alt) {
        this._alternative.put(lang, alt);
    }

    public ArrayList<String> getBibliographicCitation() {
        return _bibliographicCitation;
    }

    public void addBibliographicCitation(String bc) {
        this._bibliographicCitation.add(bc);
    }

    public ArrayList<String> getConformsTo() {
        return _conformsTo;
    }

    public void addConformsTo(String ct) {
        this._conformsTo.add(ct);
    }

    public ArrayList<String> getEdt() {
        return _edt;
    }

    public void addEdt(String edt) {
        this._edt.add(edt);
    }

    public ArrayList<String> getHasPart() {
        return _hasPart;
    }

    public void addHasPart(String hp) {
        this._hasPart.add(hp);
    }

    public ArrayList<String> getIsPartOf() {
        return _isPartOf;
    }

    public void addIsPartOf(String ipo) {
        this._isPartOf.add(ipo);
    }

    public ArrayList<String> getIsReferencedBy() {
        return _isReferencedBy;
    }

    public void addIsReferecedBy(String irb) {
        this._isReferencedBy.add(irb);
    }

    public ArrayList<String> getIsShownAt() {
        return _isShownAt;
    }

    public void addIsShownAt(String isa) {
        this._isShownAt.add(isa);
    }

    public ArrayList<String> getIsShownBy() {
        return _isShownBy;
    }

    public void addIsShownBy(String isb) {
        this._isShownBy.add(isb);
    }

    public HashMap<String, String> getLicense() {
        return _license;
    }

    public void addLicense(String lang, String l) {
        this._license.put(lang, l);
    }

    public ArrayList<String> getMeasuresVariable() {
        return _measuresVariable;
    }

    public void addMeasuresVariable(String mv) {
        this._measuresVariable.add(mv);
    }

    public ArrayList<String> getObjectOfIterest() {
        return _objectOfIterest;
    }

    public void addObjectOfInterest(String oi) {
        this._objectOfIterest.add(oi);
    }

    public ArrayList<String> getPublicationStatus() {
        return _publicationStatus;
    }

    public void addPublicationStatus(String ps) {
        this._publicationStatus.add(ps);
    }

    public ArrayList<String> getReferences() {
        return _references;
    }

    public void addReferences(String ref) {
        this._references.add(ref);
    }

    public ArrayList<String> getRev() {
        return _rev;
    }

    public void addRev(String rev) {
        this._rev.add(rev);
    }

    public ArrayList<String> getReviewStatus() {
        return _reviewStatus;
    }

    public void addReviewStatus(String re) {
        this._reviewStatus.add(re);
    }

    public ArrayList<String> getTrl() {
        return _trl;
    }

    public void addTrl(String trl) {
        this._trl.add(trl);
    }

    public ArrayList<String> getUsesIstrument() {
        return _usesIstrument;
    }

    public void addUsesInstrument(String ui) {
        this._usesIstrument.add(ui);
    }

    public ArrayList<String> getUsesMethod() {
        return _usesMethod;
    }

    public void addUsesMethod(String um) {
        this._usesMethod.add(um);
    }

    public ArrayList<String> getUsesProtocol() {
        return _usesProtocol;
    }

    public void addUsesProtocol(String up) {
        this._usesProtocol.add(up);
    }

    public ArrayList<String> getUsesTechnique() {
        return _usesTechnique;
    }

    public void addUsesTechnique(String ut) {
        this._usesTechnique.add(ut);
    }

    public boolean validate() {
    boolean isValid = true;
    if (getTitles().size() <= 0) {
      isValid = false;
      Logger.getLogger(Voa3rAP2.class.getName()).log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'title'");
    }
    if (getDates().size() <= 0) {
      isValid = false;
      Logger.getLogger(Voa3rAP2.class.getName()).log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'date'");
    }
    if (getLanguages().size() <= 0) {
      isValid = false;
      Logger.getLogger(Voa3rAP2.class.getName()).log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'language'");
    }
    if (getTypes().size() <= 0) {
      isValid = false;
      Logger.getLogger(Voa3rAP2.class.getName()).log(Level.WARNING, "[INVALID RESOURCE] It doesn't have mandatory field 'type'");
    }
    return isValid;
  }
    
  @Override
  public String toString() {
    String ret = "Voa3rAP2 {\n" +
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
        "\trights: " + this.getRights().toString() + "\n" +
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
        "\tagrovocTerms: " + this.getAgrovocTerms().toString() + "\n}";
    return ret;
  }
}
