package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.util.LockableViewPager;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.BuyerMainActivity;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.HandymanMainActivity;

import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class RegistrationActivity extends FragmentActivity implements RegistrationHandler {

    RegistrationVM registrationVM;
    private LockableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationVM = new RegistrationVM(this);
        RegistrationPagerAdapter registrationPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.registration_pager);
        viewPager.setAdapter(registrationPagerAdapter);
        viewPager.setSwipeable(false);
    }

    @Override
    public void onRegister(User user) {
        Intent intent;
        if (user instanceof Handyman) {
            intent = new Intent(this, HandymanMainActivity.class);
        } else {
            intent = new Intent(this, BuyerMainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onNext() {
        if (registrationVM.getStep() < 3) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onBack() {
        if (registrationVM.getStep() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

}
