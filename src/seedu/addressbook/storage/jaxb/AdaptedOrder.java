package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.member.UniqueMemberList;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.ReadOnlyOrder;

/**
 * JAXB-friendly adapted order data holder class.
 */
public class AdaptedOrder {

    /**
     * JAXB-friendly adapted dish item data holder class.
     */
    private static class AdaptedDishItem {
        private AdaptedMenu dish;
        private int quantity;

        @XmlElement(name = "dish")
        public AdaptedMenu getDish() {
            return dish;
        }

        @XmlElement(name = "quantity")
        public int getQuantity() {
            return quantity;
        }

        public void setDish(AdaptedMenu dish) {
            this.dish = dish;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    @XmlElement(required = true)
    private AdaptedMember customer;
    @XmlElement(required = true)
    private long date;
    @XmlElement(required = true)
    private double price;
    @XmlElement(required = true)
    private int points;

    @XmlElement
    private List<AdaptedDishItem> dishItems = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedOrder() {}

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedOrder
     */
    public AdaptedOrder(ReadOnlyOrder source) {
        customer = new AdaptedMember(source.getCustomer());
        date = source.getDate().getTime();
        price = source.getPrice();
        points = source.getPoints();

        dishItems = new ArrayList<>();
        for (Map.Entry<ReadOnlyMenus, Integer> m: source.getDishItems().entrySet()) {
            AdaptedDishItem dishItem = new AdaptedDishItem();
            dishItem.setDish(new AdaptedMenu(m.getKey()));
            dishItem.setQuantity(m.getValue());
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
            if (dishItem.getDish().isAnyRequiredFieldMissing() || Utils.isAnyNull(dishItem.getQuantity())) {
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
    public Order toModelType(List<Member> memberList) throws IllegalValueException {
        final Map<ReadOnlyMenus, Integer> dishItems = new HashMap<>();
        for (AdaptedDishItem dishItem : this.dishItems) {
            dishItems.put(dishItem.getDish().toModelType(), dishItem.getQuantity());
        }
        ReadOnlyMember customerClone = this.customer.toModelType();
        final ReadOnlyMember customer = UniqueMemberList.retrieveMember(customerClone, memberList);
        final Date date = new Date(this.date);
        final double price = this.price;
        final int pointsToRedeem = this.points;
        return new Order(customer, date, price, dishItems, pointsToRedeem);
    }


}
