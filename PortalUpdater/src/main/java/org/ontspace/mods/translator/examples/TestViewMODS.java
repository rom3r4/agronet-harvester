/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.mods.translator.examples;

import java.io.File;
import org.ontspace.mods.translator.Mods;

/**
 *
 * @author link87
 */
public class TestViewMODS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resource_path = "/home/link87/job/harvesting_affiliation/2013_04_03_12_46/oatao.univ-toulouse.fr_80_METS/defaultSet/oai:oatao.univ-toulouse.fr:138.xml";
        File f = new File(resource_path);
        Mods mods = new Mods(f);
        mods.parseModsXML();
        System.out.println(mods.toString());
        
    }
}
