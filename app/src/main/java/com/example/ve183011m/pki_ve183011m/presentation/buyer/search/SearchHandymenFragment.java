package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.BuyerRequestPreviewActivity;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment.REQUEST;

public class SearchHandymenFragment extends Fragment {

    private User user;
    private static final String USER = "user";

    private SearchHandymenRecyclerViewAdapter adapter;

    private SearchHandymenFragmentCallback mListener;

    public SearchHandymenFragment() {
    }

    public static SearchHandymenFragment newInstance(User user) {
        SearchHandymenFragment fragment = new SearchHandymenFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_handymen_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.search_handymen_list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new SearchHandymenRecyclerViewAdapter(UserManager.getInstance().getHandymen(), mListener);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.search_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BuyerSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.handymenList = UserManager.getInstance().getHandymen();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchHandymenFragmentCallback) {
            mListener = (SearchHandymenFragmentCallback) context;
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

    public interface SearchHandymenFragmentCallback {
        void onHandymanSelected(Handyman handyman);

        void onAddRequestFor(Handyman handyman);
    }

}
