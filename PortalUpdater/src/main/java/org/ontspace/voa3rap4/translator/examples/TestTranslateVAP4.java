/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ontspace.voa3rap4.translator.examples;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ontspace.MetadataRecordReference;
import org.ontspace.voa3rap2.translator.Voa3rAP2;
import org.ontspace.voa3rap2.translator.examples.TranslatingVoa3rAP2FromFolder;
import org.ontspace.voa3rap4.translator.Voa3rAP4;

/**
 *
 * @author abel
 */
public class TestTranslateVAP4 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    //to avoid async writes in output
    System.setErr(System.out);
    
    String contentDir = "/tmp/";

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
    HashMap<String,Voa3rAP4> invalidResources = new HashMap<String, Voa3rAP4>();
    for (int i = 0; i < numRes; i++) {
      String filePath = listXmlFiles.get(i).getAbsolutePath();

      System.out.println("Parsing file " + filePath);
      Voa3rAP4 vap4 = new Voa3rAP4(listXmlFiles.get(i));
      vap4.parseVoa3rAP4XML();
      System.out.println("Parsed\n");
      System.out.println("Result:");
      System.out.println("------------------------------------------------");
      if (! vap4.validate()) {
        System.out.println(vap4.toString());
        System.out.println("INVALID RESOURCE");
        invalidResources.put(filePath, vap4);
      } else {
        numValid ++;
        System.out.println("VALID RESOURCE");
      }
      System.out.println("------------------------------------------------");
    }
    System.out.println("Total num of resources: "+ numRes);
    System.out.println("Num of valid resources: "+ numValid);
    System.out.println("Invalid resources:");
    for (Map.Entry<String, Voa3rAP4> entry : invalidResources.entrySet()) {
      System.out.println(entry.getKey());
//      System.out.println(entry.getValue());
    }
  }
}
