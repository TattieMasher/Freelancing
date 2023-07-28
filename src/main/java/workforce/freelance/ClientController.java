package workforce.freelance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Check that request MAPPiNGS are to identify and that request BODIES are to supply data
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/get")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a client
    @GetMapping("/get/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientRepository.findById(id);
    }

    @PostMapping
    public Client createClient(@RequestBody Client newClient) {
        return clientRepository.save(newClient);
    }

    @PutMapping("/get/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        // Validate the 'id' parameter
        if (!isValidClientId(id)) {
            return ResponseEntity.badRequest().build(); // Invalid 'id' parameter
        }

        // Check if the client with the given ID exists in the database
        if (!doesClientExist(id)) {
            return ResponseEntity.notFound().build(); // Client with the given ID not found
        }

        // Validate the 'updatedClient' object
        if (!isValidUpdatedClient(id, updatedClient)) {
            return ResponseEntity.badRequest().build(); // Invalid 'updatedClient' object or ID mismatch
        }

        // Set the correct ID for the updated client
        updatedClient.setId(id);

        // Save the updated client
        Client savedClient = clientRepository.save(updatedClient);

        return ResponseEntity.ok(savedClient); // Return the updated client in the response
    }

    @DeleteMapping("/get/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }

    // Validation methods
    // Validate client id parameters
    private boolean isValidClientId(Long id) {
        return id != null && id > 0;
    }

    // Check if the client with the given id exists in the database
    private boolean doesClientExist(Long id) {
        return clientRepository.findById(id).isPresent();
    }

    // Validate the 'updatedClient' object
    private boolean isValidUpdatedClient(Long id, Client updatedClient) {
        return updatedClient != null && updatedClient.getId() != null && updatedClient.getId().equals(id);
    }
}