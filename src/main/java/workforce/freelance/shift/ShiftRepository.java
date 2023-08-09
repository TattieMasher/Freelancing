package workforce.freelance.shift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import workforce.freelance.client.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findAll(); // Query all shifts
    Optional<Shift> findById(Long id); // Query shift by id
    List<Shift> findByWorkerId(Long id); // Query shift by worker id

    @Query("SELECT s FROM Shift s " +
            "WHERE (:startDate IS NULL OR s.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR s.endDate <= :endDate) " +
            "AND (:workerId IS NULL OR s.worker.id IS NULL OR s.worker.id = :workerId) " +
            "AND (:clientId IS NULL OR s.client.id = :clientId)")
    List<Shift> findFilteredShifts(LocalDateTime startDate, LocalDateTime endDate, Long workerId, Long clientId);
}