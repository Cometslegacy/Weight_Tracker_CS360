package com.zybooks.davidgerardiweighttracker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        setTargetWeight(view);
        enableSmsButton(view);

        //Toggle SMS
/*        Button sms_button = view.findViewById(R.id.sms_button);
        sms_button.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Enable SMS?")
                    .setMessage("We use SMS to notify you about important updates like hitting your goal.")
                    .setPositiveButton("Yes", (dialog, which) -> checkSmsPermission())
                    .setNegativeButton("No", null)
                    .show();
        });*/


        return view;
    }

    public void enableSmsButton(View view) {
        Button enableSmsButton = view.findViewById(R.id.sms_button);
        enableSmsButton.setOnClickListener((v -> {
            checkSmsPersmission();
        }));
    }

    private void checkSmsPersmission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            sendSms();
        }
    }

    //TODO SMS verification
    //This doesnt seem to be working, come back later.
    private void sendSms() {

        EditText phoneText = getView().findViewById(R.id.editTextPhone);
        String phoneNumber = String.valueOf(phoneText.getText());

        String message = "This is an example of an SMS sent to number: " + phoneNumber;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(requireContext(), "SMS Sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "SMS Failed to Send", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTargetWeight(View view) {
        Button setTargetButton = view.findViewById(R.id.setTargetButton);
        setTargetButton.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
            builder.setTitle("Enter Today's Weight");

            final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String weightStr = input.getText().toString().trim();
                if (!weightStr.isEmpty()) {
                    float weight = Float.parseFloat(weightStr);
                    new Thread(()-> {
                        AppDatabase db = AppDatabase.getInstance(requireContext());
                        TargetWeight targetWeight = new TargetWeight(weight);
                        db.targetDao().setTarget(targetWeight);
                    }).start();

                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }
}