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
public class Creator2Creator {

    public Creator2Creator(Element child, Agrisap agris) {
        String metadata = child.getName();

        //2-Creator
        if (metadata.compareTo("creator") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setCreator(value);
            }
        } else if (metadata.compareTo("creatorPersonal") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setCreatorPersonal(value);
            }
        } else if (metadata.compareTo("creatorCorporate") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setCreatorCorporate(value);
            }
        } else if (metadata.compareTo("creatorConference") == 0) {
            String value = child.getTextTrim();
            if (value.length() > 2) {
                agris.setCreatorConference(value);
            }
        }


    }
}
