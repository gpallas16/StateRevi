package com.github.gpallas16.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.gpallas16.staterevi.StateReviAdapter;

public class SampleReviAdapter extends StateReviAdapter<MockModel, SampleReviAdapter.SampleViewHolder> {

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        holder.textView.setText(getList().get(position).getText());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    static class SampleViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public SampleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
