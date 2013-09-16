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
import java.util.Iterator;
import java.util.List;
import org.ontspace.agrovoc.AgrovocSession;

/**
 * Implementation for an Agrovoc Session Manager
 *
 */
public class AgrovocSessionManager {

    /** Structure containing the current sessions */
    private static HashMap<String, AgrovocSession> _sessions =
            new HashMap<String, AgrovocSession>();

    /**
     * Adds an Agrovoc Session to the AgrovocSessionManager
     * @param session Agrovoc Session to add
     */
    public static void addSession(AgrovocSession session) {
        _sessions.put(session.getSessionIdentifier(), session);
    }

    /**
     * Returns the Agrovoc Session for the specified identifier
     * @param identifier Identifier for the required Agrovoc Session
     * @return Agrovoc Session for the specified identifier
     */
    public static AgrovocSession getSession(String identifier) {
        return _sessions.get(identifier);
    }

    /**
     * Returns a list with the active sessions
     * @return List containing the active sessions
     */
    public static List<String> getActiveSessions() {
        ArrayList<String> activeSessions = new ArrayList<String>();
        for (Iterator iter =
                _sessions.keySet().iterator(); iter.hasNext();) {
            activeSessions.add((String) iter.next());
        }
        return activeSessions;
    }

    /**
     * Removes an Agrovoc Session from the AgrovocSessionManager
     * @param identifier Identifier for the Agrovoc Session to remove
     */
    public static void removeSession(String identifier) {
        _sessions.remove(identifier);
    }

    /**
     * Creates a new Agrovoc Session
     * @param identifier Session identifier for the agrovoc session
     * @return new AgrovocSession
     */
    public static AgrovocSession createSession(String identifier) {
        AgrovocQueryManagerImpl qm = new AgrovocQueryManagerImpl();
        AgrovocSessionImpl sess = new AgrovocSessionImpl(identifier);
        qm.bindToSession(sess);
        sess.bindToQueryManager(qm);
        addSession(sess);
        return sess;
    }

    /**
     * Returns the number of active sessions in the AgrovocSessionManager
     * @return Number of active sessions in the AgrovocSessionManager
     */
    public static int getSize() {
        return _sessions.size();
    }
}
