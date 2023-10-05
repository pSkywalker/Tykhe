package com.app.tykhe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.tykhe.settings.UpdateAccountActivity;
import com.app.tykhe.settings.UpdateSavingsActivity;

public class SettingsActivity extends AppCompatActivity {

    private ImageView backButton;

    private LinearLayout updateSavingsButton;
    private LinearLayout updateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.backButton = (ImageView) findViewById(R.id.backButton);

        this.updateSavingsButton = (LinearLayout) findViewById(R.id.savingsEditWrapper);
        this.updateAccountButton = (LinearLayout) findViewById(R.id.profileItemWrapper);


        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( SettingsActivity.this, HomeActivity.class);
                startActivity(updateIntent);
            }
        });

        this.updateSavingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( SettingsActivity.this, UpdateSavingsActivity.class);
                startActivity(updateIntent);
            }
        });
        this.updateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(SettingsActivity.this, UpdateAccountActivity.class);
                startActivity(updateIntent);
            }
        });

    }
}