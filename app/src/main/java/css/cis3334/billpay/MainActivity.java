package css.cis3334.billpay;
/**
 * Created by sdesrocher on 2/24/17
 * Main screen shows list of all bills still to be paid and minimal info about each bill.
 * Buttons included on screen to press if bill is paid, then it deletes the selected bill off of the list. Other buttons goes to the details page.
 */


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    Button btnPaid, btnDetails;
    ListView lvBill;
    ArrayAdapter<Bill> billAdapter;
    List<Bill> billList;
    BillFirebaseData billDataSource;
    DatabaseReference myBillDbRef;
    int positionSelected;
    Bill billSelected;

    //add authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //firebase checking
        mAuth = FirebaseAuth.getInstance(); //declare object for Firebase


        //methods created:
        checkUserAuthenticated();
        setupFirebaseDataChange();
        setupListView();
        setupDetailButton();
        setupPaidButton();

        //enter layout for navigation bar menu to be added on MainActivity
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //nav_view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //creating the menu for settings and add new (top right corner)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__drawer, menu);
        return true;
    }

    //added with implementing onNavItemSel at top
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks
        int id = item.getItemId();

        if (id == R.id.nav_settings){
            //Handle settings action
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }
        else if (id==R.id.nav_new){
            //Handle add new bill action
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void checkUserAuthenticated() {
       // mAuth = FirebaseAuth.getInstance(); //declare object for Firebase
        mAuthListener = new FirebaseAuth.AuthStateListener() { //initialized mAuthListener
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //track the user when they sign in or out using the firebaseAuth
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Log.d("CSS3334","onAuthStateChanged - User NOT signed in");
                    Intent signInIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(signInIntent);
                }
            }
        };
    }

    private void setupFirebaseDataChange(){
        billDataSource = new BillFirebaseData();
        myBillDbRef = billDataSource.open(this);
        myBillDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //debugging log d
                Log.d("CIS3334", "Staring onDataChange()");
                billList = billDataSource.getAllBill(dataSnapshot);
                //custom adapter for displaying each bill
                billAdapter = new BillAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, billList);
                //apply adapter to list
                lvBill.setAdapter(billAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //debug
                Log.d("CIS3334", "onCancelled: ");
            }
        });

    }
    private void setupListView(){
        lvBill = (ListView) findViewById(R.id.ListViewBill);
        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View parent,
                                    int position, long id) {
                positionSelected = position;
                Log.d("MAIN", "Bill selected at position " + positionSelected);
            }
        });
    }

    private void setupDetailButton(){
        //set up button to display details added previosuly
        btnDetails = (Button) findViewById(R.id.buttonDetails);
        btnDetails.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d("MAIN", "onClick for Details");
                Intent detailActIntent = new Intent(view.getContext(), DetailActivity.class);
                detailActIntent.putExtra("Bill", billList.get(positionSelected));
                finish();
                startActivity(detailActIntent);
            }
        });
    }

    private void setupPaidButton(){
        //set up button to delete bill off of list when paid
        btnPaid = (Button) findViewById(R.id.buttonPaid);
        btnPaid.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d("MAIN", "onClick for Paid");
                Log.d("MAIN", "Delete at position "+ positionSelected);
                billDataSource.deleteBill(billList.get(positionSelected));
                billAdapter.remove(billList.get(positionSelected));
                billAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Bill has been taken off of list", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onStart() {
        //initiate the authentication listener
        super.onStart();
        //check if user is signed in and update
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mAuth.addAuthStateListener(mAuthListener); // update the listener on the users place
    }

    @Override
    public void onStop() {
        //discontinue the authentication
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener); // remove the listener
        }
    }

}
