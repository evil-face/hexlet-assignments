package exercise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;

    // BEGIN
    @BeforeAll
    static void setup() {
        app = App.getApp();
        app.start(app.port());
        baseUrl = "http://localhost:" + app.port();
    }

    @AfterAll
    static void tearDown() {
        app.stop();
    }
    // END

    // Между тестами база данных очищается
    // Благодаря этому тесты не влияют друг на друга
    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("user");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users")
            .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users/new")
            .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // BEGIN
    @Test
    void testCreateUser_correct() {
        String firstName = "Biba";
        String lastName = "Boba";
        String email = "biba@boba.com";
        String password = "123456";

        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", firstName)
                .field("lastName", lastName)
                .field("email", email)
                .field("password", password)
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        User user = new QUser()
                .firstName.eq("Biba")
                .and().lastName.eq("Boba")
                .findOne();

        assertThat(user).isNotNull();

        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    void testCreateUser_incorrect() {
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "")
                .field("lastName", "")
                .field("email", "wrong")
                .field("password", "rrt")
                .asString();

        assertThat(response.getStatus()).isEqualTo(422);
        assertThat(new QUser().findList().size()).isEqualTo(1);

        assertThat(response.getBody()).contains("Имя не должно быть пустым");
        assertThat(response.getBody()).contains("Фамилия не должна быть пустой");
        assertThat(response.getBody()).contains("Должно быть валидным email").contains("wrong");
        assertThat(response.getBody()).contains("Пароль должен содержать не менее 4 символов").contains("rrt");
    }
    // END
}
