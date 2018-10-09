package seedu.addressbook.data.person.curriculum;

import java.util.TreeMap;
import static seedu.addressbook.common.StringUtils.UTILITY_EMPTY;

public class Curriculum {
    public static final String EXAMPLE = "CS2113T";
    public static final String MESSAGE_CURRICULUM_CONSTRAINTS = "EnrolledClass' names should be 2 alphabets, " +
                                                                "followed by 4 numbers";
    public static final String CURRICULUM_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String value;
    private TreeMap curriculum;

    public Curriculum() {
        this.curriculum = new TreeMap<String, EnrolledClass>();
        value = "empty";
    }

    public void addEnrolledClass(EnrolledClass enrolledClass){
        this.curriculum.put(enrolledClass.getClassName(), enrolledClass);
        update_value();
    }

    public void update_value(){
        String temp = UTILITY_EMPTY;
        for(Object key: curriculum.keySet()){
            if (temp == UTILITY_EMPTY){
                temp = ((EnrolledClass)curriculum.get(key)).getClassName();
            } else {
                temp += " ";
                temp += ((EnrolledClass) curriculum.get(key)).getClassName();
            }
        }
        this.value = temp;
    }
    @Override
    public String toString() { return value;}

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Curriculum // instanceof handles nulls
                && this.value.equals(((Curriculum) other).value)); // state check
    }

}