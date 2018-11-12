package seedu.addressbook.data.statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent a table storing the registering day of the members in the member list
 */
public class MemberDateTable {
    private Map<Integer, YearMember> yearMap;
    private Calendar calendar;

    public MemberDateTable() {
        this.calendar = new GregorianCalendar();
        this.yearMap = new HashMap<>();
    }

    /**
     * Adjust the yearMap based on the added Date
     */
    public void addData(Date date) {
        calendar.setTime(date);
        if (!yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            YearMember newYear = new YearMember();
            yearMap.put(calendar.get(Calendar.YEAR), newYear.addData(date));
        } else {
            yearMap.put(calendar.get(Calendar.YEAR), yearMap.get(calendar.get(Calendar.YEAR)).addData(date));
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

    public int getMonthCount(Date date) {
        calendar.setTime(date);
        return yearMap.get(calendar.get(Calendar.YEAR)).getMonthMap().get(calendar.get(Calendar.MONTH)).getCount();
    }

    public int getDayCount() {
        return yearMap.get(calendar.get(Calendar.YEAR))
                .getMonthMap().get(calendar.get(Calendar.MONTH))
                .getDayMap().get(calendar.get(Calendar.DATE))
                .getCount();
    }
}

/**
 * Represents an year in the yearMap
 */
class YearMember {
    private int count;
    private Map<Integer, MonthMember> monthMap;
    private Calendar calendar;

    public YearMember() {
        this.calendar = new GregorianCalendar();
        this.count = 0;
        this.monthMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            getMonthMap().put(i, new MonthMember());
        }
    }

    /**
     * Adjust the monthMap based on the added Date and return the yearMember container object
     */
    public YearMember addData(Date date) {
        calendar.setTime(date);
        count = getCount() + 1;
        getMonthMap().put(calendar.get(Calendar.MONTH), getMonthMap().get(calendar.get(Calendar.MONTH)).addData(date));
        return this;
    }

    public int getCount() {
        return count;
    }

    public Map<Integer, MonthMember> getMonthMap() {
        return monthMap;
    }
}

/**
 * Represents a month in the monthMap
 */
class MonthMember {
    private int count;
    private Map<Integer, DayMember> dayMap;
    private Calendar calendar;

    public MonthMember() {
        this.calendar = new GregorianCalendar();
        this.count = 0;
        this.dayMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            getDayMap().put(i, new DayMember());
        }
    }

    /**
     * Adjust the dayMap based on the added Date and return the MonthMember container object
     */
    public MonthMember addData(Date date) {
        calendar.setTime(date);
        count = getCount() + 1;
        getDayMap().put(calendar.get(Calendar.DATE), getDayMap().get(calendar.get(Calendar.DATE)).addData());
        return this;
    }

    public int getCount() {
        return count;
    }

    public Map<Integer, DayMember> getDayMap() {
        return dayMap;
    }
}

/**
 * Represents a day in the dayMap
 */
class DayMember {
    private int count;

    public DayMember() {
        this.count = 0;
    }

    public DayMember addData() {
        count = getCount() + 1;
        return this;
    }

    public int getCount() {
        return count;
    }
}
