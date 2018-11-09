package seedu.addressbook.storage;

import static org.junit.Assert.assertEquals;
import static seedu.addressbook.util.TestUtil.assertTextFilesEqual;

import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.EmployeeAddress;
import seedu.addressbook.data.employee.EmployeeEmail;
import seedu.addressbook.data.employee.EmployeeName;
import seedu.addressbook.data.employee.EmployeePhone;
import seedu.addressbook.data.employee.EmployeePosition;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberEmail;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.MemberTier;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.storage.StorageFile.StorageOperationException;

public class StorageFileTest {
    private static final String TEST_DATA_FOLDER = "test/data/StorageFileTest";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructor_nullFilePath_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        new StorageFile(null);
    }

    @Test
    public void constructor_noTxtExtension_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new StorageFile(TEST_DATA_FOLDER + "/" + "InvalidfileName");
    }

    @Test
    public void load_invalidFormat_exceptionThrown() throws Exception {
        // The file contains valid xml data, but does not match the Rms class
        StorageFile storage = getStorage("InvalidData.txt");
        thrown.expect(StorageOperationException.class);
        storage.load();
    }

    @Test
    public void load_validFormat() throws Exception {
        Rms actualRms = getStorage("ValidData.txt").load();
        Rms expectedRms = getTestRms();

        // ensure loaded Rms is properly constructed with test data
        // overwrite equals method in Rms class and replace with equals method below
        assertEquals(actualRms.getAllAttendance(), expectedRms.getAllAttendance());
        assertEquals(actualRms.getAllEmployees(), expectedRms.getAllEmployees());
        assertEquals(actualRms.getAllMembers(), expectedRms.getAllMembers());
        assertEquals(actualRms.getAllMenus(), expectedRms.getAllMenus());
        assertEquals(actualRms.getAllOrders(), expectedRms.getAllOrders());

    }

    @Test
    public void save_nullRms_exceptionThrown() throws Exception {
        StorageFile storage = getTempStorage();
        thrown.expect(NullPointerException.class);
        storage.save(null);
    }

    @Test
    public void save_validRms() throws Exception {
        Rms rms = getTestRms();
        StorageFile storage = getTempStorage();
        storage.save(rms);

        assertStorageFilesEqual(storage, getStorage("ValidData.txt"));
    }

    // getPath() method in StorageFile class is trivial so it is not tested

    /**
     * Asserts that the contents of two storage files are the same.
     */
    private void assertStorageFilesEqual(StorageFile sf1, StorageFile sf2) throws Exception {
        assertTextFilesEqual(Paths.get(sf1.getPath()), Paths.get(sf2.getPath()));
    }

    private StorageFile getStorage(String fileName) throws Exception {
        return new StorageFile(TEST_DATA_FOLDER + "/" + fileName);
    }

    private StorageFile getTempStorage() throws Exception {
        return new StorageFile(testFolder.getRoot().getPath() + "/" + "temp.txt");
    }

    private Rms getTestRms() throws Exception {
        Rms rms = new Rms();
        generateEmployeeAndAttendanceList(rms);
        generateMenuAndMemberAndOrderList(rms);
        return rms;
    }

    /**
     * Add a list of employee, attendance to the specified rms for testing
     */
    private void generateEmployeeAndAttendanceList(Rms rms) throws Exception {
        String emp1Name = "Tay";
        String emp2Name = "Lim";
        Employee emp1 = new Employee(
                new EmployeeName(emp1Name),
                new EmployeePhone("11111111"),
                new EmployeeEmail("11111111@gmail.com"),
                new EmployeeAddress("11111111 Street"),
                new EmployeePosition("Cashier"));
        Employee emp2 = new Employee(
                new EmployeeName(emp2Name),
                new EmployeePhone("22222222"),
                new EmployeeEmail("22222222@gmail.com"),
                new EmployeeAddress("22222222 Street"),
                new EmployeePosition("Cashier"));

        rms.addEmployee(emp1);
        rms.addEmployee(emp2);

        Set<Timing> timings = new LinkedHashSet<>();
        timings.add(new Timing("00:00", "11/08/2018", true));
        Attendance atd1 = new Attendance(emp1Name, true, timings);
        Attendance atd2 = new Attendance(emp2Name, true, timings);

        rms.addAttendance(atd1);
        rms.addAttendance(atd2);
    }

    /**
     * Add a list of menus, members, orders to the specified rms for testing
     */
    private void generateMenuAndMemberAndOrderList(Rms rms) throws Exception {
        Set<Tag> foodTags = new HashSet<>();
        foodTags.add(new Tag("best"));
        Menu menu1 = new Menu(
                new MenuName("Burger"),
                new Price("$5.00"),
                new Type("main"),
                foodTags);
        Menu menu2 = new Menu(
                new MenuName("Fries"),
                new Price("$2.00"),
                new Type("sides"),
                foodTags);

        rms.addMenu(menu1);
        rms.addMenu(menu2);

        Member member1 = new Member(
                new MemberName("Ang"),
                new MemberEmail("Ang@gmail.com"),
                new Points(),
                new Date(1000),
                new MemberTier("Bronze"));
        Member member2 = new Member(
                new MemberName("Salsabil"),
                new MemberEmail("Salsabil@gmail.com"),
                new Points(10000),
                new Date(1000),
                new MemberTier("Gold"));

        rms.addMember(member1);
        rms.addMember(member2);

        Map<ReadOnlyMenus, Integer> foodItems = new HashMap<>();
        foodItems.put(menu1, 1);
        foodItems.put(menu2, 2);

        Order order1 = new Order(member1, new Date(1000), 9.0, foodItems, 0);
        Order order2 = new Order(member2, new Date(1000), 8.0, foodItems, 100);

        rms.addOrder(order1);
        rms.addOrder(order2);
    }

}
