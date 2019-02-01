package com.example.ve183011m.pki_ve183011m.presentation.handyman.requests;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.requests.HandymanRequestsFragment.HandymanRequestsFragmentCallback;

import java.text.SimpleDateFormat;
import java.util.List;

public class HandymanRequestsRecyclerViewAdapter extends RecyclerView.Adapter<HandymanRequestsRecyclerViewAdapter.ViewHolder> {

    public List<Request> requestsList;
    private final HandymanRequestsFragmentCallback mListener;

    public HandymanRequestsRecyclerViewAdapter(List<Request> items, HandymanRequestsFragment.HandymanRequestsFragmentCallback listener) {
        requestsList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_handyman_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Request request = requestsList.get(position);
        holder.request = request;
        holder.mTVJobName.setText(request.getJob().getName());
        holder.mTVHandymanName.setText(request.getBuyer().getFullName());
        holder.mTVJobStatus.setText(request.getStatus().toString());
        holder.mTVJobPrice.setText(String.valueOf(request.getJob().getPrice()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        holder.mTVJobDates.setText(df.format(request.getStartDate()) + " - " + df.format(request.getEndDate()));
        if (request.getStatus() == Request.Status.ACCEPTED) {
            holder.mBtnDelete.setVisibility(View.VISIBLE);
            holder.mBtnCheck.setVisibility(View.VISIBLE);
            holder.mBtnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.setStatus(Request.Status.DONE);
                    notifyDataSetChanged();
                }
            });
            holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.setStatus(Request.Status.FAILED);
                    notifyDataSetChanged();
                }
            });
        } else if (request.getStatus() == Request.Status.SENT) {
            holder.mBtnDelete.setVisibility(View.VISIBLE);
            holder.mBtnCheck.setVisibility(View.VISIBLE);
            holder.mBtnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.setStatus(Request.Status.ACCEPTED);
                    notifyDataSetChanged();
                }
            });
            holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.setStatus(Request.Status.DENIED);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.mBtnDelete.setVisibility(View.GONE);
            holder.mBtnCheck.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onRequestSelected(holder.request);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTVJobName;
        final TextView mTVHandymanName;
        final TextView mTVJobDates;
        final TextView mTVJobPrice;
        final TextView mTVJobStatus;
        final ImageButton mBtnCheck;
        final ImageButton mBtnDelete;

        public Request request;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTVJobName = view.findViewById(R.id.requests_handyman_job_name);
            mTVHandymanName = view.findViewById(R.id.requests_handyman_full_name);
            mTVJobDates = view.findViewById(R.id.requests_handyman_job_dates);
            mTVJobPrice = view.findViewById(R.id.requests_handyman_job_price);
            mTVJobStatus = view.findViewById(R.id.requests_handyman_request_status);
            mBtnCheck = view.findViewById(R.id.requests_check_btn);
            mBtnDelete = view.findViewById(R.id.requests_delete_btn);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTVHandymanName.getText() + "'";
        }
    }
}
