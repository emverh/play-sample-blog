package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Post;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.posts.show;

public class Posts extends Controller {

    public static Result list() {
        return play.mvc.Results.TODO;
    }

    public static Result show(long id) {
        Post post = Post.get(id);

        if(post != null){
            return redirect(controllers.routes.Posts.show(post.slug()));
        }else{
            response().setContentType("text/html; charset=utf-8");
            return notFound("No such blog post.").as("text/html");
        }
    }

    public static Result show(String id) {
        boolean numeric = StringUtils.isNumeric(id);

        if (numeric) {
            return show(Long.parseLong(id));
        }

        Post post = Post.getBySlug(id);
        if (post != null) {
            response().setCookie("blog-id", post.slug());
            return ok(show.render(post));
        } else {
            response().setContentType("text/html; charset=utf-8");
            return notFound("No such blog post.");
        }
    }

    public static Result create() {
        RequestBody body = request().body();
        JsonNode textBody = body.asJson();

        if(textBody != null){
            return created("Expecting application/json request body.");
        }

        return badRequest();
    }

}
