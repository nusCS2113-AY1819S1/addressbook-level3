package seedu.addressbook.data.statistics;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MemberDateTable {
    private Map<Integer, YearMember> yearMap;
    private Calendar calendar;

    public MemberDateTable() {
        this.calendar = new GregorianCalendar();
        this.yearMap = new HashMap<>();
    }

    public void addData(Date date) {
        calendar.setTime(date);
        if (!yearMap.containsKey(calendar.get(Calendar.YEAR))) {
            YearMember newYear = new YearMember(calendar.get(Calendar.YEAR));
            yearMap.put(calendar.get(Calendar.YEAR), newYear.addData(date));
        } else {
            yearMap.put(calendar.get(Calendar.YEAR), yearMap.get(calendar.get(Calendar.YEAR)).addData(date));
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

    public int getMonthCount(Date date) {
        calendar.setTime(date);;
        return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).count;
    }

    public int getDayCount(Date date) {
        return yearMap.get(calendar.get(Calendar.YEAR)).monthMap.get(calendar.get(Calendar.MONTH)).dayMap.get(calendar.get(Calendar.DATE)).count;
    }
}

class YearMember {
    int yearNo, count;
    Map<Integer, MonthMember> monthMap;
    Calendar calendar;

    public YearMember(int yearNo) {
        this.calendar = new GregorianCalendar();
        this.yearNo = yearNo;
        this.count = 0;
        this.monthMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthMap.put(i, new MonthMember(i));
        }
    }

    public YearMember addData(Date date) {
        calendar.setTime(date);
        count++;
        monthMap.put(calendar.get(Calendar.MONTH), monthMap.get(calendar.get(Calendar.MONTH)).addData(date));
        return this;
    }
}

class MonthMember {
    int monthNo, count;
    Map<Integer, DayMember> dayMap;
    Calendar calendar;

    public MonthMember(int monthNo) {
        this.calendar = new GregorianCalendar();
        this.monthNo = monthNo;
        this.count = 0;
        this.dayMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            dayMap.put(i, new DayMember(i));
        }
    }

    public MonthMember addData(Date date) {
        calendar.setTime(date);
        count++;
        dayMap.put(calendar.get(Calendar.DATE), dayMap.get(calendar.get(Calendar.DATE)).addData());
        return this;
    }
}

class DayMember {
    int dayNo, count;

    public DayMember(int dayNo) {
        this.dayNo = dayNo;
        this.count = 0;
    }

    public DayMember addData() {
        count++;
        return this;
    }
}
