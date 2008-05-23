/**
 * FriendDetailsWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class FriendDetailsWrapper  implements java.io.Serializable {
    private java.lang.String friendId;

    private java.lang.String friendName;

    private java.lang.Integer friendShipId;

    private saicontella.core.webservices.authentication.FriendshipStatus friendshipStatus;

    private java.lang.Integer status;

    private java.lang.String userId;

    public FriendDetailsWrapper() {
    }

    public FriendDetailsWrapper(
           java.lang.String friendId,
           java.lang.String friendName,
           java.lang.Integer friendShipId,
           saicontella.core.webservices.authentication.FriendshipStatus friendshipStatus,
           java.lang.Integer status,
           java.lang.String userId) {
           this.friendId = friendId;
           this.friendName = friendName;
           this.friendShipId = friendShipId;
           this.friendshipStatus = friendshipStatus;
           this.status = status;
           this.userId = userId;
    }


    /**
     * Gets the friendId value for this FriendDetailsWrapper.
     * 
     * @return friendId
     */
    public java.lang.String getFriendId() {
        return friendId;
    }


    /**
     * Sets the friendId value for this FriendDetailsWrapper.
     * 
     * @param friendId
     */
    public void setFriendId(java.lang.String friendId) {
        this.friendId = friendId;
    }


    /**
     * Gets the friendName value for this FriendDetailsWrapper.
     * 
     * @return friendName
     */
    public java.lang.String getFriendName() {
        return friendName;
    }


    /**
     * Sets the friendName value for this FriendDetailsWrapper.
     * 
     * @param friendName
     */
    public void setFriendName(java.lang.String friendName) {
        this.friendName = friendName;
    }


    /**
     * Gets the friendShipId value for this FriendDetailsWrapper.
     * 
     * @return friendShipId
     */
    public java.lang.Integer getFriendShipId() {
        return friendShipId;
    }


    /**
     * Sets the friendShipId value for this FriendDetailsWrapper.
     * 
     * @param friendShipId
     */
    public void setFriendShipId(java.lang.Integer friendShipId) {
        this.friendShipId = friendShipId;
    }


    /**
     * Gets the friendshipStatus value for this FriendDetailsWrapper.
     * 
     * @return friendshipStatus
     */
    public saicontella.core.webservices.authentication.FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }


    /**
     * Sets the friendshipStatus value for this FriendDetailsWrapper.
     * 
     * @param friendshipStatus
     */
    public void setFriendshipStatus(saicontella.core.webservices.authentication.FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }


    /**
     * Gets the status value for this FriendDetailsWrapper.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this FriendDetailsWrapper.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }


    /**
     * Gets the userId value for this FriendDetailsWrapper.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this FriendDetailsWrapper.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FriendDetailsWrapper)) return false;
        FriendDetailsWrapper other = (FriendDetailsWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.friendId==null && other.getFriendId()==null) || 
             (this.friendId!=null &&
              this.friendId.equals(other.getFriendId()))) &&
            ((this.friendName==null && other.getFriendName()==null) || 
             (this.friendName!=null &&
              this.friendName.equals(other.getFriendName()))) &&
            ((this.friendShipId==null && other.getFriendShipId()==null) || 
             (this.friendShipId!=null &&
              this.friendShipId.equals(other.getFriendShipId()))) &&
            ((this.friendshipStatus==null && other.getFriendshipStatus()==null) || 
             (this.friendshipStatus!=null &&
              this.friendshipStatus.equals(other.getFriendshipStatus()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
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
        int _hashCode = 1;
        if (getFriendId() != null) {
            _hashCode += getFriendId().hashCode();
        }
        if (getFriendName() != null) {
            _hashCode += getFriendName().hashCode();
        }
        if (getFriendShipId() != null) {
            _hashCode += getFriendShipId().hashCode();
        }
        if (getFriendshipStatus() != null) {
            _hashCode += getFriendshipStatus().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FriendDetailsWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "friendDetailsWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friendId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friendName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendShipId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friendShipId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friendshipStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friendshipStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "friendshipStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
