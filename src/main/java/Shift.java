import java.time.LocalDateTime;
import java.util.List;

public class Shift {
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> requiredSkills;

    private final int clientId;
    private final String clientName;

    private int adminId;
    private String adminName;
    private int workerId;
    private String workerName;

    public Shift(LocalDateTime startDate, LocalDateTime endDate, List<String> requiredSkills, int clientId, String clientName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredSkills = requiredSkills;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Shift(LocalDateTime startDate, LocalDateTime endDate, int clientId, String clientName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    // Other methods to implement
}