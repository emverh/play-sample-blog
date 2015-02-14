package unit;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.cookie;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import controllers.Posts;
import play.api.libs.json.Json;
import play.mvc.Result;
import play.test.FakeRequest;

public class PostControllerTest {

    @Test
    public void show_showById() {
        Result result = Posts.show(1L);

        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat(contentType(result)).isNull();
        assertThat(charset(result)).isNull();
    }

    @Test
    public void show_getBySlug() {
        running(fakeApplication(), () -> {
            String slug = "test-blog-";
            Result result = callAction(controllers.routes.ref.Posts.show(slug), fakeRequest());

            assertThat(status(result)).isEqualTo(OK);
            assertThat(contentType(result)).isEqualTo("text/html");
            assertThat(charset(result)).isEqualTo("utf-8");
            assertThat(cookie("blog-id", result).value()).isEqualTo(slug);
            assertThat(contentAsString(result)).contains("Test Blog");
            assertThat(contentAsString(result)).contains("Test Blog Text");
        });
    }

    @Test
    public void show_postNotFound() {
        running(fakeApplication(), () -> {
            Long id = -99L;
            Result result = callAction(controllers.routes.ref.Posts.show(String.format("%d", id)) //
                    , fakeRequest());
            assertThat(status(result)).isEqualTo(NOT_FOUND);
            assertThat(contentType(result)).isEqualTo("text/html");
            assertThat(charset(result)).isEqualTo("utf-8");
            assertThat(contentAsString(result)).contains("No such blog post.");
        });
    }

    @Test
    public void create_validJson() {
        running(fakeApplication(), () -> {
            FakeRequest request = fakeRequest();

            request.withJsonBody(Json.parse("{\n" +
                    "  \"id\": 7827,\n" +
                    "  \"title\": \"test blog 7827\",\n" +
                    "  \"text\": \"text for test blog 7827\"\n" +
                    "}"));
            request.withHeader("Content-Type", "application/json");
            Result result = callAction(controllers.routes.ref.Posts.create() //
                    , request);
            assertThat(status(result)).isEqualTo(CREATED);
            assertThat(contentAsString(result)).contains("Blog post created.");
        });
    }

    @Test
    public void create_invalidHeader() {
        running(fakeApplication(), () -> {
            FakeRequest request = fakeRequest();

            request.withTextBody("{\n" +
                    "  \"id\": 7827,\n" +
                    "  \"title\": \"test blog 7827\",\n" +
                    "  \"text\": \"text for test blog 7827\"\n" +
                    "}");
            request.withHeader("Content-Type", "application/json");
            Result result = callAction(controllers.routes.ref.Posts.create() //
                    , request);
            assertThat(status(result)).isEqualTo(BAD_REQUEST);
            assertThat(contentAsString(result)).contains("Expecting application/json request body.");
        });
    }

}
