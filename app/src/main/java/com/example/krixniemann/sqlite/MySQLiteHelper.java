package com.example.krixniemann.sqlite; /**
 * Created by krixniemann on 3/31/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
*  This class is responsible for creating the database
*
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Sets a constant string for the table of comments
    public static final String TABLE_COMMENTS = "comments";
    //Sets a constant string for the ID Column
    public static final String COLUMN_ID = "_id";
    //Sets a constant string for the Comment column
    public static final String COLUMN_COMMENT = "comment";
    //Sets a constant string for the Rating column
    public static final String COLUMN_RATING = "rating";

    //Sets a constant string value for the database name
    private static final String DATABASE_NAME = "commments.db";

    //Sets a Database Version constant
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null, " + COLUMN_RATING + "text not null);";

    //Creates the MySQLiteHelper object
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*
     * Specifies actions after database is created
     * @param SQLiteDatabase database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }
    /*
    *  This method will delete all existing data and re-create the table
    *  @param SQLiteDatabase db, int oldVersion, int newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}