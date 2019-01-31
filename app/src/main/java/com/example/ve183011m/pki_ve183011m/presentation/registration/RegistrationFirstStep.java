package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.FragmentFirstStepRegistrationBinding;

import java.util.Objects;

public class RegistrationFirstStep extends Fragment implements RegistrationVM.RegistrationFirstStepCallback {

    private RegistrationVM registrationVM;
    private FragmentFirstStepRegistrationBinding binding;

    public RegistrationFirstStep() {
    }

    public static RegistrationFirstStep newInstance() {
        return new RegistrationFirstStep();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationVM = ((RegistrationActivity) Objects.requireNonNull(getActivity())).registrationVM;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_step_registration, container, false);
        binding.setVm(registrationVM);
        editTextConfig(binding);
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationVM.validateFirstStepInput(RegistrationFirstStep.this)) {
                    registrationVM.next();
                }
            }
        });
        return binding.getRoot();
    }

    private void editTextConfig(final FragmentFirstStepRegistrationBinding binding) {
        binding.etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.usernameWrapper.setError(null);
                } else {
                    registrationVM.validateUsername(RegistrationFirstStep.this);
                }
            }
        });
        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.passwordWrapper.setError(null);
                } else {
                    registrationVM.validatePassword(RegistrationFirstStep.this);
                }
            }
        });
        binding.etSecondPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.secondPasswordWrapper.setError(null);
                } else {
                    registrationVM.validateSecondPassword(RegistrationFirstStep.this);
                }
            }
        });
        binding.etFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.fullNameWrapper.setError(null);
                } else {
                    registrationVM.validateFullName(RegistrationFirstStep.this);
                }
            }
        });
    }

    @Override
    public void onEmptyUsername() {
        binding.usernameWrapper.setError(getString(R.string.enter_username));
    }

    @Override
    public void onEmptyPassword() {
        binding.passwordWrapper.setError(getString(R.string.enter_password));
    }

    @Override
    public void onEmptySecondPassword() {
        binding.secondPasswordWrapper.setError(getString(R.string.enter_password));
    }

    @Override
    public void onDifferentPasswords() {
        binding.secondPasswordWrapper.setError(getString(R.string.passwords_dont_match));
    }

    @Override
    public void onEmptyFullName() {
        binding.fullNameWrapper.setError(getString(R.string.enter_name));
    }
}
