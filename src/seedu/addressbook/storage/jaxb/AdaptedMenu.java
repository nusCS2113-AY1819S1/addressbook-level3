package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.MenuName;
import seedu.addressbook.data.menu.Price;
import seedu.addressbook.data.menu.ReadOnlyMenus;
import seedu.addressbook.data.menu.Type;
import seedu.addressbook.data.tag.Tag;

/**
 * JAXB-friendly adapted menu data holder class.
 */
public class AdaptedMenu {

    /**
     * JAXB-friendly adapted menu item detail data holder class.
     */
    private static class AdaptedMenuItemDetail {
        private String value;

        @XmlValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private AdaptedMenuItemDetail price;
    @XmlElement(required = true)
    private AdaptedMenuItemDetail type;


    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedMenu() {}


    /**
     * Converts a given Menu into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedMenu
     */
    public AdaptedMenu(ReadOnlyMenus source) {
        name = source.getName().fullName;

        price = new AdaptedMenuItemDetail();
        //price.isPrivate = source.getPrice().isPrivate();
        price.setValue(source.getPrice().value);

        type = new AdaptedMenuItemDetail();
        type.setValue(source.getType().value);

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
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
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if phone/email/address are all not null
        return Utils.isAnyNull(name, price, type)
                || Utils.isAnyNull(price.getValue(), type.getValue());
    }

    /**
     * Converts this jaxb-friendly adapted menu object into the Menu object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted menu
     */
    public Menu toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final MenuName name = new MenuName(this.name);
        final Price price = new Price(this.price.getValue()/*, this.price.isPrivate*/);
        final Type type = new Type(this.type.getValue());
        return new Menu(name, price, type, tags);
    }
}
