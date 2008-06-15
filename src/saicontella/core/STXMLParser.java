package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class STXMLParser extends DefaultHandler {

    private static Log logger = LogFactory.getLog("saicontella.core.STXMLParser");
    private STConfiguration tmpConfiguration;
    private ArrayList<STFolder> sharedFolderList;
    private int sharedFolderIndex = 0;
    private ArrayList<STFileName> fileList;
    private int fileIndex = 0;
    private ArrayList<STFriend> myFriendsList;
    
    public STXMLParser() {
        //create a list to hold the configuration objects
        sharedFolderList = new ArrayList<STFolder>();
        myFriendsList = new ArrayList<STFriend>();
        fileList = new ArrayList<STFileName>();
    }        

    public STConfiguration readConfigurationFile() {
        //parse the xml file and get the dom object
        if (parseXmlFile(STResources.getStr("Application.configurationFile"))) {
            //Iterate through the list and print the data
            printData();
            return this.tmpConfiguration;
        }
        return null;
    }

    private boolean parseXmlFile(String filename) {
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
                
        try {
            File ffile = new File(filename);
            if (!ffile.exists())
                return false;
            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();
            //parse the file and also register this class for call backs
            sp.parse(ffile, this);
            return true;
        }
        catch(SAXException se) {
            se.printStackTrace();
            logger.error(se.getMessage());
        }
        catch(ParserConfigurationException pce) {
            pce.printStackTrace();
            logger.error(pce.getMessage());
        }
        catch (IOException ie) {
            ie.printStackTrace();
            logger.error(ie.getMessage());
        }
        return false;
    }

    private void printData() {                
        logger.debug("==================================");
        logger.debug("P2PClient XML Configuration Schema");
        logger.debug("==================================");
        logger.debug("start of configuration");
        //DEPRECATED//
        //logger.debug("\tAccount Name: " + this.tmpConfiguration.getAccountName());
        //logger.debug("\tAccount Server: " + this.tmpConfiguration.getAccountServer());
        //logger.debug("\tWeb service: Username: " + this.tmpConfiguration.getWebServiceAccount() + ", Password: " + this.tmpConfiguration.getWebServicePassword());
        logger.debug("\tWeb service: Username: " + this.tmpConfiguration.getWebServiceAccount() + ", Password: ********");
        logger.debug("\tListen Port: " + this.tmpConfiguration.getListenPort());
        if (this.tmpConfiguration.getListenAddress() != null)
            logger.debug("\tListen Address: " + this.tmpConfiguration.getListenAddress());
        else
            logger.debug("\tListen Address: ANY");
        logger.debug("\tConnection Timeout: " + this.tmpConfiguration.getConnTimeout());
        logger.debug("\tAuto Connect: " + this.tmpConfiguration.getAutoConnect());
        logger.debug("\tComplete Folder: " + this.tmpConfiguration.getCompleteFolder());
        logger.debug("\tInComplete Folder: " + this.tmpConfiguration.getInCompleteFolder());
        //logger.debug("\tRatio Max Download: " + this.tmpConfiguration.getMaxDownload());
        //logger.debug("\tRatio Max Upload: " + this.tmpConfiguration.getMaxUpload());
        logger.debug("\tShared Folders:");
        ArrayList folders = this.tmpConfiguration.getFolders();
        for (int i=0; i < folders.size(); i++) {
            STFolder folder = (STFolder)folders.get(i);
            logger.debug("\tFolder: " + folder.getFolderName());
            logger.debug("\t\tAccess: " + folder.getAccess().toString());
            ArrayList friends = folder.getFriends();
            for (int k = 0; k < friends.size(); k++) {
                STFriend friend = (STFriend) friends.get(k);
                if (friend != null)
                    logger.debug("\t\t\tFriend: " + friend.getFriendName());
            }
            STFileName[] files = folder.getFiles();
            for (int j = 0; j < files.length; j++) {
                logger.debug("\t\tFilename: " + files[j].getFileName());
                logger.debug("\t\t\tBytes: " + files[j].getBytes());
                logger.debug("\t\t\tSHA-1 Hash: " + files[j].getHash());
            }
        }    
        ArrayList myFriends = tmpConfiguration.getMyFriends();
        for (int i = 0; i < myFriends.size(); i++) {
            STFriend friend = (STFriend) myFriends.get(i);
            logger.debug("\tmyFriend name: " + friend.getFriendName());
            logger.debug("\tmyFriend IpAddress: " + friend.getIPAddress());
            logger.debug("\tmyFriend Port: " + friend.getPortNumber());            
            logger.debug("\tmyFriend Id: " + friend.getFriendId());
        }        
        logger.debug("end of configuration");
    }

    private void addSTFileObjectsInSTFolder()
    {
        if (this.sharedFolderIndex > 0) {
        	STDigestFileHash stHash = new STDigestFileHash(); 
        	STFolder folder = ((STFolder)(this.sharedFolderList.get(this.sharedFolderIndex - 1)));
            STFileName[] filesToAdd = new STFileName[this.fileIndex];
            for (int i = 0; i < filesToAdd.length; i++) {
                filesToAdd[i] = (STFileName)this.fileList.get(i);
                String fullFilename = folder.getFolderName() + "\\" + filesToAdd[i].getFileName();
                /* DEPRECATED
                try {
                	FileInputStream fStream = new FileInputStream(fullFilename);
                	filesToAdd[i].setBytes(fStream.available());
                	filesToAdd[i].setFileHash(stHash.getDigest(stHash.SHA1_DIGEST, fullFilename));
                }
                catch (Exception ex) {
                	logger.error(ex.getMessage());
                	filesToAdd[i].setBytes(-1);
                }
                */                
            }
            // keeping the correct list of files
            ((STFolder)(this.sharedFolderList.get(this.sharedFolderIndex - 1))).setFiles(filesToAdd);
            // preparing the files list for the next folder parsing
            this.fileIndex = 0;
            this.fileList.clear();
        }
    }
    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("configuration")) {
            //create a new instance of configuration
            tmpConfiguration = new STConfiguration();            
        }
        else if (qName.equalsIgnoreCase("folder")) {
            this.addSTFileObjectsInSTFolder();
            this.sharedFolderIndex++;
            STFolder folder = new STFolder();
            folder.setName(attributes.getValue("name"));
            this.sharedFolderList.add(folder);
            folder.setAccess(attributes.getValue("access"));
            folder.setFriendsList(attributes.getValue("friends"), this.myFriendsList);            
        }        
        else if (qName.equalsIgnoreCase("file")) {
            this.fileIndex++;
            STFileName file = new STFileName();
            file.setFileName(attributes.getValue("name"));            
            this.fileList.add(file);
        }        
        else if (qName.equalsIgnoreCase("webservice")) {
            tmpConfiguration.setWebServiceAccount(attributes.getValue("name"));
            tmpConfiguration.setWebServicePassword(true, attributes.getValue("password"));            
            tmpConfiguration.setMaxSearchFriendsLimit(attributes.getValue("maxFriendsLimit"));
        }
        //DEPRECATED
        /*
        else if (qName.equalsIgnoreCase("account")) {
            tmpConfiguration.setAccountName(attributes.getValue("name"));
            tmpConfiguration.setAccountServer(attributes.getValue("server"));
        }
        */
        else if (qName.equalsIgnoreCase("listen")) {           
            tmpConfiguration.setListenPort(attributes.getValue("port"));
            tmpConfiguration.setListenAddress(attributes.getValue("address"));
            tmpConfiguration.setConnTimeout(attributes.getValue("conn_timeout"));
            tmpConfiguration.setMaxConnections(attributes.getValue("max_connections"));
            tmpConfiguration.setAutoConnect(attributes.getValue("auto_connect"));            
        }
        else if (qName.equalsIgnoreCase("folders")) {           
            tmpConfiguration.setCompleteFolder(attributes.getValue("complete"));
            tmpConfiguration.setInCompleteFolder(attributes.getValue("incomplete"));
        }
