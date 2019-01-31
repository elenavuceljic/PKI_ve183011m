package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.FragmentRegistrationSecondStepBinding;

import java.util.Objects;

public class RegistrationSecondStep extends Fragment implements RegistrationVM.RegistrationSecondStepCallback {

    private RegistrationVM registrationVM;
    private FragmentRegistrationSecondStepBinding binding;

    public RegistrationSecondStep() {
    }

    public static RegistrationSecondStep newInstance() {
        return new RegistrationSecondStep();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationVM = ((RegistrationActivity) Objects.requireNonNull(getActivity())).registrationVM;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_second_step, container, false);
        binding.setVm(registrationVM);
        editTextConfig(binding);
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationVM.validateSecondStepInput(RegistrationSecondStep.this)) {
                    registrationVM.next();
                }
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationVM.back();
            }
        });
        return binding.getRoot();
    }


    private void editTextConfig(final FragmentRegistrationSecondStepBinding binding) {
        binding.etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.phoneWrapper.setError(null);
                } else {
                    registrationVM.validatePhone(RegistrationSecondStep.this);
                }
            }
        });
        binding.etAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.addressWrapper.setError(null);
                } else {
                    registrationVM.validateAddress(RegistrationSecondStep.this);
                }
            }
        });
    }

    @Override
    public void onEmptyPhone() {
        binding.phoneWrapper.setError(getString(R.string.enter_phone));
    }

    @Override
    public void onEmptyAddress() {
        binding.addressWrapper.setError(getString(R.string.enter_address));
    }
}
