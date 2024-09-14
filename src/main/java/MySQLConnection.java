import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    public static void main(String[] args) {
        // Database credentials
        String jdbcUrl = "jdbc:mysql://localhost:3306/krishnag";
        String username = "admin";
        String password = "admin";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");

            // Create a statement object to execute the query
            statement = connection.createStatement();

            // Define the query to print 5 records from the customer table
            String query = "SELECT CustomerID, CustomerName, Address, Street, Area, District, State, Country, Pincode, PhoneNo, MailID, RelationShip, RelationName FROM Customer LIMIT 5";

            // Execute the query
            resultSet = statement.executeQuery(query);

            // Process and print the results
            while (resultSet.next()) {
                // Retrieve data by column names
                int customerID = resultSet.getInt("CustomerID");
                String customerName = resultSet.getString("CustomerName");
                String address = resultSet.getString("Address");
                String street = resultSet.getString("Street");
                String area = resultSet.getString("Area");
                String district = resultSet.getString("District");
                String state = resultSet.getString("State");
                String country = resultSet.getString("Country");
                String pincode = resultSet.getString("Pincode");
                String phoneNo = resultSet.getString("PhoneNo");
                String mailID = resultSet.getString("MailID");
                String relationship = resultSet.getString("RelationShip");
                String relationName = resultSet.getString("RelationName");

                // Print the data
                System.out.println("CustomerID: " + customerID + ", CustomerName: " + customerName + ", Address: " + address + ", Street: " + street + ", Area: " + area + ", District: " + district + ", State: " + state + ", Country: " + country + ", Pincode: " + pincode + ", PhoneNo: " + phoneNo + ", MailID: " + mailID + ", Relationship: " + relationship + ", RelationName: " + relationName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
