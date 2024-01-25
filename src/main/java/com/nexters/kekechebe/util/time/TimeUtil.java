package com.nexters.kekechebe.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static Today getStartAndEndOfToday() {
        LocalDate currentDate = LocalDate.now();

        LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(currentDate, LocalTime.MAX);

        return Today.builder()
                .startOfDay(startOfDay)
                .endOfDay(endOfDay)
                .build();
    }
}
