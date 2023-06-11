package com.example.assignelectricbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView urlTextView = findViewById(R.id.urlTextView);
        urlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String url = "https://github.com/Manfidos/ICT602.git";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}