package com.example.ve183011m.pki_ve183011m.presentation.buyer.profile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.databinding.FragmentHandymanProfileBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewProfileButtonsBinding;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

public class HandymanProfileFragment extends Fragment {

    public interface HandymanProfileFragmentCallback {
        void onHandymanSaved(User handyman);
    }

    private static final String HANDYMAN = "user";

    private User user;
    private boolean isInputValid = true;

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
            user = (User) getArguments().getSerializable(HANDYMAN);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handyman_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);

        if (binding == null) {
            return;
        }

        populateView();
    }

    private void populateView() {
        binding.profileContainer.addView(inflateRowItemView(R.string.username, user.getUsername(), false, R.string.enter_username));
        binding.profileContainer.addView(inflateRowItemView(R.string.full_name, user.getFullName(), false, R.string.enter_name));
        binding.profileContainer.addView(inflateRowItemView(R.string.address, user.getAddress(), false, R.string.enter_address));
        binding.profileContainer.addView(inflateRowItemView(R.string.phone_number, user.getTelephone(), false, R.string.enter_phone));
        binding.profileContainer.addView(inflateRowItemView(R.string.old_pass, "", true, R.string.enter_password));
        binding.profileContainer.addView(inflateRowItemView(R.string.new_pass, "", true, R.string.enter_password));
        binding.profileContainer.addView(inflateRowItemView(R.string.empty, "", true, R.string.enter_password));

        binding.profileContainer.addView(inflateControlsRowItemView());
    }

    public void onSaveButtonPressed() {
        if (callback != null) {
            callback.onHandymanSaved(user);
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

    private View inflateRowItemView(int title, String value, boolean secure, final int error) {
        final ViewInputRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_input_row, null, false);

        itemBinding.title.setText(title);
        itemBinding.inputEditText.setText(value);

        if (secure) {
            itemBinding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            if (title == R.string.empty) {
                itemBinding.inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean isFocused) {
                        if (isFocused) {
                            itemBinding.inputEditTextWrapper.setError(null);
                        } else {
                            TextInputEditText et1 = binding.profileContainer.getChildAt(4).findViewById(R.id.input_edit_text);
                            TextInputEditText et2 = binding.profileContainer.getChildAt(5).findViewById(R.id.input_edit_text);
                            TextInputEditText et3 = binding.profileContainer.getChildAt(6).findViewById(R.id.input_edit_text);
                            if ((et3.getText() != null && et3.getText().length() > 0) || (et2.getText() != null && et2.getText().length() > 0) || (et1.getText() != null && et1.getText().length() > 0)) {
                                if (!et2.getText().toString().equals(itemBinding.inputEditText.getText().toString())) {
                                    itemBinding.inputEditTextWrapper.setError(getString(R.string.passwords_dont_match));
                                    isInputValid = false;
                                }
                            }
                        }
                    }
                });
            } else if (R.string.old_pass == title) {
                itemBinding.inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean isFocused) {
                        if (isFocused) {
                            itemBinding.inputEditTextWrapper.setError(null);
                        } else {
                            TextInputEditText et1 = binding.profileContainer.getChildAt(4).findViewById(R.id.input_edit_text);
                            TextInputEditText et2 = binding.profileContainer.getChildAt(5).findViewById(R.id.input_edit_text);
                            TextInputEditText et3 = binding.profileContainer.getChildAt(6).findViewById(R.id.input_edit_text);
                            if ((et3.getText() != null && et3.getText().length() > 0) || (et2.getText() != null && et2.getText().length() > 0) || (et1.getText() != null && et1.getText().length() > 0)) {
                                if (!et1.getText().toString().equals(user.getPassword())) {
                                    itemBinding.inputEditTextWrapper.setError(getString(R.string.passwords_dont_match));
                                    isInputValid = false;
                                }
                            }
                        }
                    }
                });
            } else {
                itemBinding.inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean isFocused) {
                        if (isFocused) {
                            itemBinding.inputEditTextWrapper.setError(null);
                        } else {
                            TextInputEditText et1 = binding.profileContainer.getChildAt(4).findViewById(R.id.input_edit_text);
                            TextInputEditText et2 = binding.profileContainer.getChildAt(5).findViewById(R.id.input_edit_text);
                            TextInputEditText et3 = binding.profileContainer.getChildAt(6).findViewById(R.id.input_edit_text);
                            if ((et3.getText() != null && et3.getText().length() > 0) || (et2.getText() != null && et2.getText().length() > 0) || (et1.getText() != null && et1.getText().length() > 0)) {
                                if (itemBinding.inputEditText.getText() == null || itemBinding.inputEditText.getText().length() == 0) {
                                    itemBinding.inputEditTextWrapper.setError(getString(error));
                                    isInputValid = false;
                                }
                            }
                        }
                    }
                });
            }
        } else {
            itemBinding.inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean isFocused) {
                    if (isFocused) {
                        itemBinding.inputEditTextWrapper.setError(null);
                    } else {
                        if (itemBinding.inputEditText.getText() == null || itemBinding.inputEditText.getText().length() == 0) {
                            itemBinding.inputEditTextWrapper.setError(getString(error));
                            isInputValid = false;
                        }
                    }
                }
            });
        }

        return itemBinding.getRoot();
    }

    private View inflateControlsRowItemView() {
        ViewProfileButtonsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_profile_buttons, null, false);

        itemBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInputValid = true;
                for (int i = 0; i < binding.profileContainer.getChildCount() - 1; i++) {
                    ViewInputRowBinding bind = DataBindingUtil.getBinding(binding.profileContainer.getChildAt(i));
                    bind.inputEditText.requestFocus();
                    bind.inputEditText.clearFocus();
                }
                if (isInputValid) {
                    user.setUsername(((TextInputEditText)binding.profileContainer.getChildAt(0).findViewById(R.id.input_edit_text)).getText().toString());
                    user.setFullName(((TextInputEditText)binding.profileContainer.getChildAt(1).findViewById(R.id.input_edit_text)).getText().toString());
                    user.setAddress(((TextInputEditText)binding.profileContainer.getChildAt(2).findViewById(R.id.input_edit_text)).getText().toString());
                    user.setTelephone(((TextInputEditText)binding.profileContainer.getChildAt(3).findViewById(R.id.input_edit_text)).getText().toString());
                    String pass = ((TextInputEditText)binding.profileContainer.getChildAt(3).findViewById(R.id.input_edit_text)).getText().toString();
                    if (pass != null && pass.length() > 0) {
                        user.setPassword(((TextInputEditText)binding.profileContainer.getChildAt(5).findViewById(R.id.input_edit_text)).getText().toString());
                    }
                    UserManager.getInstance().addUser(user);

                    onSaveButtonPressed();
                }
            }
        });

        itemBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.profileContainer.removeAllViews();
                populateView();
            }
        });

        return itemBinding.getRoot();
    }

}