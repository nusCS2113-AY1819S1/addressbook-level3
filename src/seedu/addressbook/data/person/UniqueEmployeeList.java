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
    public static class DuplicateEmployeeException extends DuplicateDataException {
        protected DuplicateEmployeeException() {
            super("Operation would result in duplicate employees");
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
    public UniqueEmployeeList(Collection<Employee> employees) throws DuplicateEmployeeException {
        if (!Utils.elementsAreUnique(employees)) {
            throw new DuplicateEmployeeException();
        }
        employeeInternalList.addAll(employees);
    }

    public List<ReadOnlyPerson> immutableListView() {
        return Collections.unmodifiableList(employeeInternalList);
    }

    /**
     * Checks if the list contains an equivalent employee as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        return employeeInternalList.contains(toCheck);
    }

    /**
     * Adds an employeeto the list.
     *
     * @throws UniqueEmployeeList.DuplicateEmployeeException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Employee toAdd) throws UniqueEmployeeList.DuplicateEmployeeException {
        if (contains(toAdd)) {
            throw new UniqueEmployeeList.DuplicateEmployeeException();
        }
        employeeInternalList.add(toAdd);
    }

    @Override
    public Iterator<Employee> iterator() {
        return employeeInternalList.iterator();
    }
}
