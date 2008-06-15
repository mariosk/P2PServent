package saicontella.phex.stupload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * May 2008
 */

import phex.upload.UploadStatus;
import phex.utils.Localizer;

public final class STUploadStatusInfo
{
    // dont allow to create instances
    private STUploadStatusInfo()
    {
    }

    /**
     * Returns a localized string for the given status of a download file.
     *
     * @param status the status to get the string representation for.
     * @return the status string representation.
     */
    public static String getUploadStatusString( UploadStatus status )
    {
        switch( status )
        {
            case ACCEPTING_REQUEST:
                return Localizer.getString( "UploadStatus_AcceptingRequest" );
            case HANDSHAKE:
                return Localizer.getString( "UploadStatus_Handshake" );
            case QUEUED:
                return Localizer.getString( "UploadStatus_Queued" );
            case UPLOADING_THEX:
                return Localizer.getString( "UploadStatus_UploadingThex" );
            case UPLOADING_DATA:
                return Localizer.getString( "UploadStatus_UploadingData" );
            case COMPLETED:
                return Localizer.getString( "UploadStatus_Completed" );
            case ABORTED:
                return Localizer.getString( "UploadStatus_Aborted" );
            default:
                Object[] arguments = new Object[1];
                arguments[0] = status;
                return Localizer.getFormatedString( "UnrecognizedStatus",
                    arguments );
        }
    }
}