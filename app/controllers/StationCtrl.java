package controllers;

import java.util.Date;
import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Logger.info ("Station id = " + id);
        render("station.html", station);
    }
    public static void deletereading(Long id, Long readingid)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info ("Removing" + reading.id);
        station.readings.remove(reading);
        station.save();
        reading.delete();
        render("station.html", station);
    }
    public static void addReading(Long id, int code, int temperature, double windSpeed, double windDirection, int pressure) {
        // Remove milliseconds from the current time
        Date date = new Date();
        date.setTime(date.getTime() / 1000L * 1000L);

        double fTemperature = temperature * 9.0 / 5.0 + 32.0;


        Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure, date);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/" + id);
    }
}
