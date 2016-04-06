package com.workspaceit.themallbd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.workspaceit.themallbd.R;
import com.workspaceit.themallbd.activity.CheckoutActivity;
import com.workspaceit.themallbd.utility.CheckOutInfoSession;
import com.workspaceit.themallbd.utility.MakeToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CheckoutViewFragment extends Fragment implements View.OnClickListener {
    private Button confrimPayemtBtn;
    CheckOutInfoSession checkOutInfoSession;
    private EditText emailText;
    private EditText fnameText;
    private EditText lnameText;
    private Spinner countrySpinner;
    private EditText addressText;
    private EditText telephoneText;

    public CheckoutViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout_view, container, false);

        confrimPayemtBtn=(Button)view.findViewById(R.id.confrim_payemt_method);
        emailText=(EditText)view.findViewById(R.id.checkout_email);
        fnameText=(EditText)view.findViewById(R.id.checkout_fname);
        lnameText=(EditText)view.findViewById(R.id.checkout_lname);
        countrySpinner=(Spinner)view.findViewById(R.id.county_spinner);
        addressText=(EditText)view.findViewById(R.id.checkout_address);
        telephoneText=(EditText)view.findViewById(R.id.checkout_telephone);

        confrimPayemtBtn.setOnClickListener(this);
        checkOutInfoSession=new CheckOutInfoSession(getActivity());

        if(checkOutInfoSession.checkIsset()){
            this.setValue();


        }

        return view;


    }


    @Override
    public void onClick(View v) {

        if(v==confrimPayemtBtn) {
            if (checkInputValidity()) {
                this.saveInformation();

                CheckoutActivity.tabFlag = 2;
                CheckoutActivity.mViewPager.setCurrentItem(2);

            }
        }

    }


    private void setValue(){
        emailText.setText(checkOutInfoSession.getEmail());
        fnameText.setText(checkOutInfoSession.getFname());
        lnameText.setText(checkOutInfoSession.getLname());
        countrySpinner.setSelection(checkOutInfoSession.getCountryPosition());
        addressText.setText(checkOutInfoSession.getAddress());
        telephoneText.setText(checkOutInfoSession.getTelephone());
    }
    private void saveInformation(){

        checkOutInfoSession.saveDataInPreference(emailText.getText().toString(),fnameText.getText().toString(),
                lnameText.getText().toString(),countrySpinner.getSelectedItem().toString(),countrySpinner.getSelectedItemPosition(),
                addressText.getText().toString(),telephoneText.getText().toString());

       
    }

    private boolean checkInputValidity(){
        String email=emailText.getText().toString();
        CharSequence input=email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        String country=countrySpinner.getSelectedItem().toString();

        if(!matcher.matches()){
            MakeToast.showToast(getActivity(),"Please Enter a valid Email ID");
            emailText.requestFocus();
            return false;

        }else if(fnameText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "First Name is Required");
            fnameText.requestFocus();
            return false;
        }else if(lnameText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Last Name is Required");
            lnameText.requestFocus();
            return false;
        }else if(country.equals("Country")){
            MakeToast.showToast(getActivity(), "Please select a country");
            countrySpinner.requestFocus();
            return false;
        }else if(addressText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Delivery Adress is required");
            addressText.requestFocus();
            return false;
        } else if (telephoneText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(),"Telephone Number is required");
            telephoneText.requestFocus();
            return false;
        }


        return true;
    }
}
