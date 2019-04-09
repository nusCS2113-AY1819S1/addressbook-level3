package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.person.Associated;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted associate data holder class.
 */
public class AdaptedAssociate {
    @XmlValue
    public String value;

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAssociate() {}

    /**
     * Converts a given Associated into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAssociate
     */
    public AdaptedAssociate(Associated source) {
        value = source.value;
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
        return Utils.isAnyNull(value);
    }

    /**
     * Converts this jaxb-friendly adapted associate object into the Associated object.
     */
    public Associated toModelType(){
        return new Associated(value);
    }
}
