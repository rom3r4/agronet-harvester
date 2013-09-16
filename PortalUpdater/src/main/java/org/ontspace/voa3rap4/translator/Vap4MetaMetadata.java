package org.ontspace.voa3rap4.translator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.Namespace;

public class Vap4MetaMetadata {

  private Element mmDescription;
  private Voa3rAP4 parent;
  private static Namespace dcdsNS = Namespace.getNamespace("dcds", "http://purl.org/dc/xmlns/2008/09/01/dc-ds-xml/");
  private static Namespace xmlNS = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");
  private String type;
  private String date;
  private ArrayList<String> identifier = new ArrayList<String>();
  private ArrayList contributor = new ArrayList();

  public Vap4MetaMetadata(Element mmDescription, Voa3rAP4 parent) {
    this.mmDescription = mmDescription;
    this.parent = parent;
  }

  public void parse() {
    List<Element> statements = mmDescription.getChildren("statement", dcdsNS);
    System.out.println("MetaMetadata ID: " + mmDescription.getAttributeValue("resourceId", dcdsNS));
    for (Element statement : statements) {
      String propURIstr = statement.getAttributeValue("propertyURI", dcdsNS);
      try {
        URI propURI = new URI(propURIstr);
        String[] segments = propURI.getPath().split("/");
        String propName = segments[segments.length - 1];

        if (propName.equals("type")) {

          String t = Voa3rAP4.getLiteral(statement);
          if (!t.isEmpty()) {
            setType(t);
          }

        } else if (propName.equals("date")) {

          String d = Voa3rAP4.getLiteral(statement);
          if (!d.isEmpty()) {
            setDate(d);
          }

        } else if (propName.equals("identifier")) {

          String valURI = Voa3rAP4.getValueURI(statement);
          if (valURI != null) {
            addIdentifier(valURI);
          } else {
            String id = Voa3rAP4.getLiteral(statement);
            if (!id.isEmpty()) {
              addIdentifier(id);
            }
          }

        } else if (propName.equals("contributor")) {

          Vap4Agent agent = parent.getAgent(statement);
          if (agent != null) {
            addContributor(agent);
          } else {
            String valURI = Voa3rAP4.getValueURI(statement);
            if (valURI != null && ! valURI.isEmpty()) {
              addContributor(valURI);
            }
          }

        } else {
          Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid property found in Agent: " + propName);
        }

      } catch (URISyntaxException ex) {
        Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid uri", ex);
      }
    }
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public ArrayList<String> getIdentifier() {
    return identifier;
  }

  public void addIdentifier(String identifier) {
    this.identifier.add(identifier);
  }

  public ArrayList getContributor() {
    return contributor;
  }

  public void addContributor(Object contributor) {
    this.contributor.add(contributor);
  }

  @Override
  public String toString() {
    return "Vap4MetaMetadata{" + "type=" + type + ", date=" + date + ", identifier=" + identifier + ", contributor=" + contributor + '}';
  }
  
}