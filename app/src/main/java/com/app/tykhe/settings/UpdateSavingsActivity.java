package com.app.tykhe.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateSavingsActivity extends AppCompatActivity {
    private Repo repo;

    private User user;

    private ImageView backButton;

    private EditText amountToSaveInput;
    private EditText interestRateInput;

    private TextView ageToSaveTextView;
    private SeekBar ageToSaveToInput;

    private Button updateSettingsButton;


    private ArrayList<LinearLayout> savingRates;
    private ArrayList<LinearLayout> suggestedRates;
    private List<String> allSuggestedAmounts = Arrays.asList(
            "25","75","125",
            "150","225","500",
            "2000","4000","10000"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_savings);

        this.user = new User();

        this.repo = new Repo(getApplication());

        repo.getUser().observe( this, value -> {
            if( !value.isEmpty() ) {
                this.user = value.get(0);
                Log.d("user_", this.user.savingRate.toString());

                this.setAllSavingsRates(user.savingRate.ordinal());
                this.setSuggestedRates(user.savingRate.ordinal());

                this.amountToSaveInput.setText( String.valueOf( user.contributionAmount ));
                this.interestRateInput.setText( String.valueOf( user.interstRate ));
                this.ageToSaveToInput.setProgress(user.lengthOfInvestment);
                this.ageToSaveTextView.setText(String.valueOf(user.lengthOfInvestment));
            }
        } );

        savingRates = new ArrayList<>();
        savingRates.add( findViewById(R.id.weeklyButton) );
        savingRates.add( findViewById(R.id.biWeeklyButton) );
        savingRates.add( findViewById(R.id.monthlyButton ));

        suggestedRates = new ArrayList<>();
        suggestedRates.add( findViewById(R.id.firstSuggestion) );
        suggestedRates.add( findViewById(R.id.secondSuggestion) );
        suggestedRates.add( findViewById(R.id.thirdSuggestion) );

        savingRates.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // onBoardingViewModel.selectWeekly();
                setAllSavingsRates( 0 );

                setSuggestedRates( 0 );
                setSelectedSuggestedAmount( 1 );
                setViewModalAndUISuggestedAmountData(1);
            }
        });
        savingRates.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  onBoardingViewModel.selectBiWeekly();
                setAllSavingsRates( 1 );

                setSuggestedRates( 1 );
                setSelectedSuggestedAmount( 1 );
                setViewModalAndUISuggestedAmountData(1);
            }
        });
        savingRates.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // onBoardingViewModel.selectMonthly();
                setAllSavingsRates( 2 );

                setSuggestedRates( 2 );
                setSelectedSuggestedAmount( 1 );
                setViewModalAndUISuggestedAmountData(1);
            }
        });

        suggestedRates.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewModalAndUISuggestedAmountData(0);
                setSelectedSuggestedAmount( 0 );
            }
        });
        suggestedRates.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewModalAndUISuggestedAmountData(1);
                setSelectedSuggestedAmount( 1 );
            }
        });
        suggestedRates.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewModalAndUISuggestedAmountData(2);
                setSelectedSuggestedAmount( 2 );
            }
        });



        this.backButton = (ImageView) findViewById(R.id.backButton);
        this.amountToSaveInput = (EditText) findViewById(R.id.input_contributionAmount);
        this.interestRateInput = (EditText) findViewById(R.id.input_interestRate);
        this.ageToSaveTextView = (TextView) findViewById(R.id.lengthOfInvestmentSelected);
        this.ageToSaveToInput = (SeekBar) findViewById(R.id.input_durationSeekBar);
        this.updateSettingsButton = (Button) findViewById(R.id.button_update);


        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToSettingsIntent = new Intent( UpdateSavingsActivity.this, SettingsActivity.class );
                startActivity(backToSettingsIntent);
            }
        });


        this.amountToSaveInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.contributionAmount = Double.valueOf ( charSequence.toString() );
                if ( !charSequence.toString().isEmpty() ) {
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

        this.interestRateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                user.interstRate = Double.valueOf(charSequence.toString());
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

        this.ageToSaveToInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                user.lengthOfInvestment = i;
                ageToSaveTextView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        this.updateSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute( () -> {
                    repo.updateSavingsFromSettings(
                            user.savingRate,
                            user.contributionAmount,
                            user.interstRate,
                            user.lengthOfInvestment
                    );
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            Toast.makeText(UpdateSavingsActivity.this, "Savings profile updated", Toast.LENGTH_SHORT).show();
                            Intent backToSettingsIntent = new Intent(UpdateSavingsActivity.this, SettingsActivity.class);
                            startActivity(backToSettingsIntent);
                        }
                    });
                });
            }
        });

    }

    private void setAllSavingsRates( Integer item ){
        this.savingRates.get( item ).setBackground( getResources().getDrawable(R.drawable.main_button_black) );
        TextView initialSavingRateText = (TextView) savingRates.get(item).getChildAt(0);
        initialSavingRateText.setTextColor(Color.WHITE);

        switch ( item ) {
            case 0:
                user.savingRate = SavingRateEnum.savingRate.Weekly;
                Log.d( "itemNumber", "weekly" );
                break;
            case 1:
                user.savingRate = SavingRateEnum.savingRate.Biweekly;
                Log.d( "itemNumber", "biweekly" );
                break;
            case 2:
                user.savingRate = SavingRateEnum.savingRate.Monthly;
                Log.d( "itemNumber", "monthly" );
                break;
        }

        //this.onBoardingViewModel.selectMonthly();

        for( Integer x = 0; x < this.savingRates.size() ; x++  ) {
            if( x != item ) {
                this.savingRates.get(x).setBackground( getResources().getDrawable(R.drawable.main_button_white) );
                TextView initialSavingRateTextUnSet = (TextView) savingRates.get(x).getChildAt(0);
                initialSavingRateTextUnSet.setTextColor(Color.BLACK);
            }
        }
    }

    private void setSelectedSuggestedAmount( Integer item ){
        Log.d( "from selected method", String.valueOf(item) );
        if( item == -1 ) {
            for( Integer x = 0; x < this.suggestedRates.size() ; x++  ) {
                this.suggestedRates.get(x).setBackground( getResources().getDrawable(R.drawable.secondary_button_unselected) );
                TextView initialSuggestedSavingRateTextUnSet = (TextView) suggestedRates.get(x).getChildAt(0);
                initialSuggestedSavingRateTextUnSet.setTextColor(Color.BLACK);
            }
        }
        else {
            this.suggestedRates.get( item ).setBackground( getResources().getDrawable(R.drawable.seconday_button_selected) );
            TextView initialSuggestedSavingRateText = (TextView) suggestedRates.get(item).getChildAt(0);
            initialSuggestedSavingRateText.setTextColor(Color.WHITE);
            user.contributionAmount = Double.valueOf(initialSuggestedSavingRateText.getText().toString().replaceAll("[^A-Za-z0-9]", ""));
            for( Integer x = 0; x < this.suggestedRates.size() ; x++  ) {
                if( x != item ) {
                    this.suggestedRates.get(x).setBackground( getResources().getDrawable(R.drawable.secondary_button_unselected) );
                    TextView initialSuggestedSavingRateTextUnSet = (TextView) suggestedRates.get(x).getChildAt(0);
                    initialSuggestedSavingRateTextUnSet.setTextColor(Color.BLACK);
                }
            }
        }
    }
    private void setSuggestedRates( Integer savingRate ){
        TextView firstSuggested;
        TextView secondSuggested;
        TextView thirdSuggested;

        switch ( savingRate ) {
            case 0:
                firstSuggested = (TextView) this.suggestedRates.get(0).getChildAt(0);
                firstSuggested.setText( R.string.setup_savings_suggested_25 );

                secondSuggested = (TextView) this.suggestedRates.get(1).getChildAt(0);
                secondSuggested.setText( R.string.setup_savings_suggested_75 );

                thirdSuggested = (TextView) this.suggestedRates.get(2).getChildAt(0);
                thirdSuggested.setText( R.string.setup_savings_suggested_125 );
                break;
            case 1:
                firstSuggested = (TextView) this.suggestedRates.get(0).getChildAt(0);
                firstSuggested.setText( R.string.setup_savings_suggested_150 );

                secondSuggested = (TextView) this.suggestedRates.get(1).getChildAt(0);
                secondSuggested.setText( R.string.setup_savings_suggested_225 );

                thirdSuggested = (TextView) this.suggestedRates.get(2).getChildAt(0);
                thirdSuggested.setText( R.string.setup_savings_suggested_500 );
                break;
            case 2:
                firstSuggested = (TextView) this.suggestedRates.get(0).getChildAt(0);
                firstSuggested.setText( R.string.setup_savings_suggested_2000 );

                secondSuggested = (TextView) this.suggestedRates.get(1).getChildAt(0);
                secondSuggested.setText( R.string.setup_savings_suggested_4000 );

                thirdSuggested = (TextView) this.suggestedRates.get(2).getChildAt(0);
                thirdSuggested.setText( R.string.setup_savings_suggested_10000 );
                break;
        }
    }

    private void setViewModalAndUISuggestedAmountData( Integer index ){
        TextView suggestedRateText = (TextView) suggestedRates.get(index).getChildAt( 0 );
        String suggestedAmount = suggestedRateText.getText().toString().replaceAll("[^a-zA-Z0-9]", "");
        user.contributionAmount = Double.valueOf(suggestedAmount);
        amountToSaveInput.setText( suggestedAmount );
    }

    public void setClickableUpdateButton( boolean value ){
        this.updateSettingsButton.setClickable(value);
    }

}