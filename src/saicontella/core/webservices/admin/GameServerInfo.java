/**
 * GameServerInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class GameServerInfo  implements java.io.Serializable {
    private saicontella.core.webservices.admin.CountryInfoWrapper country;

    private java.lang.String[] gameInstanceIds;

    private java.lang.String gameServerId;

    private java.lang.Integer onlinePlayers;

    private java.lang.String routerIp;

    private java.lang.String serverIp;

    private java.lang.String serverName;

    private saicontella.core.webservices.admin.ServerPlatform serverPlatform;

    private int serverPort;

    private java.lang.Integer totalPlayerAllowed;

    public GameServerInfo() {
    }

    public GameServerInfo(
           saicontella.core.webservices.admin.CountryInfoWrapper country,
           java.lang.String[] gameInstanceIds,
           java.lang.String gameServerId,
           java.lang.Integer onlinePlayers,
           java.lang.String routerIp,
           java.lang.String serverIp,
           java.lang.String serverName,
           saicontella.core.webservices.admin.ServerPlatform serverPlatform,
           int serverPort,
           java.lang.Integer totalPlayerAllowed) {
           this.country = country;
           this.gameInstanceIds = gameInstanceIds;
           this.gameServerId = gameServerId;
           this.onlinePlayers = onlinePlayers;
           this.routerIp = routerIp;
           this.serverIp = serverIp;
           this.serverName = serverName;
           this.serverPlatform = serverPlatform;
           this.serverPort = serverPort;
           this.totalPlayerAllowed = totalPlayerAllowed;
    }


    /**
     * Gets the country value for this GameServerInfo.
     * 
     * @return country
     */
    public saicontella.core.webservices.admin.CountryInfoWrapper getCountry() {
        return country;
    }


    /**
     * Sets the country value for this GameServerInfo.
     * 
     * @param country
     */
    public void setCountry(saicontella.core.webservices.admin.CountryInfoWrapper country) {
        this.country = country;
    }


    /**
     * Gets the gameInstanceIds value for this GameServerInfo.
     * 
     * @return gameInstanceIds
     */
    public java.lang.String[] getGameInstanceIds() {
        return gameInstanceIds;
    }


    /**
     * Sets the gameInstanceIds value for this GameServerInfo.
     * 
     * @param gameInstanceIds
     */
    public void setGameInstanceIds(java.lang.String[] gameInstanceIds) {
        this.gameInstanceIds = gameInstanceIds;
    }

    public java.lang.String getGameInstanceIds(int i) {
        return this.gameInstanceIds[i];
    }

    public void setGameInstanceIds(int i, java.lang.String _value) {
        this.gameInstanceIds[i] = _value;
    }


    /**
     * Gets the gameServerId value for this GameServerInfo.
     * 
     * @return gameServerId
     */
    public java.lang.String getGameServerId() {
        return gameServerId;
    }


    /**
     * Sets the gameServerId value for this GameServerInfo.
     * 
     * @param gameServerId
     */
    public void setGameServerId(java.lang.String gameServerId) {
        this.gameServerId = gameServerId;
    }


    /**
     * Gets the onlinePlayers value for this GameServerInfo.
     * 
     * @return onlinePlayers
     */
    public java.lang.Integer getOnlinePlayers() {
        return onlinePlayers;
    }


    /**
     * Sets the onlinePlayers value for this GameServerInfo.
     * 
     * @param onlinePlayers
     */
    public void setOnlinePlayers(java.lang.Integer onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }


    /**
     * Gets the routerIp value for this GameServerInfo.
     * 
     * @return routerIp
     */
    public java.lang.String getRouterIp() {
        return routerIp;
    }


    /**
     * Sets the routerIp value for this GameServerInfo.
     * 
     * @param routerIp
     */
    public void setRouterIp(java.lang.String routerIp) {
        this.routerIp = routerIp;
    }


    /**
     * Gets the serverIp value for this GameServerInfo.
     * 
     * @return serverIp
     */
    public java.lang.String getServerIp() {
        return serverIp;
    }


    /**
     * Sets the serverIp value for this GameServerInfo.
     * 
     * @param serverIp
     */
    public void setServerIp(java.lang.String serverIp) {
        this.serverIp = serverIp;
    }


    /**
     * Gets the serverName value for this GameServerInfo.
     * 
     * @return serverName
     */
    public java.lang.String getServerName() {
        return serverName;
    }


    /**
     * Sets the serverName value for this GameServerInfo.
     * 
     * @param serverName
     */
    public void setServerName(java.lang.String serverName) {
        this.serverName = serverName;
    }


    /**
     * Gets the serverPlatform value for this GameServerInfo.
     * 
     * @return serverPlatform
     */
    public saicontella.core.webservices.admin.ServerPlatform getServerPlatform() {
        return serverPlatform;
    }


    /**
     * Sets the serverPlatform value for this GameServerInfo.
     * 
     * @param serverPlatform
     */
    public void setServerPlatform(saicontella.core.webservices.admin.ServerPlatform serverPlatform) {
        this.serverPlatform = serverPlatform;
    }


    /**
     * Gets the serverPort value for this GameServerInfo.
     * 
     * @return serverPort
     */
    public int getServerPort() {
        return serverPort;
    }


    /**
     * Sets the serverPort value for this GameServerInfo.
     * 
     * @param serverPort
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }


    /**
     * Gets the totalPlayerAllowed value for this GameServerInfo.
     * 
     * @return totalPlayerAllowed
     */
    public java.lang.Integer getTotalPlayerAllowed() {
        return totalPlayerAllowed;
    }


    /**
     * Sets the totalPlayerAllowed value for this GameServerInfo.
     * 
     * @param totalPlayerAllowed
     */
    public void setTotalPlayerAllowed(java.lang.Integer totalPlayerAllowed) {
        this.totalPlayerAllowed = totalPlayerAllowed;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameServerInfo)) return false;
        GameServerInfo other = (GameServerInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.gameInstanceIds==null && other.getGameInstanceIds()==null) || 
             (this.gameInstanceIds!=null &&
              java.util.Arrays.equals(this.gameInstanceIds, other.getGameInstanceIds()))) &&
            ((this.gameServerId==null && other.getGameServerId()==null) || 
             (this.gameServerId!=null &&
              this.gameServerId.equals(other.getGameServerId()))) &&
            ((this.onlinePlayers==null && other.getOnlinePlayers()==null) || 
             (this.onlinePlayers!=null &&
              this.onlinePlayers.equals(other.getOnlinePlayers()))) &&
            ((this.routerIp==null && other.getRouterIp()==null) || 
             (this.routerIp!=null &&
              this.routerIp.equals(other.getRouterIp()))) &&
            ((this.serverIp==null && other.getServerIp()==null) || 
             (this.serverIp!=null &&
              this.serverIp.equals(other.getServerIp()))) &&
            ((this.serverName==null && other.getServerName()==null) || 
             (this.serverName!=null &&
              this.serverName.equals(other.getServerName()))) &&
            ((this.serverPlatform==null && other.getServerPlatform()==null) || 
             (this.serverPlatform!=null &&
              this.serverPlatform.equals(other.getServerPlatform()))) &&
            this.serverPort == other.getServerPort() &&
            ((this.totalPlayerAllowed==null && other.getTotalPlayerAllowed()==null) || 
             (this.totalPlayerAllowed!=null &&
              this.totalPlayerAllowed.equals(other.getTotalPlayerAllowed())));
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
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getGameInstanceIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGameInstanceIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGameInstanceIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGameServerId() != null) {
            _hashCode += getGameServerId().hashCode();
        }
        if (getOnlinePlayers() != null) {
            _hashCode += getOnlinePlayers().hashCode();
        }
        if (getRouterIp() != null) {
            _hashCode += getRouterIp().hashCode();
        }
        if (getServerIp() != null) {
            _hashCode += getServerIp().hashCode();
        }
        if (getServerName() != null) {
            _hashCode += getServerName().hashCode();
        }
        if (getServerPlatform() != null) {
            _hashCode += getServerPlatform().hashCode();
        }
        _hashCode += getServerPort();
        if (getTotalPlayerAllowed() != null) {
            _hashCode += getTotalPlayerAllowed().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameServerInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "gameServerInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "countryInfoWrapper"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameInstanceIds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gameInstanceIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameServerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gameServerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("onlinePlayers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "onlinePlayers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("routerIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "routerIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverPlatform");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverPlatform"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "serverPlatform"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalPlayerAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalPlayerAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
