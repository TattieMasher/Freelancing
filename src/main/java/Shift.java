import java.time.LocalDateTime;
import java.util.List;

public class Shift {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<String> requiredSkills;

    private final int clientId;

    private final String clientName;

    private int workerId;
    private String workerName;

    public Shift(LocalDateTime startTime, LocalDateTime endTime, List<String> requiredSkills, int clientId, String clientName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.requiredSkills = requiredSkills;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Shift(LocalDateTime startTime, LocalDateTime endTime, int clientId, String clientName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
