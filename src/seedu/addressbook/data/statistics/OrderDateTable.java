package seedu.addressbook.data.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.data.order.ReadOnlyOrder;

public class OrderDateTable {
    private Map<Integer, YearOrder> yearMap;

    public OrderDateTable() {
        yearMap = new HashMap<>();
    }

    public void addData(ReadOnlyOrder order) {
        Date date = order.getDate();
        if (!yearMap.containsKey(date.getYear())) {
            YearOrder newYear = new YearOrder(date.getYear());
            yearMap.put(date.getYear(), newYear.addData(order));
        } else {
            yearMap.put(date.getYear(), yearMap.get(date.getYear()).addData(order));
        }
    }

    public int getYearCount(Date date) {
        if (yearMap.containsKey(date.getYear())) {
            return yearMap.get(date.getYear()).count;
        } else {
            return 0;
        }
    }

    public Double getYearRevenue(Date date) {
        if (yearMap.containsKey(date.getYear())) {
            return yearMap.get(date.getYear()).totalRevenue;
        } else {
            return 0.0;
        }
    }

    public int getMonthCount(Date date) {
        if (yearMap.containsKey(date.getYear()))
            return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).count;
        else return 0;
    }

    public Double getMonthRevenue(Date date) {
        if (yearMap.containsKey(date.getYear()))
            return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).totalRevenue;
        else return 0.0;
    }

    public int getDayCount(Date date) {
        if (yearMap.containsKey(date.getYear()))
            return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).dayMap.get(date.getDate()).count;
        else
            return 0;
    }

    public Double getDayRevenue(Date date) {
        if (yearMap.containsKey(date.getYear()))
            return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).dayMap.get(date.getDate()).totalRevenue;
        else return 0.0;
    }
}

class YearOrder {
    int yearNo, count;
    Double totalRevenue;
    Map<Integer, MonthOrder> monthMap;

    public YearOrder(int yearNo) {
        this.yearNo = yearNo;
        this.count = 0;
        this.totalRevenue = 0.0;
        this.monthMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthMap.put(i, new MonthOrder(i));
        }
    }

    public YearOrder addData(ReadOnlyOrder order) {
        count++;
        totalRevenue += order.getPrice();
        Date date = order.getDate();
        monthMap.put(date.getMonth(), monthMap.get(date.getMonth()).addData(order));
        return this;
    }
}

class MonthOrder {
    int monthNo, count;
    Double totalRevenue;
    Map<Integer, DayOrder> dayMap;

    public MonthOrder(int monthNo) {
        this.monthNo = monthNo;
        this.count = 0;
        this.totalRevenue = 0.0;
        this.dayMap = new HashMap<>();
        for (int i = 0; i < 31; i++) {
            dayMap.put(i, new DayOrder(i));
        }
    }

    public MonthOrder addData(ReadOnlyOrder order) {
        count++;
        totalRevenue += order.getPrice();
        Date date = order.getDate();
        dayMap.put(date.getDate(), dayMap.get(date.getDate()).addData(order));
        return this;
    }
}

class DayOrder {
    int dayNo, count;
    Double totalRevenue;

    public DayOrder(int dayNo) {
        this.dayNo = dayNo;
        this.count = 0;
        this.totalRevenue = 0.0;
    }

    public DayOrder addData(ReadOnlyOrder order) {
        count++;
        totalRevenue += order.getPrice();
        return this;
    }
}
