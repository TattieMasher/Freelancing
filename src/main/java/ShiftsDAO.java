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
                    int adminId = resultSet.getInt("shift_admin_id");
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    // Create objects from other DAO classes
                    Client client = null;
                    User admin;
                    User worker;

                    // Get Client details (cannot be null in DB)
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Create shift, to add extra items to, if found in the subsequent database queries
                    Shift shift = new Shift(shiftId, start, end, rate, client.getId(), client.getName());

                    // Get shift admin details (can be null) TODO: check if we want this, going forward
                    if (adminId != 0) {     // 0 if NULL in DB
                        try {
                            admin = UserDAO.getUserById(adminId);
                            shift.setAdminId(admin.getId());                                    // Set from query return
                            shift.setAdminName(admin.getfName() + " " + admin.getsName());      // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Get worker details (if they exist)
                    if (workerId != 0) {     // 0 if NULL in DB
                        try {
                            worker = UserDAO.getUserById(workerId);
                            shift.setWorkerId(worker.getId());                                  // Set from query return
                            shift.setWorkerName(worker.getfName() + " " + worker.getsName());   // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Add to collection of shifts
                    shifts.add(shift);
                }
            }
        }
        return shifts;
    }

    public static TreeSet<Shift> getAllShiftsByWorker(int id) throws SQLException {
        TreeSet<Shift> shifts = new TreeSet<>();

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shift WHERE worker_id = ?")) {
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
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    // Create objects from other DAO classes
                    Client client = null;
                    User admin = null;
                    User worker = null;

                    // Get Client details (cannot be null in DB)
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Create shift, to add extra items to, if found in the subsequent database queries
                    Shift shift = new Shift(shiftId, start, end, rate, client.getId(), client.getName());

                    // Get shift admin details (can be null) TODO: check if we want this, going forward
                    if (adminId != 0) {     // 0 if NULL in DB
                        try {
                            admin = UserDAO.getUserById(adminId);
                            shift.setAdminId(admin.getId());                                    // Set from query return
                            shift.setAdminName(admin.getfName() + " " + admin.getsName());      // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Get worker details (if they exist)
                    if (adminId != 0) {     // 0 if NULL in DB
                        try {
                            worker = UserDAO.getUserById(workerId);
                            shift.setWorkerId(worker.getId());                                  // Set from query return
                            shift.setWorkerName(worker.getfName() + " " + worker.getsName());   // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Add to collection of shifts
                    shifts.add(shift);
                }
            }
        }
        return shifts;
    }

    public static Shift getShiftById(int id) throws SQLException {
        Shift shift = null;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shift WHERE id = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve column values from the result set
                    int shiftId = resultSet.getInt("id");
                    LocalDateTime start = resultSet.getObject("start", LocalDateTime.class); // Retrieve as LocalDateTime
                    LocalDateTime end = resultSet.getObject("end", LocalDateTime.class);
                    List<String> requiredSkills = null; // TODO: Populate this (way down the line)
                    int clientId = resultSet.getInt("client_id");
                    int adminId = resultSet.getInt("shift_admin_id");
                    int workerId = resultSet.getInt("worker_id");
                    float rate = resultSet.getFloat("rate");

                    // Create objects from other DAO classes
                    Client client = null;
                    User admin;
                    User worker;

                    // Get Client details (cannot be null in DB)
                    try {
                        client = ClientDAO.getClientById(clientId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Create shift, to add extra items to, if found in the subsequent database queries
                    shift = new Shift(shiftId, start, end, rate, client.getId(), client.getName());

                    // Get shift admin details (can be null) TODO: check if we want this, going forward
                    if (adminId != 0) {     // 0 if NULL in DB
                        try {
                            admin = UserDAO.getUserById(adminId);
                            shift.setAdminId(admin.getId());                                    // Set from query return
                            shift.setAdminName(admin.getfName() + " " + admin.getsName());      // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Get worker details (if they exist)
                    if (workerId != 0) {     // 0 if NULL in DB
                        try {
                            worker = UserDAO.getUserById(workerId);
                            shift.setWorkerId(worker.getId());                                  // Set from query return
                            shift.setWorkerName(worker.getfName() + " " + worker.getsName());   // Concat first and last name
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return shift;
    }

    public static boolean addShift(Shift shift, Client client, User admin, User worker) throws SQLException {
        boolean result = false;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Shift (client_id, shift_admin_id, worker_id," +
                     " start, end, rate) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, client.getId());
            statement.setInt(2, admin.getId());
            statement.setInt(3, worker.getId());
            statement.setObject(4, shift.getStartDate());
            statement.setObject(5, shift.getEndDate());
            statement.setFloat(6, shift.getRate());

            int rowsAffected = statement.executeUpdate();
            result = (rowsAffected > 0);    // Set return from result of query
        }

        return result;
    }

    public static boolean deleteShift(Shift shift) throws SQLException {
        boolean result = false;

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Shift WHERE id = ?")) {
            statement.setInt(1, shift.getId());

            int rowsAffected = statement.executeUpdate();
            result = (rowsAffected > 0);    // Set return from result of query
        }

        return result;
    }
}
