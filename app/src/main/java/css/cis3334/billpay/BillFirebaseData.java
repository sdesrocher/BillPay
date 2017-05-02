package css.cis3334.billpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdesrocher on 4/26/2017.
 * Creates database and connects throughout app activities
 */

public class BillFirebaseData {
    DatabaseReference myBillDbRef;
    //adding authentication for user to get their own bill list
    FirebaseAuth firebaseAuth;
    public static final String BillDataTag = "Bill User Data";
    private String userId;  //Firebase auth ID for current logged in user

    public DatabaseReference open(AppCompatActivity mainActivity){
        //message to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myBillDbRef = database.getReference(BillDataTag);
        //set user id
        userId = getUserId(mainActivity);
        return myBillDbRef;
    }
    public void close(){
    }

    private String getUserId(AppCompatActivity activity){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            //user is signed out
            Log.d("CSS3334", "onAuthStateChanged - User is not signed in");
            Intent signInIntent = new Intent (activity, LoginActivity.class);
            activity.startActivity(signInIntent);
        }
        return user.getUid();
    }

    public Bill createBill(String name, String duedate, String amount){
    //get new database key
    String key = myBillDbRef.child(BillDataTag).push().getKey();
    //set up object
    Bill newBill = new Bill(key, name, duedate, amount);
    //write vote to firebase
    myBillDbRef.child("users").child(userId).child(key).setValue(newBill);
    return newBill;
}

public void deleteBill (Bill bill){
    String key = bill.getKey();
    myBillDbRef.child("users").child(userId).child(key).removeValue();
}
public List<Bill> getAllBill(DataSnapshot dataSnapshot){
    List<Bill> billList = new ArrayList<Bill>();
    //only loop those bills tied to user id

    for (DataSnapshot data : dataSnapshot.child("users").child(userId).getChildren()){
        Log.d("CIS3334", "=== getAllBills === "+data.toString());
        Bill bill = data.getValue(Bill.class);
        billList.add(bill);
    }
    return billList;
}
}
