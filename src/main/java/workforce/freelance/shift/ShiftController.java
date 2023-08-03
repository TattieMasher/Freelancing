package workforce.freelance.shift;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workforce.freelance.client.ClientRepository;

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

}
