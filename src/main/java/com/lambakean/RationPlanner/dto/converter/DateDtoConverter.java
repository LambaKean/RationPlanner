package com.lambakean.RationPlanner.dto.converter;

import com.lambakean.RationPlanner.dto.DateDto;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;

@Component
public class DateDtoConverter {

    public LocalDate toLocalDate(DateDto dateDto) {

        if(dateDto == null) {
            return null;
        }

        try {
            return LocalDate.of(dateDto.getYear(), dateDto.getMonth(), dateDto.getDay());
        } catch (DateTimeException e) {
            return null;
        }
    }

    public DateDto toDateDto(LocalDate localDate) {

        DateDto dateDto = new DateDto();

        dateDto.setYear(localDate.getYear());
        dateDto.setMonth(localDate.getMonthValue());
        dateDto.setDay(localDate.getDayOfMonth());

        return dateDto;
    }
}
