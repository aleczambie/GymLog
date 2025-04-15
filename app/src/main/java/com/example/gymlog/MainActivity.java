package com.example.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GymLogRepository repository;

    public static final String TAG = "AZ_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getInformationFromDisplay();
                insertGymlogRecord();
                updateDisplay();
                return true;
            }
        });
    }

    private void insertGymlogRecord(){
        GymLog log = new GymLog(mExercise,mWeight,mReps);
        repository.insertGymLog(log);
    }

    private void updateDisplay(){
        String currentInfo = binding.logDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US,"Exercise:%s%nWeigh:t%.2f%nReps:%s%n=-=-=-=%n%s", mExercise,mWeight,mReps, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
        Log.i(TAG,repository.getAllLogs().toString());
    }

    private void getInformationFromDisplay(){
        mExercise = binding.exerciseInputEditText.getText().toString();
        try{
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from Weight edit text.");
        }

        try{
            mReps =Integer.parseInt(binding.repInputEditText.getText().toString());
        } catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from Weight edit text.");
        }

    }
}