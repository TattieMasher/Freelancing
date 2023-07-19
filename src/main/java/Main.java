import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        // Various preliminary tests on user data operations
        UserDAO dao = new UserDAO();

        // Get all users
        try {
            ArrayList<User> users = new ArrayList<>(dao.getAllUsers());

            for (User u : users) {
                System.out.println("Name: " + u.getfName() + " " + u.getsName());
                System.out.println("Email: " + u.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Access specific user by id
        try {
            User user = UserDAO.getUserById(3);
            System.out.println("Accessing user id 1...");
            System.out.println("Name: " + user.getfName() + " " + user.getsName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add user
        try {
            UserDAO.insertUser("Candace", "Scoular", "candyscouly@gmail.com", "Carmex123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Access specific client by id
        try {
            Client c = ClientDAO.getClientById(1);
            System.out.println("Get client details: " + c.getName() + " " + c.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Access specific shift TODO: Deprecated create getShiftById()
        try {
            Shift shift = ShiftsDAO.getShift();
            System.out.println("Get shift details: " + shift.getId() + ", " + shift.getStartDate() + " - " + shift.getEndDate() + ": " + shift.getClientName());
            System.out.println("Worker id: " + shift.getWorkerId() + " " + shift.getWorkerName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TreeSet<Shift> shifts = ShiftsDAO.getAllShiftsByClient(1);
            System.out.println("Trying...");
            for (Shift shift : shifts) {
                System.out.println("Get shift details: " + shift.getId() + ", " + shift.getStartDate() + " - " + shift.getEndDate() + ": " + shift.getClientName());
                System.out.println("Worker id: " + shift.getWorkerId() + " " + shift.getWorkerName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get shifts from worker id. Handles none being returned.
        try {
            TreeSet<Shift> shifts = ShiftsDAO.getAllShiftsByWorker(3);
            System.out.println("Trying...");
            if (shifts.isEmpty()) {
                System.out.println("No found shifts");
            } else {
                for (Shift shift : shifts) {
                    System.out.println("Get shift details: " + shift.getId() + ", " + shift.getStartDate() + " - " + shift.getEndDate() + ": " + shift.getClientName());
                    System.out.println("Worker id: " + shift.getWorkerId() + " " + shift.getWorkerName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert shift from test data
        try {
            int shiftId = 1;
            LocalDateTime startDate = LocalDateTime.of(2023, 7, 15, 9, 0);
            LocalDateTime endDate = LocalDateTime.of(2023, 7, 15, 17, 0);
            float rate = 10.5f;
            int clientId = 1;
            String clientName = "Client Name";

            // Create a Shift object
            Shift shift = new Shift(shiftId, startDate, endDate, rate, clientId, clientName);

            try {
                Client client = ClientDAO.getClientById(1);

                User admin = UserDAO.getUserById(2);
                User worker = UserDAO.getUserById(2);

                try {
                    boolean added = ShiftsDAO.addShift(shift, client, admin, worker);
                    if (added) {
                        System.out.println("Shift added successfully.");
                    } else {
                        System.out.println("Failed to add shift.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Delete shift by id
        try {
            // Create a Shift object from DB
            Shift shift = ShiftsDAO.getShiftById(6);
            boolean deleted;

            System.out.println("Deleting shift id " + shift.getId());
            deleted = ShiftsDAO.deleteShift(shift);
            if (deleted) {
                System.out.println("Deleted.");
            } else {
                System.out.println("Failed...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}