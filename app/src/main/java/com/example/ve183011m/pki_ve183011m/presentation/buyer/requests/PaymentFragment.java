package com.example.ve183011m.pki_ve183011m.presentation.buyer.requests;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.Request;

public class PaymentFragment extends DialogFragment {

    private PaymentFragmentCallback mListener;

    private boolean isInputValid = false;
    private Request request;
    private static final String REQUEST = "request";

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(Request request) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putSerializable(REQUEST, request);
        fragment.setArguments(args);
        return fragment;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            request = (Request) getArguments().getSerializable(REQUEST);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_payment, null);

        final TextInputEditText card_number_et = view.findViewById(R.id.et_card_number);
        final TextInputLayout card_number_wrapper = view.findViewById(R.id.card_number_wrapper);

        final TextInputEditText card_expiration_date_et = view.findViewById(R.id.et_expiration_date);
        final TextInputLayout card_expiration_date_wrapper = view.findViewById(R.id.expiration_date_wrapper);

        final TextInputEditText card_cvv_et = view.findViewById(R.id.et_cvv);
        final TextInputLayout card_cvv_wrapper = view.findViewById(R.id.cvv_wrapper);

        card_number_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    card_number_wrapper.setError(null);
                } else {
                    if (card_number_et.getText() == null || card_number_et.getText().length() == 0) {
                        card_number_wrapper.setError(getString(R.string.enter_card_number));
                        isInputValid = false;
                    }
                }
            }
        });
        card_expiration_date_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    card_expiration_date_wrapper.setError(null);
                } else {
                    if (card_expiration_date_et.getText() == null || card_expiration_date_et.getText().length() == 0) {
                        card_expiration_date_wrapper.setError(getString(R.string.enter_expiration_date));
                        isInputValid = false;
                    }
                }
            }
        });
        card_cvv_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    card_cvv_wrapper.setError(null);
                } else {
                    if (card_cvv_et.getText() == null || card_cvv_et.getText().length() == 0) {
                        card_cvv_wrapper.setError(getString(R.string.enter_cvv));
                        isInputValid = false;
                    }
                }
            }
        });

        builder.setView(view)
                .setPositiveButton(R.string.pay_btn, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PaymentFragment.this.getDialog().cancel();
                    }
                });
        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button payButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                payButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isInputValid = true;
                        card_number_et.requestFocus();
                        card_cvv_et.requestFocus();
                        card_expiration_date_et.requestFocus();
                        payButton.requestFocus();
                        if (isInputValid) {
                            mListener.onRequestPaid(request);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaymentFragmentCallback) {
            mListener = (PaymentFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PaymentFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PaymentFragmentCallback {
        void onRequestPaid(Request request);
    }
}
