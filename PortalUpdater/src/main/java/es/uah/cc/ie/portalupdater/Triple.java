/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uah.cc.ie.portalupdater;

/**
 *
 * @author ivan
 */
public class Triple {
    private String subject=null;
    private String predicate=null;
    private String uriObject=null;
    private String stringObject=null;
    private String dateObject=null;
    private String objectFormat=null;
      
    
    

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the predicate
     */
    public String getPredicate() {
        return predicate;
    }

   

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param predicate the predicate to set
     */
    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

   
    
   

    /**
     * @return the uriObject
     */
    public String getUriObject() {
        return uriObject;
    }

    /**
     * @param uriObject the uriObject to set
     */
    public void setUriObject(String uriObject) {
        this.uriObject = uriObject;
    }

    /**
     * @return the dateObject
     */
    public String getDateObject() {
        return dateObject;
    }

    /**
     * @param dateObject the dateObject to set
     */
    public void setDateObject(String dateObject) {
        this.dateObject = dateObject;
    }

    /**
     * @return the stringObject
     */
    public String getStringObject() {
        return stringObject;
    }

    /**
     * @param stringObject the stringObject to set
     */
    public void setStringObject(String stringObject) {
        this.stringObject = stringObject;
    }

    
    
    
    @Override
    public String toString(){        
        StringBuilder sb=new StringBuilder("[" + this.getSubject()+"---"+this.getPredicate()+"---");
        sb.append(this.getDateObject()).append(this.getUriObject()).append(this.getStringObject());
        sb.append(" (").append(this.getObjectFormat()).append(")");
        return sb.toString();
    }

    /**
     * @return the objectFormat
     */
    public String getObjectFormat() {
        return objectFormat;
    }

    /**
     * @param objectFormat the objectFormat to set
     */
    public void setObjectFormat(String objectFormat) {
        this.objectFormat = objectFormat;
    }
}
