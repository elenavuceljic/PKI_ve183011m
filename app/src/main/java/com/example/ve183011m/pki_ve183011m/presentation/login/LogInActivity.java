package com.example.ve183011m.pki_ve183011m.presentation.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityLogInBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.BuyerMainActivity;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.HandymanMainActivity;
import com.example.ve183011m.pki_ve183011m.presentation.registration.RegistrationActivity;

import java.io.Serializable;


public class LogInActivity extends AppCompatActivity implements LoginHandler, Serializable {

    public static final String USER = "USER";

    private LogInVM logInVM;
    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in);

        logInVM = new LogInVM(this);
        binding.setVm(logInVM);

        editTextConfig(binding);
        binding.loginButton.setOnClickListener(getLogInOnClickListener());
        binding.continueAsGuest.setOnClickListener(getContinueAsGuestOnClickListener());
        binding.signUp.setOnClickListener(getSignUpOnClickListener());
    }

    private View.OnClickListener getLogInOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInVM.login();
            }
        };
    }

    private View.OnClickListener getSignUpOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        };
    }

    private View.OnClickListener getContinueAsGuestOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueAsGuest();
            }
        };
    }

    private void editTextConfig(final ActivityLogInBinding binding) {
        binding.etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.usernameWrapper.setError(null);
                } else {
                    logInVM.validateUsername();
                }
            }
        });
        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    binding.passwordWrapper.setError(null);
                } else {
                    logInVM.validatePassword();
                }
            }
        });
    }

    @Override
    public void onLoginSuccess(User user) {
        binding.usernameWrapper.setError(null);
        binding.passwordWrapper.setError(null);
        Intent intent;
        if (user instanceof Handyman)
            intent = new Intent(this, HandymanMainActivity.class);
        else
            intent = new Intent(this, BuyerMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFail() {
        binding.usernameWrapper.setError(null);
        binding.passwordWrapper.setError(null);
        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEmptyUsername() {
        binding.usernameWrapper.setError("Enter username");
    }

    @Override
    public void onEmptyPassword() {
        binding.passwordWrapper.setError("Enter password");
    }

    @Override
    public void onRegister() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onContinueAsGuest() {
        Intent intent = new Intent(this, BuyerMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
