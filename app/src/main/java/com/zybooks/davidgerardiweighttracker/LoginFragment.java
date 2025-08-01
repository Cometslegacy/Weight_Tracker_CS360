package com.zybooks.davidgerardiweighttracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        skipLoginButton(view);
        registerLoginButton(view);
        loginButton(view);

        return view;
    }

    private void goToMainActivity() {
        // Start MainActivity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

        // Close the current (Login) activity so user can’t go back
        requireActivity().finish();
    }

    private void skipLoginButton(View view) {
        Button skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(v -> {
            goToMainActivity();
        });

    }

    private void registerLoginButton(View view) {
        Button registerButton = view.findViewById(R.id.register_button);
        EditText emailInput = view.findViewById(R.id.editTextTextEmailAddress);
        EditText passwordInput = view.findViewById(R.id.editWebPassword);


        registerButton.setOnClickListener(v-> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(view.getContext(), "You must included an Email AND Password to register an account.", Toast.LENGTH_LONG).show();
                return;
            }

            LoginAccount account = new LoginAccount();
            account.login_email = email;
            account.login_password = password;

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getInstance(view.getContext());
                db.loginAccountDao().insert(account);

                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(view.getContext(),"You have successfully registered your account!", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                });
            });
        });
    }

    private void loginButton(View view) {
        Button loginButton = view.findViewById(R.id.login_button);
        EditText emailInput = view.findViewById(R.id.editTextTextEmailAddress);
        EditText passwordInput = view.findViewById(R.id.editWebPassword);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(view.getContext(), "Please enter your Email AND Password to log in.", Toast.LENGTH_LONG).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getInstance(view.getContext());

                LoginAccount account = db.loginAccountDao().login(email, password);

                new Handler(Looper.getMainLooper()).post(() -> {
                    if (account != null) {
                        Toast.makeText(view.getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    } else {
                        Toast.makeText(view.getContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }


}