package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import exercise.TemplateEngineUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.logging.Level;
import java.util.logging.Logger;



public class ArticlesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger("ArticlesServlet");

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

        switch (action) {
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        List<Map<String, String>> articles = new ArrayList<>();
        int currPage = request.getParameter("page") == null ?
                1 : Integer.parseInt(request.getParameter("page"));
        int articlesCount = 0;
        LOGGER.log(Level.INFO, "Got page number from request param : " + currPage);

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "SELECT id, title FROM articles ORDER BY id LIMIT 10 OFFSET ?");
            prepStatement.setInt(1, (currPage - 1) * 10);
            LOGGER.log(Level.INFO, "Query: " + prepStatement.toString());
            ResultSet rs = prepStatement.executeQuery();

            while (rs.next()) {
                articles.add(Map.of(
                        "id", rs.getString("id"),
                        "title", rs.getString("title")));
            }

            Statement statement = connection.createStatement();
            ResultSet countRes = statement.executeQuery("SELECT COUNT(*) FROM articles");
            countRes.first();
            articlesCount = countRes.getInt(1);
            LOGGER.log(Level.INFO, "Counted " + articlesCount + " articles from DB");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.setAttribute("articles", articles);
        request.setAttribute("currPage", currPage);
        request.setAttribute("articlesCount", articlesCount);
        LOGGER.log(Level.INFO, "Got " + articles.size() + " articles from DB");
        // END
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        int articleId = Integer.parseInt(request.getPathInfo().split("/")[1]);
        Map<String, String> article = new HashMap<>();

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "SELECT title, body FROM articles WHERE id = ?");
            prepStatement.setInt(1, articleId);
            ResultSet rs = prepStatement.executeQuery();
            if (!rs.next()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                article.put("title", rs.getString(1));
                article.put("body", rs.getString(2));
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        request.setAttribute("article", article);
        // END
        TemplateEngineUtil.render("articles/show.html", request, response);
    }
}
