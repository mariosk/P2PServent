package saicontella.core;

import java.io.*;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.nio.charset.Charset;

import phex.utils.IOUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is intended to provide localized strings.
 * 
 * <b>How to store localized ressource bundles</b>
 * Phex will look for resource boundles in the classpath that includes the
 * directory $PHEX/lang.<br>It will look for a file called 'language.list'
 * This file should contain a list of translated locales, one per row, in the format
 * language_COUNTRY e.g. de_DE for german Germany. Also its possible to only provide
 * a single language without country definition e.g. de as a definition for all
 * german speeking countries.<br>
 * Translation files for each local should be named e.g. Lang_de_DE.properties or
 * Lang_de.properties<br>
 * <br>
 * <b>Lookup strategy</b>
 * On startup Phex will try to use the locale defined in its configuration file. If
 * nothing is configured it will use the standard platform locale. With the defined
 * locale e.g. de_DE the $PHEX/lang directory and afterwards the classpath
 * phex.resources is searched for a file called Lang_de_DE.properties, then for
 * a file Lang_de.properties and then for a file Lang.properties. All found files
 * are chained for language key lookup in a ResourceBundle.
 * 
 * To display all available locales in the options menu, Phex will use the file
 * $PHEX/lang/language.list and the internal resource
 * phex/resources/language.list for available locale definitions.
 */
public class STLocalizer
{
    private static Map<String, String> langKeyMap;
    private static Locale usedLocale;
    private static List<Locale> availableLocales;
    private static DecimalFormatSymbols decimalFormatSymbols;
    private static NumberFormat integerNumberFormat;
    private static Log logger = LogFactory.getLog("saicontella.core.STLocalizer");
    
    public static void initialize( String localeStr )
    {
        Locale locale;
        if ( localeStr == null || localeStr.length() == 0 ||
            ( localeStr.length() != 2 && localeStr.length() != 5 && localeStr.length() != 8) )            
        {// default to en_US
            locale = Locale.US;
        }
        else
        {
	        String lang = localeStr.substring( 0, 2 );
	        String country = "";
	        if ( localeStr.length() >= 5 )
	        {
	            country = localeStr.substring( 3, 5 );
	        }
            String variant = "";
            if ( localeStr.length() == 8 )
            {
                variant = localeStr.substring( 6, 8 );
            }
	        locale = new Locale( lang, country, variant );
        }
        setUsedLocale( locale );
    }

    public static void setUsedLocale(Locale locale)
    {
        usedLocale = locale;
        buildResourceBundle( locale );
        decimalFormatSymbols = new DecimalFormatSymbols( usedLocale );
        integerNumberFormat = NumberFormat.getIntegerInstance( usedLocale );
    }
    
    public static Locale getUsedLocale()
    {
        return usedLocale;
    }
    
    public static DecimalFormatSymbols getDecimalFormatSymbols()
    {
        return decimalFormatSymbols;
    }
    
    public static NumberFormat getIntegerNumberFormat()
    {
        return integerNumberFormat;
    }

    private static void buildResourceBundle(Locale locale)
    {
        // we need to build up the resource bundles backwards to chain correctly.
        ArrayList<String> fileList = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer( "Lang" );
        fileList.add( buffer.toString() );
        String language = locale.getLanguage();
        if ( language.length() > 0 )
        {
            buffer.append( '_' );
            buffer.append( language );
            fileList.add( buffer.toString() );
            String country = locale.getCountry();
            if ( country.length() > 0 )
            {
                buffer.append( '_' );
                buffer.append( country );
                fileList.add( buffer.toString() );
                String variant = locale.getVariant();
                if ( variant.length() > 0 )
                {
                    buffer.append( '_' );
                    buffer.append( variant );
                    fileList.add( buffer.toString() );
                }
            }
        }
        langKeyMap = new HashMap<String, String>();
        HashMap<String, String> tmpMap = new HashMap<String, String>();
        String resourceName;
        int size = fileList.size();
        for (int i = 0; i < size; i++)
        {
            // 1) phex.resources classpath
            resourceName = "/saicontella/core/resources/" + fileList.get( i )
                + ".properties";
            tmpMap = loadProperties( resourceName );
            if ( tmpMap != null )
            {
                langKeyMap.putAll( tmpMap );
                logger.info( "Loaded language map: " + resourceName + "." );
                return;
            }
            else {
                logger.info( "Could not load language map: " + resourceName + "." );    
            }
            // 2) e.g. $PHEX/ext
            resourceName = "/" + fileList.get( i ) + ".properties";
            tmpMap = loadProperties( resourceName );
            if ( tmpMap != null )
            {
                langKeyMap.putAll( tmpMap );
                logger.info( "Loaded language map: " + resourceName + "." );
                return;
            }
            else {
                logger.info( "Could not load language map: " + resourceName + "." );
            }            
        }
    }

