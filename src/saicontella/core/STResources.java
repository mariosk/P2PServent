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
import java.text.DateFormat;

public class STResources {
    
    private static ResourceBundle appResources = null;
    private static Log logger = LogFactory.getLog("saicontella.core.STResources");

    static
    {
        try
        {
            appResources = ResourceBundle.getBundle("saicontella.core.resources.saicontella", Locale.getDefault());
            // Print available locales
            /*
            Locale list [] = DateFormat.getAvailableLocales ();
            System.out.println ("====== System available locales :======== ");
            for (int i = 0; i <list.length; i++) {
              System.out.println(list[i].toString() + "\t" + list[i].getDisplayName());
            }
            */
        }
        catch (MissingResourceException mre)
        {
            logger.error("saicontella/core/resources/saicontella.properties not found");
            System.exit(1);
        }
    }

    public static String getAppStr(String nm)
    {
        try
        {
            return appResources.getString(nm);
        }
        catch (MissingResourceException mre)
        {
            logger.error(mre.toString());
            return null;
        }
    }
}
