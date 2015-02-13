package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Authentication extends Controller {

    public static Result login(String email) {
        boolean authenticated = User.authenticated(email);
        if (authenticated) {
            session("user", email.toLowerCase());
            flash("success", "Login successful.");
            return redirect(controllers.routes.Posts.list());
        } else {
            flash("failure", "Email not found.");
            return forbidden();
        }
    }

    public static Result logout() {
        session().remove("user");
        flash("success", "Logout successful.");
        return redirect(controllers.routes.Posts.list());
    }

}
