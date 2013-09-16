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
package org.ontspace.agrisap.translator;

import org.jdom.Element;

/**
 *
 * @author raquel
 */
public class Relation2Relation {

    public Relation2Relation(Element child, Agrisap agris) {
        String metadata = child.getName();

        //13-Relation
        if (metadata.compareTo("relation") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setRelation(value);
            }
        } else //13.1-is version of
        if (metadata.compareTo("isVersionOf") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsVersionOf(value);
            }
        } else //13.2-has version
        if (metadata.compareTo("hasVersion") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setHasVersion(value);
            }
        } else //13.3-is replaced by
        if (metadata.compareTo("isReplacedBy") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsReplacedBy(value);
            }
        } else //13.4-Replaces
        if (metadata.compareTo("replaces") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setReplaces(value);
            }
        } else //13.5-is required by
        if (metadata.compareTo("isRequiredBy") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsRequiredBy(value);
            }
        } else //13.6-requires
        if (metadata.compareTo("requires") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setRequires(value);
            }
        } else //13.7-is part of
        if (metadata.compareTo("isPartOf") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsPartOf(value);
            }
        } else //13.8-has part
        if (metadata.compareTo("hasPart") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setHasPart(value);
            }
        } else //13.9-is referenced by
        if (metadata.compareTo("isReferenceBy") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsReferencedBy(value);
            }
        } else //13.10-references
        if (metadata.compareTo("references") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setReferences(value);
            }
        } else //13.11-is format of
        if (metadata.compareTo("isFormatOf") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsFormatOf(value);
            }
        } else //13.12-has format
        if (metadata.compareTo("hasFormat") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setHasFormat(value);
            }
        } else if (metadata.compareTo("isTranslationOf") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setIsTranslationOf(value);
            }
        } else if (metadata.compareTo("hasTranslation") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setHasTranslation(value);
            }
        }

    }
}
