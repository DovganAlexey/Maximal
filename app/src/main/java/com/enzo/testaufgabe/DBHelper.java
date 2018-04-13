package com.enzo.testaufgabe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enzo.testaufgabe.models.Person;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by enzo on 12.04.18.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "test_aufgabe";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_SERVER_ID = "server_id";
    private static final String KEY_NICKNAME = "username";
    private static final String KEY_FULL_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_COMPANY_NAME = "company_name";
    private static final String KEY_CATCH_PHRASE = "catchphrase";
    private static final String KEY_COMPANY_BS = "business_services";
    private static final String KEY_FULL_ADDRESS = "full_address";
    private static final String KEY_ZIPCODE = "zipcode";
    private static final String KEY_COORDS = "coordinates";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVER_ID + " TEXT,"
                + KEY_NICKNAME + " TEXT,"
                + KEY_FULL_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_WEBSITE + " TEXT,"
                + KEY_COMPANY_NAME + " TEXT,"
                + KEY_CATCH_PHRASE + " TEXT,"
                + KEY_COMPANY_BS + " TEXT,"
                + KEY_FULL_ADDRESS + " TEXT,"
                + KEY_ZIPCODE + " TEXT,"
                + KEY_COORDS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void dropTable() {
        SQLiteDatabase database = this.getReadableDatabase();
        onUpgrade(database, DATABASE_VERSION, DATABASE_VERSION);
    }

    void addUser(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, person.getId());
        values.put(KEY_NICKNAME, person.getUsername());
        values.put(KEY_FULL_NAME, person.getName());
        values.put(KEY_EMAIL, person.getEmail());
        values.put(KEY_PHONE, person.getPhone());
        values.put(KEY_WEBSITE, person.getWebsite());
        values.put(KEY_COMPANY_NAME, person.getCompany().getCompanyName());
        values.put(KEY_CATCH_PHRASE, person.getCompany().getCatchPhrase());
        values.put(KEY_COMPANY_BS, person.getCompany().getBusinessServices());
        values.put(KEY_FULL_ADDRESS, person.getAddress().getFullAddress());
        values.put(KEY_ZIPCODE, person.getAddress().getZipcode());
        values.put(KEY_COORDS, person.getAddress().getCoordinates());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<Person> getAllUsers() {
        List<Person> personList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12));
                personList.add(person);
            } while (cursor.moveToNext());
        }
        return personList;
    }
}