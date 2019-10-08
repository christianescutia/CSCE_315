import javax.swing.*;    // Needed for Swing classes
import java.awt.event.*; // Needed for ActionListener Interface
import java.sql.*;
import java.io.*;



public class TempestGUI extends JFrame
{
   private JPanel panel;             // To reference a panel
   private JButton topTenButton_Movies;       // To reference a button
   private JButton topTenButton_Comedies;
   private JButton topTenButton_Games;
   private final int WINDOW_WIDTH = 750;  // Window width
   private final int WINDOW_HEIGHT = 750; // Window height
   private JTable table;
   private JTextField searchText_movie;
   private JButton searchButton_movie;
   private JTextField searchText_actor;
   private JButton searchButton_actor;
   private JTextField searchText_year;
   private JButton searchButton_year;

   public TempestGUI()
   {
      setTitle("Welcome to our IMDb!");
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      buildPanel();
      add(panel);
      setVisible(true);
   }

   // buildling panel
   private void buildPanel()
   {
      searchText_year = new JTextField(20);
      searchButton_year = new JButton("Search Years");
      searchButton_year.addActionListener(new searchButton_year());

      searchText_movie = new JTextField(20);
      searchButton_movie = new JButton("Search Movies");
      searchButton_movie.addActionListener(new searchButton_movie());

      searchText_actor = new JTextField(20);
      searchButton_actor = new JButton("Search Actors/Directors");
      searchButton_actor.addActionListener(new searchButton_actor());
      
      topTenButton_Movies = new JButton("Top 10 Shows/Movies");
      topTenButton_Movies.addActionListener(new topTenButton_Movies());

      topTenButton_Comedies = new JButton("Top 10 Comedies");
      topTenButton_Comedies.addActionListener(new topTenButton_Comedies());

      topTenButton_Games = new JButton("Top 10 Video Games");
      topTenButton_Games.addActionListener(new topTenButton_Games());

      // Create a JPanel object and let the panel
      // field reference it.
      panel = new JPanel();

      // user input years
      panel.add(searchText_year);
      panel.add(searchButton_year);

      // user input movies
      panel.add(searchText_movie);
      panel.add(searchButton_movie);

      // user input actors
      panel.add(searchText_actor);
      panel.add(searchButton_actor);

      // top 10 movies
      panel.add(topTenButton_Movies);
      panel.add(topTenButton_Comedies);
      panel.add(topTenButton_Games);
   }

   // user input for years is taken and returns results
   private class searchButton_year implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         String userInput = searchText_year.getText();
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT tconst, titleType, primaryTitle, startyear, endyear, genres FROM title_basics WHERE primarytitle='"+userInput+"';";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
               //OUTPUT
            System.out.println("Search any actor or director");
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   // user input for movies is taken and returns results
   private class searchButton_movie implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         String userInput = searchText_movie.getText();
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT tconst, titleType, primaryTitle, startyear, endyear, genres FROM title_basics WHERE primarytitle='"+userInput+"';";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            //ResultSetMeteData result_md = result.getMetaData();
               // OUTPUT

            // ******************
            // This part is supposed to do the writing to a file part, but it either 
            // doesn't write anything (and also ouputs nothing to the table) or 
            // it just comes across some error and it says "Error accessing database" (next try block)
            System.out.println("Search any movie, show or video game");
            result.last();
            int rowCount = result.getRow();
            result.beforeFirst();
            System.out.println(rowCount);
            String title_ = "Large result";
            String resultIsLarge = "Your result returns more than 30 rows, do you want an output file?";
            if (rowCount > 30){
               int reply = JOptionPane.showConfirmDialog(null, resultIsLarge, title_, JOptionPane.YES_NO_OPTION);
               if (reply == JOptionPane.YES_OPTION){
                  FileWriter fstream = new FileWriter("out_gui.txt");
                  BufferedWriter out = new BufferedWriter(fstream);
                  while(result.next()){
                     out.write(result.getString("tconst") + "\n");
                     out.write(result.getString("titleType") + "\n");
                     out.write(result.getString("primarytitle") + "\n");
                     out.write(result.getString("startyear") + "\n");
                     out.write(result.getString("endyear") + "\n");
                     out.write(result.getString("genres") + "\n");
                  }
                  out.close();
               }
               else if (reply == JOptionPane.NO_OPTION){
                  ListTableModel model = ListTableModel.createModelFromResultSet(result);
                  table = new JTable(model);
                  JOptionPane.showMessageDialog(null, new JScrollPane(table));
               }
            } else {
               ListTableModel model = ListTableModel.createModelFromResultSet(result);
               table = new JTable(model);
               JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }// end output for this part
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   // user input for actors is taken and returns results
   private class searchButton_actor implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         String userInput = searchText_actor.getText();
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT primaryname, birthyear, deathyear, knownfortitles, nconst FROM name_basics WHERE primaryname='"+userInput+"';";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
               //OUTPUT
            System.out.println("Search any actor or director");
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   // When clicked, top 10 movies according to rating pop up
   private class topTenButton_Movies implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT title_basics.primarytitle, title_ratings.averagerating, title_basics.startyear, title_ratings.numvotes FROM title_basics INNER JOIN title_ratings ON title_basics.tconst=title_ratings.tconst WHERE title_ratings.numvotes>110000 ORDER BY title_ratings.averagerating DESC LIMIT 10;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
               //OUTPUT
            System.out.println("Top 10 shows/movies With 100,000+ votes.");
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   // When clicked, top 10 comedies according to rating pop up
   private class topTenButton_Comedies implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT title_basics.primarytitle, title_ratings.averagerating, title_ratings.numvotes FROM title_basics INNER JOIN title_ratings ON title_basics.tconst=title_ratings.tconst WHERE title_basics.genres LIKE 'Comedy' AND title_ratings.numvotes>'100000' ORDER BY title_ratings.averagerating DESC LIMIT 10;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
               //OUTPUT
            System.out.println("Top 10 Comedies With 100,000+ votes.");
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   // When clicked, top 10 movies according to rating pop up
   private class topTenButton_Games implements ActionListener{
      public void actionPerformed(ActionEvent e){
         Connection conn = null;
         try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://db-315.cse.tamu.edu/TempestDB",
               dbSetup.user, dbSetup.pswd);
         } catch (Exception d) {
            d.printStackTrace();
            System.err.println(d.getClass().getName()+": "+d.getMessage());
            System.exit(0);
         }//end try catch

         try{
               // Input
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT title_basics.primarytitle, title_ratings.averagerating, title_ratings.numvotes FROM title_basics INNER JOIN title_ratings ON title_basics.tconst=title_ratings.tconst WHERE title_basics.titletype='videoGame' AND title_ratings.numvotes>'1000' ORDER BY title_ratings.averagerating DESC LIMIT 10;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
               //OUTPUT
            System.out.println("Top 10 Video Games With 100,000+ votes.");
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            JOptionPane.showMessageDialog(null, new JScrollPane(table));
         }
         catch (Exception d){
            System.out.println("Error accessing Database.");
         }
         try {
            conn.close();
            System.out.println("Connection Closed.");
         } catch(Exception d) {
            System.out.println("Connection NOT Closed.");
         }//end try catch
      }
   }

   public static void main(String[] args)
   {
      new TempestGUI();
   }
}