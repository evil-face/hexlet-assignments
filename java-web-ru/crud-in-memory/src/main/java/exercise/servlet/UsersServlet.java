package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import static exercise.Data.getUsers;
import static exercise.Data.getNextId;

public class UsersServlet extends HttpServlet {

    private List<Map<String, String>> users = getUsers();

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    private Map<String, String> getUserById(String id) {
        Map<String, String> user = users
            .stream()
            .filter(u -> u.get("id").equals(id))
            .findAny()
            .orElse(null);

        return user;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showUsers(request, response);
                break;
            case "new":
                newUser(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "show":
                showUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "new":
                createUser(request, response);
                break;
            case "edit":
                updateUser(request, response);
                break;
            case "delete":
                destroyUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        request.setAttribute("users", users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response);
    }


    private void showUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {
        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        // BEGIN
        Map<String, String> emptyUser = new HashMap<>();
        request.setAttribute("error", "");
        request.setAttribute("user", emptyUser);
        RequestDispatcher view = request.getRequestDispatcher("/new.jsp");
        view.forward(request, response);
        // END
    }

    private void createUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        // BEGIN
        request.setCharacterEncoding("UTF-8");

        Map<String, String> newUser = new HashMap<>();
        newUser.put("firstName", request.getParameter("firstName"));
        newUser.put("lastName", request.getParameter("lastName"));
        newUser.put("email", request.getParameter("email"));

        if (newUser.get("firstName").isBlank() ||
                newUser.get("lastName").isBlank()) {
            RequestDispatcher view = request.getRequestDispatcher("/new.jsp");
            request.setAttribute("error", "First name and Last name cannot be empty");
            request.setAttribute("user", newUser);
            response.setStatus(422);
            view.forward(request, response);
            return;
        }
        newUser.put("id", getNextId());
        users.add(newUser);
        response.sendRedirect("/users");
        // END
    }

    private void editUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // BEGIN
        request.setAttribute("user", user);
        request.setAttribute("error", "");
        RequestDispatcher view = request.getRequestDispatcher("/edit.jsp");
        view.forward(request, response);
        // END
    }

    private void updateUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // BEGIN
        request.setCharacterEncoding("UTF-8");
        Map<String, String> editedUser = new HashMap<>(user);
        editedUser.put("firstName", request.getParameter("firstName"));
        editedUser.put("lastName", request.getParameter("lastName"));
        editedUser.put("email", request.getParameter("email"));

        if (editedUser.get("firstName").isBlank() ||
                editedUser.get("lastName").isBlank()) {
            RequestDispatcher view = request.getRequestDispatcher("/edit.jsp");
            request.setAttribute("error", "First name and Last name cannot be empty");
            request.setAttribute("user", editedUser);
            response.setStatus(422);
            view.forward(request, response);
            return;
        }

        user.clear();
        user.putAll(editedUser);
        response.sendRedirect("/users/show/?id=" + id);
        // END
    }

    private void deleteUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(request, response);

    }

    private void destroyUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        users.remove(user);
        response.sendRedirect("/users");
    }
}
