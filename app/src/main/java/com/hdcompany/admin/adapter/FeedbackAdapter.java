package com.hdcompany.admin.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hdcompany.admin.databinding.FeedbackItemBinding;
import com.hdcompany.admin.model.Booking;
import com.hdcompany.admin.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbackList =new ArrayList<>();
    private onClick onClick;

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FeedbackItemBinding binding = FeedbackItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new FeedbackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.binding.setFeedback(feedbackList.get(position));
        holder.binding.itemFeedback.setOnClickListener(v->this.onClick.onItemClick(holder.binding.getFeedback()));
    }

    @Override
    public int getItemCount() {
        return feedbackList == null ? 0: feedbackList.size();
    }

    public interface onClick{
        void onItemClick(Feedback feedback);
    }

    public FeedbackAdapter(onClick onClick){
        this.onClick = onClick;
    }

    public void setItems(List<Feedback> l){
        this.feedbackList.addAll(l);
    }
    public List<Feedback> getItems(){
        return this.feedbackList;
    }

    public Feedback getFeedback(int pos){
        return this.feedbackList.get(pos);
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder{
        private FeedbackItemBinding binding;
        public FeedbackViewHolder(@NonNull FeedbackItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
