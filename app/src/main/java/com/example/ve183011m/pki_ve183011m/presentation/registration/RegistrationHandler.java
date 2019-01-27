package com.example.ve183011m.pki_ve183011m.presentation.registration;

import com.example.ve183011m.pki_ve183011m.model.User;

public interface RegistrationHandler {
    void onRegister(User user);

    void onNext();

    void onBack();
}
