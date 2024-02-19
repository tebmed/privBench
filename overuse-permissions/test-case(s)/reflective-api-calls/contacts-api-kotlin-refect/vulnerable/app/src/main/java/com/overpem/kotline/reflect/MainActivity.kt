package com.overpem.kotline.reflect

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {

    private val WRITE_CONTACTS_PERMISSION_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextPhoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonCreateContact = findViewById<Button>(R.id.buttonCreateContact)

        buttonCreateContact.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val phoneNumber = editTextPhoneNumber.text.toString().trim()

            if (name.isNotEmpty() && phoneNumber.isNotEmpty()) {
                if (checkContactsPermission()) {
                    createContactUsingReflection(name, phoneNumber)
                } else {
                    requestContactsPermission()
                }
            } else {
                Toast.makeText(this, "Please enter name and phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_CONTACTS),
            WRITE_CONTACTS_PERMISSION_REQUEST
        )
    }

    private fun createContactUsingReflection(name: String, phoneNumber: String) {
        try {
            val rawContactsUri = ContactsContract.RawContacts.CONTENT_URI

            val contentValues = ContentValues().apply {
                putNull(ContactsContract.RawContacts.ACCOUNT_TYPE)
                putNull(ContactsContract.RawContacts.ACCOUNT_NAME)
            }

            val resolver: ContentResolver = contentResolver

            val insertMethod: Method = resolver.javaClass.getMethod(
                "insert",
                Uri::class.java, ContentValues::class.java
            )

            val contactUri: Uri? = insertMethod.invoke(resolver, rawContactsUri, contentValues) as Uri?

            contactUri?.let { uri ->
                val dataNameValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, uri.lastPathSegment)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                }

                val dataPhoneValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, uri.lastPathSegment)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                    put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                }

                val insertDataMethod: Method = resolver.javaClass.getMethod(
                    "insert",
                    Uri::class.java, ContentValues::class.java
                )

                insertDataMethod.invoke(resolver, ContactsContract.Data.CONTENT_URI, dataNameValues)
                insertDataMethod.invoke(resolver, ContactsContract.Data.CONTENT_URI, dataPhoneValues)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
