/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.dc.translator.examples;

import org.ontspace.voa3rap2.translator.examples.*;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import java.io.File;
import org.ontspace.agrovoc.cache.AgrovocCacheHelper;
import org.ontspace.dc.translator.DublinCore;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 *
 * @author link87
 */
public class TestViewDC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resource_path = "/home/link87/job/harvesting_test/2013_04_11_10_51/traglor.cu.edu.tr_80_DC/defaultSet/oai:traglor.cu.edu.tr:148.xml";
        File f = new File(resource_path);
        DublinCore dc = new DublinCore(f);
        dc.parseDCXML();
        System.out.println(dc.getTitles());
        System.out.println(dc.getDescriptions());
        
//        for(String creator : vap2.getCreators()) {
//        VCard vcard = Ezvcard.parse(creator).first();
//        String fullName = vcard.getFormattedName().getValue();
//        String lastName = vcard.getStructuredName().getFamily();
//        System.out.println("FullName: "+fullName);
//        System.out.println("LastName: "+lastName);
//        }
    }
}
