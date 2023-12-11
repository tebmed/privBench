package overPrivilege.vulnerable;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class RequestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.vulnerable.REQUEST_SMS_DATA".equals(intent.getAction())) {
            sendSmsData(context);
        }
    }

    private void sendSmsData(Context context) {
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"),
                new String[]{"_id", "address", "body"}, null, null, null);
        StringBuilder smsBuffer = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                smsBuffer.append("From: ").append(address).append(", Message: ").append(body).append("\n\n");
            } while (cursor.moveToNext());
            cursor.close();
        }

        Intent sendIntent = new Intent("com.maliciousapp.SMS_DATA");
        sendIntent.putExtra("sms_data", smsBuffer.toString());
        context.sendBroadcast(sendIntent);
    }
}
