package com.app.tykhe.onBoarding;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialSetYourSavingsFragment extends Fragment {

    private EditText editText_contributions;
    private EditText editText_interestRates;

    private SeekBar seekBar_lengthOfInvestment;
    private TextView lengthOfInvestmentTextView;
    private UserOnBoardingViewModel onBoardingViewModel;

    private ArrayList<LinearLayout> savingRates;
    private ArrayList<LinearLayout> suggestedRates;
    private List<String> allSuggestedAmounts = Arrays.asList(
            "25","75","125",
            "150","225","500",
            "2000","4000","10000"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("from on create view", "");

            this.onBoardingViewModel = ((OnBoardingActivity) getActivity()).getOnBoardingDataSource();

            ViewDataBinding bindingHandle = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_set_you_savings_init, container, false);
            bindingHandle.setVariable(1, this.onBoardingViewModel );

            return bindingHandle.getRoot();

    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ){
        this.editText_contributions = view.findViewById(R.id.input_contributionAmount);
        this.editText_interestRates = view.findViewById(R.id.input_interestRate);

        this.seekBar_lengthOfInvestment = view.findViewById(R.id.input_durationSeekBar);
        this.lengthOfInvestmentTextView = view.findViewById(R.id.lengthOfInvestmentSelected);

        savingRates = new ArrayList<>();
        savingRates.add( view.findViewById(R.id.weeklyButton) );
        savingRates.add( view.findViewById(R.id.biWeeklyButton) );
        savingRates.add( view.findViewById(R.id.monthlyButton ));

        suggestedRates = new ArrayList<>();
        suggestedRates.add( view.findViewById(R.id.firstSuggestion) );
        suggestedRates.add( view.findViewById(R.id.secondSuggestion) );
        suggestedRates.add( view.findViewById(R.id.thirdSuggestion) );

        this.setAllSavingsRates(1);
        this.setSuggestedRates(1);
        this.setSelectedSuggestedAmount(1);
        this.setViewModalAndUISuggestedAmountData(1);

        savingRates.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBoardingViewModel.selectWeekly();
                setAllSavingsRates( 0 );

                setSuggestedRates( 0 );
                setSelectedSuggestedAmount( 1 );
                setViewModalAndUISuggestedAmountData(1);
            }
        });
        savingRates.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBoardingViewModel.selectBiWeekly();
                setAllSavingsRates( 1 );

                setSuggestedRates( 1 );
                setSelectedSuggestedAmount( 1 );
                setViewModalAndUISuggestedAmountData(1);
            }
        });
        savingRates.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBoardingViewModel.selectMonthly();
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


        this.editText_contributions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onBoardingViewModel.setContributionAmount( editable.toString() );
                Log.d( "valuetest", editable.toString() );
                if(
                        !allSuggestedAmounts.contains( editable.toString()  )
                ) {
                    setSelectedSuggestedAmount(-1);
                }
                else if( allSuggestedAmounts.contains( editable.toString() ) ) {
                    setSelectedSuggestedAmount( true , editable.toString());
                }
            }
        });

        this.editText_interestRates.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onBoardingViewModel.setInterestRate( editable.toString() );
            }
        });

        this.seekBar_lengthOfInvestment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onBoardingViewModel.setLengthOfInvestment( i );
                Log.d( "lengthOfInvestmentValue", String.valueOf(i) );
                lengthOfInvestmentTextView.setText( String.valueOf(i) );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setAllSavingsRates( Integer item ){
        this.savingRates.get( item ).setBackground( getResources().getDrawable(R.drawable.main_button_black) );
        TextView initialSavingRateText = (TextView) savingRates.get(item).getChildAt(0);
        initialSavingRateText.setTextColor(Color.WHITE);
        for( Integer x = 0; x < this.savingRates.size() ; x++  ) {
            if( x != item ) {
                this.savingRates.get(x).setBackground( getResources().getDrawable(R.drawable.main_button_white) );
                TextView initialSavingRateTextUnSet = (TextView) savingRates.get(x).getChildAt(0);
                initialSavingRateTextUnSet.setTextColor(Color.BLACK);
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
            this.onBoardingViewModel.setContributionAmount(  initialSuggestedSavingRateText.getText().toString() );
            for( Integer x = 0; x < this.suggestedRates.size() ; x++  ) {
                if( x != item ) {
                    this.suggestedRates.get(x).setBackground( getResources().getDrawable(R.drawable.secondary_button_unselected) );
                    TextView initialSuggestedSavingRateTextUnSet = (TextView) suggestedRates.get(x).getChildAt(0);
                    initialSuggestedSavingRateTextUnSet.setTextColor(Color.BLACK);
                }
            }
        }
    }

    private void setSelectedSuggestedAmount(Boolean passedByValue, String value ){
        if( passedByValue ) {
            for( Integer x = 0; x < this.suggestedRates.size(); x++ ) {
                TextView suggestedRateText = (TextView) suggestedRates.get(x).getChildAt( 0 );
                if( suggestedRateText.getText().equals( "$"+value ) ) {
                    this.suggestedRates.get( x ).setBackground( getResources().getDrawable(R.drawable.seconday_button_selected) );
                    TextView initialSuggestedSavingRateText = (TextView) suggestedRates.get(x).getChildAt(0);
                    initialSuggestedSavingRateText.setTextColor(Color.WHITE);
                }
            }
        }
        return;
    }

    private void setViewModalAndUISuggestedAmountData( Integer index ){
        TextView suggestedRateText = (TextView) suggestedRates.get(index).getChildAt( 0 );
        String suggestedAmount = suggestedRateText.getText().toString().replaceAll("[^a-zA-Z0-9]", "");
        onBoardingViewModel.setContributionAmount( suggestedAmount );
        editText_contributions.setText( suggestedAmount );
    }

}
