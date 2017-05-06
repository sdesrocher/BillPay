package css.cis3334.billpay;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sdesrocher on 4/26/2017.
 */

public class BillAdapter extends ArrayAdapter<Bill> {

    private List<Bill> billList;  //list of bills to display
    private Context context;    //original activity that displays it
    private int layoutResource; //layout used


    public BillAdapter(Context context, int resource, List<Bill> billList){
        super(context, resource, billList);
        this.context = context;
        this.layoutResource = resource;
        this.billList = billList;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get bill displaying
        Bill bill = billList.get(position);
        View view;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.bill_row_layout, null);

        TextView tvName = (TextView)view.findViewById(R.id.textViewName);
        TextView tvDueDate = (TextView) view.findViewById(R.id.textViewDueDate);
        TextView tvAmountPer = (TextView) view.findViewById(R.id.textViewAmountPer);

        tvName.setText(bill.getName());
        tvDueDate.setText(bill.getDueDate());
        tvAmountPer.setText(bill.getAmountPer());


        return(view);
    }

}
