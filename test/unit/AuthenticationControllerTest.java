package unit;

import controllers.routes.ref;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class AuthenticationControllerTest {

    @Test
    public void login_shouldLoginIfUserExists() {
        running(fakeApplication(), () -> {
            String email = "emver.hidalgo@play.com";
            Result result = callAction(ref.Authentication.login(email), fakeRequest());

            assertThat(status(result)).isEqualTo(SEE_OTHER);
            assertThat(redirectLocation(result)).isEqualTo("/posts");
            assertThat(flash(result).get("success")).isEqualTo("Login successful.");
            assertThat(session(result).get("user")).isEqualTo(email);
        });
    }

    @Test
    public void login_shouldNotLoginIfUserDoesNotExist() {
        running(fakeApplication(), () -> {
            String email = "bad@play.com";
            Result result = callAction(ref.Authentication.login(email), fakeRequest());

            assertThat(status(result)).isEqualTo(FORBIDDEN);
            assertThat(flash(result).get("failure")).isEqualTo("Email not found.");
            assertThat(session(result).get("user")).isNull();
        });
    }

    @Test
    public void logout_shouldLogout() {
        running(fakeApplication(), () -> {
            Result result = callAction(ref.Authentication.logout(), fakeRequest());

            assertThat(status(result)).isEqualTo(SEE_OTHER);
            assertThat(flash(result).get("success")).isEqualTo("Logout successful.");
            assertThat(session(result).get("user")).isNull();
            assertThat(redirectLocation(result)).isEqualTo("/posts");
        });
    }
}
