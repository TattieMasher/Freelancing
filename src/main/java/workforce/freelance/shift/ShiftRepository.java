package workforce.freelance.shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workforce.freelance.client.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findAll(); // Query all clients
    Optional<Shift> findById(Long id); //
}