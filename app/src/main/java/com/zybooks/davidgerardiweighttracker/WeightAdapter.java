package com.zybooks.davidgerardiweighttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {

    private List<WeightEntry> weightList;
    private OnDeleteClickListener deleteClickListener;

    public WeightAdapter(List<WeightEntry> weightList, OnDeleteClickListener deleteClickListener) {
        this.weightList = weightList;
        this.deleteClickListener = deleteClickListener;
    }

    public void setWeightList(List<WeightEntry> weightList) {
        this.weightList = weightList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weight, parent, false);
        return new WeightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        WeightEntry entry = weightList.get(position);
        holder.dateText.setText(entry.date);
        holder.weightText.setText(entry.weight + " lbs");

        holder.recyclerDeleteButton.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(entry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weightList != null ? weightList.size() : 0;
    }

    static class WeightViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, weightText;
        Button recyclerDeleteButton;

        WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateTextView);
            weightText = itemView.findViewById(R.id.weightTextView);
            recyclerDeleteButton = itemView.findViewById(R.id.recyclerDeleteItemButton);
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(WeightEntry entry);
    }
}
