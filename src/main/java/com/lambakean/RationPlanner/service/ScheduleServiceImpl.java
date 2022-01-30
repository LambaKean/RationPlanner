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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<ScheduledPlannedDayDto> getMonthSchedule(LocalDate date) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать своё расписание"
        );

        Set<Schedule> userSchedules = scheduleRepository.findAllByUser(user);

        List<ScheduledPlannedDayDto> scheduledPlannedDayDtos = new ArrayList<>();
        for(Schedule schedule : userSchedules) {

            LocalDate currentDate = schedule.getStartDate();

            while (currentDate.getYear() == date.getYear() && currentDate.getMonth() == date.getMonth()) {
                scheduledPlannedDayDtos.add(
                        scheduledPlannedDayDtoConverter.toScheduledPlannedDayDto(schedule, currentDate)
                );

                currentDate = currentDate.plusDays(Optional.of(schedule.getNextRepeatAfterDays()).orElse(31));
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