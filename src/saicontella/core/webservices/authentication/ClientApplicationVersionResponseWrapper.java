/**
 * ClientApplicationVersionResponseWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public class ClientApplicationVersionResponseWrapper  extends saicontella.core.webservices.authentication.BaseResponse  implements java.io.Serializable {
    private java.lang.String downloadUrl;

    private java.util.Calendar releaseDate;

    private java.lang.String version;

    public ClientApplicationVersionResponseWrapper() {
    }

    public ClientApplicationVersionResponseWrapper(
           java.lang.String errorMessage,
           saicontella.core.webservices.authentication.ResponseSTATUS status,
           java.lang.String downloadUrl,
           java.util.Calendar releaseDate,
           java.lang.String version) {
        super(
            errorMessage,
            status);
        this.downloadUrl = downloadUrl;
        this.releaseDate = releaseDate;
        this.version = version;
    }


    /**
     * Gets the downloadUrl value for this ClientApplicationVersionResponseWrapper.
     * 
     * @return downloadUrl
     */
    public java.lang.String getDownloadUrl() {
        return downloadUrl;
    }


    /**
     * Sets the downloadUrl value for this ClientApplicationVersionResponseWrapper.
     * 
     * @param downloadUrl
     */
    public void setDownloadUrl(java.lang.String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    /**
     * Gets the releaseDate value for this ClientApplicationVersionResponseWrapper.
     * 
     * @return releaseDate
     */
    public java.util.Calendar getReleaseDate() {
        return releaseDate;
    }


    /**
     * Sets the releaseDate value for this ClientApplicationVersionResponseWrapper.
     * 
     * @param releaseDate
     */
    public void setReleaseDate(java.util.Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }


    /**
     * Gets the version value for this ClientApplicationVersionResponseWrapper.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ClientApplicationVersionResponseWrapper.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClientApplicationVersionResponseWrapper)) return false;
        ClientApplicationVersionResponseWrapper other = (ClientApplicationVersionResponseWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.downloadUrl==null && other.getDownloadUrl()==null) || 
             (this.downloadUrl!=null &&
              this.downloadUrl.equals(other.getDownloadUrl()))) &&
            ((this.releaseDate==null && other.getReleaseDate()==null) || 
             (this.releaseDate!=null &&
              this.releaseDate.equals(other.getReleaseDate()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getDownloadUrl() != null) {
            _hashCode += getDownloadUrl().hashCode();
        }
        if (getReleaseDate() != null) {
            _hashCode += getReleaseDate().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClientApplicationVersionResponseWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "clientApplicationVersionResponseWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("downloadUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "downloadUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("releaseDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "releaseDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
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
