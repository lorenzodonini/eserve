package de.tum.ecorp.reservationapp.model;

import java.util.Iterator;
import java.util.List;

public class OpeningTime {
    private int weekday;
    private List<String> openingTimes;

    public OpeningTime(int weekday, List<String> openingTimes) {
        this.weekday = weekday;
        this.openingTimes = openingTimes;
    }

    public String getWeekdayAsString() {
        switch (weekday) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "Unknown";
        }
    }

    public int getWeekday() {
        return weekday;
    }

    public String getConcatenatedOpeningTimes() {
        StringBuilder sb = new StringBuilder();
        if (openingTimes == null || openingTimes.size() == 0) {
            sb.append("Closed");
            return sb.toString();
        }
        Iterator<String> it = openingTimes.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
