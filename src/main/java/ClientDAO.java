import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    public static Client getClientById(int id) throws SQLException {
        Client client = null;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Client WHERE id = ?")) {
            statement.setInt(1, id); // Set the parameter value for the ID

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int clientId = resultSet.getInt("id");
                    String clientName = resultSet.getString("name");
                    int shiftAdminId = resultSet.getInt("primary_user");

                    client = new Client(clientId, clientName, shiftAdminId);
                }
            }
        }

        return client;
    }
}
