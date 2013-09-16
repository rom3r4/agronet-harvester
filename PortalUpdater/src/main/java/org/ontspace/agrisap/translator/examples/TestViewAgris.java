/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.agrisap.translator.examples;

import org.ontspace.dc.translator.examples.*;
import org.ontspace.voa3rap2.translator.examples.*;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.ontspace.agrisap.translator.Agrisap;
import org.ontspace.agrovoc.cache.AgrovocCacheHelper;
import org.ontspace.dc.translator.DublinCore;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 *
 * @author link87
 */
public class TestViewAgris {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resource_path = "/tmp/example.xml";
        File f = new File(resource_path);
        Agrisap ag = new Agrisap(f);
        
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            InputStream is = new FileInputStream(f);
            doc = builder.build(is);
            Element root = doc.getRootElement();
            Namespace agsNS = Namespace.getNamespace("ags",
                "http://purl.org/agmes/1.1/");
            List<Element> resources = root.getChildren("resource", agsNS);
            ag.parseAgrisapXML(resources.get(0));
            System.out.println(ag.toString());
        } catch (Exception ex) {}
        
        
        
//        for(String creator : vap2.getCreators()) {
//        VCard vcard = Ezvcard.parse(creator).first();
//        String fullName = vcard.getFormattedName().getValue();
//        String lastName = vcard.getStructuredName().getFamily();
//        System.out.println("FullName: "+fullName);
//        System.out.println("LastName: "+lastName);
//        }
    }
}
