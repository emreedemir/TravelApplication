package com.emreedemir.travelapplication.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Voyage implements Serializable
{
    private String departureCity;
    private String departureDate;
    private String departureHour;
    private String returnCity;

    public Voyage()
    {
        departureCity="";
        departureDate="";
        departureHour="";
        returnCity="";
    }

    public Voyage(String departureCity, String departureDate, String departureHour, String returnCity)
    {
        this.departureCity = departureCity;
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.returnCity = returnCity;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    public String getReturnCity() {
        return returnCity;
    }

    public void setReturnCity(String returnCity) {
        this.returnCity = returnCity;
    }

    @NonNull
    @Override
    public String toString() {
        return departureCity
                +" To "
                +returnCity+" "
                +"Date "+departureDate
                + " hour" +departureHour ;
    }
}
