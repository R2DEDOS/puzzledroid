package com.example.puzzlecito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
    Manager database
 */
public class BBDDHelper extends SQLiteOpenHelper {
    // Si cambiamos el esquema de la BBDD es necesario incrementar la versión.
    private static final int DB_VERSION = 2;
    private static final String DATABASE_NAME = "Score_puzzledroid.db";

    BBDDHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BBDDSchema.SQL_DELETE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BBDDSchema.SQL_CREATE);
    }

}
