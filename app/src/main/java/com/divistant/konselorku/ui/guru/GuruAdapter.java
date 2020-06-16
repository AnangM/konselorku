package com.divistant.konselorku.ui.guru;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.ViewHolder> {
    List<GuruModel> listGuru = new ArrayList<>();
    public GuruAdapter(){}

    public GuruAdapter(List<GuruModel> listGuru) {
        this.listGuru = listGuru;
    }

    @NonNull
    @Override
    public GuruAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GuruAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
