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
package org.ontspace.resource.owl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 */
public class Resource {

    private String _title = new String();
    private String _language = new String();
    private String _subject = new String();
    private String _type = new String();

    public Resource() {
    }

    /**
     * @return the _title
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title the _title to set
     */
    public void setTitle(String title) {
        this._title = title;
    }

    /**
     * @return the _language
     */
    public String getLanguage() {
        return _language;
    }

    /**
     * @param language the _language to set
     */
    public void setLanguage(String language) {
        this._language = language;
    }

    /**
     * @return the _subject
     */
    public String getSubject() {
        return _subject;
    }

    /**
     * @param subject the _subject to set
     */
    public void setSubject(String subject) {
        this._subject = subject;
    }

    /**
     * @return the _type
     */
    public String getType() {
        return _type;
    }

    /**
     * @param type the _type to set
     */
    public void setType(String type) {
        this._type = type;
    }

    /**
     * This method receives a DC XML FILE and creates a DC Object that
     * stores its content.
     * @param is
     * @return
     */
    public Resource parseDCXML(final InputStream is) {
        Resource resourceMetadata = new Resource();
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(is);
        } catch (JDOMException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null,
                    ex);
        } catch (IOException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        Element root = doc.getRootElement();

        List<Element> children = root.getChildren();
        for (Element child : children) {
            String childrenName = child.getName();

            if (childrenName.compareTo("title") == 0) {
                this._title = child.getTextTrim();
            }
            if (childrenName.compareTo("language") == 0) {
                this._language = child.getTextTrim();
            }
            if (childrenName.compareTo("subject") == 0) {
                this._subject = child.getTextTrim();
            }
            if (childrenName.compareTo("type") == 0) {
                this._type = child.getTextTrim();
            }
        }
        return resourceMetadata;
    }
}
