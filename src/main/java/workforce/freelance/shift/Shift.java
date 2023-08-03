package workforce.freelance.shift;

import jakarta.persistence.*;
import workforce.freelance.client.Client;
import workforce.freelance.user.User;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
public class Shift implements Comparable<Shift> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @Column(name = "shift_admin_id")
    private Integer adminId; // Integer wrapper to allow for null values

    @Column(name = "worker_id")
    private Integer workerId; // Integer wrapper to allow for null values

    @Column(name = "start", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end", nullable = false)
    private LocalDateTime endDate;

    private Duration duration;

    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    //  private List<String> requiredSkills;        TODO: Figure out how I want to use this

    private Client client;

    private User admin;
    private int worker;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmountPaid; // Total amount paid in pounds and pence

    public Shift() {
    }

    public Shift(int id, int clientId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal rate, Client client) {
        this.id = id;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
        this.client = client;

        // Calculate the duration of the shift
        calculateShiftDuration();

        // Calculate the total amount paid for a shift
        calculateTotalAmountPaid();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public int getWorker() {
        return worker;
    }

    public void setWorker(int worker) {
        this.worker = worker;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    // Compare Shift objects based on their start and end dates. If the same, compare IDs
    @Override
    public int compareTo(Shift other) {
        int startComparison = this.getStartDate().compareTo(other.getStartDate());
        if (startComparison != 0) {
            // If the start values are different, return the comparison result
            return startComparison;
        }

        int endComparison = this.getEndDate().compareTo(other.getEndDate());
        if (endComparison != 0) {
            // If the end values are different, return the comparison result
            return endComparison;
        }

        // If both start and end values are the same, compare based on the id
        return Integer.compare(this.getId(), other.getId());
    }
}