package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

public class STFileName {
    
    private String fileName;    
    private int bytes;
    private String hash;    
    
    public STFileName()
    {    
    }
    
    public STFileName(String fName) {
        this.fileName = fName;
    }

    public void setBytes(int bytes)
    {
        this.bytes = bytes;
    }

    public int getBytes()
    {
        return this.bytes;
    }

    public void setFileHash(String fHash)
    {
        this.hash = fHash;
    }

    public String getHash()
    {
        return this.hash;
    }
    
    public void setFileName(String fName)
    {
        this.fileName = fName;
    }

    public String getFileName()
    {
        return this.fileName;
    }
}
