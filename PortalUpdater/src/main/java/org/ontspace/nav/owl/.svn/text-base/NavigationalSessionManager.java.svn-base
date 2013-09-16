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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.ontspace.MetadataRepository;
import org.ontspace.nav.NavigationalSession;

/**
 * Implementation for a Navigational Session Manager
 *
 */
public class NavigationalSessionManager {

    /** Structure containing the current sessions */
    private static HashMap<String, NavigationalSession> _sessions =
            new HashMap<String, NavigationalSession>();

    /**
     * Adds a Navigational Session to the NavigationalSessionManager
     * @param session Navigational Session to add
     */
    public static void addSession(NavigationalSession session) {
        _sessions.put(session.getSessionIdentifier(), session);
    }

    /**
     * Returns the Navigational Session for the specified identifier
     * @param identifier Identifier for the required Navigational Session
     * @return Navigational Session for the specified identifier
     */
    public static NavigationalSession getSession(String identifier) {
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
     * Removes a Navigational Session from the NavigationalSessionManager
     * @param identifier Identifier for the Navigational Session to remove
     */
    public static void removeSession(String identifier) {
        if (_sessions.containsKey(identifier)) {
            _sessions.get(identifier).close();
        }
        _sessions.remove(identifier);
    }

    /**
     * Creates a new Navigational Session
     * @param rep Repository
     * @param identifier Session identifier for the navigational manager
     * @return new NavigationalSession
     */
    public static NavigationalSession createSession(MetadataRepository rep,
            String identifier) {
        NavigationalSessionImpl sess =
                new NavigationalSessionImpl(rep, identifier);
        addSession(sess);
        return sess;
    }

    /**
     * Returns the number of active sessions in the NavigationalSessionManager
     * @return Number of active sessions in the NavigationalSessionManager
     */
    public static int getSize() {
        return _sessions.size();
    }
}
