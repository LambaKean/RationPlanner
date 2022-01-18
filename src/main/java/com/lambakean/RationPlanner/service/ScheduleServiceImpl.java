package com.lambakean.RationPlanner.service;

import com.lambakean.RationPlanner.dto.ScheduleDto;
import com.lambakean.RationPlanner.dto.converter.ScheduleDtoConverter;
import com.lambakean.RationPlanner.exception.AccessDeniedException;
import com.lambakean.RationPlanner.exception.BadRequestException;
import com.lambakean.RationPlanner.model.Schedule;
import com.lambakean.RationPlanner.model.User;
import com.lambakean.RationPlanner.repository.ScheduleRepository;
import com.lambakean.RationPlanner.validator.ScheduleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceImpl implements ScheduleService {

    private final PrincipalService principalService;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDtoConverter scheduleDtoConverter;
    private final ScheduleValidator scheduleValidator;
    private final ValidationService validationService;

    @Autowired
    public ScheduleServiceImpl(PrincipalService principalService,
                               ScheduleRepository scheduleRepository,
                               ScheduleDtoConverter scheduleDtoConverter,
                               ScheduleValidator scheduleValidator,
                               ValidationService validationService) {
        this.principalService = principalService;
        this.scheduleRepository = scheduleRepository;
        this.scheduleDtoConverter = scheduleDtoConverter;
        this.scheduleValidator = scheduleValidator;
        this.validationService = validationService;
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
}
