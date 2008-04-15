/**
 * UserAuthentication_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices;

public class UserAuthentication_ServiceLocator extends org.apache.axis.client.Service implements saicontella.core.webservices.UserAuthentication_Service {

    public UserAuthentication_ServiceLocator() {
    }


    public UserAuthentication_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserAuthentication_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserAuthenticationImplPort
    private java.lang.String UserAuthenticationImplPort_address = "http://85.17.217.11:8080/UserServer/UserAuthentication";

    public java.lang.String getUserAuthenticationImplPortAddress() {
        return UserAuthenticationImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserAuthenticationImplPortWSDDServiceName = "UserAuthenticationImplPort";

    public java.lang.String getUserAuthenticationImplPortWSDDServiceName() {
        return UserAuthenticationImplPortWSDDServiceName;
    }

    public void setUserAuthenticationImplPortWSDDServiceName(java.lang.String name) {
        UserAuthenticationImplPortWSDDServiceName = name;
    }

    public saicontella.core.webservices.UserAuthentication_PortType getUserAuthenticationImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserAuthenticationImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserAuthenticationImplPort(endpoint);
    }

    public saicontella.core.webservices.UserAuthentication_PortType getUserAuthenticationImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            saicontella.core.webservices.UserAuthenticationImplPortBindingStub _stub = new saicontella.core.webservices.UserAuthenticationImplPortBindingStub(portAddress, this);
            _stub.setPortName(getUserAuthenticationImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserAuthenticationImplPortEndpointAddress(java.lang.String address) {
        UserAuthenticationImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (saicontella.core.webservices.UserAuthentication_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                saicontella.core.webservices.UserAuthenticationImplPortBindingStub _stub = new saicontella.core.webservices.UserAuthenticationImplPortBindingStub(new java.net.URL(UserAuthenticationImplPort_address), this);
                _stub.setPortName(getUserAuthenticationImplPortWSDDServiceName());
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
        if ("UserAuthenticationImplPort".equals(inputPortName)) {
            return getUserAuthenticationImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "UserAuthentication");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsi.server.user.gaming.saicon.gr/", "UserAuthenticationImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserAuthenticationImplPort".equals(portName)) {
            setUserAuthenticationImplPortEndpointAddress(address);
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
