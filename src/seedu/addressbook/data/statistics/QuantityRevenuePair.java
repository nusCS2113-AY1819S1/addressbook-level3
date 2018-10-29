package seedu.addressbook.data.statistics;

/**
 * Represents a pair of data: quantity and revenue of a statistic field
 */
public class QuantityRevenuePair implements Comparable<QuantityRevenuePair> {
    private int quantity;
    private double revenue;

    public QuantityRevenuePair() {
        this.quantity = 0;
        this.revenue = 0;
    }

    public QuantityRevenuePair(int quantity, double revenue) {
        this.quantity = quantity;
        this.revenue = revenue;
    }

    /**
     * Update the data in the quantity - revenue pair
     */
    public QuantityRevenuePair addData(int quantity, double price) {
        this.quantity += quantity;
        this.revenue += quantity * price;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    @Override
    public int compareTo(QuantityRevenuePair target) {
        if (this.getQuantity() < target.getQuantity()) {
            return -1;
        } else if (this.getQuantity() > target.getQuantity()) {
            return 1;
        } else {
            return 0;
        }
    }
}
