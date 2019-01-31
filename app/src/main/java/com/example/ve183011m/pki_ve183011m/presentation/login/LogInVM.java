package com.example.ve183011m.pki_ve183011m.presentation.login;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

public class LogInVM extends BaseObservable {

    private final UserManager userManager;
    private final LoginHandler loginHandler;

    private ObservableField<String> username;
    private ObservableField<String> password;

    LogInVM(LoginHandler handler) {
        this.userManager = UserManager.getInstance();
        loginHandler = handler;
        username = new ObservableField<>();
        password = new ObservableField<>();
    }

    public void login() {
        if (validateLogInInput()) {
            User user = userManager.getUserWithCredentials(username.get(), password.get());
            if (user != null) {
                loginHandler.onLoginSuccess(user);
            } else {
                loginHandler.onLoginFail();
            }
        }
    }

    public boolean validateLogInInput() {
        boolean isValid = true;
        if (!validateUsername()) {
            isValid = false;
        }
        if (!validatePassword()) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateUsername() {
        String user = username.get();
        if (user == null || user.isEmpty()) {
            loginHandler.onEmptyUsername();
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        String pass = password.get();
        if (pass == null || pass.isEmpty()) {
            loginHandler.onEmptyPassword();
            return false;
        }
        return true;
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }
}
