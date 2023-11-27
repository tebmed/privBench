package com.stringlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class SmsService {

    public static List<String> getSmsTitles(Context context) {
        List<String> smsTitles = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String smsData = cursor.getString(cursor.getColumnIndex("body"));
                String smsTitle = smsData.split("\n", 2)[0]; // Assuming first line of SMS body as title
                smsTitles.add(smsTitle);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return smsTitles;
    }
}
