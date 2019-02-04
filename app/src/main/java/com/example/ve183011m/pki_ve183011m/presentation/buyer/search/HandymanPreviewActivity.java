package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityHandymanPreviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanJobBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanJobsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanReviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanReviewsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRatingBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.util.List;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment.HANDYMAN;
import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class HandymanPreviewActivity extends AppCompatActivity {

    private ActivityHandymanPreviewBinding binding;

    private Handyman handyman;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = (User) getIntent().getSerializableExtra(USER);
        handyman = (Handyman) getIntent().getSerializableExtra(HANDYMAN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_handyman_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRequest();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inflateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.handyman_preview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_share) {
            share();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, handyman.getFullName());

        startActivity(sharingIntent);
    }

    public void addNewRequest() {
        Intent intent = new Intent(this, AddRequestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER, user);
        intent.putExtra(HANDYMAN, handyman);
        startActivity(intent);
    }

    private void inflateView() {
        binding.handymanPreviewContainer.addView(inflatePreviewItemView(R.string.handyman_lc, handyman.getFullName()));
        binding.handymanPreviewContainer.addView(inflatePreviewItemView(R.string.phone_number, handyman.getTelephone()));
        binding.handymanPreviewContainer.addView(inflatePreviewItemView(R.string.years_experience, String.valueOf(handyman.getExperience()) + " years"));
        binding.handymanPreviewContainer.addView(inflatePreviewRateView(handyman.getRating()));
        binding.handymanPreviewContainer.addView(inflatePreviewReviewView(handyman.getReviews()));
        binding.handymanPreviewContainer.addView(inflatePreviewJobsView(handyman.getSkills()));
    }

    private View inflatePreviewItemView(int title, String value) {
        ViewRequestPreviewItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_preview_item, null, false);
        itemBinding.title.setText(title);
        itemBinding.value.setText(value);

        return itemBinding.getRoot();
    }

    private View inflatePreviewJobItemView(String title, String value) {
        ViewHandymanJobBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_handyman_job, null, false);
        itemBinding.title.setText(title);
        itemBinding.value.setText(value);

        return itemBinding.getRoot();
    }

    private View inflatePreviewReviewItemView(String title, String value) {
        ViewHandymanReviewBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_handyman_review, null, false);
        itemBinding.title.setText(title);
        itemBinding.value.setText(value);

        return itemBinding.getRoot();
    }

    private View inflatePreviewRateView(float rating) {
        ViewRequestRatingBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_rating, null, false);

        itemBinding.title.setText(R.string.rating);
        itemBinding.ratingBar.setRating(rating);
        itemBinding.ratingBar.setIsIndicator(true);

        return itemBinding.getRoot();
    }

    private View inflatePreviewReviewView(List<Handyman.Review> reviews) {
        ViewHandymanReviewsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_handyman_reviews, null, false);

        for (Handyman.Review review : reviews) {
            itemBinding.handymanReviewsContainer.addView(inflatePreviewReviewItemView(review.getReview(), review.getReviewer().getUsername()));
        }

        return itemBinding.getRoot();
    }

    private View inflatePreviewJobsView(List<Job> skills) {
        ViewHandymanJobsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_handyman_jobs, null, false);

        for (Job job : skills) {
            itemBinding.handymanJobsContainer.addView(inflatePreviewJobItemView(job.getName(), String.valueOf(job.getPrice())));
        }

        return itemBinding.getRoot();
    }

}
