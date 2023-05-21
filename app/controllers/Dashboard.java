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

        // Wind trends
        if (lastReading.windSpeed > secondLastReading.windSpeed && secondLastReading.windSpeed > thirdLastReading.windSpeed) {
          station.windTrend = "Rising";
        } else if (lastReading.windSpeed < secondLastReading.windSpeed && secondLastReading.windSpeed < thirdLastReading.windSpeed) {
          station.windTrend = "Dropping";
        } else {
          station.windTrend = "Unchanged";
        }

        // Pressure trends
        if (lastReading.pressure > secondLastReading.pressure && secondLastReading.pressure > thirdLastReading.pressure) {
          station.pressureTrend = "Rising";
        } else if (lastReading.pressure < secondLastReading.pressure && secondLastReading.pressure < thirdLastReading.pressure) {
          station.pressureTrend = "Dropping";
        } else {
          station.pressureTrend = "Unchanged";
        }
      } else {
        station.trend = "N/A";
        station.windTrend = "N/A";
        station.pressureTrend = "N/A";
      }

      // set default value
      double maxTemperature = 00.00;
      double minTemperature = 00.00;
      double maxWindSpeed = 00.00;
      double minWindSpeed = 00.00;
      double maxPressure = 00.00;
      double minPressure = 00.00;

      for (Reading reading : readings) {
        double temperature = reading.temperature;
        double windSpeed = reading.windSpeed;
        double pressure = reading.pressure;

        if (temperature > maxTemperature) {
          maxTemperature = temperature;
        }
        if (temperature < minTemperature) {
          minTemperature = temperature;
        }

        if (windSpeed > maxWindSpeed) {
          maxWindSpeed = windSpeed;
        }
        if (windSpeed < minWindSpeed) {
          minWindSpeed = windSpeed;
        }

        if (pressure > maxPressure) {
          maxPressure = pressure;
        }
        if (pressure < minPressure) {
          minPressure = pressure;
        }
      }

      station.maxTemperature = maxTemperature;
      station.minTemperature = minTemperature;
      station.maxWindSpeed = maxWindSpeed;
      station.minWindSpeed = minWindSpeed;
      station.maxPressure = maxPressure;
      station.minPressure = minPressure;

      // Latest temperature
      int lastItem = readings.size() - 1;
      if (lastItem >= 0) {
        //latest temperature
        station.latestTemperature = readings.get(lastItem).temperature;
        // Latest Fahrenheit temperature
        station.fLatestTemperature = station.latestTemperature * 9 / 5 + 32;

      }

    }

    render("dashboard.html", stations, member);

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
  public static void addStation (String name, double longitude, double latitude)
  {
    Logger.info("Adding a Playlist");
    Member member = Accounts.getLoggedInMember();
    Station station = new Station (name, longitude, latitude);
    member.stations.add(station);
    member.save();
    redirect ("/dashboard");
  }
}
