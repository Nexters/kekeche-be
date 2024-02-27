package com.nexters.kekechebe;

import com.nexters.kekechebe.util.time.TimeUtil;
import com.nexters.kekechebe.util.time.Today;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeUtilTest {
    @Test
    public void 현재_날짜의_시작시간과_끝시간을_가져온다() {
        // given
        Today startAndEndOfToday = TimeUtil.getStartAndEndOfToday();

        // when
        LocalDateTime startOfDay = startAndEndOfToday.getStartOfDay();
        LocalDateTime endOfDay = startAndEndOfToday.getEndOfDay();

        // then
        assertThat(startOfDay)
                .hasYear(LocalDateTime.now().getYear())
                .hasMonth(LocalDateTime.now().getMonth())
                .hasDayOfMonth(LocalDateTime.now().getDayOfMonth())
                .hasHour(0)
                .hasMinute(0)
                .hasSecond(0);

        assertThat(endOfDay)
                .hasYear(LocalDateTime.now().getYear())
                .hasMonth(LocalDateTime.now().getMonth())
                .hasDayOfMonth(LocalDateTime.now().getDayOfMonth())
                .hasHour(23)
                .hasMinute(59)
                .hasSecond(59)
                .hasNano(999999999);
    }

    @Test
    public void 두_날짜_사이의_일수_구하기() {
        // given
        LocalDate beforeDate = LocalDate.of(2023, 2, 7);
        LocalDate afterDate = LocalDate.of(2024, 2, 26);

        // when
        long dateBetween = TimeUtil.getDateBetween(beforeDate, afterDate);

        // then
        assertThat(dateBetween)
                .isEqualTo(384L);
    }
}
