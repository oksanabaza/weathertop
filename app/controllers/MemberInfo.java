package controllers;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;


public class MemberInfo extends Controller
{
    public static void index() {
   models.Member member = Accounts.getLoggedInMember();
   render("member.html", member);
    }

    public static void edit(String firstname, String lastname, String email, String password) {
        models.Member member = Accounts.getLoggedInMember();
        member.edit(firstname, lastname, email, password);
        member.save();
        Logger.info(String.valueOf(member));
        render("member.html", member);

    }

}
