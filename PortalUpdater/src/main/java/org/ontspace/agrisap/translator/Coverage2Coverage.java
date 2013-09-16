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
public class Coverage2Coverage {

    public Coverage2Coverage(Element child, Agrisap agris) {
        String metadata = child.getName();

        //14-Coverage
        if (metadata.compareTo("coverage") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setCoverage(value);
            }
        } else //14.1-Spatial
        if (metadata.compareTo("spatial") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setSpatial(value);
            }
        } else //14.2-Temporal
        if (metadata.compareTo("temporal") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setTemporal(value);
            }
        }

    }
}
