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

/**
 * This class stores the information about the SDB installation
 */
public class SDBConfiguration {
    /*
    <dbURI>jdbc:mysql://127.0.0.1/ontspace_v2</dbURI>
    <user>root</user>
    <password>tragasables</password>
    <dbType>MySQL</dbType>
    <dbDriver>com.mysql.jdbc.Driver</dbDriver>
     */

    private String _dbURI;
    private String _user;
    private String _password;
    private String _engine;

    /**
     * Creates a new instance of SDBConfiguration
     */
    public SDBConfiguration() {
    }

    /**
     * Creates a new instance of SDBConfiguration specifying the general
     * parameters
     * @param _dbURI Database URI
     * @param _user Database user
     * @param _password Password
     * @param _engine DB engine
     */
    public SDBConfiguration(String _dbURI, String _user, String _password,
        String _engine) {
        this._dbURI = _dbURI;
        this._user = _user;
        this._password = _password;
        this._engine = _engine;

    }

    /**
     * Obtain the database URI
     * @return the database URI
     */
    public String getDbURI() {
        return _dbURI;
    }

    /**
     * Obtain the database user
     * @return the database user
     */
    public String getUser() {
        return _user;
    }

    /**
     * Obtain the password
     * @return the password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Obtain the database engine
     * @return the database engine
     */
    public String getEngine() {
        return _engine;
    }
}
