package com.app.tykhe.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.onBoarding.InitialAccountInfoSetupFragment;
import com.app.tykhe.onBoarding.InitialSetYourSavingsFragment;
import com.app.tykhe.onBoarding.WelcomeFragment;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

public class ReminderSettingsActivity extends AppCompatActivity {
    /*
    *
    * need to add to onboarding form
    *   if fragment ->
    *
    *
    * ViewPager for each element of saving rate
    *
    * db pull at init of activity
    *
    * */
    private Repo repo;
    private Reminder reminder = null;

    private LinearLayout savingRateChoser;
    private TextView savingRateChosenItem;

    private FragmentStateAdapter pageAdapter;
    private ViewPager2 typeOfSavingFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);

        this.repo = new Repo(getApplication());

        this.savingRateChoser = (LinearLayout) findViewById(R.id.savingRateWrapper);
        this.savingRateChosenItem = (TextView) findViewById(R.id.chosenSavingRate);

        this.typeOfSavingFlow = (ViewPager2) findViewById(R.id.typeOfSavingFlow);

        this.typeOfSavingFlow.setUserInputEnabled(( false ));
        this.pageAdapter = new ReminderSettingsActivity.ReminderTypePagerAdapter(this);
        this.typeOfSavingFlow.setAdapter( this.pageAdapter );


        repo.getReminder().observe( this, value -> {
            if( !value.isEmpty() ) {
                this.reminder = value.get(0);
                if( this.reminder.chosenType.equals("weekly") ) {

                    Log.d("asdf", this.reminder.chosenType);
                    typeOfSavingFlow.setCurrentItem(1);
                    savingRateChosenItem.setText(R.string.weekly);
                }
                else if( this.reminder.chosenType.equals("biweekly") ) {
                    typeOfSavingFlow.setCurrentItem(2);
                    savingRateChosenItem.setText(R.string.biWeekly);
                }
                else if( this.reminder.chosenType.equals("Monthly")) {
                    typeOfSavingFlow.setCurrentItem(3);
                    savingRateChosenItem.setText(R.string.monthly);
                }
            }
            else {
                repo.createReminder();
                typeOfSavingFlow.setCurrentItem(1);
                savingRateChosenItem.setText(R.string.weekly);
            }
        } );


        this.savingRateChoser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu( view );
            }
        });

    }




    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.saving_rate_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_weekly:
                        savingRateChosenItem.setText(R.string.weekly);
                        typeOfSavingFlow.setCurrentItem(1);
                        return true;
                    case R.id.menu_item_bi_weekly:
                        savingRateChosenItem.setText(R.string.biWeekly);
                        typeOfSavingFlow.setCurrentItem(2);
                        return true;
                    case R.id.menu_item_monthly:
                        savingRateChosenItem.setText(R.string.monthly);
                        typeOfSavingFlow.setCurrentItem(3);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }








    private class ReminderTypePagerAdapter extends FragmentStateAdapter {

        private UserOnBoardingViewModel viewModel;

        public ReminderTypePagerAdapter(@NonNull FragmentActivity fa/*, UserOnBoardingViewModel vm*/) {
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
                    return new WeeklyRemindersFragment( );
                case 2:
                    return new BiWeeklyRemindersFragment();
                case 3:
                    return new MonthlyRemindersFragment();
                default:
                    return new WeeklyRemindersFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}