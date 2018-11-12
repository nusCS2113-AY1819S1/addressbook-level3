package seedu.addressbook.readandwrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//@@author iamputradanish

/**
 * Class is used to read and write to external text files
 */
public class ReaderAndWriter {

    public File fileToUse (String pathName) {
        return new File(pathName);
    }

    public BufferedReader openReader (File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file));
    }

    public PrintWriter openTempWriter (File file) throws IOException {
        return new PrintWriter(new FileWriter(file));
    }

}
