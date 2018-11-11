package seedu.addressbook.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void isAnyNull() {
        // empty list
        assertFalse(Utils.isAnyNull());

        // Any non-empty list
        assertFalse(Utils.isAnyNull(new Object(), new Object()));
        assertFalse(Utils.isAnyNull("test"));
        assertFalse(Utils.isAnyNull(""));

        // non empty list with just one null at the beginning
        assertTrue(Utils.isAnyNull((Object) null));
        assertTrue(Utils.isAnyNull(null, "", new Object()));
        assertTrue(Utils.isAnyNull(null, new Object(), new Object()));

        // non empty list with nulls in the middle
        assertTrue(Utils.isAnyNull(new Object(), null, null, "test"));
        assertTrue(Utils.isAnyNull("", null, new Object()));

        // non empty list with one null as the last element
        assertTrue(Utils.isAnyNull("", new Object(), null));
        assertTrue(Utils.isAnyNull(new Object(), new Object(), null));

        // confirms nulls inside the list are not considered
        List<Object> nullList = Arrays.asList((Object) null);
        assertFalse(Utils.isAnyNull(nullList));
    }

    @Test
    public void elementsAreUnique() throws Exception {
        // empty list
        assertAreUnique();

        // only one object
        assertAreUnique((Object) null);
        assertAreUnique(1);
        assertAreUnique("");
        assertAreUnique("abc");

        // all objects unique
        assertAreUnique("abc", "ab", "a");
        assertAreUnique(1, 2);

        // some identical objects
        assertNotUnique("abc", "abc");
        assertNotUnique("abc", "", "abc", "ABC");
        assertNotUnique("", "abc", "a", "abc");
        assertNotUnique(1, Integer.valueOf(1));
        assertNotUnique(null, 1, Integer.valueOf(1));
        assertNotUnique(null, null);
        assertNotUnique(null, "a", "b", null);
    }

    @Test
    public void sortByValue() throws Exception {
        Map<String, Integer> input = new HashMap<>();
        input.put("3", 5);
        input.put("6", 12);
        input.put("8", 51);
        input.put("7", 32);
        input.put("1", 2);
        input.put("5", 8);
        input.put("2", 3);
        input.put("4", 6);
        List<Map.Entry<String, Integer>> expected = new ArrayList<>();
        expected.add(new AbstractMap.SimpleEntry<>("1", 2));
        expected.add(new AbstractMap.SimpleEntry<>("2", 3));
        expected.add(new AbstractMap.SimpleEntry<>("3", 5));
        expected.add(new AbstractMap.SimpleEntry<>("4", 6));
        expected.add(new AbstractMap.SimpleEntry<>("5", 8));
        expected.add(new AbstractMap.SimpleEntry<>("6", 12));
        expected.add(new AbstractMap.SimpleEntry<>("7", 32));
        expected.add(new AbstractMap.SimpleEntry<>("8", 51));
        assertEquals(expected, Utils.sortByValue(input));
    }

    @Test
    public void formatCurrency() throws Exception {
        double input = 9999.2;
        String expected = "9999.20";
        assertEquals(expected, Utils.formatCurrency((input)));

        input = 0.5;
        expected = "0.50";
        assertEquals(expected, Utils.formatCurrency((input)));

        input = 1;
        expected = "1.00";
        assertEquals(expected, Utils.formatCurrency((input)));
    }

    private void assertAreUnique(Object... objects) {
        assertTrue(Utils.elementsAreUnique(Arrays.asList(objects)));
    }

    private void assertNotUnique(Object... objects) {
        assertFalse(Utils.elementsAreUnique(Arrays.asList(objects)));
    }
}
