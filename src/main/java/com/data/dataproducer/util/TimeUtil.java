package com.data.dataproducer.util;

import java.time.*;
import java.util.Date;

/**
 * @author danny
 * @date 2019/5/31 10:16 AM
 */
public class TimeUtil {

    /**
     * LocalDateTime转Date
     * @param source
     * @return
     */
    public static Date toDate(LocalDateTime source) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = source.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Date转LocalDateTime
     * @param source
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date source) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = source.toInstant();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * Date转LocalDate
     * @param source
     * @return
     */
    public static LocalDate toLocalDate(Date source) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = source.toInstant();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }
}
