package seedu.addressbook.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.EmployeeAddress;
import seedu.addressbook.data.employee.EmployeeEmail;
import seedu.addressbook.data.employee.EmployeeName;
import seedu.addressbook.data.employee.EmployeePhone;
import seedu.addressbook.data.employee.EmployeePosition;
import seedu.addressbook.data.employee.Timing;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.MemberEmail;
import seedu.addressbook.data.member.MemberName;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.tag.Tag;

/**
 * A utility class to generate test data.
 */
class TestDataHelper {

    public static final int FOOD_QUANTITY = 1;

    /**
     * Generate an employee for testing purpose
     */
    public Employee peter() throws Exception {
        EmployeeName name = new EmployeeName("Peter Lee");
        EmployeePhone phone = new EmployeePhone("91234567");
        EmployeeEmail email = new EmployeeEmail("PeterLee89@rms.com");
        EmployeeAddress address = new EmployeeAddress("Clementi Ave 2, Blk 543 #13-12");
        EmployeePosition position = new EmployeePosition("Cashier");
        return new Employee(name, phone, email, address, position);
    }

    /**
     * Generate a member for testing purpose
     */
    public Member eve() throws Exception {
        MemberName name = new MemberName("Eve");
        MemberEmail email = new MemberEmail("eve@gmail.com");
        return new Member(name, email);
    }

    /**
     * Generate a menu item for testing purpose
     */
    public Menu burger() throws Exception {
        MenuName name = new MenuName("Cheese Burger");
        Price price = new Price("$5.00");
        Type type = new Type("main");
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
        return new Menu(name, price, type, tags);
    }

    /**
     * Generate a map of dish items for testing purpose
     */
    public Map<ReadOnlyMenus, Integer> foodItems() throws Exception {
        Map<ReadOnlyMenus, Integer> foods = new HashMap<>();
        foods.put(burger(), FOOD_QUANTITY);
        return foods;
    }

    /**
     * Generate empty points to redeem for testing purpose
     */
    public int pointsToRedeem() {
        return new Points().getCurrentPoints();
    }

    /**
     * Generate an order for testing purpose
     */
    public Order foodOrder() throws Exception {
        long orderingTime = 1000;
        Date orderingDate = new Date(orderingTime);
        return new Order(eve(), orderingDate, foodItems(), pointsToRedeem());
    }

    /**
     * Generate an order without customer field for testing purpose
     */
    public Order foodOrderWithoutCustomer() throws Exception {
        long orderingTime = 1000;
        Date orderingDate = new Date(orderingTime);
        return new Order(new Member(), orderingDate, foodItems(), pointsToRedeem());
    }

    /**
     * Generate an order without dishes for testing purpose
     */
    public Order foodOrderWithoutDishes() throws Exception {
        long orderingTime = 1000;
        Date orderingDate = new Date(orderingTime);
        return new Order(eve(), orderingDate, new HashMap<>(), pointsToRedeem());
    }

    /**
     * Generates a valid employee using the given seed.
     * Running this function with the same parameter values guarantees the returned employee will have the same state.
     * Each unique seed will generate a unique Employee object.
     *
     * @param seed used to generate the employee data field values
     */
    public Employee generateEmployee(int seed) throws Exception {
        return new Employee(
                new EmployeeName("Employee " + seed),
                new EmployeePhone("" + Math.abs(seed)),
                new EmployeeEmail(seed + "@email"),
                new EmployeeAddress("House of " + seed),
                new EmployeePosition("Position " + seed)
        );
    }

    /** Generates a new employee based on the detail given */
    public Employee generateEditEmployee(Employee e, String editParam, String editDetail) throws Exception {
        EmployeeName name = e.getName();
        EmployeePhone phone;
        EmployeeEmail email;
        EmployeeAddress address;
        EmployeePosition position;

        if ("phone".equals(editParam)) {
            phone = new EmployeePhone(editDetail);
        } else {
            phone = e.getPhone();
        }

        if ("email".equals(editParam)) {
            email = new EmployeeEmail(editDetail);
        } else {
            email = e.getEmail();
        }

        if ("address".equals(editParam)) {
            address = new EmployeeAddress(editDetail);
        } else {
            address = e.getAddress();
        }

        if ("position".equals(editParam)) {
            position = new EmployeePosition(editDetail);
        } else {
            position = e.getPosition();
        }

        return new Employee(name, phone, email, address, position);
    }

    /**
     * Generates a valid attendance using the given seed.
     * Running this function with the same parameter values guarantees the returned attendance will have the same state.
     * Each unique seed will generate a unique Attendance object.
     *
     * @param seed used to generate the attendance data field values
     */
    public Attendance generateAttendance(int seed) {
        return new Attendance("Employee " + seed);
    }

    /**
     * Generates a valid attendance using the given seed.
     * Running this function with the same parameter values guarantees the returned attendance will have the same state.
     * Each unique seed will generate a unique Attendance object.
     *
     * @param seed used to generate the attendance data field values
     */
    public Attendance generateAttendanceWithTime(int seed, boolean isClockedIn, Set<Timing> timings) {
        return new Attendance("Employee " + seed, isClockedIn, timings);
    }

