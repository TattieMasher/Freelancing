import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

// TODO: Get the methods below to handle null admin and worker ids. Use wasNull() like here: https://www.tutorialspoint.com/how-to-handle-null-values-while-working-with-jdbc

public class ShiftsDAO {
    public static Shift getShift() throws SQLException {
        Shift shift = null;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shift WHERE id = ?")) {
            statement.setInt(1, 2);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve column values from the result set
                    int id = resultSet.getInt("id");
                    LocalDateTime start = resultSet.getObject("start", LocalDateTime.class); // Retrieve as LocalDateTime
                    LocalDateTime end = resultSet.getObject("end", LocalDateTime.class);
                    List<String> requiredSkills = null; // TODO: Populate this (way down the line)
                    int clientId = resultSet.getInt("client_id");
                    String clientName = null;           // TODO: Populate this from another query
                    int adminId = resultSet.getInt("shift_admin_id");
                    String adminName = null;            // TODO: Populate from another query
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    Client client = null;
                    User admin = null;
                    User worker = null;

                    // Get Client details
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Get shift admin details
                    try {
                        admin = UserDAO.getUserById(adminId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Get worker details (if they exist)
                    try {
                        worker = UserDAO.getUserById(workerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    shift = new Shift(start, end, client.getId(), client.getName(), admin.getId(), (admin.getfName() + " "
                            + admin.getsName()), worker.getId(), (worker.getfName() + " " + worker.getsName()));
                }
            }

        }

        return shift;
    }

    public static Shift getShiftByClient(int clientId) throws SQLException {
        Shift shift = null;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shift WHERE client_id = ?")) {
            statement.setInt(1, clientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve column values from the result set
                    int id = resultSet.getInt("id");
                    LocalDateTime start = resultSet.getObject("start", LocalDateTime.class); // Retrieve as LocalDateTime
                    LocalDateTime end = resultSet.getObject("end", LocalDateTime.class);
                    List<String> requiredSkills = null; // TODO: Populate this (way down the line)
                    clientId = resultSet.getInt("client_id");
                    String clientName = null;           // TODO: Populate this from another query
                    int adminId = resultSet.getInt("shift_admin_id");
                    String adminName = null;            // TODO: Populate from another query
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    Client client = null;
                    User admin = null;
                    User worker = null;

                    // Get Client details
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Get shift admin details
                    try {
                        admin = UserDAO.getUserById(adminId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Get worker details (if they exist)
                    try {
                        worker = UserDAO.getUserById(workerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    shift = new Shift(start, end, client.getId(), client.getName(), admin.getId(), (admin.getfName() + " "
                            + admin.getsName()), worker.getId(), (worker.getfName() + " " + worker.getsName()));
                }
            }

        }

        return shift;
    }

    // TODO: Choose an approach and implement this
    public static TreeSet<Shift> getAllShiftsByClient(int id) throws SQLException {
        TreeSet<Shift> shifts = new TreeSet<>();

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shift WHERE client_id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve column values from the result set
                    int shiftId = resultSet.getInt("id");
                    LocalDateTime start = resultSet.getObject("start", LocalDateTime.class); // Retrieve as LocalDateTime
                    LocalDateTime end = resultSet.getObject("end", LocalDateTime.class);
                    List<String> requiredSkills = null; // TODO: Populate this (way down the line)
                    int clientId = resultSet.getInt("client_id");
                    String clientName = null;           // TODO: Populate this from another query
                    int adminId = resultSet.getInt("shift_admin_id");
                    String adminName = null;            // TODO: Populate from another query
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    Client client = null;
                    User admin = null;
                    User worker = null;

                    // Get Client details (cannot be null in DB)
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Get shift admin details (can be null) TODO: check if we want this, going forward
                    if (adminId != 0) {     // 0 if NULL in DB
                        try {
                            admin = UserDAO.getUserById(adminId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Get worker details (if they exist)
                    try {
                        worker = UserDAO.getUserById(workerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Shift shift = new Shift(shiftId, start, end, client.getId(), client.getName(), admin.getId(), (admin.getfName() + " "
                            + admin.getsName()), worker.getId(), (worker.getfName() + " " + worker.getsName()));
                    shifts.add(shift);
                }
            }
        }
        return shifts;
    }
}