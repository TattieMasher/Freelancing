import java.util.*;

public class Shifts {
    private TreeSet<Shift> shifts;

    public Shifts() {
        shifts = new TreeSet<>(Comparator.comparing(Shift::getStartDate));
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public void removeShift(Shift shift) {
        shifts.remove(shift);
    }

    public TreeSet<Shift> getAllShifts() {
        return shifts;
    }

    // Other methods and operations
}