package workforce.freelance.client;

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
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        if (isValidClientId(id)) {
            Optional<Client> clientOptional = clientRepository.findById(id);

            if (clientOptional.isPresent()) {
                return ResponseEntity.ok(clientOptional.get());
            } else {
                return ResponseEntity.badRequest().body("Client retrieval failed: Client with ID " + id + " not found");
            }
        }

        return ResponseEntity.badRequest().body("Client retrieval failed: Client Invalid client ID supplied");
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client newClient) {
        // Validate the 'newClient' object fields for creating a new client
        boolean isValidOperation = validateClientOperation(newClient.getId(), newClient, false);
        if (!isValidOperation) {
            return ResponseEntity.badRequest().body("Client creation failed: Invalid client object supplied");
        }

        Client savedClient = clientRepository.save(newClient);
        return ResponseEntity.ok(savedClient); // Return the created client in the response
    }

    @PutMapping("/get/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody Client clientToUpdate) {
        // Validate supplied id as != null and > 0
        if (!isValidClientId(id)) {
            return ResponseEntity.badRequest().body("Client update failed: Invalid client ID given");   // Invalid client id given
        }

        // Check if the Client with the given id exists in the database
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (existingClientOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Client with the given id not found
        }

        // Validate the 'clientToUpdate' object fields
        boolean isValidClient = validateClientOperation(id, clientToUpdate, true);
        if (!isValidClient) {
            return ResponseEntity.badRequest().body("Client update failed: Invalid Client object supplied"); // Invalid 'clientToUpdate' object or id mismatch
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
        // Validate supplied id as != null and > 0
        if (!isValidClientId(id)) {
            return ResponseEntity.badRequest().body("Invalid client ID given"); // Invalid client id given
        }

        // Check if the client with the given id exists in the database
        if (!doesClientExist(id)) {
            return ResponseEntity.notFound().build(); // Client with the given id not found
        }

        // Validate the 'clientToDelete' object
        boolean isValidOperation = validateClientOperation(id, clientToDelete, true);
        if (!isValidOperation) {
            return ResponseEntity.badRequest().body("Invalid 'clientToDelete' object or ID mismatch");
        }

        // Perform the delete operation
        clientRepository.deleteById(id);

        return ResponseEntity.ok("Client deleted successfully");
    }


    // Validation methods
    // Validate client id parameter as != null and > 0
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

    // Validate a Client object operation. clientId must be > 0 and != null. Supplied client object must not be null and, if updating a client record, the db must contain the same record supplied.
    private boolean validateClientOperation(Long clientId, Client clientToUpdate, boolean updateExistingClient) {
        // If supplied client is to be updated, or deleted, rather than created
        if (updateExistingClient) {
            return isValidClientId(clientId) && clientToUpdate != null && clientToUpdate.getId().equals(clientId);
        } else {
            return isValidClientId(clientId) && clientToUpdate != null;
        }
    }
}