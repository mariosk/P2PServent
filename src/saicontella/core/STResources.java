package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class STResources {
    
    private static ResourceBundle stResources = null;
    private static Log logger = LogFactory.getLog("saicontella/core/STResources");

    static
    {
        try
        {
            stResources = ResourceBundle.getBundle("saicontella.core.resources.saicontella", Locale.getDefault());
        }
        catch (MissingResourceException mre)
        {
            logger.error("saicontella/core/resources/saicontella.properties not found");
            System.exit(1);
        }
    }

    public static String getStr(String nm)
    {
        try
        {
            return stResources.getString(nm);
        }
        catch (MissingResourceException mre)
        {
            logger.error(mre.toString());
            return null;
        }
    }
}
