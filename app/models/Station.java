package models;

import java.util.ArrayList;
import java.util.List;

public class Station
{
    public String name;
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name)
    {
        this.name = name;
    }
}

