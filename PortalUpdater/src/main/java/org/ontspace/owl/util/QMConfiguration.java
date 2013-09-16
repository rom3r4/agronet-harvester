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
package org.ontspace.owl.util;

import java.util.ArrayList;

/**
 * This class stores the information about the Query Manager configuration
 */
public class QMConfiguration {

    public enum QMType {

        NAVIGATION, RESOURCE, DC, QDC, AGRIS, MODS, VOA3RAP2, VOA3RAP4
    }
    private QMType _type;
    private String _javaClass;
    private ArrayList<String> _ontologyUris;
    private String _confFilePath;

    public QMConfiguration() {
    }

    /**
     * Creates a new QMConfiguration
     * @param type Type for the query manager
     * @param javaClass Java class for the query manager
     * @param ontologyUris Ontologies' uri for the query manager
     */
    public QMConfiguration(QMConfiguration.QMType type, String javaClass,
        ArrayList<String> ontologyUris) {
        this._type = type;
        this._javaClass = javaClass;
        this._ontologyUris = ontologyUris;
    }

    /**
     * Gets the type for the query manager
     * @return Type for the query manager
     */
    public QMType getType() {
        return this._type;
    }

    /**
     * Gets the Java class for the query manager
     * @return Java class for the query manager
     */
    public String getJavaClass() {
        return this._javaClass;
    }

    /**
     * Gets the ontologies' uri for the query manager
     * @return Ontologies' uri for the query manager
     */
    public ArrayList<String> getOntologyUris() {
        return this._ontologyUris;
    }

    /**
     * Sets the configuration file path for the query manager
     * @param confFilePath Configuration file path for the query manager
     */
    public void setConfFilePath(String confFilePath){
        this._confFilePath = confFilePath;
    }

    /**
     * Gets the configuration file path for the query manager
     * @return Configuration file path for the query manager
     */
    public String getConfFilePath(){
        return this._confFilePath;
    }
}
