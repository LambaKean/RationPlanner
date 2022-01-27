package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.ScheduledPlannedDayDto;
import com.lambakean.RationPlanner.dto.converter.ScheduleDtoConverter;
import com.lambakean.RationPlanner.dto.converter.ScheduledPlannedDayDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.Schedule;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.ScheduleRepository;
import com.lambakean.RationPlanner.validator.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Component
public class ScheduleServiceImpl implements ScheduleService {

    private final PrincipalService principalService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDtoConverter scheduleDtoConverter;
    private final ScheduleValidator scheduleValidator;
    private final ValidationService validationService;
    private final ScheduledPlannedDayDtoConverter scheduledPlannedDayDtoConverter;

    @Autowired
    public ScheduleServiceImpl(PrincipalService principalService,
                               ScheduleRepository scheduleRepository,
                               ScheduleDtoConverter scheduleDtoConverter,
                               ScheduleValidator scheduleValidator,
                               ValidationService validationService,
                               ScheduledPlannedDayDtoConverter scheduledPlannedDayDtoConverter) {
        this.principalService = principalService;
        this.scheduleRepository = scheduleRepository;
        this.scheduleDtoConverter = scheduleDtoConverter;
        this.scheduleValidator = scheduleValidator;
        this.validationService = validationService;
        this.scheduledPlannedDayDtoConverter = scheduledPlannedDayDtoConverter;
    }

    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность создавать расписания"
        );

        Schedule schedule = scheduleDtoConverter.toSchedule(scheduleDto);

        if(schedule ==  null) {
            throw new BadRequestException("Данные о создаваемом расписании заполнены неверно");
        }

        schedule.setId(null);

        validationService.throwExceptionIfObjectIsInvalid(
                schedule,
                "schedule",
                scheduleValidator
        );

        if(schedule.getPlannedDay().getId() == null) {
            throw new BadRequestException("Вы не указали день, для которого хотите создать расписание");
        }

        if(!schedule.getPlannedDay().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                    String.format("Вы не имеете доступа ко дню \"%s\"", schedule.getPlannedDay().getName())
            );
        }

        scheduleRepository.saveAndFlush(schedule);

        return scheduleDtoConverter.toScheduleDto(schedule);
    }

    @Override
    public Set<ScheduledPlannedDayDto> getMonthSchedule(LocalDate date) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать своё расписание"
        );

        LocalDate prevMonthLastDay = date.withDayOfMonth(1).minusDays(1);
        LocalDate nextMonthFirstDay = date.plusMonths(1).withDayOfMonth(1);

        Set<Schedule> userSchedules = scheduleRepository.findAllByUser(user);

        Set<ScheduledPlannedDayDto> scheduledPlannedDayDtos = new HashSet<>();

        for(Schedule schedule : userSchedules) {

            LocalDate startDate = schedule.getStartDate();

            if(nextMonthFirstDay.isBefore(startDate)) {
                continue;
            }

            if(schedule.getNextRepeatAfterDays() == null) {
                if(startDate.isAfter(prevMonthLastDay) && startDate.isBefore(nextMonthFirstDay)) {
                    scheduledPlannedDayDtos.add(
                            scheduledPlannedDayDtoConverter.toScheduledPlannedDayDto(schedule, startDate)
                    );
                }
            } else {
                startDate
                        .datesUntil(nextMonthFirstDay, Period.ofDays(schedule.getNextRepeatAfterDays()))
                        .forEach(currentDate -> {
                            if(currentDate.isAfter(prevMonthLastDay)) {
                                scheduledPlannedDayDtos.add(
                                        scheduledPlannedDayDtoConverter.toScheduledPlannedDayDto(schedule, currentDate)
                                );
                            }
                        });
            }
        }

        return scheduledPlannedDayDtos;
    }

    @Override
    public ScheduleDto getScheduleById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать своё расписание"
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Расписание с id \"%s\" не найдено", id))
        );

        if(!schedule.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа к расписанию с id \"%s\"", id));
        }

        return scheduleDtoConverter.toScheduleDto(schedule);
    }

    @Override
    public void deleteScheduleById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять своё расписание"
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Расписание с id \"%s\" не найдено", id))
        );

        if(!schedule.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа к расписанию с id \"%s\"", id));
        }

        scheduleRepository.deleteById(id);
    }
}