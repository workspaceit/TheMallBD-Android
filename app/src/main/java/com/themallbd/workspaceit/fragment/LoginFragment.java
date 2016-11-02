package com.themallbd.workspaceit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.themallbd.workspaceit.preferences.SessionManager;
import com.workspaceit.themall.R;
import com.themallbd.workspaceit.asynctask.LoginAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private SessionManager mSessionManager;
    EditText email_et,password_et;
    Button login_btn;

    String email,password;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login2, container, false);
        email_et = (EditText) view.findViewById(R.id.login_email);
        password_et = (EditText) view.findViewById(R.id.login_password);
        login_btn = (Button) view.findViewById(R.id.login_button);

        this.mSessionManager = new SessionManager(getActivity());
        login_btn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == login_btn)
        {
            if (mSessionManager.checkLogin())
            {
                getActivity().finish();
            }
            else {
                email = email_et.getText().toString();
                password = password_et.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getContext(), "Email or Password is required", Toast.LENGTH_SHORT).show();
                } else {
                    new LoginAsyncTask(getActivity()).execute(email, password);
                }
            }
        }
    }
}
