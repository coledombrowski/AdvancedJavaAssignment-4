/* BCIS 3680 Forta Database Application
   Business Logic Tier
   This class specializes in data access
   Author: Cole Dombrowski
*/

package dombrowski.cole.forta;

import java.sql.*;
import java.util.ArrayList;

public class DBAccess
{
    // Build connection string
    final static String CON_STR = 
            "jdbc:mysql://localhost:3306/dombrowskic?user=root&password=bcis3680";

    public static ArrayList retrieveData(String table, String column)
    {
        // ArrayList to store results
        ArrayList<String> data = new ArrayList<>();
        
        try
        {
            // Create connection object
            Connection conn = DriverManager.getConnection(CON_STR);
            
            // Create Statement object
            Statement stmt = conn.createStatement();

            // Create SQL statement
            String sqlSelect = "SELECT vend_id, vend_name FROM vendors;";

            // Run SQL statement
            ResultSet result = stmt.executeQuery(sqlSelect);

            // Store records into array list
            while (result.next())
            {
                System.out.println(result.getInt(1)
                + "\t" + result.getString(2));
            }
        }
        catch ( SQLException se )
        {
            // Catching and displaying exception message
            System.out.println("ERROR: " + se.getMessage());
        }
        finally
        {
            // Returning the ArrayList object
            return data;
        }
    }
}
