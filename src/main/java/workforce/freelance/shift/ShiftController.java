package workforce.freelance.shift;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workforce.freelance.user.User;

import java.util.List;
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
