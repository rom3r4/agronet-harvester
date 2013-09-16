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
package org.ontspace.nav.owl;

import org.ontspace.MetadataRepository;
import org.ontspace.QueryManager;
import org.ontspace.nav.NavigationalQueryManager;
import org.ontspace.nav.NavigationalSession;

/**
 * Implementation for a Navigational Session
 * 
 */
public class NavigationalSessionImpl implements NavigationalSession {

    /** MetadataRepository where the ontologies are stored */
    private MetadataRepository _rep;
    /** Associated NavigationalQueryManager */
    private NavigationalQueryManagerImpl _qm;
    /** Session identifier for the navigational process */
    private String _identifier;

    /**
     * Creates a new instance of NavigationalSessionManagerImpl
     * @param rep Repository
     * @param identifier Session identifier for the navigational manager
     */
    public NavigationalSessionImpl(MetadataRepository rep, String identifier) {
        this._rep = rep;
        this._identifier = identifier;
    }

    /**
     * Returns the Session Identifier for the current Navigational Session
     * @return Session Identifier for the current Navigational Session
     */
    @Override
    public String getSessionIdentifier() {
        return this._identifier;
    }

    /**
     * Binds the Navigational Session to its associated Query Manager
     * @param qm Query Manager for the current navigation
     */
    public void bindToQueryManager(QueryManager qm) {
        this._qm = (NavigationalQueryManagerImpl) qm;
    }

    /**
     * Gets the NavigationalQueryManager associated to the Navigational Session
     * @return NavigationalQueryManager associated to the Navigational Session
     */
    public NavigationalQueryManager getQueryManager() {
        return this._qm;
    }

    /**
     * Close the session and free up resources held
     */
    @Override
    public void close() {
        this._qm = null;
        this._rep.close();
        this._rep = null;
    }
}
