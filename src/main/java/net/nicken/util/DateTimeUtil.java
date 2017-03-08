package net.nicken.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end){
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime){
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <=0;
    }

    public static String toString(LocalDateTime ldt){
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

}
