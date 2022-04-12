package com.lambakean.RationPlanner.service.impl;

import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.EntityNotFoundException;
import com.lambakean.RationPlanner.model.PlannedDay;
import com.lambakean.RationPlanner.model.Schedule;
import com.lambakean.RationPlanner.model.ScheduledPlannedDay;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.PlannedDayRepository;
import com.lambakean.RationPlanner.repository.ScheduleRepository;
import com.lambakean.RationPlanner.service.PrincipalService;
import com.lambakean.RationPlanner.service.ScheduleService;
import com.lambakean.RationPlanner.service.ValidationService;
import com.lambakean.RationPlanner.validator.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final PrincipalService principalService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleValidator scheduleValidator;
    private final ValidationService validationService;
    private final PlannedDayRepository plannedDayRepository;

    @Autowired
    public ScheduleServiceImpl(PrincipalService principalService,
                               ScheduleRepository scheduleRepository,
                               ScheduleValidator scheduleValidator,
                               ValidationService validationService,
                               PlannedDayRepository plannedDayRepository) {
        this.principalService = principalService;
        this.scheduleRepository = scheduleRepository;
        this.scheduleValidator = scheduleValidator;
        this.validationService = validationService;
        this.plannedDayRepository = plannedDayRepository;
    }

    @Override
    public Schedule createSchedule(Schedule scheduleData) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность создавать расписание"
        );

        validationService.validateThrowExceptionIfInvalid(scheduleData, scheduleValidator);

        PlannedDay loadedPlannedDayFromDb = plannedDayRepository.getById(scheduleData.getPlannedDayId());

        if(!user.getId().equals(loadedPlannedDayFromDb.getUserId())) {
            throw new AccessDeniedException(
                    String.format("Вы не имеете доступа ко дню c id [%s]", scheduleData.getPlannedDayId())
            );
        }

        scheduleRepository.saveAndFlush(scheduleData);

        scheduleData.setPlannedDay(loadedPlannedDayFromDb);

        return scheduleData;
    }

    @Override
    public List<ScheduledPlannedDay> getMonthSchedule(LocalDate date) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать своё расписание"
        );

        Set<Schedule> userSchedules = scheduleRepository.findAllByUser(user);

        List<ScheduledPlannedDay> scheduledPlannedDays = new ArrayList<>();

        for(Schedule schedule : userSchedules) {

            LocalDate currentDate = schedule.getStartDate();

            // пока очередная дата не вышла за пределы месяца, на который нужно сгенерировать расписание
            while(currentDate.getYear() < date.getYear()
                    || currentDate.getYear() == date.getYear() && currentDate.getMonthValue() <= date.getMonthValue()) {

                // если очередная дата входит в этот месяц, то сохраняем её в список
                if(currentDate.getYear() == date.getYear() && currentDate.getMonthValue() == date.getMonthValue()) {
                    scheduledPlannedDays.add(
                            new ScheduledPlannedDay(
                                schedule, schedule.getPlannedDay(), currentDate
                            )
                    );
                }

                int lengthOfCurrentMonth = YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth();
                currentDate = currentDate.plusDays(
                        Optional.ofNullable(schedule.getNextRepeatAfterDays()).orElse(lengthOfCurrentMonth)
                );
            }

        }

        return scheduledPlannedDays;
    }

    @Override
    public Schedule getScheduleById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность просматривать своё расписание"
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Расписание с id [%s] не найдено", id))
        );

        if(!schedule.getPlannedDay().getUserId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа к расписанию с id [%s]", id));
        }

        return schedule;
    }

    @Override
    public void deleteScheduleById(String id) {

        User user = (User) principalService.getPrincipalOrElseThrowException(
                "Вы должны войти в аккаунт, чтобы иметь возможность удалять своё расписание"
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Расписание с id [%s] не найдено", id))
        );

        if(!schedule.getPlannedDay().getUserId().equals(user.getId())) {
            throw new AccessDeniedException(String.format("Вы не имеете доступа к расписанию с id [%s]", id));
        }

        scheduleRepository.deleteById(id);
    }
}