package com.example.ve183011m.pki_ve183011m.presentation.buyer.profile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.FragmentHandymanProfileBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewProfileButtonsBinding;
import com.example.ve183011m.pki_ve183011m.model.User;

public class HandymanProfileFragment extends Fragment {

    public interface HandymanProfileFragmentCallback {
        void onHandymanSaved(User handyman);

        void onHandymanCanceled();
    }

    private static final String HANDYMAN = "handyman";

    private User handyman;

    private HandymanProfileFragmentCallback callback;
    private FragmentHandymanProfileBinding binding;

    public HandymanProfileFragment() {
        // Required empty public constructor
    }

    public static HandymanProfileFragment newInstance(User handyman) {
        HandymanProfileFragment fragment = new HandymanProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(HANDYMAN, handyman);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            handyman = (User) getArguments().getSerializable(HANDYMAN);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handyman_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);

        if (binding == null) {
            return;
        }

        binding.profileContainer.addView(inflateRowItemView("Username", "user95", false));
        binding.profileContainer.addView(inflateRowItemView("Full name", "Jack Jackson", false));
        binding.profileContainer.addView(inflateRowItemView("Address", "Boulevard 95", false));
        binding.profileContainer.addView(inflateRowItemView("Phone number", "67986987", false));
        binding.profileContainer.addView(inflateRowItemView("Old password", "kljads", true));
        binding.profileContainer.addView(inflateRowItemView("New Password", "kjdsa", true));
        binding.profileContainer.addView(inflateRowItemView("", "kjdsa", true));

        binding.profileContainer.addView(inflateControlsRowItemView());
    }

    public void onSaveButtonPressed() {
        if (callback != null) {
            callback.onHandymanSaved(handyman);
        }
    }

    public void onCancelButtonPressed() {
        if (callback != null) {
            callback.onHandymanCanceled();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandymanProfileFragmentCallback) {
            callback = (HandymanProfileFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private View inflateRowItemView(String title, String value, boolean secure) {
        ViewInputRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_input_row, null, false);

        itemBinding.title.setText(title);
        itemBinding.inputEditText.setText(value);

        if (secure) {
            itemBinding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        itemBinding.inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        return itemBinding.getRoot();
    }

    private View inflateControlsRowItemView() {
        ViewProfileButtonsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_profile_buttons, null, false);

        itemBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonPressed();
            }
        });

        itemBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonPressed();
            }
        });

        return itemBinding.getRoot();
    }

}