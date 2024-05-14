package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class veritabani extends SQLiteOpenHelper {

    private static final String Veritabani_adi = "Ogrenciler";
    private static final int Surum = 1;

    public veritabani(Context c) {
        super(c, Veritabani_adi, null, Surum);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS OgrenciBilgi (ad VARCHAR, soyad VARCHAR, yas INT, sehir VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OgrenciBilgi");
        onCreate(db);
    }
}
