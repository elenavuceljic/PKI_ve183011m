package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.search.SearchHandymenFragment.SearchHandymenFragmentCallback;

import java.util.List;

public class SearchHandymenRecyclerViewAdapter extends RecyclerView.Adapter<SearchHandymenRecyclerViewAdapter.ViewHolder> {

    private final List<Handyman> handymenList;
    private final SearchHandymenFragmentCallback mListener;

    public SearchHandymenRecyclerViewAdapter(List<Handyman> items, SearchHandymenFragmentCallback listener) {
        handymenList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_search_handymen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Handyman handyman = handymenList.get(position);
        holder.handyman = handyman;
        holder.mName.setText(handyman.getFullName());
        holder.mRating.setRating(handyman.getRating());
        holder.mJobNames.setText(handyman.getSkillsAsString());
        holder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onAddRequestFor(handyman);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onHandymanSelected(handyman);
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
        public final TextView mName;
        public final RatingBar mRating;
        public final TextView mJobNames;
        public final ImageButton mBtnAdd;
        public User handyman;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.handymen_full_name);
            mRating = view.findViewById(R.id.handymen_rating);
            mJobNames = view.findViewById(R.id.handymen_job_names);
            mBtnAdd = view.findViewById(R.id.handymen_add_btn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}
