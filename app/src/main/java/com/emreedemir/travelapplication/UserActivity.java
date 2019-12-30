package com.emreedemir.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.emreedemir.travelapplication.Model.Customer;
import com.emreedemir.travelapplication.Model.Enums.TicketType;
import com.emreedemir.travelapplication.Model.Ticket;
import com.emreedemir.travelapplication.Model.Voyage;
import com.emreedemir.travelapplication.Util.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class UserActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Calendar calendar;

    private DatePickerDialog datePickerDialog;

    private RadioGroup radioGroup;

    private Button searchTicketButton;
    private Button myTicketsButton;
    private Button exitAccountButton;

    private Spinner departureCity;
    private EditText departureDate;
    private Spinner departureHour;

    private Spinner returnCity;
    private EditText returnDate;
    private Spinner returnHour;

    private Switch voyageTypeSwitchView;
    private EditText customerName;
    private EditText customerAge;
    private EditText customerTelephone;
    private RadioGroup gender;
    private RadioButton genderMan;
    private RadioButton genderWoman;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth=FirebaseAuth.getInstance();

        user=mAuth.getCurrentUser();

        initiliazeUI();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(user ==null)
        {
            sendSignInActivity();
        }
    }

    void initiliazeUI()
    {
        customerName =findViewById(R.id.customerName);
        customerAge =findViewById(R.id.customerAge);
        customerTelephone=findViewById(R.id.telephoneNumber);

        gender =findViewById(R.id.gender);

        genderWoman=findViewById(R.id.woman);

        genderMan =findViewById(R.id.man);

        departureCity =findViewById(R.id.departureCity_ua);
        departureDate =findViewById(R.id.departureDate_ua);
        departureHour =findViewById(R.id.departureHour_ua);

        returnCity =findViewById(R.id.returnCity_ua);
        returnDate =findViewById(R.id.returnDate_ua);

        departureDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                calendar =Calendar.getInstance();

                int day =calendar.get(Calendar.DAY_OF_MONTH);
                int month =calendar.get(Calendar.MONTH);

                int year =calendar.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay)
                    {
                        departureDate.setText(String.valueOf(mDay) +" /"+String.valueOf(mMonth) +" /" +String.valueOf(mYear));
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                calendar =Calendar.getInstance();

                int day =calendar.get(Calendar.DAY_OF_MONTH);
                int month =calendar.get(Calendar.MONTH);

                int year =calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(UserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay)
                    {
                        returnDate.setText(String.valueOf(mDay) +"/"+String.valueOf(mMonth) +"/" +String.valueOf(mYear));
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        returnHour =findViewById(R.id.returnHour_ua);

        voyageTypeSwitchView=findViewById(R.id.voyageTypeSwitch);

        voyageTypeSwitchView.setChecked(false);

        voyageTypeSwitchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setVoyageTypeView(b);
            }
        });

        searchTicketButton=findViewById(R.id.searchTicketButton_ua);
        myTicketsButton=findViewById(R.id.myTickets_ua);
        exitAccountButton =findViewById(R.id.exitAccount_ua);

        searchTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVerify() &&verifyCustomerInfo())
                {
                    Toast.makeText(UserActivity.this,"Filled correctly",Toast.LENGTH_SHORT).show();
                   createTicket();
                }

                else
                    Toast.makeText(UserActivity.this,"Please fill correctly",Toast.LENGTH_LONG).show();
            }
        });

        myTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendUserToMyTicketsActivity();
            }
        });

        exitAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitFromAccount();
            }
        });

        setVoyageTypeView(voyageTypeSwitchView.isChecked());
    }

    void setVoyageTypeView(boolean voyageTypeState)
    {
        returnDate.setEnabled(voyageTypeState);
        returnHour.setEnabled(voyageTypeState);
    }

    boolean isVerify()
    {
        if(voyageTypeSwitchView.isChecked()==false)
        {
            if(departureCity.getSelectedItem().toString() ==returnCity.getSelectedItem().toString())
                return  false;
            else if(TextUtils.isEmpty(departureDate.getText().toString()))
                return false;
            else
                return true;
        }
        else if(voyageTypeSwitchView.isChecked()==true)
        {
            if(departureCity.getSelectedItem().toString()==returnCity.getSelectedItem().toString())
                return false;
            else if(TextUtils.isEmpty(departureDate.getText().toString()))
                return false;
            else if(TextUtils.isEmpty(returnDate.getText().toString()))
                return false;
            else
                return true;
        }
        else
        {
            return  false;
        }
    }

    boolean verifyCustomerInfo()
    {
        if(TextUtils.isEmpty(customerName.getText().toString()))
        {
            return false;
        }
        else if(TextUtils.isEmpty(customerAge.getText().toString()))
        {
            return false;
        }
        else if(TextUtils.isEmpty(customerTelephone.getText().toString()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    Customer getCustomer()
    {
        String gender ="";

        if(genderWoman.isChecked())
            gender="Woman";
        else
            gender="Man";

        return new Customer(customerName.getText().toString(),customerAge.getText().toString(),
                customerTelephone.getText().toString(),gender);
    }

    Voyage getDepartureVoyage()
    {
        return new Voyage(departureCity.getSelectedItem().toString(),
                departureDate.getText().toString(),
                departureHour.getSelectedItem().toString(),
                returnCity.getSelectedItem().toString());
    }

    Voyage getReturnVoyage()
    {
        return new Voyage(returnCity.getSelectedItem().toString(),
                returnDate.getText().toString(),
                returnHour.getSelectedItem().toString(),
                departureCity.getSelectedItem().toString());
    }

    void sendSignUpActivity()
    {
        Intent intent =new Intent(UserActivity.this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void sendSignInActivity()
    {
        Intent intent = new Intent(UserActivity.this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void exitFromAccount()
    {
        mAuth.signOut();

        sendSignInActivity();
    }

    void createTicket()
    {
        Customer customer =getCustomer();
        Voyage departureVoyage =getDepartureVoyage();

        String ticketID= Utility.utility.getID();

        TicketType ticketType;

        if(voyageTypeSwitchView.isChecked()==false)
            ticketType=TicketType.oneWay;
        else
            ticketType=TicketType.twoWay;


        if(voyageTypeSwitchView.isChecked()==false)
        {
            Ticket oneWayTicket = new Ticket(ticketID,customer,departureVoyage,
                    Utility.utility.getTicketCost(departureCity.getSelectedItem().toString(),
                            returnCity.getSelectedItem().toString(),ticketType));
            sendUserToBuyActivity(oneWayTicket);
        }
        else
        {
            Voyage returnVoyage =getReturnVoyage();

            Ticket twoWayTicket = new Ticket(ticketID,customer,
                    departureVoyage,returnVoyage,
                    Utility.utility.getTicketCost(departureCity.getSelectedItem().toString(),
                            returnCity.getSelectedItem().toString(),ticketType));

            sendUserToBuyActivity(twoWayTicket);
        }
    }

    void sendUserToBuyActivity(Ticket ticket)
    {
        Intent intent = new Intent(UserActivity.this,BuyTicketActivity.class);

        intent.putExtra("Ticket",ticket);

        startActivity(intent);
    }

    void sendUserToMyTicketsActivity()
    {
        Intent intent = new Intent(UserActivity.this,MyTicketsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
