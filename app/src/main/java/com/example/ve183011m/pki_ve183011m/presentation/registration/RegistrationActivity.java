package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.User;

import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class RegistrationActivity extends FragmentActivity implements RegistrationHandler, RegistrationSecondStep.OnFragmentInteractionListener, RegistrationThirdStep.OnFragmentInteractionListener {

    public RegistrationVM registrationVM;
    private RegistrationPagerAdapter registrationPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationVM = new RegistrationVM(this);
        registrationPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager(), registrationVM);

        ViewPager mPager = findViewById(R.id.registration_pager);
        mPager.setAdapter(registrationPagerAdapter);

//        editTextConfig(binding);
//        binding.nextButton.setOnClickListener(getLogInOnClickListener());
    }

    private View.OnClickListener getLogInOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationVM.nextStep();
            }
        };
    }

//    private void editTextConfig(final ActivityRegistrationBinding binding) {
//        binding.etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
//                if (isFocused) {
//                    binding.usernameWrapper.setError(null);
//                } else {
//                    registrationVM.validateUsername();
//                }
//            }
//        });
//        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
//                if (isFocused) {
//                    binding.passwordWrapper.setError(null);
//                } else {
//                    registrationVM.validatePassword();
//                }
//            }
//        });
//        binding.etSecondPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
//                if (isFocused) {
//                    binding.secondPasswordWrapper.setError(null);
//                } else {
//                    registrationVM.validateSecondPassword();
//                }
//            }
//        });
//        binding.etFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean isFocused) {
//                if (isFocused) {
//                    binding.fullNameWrapper.setError(null);
//                } else {
//                    registrationVM.validateFullName();
//                }
//            }
//        });
//    }

    @Override
    public void onRegister(User user) {
//        binding.usernameWrapper.setError(null);
//        binding.passwordWrapper.setError(null);
//        binding.secondPasswordWrapper.setError(null);
//        binding.fullNameWrapper.setError(null);
//        //TODO: add wrappers setnull
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onEmptyUsername() {

//        binding.usernameWrapper.setError("Enter username");
    }

    @Override
    public void onEmptyPassword() {
//        binding.passwordWrapper.setError("Enter password");
    }

    @Override
    public void onEmptySecondPassword() {
//        binding.secondPasswordWrapper.setError("Enter password");
    }

    @Override
    public void onDifferentPasswords() {
//        binding.secondPasswordWrapper.setError("Different passwords");
    }

    @Override
    public void onEmptyFullName() {
//        binding.fullNameWrapper.setError("Enter full name");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
