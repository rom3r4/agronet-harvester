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
package org.ontspace.nav.owl.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.ontspace.util.Configuration;

/**
 * Navigational Configuration class
 *
 */
public class NavigationalConfiguration implements Configuration {

    /** List of the OntClasses read in the configuration file */
    protected List<String> _OntClasses;
    /** List of the Individuals read in the configuration file */
    protected List<String> _Individuals;
    /** List of the blacklisted terms for properties' range */
    protected List<String> _blacklistedTerms;

    /**
     * Creates a new instance of the NavigationalConfiguration
     * @param configurationFile Path to the configuration file
     * @throws org.jdom.JDOMException
     * @throws java.io.IOException
     */
    public NavigationalConfiguration(String configurationFile)
            throws JDOMException,
            IOException {

        this._OntClasses = new ArrayList<String>();
        this._Individuals = new ArrayList<String>();
        this._blacklistedTerms = new ArrayList<String>();

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(configurationFile));
        Element root = doc.getRootElement();

        Element entryPoint = root.getChild("entryPoint");

        // Gets the OntClasses
        List<Element> ontClasses = entryPoint.getChildren("ontClass");
        for (Element ontClass : ontClasses) {
            String ontClassValue = ontClass.getChild("value").getTextTrim();
            _OntClasses.add(ontClassValue);
        }

        // Gets the Individuals
        List<Element> individuals = entryPoint.getChildren("individual");
        for (Element individual : individuals) {
            String individualValue = individual.getChild("value").getTextTrim();
            _Individuals.add(individualValue);
        }

        Element blacklistedTerms = root.getChild("blacklistedTerms");

        // Gets the blacklisted terms
        List<Element> terms = blacklistedTerms.getChildren("term");
        for (Element term : terms) {
            String termValue = term.getChild("value").getTextTrim();
            _blacklistedTerms.add(termValue);
        }


    }

    /**
     * Gets the OntResources read in the configuration file
     * @return List containing the OntResources read in the configuration file
     */
    public List<String> getOntResources() {
        List<String> temp = new ArrayList<String>();
        temp.addAll(this._OntClasses);
        temp.addAll(this._Individuals);
        return temp;
    }

    /**
     * Gets the number of OntResources read in the configuration file
     * @return Number of OntResources read in the configuration file
     */
    public int getOntResourcesSize() {
        return getOntResources().size();
    }

    /**
     * Gets the OntClasses read in the configuration file
     * @return List containing the OntClasses read in the configuration file
     */
    public List<String> getOntClasses() {
        return this._OntClasses;
    }

    /**
     * Gets the number of OntClasses read in the configuration file
     * @return Number of OntClasses read in the configuration file
     */
    public int getOntClassesSize() {
        return this._OntClasses.size();
    }

    /**
     * Gets the Individuals read in the configuration file
     * @return List containing the Individuals read in the configuration file
     */
    public List<String> getIndividuals() {
        return this._Individuals;
    }

    /**
     * Gets the number of Individuals read in the configuration file
     * @return Number of Individuals read in the configuration file
     */
    public int getIndividualsSize() {
        return this._Individuals.size();
    }

    /**
     * Gets the blacklisted terms read in the configuration file
     * @return List containing the blacklisted terms read in the configuration
     * file
     */
    public List<String> getBlacklistedTerms() {
        return this._blacklistedTerms;
    }

    /**
     * Gets the number of blacklisted terms read in the configuration file
     * @return Number of blacklisted terms read in the configuration file
     */
    public int getBlacklistedTermsSize() {
        return this._blacklistedTerms.size();
    }
}
