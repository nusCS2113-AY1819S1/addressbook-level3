package seedu.addressbook.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * Utility methods
 */
public class Utils {

    /**
     * Checks whether any of the given items are null.
     */
    public static boolean isAnyNull(Object... items) {
        for (Object item : items) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Checks if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sort a map by the values
     */
    public static <K, V extends Comparable<? super V>> List<Map.Entry <K, V>> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        return list;
    }

    /**
     * Format a double into a currency String
     */
    public static String formatCurrency(double input) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(input);
    }

    /**
     * Create blank space to position the next String at an exact distance compared to the start of the prefix String.
     * @param prefix the String after which the blank space is inserted.
     * @param distance the distance between the start of the two String that the blank space between them aims to fill.
     * @return the filler space required
     */
    public static String blankSpace(String prefix, int distance) {
        String fillingSpace;
        int fillingSpaceLength = distance - prefix.length();
        if (fillingSpaceLength > 0) {
            fillingSpace = "";
            for (int i = 0; i < fillingSpaceLength; i++) {
                fillingSpace += " ";
            }
        } else {
            fillingSpace = "\t";
        }
        return fillingSpace;
    }

    public static String blankSpace(int distance) {
        return blankSpace("", distance);
    }

}
