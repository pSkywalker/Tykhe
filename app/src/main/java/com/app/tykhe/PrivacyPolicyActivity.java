package com.app.tykhe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ImageView backButton;
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

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( PrivacyPolicyActivity.this, SettingsActivity.class);
                startActivity(updateIntent);
            }
        });

    }
}