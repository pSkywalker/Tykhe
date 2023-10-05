package com.app.tykhe.reminder;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.tykhe.R;
import com.app.tykhe.models.Reminder;
import com.app.tykhe.viewModels.ReminderDataViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BiWeeklyRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiWeeklyRemindersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout selectTimeOfDay;
    private TextView selectedTimeOfDay;

    private ArrayList<TextView> weeklyButtons = new ArrayList<TextView>();
    private ReminderDataViewModel reminderViewModel;
    private Reminder currentReminderObject = new Reminder();
    private boolean initalLoad = true;

    public BiWeeklyRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BiWeeklyRemindersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BiWeeklyRemindersFragment newInstance(String param1, String param2) {
        BiWeeklyRemindersFragment fragment = new BiWeeklyRemindersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bi_weekly_reminders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.weeklyButtons.add( view.findViewById(R.id.sundayText) );
        this.weeklyButtons.add( view.findViewById(R.id.mondayText) );
        this.weeklyButtons.add( view.findViewById(R.id.tuesdayText) );
        this.weeklyButtons.add( view.findViewById(R.id.wednesdayText) );
        this.weeklyButtons.add( view.findViewById(R.id.thursdayText ));
        this.weeklyButtons.add( view.findViewById(R.id.fridayText) );
        this.weeklyButtons.add( view.findViewById(R.id.saturdayText));
        this.weeklyButtons.add( view.findViewById(R.id.sundayTextTwo) );
        this.weeklyButtons.add( view.findViewById(R.id.mondayTextTwo) );
        this.weeklyButtons.add( view.findViewById(R.id.tuesdayTextTwo) );
        this.weeklyButtons.add( view.findViewById(R.id.wednesdayTextTwo) );
        this.weeklyButtons.add( view.findViewById(R.id.thursdayTextTwo ));
        this.weeklyButtons.add( view.findViewById(R.id.fridayTextTwo) );
        this.weeklyButtons.add( view.findViewById(R.id.saturdayTextTwo));

        this.selectTimeOfDay = (LinearLayout) view.findViewById(R.id.selectTimeOfDay);
        this.selectedTimeOfDay = ( TextView ) view.findViewById(R.id.selectedTimeOfDay);

        this.reminderViewModel = new ViewModelProvider(requireActivity()).get(ReminderDataViewModel.class);
        this.reminderViewModel.getSelectedReminderObject().observe(getViewLifecycleOwner(), reminder -> {
            if( initalLoad ){
                LinearLayout parent = (LinearLayout) ((ViewGroup) weeklyButtons.get(reminder.weeklyDay).getParent());
                weeklyButtons.get(reminder.weeklyDay).setTextColor(getResources().getColor(R.color.white));
                parent.setBackground(getResources().getDrawable(R.drawable.seconday_button_selected));
                this.currentReminderObject = reminder;
                this.selectedTimeOfDay.setText( reminder.time );
                initalLoad = false;
            }
        });

        this.selectTimeOfDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String amPm;
                                if (i >= 0 && i < 12) {
                                    amPm = " am";
                                } else {
                                    amPm = " pm";
                                }
                                String minutes = "";
                                if( String.valueOf(i1).length() == 1 ) {
                                    minutes = "0"+String.valueOf(i1);
                                }
                                else {
                                    minutes = String.valueOf(i1);
                                }
                                currentReminderObject.time = String.valueOf(i) + ":" + minutes  + amPm;
                                selectedTimeOfDay.setText( currentReminderObject.time );

                                reminderViewModel.updateReminderObject(currentReminderObject);
                            }
                        }, // OnTimeSetListener callback
                        /*
                        Integer.valueOf(currentReminderObject.time.substring(
                                0,
                                currentReminderObject.time.indexOf(':')
                        )),
                        Integer.valueOf(currentReminderObject.time.substring(
                                currentReminderObject.time.indexOf(':'),
                                currentReminderObject.time.indexOf(' ')
                        ))
                        */
                        1,
                        0

                        ,
                        false // 24-hour format
                );

                timePickerDialog.getContext().getTheme().applyStyle(R.style.CustomDatePickerDialogTheme, true);
                timePickerDialog.show();
            }
        });


        for( Integer x = 0; x < this.weeklyButtons.size(); x++ ) {
            final Integer weeklyItemSelected = x;
            LinearLayout parent = (LinearLayout)( (ViewGroup) weeklyButtons.get(x).getParent() );
            parent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    for( Integer y = 0; y < weeklyButtons.size(); y++ ) {
                        weeklyButtons.get(y).setTextColor(getResources().getColor(R.color.black));
                        LinearLayout parent = (LinearLayout)( (ViewGroup) weeklyButtons.get(y).getParent() );
                        parent.setBackground(getResources().getDrawable(R.drawable.secondary_button_unselected));
                    }

                    weeklyButtons.get(weeklyItemSelected).setTextColor(getResources().getColor(R.color.white));
                    parent.setBackground(getResources().getDrawable(R.drawable.seconday_button_selected));

                    currentReminderObject.biWeeklyDay = weeklyItemSelected;
                    currentReminderObject.savingRate = "biweekly";
                    reminderViewModel.updateReminderObject(currentReminderObject);
                }
            });

        }
    }
}