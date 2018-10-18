package seedu.addressbook.data.order;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.person.Name;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an Order in the ordering list.
 */

public class Order implements ReadOnlyOrder {

    public static final String EMPTY_NAME_STRING = "gAksDZgOjsIPyVmMIuUE";
    public static final Member EMPTY_CUSTOMER = getNewEmptyCustomer();
    private static final Logger LOGGER = Logger.getLogger( Order.class.getName() );

    private Member customer;
    private Date date;
    private double price;

    /**
     * Map with Dishes as keys and quantities as Integer values.
     *
     * Use {@code entrySet()} to create a Set for iteration.
     */
    private final Map<Menu, Integer> dishItems = new HashMap<>();

    /**
     * Constructor used for drafting new order. Uses empty customer instead of null.
     */
    public Order() {
        this.customer = getNewEmptyCustomer();
        this.date = new Date();
        this.price = 0;
    }

    /**
     * Constructor for new order to be added to the order list.
     */
    public Order(Member customer, Map<Menu, Integer> dishItems) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice();
        this.date = new Date();
    }

    /**
     * Constructor for edited order to keep the original ordered date.
     */
    public Order(Member customer, Date date, Map<Menu, Integer> dishItems) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice();
        this.date = date;
    }

    /**
     * Full constructor.
     */
    public Order(Member customer, Date date, double price, Map<Menu, Integer> dishItems) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = price;
        this.date = date;
    }

    /**
     * Copy constructor.
     */
    public Order(ReadOnlyOrder source) {
        this(source.getCustomer(), source.getDate(), source.getPrice(), source.getDishItems());
    }

    @Override
    public Member getCustomer() {
        return new Member(customer);
    }

    /**
     * Defensively returning the copy of the order's date
     */
    @Override
    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Map<Menu, Integer> getDishItems() {
        return new HashMap<>(dishItems);
    }

    private static Member getNewEmptyCustomer() {
        try {
            return new Member(new Name(EMPTY_NAME_STRING));
        } catch (IllegalValueException ie) {
            LOGGER.log(Level.SEVERE,"Order.EMPTY_NAME_STRING is invalid", ie);
            return null;
        }
    }

    public void setCustomer(ReadOnlyMember customer) {
        this.customer = new Member(customer);
    }

    /**
     * Replaces the list of dish items with the dish items in {@code replacement}.
     */
    public void setDishItems(Map<Menu, Integer> replacement) {
        dishItems.clear();
        dishItems.putAll(replacement);
        price = calculatePrice();
    }

    /**
     * Calculate and return the total price of an order.
     */
    public double calculatePrice() {
        double result = 0;
        for (Map.Entry<Menu, Integer> m: getDishItems().entrySet()) {
            double dishPrice = Double.parseDouble(m.getKey().getPrice().value);
            int dishQuantity = m.getValue();
            result += (dishPrice * dishQuantity);
        }
        return result;
    }

    /**
     * Get the number of a certain dish item in an order.
     */
    public int getDishQuantity(Menu dish) {
        if (dishItems.containsKey(dish)) {
            return dishItems.get(dish);
        } else {
            return 0;
        }
    }

    /**
     * Change the quantity of a dish in an order.
     * Used to add, remove and edit dishes in an order.
     */
    public void changeDishQuantity(ReadOnlyMenus readOnlyDish, int quantity) {
        Menu dish = new Menu(readOnlyDish);
        if (quantity == 0) {
            dishItems.remove(dish);
        } else if (quantity > 0) {
            dishItems.put(dish, quantity);
        }
    }

    @Override
    public boolean hasCustomerField() {
        return !(customer.equals(EMPTY_CUSTOMER));
    }

    @Override
    public boolean hasDishItems() {
        return !(dishItems.isEmpty());
    }

    @Override
    public boolean hasAllRequiredField() {
        return hasCustomerField() && hasDishItems();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyOrder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyOrder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(customer, date, price, dishItems);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}
