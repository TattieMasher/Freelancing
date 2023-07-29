package workforce.freelance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Check that request MAPPiNGS are to identify and that request BODIES are to supply data
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable int id) {
        Optional<Object> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return (User) userOptional.get();
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    @PutMapping("/get/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Check if the User with the given id exists in the database

        updatedUser.setId(id); // Ensure the correct ID is set for the updated User
        return userRepository.save(updatedUser);
    }

    // Validate user id parameter
    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    // Check if the user with the given id exists in the database (calls isValidId)
    private boolean doesUserExist(Long id) {
        // Validate id as not null and > 0
        if(!isValidId(id)) {
            return false;
        }

        return userRepository.findById(id).isPresent();
    }

    // Validate the 'updatedClient' object
    private boolean isValidUser(Long id, User updatedUser) {
        return updatedUser != null && updatedUser.getId() != null && updatedUser.getId().equals(id);
    }

    // Validation method for 'userToDelete' object
    private ResponseEntity<String> isValidClientToDelete(Long id, Client clientToDelete) {
        if (clientToDelete == null || !id.equals(clientToDelete.getId())) {
            return ResponseEntity.badRequest().body("Client ID mismatch");
        }

        // If all validations pass, return null to indicate success
        return null;
    }
}