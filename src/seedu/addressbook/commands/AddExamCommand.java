package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.UniqueExamList;

/**
 * Adds an exam in the exam book.
 */
public class AddExamCommand extends Command {

    public static final String COMMAND_WORD = "addexam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a exam to the exam book. "
            + "Exams can be marked private by prepending 'p' to the prefix of the exam name.\n\t"
            + "Parameters: [p]e/EXAM_NAME s/SUBJECT_NAME  d/EXAM_DATE st/EXAM_START_TIME "
            + "et/EXAM_END_TIME \n\t\tdt/EXAM_DETAILS\n\t"
            + "Example: " + COMMAND_WORD
            + " e/Math Mid-Terms 2018S1 s/Mathematics d/01-12-2018 st/09:00 et/12:00 dt/At MPSH";

    public static final String MESSAGE_SUCCESS = "New%2$s exam added : %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "This exam already exists in the exam book! ";

    private final Exam toAdd;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public AddExamCommand() {
        this.toAdd = null;
    }

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddExamCommand(String examName,
                          String subjectName, String examDate, String examStartTime,
                          String examEndTime, String examDetails,
                          boolean isExamPrivate) throws IllegalValueException {
        this.toAdd = new Exam(examName, subjectName, examDate, examStartTime, examEndTime, examDetails, isExamPrivate);
    }

    public ReadOnlyExam getExam() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            examBook.addExam(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, toAdd.isPrivate() ? " private" : ""));
        } catch (UniqueExamList.DuplicateExamException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_EXAM);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.EXAM;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
