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
package org.ontspace.agrovoc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Representation of an agrovoc concept
 *
 */
public class AgrovocConcept {

    /** 'About' field for the Agrovoc concept */
    private String _about = new String();
    /** 'prefLabel' elements for the Agrovoc concept */
    private HashMap<String, String> _prefLabels = new HashMap<String, String>();
    /** 'altLabel' elements for the Agrovoc concept */
    private HashMap<String, String> _altLabels = new HashMap<String, String>();
    /** 'scopeNote' elements for the Agrovoc concept */
    private HashMap<String, String> _scopeNotes = new HashMap<String, String>();
    /** 'changeNote' field for the Agrovoc concept */
    private String _changeNote = new String();
    /** 'broader' elements for the Agrovoc concept */
    private List<AgrovocConcept> _broader = new ArrayList<AgrovocConcept>();
    /** 'related' elements in for Agrovoc concept */
    private List<AgrovocConcept> _narrower = new ArrayList<AgrovocConcept>();
    /** 'narrower' elements in for Agrovoc concept */
    private HashMap<String,AgrovocConcept> _related = new HashMap<String,AgrovocConcept>();

    /**
     * Creates a new Agrovoc concept
     */
    public AgrovocConcept() {
        // Nothing to do
    }

    /**
     * Gets the 'about' field for the Agrovoc concept
     * @return 'about' field for the Agrovoc concept
     */
    public String getAbout() {
        return this._about;
    }

    /**
     * Sets the 'about' field for the Agrovoc concept
     * @param about 'about' field for the Agrovoc concept
     */
    public void setAbout(String about) {
        this._about = about;
    }

    /**
     * Gets the 'prefLabel' elements for the Agrovoc concept
     * @return 'prefLabel' elements for the Agrovoc concept
     */
    public HashMap<String, String> getPrefLabels() {
        return this._prefLabels;
    }

    /**
     * Gets the 'prefLabel' value for a given language for the Agrovoc concept
     * @param language Language for the 'prefLabel' value in the Agrovoc concept
     * @return 'prefLabel' value for a given language for the Agrovoc concept
     */
    public String getPrefLabel(String language) {
        return this._prefLabels.get(language);
    }

    /**
     * Sets the 'prefLabel' elements for the Agrovoc concept
     * @param prefLabels 'prefLabel' elements for the Agrovoc concept
     */
    public void setPrefLabels(HashMap<String, String> prefLabels) {
        this._prefLabels = prefLabels;
    }

    /**
     * Gets the 'altLabel' elements for the Agrovoc concept
     * @return 'altLabel' elements for the Agrovoc concept
     */
    public HashMap<String, String> getAltLabels() {
        return this._altLabels;
    }

    /**
     * Gets the 'altLabel' value for a given language for the Agrovoc concept
     * @param language Language for the 'altLabel' value in the Agrovoc concept
     * @return 'altLabel' value for a given language for the Agrovoc concept
     */
    public String getAltLabel(String language) {
        return this._altLabels.get(language);
    }

    /**
     * Sets the 'altLabel' elements for the Agrovoc concept
     * @param altLabels 'altLabel' elements for the Agrovoc concept
     */
    public void setAltLabels(HashMap<String, String> altLabels) {
        this._altLabels = altLabels;
    }

    /**
     * Gets the 'scopeNote' elements for the Agrovoc concept
     * @return 'scopeNote' elements for the Agrovoc concept
     */
    public HashMap<String, String> getScopeNotes() {
        return this._scopeNotes;
    }

    /**
     * Gets the 'scopeNote' value for a given language for the Agrovoc concept
     * @param language Language for the 'scopeNote' value in the Agrovoc concept
     * @return 'scopeNote' value for a given language for the Agrovoc concept
     */
    public String getScopeNote(String language) {
        return this._scopeNotes.get(language);
    }

    /**
     * Gets the 'scopeNote' elements for the Agrovoc concept
     * @param scopeNotes 'scopeNote' elements for the Agrovoc concept
     */
    public void setScopeNotes(HashMap<String, String> scopeNotes) {
        this._scopeNotes = scopeNotes;
    }

    /**
     * Gets the 'changeNote' field for the Agrovoc concept
     * @return 'changeNote' field for the Agrovoc concept
     */
    public String getChangeNote() {
        return this._changeNote;
    }

    /**
     * Sets the 'changeNote' field for the Agrovoc concept
     * @param changeNote 'changeNote' field for the Agrovoc concept
     */
    public void setChangeNote(String changeNote) {
        this._changeNote = changeNote;
    }

    /**
     * Gets the 'broader' elements list for the Agrovoc concept
     * @return 'broader' elements list for the Agrovoc concept
     */
    public List<AgrovocConcept> getBroader() {
        return this._broader;
    }

    /**
     * Sets the 'broader' elements list for the Agrovoc concept
     * @param broader 'broader' elements list for the Agrovoc concept
     */
    public void setBroader(List<AgrovocConcept> broader) {
        this._broader = broader;
    }

    /**
     * Gets the 'narrower' elements list for the Agrovoc concept
     * @return 'narrower' elements list for the Agrovoc concept
     */
    public List<AgrovocConcept> getNarrower() {
        return this._narrower;
    }

    /**
     * Sets the 'narrower' elements list for the Agrovoc concept
     * @param narrower 'narrower' elements list for the Agrovoc concept
     */
    public void setNarrower(List<AgrovocConcept> narrower) {
        this._narrower = narrower;
    }

    /**
     * Gets the 'related' elements for the Agrovoc concept
     * @return 'related' elements for the Agrovoc concept
     */
    public HashMap<String,AgrovocConcept> getRelated() {
        return this._related;
    }

    /**
     * Sets the 'related' elements for the Agrovoc concept
     * @param related 'related' elements for the Agrovoc concept
     */
    public void setRelated(HashMap<String,AgrovocConcept> related) {
        this._related = related;
    }
}
