package seedu.addressbook.data;

import seedu.addressbook.commands.Command;
import seedu.addressbook.data.person.Associated;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    private CommandHistory commandHistory;
    private CommandStack commandStack;
    private UniquePersonList allPersons;

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPersons = new UniquePersonList();
        commandHistory = new CommandHistory();
        commandStack = new CommandStack();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     */
    public AddressBook(UniquePersonList persons) {
        this.allPersons = new UniquePersonList(persons);
        this.commandHistory = new CommandHistory();
        this.commandStack = new CommandStack();
    }

    public void switchAddressBook(UniquePersonList persons){
        allPersons = new UniquePersonList(persons);
    }

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        allPersons.add(toAdd);
    }

    /**
     * Edit a person in the address book.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public void editPerson(ReadOnlyPerson toRemove, Person toAdd) throws PersonNotFoundException{//, DuplicatePersonException {
        allPersons.edit(toRemove, toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    /**
     * Clears all persons from the address book.
     */
    public void clear() {
        allPersons.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }

    public CommandStack getCommandStack() { return commandStack;}

    public void linkTwoPerson(ReadOnlyPerson target, ReadOnlyPerson target2) throws Associated.DuplicateAssociationException, Associated.SameTitleException {
        Person targetObject = target.getPerson();
        Person targetObject2 = target2.getPerson();
        targetObject.addAnAssociate(target2);
        targetObject2.addAnAssociate(target);
    }

    public void unlinkTwoPerson(ReadOnlyPerson target, ReadOnlyPerson target2) throws Exception{
        Person targetObject = target.getPerson();
        Person targetObject2 = target2.getPerson();
        targetObject.removeAnAssociate(target2);
        targetObject2.removeAnAssociate(target);
    }
}
