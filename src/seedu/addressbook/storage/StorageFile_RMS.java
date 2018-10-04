package seedu.addressbook.storage;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.RMS_menu;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.storage.jaxb.AdaptedAddressBook;
import seedu.addressbook.storage.jaxb.AdaptedRMS_menu;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the file used to store address book data.
 */
public class StorageFile_RMS {

    /** Default file path used if the user doesn't provide the file name. */
    public static final String DEFAULT_STORAGE_FILEPATH = "rms.txt";

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

    private final JAXBContext jaxbContext;

    public final Path path;

    /**
     * @throws InvalidStorageFilePathException if the default path is invalid
     */
    public StorageFile_RMS() throws InvalidStorageFilePathException {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    /**
     * @throws InvalidStorageFilePathException if the given file path is invalid
     */
    public StorageFile_RMS(String filePath) throws InvalidStorageFilePathException {
        try {
            jaxbContext = JAXBContext.newInstance(AdaptedRMS_menu.class);
        } catch (JAXBException jaxbe) {
            throw new RuntimeException("jaxb initialisation error");
        }

        path = Paths.get(filePath);
        if (!isValidPath(path)) {
            throw new InvalidStorageFilePathException("Storage file should end with '.txt'");
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
     * Saves all data to this storage file.
     *
     * @throws StorageOperationException if there were errors converting and/or storing data to file.
     */
    public void save(RMS_menu menuBook) throws StorageOperationException {

        /* Note: Note the 'try with resource' statement below.
         * More info: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
         */
        try (final Writer fileWriter =
                     new BufferedWriter(new FileWriter(path.toFile()))) {

            final AdaptedRMS_menu toSave = new AdaptedRMS_menu(menuBook);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(toSave, fileWriter);

        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + path + " error: " + ioe.getMessage());
        } catch (JAXBException jaxbe) {
            throw new StorageOperationException("Error converting address book into storage format");
        }
    }

    /**
     * Loads data from this storage file.
     *
     * @throws StorageOperationException if there were errors reading and/or converting data from file.
     */
    public RMS_menu load() throws StorageOperationException {
        try (final Reader fileReader =
                     new BufferedReader(new FileReader(path.toFile()))) {

            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            final AdaptedRMS_menu loaded = (AdaptedRMS_menu) unmarshaller.unmarshal(fileReader);
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
            final RMS_menu empty = new RMS_menu();
            save(empty);
            return empty;

        // other errors
        } catch (IOException ioe) {
            throw new StorageOperationException("Error writing to file: " + path);
        } catch (JAXBException jaxbe) {
            throw new StorageOperationException("Error parsing file data format");
        } catch (IllegalValueException ive) {
            throw new StorageOperationException("File contains illegal data values; data type constraints not met");
        }
    }

    public String getPath() {
        return path.toString();
    }

}
