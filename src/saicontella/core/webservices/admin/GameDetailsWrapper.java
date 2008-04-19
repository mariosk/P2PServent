/**
 * GameDetailsWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class GameDetailsWrapper  implements java.io.Serializable {
    private java.util.Calendar dateAdded;

    private java.lang.String description;

    private java.lang.String gameId;

    private java.lang.String name;

    public GameDetailsWrapper() {
    }

    public GameDetailsWrapper(
           java.util.Calendar dateAdded,
           java.lang.String description,
           java.lang.String gameId,
           java.lang.String name) {
           this.dateAdded = dateAdded;
           this.description = description;
           this.gameId = gameId;
           this.name = name;
    }


    /**
     * Gets the dateAdded value for this GameDetailsWrapper.
     * 
     * @return dateAdded
     */
    public java.util.Calendar getDateAdded() {
        return dateAdded;
    }


    /**
     * Sets the dateAdded value for this GameDetailsWrapper.
     * 
     * @param dateAdded
     */
    public void setDateAdded(java.util.Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }


    /**
     * Gets the description value for this GameDetailsWrapper.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this GameDetailsWrapper.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the gameId value for this GameDetailsWrapper.
     * 
     * @return gameId
     */
    public java.lang.String getGameId() {
        return gameId;
    }


    /**
     * Sets the gameId value for this GameDetailsWrapper.
     * 
     * @param gameId
     */
    public void setGameId(java.lang.String gameId) {
        this.gameId = gameId;
    }


    /**
     * Gets the name value for this GameDetailsWrapper.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this GameDetailsWrapper.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameDetailsWrapper)) return false;
        GameDetailsWrapper other = (GameDetailsWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateAdded==null && other.getDateAdded()==null) || 
             (this.dateAdded!=null &&
              this.dateAdded.equals(other.getDateAdded()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.gameId==null && other.getGameId()==null) || 
             (this.gameId!=null &&
              this.gameId.equals(other.getGameId()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDateAdded() != null) {
            _hashCode += getDateAdded().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getGameId() != null) {
            _hashCode += getGameId().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameDetailsWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "gameDetailsWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateAdded");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateAdded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
