/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.voa3rap2.translator.examples;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.ontspace.voa3rap2.translator.Voa3rAP2;

/**
 *
 * @author abel
 */
public class TestTranslateVAP2 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    //to avoid async writes in output
    System.setErr(System.out);
    
    String contentDir = "/home/abel/harvesting_07feb2013/2013_02_13_13_25/oai.prodinra.inra.fr_80_AGRES/defaultSet/";

    FileFilter xmlFilter = new FileFilter() {
      @Override
      public boolean accept(File file) {
        return (file.isFile() && file.getName().endsWith(".xml") && ! file.getName().equals("deletedRecords.xml"));
      }
    };
    File dir = new File(contentDir);
    List<File> listXmlFiles = new ArrayList();
    if (dir.isDirectory()) {
      if (!dir.exists()) {
        System.out.println("Error: The Directory doesn't exist");
      }
      File[] files = dir.listFiles(xmlFilter);
      listXmlFiles.addAll(Arrays.asList(files));
    }
    int numRes = listXmlFiles.size();
    int numValid = 0;
    HashMap<String,Voa3rAP2> invalidResources = new HashMap<String,Voa3rAP2>();
    for (int i = 0; i < numRes; i++) {
      String filePath = listXmlFiles.get(i).getAbsolutePath();

      System.out.println("Parsing file " + filePath);
      Voa3rAP2 vap2 = new Voa3rAP2(listXmlFiles.get(i));
      vap2.parseVoa3rAP2XML();
      System.out.println("Parsed\n");
      System.out.println("Result:");
      System.out.println("------------------------------------------------");
      if (! vap2.validate()) {
        System.out.println(vap2.toString());
        System.out.println("INVALID RESOURCE");
        invalidResources.put(filePath, vap2);
      } else {
        numValid ++;
        System.out.println("VALID RESOURCE");
      }
      System.out.println("------------------------------------------------");
    }
    System.out.println("Total num of resources: "+ numRes);
    System.out.println("Num of valid resources: "+ numValid);
    System.out.println("Invalid resources:");
    for (Entry<String, Voa3rAP2> entry : invalidResources.entrySet()) {
      System.out.println(entry.getKey());
      System.out.println(entry.getValue());
    }
  }
}
