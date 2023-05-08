package controllers;

import java.util.List;

import models.Station;
import models.Reading;
import play.mvc.Controller;

public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        render("station.html");
    }
}
