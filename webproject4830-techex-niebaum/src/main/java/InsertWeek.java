
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertWeek")
public class InsertWeek extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertWeek() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String firstDate = request.getParameter("firstDate");
      String sunday = request.getParameter("sunday");
      String monday = request.getParameter("monday");
      String tuesday = request.getParameter("tuesday");
      String wednesday = request.getParameter("wednesday");
      String thursday = request.getParameter("thursday");
      String friday = request.getParameter("friday");
      String saturday = request.getParameter("saturday");

      Connection connection = null;
      String insertSql = " INSERT INTO TableWeeklyPlanner (id, firstDate, sunday, monday, tuesday, wednesday, thursday, friday, saturday) values (default, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, firstDate);
         preparedStmt.setString(2, sunday);
         preparedStmt.setString(3, monday);
         preparedStmt.setString(4, tuesday);
         preparedStmt.setString(5, wednesday);
         preparedStmt.setString(6, thursday);
         preparedStmt.setString(7, friday);
         preparedStmt.setString(8, saturday);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Plans for the Week of: " + firstDate;
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Sunday</b>: " + sunday + "\n" + //
            "  <li><b>Monday</b>: " + monday + "\n" + //
            "  <li><b>Tuesday</b>: " + tuesday + "\n" + //
            "  <li><b>Wednesday</b>: " + wednesday + "\n" + //
            "  <li><b>Thursday</b>: " + thursday + "\n" + //
            "  <li><b>Friday</b>: " + friday + "\n" + //
            "  <li><b>Saturday</b>: " + saturday + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject4830-techex-niebaum/home_page.html>Return</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
