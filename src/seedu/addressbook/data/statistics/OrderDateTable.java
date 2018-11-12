package seedu.addressbook.data.statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.data.order.ReadOnlyOrder;

//@@author AngWM
/**
 * Represent a table storing the ordering date of the orders in the order list
 */
public class OrderDateTable {
    private Map<Integer, YearOrder> yearMap;
    private Calendar calendar;

    public OrderDateTable() {
        this.calendar = new GregorianCalendar();
        yearMap = new HashMap<>();
    }

    /**
     * Adjust the yearMap based on the added Date
     */
    public void addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        if (!yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            YearOrder newYear = new YearOrder();
            yearMap.put(calendar.get(Calendar.YEAR), newYear.addData(order));
        } else {
            yearMap.put(calendar.get(Calendar.YEAR), yearMap.get(calendar.get(Calendar.YEAR)).addData(order));
        }
    }

    public int getYearCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR)).getCount();
        } else {
            return 0;
        }
    }

    public Double getYearRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR)).getTotalRevenue();
        } else {
            return 0.0;
        }
    }

    public int getMonthCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR)).getMonthMap().get(calendar.get(Calendar.MONTH)).getCount();
        } else {
            return 0;
        }
    }

    public Double getMonthRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR))
                    .getMonthMap().get(calendar.get(Calendar.MONTH))
                    .getTotalRevenue();
        } else {
            return 0.0;
        }
    }

    public int getDayCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR))
                    .getMonthMap().get(calendar.get(Calendar.MONTH))
                    .getDayMap().get(calendar.get(Calendar.DATE))
                    .getCount();
        } else {
            return 0;
        }
    }

    public Double getDayRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR))
                    .getMonthMap().get(calendar.get(Calendar.MONTH))
                    .getDayMap().get(calendar.get(Calendar.DATE))
                    .getTotalRevenue();
        } else {
            return 0.0;
        }
    }
}

/**
 * Represents an year in the yearMap
 */
class YearOrder {
    private int count;
    private Double totalRevenue;
    private Map<Integer, MonthOrder> monthMap;
    private Calendar calendar;

    public YearOrder() {
        this.calendar = new GregorianCalendar();
        this.count = 0;
        this.totalRevenue = 0.0;
        this.monthMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            getMonthMap().put(i, new MonthOrder());
        }
    }

    /**
     * Adjust the monthMap based on the added Date and return the yearOrder container object
     */
    public YearOrder addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        count = getCount() + 1;
        totalRevenue = getTotalRevenue() + order.getPrice();
        getMonthMap().put(calendar.get(Calendar.MONTH), getMonthMap().get(calendar.get(Calendar.MONTH)).addData(order));
        return this;
    }

    public int getCount() {
        return count;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public Map<Integer, MonthOrder> getMonthMap() {
        return monthMap;
    }
}

/**
 * Represents a month in the monthMap
 */
class MonthOrder {
    private int count;
    private Double totalRevenue;
    private Map<Integer, DayOrder> dayMap;
    private Calendar calendar;

    public MonthOrder() {
        this.calendar = new GregorianCalendar();
        this.count = 0;
        this.totalRevenue = 0.0;
        this.dayMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            getDayMap().put(i, new DayOrder());
        }
    }

    /**
     * Adjust the dayMap based on the added Date and return the MonthOrder container object
     */
    public MonthOrder addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        count = getCount() + 1;
        totalRevenue = getTotalRevenue() + order.getPrice();
        getDayMap().put(calendar.get(Calendar.DATE), getDayMap().get(calendar.get(Calendar.DATE)).addData(order));
        return this;
    }

    public Map<Integer, DayOrder> getDayMap() {
        return dayMap;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public int getCount() {
        return count;
    }
}

/**
 * Represents a day in the dayMap
 */
class DayOrder {
    private int count;
    private Double totalRevenue;

    public DayOrder() {
        this.count = 0;
        this.totalRevenue = 0.0;
    }

    public DayOrder addData(ReadOnlyOrder order) {
        count = getCount() + 1;
        totalRevenue = getTotalRevenue() + order.getPrice();
        return this;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public int getCount() {
        return count;
    }
}
