/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.voa3rap4.translator.examples;

import java.io.File;
import org.ontspace.voa3rap4.translator.Voa3rAP4;

/**
 *
 * @author link87
 */
public class TestViewVAP4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resource_path = "/home/link87/job/VOA3R AP/Complete_Voa3rApL4_Ex_Abel.xml";
        File f = new File(resource_path);
        Voa3rAP4 vap4 = new Voa3rAP4(f);
        vap4.parseVoa3rAP4XML();
        System.out.println(vap4.toString());
    }
}
