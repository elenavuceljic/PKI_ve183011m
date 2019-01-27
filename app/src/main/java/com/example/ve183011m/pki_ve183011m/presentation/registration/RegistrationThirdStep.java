package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.FragmentRegistrationThirdStepBinding;

import java.util.Objects;

public class RegistrationThirdStep extends Fragment {

    private RegistrationVM registrationVM;
    private FragmentRegistrationThirdStepBinding binding;

    public RegistrationThirdStep() {
    }

    public static RegistrationThirdStep newInstance() {
        return new RegistrationThirdStep();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationVM = ((RegistrationActivity) Objects.requireNonNull(getActivity())).registrationVM;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_third_step, container, false);
        binding.setVm(registrationVM);
        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationVM.next();
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationVM.back();
            }
        });
        binding.np.setMaxValue(50);
        binding.np.setMinValue(1);
        return binding.getRoot();
    }

}
