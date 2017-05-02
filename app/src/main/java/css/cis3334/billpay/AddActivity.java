package css.cis3334.billpay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by sdesrocher on 4/26/2017.
 * Page displayed when new bill is to be added into firebase
 */

public class AddActivity extends AppCompatActivity {

    EditText etName, etDueDate, etAmt;
    Button btnSave;
    BillFirebaseData billDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        //link each variable to xml layout
        etName = (EditText) findViewById(R.id.editTextBillName);
        etDueDate = (EditText) findViewById(R.id.editTextDueDate);
        etAmt = (EditText) findViewById(R.id.editTextAmount);

        //connect with firebase
        billDataSource = new BillFirebaseData();
        DatabaseReference myBillDbRef = billDataSource.open(this);

        //set onclick for the save button after info entered
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String name = etName.getText().toString();
                String duedate = etDueDate.getText().toString();
                String amount = etAmt.getText().toString();
                billDataSource.createBill(name, duedate, amount);

                //set intent to send info entered to main activity screen
                Intent mainActIntent = new Intent(view.getContext(), MainActivity.class);
                finish();
                startActivity(mainActIntent);
            }

        });

    }
}


