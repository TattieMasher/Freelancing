import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();


        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM User");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String fName = resultSet.getString("first_name");
                String sName = resultSet.getString("second_name");
                String email = resultSet.getString("email");
                int verifiedInt = resultSet.getInt("verified");
                int userType = resultSet.getInt("type");

                // Set verified boolean as 0/1 from db
                boolean verified = (verifiedInt > 0);

                User user = new User(userId, fName, sName, email, verified, userType);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User getUserById(int id) throws SQLException {
        User result = null;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE id = ?")) {
            statement.setInt(1, id); // Set the parameter value for the ID

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve column values from the result set
                    int userId = resultSet.getInt("id");
                    String fName = resultSet.getString("first_name");
                    String sName = resultSet.getString("second_name");
                    String email = resultSet.getString("email");
                    int verifiedInt = resultSet.getInt("verified");
                    int userType = resultSet.getInt("type");

                    // Set verified boolean as 0/1 from db
                    boolean verified = (verifiedInt > 0);

                    // Create a new User object with the retrieved values
                    result = new User(userId, fName, sName, email, verified, userType);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean insertUser(String fName, String sName, String email, String password) throws SQLException {
        boolean result = false;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO User (first_name, second_name, email, password, type)\n" +
                     "VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, fName);
            statement.setString(2, sName);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setInt(5, 3);

            int rowsAffected = statement.executeUpdate();
            result = (rowsAffected > 0);    // Set return from result of query
        }

        return result;
    }

    // Other methods for specific queries, inserts, updates, deletes, etc.

    /*
    TODO: Methods for... getUserByName (return list), delete user
     */
}