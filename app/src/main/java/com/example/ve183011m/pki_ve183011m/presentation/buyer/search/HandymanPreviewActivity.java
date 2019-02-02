package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityHandymanPreviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanJobBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanJobsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanReviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewHandymanReviewsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewProfileButtonsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPaymentMethodBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRatingBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestSaveBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment.HANDYMAN;

public class HandymanPreviewActivity extends AppCompatActivity {

    private ActivityHandymanPreviewBinding binding;

    private Handyman handyman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public void addNewRequest() {
        //TODO
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

        for(Handyman.Review review: reviews) {
            itemBinding.handymanReviewsContainer.addView(inflatePreviewReviewItemView(review.getReview(), review.getReviewer().getUsername()));
        }

        return itemBinding.getRoot();
    }

    private View inflatePreviewJobsView(List<Job> skills) {
        ViewHandymanJobsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_handyman_jobs, null, false);

        for(Job job: skills) {
            itemBinding.handymanJobsContainer.addView(inflatePreviewJobItemView(job.getName(), String.valueOf(job.getPrice())));
        }

        return itemBinding.getRoot();
    }

}
