/**
 * RegisterResponseWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class RegisterResponseWrapper  extends saicontella.core.webservices.admin.AdminBaseResponse  implements java.io.Serializable {
    private java.util.Calendar serverGameInfoLastUpdate;

    private java.lang.String sessionId;

    private java.lang.String userId;

    public RegisterResponseWrapper() {
    }

    public RegisterResponseWrapper(
           java.lang.String errorMessage,
           java.lang.String stackTrace,
           saicontella.core.webservices.admin.ResponseSTATUS status,
           java.util.Calendar serverGameInfoLastUpdate,
           java.lang.String sessionId,
           java.lang.String userId) {
        super(
            errorMessage,
            stackTrace,
            status);
        this.serverGameInfoLastUpdate = serverGameInfoLastUpdate;
        this.sessionId = sessionId;
        this.userId = userId;
    }


    /**
     * Gets the serverGameInfoLastUpdate value for this RegisterResponseWrapper.
     * 
     * @return serverGameInfoLastUpdate
     */
    public java.util.Calendar getServerGameInfoLastUpdate() {
        return serverGameInfoLastUpdate;
    }


    /**
     * Sets the serverGameInfoLastUpdate value for this RegisterResponseWrapper.
     * 
     * @param serverGameInfoLastUpdate
     */
    public void setServerGameInfoLastUpdate(java.util.Calendar serverGameInfoLastUpdate) {
        this.serverGameInfoLastUpdate = serverGameInfoLastUpdate;
    }


    /**
     * Gets the sessionId value for this RegisterResponseWrapper.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this RegisterResponseWrapper.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the userId value for this RegisterResponseWrapper.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this RegisterResponseWrapper.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegisterResponseWrapper)) return false;
        RegisterResponseWrapper other = (RegisterResponseWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.serverGameInfoLastUpdate==null && other.getServerGameInfoLastUpdate()==null) || 
             (this.serverGameInfoLastUpdate!=null &&
              this.serverGameInfoLastUpdate.equals(other.getServerGameInfoLastUpdate()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getServerGameInfoLastUpdate() != null) {
            _hashCode += getServerGameInfoLastUpdate().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterResponseWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "registerResponseWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverGameInfoLastUpdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverGameInfoLastUpdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
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
