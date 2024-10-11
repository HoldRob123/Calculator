package com.example.cs460calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;

/**
 * MainActivity class for the Calculator app.
 * This class handles the UI and logic for the calculator.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TextView to display result and solution
    TextView resultTV, solutionTV;
    // Declare buttons for calculator
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonMul, buttonPlus, buttonSub, buttonDivide, buttonEquals;
    MaterialButton buttonAC, buttonDot;


    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set padding for the main layout to accommodate system windows
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize TextViews
        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        // Initialize buttons and set onClick listeners
        assignID(buttonC, R.id.button_c);
        assignID(buttonBracketOpen, R.id.button_open_bracket);
        assignID(buttonBracketClose, R.id.button_closed_bracket);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);

        assignID(buttonMul, R.id.button_multiply);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonSub, R.id.button_sub);
        assignID(buttonDivide, R.id.button_divide);

        assignID(buttonEquals, R.id.button_equals);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDot, R.id.button_dot);

    }

    /**
     * Method to assign buttons and set onClick listeners.
     * @param btn The button to be assigned.
     * @param id The resource ID of the button.
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);

    }
    /**
     * onClick method for handling button clicks.
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataTocalculate = solutionTV.getText().toString();

        // Handle "AC" button click
        if (buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        // Handle "=" button click
        if (buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            return;
        }

        // Handle "C" button click (clear last character)
        if (buttonText.equals("C")) {
            if (!dataTocalculate.isEmpty()) {
                dataTocalculate = dataTocalculate.substring(0, dataTocalculate.length() - 1);
            }
        } else {
            // Append button text to current expression
            dataTocalculate += buttonText;
        }

        // Update the displayed expression
        solutionTV.setText(dataTocalculate);

        // Calculate and display the result
        String finalResult = getResults(dataTocalculate);
        if (!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }
    /**
     * Method to calculate result using Rhino JavaScript engine.
     * @param data The mathematical expression to be evaluated.
     * @return The result of the evaluation or "Err" if there is an error.
     */
    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "javascript", 1, null).toString();
        } catch (Exception e) {
            return "Err";
        }
    }

}
