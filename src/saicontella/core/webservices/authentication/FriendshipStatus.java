/**
 * FriendshipStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class FriendshipStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FriendshipStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _REQUEST = "REQUEST";
    public static final java.lang.String _ACCEPTED = "ACCEPTED";
    public static final java.lang.String _REJECTED = "REJECTED";
    public static final java.lang.String _DELETED = "DELETED";
    public static final java.lang.String _DENIED = "DENIED";
    public static final FriendshipStatus REQUEST = new FriendshipStatus(_REQUEST);
    public static final FriendshipStatus ACCEPTED = new FriendshipStatus(_ACCEPTED);
    public static final FriendshipStatus REJECTED = new FriendshipStatus(_REJECTED);
    public static final FriendshipStatus DELETED = new FriendshipStatus(_DELETED);
    public static final FriendshipStatus DENIED = new FriendshipStatus(_DENIED);
    public java.lang.String getValue() { return _value_;}
    public static FriendshipStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FriendshipStatus enumeration = (FriendshipStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FriendshipStatus fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FriendshipStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "friendshipStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
