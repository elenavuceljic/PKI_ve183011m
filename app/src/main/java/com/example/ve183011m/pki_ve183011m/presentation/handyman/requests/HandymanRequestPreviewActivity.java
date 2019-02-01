package com.example.ve183011m.pki_ve183011m.presentation.handyman.requests;

import android.databinding.DataBindingUtil;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityHandymanRequestPreviewBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPaymentMethodBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRatingBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestSaveBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestUrgencyBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment;

import java.text.SimpleDateFormat;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.requests.PaymentFragment.REQUEST;

public class HandymanRequestPreviewActivity extends AppCompatActivity {

    ActivityHandymanRequestPreviewBinding binding;

    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = (Request) getIntent().getSerializableExtra(REQUEST);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_handyman_request_preview);

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
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.handyman_lc, request.getBuyer().getFullName()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.phone_number, request.getBuyer().getTelephone()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.urgency, request.getUrgency().name()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.job, request.getJob().getName()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.price, String.valueOf(request.getJob().getPrice())));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.start_date, df.format(request.getStartDate())));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.end_date, df.format(request.getEndDate())));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.address, request.getAddress()));
        binding.requestPreviewHolder.addView(inflatePreviewItemView(R.string.status, request.getStatus().name()));
        binding.requestPreviewHolder.addView(inflatePreviewPaymentView(request.isPayableByCash() ? "Cash" : "Card"));
    }

    private View inflatePreviewItemView(int title, String value) {
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

        itemBinding.pay.setVisibility(View.GONE);

        return itemBinding.getRoot();
    }

}
