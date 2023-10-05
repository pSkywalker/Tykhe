package com.app.tykhe.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.tykhe.R;
import com.app.tykhe.models.Reminder;
import com.app.tykhe.viewModels.ReminderDataViewModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyRemindersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ReminderDataViewModel reminderViewModel;
    private Reminder currentReminderObject;
    private boolean initialLoad = true;

    private DatePickerDialog datePickerDialog;
    private LinearLayout selectMonthlyDate;
    private TextView selectedMonthlyDate;
    private LinearLayout selectTimeOfDay;
    private TextView selectedTimeOfDay;

    public MonthlyRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyRemindersFragment newInstance(String param1, String param2) {
        MonthlyRemindersFragment fragment = new MonthlyRemindersFragment();
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
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.reminderViewModel = new ViewModelProvider(requireActivity()).get(ReminderDataViewModel.class);

        this.selectedMonthlyDate = (TextView) view.findViewById(R.id.selectedMonthlyDate);
        this.selectMonthlyDate = (LinearLayout) view.findViewById(R.id.selectMonthlyDate);

        this.selectTimeOfDay = (LinearLayout) view.findViewById(R.id.selectTimeOfDay);
        this.selectedTimeOfDay = ( TextView ) view.findViewById(R.id.selectedTimeOfDay);

            this.reminderViewModel.getSelectedReminderObject().observe( getViewLifecycleOwner(), reminder -> {
            if( this.initialLoad ) {
                String displayableDate = String.valueOf(reminder.monthlyDay);

                this.currentReminderObject = reminder;
                this.selectedTimeOfDay.setText( reminder.time );
                this.selectedMonthlyDate.setText(addSuffix(reminder, displayableDate));
                this.initialLoad = false;
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


        this.selectMonthlyDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (datePickerDialog == null || !datePickerDialog.isShowing()) {

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = currentReminderObject.monthlyDay;

                    datePickerDialog = new DatePickerDialog(
                            getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    currentReminderObject.monthlyDay = dayOfMonth;
                                    selectedMonthlyDate.setText(addSuffix(currentReminderObject, String.valueOf(dayOfMonth)));
                                    currentReminderObject.savingRate = "monthly";
                                    reminderViewModel.updateReminderObject(currentReminderObject);
                                    datePickerDialog.dismiss();
                                }
                            },
                            year,
                            month,
                            day
                    );
/*
                    datePickerDialog.getDatePicker().setOnDateChangedListener((view_, year_, monthOfYear, dayOfMonth) -> {
                        currentReminderObject.monthlyDay = dayOfMonth;
                        selectedMonthlyDate.setText(addSuffix(currentReminderObject, String.valueOf(dayOfMonth)));
                        datePickerDialog.dismiss();
                    });
 */
                    datePickerDialog.getContext().getTheme().applyStyle(R.style.CustomDatePickerDialogTheme, true);

                    datePickerDialog.setContentView(R.layout.custom_date_picker);
                    datePickerDialog.show();
                }
            }
        });
    }

    public String addSuffix(Reminder reminder , String displayableDate ) {
        if( String.valueOf(reminder.monthlyDay).charAt( String.valueOf(reminder.monthlyDay).length()-1 ) == '1' ) {
            displayableDate += "st";
        }
        else if(String.valueOf(reminder.monthlyDay).charAt( String.valueOf(reminder.monthlyDay).length()-1 ) == '2' ) {
            displayableDate += "nd";
        }
        else if( String.valueOf(reminder.monthlyDay).charAt( String.valueOf(reminder.monthlyDay).length()-1 ) == '3' ) {
            displayableDate += "rd";
        }
        else {
            displayableDate += "th";
        }
        return displayableDate;
    }
}