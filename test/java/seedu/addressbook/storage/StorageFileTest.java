package seedu.addressbook.storage;

import static org.junit.Assert.assertEquals;
import static seedu.addressbook.util.TestUtil.assertTextFilesEqual;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.storage.Storage.StorageOperationException;

public class StorageFileTest {
    private static final String TEST_DATA_FOLDER = "test/data/StorageFileTest";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructor_nullFilePath_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        new StorageFile(null, TEST_DATA_FOLDER + "/" + "examBook.txt");
    }

    @Test
    public void constructor_nullExamFilePath_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        new StorageFile(TEST_DATA_FOLDER + "/" + "addressBook.txt", null);
    }

    @Test
    public void constructor_nullBothFilePath_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        new StorageFile(null, null);
    }

    @Test
    public void constructor_defaultPath() throws Exception {
        new StorageFile();
    }

    @Test
    public void constructor_noTxtAddressBookExtension_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new StorageFile(TEST_DATA_FOLDER + "/" + "InvalidfileName", TEST_DATA_FOLDER + "/" + "exams.txt");
    }

    @Test
    public void constructor_noTxtExamBookExtension_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new StorageFile(TEST_DATA_FOLDER + "/" + "addressbook.txt", TEST_DATA_FOLDER + "/" + "InvalidExamfileName");
    }

    @Test
    public void load_invalidFormat_exceptionThrown() throws Exception {
        // The file contains valid xml data, but does not match the AddressBook class
        StorageFile storage = getStorage("InvalidData.txt", "ValidExamData.txt");
        thrown.expect(StorageOperationException.class);
        storage.load();
    }

    @Test
    public void loadExam_invalidFormat_exceptionThrown() throws Exception {
        // The file contains valid xml data, but does not match the ExamBook class
        StorageFile storage = getStorage("ValidData.txt", "InvalidExamData.txt");
        thrown.expect(StorageOperationException.class);
        storage.loadExam();
    }

    @Test
    public void load_validFormat() throws Exception {
        AddressBook actual = getStorage("ValidData.txt", "ValidExamData.txt").load();
        AddressBook expected = getTestAddressBook();

        // ensure loaded AddressBook is properly constructed with test data
        // TODO: overwrite equals method in AddressBook class and replace with equals method below
        assertEquals(actual.getAllPersons(), expected.getAllPersons());
    }

    @Test
    public void loadExam_validFormat() throws Exception {
        ExamBook actual = getStorage("ValidData.txt", "ValidExamData.txt").loadExam();
        ExamBook expected = getTestExamBook();

        // ensure loaded AddressBook is properly constructed with test data
        // TODO: overwrite equals method in ExamBook class and replace with equals method below
        assertEquals(actual.getAllExam(), expected.getAllExam());
    }

    @Test
    public void save_nullAddressBook_exceptionThrown() throws Exception {
        StorageFile storage = getTempStorage();
        thrown.expect(NullPointerException.class);
        storage.save(null);
    }

    @Test
    public void save_nullExamBook_exceptionThrown() throws Exception {
        StorageFile storage = getTempStorage();
        thrown.expect(NullPointerException.class);
        storage.saveExam(null);
    }

    @Test
    public void save_validAddressBook() throws Exception {
        ExamBook eb = getTestExamBook();
        AddressBook ab = getTestAddressBook();
        StorageFile storage = getTempStorage();
        storage.saveExam(eb);
        storage.save(ab);
        assertStorageFilesEqual(storage, getStorage("ValidData.txt", "ValidExamData.txt"));
    }

    @Test
    public void save_validExamBook() throws Exception {
        ExamBook eb = getTestExamBook();
        AddressBook ab = getTestAddressBook();
        StorageFile storage = getTempStorage();
        storage.saveExam(eb);
        storage.save(ab);
        assertExamsFilesEqual(storage, getStorage("ValidData.txt", "ValidExamData.txt"));
    }

    // getPath() method in StorageFile class is trivial so it is not tested
    // getPathExam() method in StorageFile class is trivial so it is not tested

    /**
     * Asserts that the contents of two storage files are the same.
     */
    private void assertStorageFilesEqual(StorageFile sf1, StorageFile sf2) throws Exception {
        assertTextFilesEqual(Paths.get(sf1.getPath()), Paths.get(sf2.getPath()));
    }

    /**
     * Asserts that the contents of two exams files are the same.
     */
    private void assertExamsFilesEqual(StorageFile sf1, StorageFile sf2) throws Exception {
        assertTextFilesEqual(Paths.get(sf1.getPathExam()), Paths.get(sf2.getPathExam()));
    }

    private StorageFile getStorage(String fileName, String examFileName) throws Exception {
        return new StorageFile(TEST_DATA_FOLDER + "/" + fileName, TEST_DATA_FOLDER + "/" + examFileName);
    }

    private StorageFile getTempStorage() throws Exception {
        String tempExam = testFolder.getRoot().getPath() + "/" + "tempExam.txt";
        return new StorageFile(testFolder.getRoot().getPath() + "/" + "temp.txt", tempExam);
    }

    private AddressBook getTestAddressBook() throws Exception {
        AddressBook ab = new AddressBook();
        ab.addPerson(new Person(new Name("John Doe"),
                new Phone("98765432", false),
                new Email("johnd@gmail.com", false),
                new Address("John street, block 123, #01-01", false),
                Collections.emptySet()));
        ab.addPerson(new Person(new Name("Betsy Crowe"),
                new Phone("1234567", true),
                new Email("betsycrowe@gmail.com", false),
                new Address("Newgate Prison", true),
                new HashSet<>(Arrays.asList(new Tag("friend"), new Tag("criminal")))));
        return ab;
    }

    private ExamBook getTestExamBook() throws Exception {
        ExamBook eb = new ExamBook();
        eb.addExam(new Exam("Mathematics", "Math Midterms", "01/12/2018", "09:00", "10:00", "Held in MPSH", false));
        eb.addExam(new Exam("English", "English Midterms", "02/12/2018", "09:00", "10:00", "Held in MPSH", false));
        return eb;
    }

}
