package com.example.income_tax_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class choice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(v -> {
            Intent intent = new Intent(choice.this, emiCalculations.class);
            startActivity(intent);
        });
    }
}