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
    EditText etNewAmount, etPriceResult, etAmountPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinRoommate = (Spinner) findViewById(R.id.spinnerRoommates);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        etPriceResult = (EditText) findViewById(R.id.editTextPriceResult);
        etAmountPer = (EditText) findViewById(R.id.editTextResult);
    }

    public void onClick(View view){
        int roommates = Integer.parseInt(spinRoommate.getSelectedItem().toString());

        //if statement to read spinner results and make division of total price.
        if (spinRoommate.getSelectedItem().toString()=="1"){


          //  etAmountPer.setText();
                    //example from detailactivity:   etAmt.setText(bill.getAmount());

        } else if (spinRoommate.getSelectedItem().toString()=="2"){

        } else if (spinRoommate.getSelectedItem().toString()=="3"){

        } else if (spinRoommate.getSelectedItem().toString()=="4"){

        } else if (spinRoommate.getSelectedItem().toString()=="5"){

        } else if (spinRoommate.getSelectedItem().toString()=="6"){

        }

        //display editText explaining the number, displaying amount of the original bill divided by the selected number of roommates.
        etPriceResult.setText("Price per roommate: ");
       // etAmountPer.setText();

        //setting the divided amount as string
      //  String amountPer = etAmountPer.getText().toString();


    }


    //set button intent to return to main page
     //   btnUpdate.setOnClickListener(new View.OnClickListener(){
       // public void onClick (View view){
         //   Intent mainActIntent = new Intent(view.getContext(), MainActivity.class);
           // finish();
            //startActivity(mainActIntent);
        //}
   // });
}
