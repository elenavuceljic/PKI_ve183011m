package com.example.ve183011m.pki_ve183011m.presentation.handyman.requests;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.requests.dummy.DummyContent;

public class HandymanRequestsFragment extends Fragment {

    private User user;
    private static final String USER = "user";

    public HandymanRequestsRecyclerViewAdapter adapter;

    private HandymanRequestsFragmentCallback mListener;

    public HandymanRequestsFragment() {
    }

    public static HandymanRequestsFragment newInstance(User user) {
        HandymanRequestsFragment fragment = new HandymanRequestsFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handyman_request_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new HandymanRequestsRecyclerViewAdapter(RequestManager.getInstance().getActiveRequestsForHandyman(user), mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandymanRequestsFragmentCallback) {
            mListener = (HandymanRequestsFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HandymanRequestsFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface HandymanRequestsFragmentCallback {
        void onRequestSelected(Request request);
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }
}
