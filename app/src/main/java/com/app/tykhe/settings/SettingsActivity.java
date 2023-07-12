package com.app.tykhe.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.tykhe.R;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout updateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.updateAccountButton = (LinearLayout) findViewById(R.id.profileItemWrapper);




        this.updateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(SettingsActivity.this, UpdateAccountActivity.class);
                startActivity(updateIntent);
            }
        });

    }
}