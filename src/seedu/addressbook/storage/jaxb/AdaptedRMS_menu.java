package seedu.addressbook.storage.jaxb;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.RMS_menu;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Menu;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniqueMenuList;
import seedu.addressbook.data.person.UniquePersonList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly adapted address book data holder class.
 */
@XmlRootElement(name = "RMS")
public class AdaptedRMS_menu {

    @XmlElement
    private List<AdaptedMenu> menus = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedRMS_menu() {}

    /**
     * Converts a given AddressBook into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAddressBook
     */
    public AdaptedRMS_menu(RMS_menu source) {
        menus = new ArrayList<>();
        source.getAllMenus().forEach(menu -> menus.add(new AdaptedMenu(menu)));
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
        return menus.stream().anyMatch(AdaptedMenu::isAnyRequiredFieldMissing);
    }


    /**
     * Converts this jaxb-friendly {@code AdaptedAddressBook} object into the corresponding(@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public RMS_menu toModelType() throws IllegalValueException {
        final List<Menu> menuList = new ArrayList<>();
        for (AdaptedMenu menu : menus) {
            menuList.add(menu.toModelType());
        }
        return new RMS_menu(new UniqueMenuList(menuList));
    }
}
