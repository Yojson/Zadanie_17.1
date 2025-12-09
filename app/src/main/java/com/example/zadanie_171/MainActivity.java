package com.example.zadanie_171;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private TrainingDbHelper dbHelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TrainingDbHelper(this);
        listView = findViewById(R.id.listViewTrainings);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTrainingActivity.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TrainingDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TrainingDbHelper.COLUMN_ID + " DESC"
        );

        String[] from = {
                TrainingDbHelper.COLUMN_NAME,
                TrainingDbHelper.COLUMN_REPS
        };

        int[] to = {
                android.R.id.text1,
                android.R.id.text2
        };

        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                from,
                to,
                0
        );

        listView.setAdapter(adapter);
    }
}
