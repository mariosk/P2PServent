package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class STConfiguration {
    
    private String accountName;
    private String accountServer;
    private int listenPort;
    private String listenAddress;
    private int connTimeout;
    private int maxConnections;
    private boolean autoConnect;
    private String completeFolder;
    private String inCompleteFolder;
    private int maxDownload;
    private int maxUpload;
    private ArrayList sharedFolders;
    private ArrayList myFriends;
    private String adsServer;
    private String webServiceEndpoint;
    private String webServiceAccount;
    private String webServicePassword;
    
    public STConfiguration()
    {
        this.sharedFolders = new ArrayList();
        this.myFriends = new ArrayList();
    }

    public void setWebServiceEndpoint(String value) {
        this.webServiceEndpoint = value;
    }
    public String getWebServiceEndpoint() {
        return this.webServiceEndpoint;
    }

    public void setWebServiceAccount(String value) {
        this.webServiceAccount = value;
    }
    public String getWebServiceAccount() {
        return this.webServiceAccount;
    }

    public void setWebServicePassword(String value) {
        this.webServicePassword = value;
    }
    public String getWebServicePassword() {
        return this.webServicePassword;
    }
    
    public void setCompleteFolder(String fodler) {
        this.completeFolder = fodler;        
    }
    public String getCompleteFolder() {
        return this.completeFolder;        
    }

    public void setInCompleteFolder(String fodler) {
        this.inCompleteFolder = fodler;        
    }
    public String getInCompleteFolder() {
        return this.inCompleteFolder;        
    }

    public String getListenAddress() {
        return this.listenAddress;
    }    
    public void setListenAddress(String address) {
    	this.listenAddress = address;
    }

    public int getListenPort() {
        return this.listenPort;
    }
    public void setListenPort(String port) {
        this.listenPort = Integer.parseInt(port);        
    }

    public int getConnTimeout() {
        return this.connTimeout;
    }
    public void setConnTimeout(String timeout) {
        this.connTimeout = Integer.parseInt(timeout);        
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }
    public void setMaxConnections(String max) {
        this.maxConnections = Integer.parseInt(max);        
    }

    public boolean getAutoConnect() {
        return this.autoConnect;
    }
    public void setAutoConnect(String auto) {
        this.autoConnect = Boolean.parseBoolean(auto);
    }

    public String getAccountServer() {
        return this.accountServer;
    }
    public void setAccountServer(String server) {
        this.accountServer = server;        
    }

    public ArrayList getFolders() {
        return this.sharedFolders;
    }
    public void addFolder(STFolder folder) {
        this.sharedFolders.add(folder);
    }
    
    public void setAccountName(String name) {
        this.accountName = name;        
    }
    public String getAccountName() {
        return this.accountName;        
    }

    public void setMaxDownload(int max) {
        this.maxDownload = max;        
    }
    public int getMaxDownload() {
        return this.maxDownload;
    }

    public void setMaxUpload(int max) {
        this.maxUpload = max;        
    }
    public int getMaxUpload() {
        return this.maxUpload;
    }

    public Vector getMyFriendsVectorData() {
        Vector data = new Vector();
        for (int i = 0; i < this.myFriends.size(); i++) {
            STFriend friend = (STFriend) this.myFriends.get(i);
            data.add(friend.getFriendName());
        }
        return data;
    }
    
    public ArrayList getMyFriends() {
        return this.myFriends;
    }
    public void addFriend(STFriend friend) {
        this.myFriends.add(friend);
    }

    public void setAdsServer(String server) {
        this.adsServer = server;        
    }   
    public String getAdsServer() {
        return this.adsServer;        
    }

    public int getSTFolderIndex(String fName) {
        for (int i = 0; i < this.getFolders().size(); i++) {
            STFolder folder = (STFolder) this.getFolders().get(i);
            if (fName.equalsIgnoreCase(folder.getFolderName()))
                return i;
        }
        return -1;
    }

    public int getSTFileIndex(String fileName, int folderIndex)
    {
        STFolder folder = (STFolder) this.getFolders().get(folderIndex);
        if (folder != null) {
            STFileName[] files = folder.getFiles();
            for (int i = 0; i < files.length; i++) {
                if (fileName.equalsIgnoreCase(folder.getFolderName() + "\\" + files[i].getFileName()))
                    return i;
            }
            return -1;
        }
        return -1;
    }

    public void saveXMLFile() {
        this.saveXMLFile(STResources.getStr("Application.configurationFile"));    
    }

    public void saveXMLFile(String filename) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<configuration>");
        buffer.append("\n");
        buffer.append("\t<!-- mySQL server -->");
        buffer.append("\n");
        buffer.append("\t<account name=\"" + this.getAccountName() + "\" server=\"" + this.getAccountServer() + "\"/>");
        buffer.append("\n");
        buffer.append("\t<!-- webService endpoint -->");
        buffer.append("\n");
        buffer.append("\t<webservice name=\"" + this.getWebServiceAccount() + "\" password=\"" + this.getWebServicePassword() + "\" wsdl=\"" + this.getWebServiceEndpoint() + "\"/>");
        buffer.append("\n");
        buffer.append("\t<!-- gnutella servent -->");
        buffer.append("\n");
        String value = this.getAutoConnect() ? "true":"false";
        buffer.append("\t<listen port=\"" + this.getListenPort() + "\" address=\"" + this.getListenAddress() + "\" conn_timeout=\"" + this.getConnTimeout() + "\" max_connections=\"" + this.getMaxConnections() + "\" auto_connect=\"" + value + "\"/>");
        buffer.append("\n");
        buffer.append("\t<folders complete=\"" + this.getCompleteFolder() +"\" incomplete=\"" + this.getInCompleteFolder() + "\"/>");
        buffer.append("\n");
        buffer.append("\t<ratio maxDownload=\"" + this.getMaxDownload() +"\" maxUpload=\"" + this.getMaxUpload() + "\"/>");
        buffer.append("\n");

        buffer.append("\t<myFriends>");
        buffer.append("\n");
        ArrayList friends = this.getMyFriends();
        for (int i = 0; i < friends.size(); i++) {
            STFriend friend = (STFriend)friends.get(i);
            buffer.append("\t\t<friend name=\"" + friend.getFriendName() + "\" ipaddress=\"" + friend.getIPAddress() + "\"/>");
            buffer.append("\n");
        }
        buffer.append("\t</myFriends>");
        buffer.append("\n");

        buffer.append("\t<sharedFolders>");
        buffer.append("\n");

        ArrayList folders = this.getFolders();
        for (int i = 0; i < folders.size(); i++) {
            STFolder folder = (STFolder)folders.get(i);
            value = (folder.getAccess() == STLibrary.STConstants.AccessEnumerator.PRIVATE ? "private" : "public");
            String friendsList = "";
            friends = folder.getFriends();
            STFileName[] files = folder.getFiles();
            for (int f = 0; f < friends.size(); f++) {
                STFriend friend = (STFriend) friends.get(f);
                friendsList += friend.getFriendName();
                if (f < friends.size() - 1)
                    friendsList += ",";
            }
            buffer.append("\t\t<folder name=\"" + folder.getFolderName() + "\" access=\"" + value + "\" friends=\"" + friendsList + "\">");
            buffer.append("\n");            
            for (int j = 0; j < files.length; j++) {
                buffer.append("\t\t\t<file name=\"" + files[j].getFileName() + "\"/>");
                buffer.append("\n");
            }
            buffer.append("\t\t</folder>");
            buffer.append("\n");
        }
        buffer.append("\t</sharedFolders>");
        buffer.append("\n");

        buffer.append("\t<ads server=\"" + this.getAdsServer() + "\"></ads>");
        buffer.append("\n");
        buffer.append("</configuration>");

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename + ".tmp"));
            out.write(buffer.toString());
            out.close();
            BufferedWriter outNew = new BufferedWriter(new FileWriter(filename));
            outNew.write(buffer.toString());
            outNew.close();
        }
        catch (Exception ie) {
            ie.printStackTrace();
        }
    }
}
