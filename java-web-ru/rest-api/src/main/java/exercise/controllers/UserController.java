package exercise.controllers;

import exercise.CustomException;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.core.validation.ValidationError;
import io.javalin.core.validation.ValidationException;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;
import io.ebean.DB;
import java.util.List;
import java.util.Map;

import exercise.domain.User;
import exercise.domain.query.QUser;

import io.javalin.http.HttpCode;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.lang3.StringUtils;

public class UserController implements CrudHandler {

    public void getAll(Context ctx) {
        // BEGIN
        String jsonUsers = DB.json().toJson(new QUser().findList());
        ctx.json(jsonUsers);
        // END
    };

    public void getOne(Context ctx, String id) {

        // BEGIN
        Long userID = Long.parseLong(id);
        User user = new QUser().id.eq(userID).findOne();

        if (user == null) throw new CustomException("User does not exist: ID = ", id);

        String jsonUser = DB.json().toJson(new QUser().id.eq(userID).findOne());
        ctx.json(jsonUser);
        // END
    };

    public void create(Context ctx) {

        // BEGIN
        User newUser = ctx.bodyValidator(User.class)
                .check(obj -> obj.getId() == 0, "ID is generated automatically, please exclude")
                .check(obj -> !obj.getFirstName().isEmpty(), "First name must not be empty")
                .check(obj -> !obj.getLastName().isEmpty(), "Last name must not be empty")
                .check(obj -> EmailValidator.getInstance().isValid(obj.getEmail()), "Incorrect e-mail")
                .check(obj -> obj.getPassword().length() > 4, "Password must be more that 4 characters")
                .check(obj -> StringUtils.isNumeric(obj.getPassword()), "Password must contain only numbers")
                .get();

        newUser.save();
        ctx.status(HttpCode.OK).result("User was successfully created");
        // END
    };

    public void update(Context ctx, String id) {
        // BEGIN
        Long userID = Long.parseLong(id);
        User oldUser = new QUser().id.eq(userID).findOne();
        if (oldUser == null) throw new CustomException("User does not exist: ID = ", id);

        User updatedUser = DB.json().toBean(User.class, ctx.body());

        ctx.bodyValidator(User.class)
                .check(obj -> obj.getId() == 0, "ID is generated automatically, please exclude")
                .check(obj -> !obj.getFirstName().isEmpty(), "First name must not be empty")
                .check(obj -> !obj.getLastName().isEmpty(), "Last name must not be empty")
                .check(obj -> EmailValidator.getInstance().isValid(obj.getEmail()), "Incorrect e-mail")
                .check(obj -> obj.getPassword().length() > 4, "Password must be more that 4 characters")
                .check(obj -> StringUtils.isNumeric(obj.getPassword()), "Password must contain only numbers")
                .get();

        updatedUser.setId(id);
        updatedUser.update();

        ctx.status(HttpCode.OK).result("User was successfully updated");
        // END
    };

    public void delete(Context ctx, String id) {
        // BEGIN
        Long userID = Long.parseLong(id);
        User oldUser = new QUser().id.eq(userID).findOne();
        if (oldUser == null) throw new CustomException("User does not exist: ID = ", id);

        oldUser.delete();

        ctx.status(HttpCode.OK).result("User was successfully deleted");
        // END
    };
}
