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
      int minWindSpeed = 0;
      double maxPressure = 00.00;
      double minPressure = 00.00;

      for (Reading reading : readings) {
        double temperature = reading.temperature;
        int windSpeed = (int) reading.windSpeed;
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
      station.maxWindSpeed = (int) maxWindSpeed;
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
        station.latestCode = readings.get(lastItem).code;
        station.latestPressure = readings.get(lastItem).pressure;
        station.latestWindSpeed = (int) readings.get(lastItem).windSpeed;
        station.latestWindDirection = readings.get(lastItem).windDirection;
        // Calculate wind chill with rounded result
        double windChill = 13.12 + 0.6215 * station.latestTemperature - 11.37 * Math.pow(station.latestWindSpeed, 0.16) + 0.3965 * station.latestTemperature * Math.pow(station.latestWindSpeed, 0.16);

        // Round wind chill to 2 decimal places
        double roundedWindChill = Math.round(windChill * 100.0) / 100.0;

        // Assign rounded wind chill to station
        station.windChill = roundedWindChill;

        // Apply windSpeed value based on conditions
        int windSpeed = station.latestWindSpeed;
        int windSpeedValue = 0;

        if (windSpeed == 1) {
          windSpeedValue = 0;
        } else if (windSpeed >= 1 && windSpeed <= 5) {
          windSpeedValue = 1;
        } else if (windSpeed >= 6 && windSpeed <= 11) {
          windSpeedValue = 2;
        } else if (windSpeed >= 12 && windSpeed <= 19) {
          windSpeedValue = 3;
        }else if (windSpeed >= 20 && windSpeed <= 28) {
          windSpeedValue = 4;
        }else if (windSpeed >= 29 && windSpeed <= 38) {
          windSpeedValue = 5;
        }else if (windSpeed >= 39 && windSpeed <= 49) {
          windSpeedValue = 6;
        }else if (windSpeed >= 50 && windSpeed <= 61) {
          windSpeedValue = 7;
        }else if (windSpeed >= 62 && windSpeed <= 74) {
          windSpeedValue = 8;
        }else if (windSpeed >= 75 && windSpeed <= 88) {
          windSpeedValue = 9;
        }else if (windSpeed >= 89 && windSpeed <= 102) {
          windSpeedValue = 10;
        }else if (windSpeed >= 103 && windSpeed <= 117) {
          windSpeedValue = 11;
        }else {
          windSpeedValue = -1; // else value
        }

        station.latestWindSpeedValue = windSpeedValue;
//
        // Assign wind compass direction
        double windDirection = station.latestWindDirection;
        String windCompass = null;

        if (windDirection >= 348.75 || windDirection < 11.25) {
          windCompass = "N";
        } else if (windDirection >= 11.25 || windDirection < 33.75) {
          windCompass = "NNE";
        } else if (windDirection >= 33.75 || windDirection < 56.25) {
          windCompass = "NE";
        } else if (windDirection >= 56.25 || windDirection < 78.75) {
          windCompass = "ENE";
        } else if (windDirection >= 78.75 || windDirection < 101.25) {
          windCompass = "E";
        }else if (windDirection >= 101.25 || windDirection < 123.75) {
          windCompass = "ESE";
        }else if (windDirection >= 123.75 || windDirection < 146.25) {
          windCompass = "SE";
        }else if (windDirection >= 146.25 || windDirection < 168.75) {
          windCompass = "SSE";
        }else if (windDirection >= 168.75 || windDirection < 191.25) {
          windCompass = "S";
        }else if (windDirection >= 191.25 || windDirection < 213.75) {
          windCompass = "SSW";
        }else if (windDirection >= 213.75 || windDirection < 236.25) {
          windCompass = "SW";
        }else if (windDirection >= 236.25 || windDirection < 258.75) {
          windCompass = "WSW";
        }else if (windDirection >= 258.75 || windDirection < 281.25) {
          windCompass = "W";
        }else if (windDirection >= 281.25 || windDirection < 303.75) {
          windCompass = "WNW";
        }else if (windDirection >= 303.75 || windDirection < 326.25) {
          windCompass = "NW";
        }else if (windDirection >= 326.25 || windDirection < 348.75) {
          windCompass = "NW";
        }else {
          windCompass = "";
        }

        station.windCompass = windCompass;
//

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
