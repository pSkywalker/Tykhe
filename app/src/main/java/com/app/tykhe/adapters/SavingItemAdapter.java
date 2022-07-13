package com.app.tykhe.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavingItemAdapter extends RecyclerView.Adapter<SavingItemAdapter.SavingItemViewHolder> {

    private List<String> savingItemList;

    @NonNull
    @Override
    public SavingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SavingItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.savingItemList.size();
    }

    class SavingItemViewHolder extends RecyclerView.ViewHolder {



        public SavingItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
