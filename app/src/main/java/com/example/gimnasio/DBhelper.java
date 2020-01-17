package com.example.gimnasio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    String tbl_usuarios = "CREATE TABLE tbl_usuarios (\n" +
            "\tid integer PRIMARY KEY AUTOINCREMENT,\n" +
            "\tusuario string,\n" +
            "\tnombre string,\n" +
            "\tapellido string,\n" +
            "\tfechaNacimiento string,\n" +
            "\testatura decimal,\n" +
            "\tclave string\n" +
            ");";
    String tbl_evaluaciones = "CREATE TABLE tbl_evaluaciones (\n" +
            "\tid integer PRIMARY KEY AUTOINCREMENT,\n" +
            "\tfecha string,\n" +
            "\tpeso decimal,\n" +
            "\timc decimal,\n" +
            "\tid_usuarios integer\n" +
            ");";

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbl_evaluaciones);
        db.execSQL(tbl_usuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
