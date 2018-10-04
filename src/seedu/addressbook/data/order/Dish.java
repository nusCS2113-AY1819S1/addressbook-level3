package seedu.addressbook.data.order;

import java.util.Objects;

/**
 * Represents a Dish in the Order.
 */
public class Dish {
    private String dishName;
    private double dishPrice;

    public Dish(String dishName, double dishPrice) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public String getDishName() {
        return dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Dish // instanceof handles nulls
                && this.dishName.equals(((Dish) other).dishName)
                && (this.dishPrice == ((Dish) other).dishPrice)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName, dishPrice);
    }

}
