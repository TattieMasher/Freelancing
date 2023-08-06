package workforce.freelance.shift;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workforce.freelance.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * TODO: Check that request MAPPiNGS are to identify and that request BODIES are to supply data
 */
@RestController
@RequestMapping("/api/shifts")
public class ShiftController {
    private ShiftRepository shiftRepository;

    public ShiftController (ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @GetMapping("/get")
    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable Long id) {
        if (isValidId(id)) {
            Optional<Shift> shiftOptional = shiftRepository.findById(id);

            if (shiftOptional.isPresent()) {
                return ResponseEntity.ok(shiftOptional.get());
            } else {
                return ResponseEntity.badRequest().body("Shift retrieval failed: Shift with ID " + id + " not found");
            }
        } else {
            return ResponseEntity.badRequest().body("Shift retrieval failed: Invalid shift ID supplied");
        }
    }

    @GetMapping("/getByWorker/{id}")
    public List<Shift> getShiftsByWorkerId(@PathVariable Long id) {
        if (isValidId(id)) {
            return shiftRepository.findByWorkerId(id);
        } else {
            throw new IllegalArgumentException("Invalid worker ID supplied");
        }
    }

    @GetMapping("/getFiltered") // Dates MUST be formatted like ({"startDate":"YYYY-MM-DDTHH:mm"})
    public List<Shift> getFilteredShifts(@RequestBody Map<String, String> filters) {
        LocalDateTime startDate = null; // Declare filters
        LocalDateTime endDate = null;
        Long workerId = null;
        Long clientId = null;

        // Parse filter values from the map parameter
        if (filters.containsKey("startDate")) {
            startDate = LocalDateTime.parse(filters.get("startDate"));
        }
        if (filters.containsKey("endDate")) {
            endDate = LocalDateTime.parse(filters.get("endDate"));
        }
        if (filters.containsKey("workerId")) {
            String workerIdStr = filters.get("workerId");
            if (workerIdStr != null && !workerIdStr.isEmpty()) {
                if ("null".equalsIgnoreCase(workerIdStr)) {
                    workerId = null;
                } else {
                    workerId = Long.parseLong(workerIdStr);
                }
            }
        }
        if (filters.containsKey("clientId")) {
            clientId = Long.parseLong(filters.get("clientId"));
        }

        // Call and return the Shift filtering query
        return shiftRepository.findFilteredShifts(startDate, endDate, workerId, clientId);
    }

    // Validate shift id parameter
    public boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    // Check if the user with the given id exists in the database (calls isValidId)
    private boolean doesShiftExist(Long id) {
        // Validate id as not null and > 0
        if(!isValidId(id)) {
            return false;
        }

        return shiftRepository.findById(id).isPresent();
    }

    // Validate the 'updatedShift' object
    private boolean isValidShift(Long id, User updatedShift ) {
        return updatedShift != null && updatedShift .getId() != null && updatedShift .getId().equals(id);
    }


}
