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
    public double latitude;
    public double longitude;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();
    public String trend;
    public double maxTemperature;
    public double minTemperature;
    public String windTrend;
    public String pressureTrend;
    public int maxWindSpeed;
    public int minWindSpeed;
    public double maxPressure;
    public double minPressure;
    public double latestTemperature;
    public double fLatestTemperature;
    public int latestCode;
    public int latestPressure;
    public int latestWindSpeed;
    public double latestWindDirection;
    public double latestWindSpeedValue;
    public double windChill;
    public String windCompass;


    public Station(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trend = trend;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.maxWindSpeed = maxWindSpeed;
        this.minWindSpeed = minWindSpeed;
        this.maxPressure =maxPressure;
        this.minPressure=minPressure;
        this.windTrend = windTrend;
        this.pressureTrend = pressureTrend;
        this.latestTemperature = latestTemperature;
        this.fLatestTemperature = fLatestTemperature;
        this.latestCode = latestCode;
        this.latestPressure = latestPressure;
        this.latestWindSpeed = latestWindSpeed;
        this.latestWindDirection = latestWindDirection;
        this.latestWindSpeedValue = latestWindSpeedValue;
        this.windChill = windChill;
        this.windCompass = windCompass;

    }


}

