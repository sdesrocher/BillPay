package css.cis3334.billpay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sdesrocher on 4/30/2017.
 * Users must log in to see their list. This makes it so users only see a list of their bills.
 */

public class LoginActivity extends AppCompatActivity{

    EditText etEmail, etPassword;
    Button btnLogin, btnCreate;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //connect with widgets in activity_login
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();  //declare object for Firebase
        setupCreateButton();
        setupLoginButton();
    }

    private void setupLoginButton() {
        // Set up the button to add a new fish using a seperate activity
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("CIS3334", "Signing in the user");
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                signIn(email, password);
            }
        });
    }

    private void setupCreateButton() {
        // Set up the button to add a new fish using a seperate activity
        btnCreate = (Button) findViewById(R.id.buttonCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("CIS3334", "Creating a new user account");
                //create account for new users
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                createAccount(email,password);
            }
        });
    }

    private void signIn(String email, String password){
        //sign in the recurrent user with email and password previously created.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "SignIn--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        //create account for new users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {  //update listener.
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "createAccount--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }

}//end main
