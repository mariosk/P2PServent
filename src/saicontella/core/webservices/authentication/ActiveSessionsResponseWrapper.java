/**
 * ActiveSessionsResponseWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class ActiveSessionsResponseWrapper  extends saicontella.core.webservices.authentication.BaseResponse  implements java.io.Serializable {
    private saicontella.core.webservices.authentication.ActiveSessionMiniWrapper[] activeSessions;

    private java.lang.String activeSessionsEncoded;

    public ActiveSessionsResponseWrapper() {
    }

    public ActiveSessionsResponseWrapper(
           java.lang.String errorMessage,
           saicontella.core.webservices.authentication.ResponseSTATUS status,
           saicontella.core.webservices.authentication.ActiveSessionMiniWrapper[] activeSessions,
           java.lang.String activeSessionsEncoded) {
        super(
            errorMessage,
            status);
        this.activeSessions = activeSessions;
        this.activeSessionsEncoded = activeSessionsEncoded;
    }


    /**
     * Gets the activeSessions value for this ActiveSessionsResponseWrapper.
     * 
     * @return activeSessions
     */
    public saicontella.core.webservices.authentication.ActiveSessionMiniWrapper[] getActiveSessions() {
        return activeSessions;
    }


    /**
     * Sets the activeSessions value for this ActiveSessionsResponseWrapper.
     * 
     * @param activeSessions
     */
    public void setActiveSessions(saicontella.core.webservices.authentication.ActiveSessionMiniWrapper[] activeSessions) {
        this.activeSessions = activeSessions;
    }

    public saicontella.core.webservices.authentication.ActiveSessionMiniWrapper getActiveSessions(int i) {
        return this.activeSessions[i];
    }

    public void setActiveSessions(int i, saicontella.core.webservices.authentication.ActiveSessionMiniWrapper _value) {
        this.activeSessions[i] = _value;
    }


    /**
     * Gets the activeSessionsEncoded value for this ActiveSessionsResponseWrapper.
     * 
     * @return activeSessionsEncoded
     */
    public java.lang.String getActiveSessionsEncoded() {
        return activeSessionsEncoded;
    }


    /**
     * Sets the activeSessionsEncoded value for this ActiveSessionsResponseWrapper.
     * 
     * @param activeSessionsEncoded
     */
    public void setActiveSessionsEncoded(java.lang.String activeSessionsEncoded) {
        this.activeSessionsEncoded = activeSessionsEncoded;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ActiveSessionsResponseWrapper)) return false;
        ActiveSessionsResponseWrapper other = (ActiveSessionsResponseWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.activeSessions==null && other.getActiveSessions()==null) || 
             (this.activeSessions!=null &&
              java.util.Arrays.equals(this.activeSessions, other.getActiveSessions()))) &&
            ((this.activeSessionsEncoded==null && other.getActiveSessionsEncoded()==null) || 
             (this.activeSessionsEncoded!=null &&
              this.activeSessionsEncoded.equals(other.getActiveSessionsEncoded())));
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
        if (getActiveSessions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getActiveSessions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getActiveSessions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getActiveSessionsEncoded() != null) {
            _hashCode += getActiveSessionsEncoded().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ActiveSessionsResponseWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "activeSessionsResponseWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeSessions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeSessions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "activeSessionMiniWrapper"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeSessionsEncoded");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeSessionsEncoded"));
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
