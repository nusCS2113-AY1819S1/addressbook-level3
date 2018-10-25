package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;

import javax.xml.bind.annotation.XmlElement;
import java.util.*;

public class AdaptedOrder {

    private static class AdaptedDishItem {
        @XmlElement
        public AdaptedMenu dish;
        @XmlElement
        public int quantity;
    }

    @XmlElement(required = true)
    private AdaptedMember customer;
    @XmlElement(required = true)
    private long date;
    @XmlElement(required = true)
    private double price;

    @XmlElement
    private List<AdaptedDishItem> dishItems = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedOrder() {}

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedOrder
     */
    public AdaptedOrder(ReadOnlyOrder source) {
        customer = new AdaptedMember(source.getCustomer());
        date = source.getDate().getTime();
        price = source.getPrice();

        dishItems = new ArrayList<>();
        for (Map.Entry<Menu, Integer> m: source.getDishItems().entrySet()) {
            AdaptedDishItem dishItem = new AdaptedDishItem();
            dishItem.dish = new AdaptedMenu(m.getKey());
            dishItem.quantity = m.getValue();
            dishItems.add(dishItem);
        }
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
        for (AdaptedDishItem dishItem : dishItems) {
            if (dishItem.dish.isAnyRequiredFieldMissing() || Utils.isAnyNull(dishItem.quantity)) {
                return true;
            }
        }
        return customer.isAnyRequiredFieldMissing() || Utils.isAnyNull(date, price);
    }

    /**
     * Converts this jaxb-friendly adapted order object into the Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Order toModelType() throws IllegalValueException {
        final Map<Menu, Integer> dishItems = new HashMap<>();
        for (AdaptedDishItem dishItem : this.dishItems) {
            dishItems.put(dishItem.dish.toModelType(), dishItem.quantity);
        }
        final ReadOnlyMember customer = this.customer.toModelType();
        final Date date = new Date(this.date);
        final double price = this.price;
        return new Order(customer, date, price, dishItems);
    }
}
