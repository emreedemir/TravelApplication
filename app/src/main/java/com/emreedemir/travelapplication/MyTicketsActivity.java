package com.emreedemir.travelapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emreedemir.travelapplication.Adapters.TicketAdapter;
import com.emreedemir.travelapplication.Database.TicketDatabase;
import com.emreedemir.travelapplication.Model.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTicketsActivity extends AppCompatActivity
{
    private FirebaseAuth auth;

    private FirebaseUser user;

    private ArrayList<Ticket> ticketArrayList;

    private RecyclerView recyclerView;
    private TicketAdapter adapter;

    private Button backUserActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        auth=FirebaseAuth.getInstance();

        user =auth.getCurrentUser();

        ticketArrayList=getTickets();

        Collections.reverse(ticketArrayList);

        if(ticketArrayList.size()==0)
        {
            Toast.makeText(MyTicketsActivity.this,"You dont have any tickets",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MyTicketsActivity.this,"You have tickets",Toast.LENGTH_SHORT).show();
        }

       initiliazeUI();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(user ==null)
        {
            sendToSignInActivity();
        }
    }

    private void initiliazeUI()
    {
        adapter = new TicketAdapter(ticketArrayList);

        recyclerView =findViewById(R.id.ticketRecyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(manager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        backUserActivityButton =findViewById(R.id.backToUserActivityButtonFromTicketListActivity);

        backUserActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendUserToUserActivity();
            }
        });
    }

    private void sendToSignInActivity()
    {
        Intent intent =new Intent(MyTicketsActivity.this,SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private ArrayList<Ticket> getTickets()
    {
        TicketDatabase ticketDatabase = new TicketDatabase(MyTicketsActivity.this);

        return  ticketDatabase.getAllTicketsFromDatabase();
    }

    private void sendUserToUserActivity()
    {
        Intent intent = new Intent(MyTicketsActivity.this,UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
