package exercise.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");
        showUser(request, response, id);
    }

    private List<Map<String, String>> getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        File jsonUsers = new File("src/main/resources/users.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonUsers,
                new TypeReference<List<Map<String, String>>>() { });
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder("""
                <!DOCTYPE html>
                <html lang=\"ru\">
                    <head>
                        <meta charset=\"UTF-8\">
                        <title>User List</title>
                        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css\"
                              rel=\"stylesheet\"
                              integrity=\"sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We\"
                              crossorigin=\"anonymous\">
                    </head>
                    <body>
                        <div class=\"container\">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th scope="col" class="w-auto">ID</th>
                                        <th scope="col">Full Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                """);

        Comparator<Map<String, String>> sortById = Comparator.comparing(x -> Integer.parseInt(x.get("id")));
        List<Map<String, String>> userList = getUsers().stream()
                .sorted(sortById).toList();

        for (Map<String, String> user : userList) {
            sb.append("<td>%s</td>".formatted(user.get("id")));
            sb.append("<td><a href=\"/users/%s\">%s %s</a></td></tr>".formatted(user.get("id"),
                    user.get("firstName"), user.get("lastName")));
        }

        sb.append("</tbody></table></div></body></html>");

        out.println(sb);
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");

        Optional<Map<String, String>> optionalUser = getUsers().stream().
                filter(map -> map.get("id").equals(id)).limit(1).findFirst();

        if (optionalUser.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "123");
            return;
        };
        Map<String, String> user = optionalUser.get();
        StringBuilder sb = new StringBuilder("""
                <!DOCTYPE html>
                <html lang=\"ru\">
                    <head>
                        <meta charset=\"UTF-8\">
                        <title>User</title>
                        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css\"
                              rel=\"stylesheet\"
                              integrity=\"sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We\"
                              crossorigin=\"anonymous\">
                    </head>
                    <body>
                        <div class=\"container my-3\">
                            <div class="card border-secondary mb-3" style="max-width: 18rem;">
                                <div class=\"card-header\">User #%s</div>
                                <div class="card-body text-secondary">
                """.formatted(user.get("id")));
        sb.append("<h5 class=\"card-title\">%s %s</h5>".formatted(user.get("firstName"), user.get("lastName")));
        sb.append("<p class=\"card-text\">E-mail: %s</p>".formatted(user.get("email")));
        sb.append("</div></div></div></body></html>");
        out.println(sb);
        // END
    }
}
