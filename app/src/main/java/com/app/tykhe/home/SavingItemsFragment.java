package com.app.tykhe.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tykhe.R;
import com.app.tykhe.adapters.SavingItemAdapter;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.SavingItem;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.parcelables.UserParcelable;
import com.app.tykhe.viewModels.CurrentSavingsUpdater_ViewModel;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavingItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavingItemsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private User user;
    private String mParam2;

    private Repo repo;

    private TextView savingItemsSavingRate;
    private RecyclerView savingItemsRecyclerView;
    private SavingItemAdapter rcyAdapter;
    private RecyclerView.LayoutManager rcyLayoutManager;

    private CurrentSavingsUpdater_ViewModel currentSavingsViewModel;

    private boolean initialLoad = true;

    public SavingItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavingItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavingItemsFragment newInstance(User user) {
        SavingItemsFragment fragment = new SavingItemsFragment();
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
        return inflater.inflate(R.layout.fragment_saving_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.repo = new Repo(getActivity().getApplication() );

        this.currentSavingsViewModel = new ViewModelProvider(this).get(CurrentSavingsUpdater_ViewModel.class);


        this.savingItemsSavingRate = view.findViewById(R.id.savingItemsSavingRate);


        this.savingItemsSavingRate.setText( this.user.savingRate.toString() );


        this.savingItemsRecyclerView = ( RecyclerView) view.findViewById(R.id.savingItems);

        rcyAdapter = new SavingItemAdapter(  getActivity().getApplication()) ;
        rcyLayoutManager = new LinearLayoutManager(getActivity());
        this.savingItemsRecyclerView.setAdapter( rcyAdapter );
        this.savingItemsRecyclerView.setLayoutManager( rcyLayoutManager );

        MediatorLiveData< Object > repoObserver = new MediatorLiveData<>();
        repoObserver.addSource(this.repo.getUser(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                rcyAdapter.setUser( user );
            }
        });
        repoObserver.addSource(this.repo.getAllSavingItems(), new Observer<List<SavingItem>>() {
            @Override
            public void onChanged(List<SavingItem> savingItems) {
                if( initialLoad ) {
                    rcyAdapter.updateData( savingItems );
                    rcyAdapter.setViewModel( currentSavingsViewModel );
                    initialLoad = false;
                }
            }
        });

        repoObserver.observe(getActivity(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });




    }
}