package exercise.controllers;

import io.javalin.http.Handler;
import java.util.List;
import java.util.Map;
import io.javalin.core.validation.Validator;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.JavalinValidation;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

import exercise.domain.User;
import exercise.domain.query.QUser;

public final class UserController {

    public static Handler listUsers = ctx -> {

        List<User> users = new QUser()
            .orderBy()
                .id.asc()
            .findList();

        ctx.attribute("users", users);
        ctx.render("users/index.html");
    };

    public static Handler showUser = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        User user = new QUser()
            .id.equalTo(id)
            .findOne();

        ctx.attribute("user", user);
        ctx.render("users/show.html");
    };

    public static Handler newUser = ctx -> {

        ctx.attribute("errors", Map.of());
        ctx.attribute("user", Map.of());
        ctx.render("users/new.html");
    };

    public static Handler createUser = ctx -> {
        // BEGIN
        Validator<String> firstNameValidator = ctx.formParamAsClass("firstName", String.class)
                .check(s -> !s.isEmpty(), "Имя не должно быть пустым");
        Validator<String> lastNameValidator = ctx.formParamAsClass("lastName", String.class)
                .check(s -> !s.isEmpty(), "Фамилия не должна быть пустой");
        Validator<String> emailValidator = ctx.formParamAsClass("email", String.class)
                .check(s -> EmailValidator.getInstance().isValid(s), "Некорректный e-mail");
        Validator<String> passwordValidator = ctx.formParamAsClass("password", String.class)
                .check(StringUtils::isNumeric, "Пароль может содержать только цифры")
                .check(s -> s.length() > 4, "Пароль должен быть длиннее 4 символов");

        Map<String, List<ValidationError<?>>> errors = JavalinValidation.collectErrors(
                firstNameValidator, lastNameValidator, emailValidator, passwordValidator);

        User user = new User(ctx.formParam("firstName"), ctx.formParam("lastName"),
                ctx.formParam("email"), ctx.formParam("password"));

        if (!errors.isEmpty()) {
            ctx.status(422);
            ctx.attribute("user", user);
            ctx.attribute("errors", errors);
            ctx.render("users/new.html");
            return;
        }

        user.save();
        ctx.sessionAttribute("flash", "Пользователь успешно создан");
        ctx.redirect("/users");
        // END
    };
}
