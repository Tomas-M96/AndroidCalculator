package com.example.basiccalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText resultText;
    private EditText inputText;
    private TextView displayOperation;

    private final Button[] numberButtons = new Button[11];
    private final Button[] operandButtons = new Button[7];

    //Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    private Toast toast;
    private View.OnClickListener inputListener, operandListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.txtViewResult);
        inputText = findViewById(R.id.txtViewCalculation);

        assignButtons();
        assignListeners();

        toast = Toast.makeText(getApplicationContext(),
                  "Invalid Input",
                      Toast.LENGTH_SHORT);

        for (Button button : numberButtons)
        {
            button.setOnClickListener(inputListener);
        }

        for (Button button : operandButtons)
        {
            button.setOnClickListener(operandListener);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("PendingOperation", pendingOperation);
        if (operand1 != null)
            outState.putDouble("Operand1", operand1);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString("PendingOperation");
        operand1 = savedInstanceState.getDouble("Operand1");
        displayOperation.setText(pendingOperation);
    }

    private void assignListeners() {
        inputListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                inputText.append(b.getText().toString());
            }
        };

        operandListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                btn.setPressed(true);
                String operator = btn.getText().toString();
                String value = inputText.getText().toString();

                switch (operator)
                {
                    case "Clear":
                        inputText.setText("");
                        operand1 = null;
                        resultText.setText("");
                        break;
                    case "Back":
                        try {
                            String input = inputText.getText().toString();
                            inputText.setText(input.substring(0, input.length() - 1));
                        } catch (IndexOutOfBoundsException e) {
                            toast.setText("There is nothing to delete.");
                            toast.show();
                        }
                        break;
                    case "=":
                        performOperation();
                        break;
                    case "+":
                        Double doubleValue = Double.valueOf(value);
                        performOperation(doubleValue, operator);
                        break;
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double value, String operation)
    {
        if (operand1 == null)
        {
            operand1 = value;
            resultText.setText(operand1.toString());
        }
        else {
            switch (operation)
            {
                case "+":
                    operand1 += value;
                    break;
            }
        }

        inputText.setText("");
    }

    private void performOperation()
    {
        String result = String.valueOf(operand1);
        int decimalPoint = result.indexOf(".");

        if(result.substring(decimalPoint).equals(".0"))
            resultText.setText(result.substring(0, decimalPoint));
        else
            resultText.setText(result);

        inputText.setText("");
    }

    private void assignButtons()
    {
        numberButtons[0] = findViewById(R.id.btn0);
        numberButtons[1] = findViewById(R.id.btn1);
        numberButtons[2] = findViewById(R.id.btn2);
        numberButtons[3] = findViewById(R.id.btn3);
        numberButtons[4] = findViewById(R.id.btn4);
        numberButtons[5] = findViewById(R.id.btn5);
        numberButtons[6] = findViewById(R.id.btn6);
        numberButtons[7] = findViewById(R.id.btn7);
        numberButtons[8] = findViewById(R.id.btn8);
        numberButtons[9] = findViewById(R.id.btn9);
        numberButtons[10] = findViewById(R.id.btnDecimal);

        operandButtons[0] = findViewById(R.id.btnAdd);
        operandButtons[1] = findViewById(R.id.btnSubtract);
        operandButtons[2] = findViewById(R.id.btnMultiply);
        operandButtons[3] = findViewById(R.id.btnDivide);
        operandButtons[4] = findViewById(R.id.btnEquals);
        operandButtons[5] = findViewById(R.id.btnClear);
        operandButtons[6] = findViewById(R.id.btnBack);
    }
}