package com.example.android.booksearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        Button search = (Button) findViewById(R.id.search);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText books = (EditText) findViewById(R.id.book);
                Editable searchKey = books.getText();
                if (searchKey.toString().isEmpty()) {
                    Toast.makeText(BookSearchActivity.this, "Please enter keyword to search", Toast.LENGTH_LONG).show();
                } else {
                    String searchVolume = searchKey.toString();
                    searchVolume = searchVolume.trim().replaceAll("\\s+", "+");

                Intent intent = new Intent(BookSearchActivity.this, BookList.class);
                intent.putExtra("bookSearch",searchVolume);
                startActivity(intent);
            }
        }
        }
        );}
}




