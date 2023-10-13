import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB")
public class MyServletDB extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-18-190-158-171.us-east-2.compute.amazonaws.com:3306/DBWeeklyPlanner";
   static String user = "eli-niebaum-remote";
   static String password = "csci4830";
   static Connection connection = null;

   public MyServletDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");//("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection == null) {
    	  System.out.println("Failed to make connection!");
      } 
      try {
    	 String new_sunday = new String();
         String new_monday = new String(); 
         String new_tuesday = new String(); 
         String new_wednesday = new String(); 
         String new_thursday = new String(); 
         String new_friday = new String(); 
         String new_saturday = new String();
    	  
    	 response.setContentType("text/html");
         PrintWriter out = response.getWriter(); 
         
         out.println("<br><a href=/webproject4830-techex-niebaum/home_page.html style=font-size:30px>Home</a> <br>");
    	  
         String selectSQL = "SELECT * FROM TableWeeklyPlanner";// WHERE MYUSER LIKE ?";
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         
         while (rs.next()) {
        	 
            String firstDate = rs.getString("firstDate");
            String sunday = rs.getString("sunday");
            String monday = rs.getString("monday");
            String tuesday = rs.getString("tuesday");
            String wednesday = rs.getString("wednesday");
            String thursday = rs.getString("thursday");
            String friday = rs.getString("friday");
            String saturday = rs.getString("saturday");
            
            new_sunday = sunday.replace("\n", "<br>");
            new_monday = monday.replace("\n", "<br>"); 
            new_tuesday = tuesday.replace("\n", "<br>"); 
            new_wednesday = wednesday.replace("\n", "<br>"); 
            new_thursday = thursday.replace("\n", "<br>"); 
            new_friday = friday.replace("\n", "<br>"); 
            new_saturday = saturday.replace("\n", "<br>"); 
            
            response.getWriter().append("<br><br><span style=\"font-size: 25px;\">Plans for the Week of: " + firstDate + "</span><br><br>");
            response.getWriter().append("<p><strong>Sunday:</strong><br> " + new_sunday + "</p>");
            response.getWriter().append("<p><strong>Monday:</strong><br> " + new_monday + "</p>");
            response.getWriter().append("<p><strong>Tuesday:</strong><br> " + new_tuesday + "</p>");
            response.getWriter().append("<p><strong>Wednesday:</strong><br> " + new_wednesday + "</p>");
            response.getWriter().append("<p><strong>Thursday:</strong><br> " + new_thursday + "</p>");
            response.getWriter().append("<p><strong>Friday:</strong><br> " + new_friday + "</p>");
            response.getWriter().append("<p><strong>Saturday:</strong><br> " + new_saturday + "</p>");

            
         }
      
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}