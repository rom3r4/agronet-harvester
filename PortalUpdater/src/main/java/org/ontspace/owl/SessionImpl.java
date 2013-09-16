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
package org.ontspace.owl;

import org.ontspace.Session;

/**
 * Defines sessions bound to an ont-space repository
 * 
 */
public class SessionImpl implements Session {

    private MetadataRepositoryImpl _rep;
    private String _userId;

    /**
     * Creates a new Session
     * @param rep Repository
     */
    public SessionImpl(MetadataRepositoryImpl rep) {
        _rep = rep;
    }

    /**
     * Creates a new session specifying the repository and the user
     * @param rep
     * @param userId
     */
    public SessionImpl(MetadataRepositoryImpl rep, String userId) {
        _rep = rep;
        _userId = userId;
    }

    /**
     * Obtain the repository
     * @return the _rep
     */
    public MetadataRepositoryImpl getRep() {
        return _rep;
    }

    /**
     * Set the repository
     * @param rep the repository to set
     */
    public void setRep(MetadataRepositoryImpl rep) {
        this._rep = rep;
    }

    /**
     * Obtain the user identifier
     * @return the user identifier
     */
    public String getUserId() {
        return _userId;
    }

    /**
     * Set the user identifier
     * @param userId the user identifier to set
     */
    public void setUserId(String userId) {
        this._userId = userId;
    }
}
