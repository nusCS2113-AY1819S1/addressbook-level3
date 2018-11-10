package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's scheduled appointment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchedule(String)}
 */
public class Schedule {

    public static final String EXAMPLE = "31-01-2018";
    public static final String EXAMPLE2 = "31-12-2018";
    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "The day of appointment should be written in the form of DD-MM-YYYY";
    public static final String SCHEDULE_VALIDATION_REGEX = "(((0[1-9]|[1-2][0-9]|3[0-1])-(0[13578]|(10|12)))|((0[1-9]|[1-2][0-9])-02)|((0[1-9]|[1-2][0-9]|30)-(0[469]|11)))-[0-9]{4}";

    public final String value;


    /**
     * Validates given appointment date.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Schedule(String schedule) throws IllegalValueException {
        schedule = schedule.trim();
        if (!isValidSchedule(schedule)) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_CONSTRAINTS);
        }
        this.value = schedule;
    }

    /**
     * Checks if a given string is a valid date.
     */
    public static boolean isValidSchedule(String test) {
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value + " ";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.value.equals(((Schedule) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
