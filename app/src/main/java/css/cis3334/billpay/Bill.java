package css.cis3334.billpay;

import java.io.Serializable;

/**
 * Created by sdesrocher on 4/26/2017.
 */

public class Bill implements Serializable{

    private String key;
    private String name;
    private String duedate;
    private String amount;

    public Bill(){

    }
    public Bill(String key, String name, String duedate, String amount){
        this.key = key;
        this.name = name;
        this.duedate = duedate;
        this.amount = amount;

    }

    //get and set key
    public String getKey(){

        return key;

    }public void setKey(String key){

        this.key = key;
    }

    //get and set name
    public String getName(){

        return name;

    }public void setName(String name){

        this.name = name;
    }

    //get and set duedate
    public String getDueDate(){

        return duedate;
    }public void setDueDate(String duedate){

        this.duedate = duedate;
    }

    //get and set amount
    public String getAmount(){

        return amount;

    }public void setAmount(String amount){

        this.amount = amount;
    }

    public String toString(){
        return "Bill{" +
                "Name='" + name + '\'' +
                ", DueDate='" + duedate + '\''+
                ", Amount='" + amount + '\''+
                '}';
    }

}
