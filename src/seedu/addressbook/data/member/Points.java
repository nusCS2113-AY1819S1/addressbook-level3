package seedu.addressbook.data.member;

import java.awt.*;

public class Points {
    private String points;

    public final String value;
    public Points(){
        this.points = "0";
        this.value = this.points;
    };

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Points // instanceof handles nulls
                && this.value.equals(((Points) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

//    public boolean isPrivate() {
//        return isPrivate;
//    }
}
