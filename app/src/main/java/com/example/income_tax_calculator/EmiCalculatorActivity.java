package com.example.income_tax_calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmiCalculatorActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calculator);

        dbHelper = new DatabaseHelper(this);

        EditText dateField = findViewById(R.id.editTextDate);
        EditText principalAmount = findViewById(R.id.editTextNumberDecimal);
        EditText annualInterestRate = findViewById(R.id.editTextNumber);
        EditText loanTenure = findViewById(R.id.editTextNumber2);
        TextView emiResult = findViewById(R.id.textView8);
        Button calculateButton = findViewById(R.id.button4);


        // Set current date automatically
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        dateField.setText(currentDate);

        calculateButton.setOnClickListener(v -> {
            double principal = Double.parseDouble(principalAmount.getText().toString());
            double annualInterest = Double.parseDouble(annualInterestRate.getText().toString());
            int tenure = Integer.parseInt(loanTenure.getText().toString());

            double monthlyInterestRate = (annualInterest / 12) / 100;
            double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenure)) /
                    (Math.pow(1 + monthlyInterestRate, tenure) - 1);

            emiResult.setText(String.format(Locale.getDefault(), "%.2f", emi));

            // Save calculation to database
            dbHelper.addEmiCalculation(currentDate, principal, annualInterest, tenure, emi);
        });


    }
}