    /**
     * Generates a valid member using the given seed.
     * Running this function with the same parameter values guarantees the returned employee will have the same state.
     * Each unique seed will generate a unique Member object.
     *
     * @param seed used to generate the employee data field values
     */
    public Member generateMember(int seed) throws Exception {
        return new Member(
                new MemberName("Member " + seed),
                new MemberEmail(seed + "@email")
        );
    }

    /**
     * Generates a valid menu item using the given seed.
     * Running this function with the same parameter values guarantees the returned menu item will have the same state.
     * Each unique seed will generate a unique Menu object.
     *
     * @param seed used to generate the menu item data field values
     */
    public Menu generateMenuItem(int seed) throws Exception {
        return new Menu(
                new MenuName("Menu " + seed),
                new Price("$" + Math.abs(seed)),
                new Type(("main")),
                new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))))
        );
    }

    public Map<ReadOnlyMenus, Integer> generateDishItems(int seed) throws Exception {
        Map<ReadOnlyMenus, Integer> dishItems = new HashMap<>();
        dishItems.put(generateMenuItem(seed), Math.abs(seed));
        return dishItems;
    }

    /**
     * Generates a valid order using the given seed.
     * Running this function with the same parameter values guarantees the returned menu item will have the same state.
     * Each unique seed will generate a unique Order object.
     *
     * @param seed used to generate the menu item data field values
     */
    public Order generateOrder(int seed) throws Exception {
        return new Order(
                generateMember(seed),
                new Date(Math.abs(seed)),
                generateDishItems(seed),
                new Points().getCurrentPoints()
        );
    }

    /** Generates the correct add command based on the employee given */
    public String generateAddEmpCommand(Employee e) {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("addemp");

        cmd.add(e.getName().toString());
        cmd.add("p/" + e.getPhone().toString());
        cmd.add("e/" + e.getEmail().toString());
        cmd.add("a/" + e.getAddress().toString());
        cmd.add("pos/" + e.getPosition().toString());

        return cmd.toString();
    }

    /** Generates the correct edit command based on the employee given */
    public String generateEditEmpCommand(String index, String editParam, String editDetail) {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("editemp");

        cmd.add(index);

        if (editParam == null || editDetail == null) {
            return cmd.toString();
        } else {
            if (editParam.equals("phone")) {
                cmd.add("p/" + editDetail);
            }

            if (editParam.equals("email")) {
                cmd.add("e/" + editDetail);
            }

            if (editParam.equals("address")) {
                cmd.add("a/" + editDetail);
            }

            if (editParam.equals("position")) {
                cmd.add("pos/" + editDetail);
            }
        }

        return cmd.toString();
    }

    /** Generates the correct add member command based on the member given */
    public String generateAddMemberCommand(Member e) {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("addmember");

        cmd.add(e.getName().toString());
        cmd.add(("e/") + e.getEmail());

        return cmd.toString();
    }

    /** Generates the correct add menu command based on the menu item given */
    public String generateMenuAddCommand(Menu m) {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("addmenu");

        cmd.add(m.getName().toString());
        cmd.add(("p/") + m.getPrice());
        cmd.add(("type/") + m.getType());

        Set<Tag> tags = m.getTags();
        for (Tag t: tags) {
            cmd.add("t/" + t.tagName);
        }

        return cmd.toString();
    }

    /** Generates the correct edit draft dish command based on the given index number and quantity */
    public String generateDraftOrderEditDishCommand(int index, int quantity) {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("draftdish");

        cmd.add(Integer.toString(index));
        cmd.add("q/" + quantity);

        return cmd.toString();
    }

    /**
     * Generates an Rms based on the list of Employees given.
     */
    public Rms generateRmsEmployees(List<Employee> employees) throws Exception {
        Rms rms = new Rms();
        addEmployeesToRms(rms, employees);
        return rms;
    }

    /**
     * Generates an Rms based on the list of Employees and Attendances given.
     */
    public Rms generateRmsEmployeesAndAttendances(
            List<Employee> employees,
            List<Attendance> attendances) throws Exception {
        Rms rms = new Rms();
        addEmployeesToRms(rms, employees);
        addAttendancesToRms(rms, attendances);
        return rms;
    }

    /**
     * Generates an Rms based on the list of Menu given.
     */
    public Rms generateRmsMenu(List<Menu> menus) throws Exception {
        Rms rms = new Rms();
        addToRmsMenu(rms, menus);
        return rms;
    }

    /**
     * Generates an Rms based on the list of Member given.
     */
    public Rms generateRmsMember(List<Member> members) throws Exception {
        Rms rms = new Rms();
        addMembersToRms(rms, members);
        return rms;
    }

    /**
     * Generates an Rms based on the list of Member given.
     */
    public Rms generateRmsOrder(List<Order> orders) throws Exception {
        Rms rms = new Rms();
        addOrdersToRms(rms, orders);
        return rms;
    }

    public Rms generateRmsOrder(Integer... integers) throws Exception {
        Rms rms = new Rms();
        addOrdersToRms(rms, integers);
        return rms;
    }

    /**
     * Adds the given list of Menus to the given Rms
     */
    public void addToRmsMenu(Rms rms, List<Menu> menusToAdd) throws Exception {
        for (Menu m: menusToAdd) {
            rms.addMenu(m);
        }
    }

    /**
     * Adds the given list of Employeees to the given Rms.
     */
    public void addEmployeesToRms(Rms rms, List<Employee> employeesToAdd) throws Exception {
        for (Employee e: employeesToAdd) {
            rms.addEmployee(e);
        }
    }

    /**
     * Adds the given list of Employeees to the given Rms.
     */
    public void addAttendancesToRms(Rms rms, List<Attendance> attendancesToAdd) {
        for (Attendance a: attendancesToAdd) {
            rms.addAttendance(a);
        }
    }

    /**
     * Adds the given list of Members to the given Rms
     */
    public void addMembersToRms(Rms rms, List<Member> membersToAdd) throws Exception {
        for (Member member: membersToAdd) {
            rms.addMember(member);
        }
    }

    /**
     * Adds the given list of Orders to the given Rms
     */
    public void addOrdersToRms(Rms rms, List<Order> ordersToAdd) throws Exception {
        for (Order order: ordersToAdd) {
            rms.addOrder(order);
        }
    }

    /**
     * Adds auto-generated Order objects to the given Rms
     * @param rms The Rms to which the Orders will be added
     * @param integers the seeds used to create the Orders
     */
    public void addOrdersToRms(Rms rms, Integer... integers) throws Exception {
        addOrdersToRms(rms, generateOrderList(integers));
    }

    /**
     * Creates a list of Employees based on the give Employee objects.
     */
    public List<Employee> generateEmployeeList(Employee... employees) {
        List<Employee> employeeList = new ArrayList<>();
        Collections.addAll(employeeList, employees);
        return employeeList;
    }

    /**
     * Creates a list of Attendances based on the give Attendance objects.
     */
    public List<Attendance> generateAttendanceList(Attendance... attendances) {
        List<Attendance> attendanceList = new ArrayList<>();
        Collections.addAll(attendanceList, attendances);
        return attendanceList;
    }

    /**
     * Creates a list of Members based on the give Member objects.
     */
    public List<Member> generateMemberList(Member... members) {
        List<Member> memberList = new ArrayList<>();
        Collections.addAll(memberList, members);
        return memberList;
    }

    /**
     * Creates a list of Menu Items based on the give Menu objects.
     */
    public List<Menu> generateMenuList(Menu... menus) {
        List<Menu> menuList = new ArrayList<>();
        Collections.addAll(menuList, menus);
        return menuList;
    }

    /**
     * Creates a list of Orders based on the given Order objects.
     */
    public List<Order> generateOrderList(Order... orders) {
        List<Order> orderList = new ArrayList<>();
        Collections.addAll(orderList, orders);
        return orderList;
    }

    /**
     * Creates a list of Orders based on the given integers.
     */
    public List<Order> generateOrderList(Integer... integers) throws Exception {
        List<Order> orderList = new ArrayList<>();
        for (Integer n: integers) {
            orderList.add(generateOrder(n));
        }
        return orderList;
    }

    /**
     * Generates a Member object with given name. Other fields will have some dummy values.
     */
    public Member generateMemberWithName(String name) throws Exception {
        return new Member(
                new MemberName(name),
                new MemberEmail(name + "@email")
        );
    }

    /**
     * Generates a Menu object with given name. Other fields will have some dummy values.
     */
    public Menu generateMenuWithName(String name) throws Exception {
        return new Menu(
                new MenuName(name),
                new Price("$5.00"),
                new Type("main"),
                Collections.singleton(new Tag("tag"))
        );
    }

    /**
     * Generates a Menu object with given name. Other fields will have some dummy values.
     */
    public Menu generateMenuWithGivenNameAndType(String name, String type) throws Exception {
        return new Menu(
                new MenuName(name),
                new Price("$5.00"),
                new Type(type),
                Collections.singleton(new Tag("tag"))
        );
    }

    /** Generates the correct stats employee command */
    public String generateStatsEmpCommand() {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("statsemp");

        return cmd.toString();
    }

    /** Generates the correct stats member command */
    public String generateStatsMemberCommand() {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("statsmember");

        return cmd.toString();
    }

    /** Generates the correct stats menu command based on the to and from dates given */
    public String generateStatsMenuCommand(Date from, Date to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMYYYY");

        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("statsmenu");

        if (from != null) {
            cmd.add("f/" + dateFormat.format(from));
        }
        if (to != null) {
            cmd.add("t/" + dateFormat.format(to));
        }

        return cmd.toString();
    }

    /** Generates the correct stats order command */
    public String generateStatsOrderCommand() {
        StringJoiner cmd = new StringJoiner(" ");

        cmd.add("statsorder");

        return cmd.toString();
    }
}
