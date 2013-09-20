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
package org.ontspace.agrovoc.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Service;
import java.io.StringReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * Invoker for the Agrovoc web service
 *
 */
public class WSinvoker {

    public static String getTermInfo(String URI) throws Exception {
	return "";
   }

    /**
     * Gets the information for a given term in the Agrovoc service
     * @param URI URI for the term which information will be fetched
     * @return Sanitized XML representation for the information on the term
     * @throws Exception Any exception should be handled in an interface layer
     */
     /*
    public static String getTermInfo(String URI) throws Exception {
        org.fao.acsw.webservice.ACSWWebServiceService service =
                new org.fao.acsw.webservice.ACSWWebServiceService();

        QName portQName = new QName("http://webservice.acsw.fao.org",
                "ACSWWebService");
        String req = "<getConceptInfoByURI  "
                + "xmlns=\"http://webservice.acsw.fao.org\">"
                + "<ontologyName>Agrovoc</ontologyName>"
                + "<conceptURI>" + URI + "</conceptURI>"
                + "<format>SKOS</format>"
                + "</getConceptInfoByURI>";

        Dispatch<Source> sourceDispatch = null;
        sourceDispatch = service.createDispatch(portQName, Source.class, Service.Mode.PAYLOAD);
        Source result = sourceDispatch.invoke(new StreamSource(new StringReader(req)));


        // Get XML response
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult streamResult = new StreamResult(baos);
        TransformerFactory.newInstance().newTransformer().
                transform(result, streamResult);

        // Translate XML response
        XMLInputFactory theInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = theInputFactory.createXMLEventReader(
                new ByteArrayInputStream(baos.toString().getBytes()));
        String retValue = null;
        while (reader.hasNext()) {
            XMLEvent ev = reader.nextEvent();
            if (ev.isCharacters()) {
                retValue = ev.asCharacters().getData();
            }
        }

        return retValue;
    }
    */
}
