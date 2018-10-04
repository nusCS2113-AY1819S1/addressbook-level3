package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.order.Dish;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly adapted dish data holder class.
 */
public class AdaptedDish {

    @XmlElement
    public String dishName;
    @XmlElement
    public double dishPrice;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedDish() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedDish
     */
    public AdaptedDish(Dish source) {
        dishName = source.getDishName();
        dishPrice = source.getDishPrice();
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
        return Utils.isAnyNull(dishName, dishPrice);
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Dish toModelType() throws IllegalValueException {
        return new Dish(dishName, dishPrice);
    }
}
