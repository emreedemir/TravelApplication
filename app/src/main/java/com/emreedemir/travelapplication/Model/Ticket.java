package com.emreedemir.travelapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ticket implements Serializable
{
    private String id;
    private  Customer customer;
    private Voyage departureVoyage;
    private Voyage returnVoyage;
    private String cost;

    public Ticket(String id, Customer customer, Voyage departureVoyage, String cost) {
        this.id = id;
        this.customer = customer;
        this.departureVoyage = departureVoyage;
        this.cost = cost;
        returnVoyage = new Voyage("","","","");
        returnVoyage= new Voyage();
    }

    public Ticket(String id, Customer customer, Voyage departureVoyage, Voyage returnVoyage, String cost)
    {
        this.id = id;
        this.customer = customer;
        this.departureVoyage = departureVoyage;
        this.returnVoyage = returnVoyage;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Voyage getDepartureVoyage() {
        return departureVoyage;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDepartureVoyage(Voyage departureVoyage) {
        this.departureVoyage = departureVoyage;
    }

    public Voyage getReturnVoyage() {
        return returnVoyage;
    }

    public void setReturnVoyage(Voyage returnVoyage) {
        this.returnVoyage = returnVoyage;
    }

    @NonNull
    @Override
    public String toString() {
        return id +" "+customer.toString()
                +" " +departureVoyage.toString()
                +" " +returnVoyage.toString()
                +" " +cost;
    }

}
