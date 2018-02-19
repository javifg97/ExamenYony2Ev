package com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javier.fernandez3 on 19/02/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "vehiculos";

    // Contacts table name
    private static final String TABLE_COCHES = "coches";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FABRICADO = "fabricado";
    private static final String KEY_MARCA = "marca";
    private static final String KEY_NOMBRE = "nombre";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COCHES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FABRICADO + " INTEGER,"
                + KEY_MARCA + " TEXT," +KEY_NOMBRE +" TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COCHES);

        // Create tables again
        onCreate(db);
    }

    public void addContact(sqlCoche coche) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FABRICADO, coche.getFabricado()); // Contact Name
        values.put(KEY_MARCA, coche.getMarca()); // Contact Phone Number
        values.put(KEY_NOMBRE, coche.getNombre());


        // Inserting Row
        db.insert(TABLE_COCHES, null, values);
        db.close(); // Closing database connection
    }

    public List<sqlCoche> getAllContacts() {
        List<sqlCoche> contactList = new ArrayList<sqlCoche>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COCHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                sqlCoche coche = new sqlCoche();
                coche.setId(Integer.parseInt(cursor.getString(0)));
                coche.setFabricado(Integer.parseInt(cursor.getString(1)));
                coche.setMarca(cursor.getString(2));
                coche.setNombre(cursor.getString(3));
                coche.setLat(0);
                coche.setLon(0);
                // Adding contact to list
                contactList.add(coche);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}