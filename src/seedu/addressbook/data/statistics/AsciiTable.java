package seedu.addressbook.data.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a table made in Ascii
 */
public class AsciiTable {
    private String[] headings;
    private List<List<String>> data;
    private int noOfColumns;
    private int[] columnWidths;

    private char colBorder;
    private char rowBorder;
    private char rowHBorder;
    private String prePad;
    private String postPad;

    public AsciiTable(String[] headings) {
        if (headings == null) {
            throw new IllegalArgumentException("Headings is null.");
        }
        if (headings.length == 0) {
            throw new IllegalArgumentException("No headings.");
        }

        this.colBorder = '|';
        this.rowBorder = '-';
        this.rowHBorder = '=';
        this.prePad = " ";
        this.postPad = " ";

        this.noOfColumns = headings.length;
        this.columnWidths = new int[noOfColumns];
        this.headings = headings;
        this.data = new ArrayList<>();
        for (int i = 0; i < noOfColumns; i++) {
            this.columnWidths[i] = headings[i].length();
        }
    }

    /**
     * Add and populate a row if the number number of entries is the same as the number of columns
     */
    public void addRow(String[] rowData) {
        if (rowData.length != this.noOfColumns) {
            return;
        }

        data.add(Arrays.asList(rowData));
        for (int i = 0; i < this.noOfColumns; i++) {
            if (columnWidths[i] < rowData[i].length()) {
                columnWidths[i] = rowData[i].length();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(createRowBorder(true));

        for (int i = 0; i < noOfColumns; i++) {
            String cellString = headings[i];
            sb.append(padCell(cellString, columnWidths[i], ' ', i == 0));
        }
        sb.append("\n");
        sb.append(createRowBorder(true));

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < noOfColumns; j++) {
                String cellString = data.get(i).get(j);
                sb.append(padCell(cellString, columnWidths[j], ' ', j == 0));
            }
            sb.append("\n");
            sb.append(createRowBorder(false));
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Create a border for a row
     */
    private String createRowBorder(boolean heading) {
        int tableWidth = calculateTableWidth();
        StringBuffer outputBuffer = new StringBuffer(tableWidth);
        for (int i = 0; i < tableWidth; i++) {
            if (heading) {
                outputBuffer.append(rowHBorder);
            } else {
                outputBuffer.append(rowBorder);
            }
        }
        return outputBuffer.toString() + "\n";
    }

    /**
     * Calculate and return the width of a table
     */
    private int calculateTableWidth() {
        int width = 0;
        width += 1;
        for (int columnWidth : columnWidths) {
            width += columnWidth;
            width += 1;
            width += prePad.length();
            width += postPad.length();
        }

        return width;
    }

    /**
     * Return a String after padding a String into a table cell
     */
    private String padCell(String in, int width, char pad, boolean first) {
        int cellSize = in.length();
        int padSize = width - cellSize;
        StringBuffer outputBuffer = new StringBuffer(padSize);
        for (int i = 0; i < padSize; i++) {
            outputBuffer.append(pad);
        }


        return ((first) ? colBorder : "") + this.prePad + in + outputBuffer.toString() + this.postPad + colBorder;
    }

}
