package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class RegistrationPagerAdapter extends FragmentStatePagerAdapter {

    private RegistrationVM registrationVM;

    public RegistrationPagerAdapter(FragmentManager fm, RegistrationVM registrationVM) {
        super(fm);
        this.registrationVM = registrationVM;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 1:
//                return RegistrationSecondStep.newInstance(registrationVM);
            case 2:
//                return RegistrationThirdStep.newInstance(registrationVM);
            default:
                return RegistrationFirstStep.newInstance(registrationVM);
        }
    }

}
