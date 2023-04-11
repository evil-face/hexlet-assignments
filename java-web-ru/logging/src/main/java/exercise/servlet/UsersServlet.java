package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import exercise.LoggerFactory;
import org.apache.commons.lang3.ArrayUtils;

import static exercise.App.getUsers;
import exercise.Users;
import exercise.TemplateEngineUtil;

public class UsersServlet extends HttpServlet {

    private Users users = getUsers();
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServlet.class);

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);
        LOGGER.log(Level.INFO, "Got GET request with action " + action);

        switch (action) {
            case "list" -> showUsers(request, response);
            case "new" -> newUser(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);
        LOGGER.log(Level.INFO, "Got POST request with action " + action);

        switch (action) {
            case "list" -> createUser(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        request.setAttribute("users", users.getAll());
        TemplateEngineUtil.render("users/index.html", request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        Map<String, String> user = users.build();

        request.setAttribute("user", user);
        request.setAttribute("error", "");

        TemplateEngineUtil.render("users/new.html", request, response);
    }

    private void createUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        HttpSession session = request.getSession();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Map<String, String> user = users.build(firstName, lastName, email);

        if (firstName.isEmpty() || lastName.isEmpty()) {
            request.setAttribute("user", user);
            session.setAttribute("flash", "Имя и Фамилия не могут быть пустыми");
            response.setStatus(422);
            TemplateEngineUtil.render("users/new.html", request, response);
            return;
        }

        users.add(user);
        session.setAttribute("flash", "Пользователь успешно создан");
        response.sendRedirect("/users");
    }
}
