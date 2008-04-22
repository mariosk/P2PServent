/**
 * UserAuthentication_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.authentication;

public interface UserAuthentication_PortType extends java.rmi.Remote {
    public int setStatus(java.lang.String sessionId, int status) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.AvailableCreditsResponseWrapper availableCredits(java.lang.String sessionId, java.lang.String userId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.LoginResponseWrapper login(java.lang.String username, java.lang.String password, java.lang.String applicationId, java.lang.Integer port) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.LoginResponseWrapper loginExternal(java.lang.String username, java.lang.String password, java.lang.String userIp, java.lang.String applicationId, java.lang.Integer port) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.LoginResponseWrapper loggoff(java.lang.String sessionId, java.lang.String userId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.ActiveSessionsResponseWrapper activeSessions(java.lang.String sessionId, java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.ActiveSessionsResponseWrapper activeSessionsSerialized(java.lang.String sessionId, java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.UserRegistrationsWrapper[] fetchUserPackageRegistrations(java.lang.String sessionId, java.lang.String userId, java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.UserSettingsWrapper[] fetchUserSettings(java.lang.String sessionId, java.lang.String userId, java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.BaseResponse addFriend(java.lang.String sessionId, java.lang.String friendId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.BaseResponse removeFriend(java.lang.String sessionId, java.lang.String userId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.FriendDetailsWrapper[] searchFriend(java.lang.String sessionId, java.lang.String friendName) throws java.rmi.RemoteException;
    public saicontella.core.webservices.authentication.UserInfoWrapper[] searchCandidateFriend(java.lang.String sessionId, java.lang.String userName, java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException;
}
