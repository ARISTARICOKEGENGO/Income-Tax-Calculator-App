package com.example.income_tax_calculator;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Locale;

public class emiCalculations extends AppCompatActivity {

    private TextView dateView, principalView, interestRateView, tenureView, emiResultView;
    private TextView prevButton, nextButton;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calculations);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        loadEmiCalculations();

        prevButton.setOnClickListener(v -> moveToPreviousRecord());
        nextButton.setOnClickListener(v -> moveToNextRecord());
    }

    private void initializeViews() {
        dateView = findViewById(R.id.textView27);
        principalView = findViewById(R.id.textView29);
        interestRateView = findViewById(R.id.textView31);
        tenureView = findViewById(R.id.textView33);
        emiResultView = findViewById(R.id.textView35);
        prevButton = findViewById(R.id.textView36);
        nextButton = findViewById(R.id.textView37);
    }

    private void loadEmiCalculations() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            cursor = dbHelper.getEmiCalculations();
            if (cursor != null && cursor.moveToFirst()) {
                displayCurrentRecord();
            }
        } catch (Exception e) {

            // Handle the exception (e.g., show an error message to the user)
        }
    }

    @SuppressLint("Range")
    private void displayCurrentRecord() {
        if (cursor != null && !cursor.isAfterLast()) {
            dateView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMI_DATE)));
            principalView.setText(String.format(Locale.getDefault(), "%.2f", cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMI_PRINCIPAL))));
            interestRateView.setText(String.format(Locale.getDefault(), "%.2f%%", cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMI_INTEREST_RATE))));
            tenureView.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMI_TENURE))));
            emiResultView.setText(String.format(Locale.getDefault(), "%.2f", cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMI_RESULT))));
        }
    }

    private void moveToPreviousRecord() {
        if (cursor != null && !cursor.isFirst() && cursor.moveToPrevious()) {
            displayCurrentRecord();
        }
    }

    private void moveToNextRecord() {
        if (cursor != null && !cursor.isLast() && cursor.moveToNext()) {
            displayCurrentRecord();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}