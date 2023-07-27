package workforce.freelance;

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
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        updatedUser.setId(id); // Ensure the correct ID is set for the updated User
        return userRepository.save(updatedUser);
    }

    @PutMapping("/get/{id}/first-name")
    public User updateFirstName(@PathVariable int id, @RequestBody String firstName) {
        User existingUser = getUserById(id);
        existingUser.setfName(firstName);
        return userRepository.save(existingUser);
    }

    @PutMapping("/get/{id}/last-name")
    public User updateLastName(@PathVariable int id, @RequestBody String lastName) {
        User existingUser = getUserById(id);
        existingUser.setsName(lastName);
        return userRepository.save(existingUser);
    }

    @PutMapping("/get/{id}/email")
    public User updateEmail(@PathVariable int id, @RequestBody String email) {
        User existingUser = getUserById(id);
        existingUser.setEmail(email);
        return userRepository.save(existingUser);
    }

    @PutMapping("/get/{id}/verified")
    public User updateVerifiedStatus(@PathVariable int id, @RequestBody boolean verified) {
        User existingUser = getUserById(id);
        existingUser.setVerified(verified);
        return userRepository.save(existingUser);
    }

    @PutMapping("/get/{id}/userType")
    public User updateUserType(@PathVariable int id, @RequestBody int userType) {
        User existingUser = getUserById(id);
        existingUser.setUserType(userType);
        return userRepository.save(existingUser);
    }
}