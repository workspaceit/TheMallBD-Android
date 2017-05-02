package com.themallbd.workspaceit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.themallbd.workspaceit.asynctask.RegistrationAsynTask;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.utility.MakeToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationFragment extends Fragment implements View.OnClickListener {


    public static EditText fnameText;
    public static EditText lnameText;
    public static EditText emailText;
    public static EditText phoneText;
    public static EditText passwordText;
    public static EditText confrimPassText;
    public static Button submitButton;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        fnameText = (EditText) view.findViewById(R.id.fname_reg);
        lnameText = (EditText) view.findViewById(R.id.lname_reg);
        emailText = (EditText) view.findViewById(R.id.email_reg);
        phoneText = (EditText) view.findViewById(R.id.phone_reg);
        passwordText = (EditText) view.findViewById(R.id.password_reg);
        confrimPassText = (EditText) view.findViewById(R.id.confrim_pass_reg);
        submitButton = (Button) view.findViewById(R.id.registration_button);

        submitButton.setOnClickListener(this);


        return view;
    }




    @Override
    public void onClick(View v) {
        if (v == submitButton) {
            if(this.checkInputValidity()){
                new RegistrationAsynTask(getActivity()).execute(lnameText.getText().toString(),lnameText.getText().toString(),
                        emailText.getText().toString(),phoneText.getText().toString(),passwordText.getText().toString(),confrimPassText.getText().toString());

            }

        }
    }


    private boolean checkInputValidity() {
        String email = emailText.getText().toString();
        CharSequence input = email;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);


        if (fnameText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "First Name is Required");
            fnameText.requestFocus();
            return false;
        } else if (lnameText.getText().toString().equals("")) {
            MakeToast.showToast(getActivity(), "Last Name is Required");
            lnameText.requestFocus();
            return false;
        } else if (!matcher.matches()) {
            MakeToast.showToast(getActivity(), "Please Enter a valid Email ID");
            emailText.requestFocus();
            return false;
        }else if(phoneText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Phone number is required");
            phoneText.requestFocus();
            return false;
        }else if(passwordText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "Password is required");
            passwordText.requestFocus();
            return false;
        }else if(passwordText.getText().toString().length()<6){
            MakeToast.showToast(getActivity(), "Password must be at least 6 Characters");
            passwordText.requestFocus();
            return false;

        }else if(confrimPassText.getText().toString().equals("")){
            MakeToast.showToast(getActivity(), "You did not enter confirm password");
            confrimPassText.requestFocus();
            return false;

        } else if(!passwordText.getText().toString().equals(confrimPassText.getText().toString())){
            MakeToast.showToast(getActivity(), "Password Mismatched");
            confrimPassText.requestFocus();
            return false;


        }

        return true;

    }





}