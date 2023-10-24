package com.app.tykhe;

import static com.app.tykhe.misc.SavingRateEnum.savingRate.Weekly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tykhe.home.NoSavingItemsFragment;
import com.app.tykhe.home.SavingItemsFragment;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.SavingItem;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;
import com.app.tykhe.reminder.BiWeeklyRemindersFragment;
import com.app.tykhe.reminder.MonthlyRemindersFragment;
import com.app.tykhe.reminder.WeeklyRemindersFragment;
import com.app.tykhe.services.Restarter;
import com.app.tykhe.services.SavingItemsSpawner;
import com.app.tykhe.viewModels.CurrentSavingsUpdater_ViewModel;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout reminderButton;
    private LinearLayout settingsButton;
    private TextView welcomeTextView;
    private TextView currentSavingsDisplay;
    private TextView interestRateDisplay;
    private TextView contributionTypeDisplay;
    private TextView contributionAmountDisplay;
    private TextView ageDisplay;
    private TextView totalSavingsDisplay;

    private ViewPager2 savingItemsViewPager;

    private boolean firstLoad = true;

    private Repo repo;
    private User user;
    private CurrentSavingsUpdater_ViewModel currentSavingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SavingItemsSpawner spawner = new SavingItemsSpawner();
        Intent spawnerIntent = new Intent(this, spawner.getClass());
        if (!isMyServiceRunning(spawner.getClass())) {
            startService(spawnerIntent);

            //SavingItemsSpawner.getInstance().setReminder(
            //        this.repo.getReminder().getValue().get(0)
            //);

        }

        this.reminderButton = ( LinearLayout) findViewById(R.id.reminderButton);
        this.settingsButton = (LinearLayout) findViewById(R.id.settingsButton);

        this.welcomeTextView = ( TextView ) findViewById( R.id.welcomeMessage );
        this.currentSavingsDisplay = ( TextView ) findViewById(R.id.currentSavingsDisplay);
        this.interestRateDisplay = (TextView) findViewById(R.id.interestRateDisplay);
        this.contributionTypeDisplay = ( TextView) findViewById( R.id.contributionTypeDisplay );
        this.contributionAmountDisplay = (TextView) findViewById(R.id.contributionAmountDisplay );
        this.ageDisplay = (TextView) findViewById(R.id.ageDisplay);
        this.totalSavingsDisplay = ( TextView ) findViewById(R.id.totalSavingsDisplay);

        this.savingItemsViewPager = (ViewPager2) findViewById(R.id.savingItemsViewPager);
        this.savingItemsViewPager.setUserInputEnabled( false );



        this.repo = new Repo( getApplication() );
        this.currentSavingsViewModel = new ViewModelProvider(this).get(CurrentSavingsUpdater_ViewModel.class);;
/*
        SavingItem si = new SavingItem();
        si.SavingItmeDate = "2024/12/12";
        si.SavingItemStatus = 1;
        si.SavingItemAmounts = 1000.0;
        repo.createSavingItem(si);
        */

