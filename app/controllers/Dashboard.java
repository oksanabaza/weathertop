package controllers;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");

    Reading r1 = new Reading(800, 0.5,3.5,1001);
    Reading r2 = new Reading(600,6.0, 2,1004);
    Station s1 = new Station("Tramore");
    s1.readings.add (r1);
    s1.readings.add (r2);


    Reading r3 = new Reading(700,8.0,1,1000);
    Reading r4 = new Reading(200,0.5,3.5,999);
    Station s2 = new Station("Вгтmore");
    s2.readings.add (r3);
    s2.readings.add (r4);

    List<Station> stations= new ArrayList<Station>();
    stations.add(s1);
    stations.add(s2);

    render ("dashboard.html", stations);
  }
}

