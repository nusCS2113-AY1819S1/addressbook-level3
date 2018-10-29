package seedu.addressbook.data.statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import seedu.addressbook.data.order.ReadOnlyOrder;

public class OrderDateTable {
    private Map<Integer, YearOrder> yearMap;
    private Calendar calendar;

    public OrderDateTable() {
        this.calendar = new GregorianCalendar();
        yearMap = new HashMap<>();
    }

    public void addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        if (!yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            YearOrder newYear = new YearOrder(calendar.get(Calendar.YEAR));
            yearMap.put(calendar.get(Calendar.YEAR), newYear.addData(order));
        } else {
            yearMap.put(calendar.get(Calendar.YEAR), yearMap.get(calendar.get(Calendar.YEAR)).addData(order));
        }
    }

    public int getYearCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR)).count;
        } else {
            return 0;
        }
    }

    public Double getYearRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            return yearMap.get(calendar.get(Calendar.YEAR)).totalRevenue;
        } else {
            return 0.0;
        }
    }

    public int getMonthCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR)))
            return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).count;
        else return 0;
    }

    public Double getMonthRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR)))
            return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).totalRevenue;
        else return 0.0;
    }

    public int getDayCount(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR)))
            return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).dayMap.get(calendar.get(Calendar.DATE)).count;
        else
            return 0;
    }

    public Double getDayRevenue(Date date) {
        calendar.setTime(date);
        if (yearMap.containsKey(calendar.get(Calendar.YEAR)))
            return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).dayMap.get(calendar.get(Calendar.DATE)).totalRevenue;
        else return 0.0;
    }
}

class YearOrder {
    int yearNo, count;
    Double totalRevenue;
    Map<Integer, MonthOrder> monthMap;
    Calendar calendar;

    public YearOrder(int yearNo) {
        this.calendar = new GregorianCalendar();
        this.yearNo = yearNo;
        this.count = 0;
        this.totalRevenue = 0.0;
        this.monthMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthMap.put(i, new MonthOrder(i));
        }
    }

    public YearOrder addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        count++;
        totalRevenue += order.getPrice();
        monthMap.put(calendar.get(Calendar.MONTH), monthMap.get(calendar.get(Calendar.MONTH)).addData(order));
        return this;
    }
}

class MonthOrder {
    int monthNo, count;
    Double totalRevenue;
    Map<Integer, DayOrder> dayMap;
    Calendar calendar;

    public MonthOrder(int monthNo) {
        this.calendar = new GregorianCalendar();
        this.monthNo = monthNo;
        this.count = 0;
        this.totalRevenue = 0.0;
        this.dayMap = new HashMap<>();
        for (int i = 0; i < 31; i++) {
            dayMap.put(i, new DayOrder(i));
        }
    }

    public MonthOrder addData(ReadOnlyOrder order) {
        calendar.setTime(order.getDate());
        count++;
        totalRevenue += order.getPrice();
        dayMap.put(calendar.get(Calendar.DATE), dayMap.get(calendar.get(Calendar.DATE)).addData(order));
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
