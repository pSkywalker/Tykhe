package com.app.tykhe.adapters;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.SavingItem;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.CurrentSavingsWrapper;
import com.app.tykhe.viewModels.CurrentSavingsUpdater_ViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SavingItemAdapter extends RecyclerView.Adapter<SavingItemAdapter.SavingItemViewHolder> {


    public List<SavingItem> savingItemList;
    private Repo repo;
    private Application app;
    private User user;
    private CurrentSavingsUpdater_ViewModel currentSavingsUpdater_viewModel;
    Comparator<SavingItem> dateComparator;
    SimpleDateFormat dateFormat;

    private FirebaseAnalytics fba;

    public SavingItemAdapter( Application application ){
        this.savingItemList = new ArrayList<SavingItem>();
        this.repo = new Repo( application );
        this.app = application;
        this.fba = FirebaseAnalytics.getInstance(app);
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        this.dateComparator = (entry1, entry2) -> {
            try {
                Date date1 = new Date(entry1.SavingItmeDate);
                Date date2 = new Date(entry2.SavingItmeDate);
                return date2.compareTo(date1);
            } catch (Exception e) {
                e.printStackTrace();
                return 0; // Handle parsing errors
            }
        };
    }

    public void removeItem(int position) {
        this.savingItemList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateData( List< SavingItem > data ){
        this.savingItemList = data;
        // Sort the list using the custom comparator
        try {
            Collections.sort(this.savingItemList, dateComparator);
        }
        catch( Exception ex ){ }
        this.notifyDataSetChanged();
    }
    public void updateData ( List<SavingItem> data, User user ){
        this.savingItemList = data;
        this.user = user;
        try {
            Collections.sort(this.savingItemList, dateComparator);
        }catch(Exception ex){ }
        this.notifyDataSetChanged();
    }

    public void setUser( User user ){
        this.user = user;
    }

    public void setViewModel( CurrentSavingsUpdater_ViewModel vm ){
        this.currentSavingsUpdater_viewModel = vm;

    }

    @NonNull
    @Override
    public SavingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saving_item, parent, false);

        return new SavingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingItemViewHolder holder, int position) {

        if( position == 0 ) {
            holder.savingItemWrapper.setBackgroundResource(R.color.reminders_days_of_week_background);
        }

        holder.savingItemDate.setText( String.valueOf( dateFormat.format(new Date(savingItemList.get(position).SavingItmeDate))));
        holder.savingItemAmount.setText( "Saving amount: $" + String.valueOf( this.savingItemList.get(position).SavingItemAmounts ) );

        switch( this.savingItemList.get(position).SavingItemStatus ) {
            case 0:
                holder.savingItemStatusButton.setBackgroundResource( R.color.transparent );
                holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_uncomplete );

                holder.savingItemStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        viewHolderBtnFunctionality( holder , repo );
                        fba = FirebaseAnalytics.getInstance(app);
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SavingDayCompleted"); // Name of the content
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "action");
                        fba.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    }
                });
                break;
            case 1:
                holder.savingItemStatusButton.setBackgroundResource( R.drawable.on_boarding_button_enabled_black );
                holder.savingItemStatusButton.setTextColor(this.app.getResources().getColor(R.color.white));
                holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_complete );

                holder.savingItemStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*
                        repo.updateSavingItemStatus( 0 , savingItemList.get(holder.getAdapterPosition()).pk );
                        holder.savingItemStatusButton.setBackgroundResource( R.drawable.borderless_button );
                        holder.savingItemStatusButton.setTextColor(app.getResources().getColor(R.color.black));
                        holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_uncomplete );
                        //repo.updateCurrentSavings( user.currentSavings += savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts );

                        currentSavingsUpdater_viewModel.setCurrentSavings( new CurrentSavingsWrapper( user.currentSavings += savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts ));
                        */
                        viewHolderBtnFunctionality(holder , repo);
                        fba = FirebaseAnalytics.getInstance(app);
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SavingDayCompleted"); // Name of the content
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "action");
                        fba.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    }
                });
                break;
            case 2:
                holder.savingItemStatusButton.setBackgroundResource( R.drawable.on_boarding_button_enabled_white );
                holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_overdue );
                holder.savingItemStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*
                        repo.updateSavingItemStatus( 1 , savingItemList.get(holder.getAdapterPosition()).pk );
                        holder.savingItemStatusButton.setBackgroundResource( R.drawable.on_boarding_button_black );
                        holder.savingItemStatusButton.setTextColor(app.getResources().getColor(R.color.white));
                        holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_uncomplete );
                        //repo.updateCurrentSavings( user.currentSavings -= savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts );
                        currentSavingsUpdater_viewModel.setCurrentSavings( new CurrentSavingsWrapper(  user.currentSavings -= savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts ) );
                        */
                       viewHolderBtnFunctionality( holder, repo );
                        fba = FirebaseAnalytics.getInstance(app);
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SavingDayCompleted"); // Name of the content
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "action");
                        fba.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return this.savingItemList.size();
    }

    public void viewHolderBtnFunctionality( SavingItemViewHolder holder, Repo repo ){
        if( holder.savingItemStatusButton.getText().toString().equals( app.getString(R.string.home_screen_saving_item_btn_uncomplete) ) ) {
            holder.savingItemStatusButton.setBackgroundResource( R.drawable.on_boarding_button_enabled_black );
            holder.savingItemStatusButton.setTextColor(app.getResources().getColor(R.color.white));
            holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_complete );
            repo.updateSavingItemStatus( 1 , savingItemList.get(holder.getAdapterPosition()).pk );

            double value = Double.parseDouble(String.valueOf(  user.currentSavings ));
            double newCurrentSavings = value -= savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts;
            //Log.d( "asdf", String.valueOf( newCurrentSavings ) );
            //repo.updateCurrentSavings( newCurrentSavings );
            //currentSavingsUpdater_viewModel.setCurrentSavings( new CurrentSavingsWrapper( newCurrentSavings ));
            //notifyItemChanged( holder.getAdapterPosition() );
        }
        else {
            repo.updateSavingItemStatus( 0 , savingItemList.get(holder.getAdapterPosition()).pk );
            holder.savingItemStatusButton.setBackgroundResource( R.color.transparent );
            holder.savingItemStatusButton.setTextColor(app.getResources().getColor(R.color.black));
            holder.savingItemStatusButton.setText( R.string.home_screen_saving_item_btn_uncomplete );
            //repo.updateCurrentSavings( user.currentSavings += savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts );

            //currentSavingsUpdater_viewModel.setCurrentSavings( new CurrentSavingsWrapper( user.currentSavings += savingItemList.get(holder.getAdapterPosition()).SavingItemAmounts ));
            // notifyItemChanged( holder.getAdapterPosition() );
        }
    }

    class SavingItemViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout savingItemWrapper;
        private TextView savingItemDate;
        private TextView savingItemAmount;
        private Button savingItemStatusButton;

        public SavingItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.savingItemWrapper = itemView.findViewById(R.id.savingItemWrapper);
            this.savingItemDate = itemView.findViewById(R.id.savingItemDate);
            this.savingItemAmount = itemView.findViewById(R.id.savingItemAmount);
            this.savingItemStatusButton = itemView.findViewById(R.id.savingItemStatusButton);


        }
    }

}
