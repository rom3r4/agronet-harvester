/*
ont-space - The ontology-based resource metadata repository
Copyright (c) 2006-2011, Information Eng. Research Unit - Univ. of Alcala
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
package es.uah.cc.ie.portalupdater;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author abian
 */
public class FAOMetadataHelper {

    private File _xmlFile = null;
    private HashMap<String, Element> _agsResource = null;
    private Logger _logger = null;

    public FAOMetadataHelper(File xmlFile) {
        _xmlFile = xmlFile;
        _agsResource = new HashMap<String, Element>();
    }

    public FAOMetadataHelper(File xmlFile, Logger fileLogger) {
        this(xmlFile);
        _logger = fileLogger;
    }

    /**
     * This method fulfills an Agris Object that stores the metadata.
     */
    public void parseAgrisapXML() {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(_xmlFile);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            obtainAgsResources(root);
            is.close();
        } catch (JDOMException ex) {
            if (_logger != null) {
                _logger.log(Level.SEVERE, ex.getMessage());
            }
        } catch (IOException ex) {
//          ex.printStackTrace();
            if (_logger != null) {
                _logger.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

    private void obtainAgsResources(Element root) {
        Namespace agsNS = Namespace.getNamespace("ags",
                "http://purl.org/agmes/1.1/");
        List<Element> resources = root.getChildren("resource", agsNS);
        for (Element child : resources) {
            String id = child.getAttributeValue("ARN", agsNS);
            getAgsResource().put(id, child);
            if (_logger != null) {
                List<Element> idList = child.getChildren("identifier");
                if (idList.isEmpty()) {
                    String message = new String();
                    if (_xmlFile != null) {
                        message = _xmlFile.getName() + " ";
                    }
                    message += "FAO resource id " + id + " URL -> not found ";
                    _logger.log(Level.INFO, message);
                }
            }

        }
    }

    /**
     * Obtain all the resources included in the same XML file
     * @return the map with all the agsResource stored in this file
     */
    public HashMap<String, Element> getAgsResource() {
        return _agsResource;
    }
}
