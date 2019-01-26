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

public class RegistrationFirstStep extends Fragment {

    private static final String ARG_VM = "REGISTRATION_VM";

    private RegistrationVM registrationVM;
    private FragmentFirstStepRegistrationBinding binding;

    public RegistrationFirstStep() {
    }

    public static RegistrationFirstStep newInstance(RegistrationVM registrationVM) {
        RegistrationFirstStep fragment = new RegistrationFirstStep();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VM, registrationVM);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registrationVM = ((RegistrationActivity) Objects.requireNonNull(getActivity())).registrationVM;
//        if (getArguments() != null) {
//            registrationVM = (RegistrationVM) getArguments().getSerializable(ARG_VM);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_step_registration, container, true);
        binding.setVm(registrationVM);
        return binding.getRoot();
    }

}
