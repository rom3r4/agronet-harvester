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

import java.net.URI;
import org.ontspace.MetadataRecordReference;

/**
 *
 * Represents a (distributed) reference to a metadata record 
 * stored inside a MetadataRepository. 
 * 
 */
public class MetadataRecordReferenceImpl implements MetadataRecordReference {

    private URI _repositoryURI;
    private String _localId;

    /** Creates a new instance of MetadataRecordReferenceImpl
     * @param rep Repository URI
     * @param id LO id generated randomly (UUID stilye)
     */
    public MetadataRecordReferenceImpl(URI rep, String id) {
        _repositoryURI = rep;
        _localId = id;
    }

    /**
     * Provides the URI to get access to the repository
     * in which the metadata record lives.
     *
     **/
    @Override
    public URI getMRRAccessInterface() {
        return _repositoryURI;
    }

    /**
     * Gets the (locally unique) metadata record identifier.
     */
    @Override
    public String getLocalMetadataRecordId() {
        return _localId;
    }

    /**
     * Set the Learning object metadata record reference access interface
     * @param rep Repository
     */
    public void setLOMRAccessInterface(URI rep) {
        _repositoryURI = rep;
    }

    /**
     * Set the learning obejct identifier
     * @param id identifier
     */
    public void setLocalLearningObjectId(String id) {
        _localId = id;
    }

    /**
     * Gets repository uri plus local learning object identifier
     * @return String with uri plus local learning object identifier
     */
    @Override
    public String toString() {
        return _repositoryURI.toString() + "?id=" + _localId;
    }
}
