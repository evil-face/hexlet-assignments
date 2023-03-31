package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    private List<String> companies = Data.getCompanies();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();
        String queryString = request.getQueryString();
        String querySearchParam = request.getParameter("search");
        if (queryString == null || querySearchParam == null) {
            companies.forEach(out::println);
        } else {
            List<String> filteredList = companies.stream()
                    .filter(s -> s.contains(querySearchParam)).toList();
            if (filteredList.isEmpty()) {
                out.println("Companies not found");
            } else {
                filteredList.forEach(out::println);
            }
        }
        // END
    }
}
