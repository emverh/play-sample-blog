package unit;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import org.junit.Test;

import models.Post;
import play.twirl.api.Content;

public class PostViewTest {

    @Test
    public void renderTemplate() {
        Post post = new Post(2l, "Test post", "Test post text");
        Content html = views.html.posts.show.render(post);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains(post.text);
        assertThat(contentAsString(html)).contains(post.title);
    }

    //Content html = views.html.index.render("Your new application is ready.");
}
