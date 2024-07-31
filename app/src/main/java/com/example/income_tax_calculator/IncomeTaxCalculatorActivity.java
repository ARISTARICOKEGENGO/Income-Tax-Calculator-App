package com.example.income_tax_calculator; // Updated package name to match file path

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class IncomeTaxCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_tax_calculator);

        // Initialize UI components
        EditText editTextGrossIncome = findViewById(R.id.editTextNumberDecimal2);
        EditText editTextPersonalRelief = findViewById(R.id.editTextNumber4);
        EditText editTextInsuranceRelief = findViewById(R.id.editTextNumber5);
        EditText editTextHousingRelief = findViewById(R.id.editTextNumber6);
        EditText editTextPensionContribution = findViewById(R.id.editTextNumber7);
        EditText editTextTaxableIncome = findViewById(R.id.editTextNumber8);
        EditText editTextPayableTax = findViewById(R.id.editTextNumber9);
        EditText editTextBasicSalary = findViewById(R.id.editTextNumber10);
        Button buttonCalculate = findViewById(R.id.button5);

        // Set onClickListener for calculate button using lambda
        buttonCalculate.setOnClickListener(v -> {
            // Retrieve input values
            double grossIncome = Double.parseDouble(editTextGrossIncome.getText().toString());
            double personalRelief = Double.parseDouble(editTextPersonalRelief.getText().toString());
            double insuranceRelief = Double.parseDouble(editTextInsuranceRelief.getText().toString());
            double housingRelief = Double.parseDouble(editTextHousingRelief.getText().toString());
            double pensionContribution = Double.parseDouble(editTextPensionContribution.getText().toString());

            // Calculate total deductions
            double totalDeductions = personalRelief + insuranceRelief + housingRelief + pensionContribution;

            // Calculate taxable income
            double taxableIncome = grossIncome - totalDeductions;

            // Calculate payable tax
            double payableTax = calculatePayableTax(taxableIncome);

            // Calculate basic salary
            double basicSalary = taxableIncome - payableTax;

            // Display results
            editTextTaxableIncome.setText(String.format(Locale.getDefault(), "%.2f", taxableIncome));
            editTextPayableTax.setText(String.format(Locale.getDefault(), "%.2f", payableTax));
            editTextBasicSalary.setText(String.format(Locale.getDefault(), "%.2f", basicSalary));
        });
    }

    private double calculatePayableTax(double taxableIncome) {
        // Apply tax bands and rates
        if (taxableIncome <= 288000) {
            return taxableIncome * 0.10;
        } else if (taxableIncome <= 388000) {
            return (288000 * 0.10) + ((taxableIncome - 288000) * 0.25);
        } else if (taxableIncome <= 6008000) {
            return (288000 * 0.10) + (100000 * 0.25) + ((taxableIncome - 388000) * 0.30);
        } else if (taxableIncome <= 9600000) {
            return (288000 * 0.10) + (100000 * 0.25) + (5612000 * 0.30) + ((taxableIncome - 6008000) * 0.325);
        } else {
            return (288000 * 0.10) + (100000 * 0.25) + (5612000 * 0.30) + (3600000 * 0.325) + ((taxableIncome - 9600000) * 0.35);
        }
    }
}
