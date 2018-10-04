package seedu.addressbook.storage;

import seedu.addressbook.data.RMS;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.order.OrderList;
import seedu.addressbook.storage.jaxb.AdaptedOrderList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the file used to store order list data.
 */
public class RMSStorageFile {

    /** Default file path used if the user doesn't provide the file name. */
    public static final String DEFAULT_ORDER_STORAGE_FILEPATH = "orderlist.txt";

    /* Note: Note the use of nested classes below.
     * More info https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
     */

    /**
     * Signals that the given file path does not fulfill the storage filepath constraints.
     */
    public static class InvalidStorageFilePathException extends IllegalValueException {
        public InvalidStorageFilePathException(String message) {
            super(message);
        }
    }

    /**
     * Signals that some error has occured while trying to convert and read/write data between the application
     * and the storage file.
     */
    public static class StorageOperationException extends Exception {
        public StorageOperationException(String message) {
            super(message);
        }
    }

    private final JAXBContext jaxbOrderListContext;

    public final Path orderListPath;

    /**
     * @throws InvalidStorageFilePathException if the default path is invalid
     */
    public RMSStorageFile() throws InvalidStorageFilePathException {
        this(DEFAULT_ORDER_STORAGE_FILEPATH);
    }

    /**
     * @throws InvalidStorageFilePathException if the given file path is invalid
     */
    public RMSStorageFile(String orderFilePath) throws InvalidStorageFilePathException {
        try {
            jaxbOrderListContext = JAXBContext.newInstance(AdaptedOrderList.class);
        } catch (JAXBException jaxbe) {
            throw new RuntimeException("jaxb initialisation error");
        }

        orderListPath = Paths.get(orderFilePath);
        if (!isValidPath(orderListPath)) {
            throw new InvalidStorageFilePathException("Order storage file should end with '.txt'");
        }
    }

    /**
     * Returns true if the given path is acceptable as a storage file.
     * The file path is considered acceptable if it ends with '.txt'
     */
    private static boolean isValidPath(Path filePath) {
        return filePath.toString().endsWith(".txt");
    }

    /**
     * Saves all data to these storage files.
     *
     * @throws StorageOperationException if there were errors converting and/or storing data to file.
     */
    public void save(RMS restaurantSystem) throws StorageOperationException {

        /* Note: Note the 'try with resource' statement below.
         * More info: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
         */
        saveOrderList(restaurantSystem.getAllOrders());
    }

    /**
     * Saves order data to the order storage files.
     *
     * @throws StorageOperationException if there were errors converting and/or storing data to file.
     */
    public void saveOrderList(OrderList orderList) throws StorageOperationException {

        /* Note: Note the 'try with resource' statement below.
         * More info: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
         */
        try (final Writer fileWriter =
                     new BufferedWriter(new FileWriter(orderListPath.toFile()))) {

            final AdaptedOrderList toSave = new AdaptedOrderList(orderList);
            final Marshaller marshaller = jaxbOrderListContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(toSave, fileWriter);

        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + orderListPath + " error: " + ioe.getMessage());
        } catch (JAXBException jaxbe) {
            throw new StorageOperationException("Error converting order list into storage format");
        }
    }

    /**
     * Loads data from these storage file.
     *
     * @throws StorageOperationException if there were errors reading and/or converting data from file.
     */
    public RMS load() throws StorageOperationException {
        OrderList loadedOrderList = loadOrderList();
        return new RMS(loadedOrderList);
    }

    /**
     * Loads data from these storage file.
     *
     * @throws StorageOperationException if there were errors reading and/or converting data from file.
     */
    public OrderList loadOrderList() throws StorageOperationException {
        try (final Reader fileReader =
                     new BufferedReader(new FileReader(orderListPath.toFile()))) {

            final Unmarshaller unmarshaller = jaxbOrderListContext.createUnmarshaller();
            final AdaptedOrderList loaded = (AdaptedOrderList) unmarshaller.unmarshal(fileReader);
            // manual check for missing elements
            if (loaded.isAnyRequiredFieldMissing()) {
                throw new StorageOperationException("File data missing some elements");
            }
            return loaded.toModelType();

            /* Note: Here, we are using an exception to create the file if it is missing. However, we should minimize
             * using exceptions to facilitate normal paths of execution. If we consider the missing file as a 'normal'
             * situation (i.e. not truly exceptional) we should not use an exception to handle it.
             */

            // create empty file if not found
        } catch (FileNotFoundException fnfe) {
            final OrderList empty = new OrderList();
            saveOrderList(empty);
            return empty;

            // other errors
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + orderListPath);
        } catch (JAXBException jaxbe) {
            throw new StorageOperationException("Error parsing order list file data format");
        } catch (IllegalValueException ive) {
            throw new StorageOperationException("Order list file contains illegal data values; "
                    + "data type constraints not met");
        }
    }

    public String getOrderListPath() {
        return orderListPath.toString();
    }

}
