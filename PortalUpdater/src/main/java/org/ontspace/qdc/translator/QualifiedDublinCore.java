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
package org.ontspace.qdc.translator;

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
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Raquel
 */
public class QualifiedDublinCore {

    private ArrayList<String> _identifier = new ArrayList();
    private HashMap<String, String> _title =  new HashMap<String, String>();
    private ArrayList<String> _language = new ArrayList();
    private HashMap<String, String> _description =  new HashMap<String, String>();
    private HashMap<String, ArrayList<String>> _subject =  new HashMap<String, ArrayList<String>>();
    private ArrayList<String> _coverage = new ArrayList();
    private ArrayList<String> _type = new ArrayList();
    private ArrayList<String> _date = new ArrayList();
    private ArrayList<String> _creator = new ArrayList();
    private ArrayList<String> _contributor = new ArrayList();
    private ArrayList<String> _publisher = new ArrayList();
    private ArrayList<String> _format = new ArrayList();
    private ArrayList<String> _rights = new ArrayList();
    private ArrayList<String> _relation = new ArrayList();
    private ArrayList<String> _source = new ArrayList();
    //qualifiers elements
    private ArrayList<String> _audience = new ArrayList();
    private ArrayList<String> _provenance = new ArrayList();
    private ArrayList<String> _rightsHolder = new ArrayList();
    //refined elements
    private HashMap<String, String> _alternative =  new HashMap<String, String>();
    private HashMap<String, String> _tableOfContents =  new HashMap<String, String>();
    private HashMap<String, String> _abstract = new HashMap<String, String>();
    private ArrayList<String> _created = new ArrayList();
    private ArrayList<String> _valid = new ArrayList();
    private ArrayList<String> _available = new ArrayList();
    private ArrayList<String> _issued = new ArrayList();
    private ArrayList<String> _modified = new ArrayList();
    private ArrayList<String> _dateAccepted = new ArrayList();
    private ArrayList<String> _dateCopyrighted = new ArrayList();
    private ArrayList<String> _dateSubmited = new ArrayList();
    private ArrayList<String> _extent = new ArrayList();
    private ArrayList<String> _medium = new ArrayList();
    private ArrayList<String> _bibliographicCitation = new ArrayList();
    private ArrayList<String> _isVersionOf = new ArrayList();
    private ArrayList<String> _hasVersion = new ArrayList();
    private ArrayList<String> _isReplacedBy = new ArrayList();
    private ArrayList<String> _replaces = new ArrayList();
    private ArrayList<String> _isRequiredBy = new ArrayList();
    private ArrayList<String> _requires = new ArrayList();
    private ArrayList<String> _isPartOf = new ArrayList();
    private ArrayList<String> _hasPart = new ArrayList();
    private ArrayList<String> _isReferencedBy = new ArrayList();
    private ArrayList<String> _references = new ArrayList();
    private ArrayList<String> _isFormatOf = new ArrayList();
    private ArrayList<String> _hasFormat = new ArrayList();
    private ArrayList<String> _conformsTo = new ArrayList();
    private ArrayList<String> _spatial = new ArrayList();
    private ArrayList<String> _temporal = new ArrayList();
    private HashMap<String,String> _accessRights = new HashMap<String,String>();
    private HashMap<String,String> _license = new HashMap<String,String>();
    private ArrayList<String> _mediator = new ArrayList();
    private ArrayList<String> _educationLevel = new ArrayList();

    public QualifiedDublinCore() {
    }

    /**
     * This method receives a DC XML FILE and creates a DC Object that
     * stores its content.
     * @param is
     * @return
     */
    public QualifiedDublinCore parseQDCXML(final InputStream is) {

        QualifiedDublinCore qdc = new QualifiedDublinCore();
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(is);
        } catch (JDOMException ex) {
            Logger.getLogger(QualifiedDublinCore.class.getName()).log(
                    Level.SEVERE, null,
                    ex);
        } catch (IOException ex) {
            Logger.getLogger(QualifiedDublinCore.class.getName()).log(
                    Level.SEVERE, null,
                    ex);
        }

