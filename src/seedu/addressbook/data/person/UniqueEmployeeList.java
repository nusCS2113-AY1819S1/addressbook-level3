package seedu.addressbook.data.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

public class UniqueEmployeeList implements Iterable<Employee>{


    private final List<Employee> employeeInternalList = new ArrayList<>();

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }


    /**
     * Constructs empty employee list.
     */
    public UniqueEmployeeList(){}

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueEmployeeList(UniqueEmployeeList source) {
        employeeInternalList.addAll(source.employeeInternalList);
    }

    // exact copy from UniquePersonList
    public UniqueEmployeeList(Collection<Employee> employees) throws DuplicatePersonException {
        if (!Utils.elementsAreUnique(employees)) {
            throw new DuplicatePersonException();
        }
        employeeInternalList.addAll(employees);
    }

    public List<ReadOnlyPerson> immutableListView() {
        return Collections.unmodifiableList(employeeInternalList);
    }

    @Override
    public Iterator<Employee> iterator() {
        return employeeInternalList.iterator();
    }
}
