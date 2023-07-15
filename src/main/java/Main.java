import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Initial test to see if Hikari is working to obtain connections
        try (Connection connection = DbManager.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Client");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + "  " + rs.getString(2));
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
        }
    }
}