        return translateNodes(doc.getRootElement(), qdc);


    }

    public QualifiedDublinCore translateNodes(Element root, QualifiedDublinCore qdc) {

        //tags
        List<String> titleNames = new ArrayList<String>();
        titleNames.add("title");
        titleNames.add("alternative");

        List<String> creatorNames = new ArrayList<String>();
        creatorNames.add("creator");

        List<String> subjectNames = new ArrayList<String>();
        subjectNames.add("subject");

        List<String> descriptionNames = new ArrayList<String>();
        descriptionNames.add("description");
        descriptionNames.add("tableOfContents");
        descriptionNames.add("abstract");

        List<String> publisherNames = new ArrayList<String>();
        publisherNames.add("publisher");

        List<String> contributorNames = new ArrayList<String>();
        contributorNames.add("contributor");

        List<String> dateNames = new ArrayList<String>();
        dateNames.add("date");
        dateNames.add("created");
        dateNames.add("valid");
        dateNames.add("available");
        dateNames.add("issued");
        dateNames.add("modified");
        dateNames.add("dateAccepted");
        dateNames.add("dateCopyrighted");
        dateNames.add("dateSubmited");

        List<String> typeNames = new ArrayList<String>();
        typeNames.add("type");

        List<String> formatNames = new ArrayList<String>();
        formatNames.add("format");
        formatNames.add("extent");
        formatNames.add("medium");

        List<String> identifierNames = new ArrayList<String>();
        identifierNames.add("identifier");
        identifierNames.add("bibliographicCitation");

        List<String> sourceNames = new ArrayList<String>();
        sourceNames.add("source");

        List<String> languageNames = new ArrayList<String>();
        languageNames.add("language");

        List<String> relationNames = new ArrayList<String>();
        relationNames.add("relation");
        relationNames.add("isVersionOf");
        relationNames.add("hasVersion");
        relationNames.add("isReplacedBy");
        relationNames.add("replaces");
        relationNames.add("isRequiredBy");
        relationNames.add("requires");
        relationNames.add("isPartOf");
        relationNames.add("hasPart");
        relationNames.add("isReferencedBy");
        relationNames.add("references");
        relationNames.add("isFormatOf");

        relationNames.add("hasFormat");
        relationNames.add("conformsTo");


        List<String> coverageNames = new ArrayList<String>();
        coverageNames.add("coverage");
        coverageNames.add("spatial");
        coverageNames.add("temporal");

        List<String> rightsNames = new ArrayList<String>();
        rightsNames.add("rights");

        rightsNames.add("accessRights");
        rightsNames.add("license");

        List<String> audienceNames = new ArrayList<String>();
        audienceNames.add("audience");
        audienceNames.add("mediator");
        audienceNames.add("educationLevel");



        List<String> provenanceNames = new ArrayList<String>();
        provenanceNames.add("provenance");

        List<String> rightsHolderNames = new ArrayList<String>();
        rightsHolderNames.add("rightsHolder");


        //XML tree
        List<Element> children = root.getChildren();
        for (Element child : children) {


            String childrenName = child.getName();
            System.out.println(child.getName());
            if (identifierNames.contains(childrenName)) {
                Identifier2Identifier identifier2Identifier = new Identifier2Identifier(child, qdc);
            }

            if (titleNames.contains(childrenName)) {
                Title2Title title2Title = new Title2Title(child, qdc);
            }
            if (languageNames.contains(childrenName)) {
                Language2Language language2Language = new Language2Language(child, qdc);
            }
            if (descriptionNames.contains(childrenName)) {
                Description2Description description2Description = new Description2Description(child, qdc);
            }
            if (subjectNames.contains(childrenName)) {
                Subject2Subject subject2Subject = new Subject2Subject(child, qdc);
            }
            if (coverageNames.contains(childrenName)) {
                Coverage2Coverage coverage2Coverage = new Coverage2Coverage(child, qdc);
            }
            if (typeNames.contains(childrenName)) {
                Type2Type type2Type = new Type2Type(child, qdc);
            }
            if (dateNames.contains(childrenName)) {
                Date2Date date2Date = new Date2Date(child, qdc);
            }
            if (creatorNames.contains(childrenName)) {
                Creator2Creator creator2Creator = new Creator2Creator(child, qdc);
            }
            if (contributorNames.contains(childrenName)) {
                Contributor2Contributor contributor2Contributor = new Contributor2Contributor(child, qdc);
            }
            if (publisherNames.contains(childrenName)) {
                Publisher2Publisher publisher2Publisher = new Publisher2Publisher(child, qdc);
            }
            if (formatNames.contains(childrenName)) {
                Format2Format format2Format = new Format2Format(child, qdc);
            }
            if (rightsNames.contains(childrenName)) {
                Rights2Rights rights2Rights = new Rights2Rights(child, qdc);
            }
            if (relationNames.contains(childrenName)) {
                Relation2Relation relation2Relation = new Relation2Relation(child, qdc);
            }
            if (sourceNames.contains(childrenName)) {
                Source2Source source2Source = new Source2Source(child, qdc);
            }
            if (audienceNames.contains(childrenName)) {
                Audience2Audience audience2Audience = new Audience2Audience(child, qdc);
            }
            if (provenanceNames.contains(childrenName)) {
                Provenance2Provenance provenance2Provenance = new Provenance2Provenance(child, qdc);
            }
            if (rightsHolderNames.contains(childrenName)) {
                RighstHolder2RightsHolder righstHolder2RightsHolder = new RighstHolder2RightsHolder(child, qdc);
            }


            translateNodes(child, qdc);
        }//end for


        return qdc;
    }

    /**
     * @return the _identifier
     */
    public ArrayList<String> getIdentifier() {
        return _identifier;
    }

    /**
     * @return the _title
     */
    public String getTitle(String lang) {
        return _title.get(lang);
    }
    
    /**
     * Obtain all the titles
     * @return the _titles
     */
    public HashMap<String, String> getTitles() {
        return _title;
    }

    /**
     * @return the _language
     */
    public ArrayList<String> getLanguage() {
        return _language;
    }

    /**
     * @return the _description
     */
    public String getDescription(String lang) {
        return _description.get(lang);
    }
    
    /**
     * 
     * @return the _description
     */
    public HashMap <String, String> getDescriptions (){
        return _description;
    }

    /**
     * @return the _subject
     */
    public ArrayList<String> getSubject(String lang) {
        return _subject.get(lang);
    }
    
    /**
     * 
     * @return the _subject
     */
    public HashMap<String, ArrayList<String>> getSubjects(){

        return _subject;
    }

    /**
     * @return the _coverage
     */
    public ArrayList<String> getCoverage() {
        return _coverage;
    }

    /**
     * @return the _type
     */
    public ArrayList<String> getType() {
        return _type;
    }

    /**
     * @return the _date
     */
    public ArrayList<String> getDate() {
        return _date;
    }

    /**
     * @return the _creator
     */
    public ArrayList<String> getCreator() {
        return _creator;
    }

    /**
     * @return the _contributor
     */
    public ArrayList<String> getContributor() {
        return _contributor;
    }

    /**
     * @return the _publisher
     */
    public ArrayList<String> getPublisher() {
        return _publisher;
    }

    /**
     * @return the _format
     */
    public ArrayList<String> getFormat() {
        return _format;
    }

    /**
     * @return the _rights
     */
    public ArrayList<String> getRights() {
        return _rights;
    }

    /**
     * @return the _relation
     */
    public ArrayList<String> getRelation() {
        return _relation;
    }

    /**
     * @return the _source
     */
    public ArrayList<String> getSource() {
        return _source;
    }

    /**
     * @return the _audience
     */
    public ArrayList<String> getAudience() {
        return _audience;
    }

    /**
     * @return the _provenance
     */
    public ArrayList<String> getProvenance() {
        return _provenance;
    }

    /**
     * @return the _rightsHolder
     */
    public ArrayList<String> getRightsHolder() {
        return _rightsHolder;
    }

    /**
     * @return the _alternative
     */
    public String getAlternative(String lang) {
        return _alternative.get(lang);
    }
    
    /**
     * 
     * @return the _alternative
     */
    public HashMap<String, String> getAlternative () {
        return _alternative;
    }

    /**
     * @return the _tableOfContents
     */
    public String getTableOfContents(String lang) {
        return _tableOfContents.get(lang);
    }
    
    /**
     * 
     * @return the _tableOfContents
     */
    public HashMap<String, String> getTableOfContents () {
        return _tableOfContents;
    }

    /**
     * @return the _abstract
     */
    public String getAbstract(String lang) {
        return _abstract.get(lang);
    }
    
    /**
     * 
     * @return the _abstract
     */
    public HashMap<String, String> getAbstract () 
    {
        return _abstract;
    }

    /**
     * @return the _created
     */
    public ArrayList<String> getCreated() {
        return _created;
    }

    /**
     * @return the _valid
     */
    public ArrayList<String> getValid() {
        return _valid;
    }

    /**
     * @return the _available
     */
    public ArrayList<String> getAvailable() {
        return _available;
    }

    /**
     * @return the _issued
     */
    public ArrayList<String> getIssued() {
        return _issued;
    }

    /**
     * @return the _modified
     */
    public ArrayList<String> getModified() {
        return _modified;
    }

    /**
     * @return the _dateAccepted
     */
    public ArrayList<String> getDateAccepted() {
        return _dateAccepted;
    }

    /**
     * @return the _dateCopyrighted
     */
    public ArrayList<String> getDateCopyrighted() {
        return _dateCopyrighted;
    }

    /**
     * @return the _dateSubmited
     */
    public ArrayList<String> getDateSubmited() {
        return _dateSubmited;
    }

    /**
     * @return the _extent
     */
    public ArrayList<String> getExtent() {
        return _extent;
    }

    /**
     * @return the _medium
     */
    public ArrayList<String> getMedium() {
        return _medium;
    }

    /**
     * @return the _bibliographicCitation
     */
    public ArrayList<String> getBibliographicCitation() {
        return _bibliographicCitation;
    }

    /**
     * @return the _isVersionOf
     */
    public ArrayList<String> getIsVersionOf() {
        return _isVersionOf;
    }

    /**
     * @return the _hasVersion
     */
    public ArrayList<String> getHasVersion() {
        return _hasVersion;
    }

    /**
     * @return the _isReplacedBy
     */
    public ArrayList<String> getIsReplacedBy() {
        return _isReplacedBy;
    }

    /**
     * @return the _replaces
     */
    public ArrayList<String> getReplaces() {
        return _replaces;
    }

    /**
     * @return the _isRequiredBy
     */
    public ArrayList<String> getIsRequiredBy() {
        return _isRequiredBy;
    }

    /**
     * @return the _requires
     */
    public ArrayList<String> getRequires() {
        return _requires;
    }

    /**
     * @return the _isPartOf
     */
    public ArrayList<String> getIsPartOf() {
        return _isPartOf;
    }

    /**
     * @return the _hasPart
     */
    public ArrayList<String> getHasPart() {
        return _hasPart;
    }

    /**
     * @return the _isReferencedBy
     */
    public ArrayList<String> getIsReferencedBy() {
        return _isReferencedBy;
    }

    /**
     * @return the _references
     */
    public ArrayList<String> getReferences() {
        return _references;
    }

    /**
     * @return the _isFormatOf
     */
    public ArrayList<String> getIsFormatOf() {
        return _isFormatOf;
    }

    /**
     * @return the _hasFormat
     */
    public ArrayList<String> getHasFormat() {
        return _hasFormat;
    }

    /**
     * @return the _conformsTo
     */
    public ArrayList<String> getConformsTo() {
        return _conformsTo;
    }

    /**
     * @return the _spatial
     */
    public ArrayList<String> getSpatial() {
        return _spatial;
    }

    /**
     * @return the _temporal
     */
    public ArrayList<String> getTemporal() {
        return _temporal;
    }

    /**
     * @return the _accessRights
     */
    public HashMap<String,String> getAccessRights() {
        return _accessRights;
    }

    /**
     * @return the _license
     */
    public HashMap<String,String> getLicense() {
        return _license;
    }

    /**
     * @return the _mediator
     */
    public ArrayList<String> getMediator() {
        return _mediator;
    }

    /**
     * @return the _educationLevel
     */
    public ArrayList<String> getEducationLevel() {
        return _educationLevel;
    }

    /**
     * @param identifier the _identifier to set
     */
    public void addIdentifier(String identifier) {
        this._identifier.add(identifier);
    }

    /**
     * @param title the _title to set
     */
    public void addTitle(String lang, String title) {
        this._title.put(lang, title);
    }

    /**
     * @param language the _language to set
     */
    public void addLanguage(String language) {
        this._language.add(language);
    }

    /**
     * @param description the _description to set
     */
    public void addDescription(String lang, String description) {
        this._description.put(lang, description);
    }

    /**
     * @param subject the _subject to set
     */
    public void addSubject(String lang, String subject) {
        ArrayList<String> subjectsInLang = this._subject.get(lang);
        if (subjectsInLang == null) {
            subjectsInLang = new ArrayList<String>();
        }
        subjectsInLang.add(subject);
        this._subject.put(lang, subjectsInLang);
    }

    /**
     * @param coverage the _coverage to set
     */
    public void addCoverage(String coverage) {
        this._coverage.add(coverage);
    }

    /**
     * @param type the _type to set
     */
    public void addType(String type) {
        this._type.add(type);
    }

    /**
     * @param date the _date to set
     */
    public void addDate(String date) {
        this._date.add(date);
    }

    /**
     * @param creator the _creator to set
     */
    public void addCreator(String creator) {
        this._creator.add(creator);
    }

    /**
     * @param contributor the _contributor to set
     */
    public void addContributor(String contributor) {
        this._contributor.add(contributor);
    }

    /**
     * @param publisher the _publisher to set
     */
    public void addPublisher(String publisher) {
        this._publisher.add(publisher);
    }

    /**
     * @param format the _format to set
     */
    public void addFormat(String format) {
        this._format.add(format);
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
        this._relation.add(relation);
    }

    /**
     * @param source the _source to set
     */
    public void addSource(String source) {
        this._source.add(source);
    }

    /**
     * @param audience the _audience to set
     */
    public void addAudience(String audience) {
        this._audience.add(audience);
    }

    /**
     * @param provenance the _provenance to set
     */
    public void addProvenance(String provenance) {
        this._provenance.add(provenance);
    }

    /**
     * @param rightsHolder the _rightsHolder to set
     */
    public void addRightsHolder(String rightsHolder) {
        this._rightsHolder.add(rightsHolder);
    }

    /**
     * @param alternativetitle the _alternative to set
     */
    public void addAlternative(String lang, String alternative) {
        this._alternative.put(lang, alternative);
    }

    /**
     * @param tableOfContents the _tableOfContents to set
     */
    public void addTableOfContents(String lang, String tableOfContents) {
        this._tableOfContents.put(lang, tableOfContents);
    }

    /**
     * @param abstract the _abstract to set
     */
    public void addAbstract(String lang, String abs) {
        this._abstract.put(lang, abs);
    }

    /**
     * @param created the _created to set
     */
    public void addCreated(String created) {
        this._created.add(created);
    }

    /**
     * @param valid the _valid to set
     */
    public void addValid(String valid) {
        this._valid.add(valid);
    }

    /**
     * @param available the _available to set
     */
    public void addAvailable(String available) {
        this._available.add(available);
    }

    /**
     * @param issued the _issued to set
     */
    public void addIssued(String issued) {
        this._issued.add(issued);
    }

    /**
     * @param modified the _modified to set
     */
    public void addModified(String modified) {
        this._modified.add(modified);
    }

    /**
     * @param dateAccepted the _dateAccepted to set
     */
    public void addDateAccepted(String dateAccepted) {
        this._dateAccepted.add(dateAccepted);
    }

    /**
     * @param dateCopyrighted the _dateCopyrighted to set
     */
    public void addDateCopyrighted(String dateCopyrighted) {
        this._dateCopyrighted.add(dateCopyrighted);
    }

    /**
     * @param dateSubmited the _dateSubmited to set
     */
    public void addDateSubmited(String dateSubmited) {
        this._dateSubmited.add(dateSubmited);
    }

    /**
     * @param extent the _extent to set
     */
    public void addExtent(String extent) {
        this._extent.add(extent);
    }

    /**
     * @param medium the _medium to set
     */
    public void addMedium(String medium) {
        this._medium.add(medium);
    }

    /**
     * @param bibliographicCitation the _bibliographicCitation to set
     */
    public void addBibliographicCitation(String bibliographicCitation) {
        this._bibliographicCitation.add(bibliographicCitation);
    }

    /**
     * @param isVersionOf the _isVersionOf to set
     */
    public void addIsVersionOf(String isVersionOf) {
        this._isVersionOf.add(isVersionOf);
    }

    /**
     * @param hasVersion the _hasVersion to set
     */
    public void addHasVersion(String hasVersion) {
        this._hasVersion.add(hasVersion);
    }

    /**
     * @param isReplacedBy the _isReplacedBy to set
     */
    public void addIsReplacedBy(String isReplacedBy) {
        this._isReplacedBy.add(isReplacedBy);
    }

    /**
     * @param replaces the _replaces to set
     */
    public void addReplaces(String replaces) {
        this._replaces.add(replaces);
    }

    /**
     * @param isRequiredBy the _isRequiredBy to set
     */
    public void addIsRequiredBy(String isRequiredBy) {
        this._isRequiredBy.add(isRequiredBy);
    }

    /**
     * @param requires the _requires to set
     */
    public void addRequires(String requires) {
        this._requires.add(requires);
    }

    /**
     * @param isPartOf the _isPartOf to set
     */
    public void addIsPartOf(String isPartOf) {
        this._isPartOf.add(isPartOf);
    }

    /**
     * @param hasPart the _hasPart to set
     */
    public void addHasPart(String hasPart) {
        this._hasPart.add(hasPart);
    }

    /**
     * @param isReferencedBy the _isReferencedBy to set
     */
    public void addIsReferencedBy(String isReferencedBy) {
        this._isReferencedBy.add(isReferencedBy);
    }

    /**
     * @param references the _references to set
     */
    public void addReferences(String references) {
        this._references.add(references);
    }

    /**
     * @param isFormatOf the _isFormatOf to set
     */
    public void addIsFormatOf(String isFormatOf) {
        this._isFormatOf.add(isFormatOf);
    }

    /**
     * @param hasFormat the _hasFormat to set
     */
    public void addHasFormat(String hasFormat) {
        this._hasFormat.add(hasFormat);
    }

    /**
     * @param conformsTo the _conformsTo to set
     */
    public void addConformsTo(String conformsTo) {
        this._conformsTo.add(conformsTo);
    }

    /**
     * @param spatial the _spatial to set
     */
    public void addSpatial(String spatial) {
        this._spatial.add(spatial);
    }

    /**
     * @param temporal the _temporal to set
     */
    public void addTemporal(String temporal) {
        this._temporal.add(temporal);
    }

    /**
     * @param accessRights the _accessRights to set
     */
    public void addAccessRights(String lang, String accessRights) {
        this._accessRights.put(lang, accessRights);
    }

    /**
     * @param license the _license to set
     */
    public void addLicense(String lang, String license) {
        this._license.put(lang, license);
    }

    /**
     * @param mediator the _mediator to set
     */
    public void addMediator(String mediator) {
        this._mediator.add(mediator);
    }

    /**
     * @param educationLevel the _educationLevel to set
     */
    public void addEducationLevel(String educationLevel) {
        this._educationLevel.add(educationLevel);
    }
}
