/**
 * UserInfoWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class UserInfoWrapper  implements java.io.Serializable {
    private float availableCredits;

    private java.util.Calendar birthDate;

    private java.lang.String countryId;

    private java.util.Calendar dateAdded;

    private java.lang.String extUserId;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private java.util.Calendar lastPurchase;

    private java.lang.String password;

    private java.lang.String userId;

    private java.lang.String userName;

    public UserInfoWrapper() {
    }

    public UserInfoWrapper(
           float availableCredits,
           java.util.Calendar birthDate,
           java.lang.String countryId,
           java.util.Calendar dateAdded,
           java.lang.String extUserId,
           java.lang.String firstName,
           java.lang.String lastName,
           java.util.Calendar lastPurchase,
           java.lang.String password,
           java.lang.String userId,
           java.lang.String userName) {
           this.availableCredits = availableCredits;
           this.birthDate = birthDate;
           this.countryId = countryId;
           this.dateAdded = dateAdded;
           this.extUserId = extUserId;
           this.firstName = firstName;
           this.lastName = lastName;
           this.lastPurchase = lastPurchase;
           this.password = password;
           this.userId = userId;
           this.userName = userName;
    }


    /**
     * Gets the availableCredits value for this UserInfoWrapper.
     * 
     * @return availableCredits
     */
    public float getAvailableCredits() {
        return availableCredits;
    }


    /**
     * Sets the availableCredits value for this UserInfoWrapper.
     * 
     * @param availableCredits
     */
    public void setAvailableCredits(float availableCredits) {
        this.availableCredits = availableCredits;
    }


    /**
     * Gets the birthDate value for this UserInfoWrapper.
     * 
     * @return birthDate
     */
    public java.util.Calendar getBirthDate() {
        return birthDate;
    }


    /**
     * Sets the birthDate value for this UserInfoWrapper.
     * 
     * @param birthDate
     */
    public void setBirthDate(java.util.Calendar birthDate) {
        this.birthDate = birthDate;
    }


    /**
     * Gets the countryId value for this UserInfoWrapper.
     * 
     * @return countryId
     */
    public java.lang.String getCountryId() {
        return countryId;
    }


    /**
     * Sets the countryId value for this UserInfoWrapper.
     * 
     * @param countryId
     */
    public void setCountryId(java.lang.String countryId) {
        this.countryId = countryId;
    }


    /**
     * Gets the dateAdded value for this UserInfoWrapper.
     * 
     * @return dateAdded
     */
    public java.util.Calendar getDateAdded() {
        return dateAdded;
    }


    /**
     * Sets the dateAdded value for this UserInfoWrapper.
     * 
     * @param dateAdded
     */
    public void setDateAdded(java.util.Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }


    /**
     * Gets the extUserId value for this UserInfoWrapper.
     * 
     * @return extUserId
     */
    public java.lang.String getExtUserId() {
        return extUserId;
    }


    /**
     * Sets the extUserId value for this UserInfoWrapper.
     * 
     * @param extUserId
     */
    public void setExtUserId(java.lang.String extUserId) {
        this.extUserId = extUserId;
    }


    /**
     * Gets the firstName value for this UserInfoWrapper.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this UserInfoWrapper.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this UserInfoWrapper.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this UserInfoWrapper.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the lastPurchase value for this UserInfoWrapper.
     * 
     * @return lastPurchase
     */
    public java.util.Calendar getLastPurchase() {
        return lastPurchase;
    }


    /**
     * Sets the lastPurchase value for this UserInfoWrapper.
     * 
     * @param lastPurchase
     */
    public void setLastPurchase(java.util.Calendar lastPurchase) {
        this.lastPurchase = lastPurchase;
    }


    /**
     * Gets the password value for this UserInfoWrapper.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this UserInfoWrapper.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the userId value for this UserInfoWrapper.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this UserInfoWrapper.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the userName value for this UserInfoWrapper.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this UserInfoWrapper.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserInfoWrapper)) return false;
        UserInfoWrapper other = (UserInfoWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.availableCredits == other.getAvailableCredits() &&
            ((this.birthDate==null && other.getBirthDate()==null) || 
             (this.birthDate!=null &&
              this.birthDate.equals(other.getBirthDate()))) &&
            ((this.countryId==null && other.getCountryId()==null) || 
             (this.countryId!=null &&
              this.countryId.equals(other.getCountryId()))) &&
            ((this.dateAdded==null && other.getDateAdded()==null) || 
             (this.dateAdded!=null &&
              this.dateAdded.equals(other.getDateAdded()))) &&
            ((this.extUserId==null && other.getExtUserId()==null) || 
             (this.extUserId!=null &&
              this.extUserId.equals(other.getExtUserId()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.lastPurchase==null && other.getLastPurchase()==null) || 
             (this.lastPurchase!=null &&
              this.lastPurchase.equals(other.getLastPurchase()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
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
        _hashCode += new Float(getAvailableCredits()).hashCode();
        if (getBirthDate() != null) {
            _hashCode += getBirthDate().hashCode();
        }
        if (getCountryId() != null) {
            _hashCode += getCountryId().hashCode();
        }
        if (getDateAdded() != null) {
            _hashCode += getDateAdded().hashCode();
        }
        if (getExtUserId() != null) {
            _hashCode += getExtUserId().hashCode();
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
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserInfoWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "userInfoWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableCredits");
        elemField.setXmlName(new javax.xml.namespace.QName("", "availableCredits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "birthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("dateAdded");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateAdded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extUserId"));
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
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
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
