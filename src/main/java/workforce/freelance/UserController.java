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

    @GetMapping("/{id}")
    private User getUserById(@PathVariable int id) {
        Optional<Object> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return (User) userOptional.get();
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    @PutMapping("/{id}/firstName")
    public User updateFirstName(@PathVariable int id, @RequestBody String firstName) {
        User existingUser = getUserById(id);
        existingUser.setfName(firstName);
        return userRepository.save(existingUser);
    }

    @PutMapping("/{id}/lastName")
    public User updateLastName(@PathVariable int id, @RequestBody String lastName) {
        User existingUser = getUserById(id);
        existingUser.setsName(lastName);
        return userRepository.save(existingUser);
    }

    @PutMapping("/{id}/email")
    public User updateEmail(@PathVariable int id, @RequestBody String email) {
        User existingUser = getUserById(id);
        existingUser.setEmail(email);
        return userRepository.save(existingUser);
    }

    @PutMapping("/{id}/verified")
    public User updateVerifiedStatus(@PathVariable int id, @RequestBody boolean verified) {
        User existingUser = getUserById(id);
        existingUser.setVerified(verified);
        return userRepository.save(existingUser);
    }

    @PutMapping("/{id}/userType")
    public User updateUserType(@PathVariable int id, @RequestBody int userType) {
        User existingUser = getUserById(id);
        existingUser.setUserType(userType);
        return userRepository.save(existingUser);
    }
}