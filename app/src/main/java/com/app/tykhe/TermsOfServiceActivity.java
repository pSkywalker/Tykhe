package com.app.tykhe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsOfServiceActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView termsOfServiceLink;
    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // You can also show a message or take any other action here
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);

        this.backButton = ( ImageView ) findViewById(R.id.backButton);
        this.termsOfServiceLink = ( TextView) findViewById(R.id.link);

        this.termsOfServiceLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://socialfeed-d4df9.firebaseapp.com/"));
                startActivity(intent);
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( TermsOfServiceActivity.this, SettingsActivity.class);
                startActivity(updateIntent);
            }
        });
    }
}