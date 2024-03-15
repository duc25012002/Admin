package com.hdcompany.admin.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdcompany.admin.databinding.BookingItemBinding;
import com.hdcompany.admin.model.Booking;
import com.hdcompany.admin.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingHolder> {

    private List<Booking> bookingList = new ArrayList<>();
    private onClick onClick;
    public interface onClick{
        void onItemClick(Booking booking);
    }

    public BookingsAdapter( onClick onClick){
        this.onClick = onClick;
    }

    public void setItems(@NonNull List<Booking> list){
        this.bookingList.addAll(list);
    }
    public List<Booking> getItems(){
        return this.bookingList;
    }
    public Booking getBooking(int pos){
        return this.bookingList.get(pos);
    }
    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookingItemBinding binding = BookingItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new BookingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        holder.binding.setBooking(bookingList.get(position));
        holder.binding.itemBooking.setOnClickListener(v->this.onClick.onItemClick(holder.binding.getBooking()));
    }

    @Override
    public int getItemCount() {
        return bookingList == null ? 0: bookingList.size();
    }

    public class BookingHolder extends RecyclerView.ViewHolder{
        private BookingItemBinding binding;
        public BookingHolder(@NonNull BookingItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
