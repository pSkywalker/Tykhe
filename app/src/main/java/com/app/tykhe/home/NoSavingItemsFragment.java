package com.app.tykhe.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tykhe.R;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.parcelables.UserParcelable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoSavingItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoSavingItemsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private User user;
    private String mParam2;


    private TextView savingItemsSavingRate;


    public NoSavingItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment noSavingItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoSavingItemsFragment newInstance(User user) {
        NoSavingItemsFragment fragment = new NoSavingItemsFragment();
        Bundle args = new Bundle();
        UserParcelable userP = new UserParcelable( user );
        args.putParcelable(ARG_PARAM1, userP);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UserParcelable userP = getArguments().getParcelable(ARG_PARAM1);
            user = userP.getUserEntityFromParcelable();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_saving_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.savingItemsSavingRate = view.findViewById(R.id.savingItemsSavingRate);


        this.savingItemsSavingRate.setText( this.user.savingRate.toString() );

    }
}