/*
        MediatorLiveData< Object > repoObserver = new MediatorLiveData<>();
        repoObserver.addSource(this.repo.getUser(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                user = users.get(0);


                double totalReocurringSavings = 0;
                switch( user.savingRate ) {
                    case Weekly:
                        totalReocurringSavings = ((user.lengthOfInvestment - user.age ) * 52) * user.contributionAmount;
                        break;
                    case Biweekly:
                        totalReocurringSavings = (( user.lengthOfInvestment - user.age ) * ( 12 * 2 )) * user.contributionAmount;
                        break;
                    case Monthly:
                        totalReocurringSavings = (( user.lengthOfInvestment - user.age ) * ( 12 )) * user.contributionAmount;
                        break;
                }
                double estimatedTotal = user.currentSavings + totalReocurringSavings;
                estimatedTotal = ( estimatedTotal *= user.interstRate ) + estimatedTotal;
                totalSavingsDisplay.setText( String.valueOf( new DecimalFormat("#,##0.###").format( estimatedTotal ) ) );

                setView();
            }
        });
        repoObserver.addSource(this.repo.getAllSavingItems(), new Observer<List<SavingItem>>() {
            @Override
            public void onChanged(List<SavingItem> savingItems) {
                if( firstLoad ){
                    if (savingItems.isEmpty()) {
                        savingItemsViewPager.setCurrentItem(0);
                    } else {
                        savingItemsViewPager.setCurrentItem(1);
                    }
                    firstLoad = false;
                }

                double amountToAddToTotal = 0;
                for( int x =  0; x < savingItems.size(); x++ ) {
                    if( savingItems.get(x).SavingItemStatus == 1 ) {
                        amountToAddToTotal += savingItems.get(x).SavingItemAmounts;
                    }
                }
                currentSavingsDisplay.setText( "$" +  new DecimalFormat("#,##0.###").format(user.currentSavings + amountToAddToTotal) );
            }
        });

        repoObserver.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });
*/

        this.repo.getUser().observe( this, user -> {
            try {
                this.user = user.get(0);
                if ( this.user.name == null
                        ||
                     this.user.lengthOfInvestment == null
                ) {
                    throw new IndexOutOfBoundsException();
                }
                this.repo.getAllSavingItems().observe( this, savingItemLists -> {
                    //Log.d( "savingItems", String.valueOf( savingItemLists.get(0).SavingItemAmounts ) );
                        if (savingItemLists.isEmpty()) {
                            savingItemsViewPager.setCurrentItem(1);
                        } else {
                            if( firstLoad ) {
                                savingItemsViewPager.setCurrentItem(0);
                                firstLoad = false;
                            }
                        }


                    double amountToAddToTotal = 0;
                    for( int x =  0; x < savingItemLists.size(); x++ ) {
                        if( savingItemLists.get(x).SavingItemStatus == 0 ) {
                            amountToAddToTotal += savingItemLists.get(x).SavingItemAmounts;
                        }
                    }


                    //Log.d( "asdf", String.valueOf( this.user.currentSavings ) + " " + String.valueOf( amountToAddToTotal));
                    this.currentSavingsDisplay.setText("$" + new DecimalFormat("#,##0.###").format(this.user.currentSavings + amountToAddToTotal));

                    double totalReocurringSavings = 0;
                    switch( this.user.savingRate ) {
                        case Weekly:
                            totalReocurringSavings = (( this.user.lengthOfInvestment - this.user.age ) * 52) * this.user.contributionAmount;
                            break;
                        case Biweekly:
                            totalReocurringSavings = (( this.user.lengthOfInvestment - this.user.age ) * ( 12 * 2 )) * this.user.contributionAmount;
                            break;
                        case Monthly:
                            totalReocurringSavings = (( this.user.lengthOfInvestment - this.user.age ) * ( 12 )) * this.user.contributionAmount;
                            break;
                    }
                    double estimatedTotal = this.user.currentSavings + totalReocurringSavings;
                    double intrestRate = ( estimatedTotal * ( this.user.interstRate/100) );
                    estimatedTotal = estimatedTotal + intrestRate;
                    Log.d( "intrestrate", String.valueOf( intrestRate));
                    this.totalSavingsDisplay.setText(  "$" + String.valueOf( new DecimalFormat("#,##0.###").format( estimatedTotal ) ) );
                });

                this.setView();
            }
            catch ( IndexOutOfBoundsException exp ) {
                Intent onBoardingActivityIntent = new Intent( HomeActivity.this, OnBoardingActivity.class );
                startActivity( onBoardingActivityIntent );
            }
        });

        this.currentSavingsViewModel.getCurrentSavingsObject().observe( this, newNumber -> {
            Log.d( "asdf", String.valueOf( newNumber ));
            this.currentSavingsDisplay.setText( "$" + new DecimalFormat("#,##0.###").format(newNumber) );
        });


        this.reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminderIntent = new Intent( HomeActivity.this, ReminderSettingsActivity.class );
                startActivity(reminderIntent);
            }
        });

        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reminderIntent = new Intent( HomeActivity.this, SettingsActivity.class );
                startActivity(reminderIntent);
            }
        });


    }
    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        //SharedPreferences sp = new Sha
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    public void setView(){

        this.savingItemsViewPager.setAdapter( new SavingItemsPagerAdapter(this, this.user ) );


        this.welcomeTextView.setText( "Welcome, " + this.user.name );
        this.interestRateDisplay.setText( String.valueOf(this.user.interstRate) + "%" );
        this.contributionTypeDisplay.setText( "Savings " + this.user.savingRate );
        this.contributionAmountDisplay.setText( "$" + new DecimalFormat("#,##0.###").format(this.user.contributionAmount)  );
        this.ageDisplay.setText( String.valueOf( this.user.lengthOfInvestment ) );

    }




    private class SavingItemsPagerAdapter extends FragmentStateAdapter {

        private User user;

        public SavingItemsPagerAdapter(@NonNull FragmentActivity fa, User user ) {
            super(fa);
            this.user = user;
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("createFragment", String.valueOf(position));
            switch ( position ) {
                case 0:
                    return SavingItemsFragment.newInstance(this.user);
                case 1:
                    return NoSavingItemsFragment.newInstance(this.user);
                default:
                    return NoSavingItemsFragment.newInstance(this.user);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}