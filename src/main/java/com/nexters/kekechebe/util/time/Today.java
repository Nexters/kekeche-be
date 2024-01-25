package com.nexters.kekechebe.util.time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Today {
    private final LocalDateTime startOfDay;
    private final LocalDateTime endOfDay;
}
