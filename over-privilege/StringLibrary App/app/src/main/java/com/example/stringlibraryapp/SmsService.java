package com.example.stringlibraryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class SmsService {

    public static List<String> getSmsTitles(Context context) {
        List<String> smsTitles = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(smsUri, new String[]{"_id", "address", "date", "body"}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(1);
                String body = cursor.getString(3);
                smsTitles.add("From: " + address + ", Body: " + body);
            }
            cursor.close();
        }
        return smsTitles;
    }
}
