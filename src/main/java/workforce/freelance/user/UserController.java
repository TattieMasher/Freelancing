package workforce.freelance.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workforce.freelance.client.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Check that request MAPPiNGS are to identify and that request BODIES are to supply data
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private ClientRepository clientRepository;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (isValidUserId(id)) {
            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                return ResponseEntity.badRequest().body("User retrieval failed: User with ID " + id + " not found");
            }
        } else {
            return ResponseEntity.badRequest().body("User retrieval failed: Invalid user ID supplied");
        }
    }

    @PutMapping("/get/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Validate supplied id as != null and > 0
        if (!isValidUserId(id)) {
            return ResponseEntity.badRequest().body("User update failed: Invalid user ID given");
        }

        // Check if the User with the given id exists in the database
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // User with the given id not found
        }

        // Validate the 'updatedUser' email address (using regex)- TODO: Move this into the validateUserOperation later
        boolean isValidEmail = User.isValidEmailAddress(updatedUser.getEmail());
        if (!isValidEmail) {
            return ResponseEntity.badRequest().body("User update failed: Invalid User object supplied");
        }

        // Validate the 'updatedUser' object fields (set updateExistingUser to true, because this is an update operation)
        boolean isValidUser = validateUserOperation(id, updatedUser, true);
        if (!isValidUser) {
            return ResponseEntity.badRequest().body("User update failed: Invalid User object supplied");
        }

        // Get the existing User entity from the optional
        User existingUser = existingUserOptional.get();

        // Update the fields of the existing User entity with the data from updatedUser
        existingUser.setfName(updatedUser.getfName());
        existingUser.setsName(updatedUser.getsName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setVerified(updatedUser.isVerified());
        existingUser.setUserType(updatedUser.getUserType());

        // Check if the client field is set in the updatedUser
        if (updatedUser.getClient() != null) {
            // Fetch the corresponding Client entity from the database using its ID
            Optional<Client> existingClientOptional = clientRepository.findById(updatedUser.getClient().getId());
            if (existingClientOptional.isPresent()) {
                Client existingClient = existingClientOptional.get();
                existingUser.setClient(existingClient);
            } else {
                return ResponseEntity.badRequest().body("User update failed: Invalid Client ID supplied"); // Invalid client ID provided
            }
        } else {
            // If the client field is null in updatedUser, set it to null in the existing User entity as well
            existingUser.setClient(null);
        }

        // Save the updated user
        User savedUser = userRepository.save(existingUser);

        return ResponseEntity.ok(savedUser); // Return the updated user in the response
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

    // Validate the 'updatedUser' object
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

    // Validate a user object operation. clientId must be > 0 and != null. Supplied User object must not be null and, if updating a User record, the db must contain the same record supplied.
    private boolean validateUserOperation(Long userId, User userToUpdate, boolean updateExistingUser) {
        // If supplied client is to be updated, or deleted, rather than created
        if (updateExistingUser) {
            return isValidUserId(userId) && userToUpdate != null && userToUpdate.getId().equals(userId);
        } else {
            return isValidUserId(userId) && userToUpdate != null;
        }
    }
}