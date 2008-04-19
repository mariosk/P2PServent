/**
 * UserServerAdmin_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package saicontella.core.webservices.admin;

public interface UserServerAdmin_PortType extends java.rmi.Remote {
    public saicontella.core.webservices.admin.RegisterResponseWrapper register(java.lang.String userName, java.lang.String password, java.lang.String firstName, java.lang.String lastName, java.util.Calendar birthDate, java.lang.String countryId, java.lang.String extUserId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.PurchaseResponseWrapper resetUserCredits(java.lang.String userId, java.lang.String extUserId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.RegisterResponseWrapper updateUserPassword(java.lang.String userId, java.lang.String extUserId, java.lang.String password, java.lang.String newPassword) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.GameInstanceWrapper[] listGameInstances(java.lang.String gameId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ActiveSessionWrapper[] listActiveSessions() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper updateGameServer(java.lang.String serverId, java.lang.String countryId, java.lang.String serverName, java.lang.String serverIp, int serverPort, java.lang.String internalServerIp, int maxActiveUsers, java.lang.String routerIp, saicontella.core.webservices.admin.ServerPlatform serverPlatform) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper deleteGameServer(java.lang.String serverId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.GameServerInfo[] listGameServers() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper updateGame(java.lang.String gameId, java.lang.String name, java.lang.String description) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper deleteGame(java.lang.String gameId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper updateGameInstance(java.lang.String instanceId, saicontella.core.webservices.admin.GameInstanceWrapper info) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper deleteGameInstance(java.lang.String instanceId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.GameDetailsWrapper[] listGames() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.CountryInfoWrapper[] listCountries() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper updateCountry(java.lang.String countryId, saicontella.core.webservices.admin.CountryInfoWrapper details) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper deleteCountry(java.lang.String countryId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ClientApplicationWrapper[] listClientApplications() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.UserInfoWrapper[] listUsers() throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper setUserSettings(java.lang.String userId, java.lang.String applicationId, saicontella.core.webservices.admin.UserSettingsWrapper[] userSettingsWrapperList) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.UserListingsWrapper searchUsers(java.lang.String username, java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper updateClientVersion(java.lang.String version, java.lang.String downloadLocation) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper createCountry(saicontella.core.webservices.admin.CountryInfoWrapper details) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.PurchaseResponseWrapper handleUserCredits(java.lang.String userId, java.lang.String extUserId, float credits) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper createGame(java.lang.String name, java.lang.String description) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper createGameServer(java.lang.String countryId, java.lang.String serverName, java.lang.String serverIp, int serverPort, java.lang.String internalServerIp, int maxActiveUsers, java.lang.String routerIp, saicontella.core.webservices.admin.ServerPlatform serverPlatform) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper disableGameServer(java.lang.String serverId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper enableGameServer(java.lang.String serverId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper createGameInstance(saicontella.core.webservices.admin.GameInstanceWrapper info) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper addInstanceToGameServer(java.lang.String serverId, java.lang.String instanceId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ServerAdminResponseWrapper removeInstanceFromGameServer(java.lang.String serverId, java.lang.String instanceId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.GameServerInfo[] listGameServersByGameInstance(java.lang.String gameId, java.lang.String instanceId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.GameServerInfo[] listGameServersByGameId(java.lang.String gameId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.ContentCategoryWrapper[] listAvailableContentCategories(java.lang.String applicationId) throws java.rmi.RemoteException;
    public saicontella.core.webservices.admin.UserSettingsWrapper[] fetchUserSettings(java.lang.String userId, java.lang.String applicationId) throws java.rmi.RemoteException;
}
