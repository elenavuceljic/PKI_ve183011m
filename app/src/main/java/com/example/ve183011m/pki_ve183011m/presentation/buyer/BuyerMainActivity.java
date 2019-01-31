package com.example.ve183011m.pki_ve183011m.presentation.buyer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityBuyerMainBinding;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.history.BuyerRequestsFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.search.SearchHandymenFragment;
import com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity;

import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class BuyerMainActivity extends AppCompatActivity implements SearchHandymenFragment.OnListFragmentInteractionListener, BuyerRequestsFragment.BuyerRequestsFragmentCallback, HandymanProfileFragment.HandymanProfileFragmentCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ActivityBuyerMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buyer_main);

        User user = (User)getIntent().getSerializableExtra(USER);

        setSupportActionBar(binding.toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), user);
        binding.container.setAdapter(mSectionsPagerAdapter);

        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        binding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.container));

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    binding.bottomRequestsNavigation.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomRequestsNavigation.setVisibility(View.GONE);
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
                switch (menuItem.getItemId()) {
                    case R.id.active_request:
                        // filter with active
                        return true;
                    case R.id.closed_requests:
                        // filter with closed
                        return true;
                }

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buyer_main, menu);
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
    public void onRequestSelected(Request request) {

    }

    @Override
    public void onHandymanSaved(User handyman) {
        Toast.makeText(this, "Saved changes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListFragmentInteraction(User handyman) {

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
                case 0: {
                    return SearchHandymenFragment.newInstance();
                }
                case 1: {
                    return BuyerRequestsFragment.newInstance(user);
                }
                default: {
                    return HandymanProfileFragment.newInstance(user);
                }
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
