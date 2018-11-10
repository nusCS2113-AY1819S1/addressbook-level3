package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.addressbook.data.Rms;
import seedu.addressbook.data.employee.Attendance;
import seedu.addressbook.data.employee.Employee;
import seedu.addressbook.data.employee.UniqueAttendanceList;
import seedu.addressbook.data.employee.UniqueEmployeeList;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.UniqueMemberList;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.UniqueMenuList;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.UniqueOrderList;

/**
 * JAXB-friendly adapted Rms data holder class.
 */
@XmlRootElement(name = "Rms")
public class AdaptedRms {

    @XmlElement(name = "menus")
    private List<AdaptedMenu> menus = new ArrayList<>();
    @XmlElement(name = "members")
    private List<AdaptedMember> members = new ArrayList<>();
    @XmlElement(name = "employees")
    private List<AdaptedEmployee> employees = new ArrayList<>();
    @XmlElement(name = "orders")
    private List<AdaptedOrder> orders = new ArrayList<>();
    @XmlElement(name = "attendance")
    private List<AdaptedAttendance> attendances = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedRms() {}

    public AdaptedRms(Rms source) {
        source.getAllMenus().forEach(menu -> menus.add(new AdaptedMenu(menu)));
        source.getAllEmployees().forEach(employee -> employees.add(new AdaptedEmployee(employee)));
        source.getAllMembers().forEach(member -> members.add(new AdaptedMember(member)));
        source.getAllOrders().forEach(order -> orders.add(new AdaptedOrder(order)));
        source.getAllAttendance().forEach(attendance -> attendances.add(new AdaptedAttendance(attendance)));
    }


    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        return (menus.stream().anyMatch(AdaptedMenu::isAnyRequiredFieldMissing)
                || members.stream().anyMatch(AdaptedMember::isAnyRequiredFieldMissing)
                || employees.stream().anyMatch(AdaptedEmployee::isAnyRequiredFieldMissing)
                || orders.stream().anyMatch(AdaptedOrder::isAnyRequiredFieldMissing)
                || attendances.stream().anyMatch(AdaptedAttendance::isAnyRequiredFieldMissing));
    }

    /**
     * Converts this jaxb-friendly {@code AdaptedRms} object into the corresponding(@code Rms} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Rms toModelType() throws IllegalValueException {
        final List<Menu> menuList = new ArrayList<>();
        final List<Employee> employeeList = new ArrayList<>();
        final List<Member> memberList = new ArrayList<>();
        final List<Order> orderList = new ArrayList<>();
        final List<Attendance> attendanceList = new ArrayList<>();

        for (AdaptedEmployee employee : employees) {
            employeeList.add(employee.toModelType());
        }

        for (AdaptedMenu menu : menus) {
            menuList.add(menu.toModelType());
        }

        for (AdaptedMember member : members) {
            memberList.add(member.toModelType());
        }

        for (AdaptedOrder order : orders) {
            orderList.add(order.toModelType(memberList));
        }

        for (AdaptedAttendance attendance : attendances) {
            attendanceList.add(attendance.toModelType());
        }

        return new Rms(
                new UniqueMenuList(menuList),
                new UniqueEmployeeList(employeeList),
                new UniqueOrderList(orderList),
                new UniqueMemberList(memberList),
                new UniqueAttendanceList(attendanceList)
        );
    }
}
