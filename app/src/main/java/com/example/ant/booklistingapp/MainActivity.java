package com.example.ant.booklistingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editText.getText().toString();
                Log.i(LOG_TAG, keyword);
                String searchUrl = BOOK_REQUEST_URL + keyword;
                Intent showResultsIntent = new Intent(MainActivity.this, DisplayResults.class);
                showResultsIntent.putExtra("url", searchUrl);
                startActivity(showResultsIntent);
            }
        });

    }

//    private void showResults(String requestUrl) {
//        Intent showResultsIntent = new Intent(MainActivity.this, DisplayResults.class);
//        showResultsIntent.putExtra("url", requestUrl);
//        startActivity(showResultsIntent);
//    }

}
