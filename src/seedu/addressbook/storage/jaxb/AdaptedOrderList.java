package seedu.addressbook.storage.jaxb;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.order.Order;
import seedu.addressbook.data.order.OrderList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly adapted order list data holder class.
 */
@XmlRootElement(name = "OrderList")
public class AdaptedOrderList {

    @XmlElement
    private List<AdaptedOrder> orders = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedOrderList() {}

    /**
     * Converts a given OrderList into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedOrderList
     */
    public AdaptedOrderList(OrderList source) {
        orders = new ArrayList<>();
        source.forEach(order -> orders.add(new AdaptedOrder(order)));
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
        return orders.stream().anyMatch(AdaptedOrder::isAnyRequiredFieldMissing);
    }


    /**
     * Converts this jaxb-friendly {@code AdaptedOrderList} object into the corresponding(@code OrderList} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public OrderList toModelType() throws IllegalValueException {
        final List<Order> orderList = new ArrayList<>();
        for (AdaptedOrder order : orders) {
            orderList.add(order.toModelType());
        }
        return new OrderList(orderList);
    }
}
