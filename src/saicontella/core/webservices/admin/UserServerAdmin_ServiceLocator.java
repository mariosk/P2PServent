/**
 * UserServerAdmin_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public class UserServerAdmin_ServiceLocator extends org.apache.axis.client.Service implements saicontella.core.webservices.admin.UserServerAdmin_Service {

    public UserServerAdmin_ServiceLocator() {
    }


    public UserServerAdmin_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserServerAdmin_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserServerAdminImplPort
    private java.lang.String UserServerAdminImplPort_address = "http://85.17.217.11:8080/UserServer/UserServerAdmin";

    public java.lang.String getUserServerAdminImplPortAddress() {
        return UserServerAdminImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserServerAdminImplPortWSDDServiceName = "UserServerAdminImplPort";

    public java.lang.String getUserServerAdminImplPortWSDDServiceName() {
        return UserServerAdminImplPortWSDDServiceName;
    }

    public void setUserServerAdminImplPortWSDDServiceName(java.lang.String name) {
        UserServerAdminImplPortWSDDServiceName = name;
    }

    public saicontella.core.webservices.admin.UserServerAdmin_PortType getUserServerAdminImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserServerAdminImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserServerAdminImplPort(endpoint);
    }

    public saicontella.core.webservices.admin.UserServerAdmin_PortType getUserServerAdminImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            saicontella.core.webservices.admin.UserServerAdminImplPortBindingStub _stub = new saicontella.core.webservices.admin.UserServerAdminImplPortBindingStub(portAddress, this);
            _stub.setPortName(getUserServerAdminImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserServerAdminImplPortEndpointAddress(java.lang.String address) {
        UserServerAdminImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (saicontella.core.webservices.admin.UserServerAdmin_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                saicontella.core.webservices.admin.UserServerAdminImplPortBindingStub _stub = new saicontella.core.webservices.admin.UserServerAdminImplPortBindingStub(new java.net.URL(UserServerAdminImplPort_address), this);
                _stub.setPortName(getUserServerAdminImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserServerAdminImplPort".equals(inputPortName)) {
            return getUserServerAdminImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "UserServerAdmin");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "UserServerAdminImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserServerAdminImplPort".equals(portName)) {
            setUserServerAdminImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
