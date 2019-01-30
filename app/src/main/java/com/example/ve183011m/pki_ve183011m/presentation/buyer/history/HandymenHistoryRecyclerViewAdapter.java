package com.example.ve183011m.pki_ve183011m.presentation.buyer.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.history.HandymenHistoryFragment.OnListFragmentInteractionListener;

import java.util.List;

public class HandymenHistoryRecyclerViewAdapter extends RecyclerView.Adapter<HandymenHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<User> handymenList;
    private final OnListFragmentInteractionListener mListener;

    public HandymenHistoryRecyclerViewAdapter(List<User> items, OnListFragmentInteractionListener listener) {
        handymenList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_handymen_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.handyman = handymenList.get(position);
        holder.mIdView.setText(handymenList.get(position).getFullName());
        holder.mContentView.setText(handymenList.get(position).getAddress());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.handyman);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return handymenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public User handyman;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.history_handyman_full_name);
            mContentView = view.findViewById(R.id.history_handyman_rating);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
