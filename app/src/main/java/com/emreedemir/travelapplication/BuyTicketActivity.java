package com.emreedemir.travelapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emreedemir.travelapplication.Database.TicketDatabase;
import com.emreedemir.travelapplication.Model.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class BuyTicketActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private FirebaseUser user;

    private Button buyTicketButton;

    private Button cancelBuyTicket;

    public Ticket currentTicket;

    private TextView ticketId;

    private TextView customerView;

    private  TextView departureView;

    private TextView returnView;

    private TextView paymentView;

    public TicketDatabase ticketDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);

        mAuth =FirebaseAuth.getInstance();

        user =mAuth.getCurrentUser();

        ticketDatabase = new TicketDatabase(this);

        if(user !=null)
        {
            Intent intent=getIntent();

            Ticket ticket =(Ticket) intent.getSerializableExtra("Ticket");

            if(ticket !=null)
            {
                currentTicket=ticket;
            }
            else
            {
                Toast.makeText(this,"Buy Ticket Activity Crashed",Toast.LENGTH_SHORT).show();
                sendToUserActivity();
            }
        }
        initiliazeUI();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(user==null)
        {
            sendToSignInActivity();
        }
    }

    void initiliazeUI()
    {
        ticketId=findViewById(R.id.ticketIdViewBuyTickets);

        customerView =findViewById(R.id.customerViewBuyTickets);

        departureView=findViewById(R.id.departureViewBuyTickets);

        returnView =findViewById(R.id.returnViewBuyTickets);

        paymentView =findViewById(R.id.returnViewBuyTickets);


        buyTicketButton=findViewById(R.id.buyTicket);

        if(currentTicket!=null)
        {
            ticketId.setText("Ticket Id : " +currentTicket.getId());
            departureView.setText("Deparuture info : " +currentTicket.getDepartureVoyage().toString());
            returnView.setText("Return Info" +currentTicket.getReturnVoyage().toString());
            paymentView.setText("Payment info : " +currentTicket.getCost() +" TL");

            customerView.setText("Customer info : " +currentTicket.getCustomer().toString());
        }

        cancelBuyTicket=findViewById(R.id.cancelTicketBuy);

        cancelBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelBuy();
            }
        });

        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyTicket();
            }
        });
    }

    void buyTicket()
    {
        saveTicketToDatabase();
    }

    void cancelBuy()
    {
        sendToUserActivity();
    }

    void saveTicketToDatabase()
    {

        if(currentTicket!=null)
        {
            boolean ticketSaveState =ticketDatabase.saveTicketToDatabase(currentTicket);

            if(ticketSaveState==true)
            {
                Toast.makeText(this,"bought th ticket",Toast.LENGTH_SHORT).show();
                sendToUserActivity();
            }
            else
            {
                Toast.makeText(this,"cant bought th ticket,try again",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,"Ticket is null",Toast.LENGTH_SHORT).show();
        }
    }

    void sendToUserActivity()
    {
        Intent intent = new Intent(BuyTicketActivity.this,UserActivity.class);
        startActivity(intent);
    }

    void sendToSignInActivity()
    {
        Intent intent = new Intent();

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }
}
