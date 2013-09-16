/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.voa3rap2.translator.examples;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import java.io.File;
import org.ontspace.agrovoc.cache.AgrovocCacheHelper;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 *
 * @author link87
 */
public class TestViewVAP2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resource_path = "/home/link87/job/harvesting_carmona/2013_02_19_10_18/voa3r.dev.acta.asso.fr_80_VOA3R/defaultSet/oai:voa3r.dev.acta.asso.fr:4874.xml";
        File f = new File(resource_path);
        Voa3rAP2 vap2 = new Voa3rAP2(f);
        vap2.parseVoa3rAP2XML();
        System.out.println(vap2.toString());
        AgrovocCacheHelper ah = AgrovocCacheHelper.getInstance();
        System.out.println("agriculture: " + ah.getAgrovocId("agriculture"));
        System.out.println("testing: " + ah.getAgrovocId("testing"));
        
//        for(String creator : vap2.getCreators()) {
//        VCard vcard = Ezvcard.parse(creator).first();
//        String fullName = vcard.getFormattedName().getValue();
//        String lastName = vcard.getStructuredName().getFamily();
//        System.out.println("FullName: "+fullName);
//        System.out.println("LastName: "+lastName);
//        }
    }
}
