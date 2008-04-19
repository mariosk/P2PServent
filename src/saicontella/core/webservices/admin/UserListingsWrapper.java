/**
 * UserListingsWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class UserListingsWrapper  extends saicontella.core.webservices.admin.AdminBaseResponse  implements java.io.Serializable {
    private saicontella.core.webservices.admin.UserInfoWrapper[] userListings;

    public UserListingsWrapper() {
    }

    public UserListingsWrapper(
           java.lang.String errorMessage,
           java.lang.String stackTrace,
           saicontella.core.webservices.admin.ResponseSTATUS status,
           saicontella.core.webservices.admin.UserInfoWrapper[] userListings) {
        super(
            errorMessage,
            stackTrace,
            status);
        this.userListings = userListings;
    }


    /**
     * Gets the userListings value for this UserListingsWrapper.
     * 
     * @return userListings
     */
    public saicontella.core.webservices.admin.UserInfoWrapper[] getUserListings() {
        return userListings;
    }


    /**
     * Sets the userListings value for this UserListingsWrapper.
     * 
     * @param userListings
     */
    public void setUserListings(saicontella.core.webservices.admin.UserInfoWrapper[] userListings) {
        this.userListings = userListings;
    }

    public saicontella.core.webservices.admin.UserInfoWrapper getUserListings(int i) {
        return this.userListings[i];
    }

    public void setUserListings(int i, saicontella.core.webservices.admin.UserInfoWrapper _value) {
        this.userListings[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserListingsWrapper)) return false;
        UserListingsWrapper other = (UserListingsWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.userListings==null && other.getUserListings()==null) || 
             (this.userListings!=null &&
              java.util.Arrays.equals(this.userListings, other.getUserListings())));
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
        if (getUserListings() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserListings());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUserListings(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserListingsWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "userListingsWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userListings");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userListings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "userInfoWrapper"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
