package com.example.puzzlecito;

/*
    Schema Database for SQLite
 */
class BBDDSchema {

    private BBDDSchema() {}

    static final String TABLE = "score";
    static String NAME = "name";
    static String TIME =  "time";

    static final String SQL_CREATE =
            "CREATE TABLE " + BBDDSchema.TABLE + " (" +
                    BBDDSchema.NAME + " TEXT PRIMARY KEY, " +
                    String.valueOf(BBDDSchema.TIME) + " INT)";

    static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE;
}
