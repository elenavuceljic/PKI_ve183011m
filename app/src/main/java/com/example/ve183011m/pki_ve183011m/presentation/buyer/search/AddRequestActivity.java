package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityAddRequestBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewInputRowBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewProfileButtonsBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestDateBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestPreviewItemBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestRadioBinding;
import com.example.ve183011m.pki_ve183011m.databinding.ViewRequestSpinnerBinding;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.ve183011m.pki_ve183011m.presentation.buyer.profile.HandymanProfileFragment.HANDYMAN;
import static com.example.ve183011m.pki_ve183011m.presentation.login.LogInActivity.USER;

public class AddRequestActivity extends AppCompatActivity {

    private ActivityAddRequestBinding binding;

    private Handyman handyman;

    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handyman = (Handyman) getIntent().getSerializableExtra(HANDYMAN);
        User user = (User) getIntent().getSerializableExtra(USER);

        request = new Request(handyman, user);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_request);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        inflateView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inflateView() {
        binding.requestHolder.addView(inflatePreviewItemView(R.string.handyman_lc, handyman.getFullName()));
        binding.requestHolder.addView(inflatePreviewItemView(R.string.phone_number, handyman.getTelephone()));
        binding.requestHolder.addView(inflateRowItemView());
        binding.requestHolder.addView(inflateRequestDateView(R.string.start_date, request.getStartDate(), R.string.enter_date));
        binding.requestHolder.addView(inflateRequestDateView(R.string.end_date, request.getEndDate(), R.string.enter_date));
        binding.requestHolder.addView(inflateRequestPaymentItemView());
        binding.requestHolder.addView(inflateRequestJobItemView());
        binding.requestHolder.addView(inflateSaveView());
    }

    private View inflateSaveView() {
        ViewProfileButtonsBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_profile_buttons, null, false);

        itemBinding.cancel.setVisibility(View.GONE);
        itemBinding.save.setText(R.string.add);
        itemBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request.getAddress().length() > 0 && (request.getEndDate().after(request.getStartDate()) || request.getStartDate() == request.getEndDate())) {
                    RequestManager.getInstance().addRequest(request);
                    finish();
                } else {
                    binding.requestHolder.getChildAt(2).requestFocus();
                    binding.requestHolder.getChildAt(2).clearFocus();

                    binding.requestHolder.getChildAt(4).requestFocus();
                    binding.requestHolder.getChildAt(4).clearFocus();

                    Toast.makeText(AddRequestActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return itemBinding.getRoot();

    }

    private View inflateRequestJobItemView() {
        ViewRequestSpinnerBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_spinner, null, false);

        itemBinding.spinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, handyman.getSkills()));
        itemBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setJob(handyman.getSkills().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                request.setJob(handyman.getSkills().get(0));
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

    private View inflateRequestPaymentItemView() {
        ViewRequestRadioBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_radio, null, false);

        itemBinding.rbPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cash) {
                    request.setPayableByCash(true);
                } else {
                    request.setPayableByCash(false);
                }
            }
        });

        return itemBinding.getRoot();
    }

    private View inflateRowItemView() {
        final ViewInputRowBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_input_row, null, false);

        itemBinding.title.setText(R.string.address);
        itemBinding.inputEditText.setText("");

        itemBinding.inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    itemBinding.inputEditTextWrapper.setError(null);
                } else {
                    if (itemBinding.inputEditText.getText() == null || itemBinding.inputEditText.getText().length() == 0) {
                        itemBinding.inputEditTextWrapper.setError(getString(R.string.enter_address));
                    }
                }
            }
        });

        itemBinding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                request.setAddress(s.toString());
            }
        });

        return itemBinding.getRoot();
    }

    private View inflateRequestDateView(final int title, Date value, final int error) {
        final ViewRequestDateBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.view_request_date, null, false);

        itemBinding.title.setText(title);

        String myFormat = "MM/dd/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        itemBinding.dateEt.setText(sdf.format(value));

        final Calendar myCalendar = Calendar.getInstance();

        itemBinding.dateEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    itemBinding.dateEt.setError(null);
                } else {
                    if (title == R.string.end_date) {
                        if (request.getEndDate().before(request.getStartDate()) && request.getEndDate() != request.getStartDate()) {
                            itemBinding.dateEt.setError("End date must be before start date");
                        }
                    }
                }
            }
        });
        itemBinding.dateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (title == R.string.end_date) {
                    request.setEndDate(myCalendar.getTime());
                } else {
                    request.setStartDate(myCalendar.getTime());
                }

                if (title == R.string.end_date) {
                    if (request.getEndDate().before(request.getStartDate()) && request.getEndDate() != request.getStartDate()) {
                        itemBinding.dateEt.setError("End date must be before start date");
                    }
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                itemBinding.dateEt.setText(sdf.format(myCalendar.getTime()));
            }

        };


        itemBinding.dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddRequestActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return itemBinding.getRoot();
    }

}
