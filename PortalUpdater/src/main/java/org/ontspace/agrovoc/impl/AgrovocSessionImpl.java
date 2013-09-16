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

import org.ontspace.agrovoc.AgrovocQueryManager;
import org.ontspace.agrovoc.AgrovocSession;

/**
 * Implementation for an Agrovoc Session
 *
 */
public class AgrovocSessionImpl implements AgrovocSession {

    /** Associated AgrovocQueryManagerImpl */
    private AgrovocQueryManagerImpl _qm;
    /** Session identifier for the agrovoc process */
    private String _identifier;

    /**
     * Creates a new instance of AgrovocSessionImpl
     * @param identifier Identifier for the current Agrovoc Session
     */
    public AgrovocSessionImpl(String identifier) {
        this._identifier = identifier;
    }

    /**
     * Returns the Session Identifier for the current Agrovoc Session
     * @return Session Identifier for the current Agrovoc Session
     */
    @Override
    public String getSessionIdentifier() {
        return this._identifier;
    }

    /**
     * Binds the Agrovoc Session to its associated Query Manager
     * @param qm Query Manager for the current agrovoc process
     */
    public void bindToQueryManager(AgrovocQueryManager qm) {
        this._qm = (AgrovocQueryManagerImpl) qm;
    }

    /**
     * Gets the AgrovocQueryManager associated to the Agrovoc Session
     * @return AgrovocQueryManager associated to the Agrovoc Session
     */
    public AgrovocQueryManager getQueryManager() {
        return this._qm;
    }
}
