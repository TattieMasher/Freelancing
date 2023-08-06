package workforce.freelance.shift;

import jakarta.persistence.*;
import workforce.freelance.client.Client;
import workforce.freelance.user.User;

import java.math.BigDecimal;
import java.time.Duration;
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

    @Transient // TODO: Check me
    private Duration duration;

    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmountPaid;

    // Constructors
    public Shift() {
        System.out.println("Default constructor used");
    }

    public Shift(int id, Client client, User admin, User worker, LocalDateTime startDate, LocalDateTime endDate, BigDecimal rate, BigDecimal totalAmountPaid) {
        this.id = id;
        this.client = client;
        this.admin = admin;
        this.worker = worker;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;

        System.out.println("Parameter constructor used");

        // Calculate the duration of the shift
        calculateShiftDuration();

        // Calculate the total amount paid for a shift
        calculateTotalAmountPaid();
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    // Calculate and set the duration of this Shift object (TODO: sort out time unit)
    private void calculateShiftDuration() {
        duration = Duration.between(startDate, endDate);
    }

    // Calculate the total £ value of a shift, based on the hourly rate and time of a shift
    private void calculateTotalAmountPaid() {
        // Convert the duration to hours (get seconds and divide by 3600)
        long durationInHours = duration.getSeconds() / 3600;

        // Calculate the total amount paid: totalAmountPaid = rate * durationInHours
        totalAmountPaid = rate.multiply(BigDecimal.valueOf(durationInHours));
    }


}