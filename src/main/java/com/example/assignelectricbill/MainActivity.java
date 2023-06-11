package com.example.assignelectricbill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etkWh;
    EditText etrebate;
    Button btnCalculate;
    Button btnClear;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etkWh = findViewById(R.id.electric);
        etrebate = findViewById(R.id.rebate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvOutput = findViewById(R.id.tvOutput);

        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCalculate:

                String kWhString = etkWh.getText().toString();
                if (kWhString.isEmpty()) {
                    etkWh.setError("Please enter the number of electrical units");
                    return;
                }

                String rebateString = etrebate.getText().toString();
                if (rebateString.isEmpty()) {
                    etrebate.setError("Please enter the rebate percentage");
                    return;
                }

                try {
                    double kWh = Double.parseDouble(kWhString);
                    double rebatePercentage = Double.parseDouble(rebateString);

                    // Validate input numbers
                    if (kWh <= 0 || rebatePercentage < 0 || rebatePercentage > 5) {
                        throw new IllegalArgumentException("Invalid input values");
                    }

                    // Calculate the electricity bill
                    double bill = calculateElectricityBill(kWh, rebatePercentage);

                    // Limit decimal places for the calculated kWh value
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    String formattedBill = decimalFormat.format(bill);

                    // Format the result as a receipt
                    String receipt = "Electricity Bill Receipt\n\n" +
                            "Units Used: " + kWh + " kWh\n" +
                            "Rebate Percentage: " + rebatePercentage + "%\n" +
                            "------------------------\n" +
                            "Total Charges: RM " + formattedBill + "\n" +
                            "------------------------";

                    // Display the receipt
                    tvOutput.setText(receipt);
                } catch (NumberFormatException e) {
                    // Handle invalid number format
                    tvOutput.setText("Invalid input values");
                } catch (IllegalArgumentException e) {
                    // Handle invalid input values
                    tvOutput.setText(e.getMessage());
                }
                break;

            case R.id.btnClear:
                etkWh.setText("");
                etrebate.setText("");
                tvOutput.setText("");
                break;
        }
    }

    private double calculateElectricityBill(double kWh, double rebatePercentage) {
        double block1 = 200;
        double block2 = 300;
        double block3 = 600;
        double block4 = 900;

        double charge1 = 0.218;
        double charge2 = 0.334;
        double charge3 = 0.516;
        double charge4 = 0.546;

        double bill = 0;

        if (kWh <= block1) {
            bill = kWh * charge1;
        } else if (kWh <= block2) {
            bill = (block1 * charge1) + ((kWh - block1) * charge2);
        } else if (kWh <= block3) {
            bill = (block1 * charge1) + ((block2 - block1) * charge2) + ((kWh - block2) * charge3);
        } else if (kWh <= block4) {
            bill = (block1 * charge1) + ((block2 - block1) * charge2) + ((block3 - block2) * charge3)
                    + ((kWh - block3) * charge4);
        } else {
            bill = (block1 * charge1) + ((block2 - block1) * charge2) + ((block3 - block2) * charge3)
                    + ((block4 - block3) * charge4) + ((kWh - block4) * charge4);
        }

        // Calculate rebate amount
        double rebateAmount = bill * (rebatePercentage / 100);

        // Apply rebate
        bill -= rebateAmount;

        return bill;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                //Toast.makeText(this, "This is about", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}