package seedu.addressbook.data.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberDateTable {
    private Map<Integer, Year> yearMap;

    public MemberDateTable() {
        yearMap = new HashMap<>();
    }

    public void addData(Date date) {
        if (!yearMap.containsKey(date.getYear())) {
            Year newYear = new Year(date.getYear());
            yearMap.put(date.getYear(), newYear.addData(date));
        } else {
            yearMap.put(date.getYear(), yearMap.get(date.getYear()).addData(date));
        }
    }

    public int getYearCount(Date date) {
        if (yearMap.containsKey(date.getYear())) {
            return yearMap.get(date.getYear()).count;
        } else {
            return 0;
        }
    }

    public int getMonthCount(Date date) {
        return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).count;
    }

    public int getDayCount(Date date) {
        return yearMap.get(date.getYear()).monthMap.get(date.getMonth()).dayMap.get(date.getDay()).count;
    }
}

class Year {
    int yearNo, count;
    Map<Integer, Month> monthMap;

    public Year(int yearNo) {
        this.yearNo = yearNo;
        this.count = 0;
        this.monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthMap.put(i, new Month(i));
        }
    }

    public Year addData(Date date) {
        count++;
        monthMap.put(date.getMonth(), monthMap.get(date.getMonth()).addData(date));
        return this;
    }
}

class Month {
    int monthNo, count;
    Map<Integer, Day> dayMap;

    public Month(int monthNo) {
        this.monthNo = monthNo;
        this.count = 0;
        this.dayMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            dayMap.put(i, new Day(i));
        }
    }

    public Month addData(Date date) {
        count++;
        dayMap.put(date.getDay(), dayMap.get(date.getDay()).addData());
        return this;
    }
}

class Day {
    int dayNo, count;

    public Day(int dayNo) {
        this.dayNo = dayNo;
        this.count = 0;
    }

    public Day addData() {
        count++;
        return this;
    }
}
