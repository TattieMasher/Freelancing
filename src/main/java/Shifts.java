import java.util.ArrayList;
import java.util.List;

public class Shifts {
    private List<Shift> shifts;

    public Shifts() {
        shifts = new ArrayList<>();
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public void removeShift(Shift shift) {
        shifts.remove(shift);
    }

    public List<Shift> getAllShifts() {
        return shifts;
    }

    // Other methods to implement
}
