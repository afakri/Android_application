package com.example.service_novigrad;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class branchAdapter extends RecyclerView.Adapter<branchAdapter.ViewHolder>  {

    private static List<Branch> mData;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // stores and recycles views as they are scrolled off screen
     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,address;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.branchname);
            address= itemView.findViewById(R.id.address);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // data is passed into the constructor
    branchAdapter(Context context, List<Branch> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;


    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.branch, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Branch b1 = mData.get(position);
        holder.name.setText(b1.getName());
        holder.address.setText(b1.getAddress());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<Branch> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }



    // convenience method for getting data at click position
    Branch getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
