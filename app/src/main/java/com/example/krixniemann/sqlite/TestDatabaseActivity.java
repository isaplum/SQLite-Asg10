package com.example.krixniemann.sqlite; /**
 * Created by krixniemann on 3/31/2017.
 */

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krixniemann.sqlite.Comment;
import com.example.krixniemann.sqlite.CommentsDataSource;

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;
    EditText etRating;
    EditText etComment;
    TextView tvComment;
    TextView tvRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        etRating = (EditText) findViewById(R.id.editText);
        tvRating = (TextView) findViewById(R.id.viewText);
        etComment = (EditText) findViewById(R.id.etComment);
        tvComment = (TextView) findViewById(R.id.tvComment);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()) {
            case R.id.add:
                String comments = etComment.getText().toString();
                String ratings = etRating.getText().toString();

                // save the new comment to the database

                comment = datasource.createComment(comments, ratings);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
