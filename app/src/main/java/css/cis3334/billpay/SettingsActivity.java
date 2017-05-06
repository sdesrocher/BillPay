package css.cis3334.billpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinRoommate;
    Button btnUpdate;
    EditText etAmount, etPriceResult, etAmountResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        spinRoommate = (Spinner) findViewById(R.id.spinnerRoommates);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        etPriceResult = (EditText) findViewById(R.id.editTextPriceResult);
        etAmountResult = (EditText) findViewById(R.id.editTextResult);

    }

    public void onClick(View view){
        //get info from Bill class
        Bundle bundle = getIntent().getExtras();
        Bill bill = (Bill) bundle.getSerializable("Bill");
        etAmount = (EditText) findViewById(R.id.editTextAmount);

       // int roommates = Integer.parseInt(spinRoommate.getSelectedItem().toString());

        //if statement to read spinner results and make division of total price by number of roommates. Set as etPriceResult and pass that to etAmount to be sent back.
        if (spinRoommate.getSelectedItem().toString()=="1"){
            etPriceResult.setText(bill.getAmount);


        } else if (spinRoommate.getSelectedItem().toString()=="2"){
            etPriceResult = (bill.getAmount / 2);

        } else if (spinRoommate.getSelectedItem().toString()=="3"){
            etPriceResult = (bill.getAmount / 3);

        } else if (spinRoommate.getSelectedItem().toString()=="4"){
            etPriceResult = (bill.getAmount / 4);

        } else if (spinRoommate.getSelectedItem().toString()=="5"){
            etPriceResult = (bill.getAmount / 5);

        } else if (spinRoommate.getSelectedItem().toString()=="6"){
            etPriceResult = (bill.getAmount / 6);

        }

        //display editText explaining the number, displaying amount of the original bill divided by the selected number of roommates.
        etAmountResult.setText("Price per roommate: " + etPriceResult);


        //setting the divided amount as string
      //  String amountPer = etAmountPer.getText().toString();


    }
    //set the new AmountPer
    final etAmountPer = etAmountResult;

    //set button intent to return to main page
     //   btnUpdate.setOnClickListener(new View.OnClickListener(){
       // public void onClick (View view){
         //   Intent mainActIntent = new Intent(view.getContext(), MainActivity.class);
           // finish();
            //startActivity(mainActIntent);
        //}
   // });
}
