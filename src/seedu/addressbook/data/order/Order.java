package seedu.addressbook.data.order;

import seedu.addressbook.data.person.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an Order in the ordering list.
 */

public class Order implements ReadOnlyOrder {

    private Person customer;
    private Date date;
    private int price;
    private final Map<Dish, Integer> dishes = new HashMap();

    /**
     * Constructor for new order.
     */
    public Order(Person customer, Map<Dish, Integer> dishes) {
        this.customer = customer;
        this.dishes.putAll(dishes);
        this.price = calculatePrice(dishes);
        this.date = new Date();
    }

    /**
     * Constructor for edited order to keep the original ordered date.
     */
    public Order(Person customer, Map<Dish, Integer> dishes, Date date) {
        this.customer = customer;
        this.dishes.putAll(dishes);
        this.price = calculatePrice(dishes);
        this.date = date;
    }

    /**
     * Copy constructor.
     */
    public Order(ReadOnlyOrder source) {
        this(source.getCustomer(), source.getDishes(), source.getDate());
    }

    @Override
    public Person getCustomer() {
        return customer;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Map<Dish, Integer> getDishes() {
        return new HashMap<>(dishes);
    }

    /**
     * Replaces the list of dishes with the dishes in {@code replacement}.
     */
    public void setDishes(Map<Dish, Integer> replacement) {
        dishes.clear();
        dishes.putAll(replacement);
        price = calculatePrice(dishes);
    }

    public int calculatePrice(Map<Dish, Integer> dishes) {
        int result = 0;
        for (Map.Entry<Dish, Integer> m: getDishes().entrySet()) {
            int dishPrice = m.getKey().getDishPrice();
            int dishQuantity = m.getValue();
            result += (dishPrice * dishQuantity);
        }
        return result;
    }

    public void changeDishQuantity(Dish dish, int quantity) {
        if (dishes.containsKey(dish)) {
            dishes.remove(dish);
        }
        dishes.put(dish, quantity);
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
        return Objects.hash(customer, date, price, dishes);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}
