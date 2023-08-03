package workforce.freelance.shift;

import jakarta.persistence.*;
import workforce.freelance.client.Client;
import workforce.freelance.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "shift_admin_id")
    private User admin;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private User worker;

    @Column(name = "start", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmountPaid;

// Constructors, getters, setters, and other methods
    public Shift() {
    }

}