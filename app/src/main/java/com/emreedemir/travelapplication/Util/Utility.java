package com.emreedemir.travelapplication.Util;

import com.emreedemir.travelapplication.Model.Enums.TicketType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class Utility
{
    public static Utility utility =  new Utility();

    private Utility(){}

    public String getTicketCost(String departureCity, String returnCity, TicketType ticketType)
    {
        if(ticketType ==TicketType.oneWay)
        {
            return "50";
        }
        else
        {
            return "100";
        }
    }

    public String getID()
    {
        return UUID.randomUUID().toString();
    }

}
