package seedu.addressbook.data.order;

import seedu.addressbook.data.member.Member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an Order in the ordering list.
 */

public class Order implements ReadOnlyOrder {

    private Member customer;
    private Date date;
    private double price;

    /**
     * Map with Dishes as keys and quantities as Integer values.
     *
     * Use {@code entrySet()} to create a Set for iteration.
     */
    private final Map<Dish, Integer> dishItems = new HashMap<>();

    /**
     * Constructor for new order.
     */
    public Order(Member customer, Map<Dish, Integer> dishItems) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice();
        this.date = new Date();
    }

    /**
     * Constructor for edited order to keep the original ordered date.
     */
    public Order(Member customer, Date date, Map<Dish, Integer> dishItems) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice();
        this.date = date;
    }

    /**
     * Full constructor.
     */
    public Order(Member customer, Date date, double price, Map<Dish, Integer> dishItems) {
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
        return customer;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public Map<Dish, Integer> getDishItems() {
        return new HashMap<>(dishItems);
    }

    /**
     * Replaces the list of dish items with the dish items in {@code replacement}.
     */
    public void setDishItems(Map<Dish, Integer> replacement) {
        dishItems.clear();
        dishItems.putAll(replacement);
        price = calculatePrice();
    }

    public double calculatePrice() {
        double result = 0;
        for (Map.Entry<Dish, Integer> m: getDishItems().entrySet()) {
            double dishPrice = m.getKey().getDishPrice();
            int dishQuantity = m.getValue();
            result += (dishPrice * dishQuantity);
        }
        return result;
    }

    public int getDishQuantity(Dish dish) {
        if (dishItems.containsKey(dish)) {
            return dishItems.get(dish);
        } else {
            return 0;
        }

    }

    public void changeDishQuantity(Dish dish, int quantity) {
        dishItems.remove(dish);
        dishItems.put(dish, quantity);
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
