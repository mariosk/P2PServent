/**
 * UserRegistrationsWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class UserRegistrationsWrapper  implements java.io.Serializable {
    private java.lang.String contentCategoryId;

    private java.util.Calendar expirationDate;

    public UserRegistrationsWrapper() {
    }

    public UserRegistrationsWrapper(
           java.lang.String contentCategoryId,
           java.util.Calendar expirationDate) {
           this.contentCategoryId = contentCategoryId;
           this.expirationDate = expirationDate;
    }


    /**
     * Gets the contentCategoryId value for this UserRegistrationsWrapper.
     * 
     * @return contentCategoryId
     */
    public java.lang.String getContentCategoryId() {
        return contentCategoryId;
    }


    /**
     * Sets the contentCategoryId value for this UserRegistrationsWrapper.
     * 
     * @param contentCategoryId
     */
    public void setContentCategoryId(java.lang.String contentCategoryId) {
        this.contentCategoryId = contentCategoryId;
    }


    /**
     * Gets the expirationDate value for this UserRegistrationsWrapper.
     * 
     * @return expirationDate
     */
    public java.util.Calendar getExpirationDate() {
        return expirationDate;
    }


    /**
     * Sets the expirationDate value for this UserRegistrationsWrapper.
     * 
     * @param expirationDate
     */
    public void setExpirationDate(java.util.Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserRegistrationsWrapper)) return false;
        UserRegistrationsWrapper other = (UserRegistrationsWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contentCategoryId==null && other.getContentCategoryId()==null) || 
             (this.contentCategoryId!=null &&
              this.contentCategoryId.equals(other.getContentCategoryId()))) &&
            ((this.expirationDate==null && other.getExpirationDate()==null) || 
             (this.expirationDate!=null &&
              this.expirationDate.equals(other.getExpirationDate())));
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
        if (getContentCategoryId() != null) {
            _hashCode += getContentCategoryId().hashCode();
        }
        if (getExpirationDate() != null) {
            _hashCode += getExpirationDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserRegistrationsWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "userRegistrationsWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentCategoryId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentCategoryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expirationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expirationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
