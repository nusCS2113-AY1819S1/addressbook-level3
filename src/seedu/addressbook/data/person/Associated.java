package seedu.addressbook.data.person;

/**
 * Represents a Person's associate in the address book.
 */
public class Associated {
    public final String value;

//    private ArrayList<ReadOnlyPerson> associates;
//
//    Associated(){
//        this.associates = new ArrayList<>();
//    }
//
//    public static class NoAssociatesException extends Exception {}

    public static class DuplicateAssociationException extends Exception {}

    public static class SameTitleException extends Exception {}

    public static class NoAssociationException extends Exception {}
//
//    public void addToAssociated (ReadOnlyPerson toAdd) throws DuplicateAssociationException{
//        if(associates.contains(toAdd)) throw new DuplicateAssociationException();
//        this.associates.add(toAdd);
//    }
//
//    public String getAssociates() throws NoAssociatesException {
//        if(associates.isEmpty()) throw new NoAssociatesException();
//        return associatesToString();
//    }
//
//    public String associatesToString() {
//        final StringBuilder builder = new StringBuilder();
//        ReadOnlyPerson temp = associates.get(0);
//        builder.append(temp.getTitle() + "s \n");
//        for (ReadOnlyPerson associatedPerson :associates) {
//            builder.append(associatedPerson.getName());
//            builder.append("\n");
//        }
//        return builder.toString();
//    }
    /**
     * Validates given tag name.
     *
     * @throws SameTitleException if the person to add has the same title as the target.
     */
    public Associated(String toAddName, Title toAddTitle, Title ownTitle) throws SameTitleException{
        if(toAddTitle.equals(ownTitle)) throw new SameTitleException();
        this.value = toAddName;
    }

    /**
     * Constructor for AdaptedAssociate
     */
    public Associated(String value){
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Associated // instanceof handles nulls
                && this.value.equals(((Associated) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
