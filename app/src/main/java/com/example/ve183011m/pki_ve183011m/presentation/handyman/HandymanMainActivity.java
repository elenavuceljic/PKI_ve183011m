package com.example.ve183011m.pki_ve183011m.presentation.handyman;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityHandymanMainBinding;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.map.HandymanMapFragment;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.requests.HandymanRequestPreviewActivity;
import com.example.ve183011m.pki_ve183011m.presentation.handyman.requests.HandymanRequestsFragment;
import com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity;

import java.util.List;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment.REQUEST;
import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class HandymanMainActivity extends AppCompatActivity implements HandymanProfileFragment.HandymanProfileFragmentCallback,
        HandymanRequestsFragment.HandymanRequestsFragmentCallback, HandymanMapFragment.HandymanMapFragmentCallback, HandymanSearchFragment.HandymanSearchFragmentCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ActivityHandymanMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_handyman_main);

        final User user = (User) getIntent().getSerializableExtra(USER);

        setSupportActionBar(binding.toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), user);
        binding.container.setAdapter(mSectionsPagerAdapter);

        binding.container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if ((getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem()) instanceof HandymanMapFragment)) {
                    HandymanMapFragment fragment = (HandymanMapFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem());
                    fragment.updateMapUI();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.container));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    binding.bottomRequestsNavigation.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomRequestsNavigation.setVisibility(View.GONE);
                }

                if (tab.getPosition() == 2) {
                    binding.searchFab.hide();
                } else {
                    binding.searchFab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.bottomRequestsNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if ((getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem()) instanceof HandymanRequestsFragment)) {
                    HandymanRequestsFragment fragment = (HandymanRequestsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem());
                    RequestManager requestManager = RequestManager.getInstance();
                    switch (menuItem.getItemId()) {
                        case R.id.active_request:
                            if (fragment != null) {
                                fragment.adapter.requestsList = requestManager.getActiveRequestsForHandyman(user);
                                fragment.adapter.notifyDataSetChanged();
                                return true;
                            }
                        case R.id.closed_requests:
                            if (fragment != null) {
                                fragment.adapter.requestsList = requestManager.getClosedRequestsForHandyman(user);
                                fragment.adapter.notifyDataSetChanged();
                                return true;
                            }
                    }
                }
                return false;
            }
        });

        binding.searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandymanSearchFragment.newInstance().show(getSupportFragmentManager(), "search");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_handyman_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log_out) {
            logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void logOut() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onHandymanSaved(User handyman) {
        Toast.makeText(this, "Saved changes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSelected(Request request) {
        Intent intent = new Intent(this, HandymanRequestPreviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(REQUEST, request);
        startActivity(intent);
    }

    @Override
    public void onSearchBy(List<Request.Urgency> urgencyList, float lessThanDistance) {
        binding.bottomRequestsNavigation.setSelectedItemId(binding.bottomRequestsNavigation.getSelectedItemId());
        if ((getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem()) instanceof HandymanMapFragment)) {
            HandymanMapFragment fragment = (HandymanMapFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem());
            fragment.updateMapUI();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private User user;

        public SectionsPagerAdapter(FragmentManager fm, User user) {
            super(fm);
            this.user = user;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HandymanRequestsFragment.newInstance(user);
                case 1:
                    return HandymanMapFragment.newInstance(user);
                default:
                    return HandymanProfileFragment.newInstance(user);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
