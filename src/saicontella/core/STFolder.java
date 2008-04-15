package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.util.ArrayList;

public class STFolder {
    
    private String folderName;
    private STLibrary.STConstants.AccessEnumerator accessValue;
    private STFileName[] files;
    private ArrayList friendsList;
    
    public STFolder() {        
    }
    
    public STFolder(String fName, STFileName[] filesList, String accStr, ArrayList friends) {
        this.folderName = fName;
        this.files = filesList;
        if (accStr.equals(STLibrary.STConstants.PUBLIC_ACCESS))
            this.accessValue = STLibrary.STConstants.AccessEnumerator.PUBLIC;
        else if (accStr.equals(STLibrary.STConstants.PRIVATE_ACCESS))
            this.accessValue = STLibrary.STConstants.AccessEnumerator.PRIVATE;
        if (friends == null)
            this.friendsList = new ArrayList();
        else
            this.friendsList = friends;        
    }
    
    public String getFolderName()
    {
        return this.folderName;
    }
    
    public STFileName[] getFiles()
    {
        return this.files;
    }
    
    public void setName(String name)
    {
        this.folderName = name;
    }
    
    public void setFiles(STFileName[] filesList) {
        this.files = filesList;
    }

    public void setAccess(String accStr)
    {
        if (accStr.equals(STLibrary.STConstants.PUBLIC_ACCESS))
            this.accessValue = STLibrary.STConstants.AccessEnumerator.PUBLIC;
        else if (accStr.equals(STLibrary.STConstants.PRIVATE_ACCESS))
            this.accessValue = STLibrary.STConstants.AccessEnumerator.PRIVATE;
    }

    public STLibrary.STConstants.AccessEnumerator getAccess()
    {
        return this.accessValue;
    }

    public void addFriend(STFriend friend)
    {
        this.friendsList.add(friend);
    }
    
    public void setFriendsList(String friends, ArrayList myFriendsList)
    {
        String[] splitted = friends.split(",");
        if (this.friendsList != null)
            this.friendsList.clear();
        else
            this.friendsList = new ArrayList();
        if (splitted.length == 1) {
            if (splitted[0].equals(""))
                return;
        }
        for (int i = 0; i < splitted.length; i++) {
            String friendName = splitted[i].trim();
            this.friendsList.add(STLibrary.getSTFriend(friendName, myFriendsList));
        }
    }

    public ArrayList getFriends()
    {
        return this.friendsList;
    }    
}
