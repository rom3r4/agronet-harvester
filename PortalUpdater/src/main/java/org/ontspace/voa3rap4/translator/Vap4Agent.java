package org.ontspace.voa3rap4.translator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.Namespace;

public class Vap4Agent {
  
  private Element dcdsDescription;
  
  private static Namespace dcdsNS = Namespace.getNamespace("dcds", "http://purl.org/dc/xmlns/2008/09/01/dc-ds-xml/");
  private static Namespace xmlNS = Namespace.getNamespace("xml", "http://www.w3.org/XML/1998/namespace");
  
  private String name="", firstName="", lastName="", mbox="", type="";
  
  public Vap4Agent(Element dcdsDescription) {
    this.dcdsDescription = dcdsDescription;
  }

  public void parse() {
    List<Element> statements = dcdsDescription.getChildren("statement", dcdsNS);
    System.out.println("Agent ID: "+ dcdsDescription.getAttributeValue("resourceId", dcdsNS));
    for (Element statement : statements) {
      String propURIstr = statement.getAttributeValue("propertyURI", dcdsNS);
      try {
        URI propURI = new URI(propURIstr);
        String[] segments = propURI.getPath().split("/");
        String propName = segments[segments.length - 1];
        
        if (propName.equals("name")) {

          setName(statement.getValue().trim());
          
        } else if (propName.equals("firstName") || propName.equals("givenName")) {
          
          setFirstName(statement.getValue().trim());
          
        } else if (propName.equals("lastName") || propName.equals("familyName")) {
          
          setLastName(statement.getValue().trim());
          
        } else if (propName.equals("mbox")) {
          
          String valURIstr = statement.getAttributeValue("valueURI", dcdsNS);
          URI valURI = new URI(valURIstr);
          setMbox(valURIstr);
          
        } else if (propName.equals("type")) {
          
          String valURIstr = statement.getAttributeValue("valueURI", dcdsNS);
          URI valURI = new URI(valURIstr);
          setType(valURIstr);
          
        } else {
          Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid property found in Agent: " + propName);
        }
        
      } catch (URISyntaxException ex) {
        Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid uri", ex);
      } catch (NullPointerException npe) {
          Logger.getLogger(Vap4Agent.class.getName()).log(Level.SEVERE, "Invalid uri", npe);
      }
    }
    
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMbox() {
    return mbox;
  }

  public void setMbox(String mbox) {
    this.mbox = mbox;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
  public String getFullName() {
      if (!this.firstName.isEmpty() && !this.lastName.isEmpty()) {
          return this.lastName + ", " + this.firstName;
      } else if (! this.name.isEmpty()) {
          return this.name;
      } else if (! this.lastName.isEmpty()) {
          return this.lastName;
      }
      return this.firstName;
  }
  
  @Override
  public String toString() {
    return "Vap4Agent{" + "name=" + name + ", firstName=" + firstName + ", lastName=" + lastName + ", mbox=" + mbox + ", type=" + type + '}';
  }
  
  
}