package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.TimeDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class TimeDtoConverter {

    public LocalTime toLocalTime(TimeDto timeDto) {

        if(timeDto == null) {
            return null;
        }

        if(0 > timeDto.getHours() || timeDto.getHours() > 23
                || 0 > timeDto.getMinutes() || timeDto.getMinutes() > 59) {
            return null;
        }

        return LocalTime.of(timeDto.getHours(), timeDto.getMinutes());
    }

    public Duration toDuration(TimeDto timeDto) {

        if(timeDto == null) {
            return null;
        }

        int durationInMinutes = 0;

        if(timeDto.getHours() != null) {
            durationInMinutes += timeDto.getHours() * 60;
        }

        if(timeDto.getMinutes() != null) {
            durationInMinutes += timeDto.getMinutes();
        }

        return Duration.ofMinutes(durationInMinutes);
    }

    public TimeDto toTimeDto(LocalTime localTime) {

        if(localTime == null) {
            return null;
        }

        return new TimeDto(localTime.getHour(), localTime.getMinute());
    }

    public TimeDto toTimeDto(Duration duration) {

        if(duration == null) {
            return null;
        }

        TimeDto timeDto = new TimeDto();

        timeDto.setHours((int) duration.toHours());
        timeDto.setMinutes((int) duration.toMinutes());

        return timeDto;
    }
}
