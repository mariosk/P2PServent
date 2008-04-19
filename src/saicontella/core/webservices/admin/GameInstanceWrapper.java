/**
 * GameInstanceWrapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class GameInstanceWrapper  implements java.io.Serializable {
    private java.lang.String clientDefaultInstallationPath;

    private int currentUsers;

    private java.lang.String executableName;

    private java.lang.String executableServerIpParam;

    private java.lang.String gameId;

    private java.lang.String instanceId;

    private int maxActiveUsers;

    private int minutesPerCredit;

    private java.lang.String registryKey;

    private java.lang.String serverInstallRootPath;

    private java.lang.String serverLogsPath;

    private java.lang.String[] tcpPorts;

    private java.lang.String thumbnailBase64Encoded;

    private java.lang.String[] udpPorts;

    private java.lang.String version;

    public GameInstanceWrapper() {
    }

    public GameInstanceWrapper(
           java.lang.String clientDefaultInstallationPath,
           int currentUsers,
           java.lang.String executableName,
           java.lang.String executableServerIpParam,
           java.lang.String gameId,
           java.lang.String instanceId,
           int maxActiveUsers,
           int minutesPerCredit,
           java.lang.String registryKey,
           java.lang.String serverInstallRootPath,
           java.lang.String serverLogsPath,
           java.lang.String[] tcpPorts,
           java.lang.String thumbnailBase64Encoded,
           java.lang.String[] udpPorts,
           java.lang.String version) {
           this.clientDefaultInstallationPath = clientDefaultInstallationPath;
           this.currentUsers = currentUsers;
           this.executableName = executableName;
           this.executableServerIpParam = executableServerIpParam;
           this.gameId = gameId;
           this.instanceId = instanceId;
           this.maxActiveUsers = maxActiveUsers;
           this.minutesPerCredit = minutesPerCredit;
           this.registryKey = registryKey;
           this.serverInstallRootPath = serverInstallRootPath;
           this.serverLogsPath = serverLogsPath;
           this.tcpPorts = tcpPorts;
           this.thumbnailBase64Encoded = thumbnailBase64Encoded;
           this.udpPorts = udpPorts;
           this.version = version;
    }


    /**
     * Gets the clientDefaultInstallationPath value for this GameInstanceWrapper.
     * 
     * @return clientDefaultInstallationPath
     */
    public java.lang.String getClientDefaultInstallationPath() {
        return clientDefaultInstallationPath;
    }


    /**
     * Sets the clientDefaultInstallationPath value for this GameInstanceWrapper.
     * 
     * @param clientDefaultInstallationPath
     */
    public void setClientDefaultInstallationPath(java.lang.String clientDefaultInstallationPath) {
        this.clientDefaultInstallationPath = clientDefaultInstallationPath;
    }


    /**
     * Gets the currentUsers value for this GameInstanceWrapper.
     * 
     * @return currentUsers
     */
    public int getCurrentUsers() {
        return currentUsers;
    }


    /**
     * Sets the currentUsers value for this GameInstanceWrapper.
     * 
     * @param currentUsers
     */
    public void setCurrentUsers(int currentUsers) {
        this.currentUsers = currentUsers;
    }


    /**
     * Gets the executableName value for this GameInstanceWrapper.
     * 
     * @return executableName
     */
    public java.lang.String getExecutableName() {
        return executableName;
    }


    /**
     * Sets the executableName value for this GameInstanceWrapper.
     * 
     * @param executableName
     */
    public void setExecutableName(java.lang.String executableName) {
        this.executableName = executableName;
    }


    /**
     * Gets the executableServerIpParam value for this GameInstanceWrapper.
     * 
     * @return executableServerIpParam
     */
    public java.lang.String getExecutableServerIpParam() {
        return executableServerIpParam;
    }


    /**
     * Sets the executableServerIpParam value for this GameInstanceWrapper.
     * 
     * @param executableServerIpParam
     */
    public void setExecutableServerIpParam(java.lang.String executableServerIpParam) {
        this.executableServerIpParam = executableServerIpParam;
    }


    /**
     * Gets the gameId value for this GameInstanceWrapper.
     * 
     * @return gameId
     */
    public java.lang.String getGameId() {
        return gameId;
    }


    /**
     * Sets the gameId value for this GameInstanceWrapper.
     * 
     * @param gameId
     */
    public void setGameId(java.lang.String gameId) {
        this.gameId = gameId;
    }


    /**
     * Gets the instanceId value for this GameInstanceWrapper.
     * 
     * @return instanceId
     */
    public java.lang.String getInstanceId() {
        return instanceId;
    }


    /**
     * Sets the instanceId value for this GameInstanceWrapper.
     * 
     * @param instanceId
     */
    public void setInstanceId(java.lang.String instanceId) {
        this.instanceId = instanceId;
    }


    /**
     * Gets the maxActiveUsers value for this GameInstanceWrapper.
     * 
     * @return maxActiveUsers
     */
    public int getMaxActiveUsers() {
        return maxActiveUsers;
    }


    /**
     * Sets the maxActiveUsers value for this GameInstanceWrapper.
     * 
     * @param maxActiveUsers
     */
    public void setMaxActiveUsers(int maxActiveUsers) {
        this.maxActiveUsers = maxActiveUsers;
    }


    /**
     * Gets the minutesPerCredit value for this GameInstanceWrapper.
     * 
     * @return minutesPerCredit
     */
    public int getMinutesPerCredit() {
        return minutesPerCredit;
    }


    /**
     * Sets the minutesPerCredit value for this GameInstanceWrapper.
     * 
     * @param minutesPerCredit
     */
    public void setMinutesPerCredit(int minutesPerCredit) {
        this.minutesPerCredit = minutesPerCredit;
    }


    /**
     * Gets the registryKey value for this GameInstanceWrapper.
     * 
     * @return registryKey
     */
    public java.lang.String getRegistryKey() {
        return registryKey;
    }


    /**
     * Sets the registryKey value for this GameInstanceWrapper.
     * 
     * @param registryKey
     */
    public void setRegistryKey(java.lang.String registryKey) {
        this.registryKey = registryKey;
    }


    /**
     * Gets the serverInstallRootPath value for this GameInstanceWrapper.
     * 
     * @return serverInstallRootPath
     */
    public java.lang.String getServerInstallRootPath() {
        return serverInstallRootPath;
    }


    /**
     * Sets the serverInstallRootPath value for this GameInstanceWrapper.
     * 
     * @param serverInstallRootPath
     */
    public void setServerInstallRootPath(java.lang.String serverInstallRootPath) {
        this.serverInstallRootPath = serverInstallRootPath;
    }


    /**
     * Gets the serverLogsPath value for this GameInstanceWrapper.
     * 
     * @return serverLogsPath
     */
    public java.lang.String getServerLogsPath() {
        return serverLogsPath;
    }


    /**
     * Sets the serverLogsPath value for this GameInstanceWrapper.
     * 
     * @param serverLogsPath
     */
    public void setServerLogsPath(java.lang.String serverLogsPath) {
        this.serverLogsPath = serverLogsPath;
    }


    /**
     * Gets the tcpPorts value for this GameInstanceWrapper.
     * 
     * @return tcpPorts
     */
    public java.lang.String[] getTcpPorts() {
        return tcpPorts;
    }


    /**
     * Sets the tcpPorts value for this GameInstanceWrapper.
     * 
     * @param tcpPorts
     */
    public void setTcpPorts(java.lang.String[] tcpPorts) {
        this.tcpPorts = tcpPorts;
    }

    public java.lang.String getTcpPorts(int i) {
        return this.tcpPorts[i];
    }

    public void setTcpPorts(int i, java.lang.String _value) {
        this.tcpPorts[i] = _value;
    }


    /**
     * Gets the thumbnailBase64Encoded value for this GameInstanceWrapper.
     * 
     * @return thumbnailBase64Encoded
     */
    public java.lang.String getThumbnailBase64Encoded() {
        return thumbnailBase64Encoded;
    }


    /**
     * Sets the thumbnailBase64Encoded value for this GameInstanceWrapper.
     * 
     * @param thumbnailBase64Encoded
     */
    public void setThumbnailBase64Encoded(java.lang.String thumbnailBase64Encoded) {
        this.thumbnailBase64Encoded = thumbnailBase64Encoded;
    }


    /**
     * Gets the udpPorts value for this GameInstanceWrapper.
     * 
     * @return udpPorts
     */
    public java.lang.String[] getUdpPorts() {
        return udpPorts;
    }


    /**
     * Sets the udpPorts value for this GameInstanceWrapper.
     * 
     * @param udpPorts
     */
    public void setUdpPorts(java.lang.String[] udpPorts) {
        this.udpPorts = udpPorts;
    }

    public java.lang.String getUdpPorts(int i) {
        return this.udpPorts[i];
    }

    public void setUdpPorts(int i, java.lang.String _value) {
        this.udpPorts[i] = _value;
    }


    /**
     * Gets the version value for this GameInstanceWrapper.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this GameInstanceWrapper.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GameInstanceWrapper)) return false;
        GameInstanceWrapper other = (GameInstanceWrapper) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.clientDefaultInstallationPath==null && other.getClientDefaultInstallationPath()==null) || 
             (this.clientDefaultInstallationPath!=null &&
              this.clientDefaultInstallationPath.equals(other.getClientDefaultInstallationPath()))) &&
            this.currentUsers == other.getCurrentUsers() &&
            ((this.executableName==null && other.getExecutableName()==null) || 
             (this.executableName!=null &&
              this.executableName.equals(other.getExecutableName()))) &&
            ((this.executableServerIpParam==null && other.getExecutableServerIpParam()==null) || 
             (this.executableServerIpParam!=null &&
              this.executableServerIpParam.equals(other.getExecutableServerIpParam()))) &&
            ((this.gameId==null && other.getGameId()==null) || 
             (this.gameId!=null &&
              this.gameId.equals(other.getGameId()))) &&
            ((this.instanceId==null && other.getInstanceId()==null) || 
             (this.instanceId!=null &&
              this.instanceId.equals(other.getInstanceId()))) &&
            this.maxActiveUsers == other.getMaxActiveUsers() &&
            this.minutesPerCredit == other.getMinutesPerCredit() &&
            ((this.registryKey==null && other.getRegistryKey()==null) || 
             (this.registryKey!=null &&
              this.registryKey.equals(other.getRegistryKey()))) &&
            ((this.serverInstallRootPath==null && other.getServerInstallRootPath()==null) || 
             (this.serverInstallRootPath!=null &&
              this.serverInstallRootPath.equals(other.getServerInstallRootPath()))) &&
            ((this.serverLogsPath==null && other.getServerLogsPath()==null) || 
             (this.serverLogsPath!=null &&
              this.serverLogsPath.equals(other.getServerLogsPath()))) &&
            ((this.tcpPorts==null && other.getTcpPorts()==null) || 
             (this.tcpPorts!=null &&
              java.util.Arrays.equals(this.tcpPorts, other.getTcpPorts()))) &&
            ((this.thumbnailBase64Encoded==null && other.getThumbnailBase64Encoded()==null) || 
             (this.thumbnailBase64Encoded!=null &&
              this.thumbnailBase64Encoded.equals(other.getThumbnailBase64Encoded()))) &&
            ((this.udpPorts==null && other.getUdpPorts()==null) || 
             (this.udpPorts!=null &&
              java.util.Arrays.equals(this.udpPorts, other.getUdpPorts()))) &&
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
        int _hashCode = 1;
        if (getClientDefaultInstallationPath() != null) {
            _hashCode += getClientDefaultInstallationPath().hashCode();
        }
        _hashCode += getCurrentUsers();
        if (getExecutableName() != null) {
            _hashCode += getExecutableName().hashCode();
        }
        if (getExecutableServerIpParam() != null) {
            _hashCode += getExecutableServerIpParam().hashCode();
        }
        if (getGameId() != null) {
            _hashCode += getGameId().hashCode();
        }
        if (getInstanceId() != null) {
            _hashCode += getInstanceId().hashCode();
        }
        _hashCode += getMaxActiveUsers();
        _hashCode += getMinutesPerCredit();
        if (getRegistryKey() != null) {
            _hashCode += getRegistryKey().hashCode();
        }
        if (getServerInstallRootPath() != null) {
            _hashCode += getServerInstallRootPath().hashCode();
        }
        if (getServerLogsPath() != null) {
            _hashCode += getServerLogsPath().hashCode();
        }
        if (getTcpPorts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTcpPorts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTcpPorts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getThumbnailBase64Encoded() != null) {
            _hashCode += getThumbnailBase64Encoded().hashCode();
        }
        if (getUdpPorts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUdpPorts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUdpPorts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GameInstanceWrapper.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "gameInstanceWrapper"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDefaultInstallationPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientDefaultInstallationPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentUsers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentUsers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executableName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "executableName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("executableServerIpParam");
        elemField.setXmlName(new javax.xml.namespace.QName("", "executableServerIpParam"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gameId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gameId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instanceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "instanceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxActiveUsers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxActiveUsers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minutesPerCredit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minutesPerCredit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "registryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverInstallRootPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverInstallRootPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serverLogsPath");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serverLogsPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcpPorts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcpPorts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thumbnailBase64Encoded");
        elemField.setXmlName(new javax.xml.namespace.QName("", "thumbnailBase64Encoded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udpPorts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "udpPorts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
