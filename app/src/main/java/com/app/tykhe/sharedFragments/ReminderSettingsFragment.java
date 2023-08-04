package com.app.tykhe.sharedFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tykhe.R;
import com.app.tykhe.misc.SavingRateEnum;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReminderSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private SavingRateEnum reminderType;
    private Integer dayOfWeek_weekly;
    private Integer dayOfWeek_biweekly;
    private Integer dayOfMonth;
    private String notificationTime;
    private boolean reminderActive;


    public ReminderSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReminderSettingsFragment newInstance(String param1, String param2) {
        ReminderSettingsFragment fragment = new ReminderSettingsFragment();
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
        return inflater.inflate(R.layout.fragment_reminder_settings, container, false);
    }
}