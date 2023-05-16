package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;


public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Admin");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;

    for (Station station : stations) {
      List<Reading> readings = station.readings;
      int numReadings = readings.size();

      if (numReadings >= 3) {
        Reading lastReading = readings.get(numReadings - 1);
        Reading secondLastReading = readings.get(numReadings - 2);
        Reading thirdLastReading = readings.get(numReadings - 3);

        if (lastReading.temperature > secondLastReading.temperature && secondLastReading.temperature > thirdLastReading.temperature) {
          station.trend = "Rising";
        } else if (lastReading.temperature < secondLastReading.temperature && secondLastReading.temperature < thirdLastReading.temperature) {
          station.trend = "Dropping";
        } else {
          station.trend = "Unchanged";
        }
      } else {
        station.trend = "N/A";
      }

      // Find maximum and minimum temperature
      double maxTemp = Double.MIN_VALUE;
      double minTemp = Double.MAX_VALUE;

      for (Reading reading : readings) {
        double temperature = reading.temperature;
        if (temperature > maxTemp) {
          maxTemp = temperature;
        }
        if (temperature < minTemp) {
          minTemp = temperature;
        }
      }

      station.maxTemperature = maxTemp;
      station.minTemperature = minTemp;
    }

    render("dashboard.html", stations);
  }

  public static void deleteStation (Long id)
  {
    Logger.info("Deleting a Playlist");
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    redirect ("/dashboard");
  }
  public static void addStation (String name)
  {
    Logger.info("Adding a Playlist");
    Member member = Accounts.getLoggedInMember();
    Station station = new Station (name);
    member.stations.add(station);
    member.save();
    redirect ("/dashboard");
  }
}