/*
        else if (qName.equalsIgnoreCase("ratio")) {           
            tmpConfiguration.setMaxDownload(Integer.parseInt(attributes.getValue("maxDownload")));
            tmpConfiguration.setMaxUpload(Integer.parseInt(attributes.getValue("maxUpload")));
        }
*/        
        else if (qName.equalsIgnoreCase("friend")) {           
            STFriend myFriend = new STFriend(attributes.getValue("name"));
            myFriend.setIPAddress(attributes.getValue("ipaddress"));
            String portNum = attributes.getValue("port");
            Integer port = new Integer(portNum);
            myFriend.setPortNumber(port.intValue());
            myFriend.setFriendId(attributes.getValue("friendid"));            
            this.myFriendsList.add(myFriend);
        }
    }
	
    public void characters(char[] ch, int start, int length) throws SAXException {
        //tempVal = new String(ch,start,length);
    }
	
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(qName.equalsIgnoreCase("configuration")) {
            this.addSTFileObjectsInSTFolder();
            // Propagating all the lists here...
            for (int i = 0; i < this.myFriendsList.size(); i++) {
                this.tmpConfiguration.addFriend(this.myFriendsList.get(i));                
            }
            for (int i = 0; i < this.sharedFolderList.size(); i++) {
                this.tmpConfiguration.addFolder(this.sharedFolderList.get(i));
            }
        }        
    }    
}