    private static HashMap<String, String> loadProperties(String name)
    {
        InputStream s = null;
        InputStreamReader stream = null;
        BufferedReader reader = null;

        try {
            s = STLocalizer.class.getResourceAsStream( name );
            if ( s == null ) { return null; }
            stream = new InputStreamReader(s,"UTF8");
            // make sure it is buffered
            reader = new BufferedReader(stream);
        }
        catch (Exception e) {

        }
        
        Properties props = new Properties();
        try
        {
            props.load( reader );
            return new HashMap( props );
        }
        catch (IOException exp)
        {
        }
        finally
        {
            IOUtil.closeQuietly( stream );
        }
        return null;
    }

    /**
     * To display all available locales in the options menu, Phex will use the file
     * $PHEX/lang/translations.list and the internal resource
     * phex/resources/translations.list for available locale definitions.
     */
    public static List<Locale> getAvailableLocales()
    {
        if ( availableLocales != null ) { return availableLocales; }
        availableLocales = new ArrayList<Locale>();
        List<Locale> list = loadLocalList( "/language.list" );
        availableLocales.addAll( list );
        list = loadLocalList( "/saicontella/core/resources/language.list" );
        availableLocales.addAll( list );
        return availableLocales;
    }

    private static List<Locale> loadLocalList(String name)
    {
        InputStream stream = STLocalizer.class.getResourceAsStream( name );
        if ( stream == null ) { return Collections.emptyList(); }
        // make sure it is buffered
        try
        {
            BufferedReader reader = new BufferedReader( new InputStreamReader(
                stream, "ISO-8859-1" ) );
            ArrayList<Locale> list = new ArrayList<Locale>();
            String line;
            Locale locale;
            while (true)
            {
                line = reader.readLine();
                if ( line == null )
                {
                    break;
                }
                line = line.trim();
                if ( line.startsWith( "#" ) 
                  || ( line.length() != 2 && line.length() != 5 && line.length() != 8 ) )
                {
                    continue;
                }
                String lang = line.substring( 0, 2 );
                String country = "";
                if ( line.length() >= 5 )
                {
                    country = line.substring( 3, 5 );
                }
                String variant = "";
                if ( line.length() == 8 )
                {
                    variant = line.substring( 6, 8 );
                }
                locale = new Locale( lang, country, variant );
                list.add( locale );
            }
            return list;
        }
        catch (IOException exp)
        {
            logger.error( exp.getMessage() );
        }
        finally
        {
            IOUtil.closeQuietly( stream );
        }
        return Collections.emptyList();
    }

    /**
     * Returns the actual language text out of the resource boundle.
     * If the key is not defined it returns the key itself and prints an
     * error message on system.err.
     */
    public static String getString(String key)
    {
        String value = langKeyMap.get( key );
        if ( value == null )
        {
            logger.error( "Missing language key: " + key );
            value = key;
        }
        return value;
    }

    /**
     * Returns the first character of the actual language text out of the
     * resource boundle. The method can be usefull for getting mnemonics.
     * If the key is not defined it returns the first char of the key itself and
     * prints an error message on system.err.
     */
    public static char getChar(String key)
    {
        String str = getString( key );
        return str.charAt( 0 );
    }

    /**
     * Returns the actual language text out of the resource boundle and formats
     * it accordingly with the given Object array.
     * If the key is not defined it returns the key itself and print an
     * error message on system.err.
     */
    public static String getFormatedString(String key, Object ... obj)
    {
        String value = null;
        
        String lookupValue = langKeyMap.get( key );
        if ( lookupValue != null )
        {
            value = MessageFormat.format( lookupValue, obj );
        }
        else
        {
            logger.info( "Missing language key: " + key );
            value = key;
        }
        return value;
    }
}