package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import saicontella.core.STLibrary.STConstants.*;

public class STFriend {
    
    private String friendName;
    private String friendId;
    private StatusEnumerator status;
    private String IPAddress;
    private int portNumber;
    
    public STFriend(String name) {
        this.friendName = name;
    }

    public int getPortNumber()
    {
        return this.portNumber;
    }
    public void setPortNumber(int port)
    {
        this.portNumber = port;
    }
    
    public String getIPAddress()
    {
        return this.IPAddress;
    }
    public void setIPAddress(String IPAddress)
    {
        this.IPAddress = IPAddress;
    }

    public String getFriendId()
    {
        return this.friendId;
    }
    public void setFriendId(String friendId)
    {
        this.friendId = friendId;
    }

    public void setStatus(String statusStr)
    {
        if (statusStr.equals("online"))
            this.status = StatusEnumerator.ONLINE;
        else if (statusStr.equals("offline"))
            this.status = StatusEnumerator.OFFLINE;
        else
            this.status = StatusEnumerator.UNDEFINED;
    }

    public StatusEnumerator getStatus()
    {
        return this.status;
    }

    public void setFriendName(String name)
    {
        this.friendName = name;
    }
    
    public String getFriendName()
    {
        return this.friendName;
    }
}
