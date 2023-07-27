import java.time.LocalDateTime;
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

    public boolean trimByDate(LocalDateTime start, LocalDateTime end) {
        TreeSet<Shift> newShifts = new TreeSet<>();
        boolean trimmed = false;

        for(Shift shift : shifts) {
            //  Add currently iterated shift if after specified start date and before
            if (shift.getStartDate().isAfter(start) && shift.getEndDate().isBefore(end)) {
                newShifts.add(shift);
                trimmed = true;
            }
        }

        shifts = newShifts;         // Return the new (or unchanged!) TreeSet
        return trimmed;             // Return the result of the trimming
    }

    // Other methods and operations
}