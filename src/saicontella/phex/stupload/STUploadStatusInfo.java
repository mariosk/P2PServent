package saicontella.phex.stupload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * May 2008
 */

import phex.upload.UploadStatus;
import saicontella.core.STLocalizer;

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
                return STLocalizer.getString( "UploadStatus_AcceptingRequest" );
            case HANDSHAKE:
                return STLocalizer.getString( "UploadStatus_Handshake" );
            case QUEUED:
                return STLocalizer.getString( "UploadStatus_Queued" );
            case UPLOADING_THEX:
                return STLocalizer.getString( "UploadStatus_UploadingThex" );
            case UPLOADING_DATA:
                return STLocalizer.getString( "UploadStatus_UploadingData" );
            case COMPLETED:
                return STLocalizer.getString( "UploadStatus_Completed" );
            case ABORTED:
                return STLocalizer.getString( "UploadStatus_Aborted" );
            default:
                Object[] arguments = new Object[1];
                arguments[0] = status;
                return STLocalizer.getFormatedString( "UnrecognizedStatus",
                    arguments );
        }
    }
}