package com.example.ve183011m.pki_ve183011m.presentation.buyer.requests;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityRequestPreviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPaymentMethodBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRatingBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestSaveBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestUrgencyBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.text.SimpleDateFormat;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment.REQUEST;

public class BuyerRequestPreviewActivity extends AppCompatActivity implements PaymentFragment.PaymentFragmentCallback {

    private ActivityRequestPreviewBinding binding;

    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = (Request) getIntent().getSerializableExtra(REQUEST);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_preview);

        inflateView();

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inflateView() {
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.handyman_lc, request.getHandyman().getFullName()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.phone_number, request.getHandyman().getTelephone()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.urgency, request.getUrgency().name()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.job, request.getJob().getName()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.price, String.valueOf(request.getJob().getPrice())));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.start_date, df.format(request.getStartDate())));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.end_date, df.format(request.getEndDate())));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.address, request.getAddress()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.status, request.getStatus().name()));
        binding.requestPreviewHolder.addView(inflatePreviewPaymentView(request.isPayableByCash() ? "Cash" : "Card", (!request.isPayableByCash() && !request.isPayed())));
        binding.requestPreviewHolder.addView(inflatePreviewRateView(0.0f));
        binding.requestPreviewHolder.addView(inflatePreviewReviewView(""));
        binding.requestPreviewHolder.addView(inflatePreviewSaveReviewView());
    }

    private View inflatePreviewSaveReviewView() {
        ViewRequestSaveBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_save, null, false);
        itemBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ((RatingBar)binding.requestPreviewHolder.findViewById(R.id.rating_bar)).getRating();
                String review = ((EditText)binding.requestPreviewHolder.getChildAt(11).findViewById(R.id.input_edit_text)).getText().toString();
                User user = request.getBuyer();
                request.getHandyman().addReview(new Handyman.Review(review, user));
                request.getHandyman().setRating(rating);
                Toast.makeText(getBaseContext(), "Review saved", Toast.LENGTH_LONG).show();
            }
        });

        return itemBinding.getRoot();
    }

    private View inflatePreviewItemView(int title, String value) {
        ViewRequestPreviewItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_preview_item, null, false);
        itemBinding.title.setText(title);
        itemBinding.value.setText(value);

        return itemBinding.getRoot();
    }

    private View inflatePreviewPaymentView(String paymentType, boolean showButton) {
        ViewRequestPaymentMethodBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_payment_method, null, false);
        itemBinding.paymentMethod.setText(paymentType);

        itemBinding.pay.setVisibility(showButton ? View.VISIBLE : View.GONE);
        itemBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = PaymentFragment.newInstance(request);
                newFragment.show(getSupportFragmentManager(), "payment");
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
        ViewInputRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_input_row, null, false);

        itemBinding.inputEditText.setText(review);

        itemBinding.inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        return itemBinding.getRoot();
    }

    @Override
    public void onRequestPaid(Request request) {
        request.setPayed(true);
        binding.requestPreviewHolder.removeAllViews();
        inflateView();
    }
}
