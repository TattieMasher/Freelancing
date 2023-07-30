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
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientToUpdate) {
        // Validate supplied id as != null and > 0
        if (!isValidClientId(id)) {
            return ResponseEntity.notFound().build();   // Invalid client id given
        }

        // Check if the Client with the given id exists in the database
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (existingClientOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Client with the given id not found
        }

        // Validate the 'clientToUpdate' object fields
        if (!isValidUpdatedClient(id, clientToUpdate)) {
            return ResponseEntity.badRequest().build(); // Invalid 'clientToUpdate' object or id mismatch
        }

        // Get the existing Client entity from the optional
        Client existingClient = existingClientOptional.get();

        // Update the fields of the existing Client entity with the data from clientToUpdate
        existingClient.setName(clientToUpdate.getName());

        // Check if the primaryUser field is set in the clientToUpdate
        if (clientToUpdate.getPrimaryUser() != null) {
            // Set the primaryUser for the existing Client entity and synchronize the User entity's client field
            existingClient.setPrimaryUser(clientToUpdate.getPrimaryUser());
        } else {
            // If the primaryUser is null in clientToUpdate, set it to null in the existing Client entity as well
            existingClient.setPrimaryUser(null);
        }

        // Save the updated client
        Client savedClient = clientRepository.save(existingClient);

        return ResponseEntity.ok(savedClient); // Return the updated client in the response
    }

    @DeleteMapping("/get/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id, @RequestBody Client clientToDelete) {
        // Check if the client with the given id exists in the database
        if (!doesClientExist(id)) {
            return ResponseEntity.notFound().build(); // Client with the given id not found
        }

        // Validate the 'clientToDelete' object
        ResponseEntity<String> clientValidationResponse = isValidClientToDelete(id, clientToDelete);
        if (clientValidationResponse != null) {
            return clientValidationResponse; // Invalid 'clientToDelete' object or id mismatch
        }

        // Perform the delete operation
        clientRepository.deleteById(id);

        return ResponseEntity.ok("Client deleted successfully");
    }

    // Validation methods

    // Validate client id parameter
    private boolean isValidClientId(Long id) {
        return id != null && id > 0;
    }

    // Check if the client with the given id exists in the database (calls isValidId)
    private boolean doesClientExist(Long id) {
        // Validate id as not null and > 0
        if(!isValidClientId(id)) {
            return false;
        }

        return clientRepository.findById(id).isPresent();
    }

    // Validate the 'updatedClient' object, cannot be null and must have an existing id in database
    private boolean isValidUpdatedClient(Long id, Client updatedClient) {
        return updatedClient != null && updatedClient.getId() != null && updatedClient.getId().equals(id);
    }

    // Validation method for 'clientToDelete' object
    private ResponseEntity<String> isValidClientToDelete(Long id, Client clientToDelete) {
        if (clientToDelete == null || !id.equals(clientToDelete.getId())) {
            return ResponseEntity.badRequest().body("Client ID mismatch");
        }

        // If all validations pass, return null to indicate success
        return null;
    }
}