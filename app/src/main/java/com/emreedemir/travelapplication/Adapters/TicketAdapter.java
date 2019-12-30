package com.emreedemir.travelapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emreedemir.travelapplication.Model.Ticket;
import com.emreedemir.travelapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder>
{
    private ArrayList<Ticket> ticketList;

    public TicketAdapter(ArrayList<Ticket> ticketList)
    {
        this.ticketList=ticketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_list_view,parent,false);

        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.ticketIdTextView.setText("Ticket id : " +ticketList.get(position).getId());

        holder.customer.setText("Customer info : " +ticketList.get(position).getCustomer().toString());
        holder.departureTextView.setText("Departure info : " +ticketList.get(position).getDepartureVoyage().toString());

        holder.returnTextView.setText("Return info " +ticketList.get(position).getReturnVoyage().toString());

        holder.cost.setText("Payment : " +ticketList.get(position).getCost() +" TL");

    }

    @Override
    public int getItemCount()
    {
        return ticketList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView ticketIdTextView;
        TextView cost;
        TextView customer;
        TextView departureTextView;
        TextView returnTextView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            ticketIdTextView =itemView.findViewById(R.id.ticketId);

            cost=itemView.findViewById(R.id.ticket_cost);
            customer =itemView.findViewById(R.id.custome_infos);

            departureTextView=itemView.findViewById(R.id.departureVoyageInfo);

            returnTextView=itemView.findViewById(R.id.returnVoyageInfo);
        }
    }
}
