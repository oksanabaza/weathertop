package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Menu extends Controller
{
    public static void index() {
        Logger.info("Rendering member");
        Member member = Accounts.getLoggedInMember();
        render ("tags/menu.html", member);
    }
}

