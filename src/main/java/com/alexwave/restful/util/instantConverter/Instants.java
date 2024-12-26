package com.alexwave.restful.util.instantConverter;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface Instants {
    ZoneOffset timeZone = ZoneOffset.UTC;
    String dateFormat = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withZone(timeZone);
}
