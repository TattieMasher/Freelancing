import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
            User user = dao.getUserById(3);
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
    }
}