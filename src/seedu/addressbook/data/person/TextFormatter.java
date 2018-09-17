package seedu.addressbook.data.person;
/**
 * This is the class in charge of formatting texts passed by TextUI
 * */
public class TextFormatter {
    /**
     * Returns a concatenated version of the printable strings of each object.
     */
    public String getPrintableString(boolean showPrivate, Printable... printables) {
        String stringChain = "";
        for (Printable i: printables) {
            stringChain += i.getPrintableString(showPrivate) + " ";
        }
        return stringChain;
    }
}
