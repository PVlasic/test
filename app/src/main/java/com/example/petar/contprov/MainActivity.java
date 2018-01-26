package com.example.petar.contprov;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTitle(View view) {

        //---add a book---

        ContentValues values = new ContentValues();
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("isbn", ((EditText)
                findViewById(R.id.txtISBN)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://com.example.ContProv.BookProvider/books"),
                values);
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://com.example.ContProv.BookProvider/books");

        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                BookProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BookProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BookProvider.ISBN)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    public void onClickRetrieveTitlesBL(View view) {
        Uri allTitles = Uri.parse("content://com.example.ContProv.BookProvider/books");
        Cursor c;
        if(android.os.Build.VERSION.SDK_INT < 11)
        {
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        }else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex(BookProvider.TITLE)).substring(0,1).equals("B") || c.getString(c.getColumnIndex(BookProvider.TITLE)).substring(0,1).equals("L") ){
                    Toast.makeText(this,
                            c.getString(c.getColumnIndex(
                                    BookProvider._ID)) + ", " +
                                    c.getString(c.getColumnIndex(
                                            BookProvider.TITLE)) + ", " +
                                    c.getString(c.getColumnIndex(
                                            BookProvider.ISBN)),
                            Toast.LENGTH_SHORT).show();
                }
            }while(c.moveToNext());
        }
    }

    public void deleteISBN(View view) {
        EditText isbn = (EditText) findViewById(R.id.txtISBN);
        String isbntxt = isbn.getText().toString();
        Uri allTitles = Uri.parse(
                "content://com.example.ContProv.BookProvider/books");

        getContentResolver().delete(allTitles, "ISBN = ?",new String[]{isbntxt});

    }
}
