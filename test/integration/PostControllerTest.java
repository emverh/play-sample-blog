package integration;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

public class PostControllerTest {


    @Test
    public void blogPostShow() {
        // given
        Long id = 1L;

        // when
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo(String.format("http://localhost:3333/posts/%d", id));
            assertThat(browser.pageSource()).contains("Test Blog");
            assertThat(browser.pageSource()).contains("Test Blog Text");
        });
    }

    @Test
    public void show_showWithSlug() {
        // given
        String slug  = "test-blog-";

        // when
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo(String.format("http://localhost:3333/posts/%s", slug));
            assertThat(browser.pageSource()).contains("Test Blog");
            assertThat(browser.pageSource()).contains("Test Blog Text");
        });
    }

    @Test
    public void blogPostNotFound() {
        // given
        Long id = -99L;

        // when
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo(String.format("http://localhost:3333/posts/%d", id));
            assertThat(browser.pageSource()).contains("No such blog post.");
        });
    }
}
