package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button Kaydet;
    Button Goster;
    Button Sil;
    Button Guncelle;

    EditText ad;
    EditText soyad;
    EditText yas;
    EditText sehir;
    TextView Bilgiler;
    private veritabani v1;
    private String[] sutunlar = {"ad", "soyad", "yas", "sehir"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = new veritabani(this);
        Kaydet = findViewById(R.id.button);
        Goster = findViewById(R.id.button2);
        Sil = findViewById(R.id.button3);
        Guncelle = findViewById(R.id.button4);

        ad = findViewById(R.id.editTextText);
        soyad = findViewById(R.id.editTextText2);
        yas = findViewById(R.id.editTextText3);
        sehir = findViewById(R.id.editTextText4);
        Bilgiler = findViewById(R.id.textView5);

        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KayitEkle(ad.getText().toString(), soyad.getText().toString(), yas.getText().toString(), sehir.getText().toString());
            }
        });

        Goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor crs = KayitGetir();
                KayitGoster(crs);
            }
        });

        Sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KayitSil(ad.getText().toString());
            }
        });

        Guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KayitGuncelle(ad.getText().toString(), soyad.getText().toString(), yas.getText().toString(), sehir.getText().toString());
            }
        });
    }

    private Cursor KayitGetir() {
        SQLiteDatabase db = v1.getWritableDatabase();
        return db.query("OgrenciBilgi", sutunlar, null, null, null, null, null);
    }

    private void KayitGoster(Cursor goster) {
        StringBuilder builder = new StringBuilder();
        while (goster.moveToNext()) {
            String add = goster.getString(goster.getColumnIndex("ad"));
            String soyadd = goster.getString(goster.getColumnIndex("soyad"));
            String yass = goster.getString(goster.getColumnIndex("yas"));
            String sehirr = goster.getString(goster.getColumnIndex("sehir"));
            builder.append("Ad: ").append(add).append("\n");
            builder.append("Soyad: ").append(soyadd).append("\n");
            builder.append("Yaş: ").append(yass).append("\n");
            builder.append("Şehir: ").append(sehirr).append("\n");
            builder.append("----------------").append("\n");
        }
        goster.close();
        Bilgiler.setText(builder.toString());
    }

    private void KayitEkle(String adi, String soyadi, String yasi, String sehri) {
        SQLiteDatabase db = v1.getWritableDatabase();
        try {
            ContentValues veriler = new ContentValues();
            veriler.put("ad", adi);
            veriler.put("soyad", soyadi);
            veriler.put("yas", yasi);
            veriler.put("sehir", sehri);
            db.insertOrThrow("OgrenciBilgi", null, veriler);
        } finally {
            db.close();
        }
    }

    private void KayitSil(String adi) {
        SQLiteDatabase db = v1.getReadableDatabase();
        try {
            db.delete("OgrenciBilgi", "ad=?", new String[]{adi});
        } finally {
            db.close();
        }
    }

    private void KayitGuncelle(String addd, String soyaddd, String yasss, String sehirrr) {
        SQLiteDatabase db = v1.getWritableDatabase();
        try {
            ContentValues cvGuncelle = new ContentValues();
            cvGuncelle.put("soyad", soyaddd);
            cvGuncelle.put("yas", yasss);
            cvGuncelle.put("sehir", sehirrr);
            db.update("OgrenciBilgi", cvGuncelle, "ad=?", new String[]{addd});
        } finally {
            db.close();
        }
    }
}
