package controllers;

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
}
