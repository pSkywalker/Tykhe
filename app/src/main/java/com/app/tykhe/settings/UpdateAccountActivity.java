package com.app.tykhe.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.User;

public class UpdateAccountActivity extends AppCompatActivity {
    private Repo repo;

    private User user;

    private ImageView backButton;
    private EditText usernameInput;
    private TextView ageDisplay;
    private SeekBar ageInput;
    private Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        this.user = new User();

        this.repo = new Repo(getApplication());

        repo.getUser().observe( this, value -> {
            if( !value.isEmpty() ) {
                this.user = value.get(0);
                Log.d("user_", this.user.name);

                this.usernameInput.setText( this.user.name );
                this.ageDisplay.setText(String.valueOf(this.user.age));
                this.ageInput.setProgress(this.user.age);
            }
        } );

        this.backButton = (ImageView) findViewById(R.id.backButton);
        this.usernameInput = (EditText) findViewById(R.id.accountHoldersName);
        this.ageDisplay = (TextView) findViewById(R.id.currentAgeSelected);
        this.ageInput = (SeekBar) findViewById(R.id.ageSeekBar);

        this.updateButton = (Button) findViewById(R.id.updateButton);
        this.setClickableUpdateButton(false);

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToSettingsIntent = new Intent(UpdateAccountActivity.this, SettingsActivity.class);
                startActivity(backToSettingsIntent);
            }
        });

        this.usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.name = charSequence.toString();
                if( !charSequence.toString().isEmpty() ) {
                    setClickableUpdateButton(true);
                }
                else {
                    setClickableUpdateButton(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.ageInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                user.age = i;
                ageDisplay.setText( String.valueOf(i) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        this.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTask.execute( () -> {
                    repo.updateUserFromSettings(user.name, user.age);
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            Toast.makeText(UpdateAccountActivity.this, "User profile updated", Toast.LENGTH_SHORT).show();
                            Intent backToSettingsIntent = new Intent(UpdateAccountActivity.this, SettingsActivity.class);
                            startActivity(backToSettingsIntent);
                        }
                    });
                });

            }
        });

    }


    public void setClickableUpdateButton( boolean value ){
        this.updateButton.setClickable(value);
    }


}