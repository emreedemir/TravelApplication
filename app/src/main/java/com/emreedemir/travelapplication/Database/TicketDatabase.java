package com.emreedemir.travelapplication.Database;

import android.app.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.emreedemir.travelapplication.Model.Customer;
import com.emreedemir.travelapplication.Model.Ticket;
import com.emreedemir.travelapplication.Model.Voyage;
import com.emreedemir.travelapplication.Util.Utility;

import java.util.ArrayList;
import java.util.List;

public class TicketDatabase extends SQLiteOpenHelper
{
    public static final int Database_Version=1;

    public static final String Database_Name="Ticket_Database";

    public static final String TABLE_NAME="Table_Name";

    public static final String Ticket_DB_ID="db_id";

    public static final String Ticket_ID="id";

    public static final String Departure_City="departure_city";
    public static final String DepartureReturn_City="departure_return_city";
    public static final String Departure_Date="departure_date";
    public static final String Departure_Hour="departure_hour";

    public static final String Return_City ="return_city";
    public static final String Return_Date="return_date";
    public static final String Return_Hour="return_hour";
    public static final String Return_Return_City="return_return_city";

    public static final String Customer_Name="customer_name";
    public static final String Customer_Age="customer_age";
    public static final String Customer_Telephone="telephone_number";
    public static final String Customer_Gender="customer_gender";

    Context context;
    public static final String Payment="payment";

    String CREATION="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("
            +Ticket_DB_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +Ticket_ID +" TEXT, "
            +Departure_City +" VARCHAR(250),"
            +Departure_Date +" VARCHAR(250), "
            +Departure_Hour +" VARCHAR(250), "
            +DepartureReturn_City+" VARCHAR(250), "
            +Return_City +" VARCHAR(250), "
            +Return_Date +" VARCHAR(250), "
            +Return_Hour +" VARCHAR(250), "
            +Return_Return_City +" VARCHAR(250), "
            +Payment +" VARCHAR(250), "
            +Customer_Name +" VARCHAR(250), "
            +Customer_Age +" VARCHAR(250), "
            +Customer_Gender +" VARCHAR(250), "
            +Customer_Telephone +" VARCHAR(250)"
            +")";

    public TicketDatabase(@Nullable Context context)
    {
        super(context, Database_Name, null, Database_Version);
        this.context=context;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.execSQL(CREATION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean saveTicketToDatabase(Ticket ticket)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(Ticket_ID,ticket.getId());

        //Departure info
        if(ticket.getDepartureVoyage() !=null)
        {
            values.put(Departure_City,ticket.getDepartureVoyage().getDepartureCity());
            values.put(Departure_Date,ticket.getDepartureVoyage().getDepartureDate());
            values.put(Departure_Hour,ticket.getDepartureVoyage().getDepartureHour());
            values.put(DepartureReturn_City,ticket.getDepartureVoyage().getReturnCity());

        }
        //Return info
        if(ticket.getReturnVoyage() !=null)
        {
            values.put(Return_City,ticket.getReturnVoyage().getDepartureCity());
            values.put(Return_Date,ticket.getReturnVoyage().getDepartureDate());
            values.put(Return_Hour,ticket.getReturnVoyage().getDepartureHour());
            values.put(Return_Return_City,ticket.getReturnVoyage().getReturnCity());
        }



        values.put(Payment,ticket.getCost());

        //Customer's info

        if(ticket.getCustomer() !=null)
        {
            values.put(Customer_Name,ticket.getCustomer().getName());

            values.put(Customer_Age,ticket.getCustomer().getAge());

            values.put(Customer_Gender,ticket.getCustomer().getGender());

            values.put(Customer_Telephone,ticket.getCustomer().getPhoneNumber());
        }

        long id =sqLiteDatabase.insert(TABLE_NAME,null,values);

        sqLiteDatabase.close();

        if(id ==-1)
            return false;
        else
            return true;
    }

    public ArrayList<Ticket> getAllTicketsFromDatabase()
    {
        ArrayList<Ticket> ticketList = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery ="SELECT * FROM "+TABLE_NAME ;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if(cursor.moveToFirst())
        {
            do {

                String ticketId =cursor.getString(cursor.getColumnIndex(Ticket_ID));

                //Departure
                String departureCity=cursor.getString(cursor.getColumnIndex(Departure_City));
                String departureDate=cursor.getString(cursor.getColumnIndex(Departure_Date));
                String departureHout=cursor.getString(cursor.getColumnIndex(Departure_Hour));
                String departureReturnCity= cursor.getString(cursor.getColumnIndex(DepartureReturn_City));


                Voyage departureVoyage = new Voyage(departureCity,departureDate,departureHout,departureReturnCity);

                //Return
                String returnCity=cursor.getString(cursor.getColumnIndex(Return_City));
                String returnDate=cursor.getString(cursor.getColumnIndex(Return_Date));
                String returnHour=cursor.getString(cursor.getColumnIndex(Return_Hour));
                String returnReturnCity=cursor.getString(cursor.getColumnIndex(Return_Return_City));

                Voyage returnVoyage = new Voyage(returnCity,returnDate,returnHour,returnReturnCity);

                //Customer
                String customerName=cursor.getString(cursor.getColumnIndex(Customer_Name));
                String customerAge=cursor.getString(cursor.getColumnIndex(Customer_Age));
                String customerGender=cursor.getString(cursor.getColumnIndex(Customer_Gender));
                String customerTepelhoneNumber=cursor.getString(cursor.getColumnIndex(Customer_Telephone));

                Customer customer = new Customer(customerName,customerAge,customerTepelhoneNumber,customerGender);

                String payment =cursor.getString(cursor.getColumnIndex(Payment));

                Ticket ticket = new Ticket(ticketId,customer,departureVoyage,returnVoyage,payment);

                ticketList.add(ticket);
            }while (cursor.moveToNext());
        }

        return ticketList;
    }
}
