package org.ontspace.voa3rap4.translator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.Namespace;

public class Vap4Research {
  
  private Element resDescription;
  
  private static Namespace dcdsNS = Namespace.getNamespace("dcds", "http://purl.org/dc/xmlns/2008/09/01/dc-ds-xml/");
  private static Namespace xmlNS = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");
  
  private ArrayList<String> objectOfInterest = new ArrayList<String>();
  private ArrayList<String> measuresVariable = new ArrayList<String>();
  private ArrayList<String> usesMethod = new ArrayList<String>();
  private ArrayList<String> usesProtocol = new ArrayList<String>();
  private ArrayList<String> usesInstrument = new ArrayList<String>();
  private ArrayList<String> usesTechnique = new ArrayList<String>();

  public Vap4Research(Element resDescription) {
    this.resDescription = resDescription;
  }

  public void parse() {
    List<Element> statements = resDescription.getChildren("statement", dcdsNS);
    System.out.println("Research ID: " + resDescription.getAttributeValue("resourceId", dcdsNS));
    for (Element statement : statements) {
      String propURIstr = statement.getAttributeValue("propertyURI", dcdsNS);
      try {
        URI propURI = new URI(propURIstr);
        String[] segments = propURI.getPath().split("/");
        String propName = segments[segments.length - 1];
        
        String value = getValueURIorValue(statement);
        
        if (! value.isEmpty()) {
          if (propName.equals("objectOfInterest")) {

            addObjectOfInterest(value);

          } else if (propName.equals("measuresVariable")) {

            addMeasuresVariable(value);

          } else if (propName.equals("usesMethod")) {

            addUsesMethod(value);

          } else if (propName.equals("usesProtocol")) {

            addUsesProtocol(value);

          } else if (propName.equals("usesInstrument")) {

            addUsesInstrument(value);

          } else if (propName.equals("usesTechnique")) {

            addUsesTechnique(value);

          } else {
            Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid property found in Agent: " + propName);
          }
        }
      } catch (URISyntaxException ex) {
        Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid uri", ex);
      }
    }
  }

  private String getValueURIorValue(Element statement) {
    String val = Voa3rAP4.getValueURI(statement);
    if (val == null) {
      val = Voa3rAP4.getLiteral(statement);
    }
    return val;
  }
  
  public ArrayList<String> getObjectOfInterest() {
    return objectOfInterest;
  }

  public void addObjectOfInterest(String objectOfInterest) {
    this.objectOfInterest.add(objectOfInterest);
  }

  public ArrayList<String> getMeasuresVariable() {
    return measuresVariable;
  }

  public void addMeasuresVariable(String measuresVariable) {
    this.measuresVariable.add(measuresVariable);
  }

  public ArrayList<String> getUsesMethod() {
    return usesMethod;
  }

  public void addUsesMethod(String usesMethod) {
    this.usesMethod.add(usesMethod);
  }

  public ArrayList<String> getUsesProtocol() {
    return usesProtocol;
  }

  public void addUsesProtocol(String usesProtocol) {
    this.usesProtocol.add(usesProtocol);
  }

  public ArrayList<String> getUsesInstrument() {
    return usesInstrument;
  }

  public void addUsesInstrument(String usesInstrument) {
    this.usesInstrument.add(usesInstrument);
  }

  public ArrayList<String> getUsesTechnique() {
    return usesTechnique;
  }

  public void addUsesTechnique(String usesTechnique) {
    this.usesTechnique.add(usesTechnique);
  }

  @Override
  public String toString() {
    return "Vap4Research{" + "objectOfInterest=" + objectOfInterest + ", measuresVariable=" + measuresVariable + ", usesMethod=" + usesMethod + ", usesProtocol=" + usesProtocol + ", usesInstrument=" + usesInstrument + ", usesTechnique=" + usesTechnique + '}';
  }
  
}