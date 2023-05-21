package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Main extends Controller
{
    public static void index() {
        Logger.info("Rendering member");
        Member member = Accounts.getLoggedInMember();
        render ("main.html", member);
    }
}

