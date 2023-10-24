package com.app.tykhe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.RoomDatabase;
import com.app.tykhe.localStorage.dao.UserDao;
import com.app.tykhe.localStorage.entities.User;
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

    private Repo repo;
    private User user = null;

    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // You can also show a message or take any other action here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        this.repo = new Repo(getApplication());
        setContentView(R.layout.activity_on_boarding);


        this.onBoardingDataSource = new ViewModelProvider(this).get(UserOnBoardingViewModel.class);
        this.onBoardingFlow = findViewById( R.id.onBoardingFlow );

        //OnBoarding onBoardingForm = this.onBoardingDataSource.getOnBoardingForm().getValue();
        this.onBoardingFlow.setUserInputEnabled(( false ));
        this.pageAdapter = new OnBoardingPagerAdapter(this, onBoardingDataSource );
        this.onBoardingFlow.setAdapter( this.pageAdapter );

/*
        repo.getUser().observe( this, value -> {
            if( !value.isEmpty() ) {
                this.user = value.get(0);
                Log.d("user_", this.user.name);
            }
        } );
*/
        this.onBoardingDataSource.getOnBoardingForm().observe( this, onBoardingForm -> {


            if( onBoardingForm.getCurrentStep() != this.onBoardingFlow.getCurrentItem() ) {
                Log.d( "currentStep", String.valueOf(onBoardingForm.getCurrentStep()) );
                this.onBoardingFlow.setCurrentItem(onBoardingForm.getCurrentStep());
                switch ( onBoardingForm.getCurrentStep() ) {
                    case 1:
                        Log.d("currentStep", "from case 1");
                        if( this.user == null ) {
                            AsyncTask.execute( () -> {
                                this.repo.createUser();
                            });
                        }
                        break;
                    case 2:
                        Log.d("currentStep", "from case 2");
                        AsyncTask.execute( () -> {
                            this.repo.updateUser(onBoardingForm);
                        });
                        break;
                    case 3:
                        Log.d("currentStep", "from case 3");
                        AsyncTask.execute( () -> {
                            this.repo.updateUser(onBoardingForm);
                            this.repo.createReminder();
                            Intent homeActivityIntent = new Intent( OnBoardingActivity.this, HomeActivity.class );
                            startActivity( homeActivityIntent );
                        });
                        break;
                    case 0:
                        break;
                    default:
                        AsyncTask.execute( () -> {
                            this.repo.updateUser(onBoardingForm);
                            Log.d( "user", this.repo.getUser().getValue().get(0).toString() );
                        });
                        break;
                }

            }
            //Log.d("fromOnBoarding", onBoardingFrom.toString());
        });


    }

    public UserOnBoardingViewModel getOnBoardingDataSource( ){
        return this.onBoardingDataSource;
    }

    private class OnBoardingPagerAdapter extends FragmentStateAdapter {

        private UserOnBoardingViewModel viewModel;

        public OnBoardingPagerAdapter(@NonNull FragmentActivity fa, UserOnBoardingViewModel vm) {
            super(fa);
            //this.viewModel = vm;
            /*
            User user = new User();
            user.name = "asdf";
            user.age  = 26;
            user.user_id = 1;
            this.repo.insert( user );
            */
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