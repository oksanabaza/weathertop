package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model
{
    public String name;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();
    public String trend;
    public double maxTemperature;
    public double minTemperature;

    public Station(String name)
    {
        this.name = name;
        this.trend = trend;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }
}

