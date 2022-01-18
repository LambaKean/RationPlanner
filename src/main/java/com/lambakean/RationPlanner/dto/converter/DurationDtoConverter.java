package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.DurationDto;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DurationDtoConverter {

    public LocalTime toLocalTime(DurationDto durationDto) {

        if(durationDto == null) {
            return null;
        }

        if(0 > durationDto.getHours() || durationDto.getHours() > 23
                || 0 > durationDto.getMinutes() || durationDto.getMinutes() > 59) {
            return null;
        }

        return LocalTime.of(durationDto.getHours(), durationDto.getMinutes());
    }

    public DurationDto toDurationDto(LocalTime localTime) {
        return new DurationDto(localTime.getHour(), localTime.getMinute());
    }
}
