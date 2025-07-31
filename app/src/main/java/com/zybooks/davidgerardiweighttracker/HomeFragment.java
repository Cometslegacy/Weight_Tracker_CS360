package com.zybooks.davidgerardiweighttracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppDatabase db;
    private WeightAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = AppDatabase.getInstance(requireContext());

        AddWeightButton(view);
        recyclerUpdate(view);
        updateCurrentWeight(view);
        updateTargetWeight(view);

        // Inflate the layout for this fragment
        return view;
    }

    //Functions--------------------------------------------------------------------------------------

    private void AddWeightButton(View view) {
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Enter Today's Weight");

            final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String weightStr = input.getText().toString().trim();
                if (!weightStr.isEmpty()) {
                    float weight = Float.parseFloat(weightStr);
                    String today = LocalDate.now().toString();

                    WeightEntry entry = new WeightEntry();
                    entry.date = today;
                    entry.weight = weight;

                    // Insert in background thread
                    new Thread(() -> {
                        AppDatabase db = AppDatabase.getInstance(requireContext());
                        db.weightDao().insertWeight(entry);

                        requireActivity().runOnUiThread(() -> {
                            refreshRecycler();
                        });
                    }).start();

                    Toast.makeText(requireContext(), "Weight saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "No weight entered", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }


    private void recyclerUpdate(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.dataRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<WeightEntry> entries = db.weightDao().getAllWeights();

        adapter = new WeightAdapter(entries, entry -> {
            // Run delete in background thread
            new Thread(() -> {
                db.weightDao().deleteWeight(entry);
                requireActivity().runOnUiThread(() -> recyclerUpdate(view));  // refresh after deletion
            }).start();
        });

        recyclerView.setAdapter(adapter);
        updateCurrentWeight(view);
    }


    private void refreshRecycler() {
        List<WeightEntry> updatedList = db.weightDao().getAllWeights();
        adapter.setWeightList(updatedList);
        updateCurrentWeight(getView());
    }

    private void updateCurrentWeight(View view){
        TextView currentWeightTextView = view.findViewById(R.id.currentWeightText);

        WeightEntry latestEntry = db.weightDao().getLastEntry();

        if (latestEntry != null){
            String currentWeight = "Current Weight:\n" + latestEntry.weight;
            currentWeightTextView.setText(currentWeight);
        }
        else {
            currentWeightTextView.setText("Current Weight: \n XXX");
        }


    }

    private void updateTargetWeight(View view) {
        TextView targetWeightTextview = view.findViewById(R.id.targetWeightText);

        TargetWeight target = db.targetDao().getTarget();

        if (target != null) {
            String targetWeight = "Target Weight:\n" + target.target;
            targetWeightTextview.setText(targetWeight);
        }
        else {
            targetWeightTextview.setText("Please set target weight in account settings.");
        }
    }


}