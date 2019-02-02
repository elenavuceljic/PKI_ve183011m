package com.example.ve183011m.pki_ve183011m.presentation.buyer;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
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
import com.example.ve183011m.pki_ve183011m.databinding.ActivityBuyerMainBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.BuyerRequestPreviewActivity;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.BuyerRequestsFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.search.AddRequestActivity;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.search.HandymanPreviewActivity;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.search.SearchHandymenFragment;
import com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment.HANDYMAN;
import static com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment.REQUEST;
import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class BuyerMainActivity extends AppCompatActivity implements SearchHandymenFragment.SearchHandymenFragmentCallback, BuyerRequestsFragment.BuyerRequestsFragmentCallback, HandymanProfileFragment.HandymanProfileFragmentCallback, PaymentFragment.PaymentFragmentCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ActivityBuyerMainBinding binding;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buyer_main);

        user = (User) getIntent().getSerializableExtra(USER);

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
                BuyerRequestsFragment fragment = (BuyerRequestsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + binding.container.getCurrentItem());
                RequestManager requestManager = RequestManager.getInstance();
                switch (menuItem.getItemId()) {
                    case R.id.active_request:
                        fragment.adapter.active = true;
                        fragment.adapter.requestsList = requestManager.getActiveRequestsForBuyer(user);
                        fragment.adapter.notifyDataSetChanged();
                        return true;
                    case R.id.closed_requests:
                        fragment.adapter.active = false;
                        fragment.adapter.requestsList = requestManager.getClosedRequestsForBuyer(user);
                        fragment.adapter.notifyDataSetChanged();
                        return true;
                }

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        Intent intent = new Intent(this, BuyerRequestPreviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(REQUEST, request);
        startActivity(intent);
    }

    @Override
    public void onPayRequest(Request request) {
        DialogFragment newFragment = PaymentFragment.newInstance(request);
        newFragment.show(getSupportFragmentManager(), "payment");
    }

    @Override
    public void onHandymanSaved(User handyman) {
        Toast.makeText(this, "Saved changes", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onHandymanSelected(Handyman handyman) {
        Intent intent = new Intent(this, HandymanPreviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER, user);
        intent.putExtra(HANDYMAN, handyman);
        startActivity(intent);
    }

    @Override
    public void onAddRequestFor(Handyman handyman) {
        Intent intent = new Intent(this, AddRequestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER, user);
        intent.putExtra(HANDYMAN, handyman);
        startActivity(intent);
    }

    @Override
    public void onRequestPaid(Request request) {
        request.setPayed(true);
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
                    return SearchHandymenFragment.newInstance(user);
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
