package com.example.ve183011m.pki_ve183011m.presentation.buyer.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.Request;

import java.util.List;

public class BuyerRequestsRecyclerViewAdapter extends RecyclerView.Adapter<BuyerRequestsRecyclerViewAdapter.ViewHolder> {

    private final List<Request> requestsList;
    private final BuyerRequestsFragment.BuyerRequestsFragmentCallback mListener;

    public BuyerRequestsRecyclerViewAdapter(List<Request> items, BuyerRequestsFragment.BuyerRequestsFragmentCallback listener) {
        requestsList = items;
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
        holder.handyman = requestsList.get(position);
        holder.mIdView.setText(requestsList.get(position).getHandyman().getFullName());
        holder.mContentView.setText(requestsList.get(position).getJob().getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onRequestSelected(holder.handyman);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Request handyman;

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
