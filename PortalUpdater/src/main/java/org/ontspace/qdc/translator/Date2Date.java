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
public class Date2Date {

    public Date2Date(Element child, QualifiedDublinCore qdc) {
        String metadata = child.getName();

        //7-Date
        if (metadata.compareTo("date") == 0) {
            String value = child.getTextTrim();
            qdc.addDate(value);
        }

        //7.1-Created
        if (metadata.compareTo("created") == 0) {
            String value = child.getTextTrim();
            qdc.addCreated(value);
        }

        //7.2-Valid
        if (metadata.compareTo("valid") == 0) {
            String value = child.getTextTrim();
            qdc.addValid(value);
        }

        //7.3-available
        if (metadata.compareTo("available") == 0) {
            String value = child.getTextTrim();
            qdc.addAvailable(value);
        }

        //7.4-issued
        if (metadata.compareTo("issued") == 0) {
            String value = child.getTextTrim();
            qdc.addIssued(value);
        }

        //7.5-modified
        if (metadata.compareTo("modified") == 0) {
            String value = child.getTextTrim();
            qdc.addModified(value);
        }

        //7.6-Date Accepted
        if (metadata.compareTo("dateAccepted") == 0) {
            String value = child.getTextTrim();
            qdc.addDateAccepted(value);
        }

        //7.7-Date Copyrighted
        if (metadata.compareTo("dateCopyrighted") == 0) {
            String value = child.getTextTrim();
            qdc.addDateCopyrighted(value);
        }

        //7.8-Date Submited
        if (metadata.compareTo("dateSubmited") == 0) {
            String value = child.getTextTrim();
            qdc.addDateSubmited(value);
        }

    }
}
