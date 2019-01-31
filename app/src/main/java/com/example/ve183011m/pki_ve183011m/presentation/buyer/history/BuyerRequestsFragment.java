package com.example.ve183011m.pki_ve183011m.presentation.buyer.history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

public class BuyerRequestsFragment extends Fragment {

    private User user;
    private static final String USER = "user";

    private BuyerRequestsFragmentCallback mListener;

    public BuyerRequestsFragment() {
    }

    public static BuyerRequestsFragment newInstance(User user) {
        BuyerRequestsFragment fragment = new BuyerRequestsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handymen_history_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new BuyerRequestsRecyclerViewAdapter(RequestManager.getInstance().getRequestsForUser(user), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BuyerRequestsFragmentCallback) {
            mListener = (BuyerRequestsFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BuyerRequestsFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface BuyerRequestsFragmentCallback {
        void onRequestSelected(Request request);
    }
}
