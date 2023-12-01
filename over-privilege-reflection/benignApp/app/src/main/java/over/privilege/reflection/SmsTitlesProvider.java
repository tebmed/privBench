package over.privilege.reflection;

import android.content.pm.PackageManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

public class SmsTitlesProvider extends ContentProvider {

    // Définir l'URI du Content Provider
    public static final Uri CONTENT_URI = Uri.parse("content://com.benignapp.smstitlesprovider");

    @Override
    public boolean onCreate() {
        return true;
    }

    // Méthode pour gérer les requêtes de données
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Récupérer les SMS et retourner les titres
        if (getContext().checkCallingOrSelfPermission("android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            return getContext().getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, new String[]{"_id", "body AS title"}, null, null, sortOrder);
        } else {
            // Si la permission n'est pas accordée, retourner null ou lever une SecurityException
            return null;
        }
    }

    // Les autres méthodes nécessaires pour un Content Provider
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.com.benignapp.sms";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Insertion not supported");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Deletion not supported");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Update not supported");
    }
}
