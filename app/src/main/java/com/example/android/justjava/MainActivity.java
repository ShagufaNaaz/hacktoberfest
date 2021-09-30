/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity <= 100) {
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You cannot order more than 100 coffees", Toast.LENGTH_SHORT).show();
            quantity = 100;
            displayQuantity(quantity);
        }

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
//            Show an error message as a toast
//            "this" here below is for "this activity"
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById((R.id.name_field));
        String name = nameField.getText().toString();
//      Log.v("MainActivity", "Name :" + name);
        CheckBox ChocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();
//      Log.v("MainActivity", "has chocolate :" + hasChocolate);
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
//      Log.v("MainActivity", "has whipped cream :" + hasWhippedCream);
        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }
//    *********    for showing in log cats with log level = verbose   **********
//        Log.v("MainActivity", "The price is" + price);


    /**
     * Calculates the price of the order.
     *
     * @param addChocolate    is whether the chocolate is added or not
     * @param addWhippedCream is whether the whipped cream is added or not
     * @return total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int price;
//        Price of 1 cup of coffee
        int basePrice = 5;
//        If user chooses whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
//        If user choose chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        price = basePrice * quantity;
        return price;
//        if((addChocolate==true) && (addWhippedCream==true))
//        {
//            price=(basePrice+1+2)*quantity;
//        }
//        else if(addWhippedCream)
//        {
//            price=(basePrice+1)*quantity;
//        }
//        else if(addChocolate)
//        {
//            price=(basePrice+2)*quantity;
//        }
//        else
//        {
//            price=basePrice;
//        }


    }

    /**
     * This method displays the summary of the order on the screen
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants Chocolate topping
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */

//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
