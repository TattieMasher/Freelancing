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
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        if (isValidUserId(userId)) {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                return ResponseEntity.badRequest().body("User retrieval failed: User with ID " + userId + "not found");
            }
        } else {
            return ResponseEntity.badRequest().body("User retrieval failed: Invalid user ID supplied");
        }
    }

    @PutMapping("/get/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Check if the User with the given id exists in the database

        updatedUser.setId(id); // Ensure the correct ID is set for the updated User
        return userRepository.save(updatedUser);
    }

    // Validate user id parameter
    private boolean isValidUserId(Long id) {
        return id != null && id > 0;
    }

    // Check if the user with the given id exists in the database (calls isValidId)
    private boolean doesUserExist(Long id) {
        // Validate id as not null and > 0
        if(!isValidUserId(id)) {
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

    // Validate a Client object operation. clientId must be > 0 and != null. Supplied client object must not be null and, if updating a client record, the db must contain the same record supplied.
    private boolean validateUserOperation(Long userId, User userToUpdate, boolean updateExistingUser) {
        // If supplied client is to be updated, or deleted, rather than created
        if (updateExistingUser) {
            return isValidUserId(userId) && userToUpdate != null && userToUpdate.getId().equals(userId);
        } else {
            return isValidUserId(userId) && userToUpdate != null;
        }
    }
}