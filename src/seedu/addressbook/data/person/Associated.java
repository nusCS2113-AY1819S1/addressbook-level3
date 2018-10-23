package seedu.addressbook.data.person;

import java.util.ArrayList;

public class Associated {
    private ArrayList<ReadOnlyPerson> associates;

    Associated(){
        this.associates = new ArrayList<>();
    }

    public static class NoAssociatesException extends Exception {}

    public static class DuplicateAssociationException extends Exception {}

    public void addToAssociated (ReadOnlyPerson toAdd) throws DuplicateAssociationException{
        if(associates.contains(toAdd)) throw new DuplicateAssociationException();
        this.associates.add(toAdd);
    }

    public String getAssociates() throws NoAssociatesException {
        if(associates.isEmpty()) throw new NoAssociatesException();
        return associatesToString();
    }

    public String associatesToString() {
        final StringBuilder builder = new StringBuilder();
        ReadOnlyPerson temp = associates.get(0);
        builder.append("Associated ");
        builder.append(temp.getTitle() + "s \n");
        for (ReadOnlyPerson associatedPerson :associates) {
            builder.append(associatedPerson.getName());
            builder.append("\n");
        }
        return builder.toString();
    }
}
