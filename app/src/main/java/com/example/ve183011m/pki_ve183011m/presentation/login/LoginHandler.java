package com.example.ve183011m.pki_ve183011m.presentation.login;

import com.example.ve183011m.pki_ve183011m.model.User;

public interface LoginHandler {

    void onLoginSuccess(User user);

    void onLoginFail();

    void onEmptyUsername();

    void onEmptyPassword();

    void onRegister();

    void onContinueAsGuest();

}
