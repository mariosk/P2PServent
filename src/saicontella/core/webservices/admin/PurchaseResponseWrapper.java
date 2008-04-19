/**
 * PurchaseResponseWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class PurchaseResponseWrapper  extends saicontella.core.webservices.admin.AdminBaseResponse  implements java.io.Serializable {
    private java.lang.String purchaseId;

    public PurchaseResponseWrapper() {
    }

    public PurchaseResponseWrapper(
           java.lang.String errorMessage,
           java.lang.String stackTrace,
           saicontella.core.webservices.admin.ResponseSTATUS status,
           java.lang.String purchaseId) {
        super(
            errorMessage,
            stackTrace,
            status);
        this.purchaseId = purchaseId;
    }


    /**
     * Gets the purchaseId value for this PurchaseResponseWrapper.
     * 
     * @return purchaseId
     */
    public java.lang.String getPurchaseId() {
        return purchaseId;
    }


    /**
     * Sets the purchaseId value for this PurchaseResponseWrapper.
     * 
     * @param purchaseId
     */
    public void setPurchaseId(java.lang.String purchaseId) {
        this.purchaseId = purchaseId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PurchaseResponseWrapper)) return false;
        PurchaseResponseWrapper other = (PurchaseResponseWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.purchaseId==null && other.getPurchaseId()==null) || 
             (this.purchaseId!=null &&
              this.purchaseId.equals(other.getPurchaseId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getPurchaseId() != null) {
            _hashCode += getPurchaseId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PurchaseResponseWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "purchaseResponseWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchaseId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "purchaseId"));
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
