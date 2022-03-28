package seedu.contax.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.contax.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.contax.model.chrono.ScheduleItem;
import seedu.contax.model.person.Person;

/**
 * Represents an appointment in the Schedule. Time related functionality is implemented in the superclass
 * {@link ScheduleItem}.
 */
public class Appointment extends ScheduleItem {

    // Appointment identification fields
    private final StartDateTime startDateTime;

    // Data fields
    private final Name name;
    private final Duration duration;
    private final Person person;
    private final Priority priority;

    /**
     * Constructs an {@code Appointment}.
     * The fields {@code name, startDateTime, duration} must be present and not null.
     * The {@code person} argument is optional, and may be null.
     *
     * @param name A valid Appointment Name.
     * @param startDateTime A valid Appointment Starting DateTime.
     * @param duration A valid Appointment Duration.
     * @param person A valid Person or null.
     */
    public Appointment(Name name, StartDateTime startDateTime, Duration duration, Person person) {
        super(Appointment.getStartDateTimeOrThrow(startDateTime),
                Appointment.computeEndDateTime(startDateTime, duration));
        requireNonNull(name);

        this.name = name;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.person = person;
        this.priority = null;
    }

    /**
     * Constructs an {@code Appointment} with additional attribute priority.
     * The fields {@code name, startDateTime, duration} must be present and not null.
     * The fields {@code priority} is optional and may be null.
     *
     * @param name A valid Appointment Name.
     * @param startDateTime A valid Appointment Starting DateTime.
     * @param duration A valid Appointment Duration.
     * @param person A valid Person or null.
     * @param priority A valid priority level.
     */
    public Appointment(Name name, StartDateTime startDateTime, Duration duration, Person person, Priority priority) {
        super(Appointment.getStartDateTimeOrThrow(startDateTime),
                Appointment.computeEndDateTime(startDateTime, duration));
        requireNonNull(name);

        this.name = name;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.person = person;
        this.priority = priority;
    }

    public Name getName() {
        return this.name;
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return this.startDateTime.value;
    }

    public StartDateTime getStartDateTimeObject() {
        return this.startDateTime;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Person getPerson() {
        return person;
    }

    public Priority getPriority() {
        return priority;
    }

    public Appointment withPriority(Priority priority) {
        return new Appointment(name, startDateTime, duration, person, priority);
    }

    /**
     * Creates a new {@code Appointment} instance with the supplied {@code Person} object.
     *
     * @param newPerson The person object, or null, to replace the current associated person.
     * @return A new immutable instance of Appointment with the updated Person.
     */
    public Appointment withPerson(Person newPerson) {
        return new Appointment(name, startDateTime, duration, newPerson);
    }

    /**
     * Returns the ending DateTime of this appointment.
     *
     * @return The end DateTime of this appointment.
     */
    @Override
    public LocalDateTime getEndDateTime() {
        return Appointment.computeEndDateTime(startDateTime, duration);
    }

    /**
     * Returns true if both appointments have the same name and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;

        return otherAppointment.getName().equals(getName())
                && otherAppointment.getStartDateTimeObject().equals(getStartDateTimeObject())
                && otherAppointment.getDuration().equals(getDuration())
                && Objects.equals(otherAppointment.getPerson(), getPerson())
                && Objects.equals(otherAppointment.getPriority(), getPriority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDateTime, duration, person, priority);
    }

    @Override
    public String toString() {
        return "**Name:** "
                + getName()
                + "\n **Start Date Time:** "
                + getStartDateTimeObject()
                + "\n **Duration:** "
                + getDuration()
                + "\n **Person:** "
                + (getPerson() == null ? "None" : getPerson().getName())
                + "\n **Priority:** "
                + getPriority();
    }

    /**
     * Extracts the LocalDateTime object from the supplied StartDateTime object.
     *
     * @param startDateTimeObject The StartDateTime container to extract from.
     * @return The extracted LocalDateTime object.
     */
    private static LocalDateTime getStartDateTimeOrThrow(StartDateTime startDateTimeObject) {
        requireNonNull(startDateTimeObject);
        return startDateTimeObject.value;
    }

    /**
     * Computes the ending time from the {@code StartDateTime} and {@code Duration}.
     *
     * @param startDateTime The StartDateTime container use.
     * @param duration The Duration container to use.
     * @return The computed end date-time.
     */
    private static LocalDateTime computeEndDateTime(StartDateTime startDateTime, Duration duration) {
        requireAllNonNull(startDateTime, duration);
        return startDateTime.value.plusMinutes(duration.value);
    }
}
