import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        // Various preliminary tests on user data operations
        UserDAO dao = new UserDAO();

        try {
            ArrayList<User> users = new ArrayList<>(dao.getAllUsers());

            for (User u : users) {
                System.out.println("Name: " + u.getfName() + " " + u.getsName());
                System.out.println("Email: " + u.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            User user = UserDAO.getUserById(3);
            System.out.println("Accessing user id 1...");
            System.out.println("Name: " + user.getfName() + " " + user.getsName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UserDAO.insertUser("Candace", "Scoular", "candyscouly@gmail.com", "Carmex123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Client c = ClientDAO.getClientById(1);
            System.out.println("Get client details: " + c.getName() + " " + c.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }
}