package seedu.contax.model.appointment;

import seedu.contax.model.chrono.ScheduleItem;
import seedu.contax.model.chrono.TimeRange;

/**
 * Represents an empty slot in the schedule.
 */
public class AppointmentSlot extends ScheduleItem {

    public AppointmentSlot(TimeRange slotRange) {
        super(slotRange);
    }

    @Override
    public String toString() {
        return "Start Date Time: "
                + getStartDateTime()
                + "; End Date Time: "
                + getEndDateTime();
    }
}