package com.example.ve183011m.pki_ve183011m.presentation.handyman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.model.Request;

import java.util.ArrayList;
import java.util.List;

public class HandymanSearchFragment extends DialogFragment {

    private HandymanSearchFragmentCallback mListener;

    public HandymanSearchFragment() {
        // Required empty public constructor
    }

    public static HandymanSearchFragment newInstance() {
        return new HandymanSearchFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_handyman_search, null);

        builder.setTitle(R.string.search_by)
                .setView(view)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        List<Request.Urgency> urgencyList = new ArrayList<>();
                        float distance = 5;

                        if (((CheckBox)view.findViewById(R.id.cb_high)).isChecked()) {
                            urgencyList.add(Request.Urgency.HIGH);
                        }
                        if (((CheckBox)view.findViewById(R.id.cb_medium)).isChecked()) {
                            urgencyList.add(Request.Urgency.MEDIUM);
                        }
                        if (((CheckBox)view.findViewById(R.id.cb_low)).isChecked()) {
                            urgencyList.add(Request.Urgency.LOW);
                        }
                        Editable text = ((EditText) view.findViewById(R.id.distance_et)).getText();
                        if (text != null && text.length() > 0) {
                            distance = Float.parseFloat(text.toString());
                        }
                        RequestManager.setDistanceFilter(distance);
                        RequestManager.setUrgencyFilter(urgencyList);
                        mListener.onSearchBy(urgencyList, distance);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HandymanSearchFragment.this.dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandymanSearchFragmentCallback) {
            mListener = (HandymanSearchFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HandymanSearchFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface HandymanSearchFragmentCallback {
        void onSearchBy(List<Request.Urgency> urgencyList, float lessThanDistance);
    }
}
