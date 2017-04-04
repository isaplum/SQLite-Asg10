package com.example.krixniemann.sqlite; /**
 * Created by krixniemann on 3/31/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.krixniemann.sqlite.Comment;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT };

    /*
    * Initializes the database helper
    * @param Context context
     */
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }


  // Initializes the writable database

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //Closes the database helper
    public void close() {
        dbHelper.close();
    }

    /*
    *Creates the comments and stores them in the database
    * @param String comment
     */
    public Comment createComment(String comment, String rating) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    /*
    *Deletes comments
    * @param com.example.krixniemann.sqlite.Comment comment
     */
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("com.example.krixniemann.sqlite.Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /*
    * Gathers collection of comments
     */
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    /*
    * Directs the cursor to the comment
    * @param Cursor cursor
     */
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        comment.setRating(cursor.getString( cursor.getColumnIndex( MySQLiteHelper.COLUMN_RATING)));
        return comment;
    }
}
