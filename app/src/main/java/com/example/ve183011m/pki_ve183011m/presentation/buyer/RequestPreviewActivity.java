package com.example.ve183011m.pki_ve183011m.presentation.buyer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityRequestPreviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPaymentMethodBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRatingBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestReviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestUrgencyBinding;

public class RequestPreviewActivity extends AppCompatActivity {

    private ActivityRequestPreviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_preview);

        binding.requestPreviewHolder.addView(inflatePreviewItemView("Handyman", "John Doe"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Phone number", "064578349"));
        binding.requestPreviewHolder.addView(inflatePreviewUrgencyView(25));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Job", "Plumbing"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Price", "300$"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Start date", "Thu, Oct 19 2018"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("End date", "Thu, Oct 21 2018"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Address", "South boulevard 161"));
        binding.requestPreviewHolder.addView(inflatePreviewItemView("Status", "Accepted"));
        binding.requestPreviewHolder.addView(inflatePreviewPaymentView("Card"));
        binding.requestPreviewHolder.addView(inflatePreviewRateView(2.5f));
        binding.requestPreviewHolder.addView(inflatePreviewReviewView("Example comment"));
    }

    private View inflatePreviewItemView(String title, String value) {
        ViewRequestPreviewItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_preview_item, null, false);
        itemBinding.title.setText(title);
        itemBinding.value.setText(value);

        return itemBinding.getRoot();
    }

    private View inflatePreviewPaymentView(String paymentType) {
        ViewRequestPaymentMethodBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_payment_method, null, false);
        itemBinding.paymentMethod.setText(paymentType);

        itemBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return itemBinding.getRoot();
    }

    private View inflatePreviewUrgencyView(int percentage) {
        ViewRequestUrgencyBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_urgency, null, false);
        itemBinding.urgencySeekBar.setProgress(percentage);

        itemBinding.urgencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return itemBinding.getRoot();
    }

    private View inflatePreviewRateView(float rating) {
        ViewRequestRatingBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_rating, null, false);

        itemBinding.ratingBar.setRating(rating);
        return itemBinding.getRoot();
    }

    private View inflatePreviewReviewView(String review) {
        ViewRequestReviewBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_review, null, false);

        itemBinding.reviewEditText.setText(review);

        itemBinding.reviewEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        return itemBinding.getRoot();
    }

}
