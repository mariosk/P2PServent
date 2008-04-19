/**
 * ContentCategoryWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class ContentCategoryWrapper  implements java.io.Serializable {
    private java.lang.String contentCategoryId;

    private java.lang.String contentCategoryName;

    public ContentCategoryWrapper() {
    }

    public ContentCategoryWrapper(
           java.lang.String contentCategoryId,
           java.lang.String contentCategoryName) {
           this.contentCategoryId = contentCategoryId;
           this.contentCategoryName = contentCategoryName;
    }


    /**
     * Gets the contentCategoryId value for this ContentCategoryWrapper.
     * 
     * @return contentCategoryId
     */
    public java.lang.String getContentCategoryId() {
        return contentCategoryId;
    }


    /**
     * Sets the contentCategoryId value for this ContentCategoryWrapper.
     * 
     * @param contentCategoryId
     */
    public void setContentCategoryId(java.lang.String contentCategoryId) {
        this.contentCategoryId = contentCategoryId;
    }


    /**
     * Gets the contentCategoryName value for this ContentCategoryWrapper.
     * 
     * @return contentCategoryName
     */
    public java.lang.String getContentCategoryName() {
        return contentCategoryName;
    }


    /**
     * Sets the contentCategoryName value for this ContentCategoryWrapper.
     * 
     * @param contentCategoryName
     */
    public void setContentCategoryName(java.lang.String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContentCategoryWrapper)) return false;
        ContentCategoryWrapper other = (ContentCategoryWrapper) obj;
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
            ((this.contentCategoryName==null && other.getContentCategoryName()==null) || 
             (this.contentCategoryName!=null &&
              this.contentCategoryName.equals(other.getContentCategoryName())));
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
        if (getContentCategoryName() != null) {
            _hashCode += getContentCategoryName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContentCategoryWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "contentCategoryWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentCategoryId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentCategoryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentCategoryName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contentCategoryName"));
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
