package com.example.ve183011m.pki_ve183011m.presentation.registration;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ve183011m.pki_ve183011m.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationSecondStep.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationSecondStep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationSecondStep extends Fragment {

    private static final String ARG_VM = "REGISTRATION_VM";

    private RegistrationVM registrationVM;

    private OnFragmentInteractionListener mListener;

    public RegistrationSecondStep() {
    }

    public static RegistrationSecondStep newInstance(RegistrationVM registrationVM) {
        RegistrationSecondStep fragment = new RegistrationSecondStep();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VM, registrationVM);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            registrationVM = (RegistrationVM) getArguments().getSerializable(ARG_VM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_second_step, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
