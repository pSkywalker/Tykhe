package com.app.tykhe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;
import com.app.tykhe.reminder.BiWeeklyRemindersFragment;
import com.app.tykhe.reminder.MonthlyRemindersFragment;
import com.app.tykhe.reminder.WeeklyRemindersFragment;
import com.app.tykhe.viewModels.ReminderDataViewModel;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;
import com.suke.widget.SwitchButton;

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
    private User user;

    private ReminderDataViewModel reminderViewModel;


    private ImageView backButton;

    private LinearLayout savingRateChoser;
    private TextView savingRateChosenItem;

    private FragmentStateAdapter pageAdapter;
    private ViewPager2 typeOfSavingFlow;

    private TextView timeOfReminderTextView;
    private TextView freqText;
    private SwitchButton reminderStatusToggle;

    private Button saveChangesButton;
    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        // You can also show a message or take any other action here
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);

        this.repo = new Repo(getApplication());

        this.reminderViewModel = new ViewModelProvider(this).get(ReminderDataViewModel.class);

        this.savingRateChoser = (LinearLayout) findViewById(R.id.savingRateWrapper);
        this.savingRateChosenItem = (TextView) findViewById(R.id.chosenSavingRate);

        this.backButton = (ImageView) findViewById(R.id.backButton);
        this.typeOfSavingFlow = (ViewPager2) findViewById(R.id.typeOfSavingFlow);

        this.typeOfSavingFlow.setUserInputEnabled(( false ));
        this.pageAdapter = new ReminderSettingsActivity.ReminderTypePagerAdapter(this);
        this.typeOfSavingFlow.setAdapter( this.pageAdapter );

        this.timeOfReminderTextView = (TextView) findViewById(R.id.timeOfReminderTextView);
        //this.reminderStatusToggle = (com.suke.widget.SwitchButton) findViewById(R.id.reminderStatusToggle);
        this.freqText = ( TextView) findViewById(R.id.freqText);

        this.saveChangesButton = (Button) findViewById(R.id.saveChangesButton);

        repo.getReminder().observe( this, value -> {
            if( this.reminder == null ) {
                this.reminder = value.get(0);
               // Log.d( "asdf", String.valueOf( this.reminder.pk ) );
                com.app.tykhe.models.Reminder tempReminderObject = new com.app.tykhe.models.Reminder();
                tempReminderObject.setSelfFromEntity( this.reminder );
                reminderViewModel.updateReminderObject( tempReminderObject );
                //this.setView();
            }
        });
        repo.getUser().observe(this, user -> {
            if( !user.isEmpty() ){
                user = user;
            }
        });

        reminderViewModel.getSelectedReminderObject().observe( this, value -> {

            if( true ) {
                // this.reminder = value.get(0);
                this.reminder = new Reminder();
                com.app.tykhe.models.Reminder reminderModel = new com.app.tykhe.models.Reminder();
                this.reminder.chosenType = value.savingRate;
                this.reminder.status = value.status;
                this.reminder.weeklyChoonenDay = value.weeklyDay;
                this.reminder.biweeklyChosenDay = value.biWeeklyDay;
                this.reminder.monthlyChosenDay = value.monthlyDay;
                this.reminder.time = value.time;

                this.setView();
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
        /*
        this.reminderStatusToggle.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                reminder.status = isChecked;
            }
        });
        */
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent( ReminderSettingsActivity.this, HomeActivity.class);
                startActivity(updateIntent);
            }
        });


        this.saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repo.updateReminder(reminder);

                SavingRateEnum.savingRate newSavingRate = SavingRateEnum.savingRate.Biweekly;
                /*if( user.savingRate == SavingRateEnum.savingRate.Weekly ) {
                    newSavingRate = SavingRateEnum.savingRate.Weekly;
                }
                else if( user.savingRate == SavingRateEnum.savingRate.Biweekly ) {
                    newSavingRate = SavingRateEnum.savingRate.Biweekly;
                }
                else if( user.savingRate == SavingRateEnum.savingRate.Monthly ) {
                    newSavingRate = SavingRateEnum.savingRate.Monthly;
                }*/

                if( reminder.chosenType.equals( "weekly") ) {
                    newSavingRate = SavingRateEnum.savingRate.Weekly;
                }
                else if( reminder.chosenType.equals("biweekly")|| reminder.chosenType.equals("monthy") ) {
                    newSavingRate = SavingRateEnum.savingRate.Biweekly;
                }
                else if( reminder.chosenType.equals("Monthly") || reminder.chosenType.equals("monthly") ) {
                    newSavingRate = SavingRateEnum.savingRate.Monthly;
                }

                repo.updateSavingRate( newSavingRate );
                Toast.makeText(ReminderSettingsActivity.this, "Reminder updated", Toast.LENGTH_LONG).show();
            }
        });

    }



    public void setView(){
        this.timeOfReminderTextView.setText(this.reminder.time);
//        this.reminderStatusToggle.setChecked(this.reminder.status);
        switch (this.reminder.chosenType) {
            case "weekly":
                this.freqText.setText("frequency: Weekly");
                break;
            case "biweekly":
                this.freqText.setText("frequency: Biweekly");
                break;
            case "Monthly":
                this.freqText.setText("frequency: Monthly");
                break;
        }

        if (this.reminder.chosenType.equals("weekly")) {

            //Log.d("asdf", this.reminder.chosenType);
            typeOfSavingFlow.setCurrentItem(0);
            savingRateChosenItem.setText(R.string.weekly);
        } else if (this.reminder.chosenType.equals("biweekly")) {
            typeOfSavingFlow.setCurrentItem(1);
            savingRateChosenItem.setText(R.string.biWeekly);
        } else if (this.reminder.chosenType.equals("Monthly")) {
            typeOfSavingFlow.setCurrentItem(2);
            savingRateChosenItem.setText(R.string.monthly);
        }

    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.saving_rate_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                com.app.tykhe.models.Reminder tempReminderObject = new com.app.tykhe.models.Reminder();
                switch (item.getItemId()) {
                    case R.id.menu_item_weekly:
                        savingRateChosenItem.setText(R.string.weekly);
                        typeOfSavingFlow.setCurrentItem(0);
                        reminder.chosenType = "weekly";
                        tempReminderObject.setSelfFromEntity( reminder );
                        reminderViewModel.updateReminderObject( tempReminderObject );
                        return true;
                    case R.id.menu_item_bi_weekly:
                        savingRateChosenItem.setText(R.string.biWeekly);
                        typeOfSavingFlow.setCurrentItem(1);
                        reminder.chosenType = "biweekly";
                        tempReminderObject.setSelfFromEntity( reminder );
                        reminderViewModel.updateReminderObject( tempReminderObject );
                        return true;
                    case R.id.menu_item_monthly:
                        savingRateChosenItem.setText(R.string.monthly);
                        typeOfSavingFlow.setCurrentItem(2);
                        reminder.chosenType = "Monthly";
                        tempReminderObject.setSelfFromEntity( reminder );
                        reminderViewModel.updateReminderObject( tempReminderObject );
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
                case 0:
                    return new WeeklyRemindersFragment( );
                case 1:
                    return new BiWeeklyRemindersFragment();
                case 2:
                    Log.d("asdf", "from monthly screen");
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