/**
 * UserAuthentication_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices;

public interface UserAuthentication_PortType extends java.rmi.Remote {
    public int setStatus(java.lang.String sessionId, int status) throws java.rmi.RemoteException;
    public saicontella.core.webservices.AvailableCreditsResponseWrapper availableCredits(java.lang.String sessionId, java.lang.String userId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.LoginResponseWrapper login(java.lang.String username, java.lang.String password, java.lang.String applicationId, java.lang.Integer port) throws java.rmi.RemoteException;
    public saicontella.core.webservices.LoginResponseWrapper loggoff(java.lang.String sessionId, java.lang.String userId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.ActiveSessionsResponseWrapper activeSessions(java.lang.String sessionId, java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.ActiveSessionsResponseWrapper activeSessionsSerialized(java.lang.String sessionId, java.lang.String applicationId) throws java.rmi.RemoteException;
}
