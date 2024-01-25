package com.nexters.kekechebe;

import com.nexters.kekechebe.util.time.TimeUtil;
import com.nexters.kekechebe.util.time.Today;
import org.junit.jupiter.api.Test;

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
}
