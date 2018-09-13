package seedu.addressbook.data.person;

public class TextFormatter {
    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    String getPrintableString(boolean showPrivate, Printable... printables) {
        String stringChain = "";
        for (Printable i: printables){
            stringChain += i.getPrintableString(showPrivate) + " ";
        }
        return stringChain;
    }
}
