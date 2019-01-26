package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.io.Serializable;

public class RegistrationVM extends BaseObservable implements Serializable {

    private final UserManager userManager;
    private final RegistrationHandler registrationHandler;

    private ObservableField<String> username;
    private ObservableField<String> password;
    private ObservableField<String> passwordSecond;
    private ObservableField<String> fullName;
    private ObservableField<String> telephone;
    private ObservableField<String> address;
    private ObservableField<Boolean> isBuyer;
    private ObservableField<Integer> experience;
    private ObservableField<Boolean> plumbing;
    private ObservableField<Boolean> electrical;
    private ObservableField<Boolean> furniture;
    private ObservableField<Boolean> ac;
    private ObservableField<Boolean> paintJob;

    private int step = 0;

    RegistrationVM(RegistrationHandler handler) {
        this.userManager = UserManager.getInstance();
        registrationHandler = handler;
        username = new ObservableField<>();
        password = new ObservableField<>();
        passwordSecond = new ObservableField<>();
        fullName = new ObservableField<>();
        telephone = new ObservableField<>();
        address = new ObservableField<>();
        isBuyer = new ObservableField<>();
        experience = new ObservableField<>();
        plumbing = new ObservableField<>();
        electrical = new ObservableField<>();
        furniture = new ObservableField<>();
        ac = new ObservableField<>();
        paintJob = new ObservableField<>();
    }

    public void nextStep() {
        if (validateRegistrationInput()) {
            switch (step) {
                case 0: {
                    step++;
                    break;
                }
                case 1: {
                    step++;
                    break;
                }
                case 2: {
                    User user = new User(username.get(), password.get());
                    userManager.addUser(user);
                    registrationHandler.onRegister(user);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    public boolean validateRegistrationInput() {
        boolean isValid = true;
        switch (step) {
            case 0: {
                if (!validateUsername()) {
                    isValid = false;
                }
                if (!validateSecondPassword()) {
                    isValid = false;
                }
                if (!validateFullName()) {
                    isValid = false;
                }
                break;
            }
            case 1: {
                isValid = false;
                break;
            }
            case 2: {
                isValid = false;
                break;
            }
            default: {
                break;
            }
        }
        return isValid;
    }

    public boolean validateUsername() {
        String user = username.get();
        if (user == null || user.isEmpty()) {
            registrationHandler.onEmptyUsername();
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        String pass = password.get();
        if (pass == null || pass.isEmpty()) {
            registrationHandler.onEmptyPassword();
            return false;
        }
        return true;
    }

    public boolean validateSecondPassword() {
        String secondPass = passwordSecond.get();
        if (secondPass == null || secondPass.isEmpty()) {
            registrationHandler.onEmptySecondPassword();
            return false;
        }

        String firstPass = password.get();
        if (validatePassword() && !firstPass.equals(secondPass)) {
            registrationHandler.onDifferentPasswords();
            return false;
        }
        return true;
    }

    public boolean validateFullName() {
        String name = fullName.get();
        if (name == null || name.isEmpty()) {
            registrationHandler.onEmptyFullName();
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

    public ObservableField<String> getPasswordSecond() {
        return passwordSecond;
    }

    public void setPasswordSecond(ObservableField<String> passwordSecond) {
        this.passwordSecond = passwordSecond;
    }

    public ObservableField<String> getFullName() {
        return fullName;
    }

    public void setFullName(ObservableField<String> fullName) {
        this.fullName = fullName;
    }

    public ObservableField<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(ObservableField<String> telephone) {
        this.telephone = telephone;
    }

    public ObservableField<String> getAddress() {
        return address;
    }

    public void setAddress(ObservableField<String> address) {
        this.address = address;
    }

    public ObservableField<Boolean> getIsBuyer() {
        return isBuyer;
    }

    public void setIsBuyer(ObservableField<Boolean> isBuyer) {
        this.isBuyer = isBuyer;
    }

    public ObservableField<Integer> getExperience() {
        return experience;
    }

    public void setExperience(ObservableField<Integer> experience) {
        this.experience = experience;
    }

    public ObservableField<Boolean> getPlumbing() {
        return plumbing;
    }

    public void setPlumbing(ObservableField<Boolean> plumbing) {
        this.plumbing = plumbing;
    }

    public ObservableField<Boolean> getElectrical() {
        return electrical;
    }

    public void setElectrical(ObservableField<Boolean> electrical) {
        this.electrical = electrical;
    }

    public ObservableField<Boolean> getFurniture() {
        return furniture;
    }

    public void setFurniture(ObservableField<Boolean> furniture) {
        this.furniture = furniture;
    }

    public ObservableField<Boolean> getAc() {
        return ac;
    }

    public void setAc(ObservableField<Boolean> ac) {
        this.ac = ac;
    }

    public ObservableField<Boolean> getPaintJob() {
        return paintJob;
    }

    public void setPaintJob(ObservableField<Boolean> paintJob) {
        this.paintJob = paintJob;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
