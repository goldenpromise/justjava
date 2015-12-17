package com.example.android.promisejustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v("MainActivity", "Has chocolate fudge: " + hasChocolate);

        CheckBox nutmegCheckBox = (CheckBox) findViewById(R.id.nutmeg_checkbox);
        boolean hasNutmeg = nutmegCheckBox.isChecked();
        Log.v("MainActivity", "Has nutmeg: " + hasNutmeg);

        CheckBox vanillaPowderCheckBox = (CheckBox) findViewById(R.id.vanilla_powder_checkbox);
        boolean hasVanillaPowder = vanillaPowderCheckBox.isChecked();
        Log.v("MainActivity", "Has vanilla powder: " + hasVanillaPowder);

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasNutmeg, hasVanillaPowder);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasNutmeg, hasVanillaPowder);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addNutmeg, boolean addVanillaPowder) {
        String priceMessage= "\nName: "+ name;
        priceMessage+= "\nAdd whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate fudge? " + addChocolate;
        priceMessage+= "\nAdd nutmeg? " + addNutmeg;
        priceMessage+= "\nAdd vanilla powder? " + addVanillaPowder;
        priceMessage= priceMessage + "\nQuantity: " + quantity;
        priceMessage= priceMessage + "\nTotal: $" + price;
        priceMessage= priceMessage + "\nThank You!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    /*private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);*/
    }

    int quantity= 2;
    public void increment(View view){
        if (quantity >= 35) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 35 coffees", Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view){

        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();

            return ;
        }

        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addNutmeg, boolean addVanillaPowder) {
        int basePrice =5;

        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }

        if (addNutmeg){
            basePrice = basePrice + 3;
        }

        if (addVanillaPowder){
            basePrice = basePrice + 3;
        }

        return quantity * basePrice;
    }

}
