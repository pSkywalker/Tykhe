package com.app.tykhe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView privacyPolicyLink;

    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // You can also show a message or take any other action here
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        this.backButton = (ImageView) findViewById(R.id.backButton);
        this.privacyPolicyLink = ( TextView) findViewById(R.id.link);

        this.privacyPolicyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://socialfeed-d4df9.firebaseapp.com/"));
                startActivity(intent);
            }
        });
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( PrivacyPolicyActivity.this, SettingsActivity.class);
                startActivity(updateIntent);
            }
        });

    }
}