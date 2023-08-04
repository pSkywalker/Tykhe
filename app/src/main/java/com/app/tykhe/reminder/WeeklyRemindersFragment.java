package com.app.tykhe.reminder;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tykhe.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyRemindersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView sundayButton;
    private TextView mondayButton;
    private TextView tuesdayButton;
    private TextView wednesdayButton;
    private TextView thursdayButton;
    private TextView fridayButton;
    private TextView saturdayButton;




    private ArrayList<TextView> weeklyButtons = new ArrayList<TextView>();


    public WeeklyRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyRemindersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyRemindersFragment newInstance(String param1, String param2) {
        WeeklyRemindersFragment fragment = new WeeklyRemindersFragment();
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
        return inflater.inflate(R.layout.fragment_weekly_reminders, container, false);
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {

        this.weeklyButtons.add( view.findViewById(R.id.sundayText) );
        this.weeklyButtons.add( view.findViewById(R.id.mondayText) );
        this.weeklyButtons.add( view.findViewById(R.id.tuesdayText) );
        this.weeklyButtons.add( view.findViewById(R.id.wednesdayText) );
        this.weeklyButtons.add( view.findViewById(R.id.thursdayText ));
        this.weeklyButtons.add( view.findViewById(R.id.fridayText) );
        this.weeklyButtons.add( view.findViewById(R.id.saturdayText));

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

                }
            });

        }



    }








}