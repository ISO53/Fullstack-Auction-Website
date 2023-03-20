package com.iso.bidding.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateConverter {

    public static final byte SECOND = 0;
    public static final byte MINUTE = 1;
    public static final byte HOUR = 3;
    public static final byte DAY = 4;
    public static final byte WEEK = 5;
    public static final byte MONTH = 6;
    public static final byte YEAR = 7;

    public static long timeUnitToMillis(long timeUnit, int TYPE) {
        return switch (TYPE) {
            case SECOND -> timeUnit * 1000;
            case MINUTE -> timeUnit * 60000;
            case HOUR -> timeUnit * 3600000;
            case DAY -> timeUnit * 86400000;
            case WEEK -> timeUnit * 604800000;
            case MONTH -> (long) (timeUnit * 2.62974383 * Math.pow(10, 9));
            case YEAR -> (long) (timeUnit * 3.1556926 * Math.pow(10, 10));
            default -> -1;
        };
    }

    public static long millisToTimeUnit(long timeUnit, int TYPE) {
        return switch (TYPE) {
            case SECOND -> timeUnit / 1000;
            case MINUTE -> timeUnit / 60000;
            case HOUR -> timeUnit / 3600000;
            case DAY -> timeUnit / 86400000;
            case WEEK -> timeUnit / 604800000;
            case MONTH -> (long) (timeUnit / (2.62974383 * Math.pow(10, 9)));
            case YEAR -> (long) (timeUnit / (3.1556926 * Math.pow(10, 10)));
            default -> -1;
        };
    }

    public static String millisToDateFormat(long millis) {
        DateFormat obj = new SimpleDateFormat("dd MM yyyy HH:mm:ss:SSS Z");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(millis);
        // now we format the res by using SimpleDateFormat
        return obj.format(res);
    }
}
