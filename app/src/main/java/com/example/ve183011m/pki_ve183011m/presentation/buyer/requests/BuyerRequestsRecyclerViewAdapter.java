package com.example.ve183011m.pki_ve183011m.presentation.buyer.requests;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.model.Request;

import java.text.SimpleDateFormat;
import java.util.List;

public class BuyerRequestsRecyclerViewAdapter extends RecyclerView.Adapter<BuyerRequestsRecyclerViewAdapter.ViewHolder> {

    public List<Request> requestsList;
    public boolean active = true;
    private final BuyerRequestsFragment.BuyerRequestsFragmentCallback mListener;

    public BuyerRequestsRecyclerViewAdapter(List<Request> items, BuyerRequestsFragment.BuyerRequestsFragmentCallback listener) {
        requestsList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_buyer_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Request request = requestsList.get(position);
        holder.request = request;
        holder.mTVJobName.setText(request.getJob().getName());
        holder.mTVHandymanName.setText(request.getHandyman().getFullName());
        holder.mTVJobStatus.setText(request.getStatus().toString());
        holder.mTVJobPrice.setText(String.valueOf(request.getJob().getPrice()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        holder.mTVJobDates.setText(df.format(request.getStartDate()) + " - " + df.format(request.getEndDate()));
        if (request.getStatus() == Request.Status.ACCEPTED && !request.isPayableByCash() && !request.isPayed()) {
            holder.mBtnAction.setText(R.string.pay);
            holder.mBtnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPayRequest(request);
                }
            });
        } else if (request.getStatus() == Request.Status.SENT || (request.getStatus()== Request.Status.ACCEPTED && (request.isPayed() || request.isPayableByCash()))) {
            holder.mBtnAction.setVisibility(View.GONE);
        } else {
            holder.mBtnAction.setText(R.string.delete);
            holder.mBtnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestManager.getInstance().deleteRequest(request);
                    notifyDataSetChanged();
                }
            });
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
        final Button mBtnAction;

        public Request request;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTVJobName = view.findViewById(R.id.requests_buyer_job_name);
            mTVHandymanName = view.findViewById(R.id.requests_buyer_full_name);
            mTVJobDates = view.findViewById(R.id.requests_buyer_job_dates);
            mTVJobPrice = view.findViewById(R.id.requests_buyer_job_price);
            mTVJobStatus = view.findViewById(R.id.requests_buyer_request_status);
            mBtnAction = view.findViewById(R.id.requests_action_btn);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTVHandymanName.getText() + "'";
        }
    }
}
