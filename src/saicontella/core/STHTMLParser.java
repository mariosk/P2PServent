package saicontella.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.text.html.HTML.Tag;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class STHTMLParser extends ParserCallback{

    private static Log logger = LogFactory.getLog("saicontella.core.STHTMLParser");
    private ImageIcon imageFetched;
    private String linkURL;
    private boolean foundFirstImage;
    
    public void runCallback(String urlString) {
        try {            
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            String contentType = connection.getContentType();
            if (!contentType.equalsIgnoreCase("text/html") && contentType.startsWith("image/")) {
                this.imageFetched = new ImageIcon(new URL(urlString));
                this.linkURL = "";
                return;
            }            
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            HTMLEditorKit.Parser parser = new ParserDelegator();
            HTMLEditorKit.ParserCallback callback = this;
            parser.parse(br, callback, true);            
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }        
    }
    
    public STHTMLParser() {
        foundFirstImage = false;
    }

    public ImageIcon getImageIcon() {
        return this.imageFetched;
    }

    public String getLinkURL() {
        return this.linkURL;
    }

    public void handleSimpleTag(Tag t, MutableAttributeSet a, int p) {        
        try { 
            if (t == Tag.IMG && !foundFirstImage) {
                if (a != null) {
                    String srcString = (String)a.getAttribute(HTML.Attribute.SRC);
                    this.imageFetched = new ImageIcon(new URL(srcString));
                    foundFirstImage = true;
                }                
            }
        } catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public void handleStartTag(Tag t, MutableAttributeSet a, int p){
        if (t == Tag.A && !foundFirstImage) {
            if (a != null) {
                this.linkURL = (String)a.getAttribute(HTML.Attribute.HREF);
            }        
        }
    }

    public void handleText(char[] t, int p){
    }

    public void handleEndTag(Tag t, int p){
    }

    public void handleError(String t, int p){
    }
}