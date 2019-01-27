package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class RegistrationPagerAdapter extends FragmentStatePagerAdapter {

    public RegistrationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return RegistrationFirstStep.newInstance();
            case 1:
                return RegistrationSecondStep.newInstance();
            default:
                return RegistrationThirdStep.newInstance();
        }
    }

}
