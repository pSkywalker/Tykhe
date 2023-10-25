package com.app.tykhe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ContactUsActivity extends AppCompatActivity {


    private ImageView backButton;

    private EditText emailEditText;
    private Button sendEmail;



    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        this.backButton = ( ImageView) findViewById(R.id.backButton);
        //this.emailEditText = (EditText) findViewById(R.id.emailEditText );
        this.sendEmail = ( Button ) findViewById(R.id.button_sendEmail);

    /*
        this.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = editable.toString();
            }
        });
    */
        this.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail( email );
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( ContactUsActivity.this, SettingsActivity.class);
                startActivity(updateIntent);
            }
        });

    }

    private void sendEmail ( String msg ){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        // Set the "mailto" data to the email recipients
        emailIntent.setData(Uri.parse("mailto:boraniev@outlook.com"));

        // Set the subject of the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tykhe - From contact us page");

        // Set the message of the email
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

        // Check if there's an email client available to handle this intent
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}