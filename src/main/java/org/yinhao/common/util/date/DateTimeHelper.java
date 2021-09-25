package org.yinhao.common.util.date;


import lombok.extern.slf4j.Slf4j;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public final class DateTimeHelper {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String HH_MM_SS = "HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static Date today() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return dateToLocalDateTime(date, ZoneId.systemDefault());
    }

    public static LocalDateTime dateToLocalDateTime(Date date, ZoneId tzId) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, tzId);
    }

    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDate(date, ZoneId.systemDefault());
    }

    public static LocalDate dateToLocalDate(Date date, ZoneId tzId) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, tzId).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        return dateToLocalTime(date, ZoneId.systemDefault());
    }

    public static LocalTime dateToLocalTime(Date date, ZoneId tzId) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, tzId).toLocalTime();
    }

    public static Date localDateTimeToDate(LocalDateTime ldt) {
        return localDateTimeToDate(ldt, ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime ldt, ZoneId tzId) {
        Instant instant = ldt.atZone(tzId).toInstant();
        return Date.from(instant);
    }

    public static Date localDateToDate(LocalDate ld) {
        return localDateToDate(ld, ZoneId.systemDefault());
    }

    public static Date localDateToDate(LocalDate ld, ZoneId tzId) {
        Instant instant = ld.atStartOfDay().atZone(tzId).toInstant();
        return Date.from(instant);
    }

    public static Date localTimeToDate(LocalTime lt) {
        return localTimeToDate(lt, ZoneId.systemDefault());
    }

    public static Date localTimeToDate(LocalTime lt, ZoneId tzId) {
        Instant instant = lt.atDate(LocalDate.now()).atZone(tzId).toInstant();
        return Date.from(instant);
    }

    public static Date localTimeToDate(LocalTime lt, int year, int month, int day) {
        return localTimeToDate(lt, ZoneId.systemDefault(), year, month, day);
    }

    public static Date localTimeToDate(LocalTime lt, ZoneId tzId, int year, int month, int day) {
        Instant instant = lt.atDate(LocalDate.of(year, month, day)).atZone(tzId).toInstant();
        return Date.from(instant);
    }

    public static String dateToString(Date date, String formatter) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(formatter);
        LocalDateTime dateTime = dateToLocalDateTime(date);
        return dateTime.format(f);
    }

    public static String localDateToString(LocalDateTime dateTime, String formatter) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(formatter);
        return dateTime.format(f);
    }

    public static Date stringToDate(String date, String pattern) {
        LocalDateTime parse = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
        return localDateTimeToDate(parse);
    }

    public static Date stringToDateLong(String date) {
        LocalDateTime parse = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
        return localDateTimeToDate(parse);
    }

    public static String formatDate2LongStr(Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return dateTimeFormatter.format(dateToLocalDateTime(date));
    }

    public static Date stringToDateShort(String date) {
        if (date == null || (date = date.trim()).isEmpty()) {
            return null;
        }
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern(YYYY_MM_DD));
        return localDateToDate(parse);
    }

    public static Date stringToDateShort(String date, String pattern) {
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        return localDateToDate(parse);
    }

    public static LocalDateTime stringToLocalDateTime(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        return parse;
    }

    public static Date plusDays(Date date, int day) {
        if (date == null) {
            return null;
        }
        LocalDateTime dateTime = dateToLocalDateTime(date);
        dateTime = dateTime.plusDays(day);
        return localDateTimeToDate(dateTime);
    }

    public static Date clearToDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getCurrentTime() {
        return new Date();
    }


    /**
     * timestamp 转换为日志
     *
     * @param timestamp 单位：毫秒
     * @return: java.util.Date
     **/
    public static Date convertTimestampToDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }

    /**
     * 格式化周几
     *
     * @param date :
     * @return: {@link String}
     **/
    public static String formatDayOfWeek(Date date) {
        LocalDate localDate = dateToLocalDate(date, ZoneId.of("GMT+8"));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return "星期一";
            case TUESDAY:
                return "星期二";
            case WEDNESDAY:
                return "星期三";
            case THURSDAY:
                return "星期四";
            case FRIDAY:
                return "星期五";
            case SATURDAY:
                return "星期六";
            default:
                return "星期日";
        }
    }


}
