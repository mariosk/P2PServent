/**
 * LoginResponseWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class LoginResponseWrapper  extends saicontella.core.webservices.authentication.BaseResponse  implements java.io.Serializable {
    private float availableCredits;

    private java.lang.String clientDownloadLocation;

    private java.lang.String countryId;

    private java.lang.String countryName;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.util.Calendar lastPurchase;

    private java.lang.String latestClientVersion;

    private java.util.Calendar serverGameInfoLastUpdate;

    private java.lang.String sessionId;

    private java.lang.String userId;

    public LoginResponseWrapper() {
    }

    public LoginResponseWrapper(
           java.lang.String errorMessage,
           saicontella.core.webservices.authentication.ResponseSTATUS status,
           float availableCredits,
           java.lang.String clientDownloadLocation,
           java.lang.String countryId,
           java.lang.String countryName,
           java.lang.String firstName,
           java.lang.String lastName,
           java.util.Calendar lastPurchase,
           java.lang.String latestClientVersion,
           java.util.Calendar serverGameInfoLastUpdate,
           java.lang.String sessionId,
           java.lang.String userId) {
        super(
            errorMessage,
            status);
        this.availableCredits = availableCredits;
        this.clientDownloadLocation = clientDownloadLocation;
        this.countryId = countryId;
        this.countryName = countryName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastPurchase = lastPurchase;
        this.latestClientVersion = latestClientVersion;
        this.serverGameInfoLastUpdate = serverGameInfoLastUpdate;
        this.sessionId = sessionId;
        this.userId = userId;
    }


    /**
     * Gets the availableCredits value for this LoginResponseWrapper.
     * 
     * @return availableCredits
     */
    public float getAvailableCredits() {
        return availableCredits;
    }


    /**
     * Sets the availableCredits value for this LoginResponseWrapper.
     * 
     * @param availableCredits
     */
    public void setAvailableCredits(float availableCredits) {
        this.availableCredits = availableCredits;
    }


    /**
     * Gets the clientDownloadLocation value for this LoginResponseWrapper.
     * 
     * @return clientDownloadLocation
     */
    public java.lang.String getClientDownloadLocation() {
        return clientDownloadLocation;
    }


    /**
     * Sets the clientDownloadLocation value for this LoginResponseWrapper.
     * 
     * @param clientDownloadLocation
     */
    public void setClientDownloadLocation(java.lang.String clientDownloadLocation) {
        this.clientDownloadLocation = clientDownloadLocation;
    }


    /**
     * Gets the countryId value for this LoginResponseWrapper.
     * 
     * @return countryId
     */
    public java.lang.String getCountryId() {
        return countryId;
    }


    /**
     * Sets the countryId value for this LoginResponseWrapper.
     * 
     * @param countryId
     */
    public void setCountryId(java.lang.String countryId) {
        this.countryId = countryId;
    }


    /**
     * Gets the countryName value for this LoginResponseWrapper.
     * 
     * @return countryName
     */
    public java.lang.String getCountryName() {
        return countryName;
    }


    /**
     * Sets the countryName value for this LoginResponseWrapper.
     * 
     * @param countryName
     */
    public void setCountryName(java.lang.String countryName) {
        this.countryName = countryName;
    }


    /**
     * Gets the firstName value for this LoginResponseWrapper.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this LoginResponseWrapper.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this LoginResponseWrapper.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this LoginResponseWrapper.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the lastPurchase value for this LoginResponseWrapper.
     * 
     * @return lastPurchase
     */
    public java.util.Calendar getLastPurchase() {
        return lastPurchase;
    }


    /**
     * Sets the lastPurchase value for this LoginResponseWrapper.
     * 
     * @param lastPurchase
     */
    public void setLastPurchase(java.util.Calendar lastPurchase) {
        this.lastPurchase = lastPurchase;
    }


    /**
     * Gets the latestClientVersion value for this LoginResponseWrapper.
     * 
     * @return latestClientVersion
     */
    public java.lang.String getLatestClientVersion() {
        return latestClientVersion;
    }


    /**
     * Sets the latestClientVersion value for this LoginResponseWrapper.
     * 
     * @param latestClientVersion
     */
    public void setLatestClientVersion(java.lang.String latestClientVersion) {
        this.latestClientVersion = latestClientVersion;
    }


    /**
     * Gets the serverGameInfoLastUpdate value for this LoginResponseWrapper.
     * 
     * @return serverGameInfoLastUpdate
     */
    public java.util.Calendar getServerGameInfoLastUpdate() {
        return serverGameInfoLastUpdate;
    }


    /**
     * Sets the serverGameInfoLastUpdate value for this LoginResponseWrapper.
     * 
     * @param serverGameInfoLastUpdate
     */
    public void setServerGameInfoLastUpdate(java.util.Calendar serverGameInfoLastUpdate) {
        this.serverGameInfoLastUpdate = serverGameInfoLastUpdate;
    }


    /**
     * Gets the sessionId value for this LoginResponseWrapper.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this LoginResponseWrapper.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the userId value for this LoginResponseWrapper.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this LoginResponseWrapper.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LoginResponseWrapper)) return false;
        LoginResponseWrapper other = (LoginResponseWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.availableCredits == other.getAvailableCredits() &&
            ((this.clientDownloadLocation==null && other.getClientDownloadLocation()==null) || 
             (this.clientDownloadLocation!=null &&
              this.clientDownloadLocation.equals(other.getClientDownloadLocation()))) &&
            ((this.countryId==null && other.getCountryId()==null) || 
             (this.countryId!=null &&
              this.countryId.equals(other.getCountryId()))) &&
            ((this.countryName==null && other.getCountryName()==null) || 
             (this.countryName!=null &&
              this.countryName.equals(other.getCountryName()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.lastPurchase==null && other.getLastPurchase()==null) || 
             (this.lastPurchase!=null &&
              this.lastPurchase.equals(other.getLastPurchase()))) &&
            ((this.latestClientVersion==null && other.getLatestClientVersion()==null) || 
             (this.latestClientVersion!=null &&
              this.latestClientVersion.equals(other.getLatestClientVersion()))) &&
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
        _hashCode += new Float(getAvailableCredits()).hashCode();
        if (getClientDownloadLocation() != null) {
            _hashCode += getClientDownloadLocation().hashCode();
        }
        if (getCountryId() != null) {
            _hashCode += getCountryId().hashCode();
        }
        if (getCountryName() != null) {
            _hashCode += getCountryName().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getLastPurchase() != null) {
            _hashCode += getLastPurchase().hashCode();
        }
        if (getLatestClientVersion() != null) {
            _hashCode += getLatestClientVersion().hashCode();
        }
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
        new org.apache.axis.description.TypeDesc(LoginResponseWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "loginResponseWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableCredits");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availableCredits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDownloadLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientDownloadLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "countryId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "countryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastPurchase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastPurchase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latestClientVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "latestClientVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
