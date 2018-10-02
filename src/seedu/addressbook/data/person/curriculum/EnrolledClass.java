package seedu.addressbook.data.person.curriculum;

import java.util.TreeMap;

public class EnrolledClass {
    private String className;
    private TreeMap weeks;

    public EnrolledClass(String className) {
        this.className = className;
        this.weeks = new TreeMap<String, String>();
        for(int i = 0; i < 13; i++){
            weeks.put("Week_" + i, "datapath" + i);
        }
    }

    public String getClassName() {
        return className;
    }

}
