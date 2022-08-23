package com.app.tykhe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.app.tykhe.models.OnBoarding;
import com.app.tykhe.onBoarding.InitialSetYourSavingsFragment;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;
import com.app.tykhe.onBoarding.InitialAccountInfoSetupFragment;
import com.app.tykhe.onBoarding.WelcomeFragment;

public class OnBoardingActivity extends AppCompatActivity {

    public static int FIRST_PAGE = 0;
    public static int NUM_PAGES = 2;

    private UserOnBoardingViewModel onBoardingDataSource;

    private FragmentStateAdapter pageAdapter;

    private ViewPager2 onBoardingFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        this.onBoardingDataSource = new ViewModelProvider(this).get(UserOnBoardingViewModel.class);
        this.onBoardingFlow = findViewById( R.id.onBoardingFlow );

        OnBoarding onBoardingForm = this.onBoardingDataSource.getOnBoardingForm().getValue();
        this.onBoardingFlow.setUserInputEnabled(( false ));
        this.pageAdapter = new OnBoardingPagerAdapter(this );
        this.onBoardingFlow.setAdapter( this.pageAdapter );

        this.onBoardingDataSource.getOnBoardingForm().observe( this, onBoardingFrom -> {
            if( !onBoardingForm.getCurrentStep().equals( this.onBoardingDataSource.getOnBoardingForm() )) {
                this.onBoardingFlow.setCurrentItem(onBoardingForm.getCurrentStep());
            }
            //Log.d("fromOnBoarding", onBoardingFrom.toString());
        });


    }

    public UserOnBoardingViewModel getOnBoardingDataSource( ){
        return this.onBoardingDataSource;
    }

    private class OnBoardingPagerAdapter extends FragmentStateAdapter {

        public OnBoardingPagerAdapter(@NonNull FragmentActivity fa ) {
            super(fa);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("createFragment", String.valueOf(position));
            switch ( position ) {
                case 1:
                    return new InitialAccountInfoSetupFragment();
                case 2:
                    return new InitialSetYourSavingsFragment();
                default:
                    return new WelcomeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return OnBoardingActivity.NUM_PAGES+1;
        }
    }

}