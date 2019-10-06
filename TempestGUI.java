import javax.swing.*;    // Needed for Swing classes
import java.awt.event.*; // Needed for ActionListener Interface
import java.sql.*;

public class TempestGUI extends JFrame
{
   private JPanel panel;             // To reference a panel
   private JTextField testTextField; // To reference a text field
   private JButton testButton;       // To reference a button
   private final int WINDOW_WIDTH = 1000;  // Window width
   private final int WINDOW_HEIGHT = 1000; // Window height
   private JTable table;

   public TempestGUI()
   {
      setTitle("Welcome to our IMDb!");
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      buildPanel();
      add(panel);
      setVisible(true);
   }

   private void buildPanel()
   {
      //testTextField = new JTextField(10);
      testButton = new JButton("Top 10 Shows/Movies");
      testButton.addActionListener(new TestButtonListener());

      // Create a JPanel object and let the panel
      // field reference it.
      panel = new JPanel();

      // Add the label, text field, and button
      // components to the panel.
      //panel.add(testTextField);
      panel.add(testButton);
   }

   private class TestButtonListener implements ActionListener{
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
            System.out.println("Opened database successfully");
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String test_ing = "title_basics";
            String sqlStatement = "SELECT title_basics.primarytitle, title_ratings.averagerating, title_basics.startyear, title_ratings.numvotes FROM " + test_ing + " INNER JOIN title_ratings ON title_basics.tconst=title_ratings.tconst WHERE title_ratings.numvotes>110000 ORDER BY title_ratings.averagerating DESC LIMIT 10;";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

               //OUTPUT
            System.out.println("Top 10 shows/movies with 100,000+ votes.");
            //String format = "%-40s %-15s %-15s %-15s\n";
            //System.out.println(String.format(format, "Primary Title", "Average Rating", "Start Year", "Number of Votes"));
            //System.out.println("_______________________________________________________________________________________");
            /*String primarytitle;
            String averagerating;
            String startyear;
            String numvotes;*/
            ListTableModel model = ListTableModel.createModelFromResultSet(result);
            table = new JTable(model);
            /*while (result.next()) {
               primarytitle = result.getString("primarytitle");
               averagerating = result.getString("averagerating");
               startyear = result.getString("startyear");
               numvotes = result.getString("numvotes");
               System.out.println(String.format(format, primarytitle, averagerating, startyear, numvotes));
            }*/
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

         //input = testTextField.getText();
         //JOptionPane.showMessageDialog(null, String.format(format, primarytitle, averagerating, startyear, numvotes));
      }
   }

   public static void main()
   {
      new TempestGUI();
   }
}