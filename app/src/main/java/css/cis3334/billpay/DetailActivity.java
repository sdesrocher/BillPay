package css.cis3334.billpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sdesrocher on 4/26/2017.
 * Shows the list of details first entered in when new bill added. Contains button to return to main activity.
 */

public class DetailActivity extends AppCompatActivity{

    Button btnReturn;
    EditText etName, etDueDate, etAmt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        Bill bill = (Bill) bundle.getSerializable("Bill");

        //link variables to xml
        etName = (EditText) findViewById(R.id.editTextBillName);
        etDueDate = (EditText) findViewById(R.id.editTextDueDate);
        etAmt = (EditText) findViewById(R.id.editTextAmount);

        //take entried from each editText
        etName.setText(bill.getName());
        etDueDate.setText(bill.getDueDate());
        etAmt.setText(bill.getAmount());

        //set button intent to return to main page
        btnReturn = (Button) findViewById(R.id.buttonReturn);
        btnReturn.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                Intent mainActIntent = new Intent(view.getContext(), MainActivity.class);
                finish();
                startActivity(mainActIntent);
            }
        });


}}
