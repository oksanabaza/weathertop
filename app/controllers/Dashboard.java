package controllers;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");

    Reading r1 = new Reading(800, 0.5,3.5,1001);
    Reading r2 = new Reading(600,6.0, 2,1004);
    Reading r3 = new Reading(700,8.0,1,1000);
    Reading r4 = new Reading(200,0.5,3.5,999);
    Station station = new Station("Tramore");
    station.readings.add (r1);
    station.readings.add (r2);
    station.readings.add (r3);
    station.readings.add (r4);

    render ("dashboard.html", station);
  }
}

