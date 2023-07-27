package workforce.freelance;

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
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        updatedClient.setId(id); // Ensure the correct ID is set for the updated client
        return clientRepository.save(updatedClient);
    }

    @DeleteMapping("/get/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}