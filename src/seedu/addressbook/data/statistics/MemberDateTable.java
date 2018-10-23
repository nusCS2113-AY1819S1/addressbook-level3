package seedu.addressbook.data.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberDateTable {
    private Map<Integer, YearMember> yearMap;

    public MemberDateTable() {
        yearMap = new HashMap<>();
    }

    public void addData(Date date) {
        if (!yearMap.containsKey(date.getYear())) {
            YearMember newYear = new YearMember(date.getYear());
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

class YearMember {
    int yearNo, count;
    Map<Integer, MonthMember> monthMap;

    public YearMember(int yearNo) {
        this.yearNo = yearNo;
        this.count = 0;
        this.monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthMap.put(i, new MonthMember(i));
        }
    }

    public YearMember addData(Date date) {
        count++;
        monthMap.put(date.getMonth(), monthMap.get(date.getMonth()).addData(date));
        return this;
    }
}

class MonthMember {
    int monthNo, count;
    Map<Integer, DayMember> dayMap;

    public MonthMember(int monthNo) {
        this.monthNo = monthNo;
        this.count = 0;
        this.dayMap = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            dayMap.put(i, new DayMember(i));
        }
    }

    public MonthMember addData(Date date) {
        count++;
        dayMap.put(date.getDay(), dayMap.get(date.getDay()).addData());
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
