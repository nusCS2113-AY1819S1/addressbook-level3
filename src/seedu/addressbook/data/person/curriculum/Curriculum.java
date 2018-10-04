package seedu.addressbook.data.person.curriculum;

import java.util.TreeMap;

public class Curriculum {
    public static final String EXAMPLE = "CS2113T";
    public static final String MESSAGE_CURRICULUM_CONSTRAINTS = "EnrolledClass' names should be 2 alphabets, " +
                                                                "followed by 4 numbers";
    public static final String CURRICULUM_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String value;
    private TreeMap curriculum;

    public Curriculum() {
        this.curriculum = new TreeMap<String, EnrolledClass>();
        value = null;
    }

    public void addEnrolledClass(EnrolledClass enrolledClass){
        this.curriculum.put(enrolledClass.getClassName(), enrolledClass);
        recalculate(value);
    }

    public void recalculate(String value){
        String temp = null;
        for(Object key: curriculum.keySet()){
            if (temp == null){
                temp =  ((EnrolledClass)curriculum.get(key)).getClassName();
            } else {
                temp += " ";
                temp += ((EnrolledClass) curriculum.get(key)).getClassName();
            }
        }
        this.value = temp;
    }
    @Override
    public String toString() { return value;}

}