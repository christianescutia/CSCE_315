  import java.sql.*;
  //import java.sql.DriverManager;
  /*
  Tim McGuire, adapted from Robert lightfoot
  CSCE 315
  9-27-2019
   */
  public class jdbcpostgreSQL {
    public static void main(String args[]) {
      

       //try{
       /*
       //create a statement object
         Statement stmt = conn.createStatement();
         //create an SQL statement
         String test_ing = "title_basics";
         String sqlStatement = "SELECT title_basics.primarytitle, title_ratings.averagerating, title_basics.startyear, title_ratings.numvotes FROM " + test_ing + " INNER JOIN title_ratings ON title_basics.tconst=title_ratings.tconst WHERE title_ratings.numvotes>100000 ORDER BY title_ratings.averagerating DESC LIMIT 10;";
         //send statement to DBMS
         ResultSet result = stmt.executeQuery(sqlStatement);

         //OUTPUT
         System.out.println("Top 10 shows/movies with 100,000+ votes.");
         String format = "%-40s %-15s %-15s %-15s\n";
         System.out.println(String.format(format, "Primary Title", "Average Rating", "Start Year", "Number of Votes"));
         System.out.println("_______________________________________________________________________________________");
         while (result.next()) {
          String primarytitle = result.getString("primarytitle");
          String averagerating = result.getString("averagerating");
          String startyear = result.getString("startyear");
          String numvotes = result.getString("numvotes");
          System.out.println(String.format(format, primarytitle, averagerating, startyear, numvotes));
        }
        */
        TempestGUI gui_test = new TempestGUI();
        gui_test.main();
      //} catch (Exception e){
       //System.out.println("Error accessing Database.");
     //}

      //closing the connection
    
    }//end main
  }//end Class