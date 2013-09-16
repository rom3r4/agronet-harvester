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
package org.ontspace.qdc.translator;

import org.jdom.Element;

/**
 *
 * @author raquel
 */
public class Relation2Relation {

    public Relation2Relation(Element child, QualifiedDublinCore qdc) {
        String metadata = child.getName();

        //13-Relation
        if (metadata.compareTo("relation") == 0) {
            String value = child.getTextTrim();
            qdc.addRelation(value);
        }

        //13.1-is version of
        if (metadata.compareTo("isVersionOf") == 0) {
            String value = child.getTextTrim();
            qdc.addIsVersionOf(value);
        }

        //13.2-has version
        if (metadata.compareTo("hasVersion") == 0) {
            String value = child.getTextTrim();
            qdc.addHasVersion(value);
        }

        //13.3-is replaced by
        if (metadata.compareTo("isReplacedBy") == 0) {
            String value = child.getTextTrim();
            qdc.addIsReplacedBy(value);
        }

        //13.4-Replaces
        if (metadata.compareTo("replaces") == 0) {
            String value = child.getTextTrim();
            qdc.addReplaces(value);
        }


        //13.5-is required by
        if (metadata.compareTo("isRequiredBy") == 0) {
            String value = child.getTextTrim();
            qdc.addIsRequiredBy(value);
        }

        //13.6-requires
        if (metadata.compareTo("requires") == 0) {
            String value = child.getTextTrim();
            qdc.addRequires(value);
        }

        //13.7-is part of
        if (metadata.compareTo("isPartOf") == 0) {
            String value = child.getTextTrim();
            qdc.addIsPartOf(value);
        }

        //13.8-has part
        if (metadata.compareTo("hasPart") == 0) {
            String value = child.getTextTrim();
            qdc.addHasPart(value);
        }

        //13.9-is referenced by
        if (metadata.compareTo("isReferencedBy") == 0) {
            String value = child.getTextTrim();
            qdc.addIsReferencedBy(value);
        }

        //13.10-references
        if (metadata.compareTo("references") == 0) {
            String value = child.getTextTrim();
            qdc.addReferences(value);
        }

        //13.11-is format of
        if (metadata.compareTo("isFormatOf") == 0) {
            String value = child.getTextTrim();
            qdc.addIsFormatOf(value);
        }

        //13.12-has format
        if (metadata.compareTo("hasFormat") == 0) {
            String value = child.getTextTrim();
            qdc.addHasFormat(value);
        }

        //13.13-conforms to
        if (metadata.compareTo("conformsTo") == 0) {
            String value = child.getTextTrim();
            qdc.addConformsTo(value);
        }

    }
}
