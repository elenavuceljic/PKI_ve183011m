package com.example.ve183011m.pki_ve183011m.presentation.buyer.search;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RatingBar;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.UserManager;
import com.example.ve183011m.pki_ve183011m.databinding.ActivityBuyerSearchBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class BuyerSearchActivity extends AppCompatActivity {

    private ActivityBuyerSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_buyer_search);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        UserManager.setPriceLessThanFilter(0.0f);
        UserManager.setPriceMoreThanFilter(0.0f);
        UserManager.setExperienceFilter(0);
        UserManager.setRatingFilter(0.0f);
        UserManager.setJobFilter("");
        UserManager.setStartDate(null);
        UserManager.setEndDate(null);

        final String[] strings = {"Plumbing", "Electrical installations", "Furniture assembly", "Interior painting", "Air conditioning"};
        binding.spinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, strings));
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserManager.setJobFilter(strings[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String myFormat = "MM/dd/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        final Calendar myCalendar = Calendar.getInstance();

        binding.startDateEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.startDateEt.setError(null);
                }
            }
        });
        binding.endDateEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.endDateEt.setError(null);
                } else {
                    try {
                        if (sdf.parse(binding.endDateEt.getText().toString()).before(sdf.parse(binding.startDateEt.getText().toString())) && sdf.parse(binding.endDateEt.getText().toString()) != sdf.parse(binding.startDateEt.getText().toString())) {
                            binding.endDateEt.setError("End date must be before start date");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.startDateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UserManager.setStartDate(myCalendar.getTime());
            }
        });
        binding.endDateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String myFormat = "MM/dd/yy";
                final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                try {
                    if (myCalendar.getTime().before(sdf.parse(binding.startDateEt.getText().toString())) && myCalendar.getTime() != sdf.parse(binding.startDateEt.getText().toString())) {
                        binding.endDateEt.setError("End date must be before start date");
                    } else {
                        UserManager.setEndDate(myCalendar.getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
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

                binding.startDateEt.setText(sdf.format(myCalendar.getTime()));
            }

        };
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                binding.endDateEt.setText(sdf.format(myCalendar.getTime()));
            }

        };


        binding.startDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BuyerSearchActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.endDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BuyerSearchActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                UserManager.setRatingFilter(rating);
            }
        });

        binding.experienceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UserManager.setExperienceFilter(Integer.valueOf(s.toString()));
            }
        });
        binding.priceFromEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UserManager.setPriceMoreThanFilter(Integer.valueOf(s.toString()));
            }
        });
        binding.priceToEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UserManager.setPriceLessThanFilter(Integer.valueOf(s.toString()));
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
}
