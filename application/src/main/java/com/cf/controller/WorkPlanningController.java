package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.controller.dto.RepeatWorkPlanningDto;
import com.cf.controller.dto.WorkPlanningDto;
import com.cf.domain.User;
import com.cf.domain.WorkPlanning;
import com.cf.domain.Planning;
import com.cf.service.PlanningService;
import com.cf.service.UserService;
import com.cf.service.WorkPlanningService;
import com.cf.utils.DateUtils;
import com.cf.utils.ValidationDtoUtils;
import com.cf.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping({"/client/workplannings"})

public class WorkPlanningController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(WorkPlanningController.class);

    @Inject
    WorkPlanningService workplanService;
    @Inject
    PlanningService planningService;
    @Inject
    UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<WorkPlanningDto> findCurrentUserWorkPlannings() {

        String userName = UserUtils.getCurrentUser().getUsername();

        List<WorkPlanning> worPlanningList = workplanService.findByUsername(userName);

        return ValidationDtoUtils.getDtoListFromEntityList(worPlanningList, WorkPlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<WorkPlanningDto> findUserWorkPlanningsByUserId(@PathVariable("id") final long userId) {

        List<WorkPlanning> worPlanningList = workplanService.findByUserId(userId);

        return ValidationDtoUtils.getDtoListFromEntityList(worPlanningList, WorkPlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/manager", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<WorkPlanningDto> findPlanningsOfManagerSubordinatesByManagerId() {
        if (UserUtils.isCurrentUserAdmin()) {
            User user = userService.findByUsername(UserUtils.getCurrentUser().getUsername());
            List<WorkPlanning> planningList = workplanService.findOfManagerSubordinatesByManagerId(user.getId());
            return ValidationDtoUtils.getDtoListFromEntityList(planningList, WorkPlanningDto.class, logger);
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public List<WorkPlanningDto> saveUpdateUserWorkPlanningByUserId(@PathVariable("id") final long userId,
                                                    @RequestBody final WorkPlanningDto[] workPlanDtos) {

        List<WorkPlanning> workPlanListForSave = ValidationDtoUtils.getEntityListFromDtoList(Arrays.asList(workPlanDtos), logger);

        workplanService.updateByUserId(userId, workPlanListForSave);

        return ValidationDtoUtils.getDtoListFromEntityList(workPlanListForSave, WorkPlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/copy/{id}", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public void copyUpdateUserWorkPlanning(@PathVariable("id") final long userId, @RequestBody final Long[] userIds) {

        List<WorkPlanning> sourceList = workplanService.findByUserId(userId);

        for (Long toUserId : userIds) {
            for (WorkPlanning wp : sourceList) {
                wp.setUserId(toUserId);
                wp.setId(null);
            }
            workplanService.save(sourceList);
        }
    }

    /*@ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public List<WorkPlanningDto> saveUpdateCurrentUserWorkPlanning(@RequestBody final WorkPlanningDto[] workPlanDtos) {
        List<WorkPlanning> workPlanListForSave = TranslateValidateDtoUtils.getEntityListFromDtoList(Arrays.asList(workPlanDtos), logger);
        String currentUsername = UserUtils.getCurrentUser().getUsername();
        //String currentUsername = "kmarabet";
        workplanService.updateByUsername(currentUsername, workPlanListForSave);
        return TranslateValidateDtoUtils.getDtoListFromEntityList(workPlanListForSave, WorkPlanningDto.class, logger);
    }*/

    @ResponseBody
    @RequestMapping(value = "/repeat/{id}", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public void applyWorkPlanningsByUserId(@PathVariable("id") final long userId,
                                           @RequestBody final RepeatWorkPlanningDto repeatDto) {

        Date startDate = DateUtils.validateParseDate(repeatDto.getStartsDate(), null, logger);


        List<WorkPlanning> workPlannings = workplanService.findByUserId(userId);

        if (workPlannings.size() == 0){
            ValidationDtoUtils.logAndThrowControllerException("The work planning is empty for the current user, should save the work planning before run the repeat process.", logger, null);
            return;
        }
        //Validate RepeatWorkPlanningDto
        Date endDate = null;
        switch (repeatDto.getEndType()){
            case END:
                repeatDto.setEndDate(repeatDto.getValue());
                endDate = DateUtils.validateParseDate(repeatDto.getEndDate(), null, logger);
                break;
            case AFTER:
                endDate = null;
                repeatDto.setOccurrences(Integer.decode(repeatDto.getValue()));
                if (repeatDto.getOccurrences() == null || repeatDto.getOccurrences() <= 0){
                    ValidationDtoUtils.logAndThrowControllerException("Not valid occurrences parameter: " + repeatDto.getOccurrences(), logger, null);
                }
                break;
            case NEVER: endDate = DateUtils.addOneYear(startDate);
                break;
            default: ValidationDtoUtils.logAndThrowControllerException("Not valid end type: " + repeatDto.getEndType(), logger, null);
        }

        planningService.applyWorkPlannings(userId, workPlannings, startDate, endDate, repeatDto.getOccurrences());
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MimeTypes.JSON)
    public void applyUpdateWorkPlanningsForCurrentUser(@PathVariable("id") final long userId,
                                                       @RequestBody final String updateFrom) {

        List<WorkPlanning> workPlannings = workplanService.findByUserId(userId);
        if (workPlannings.size() == 0){
            ValidationDtoUtils.logAndThrowControllerException("The work planning is empty for the current user, should save the work planning before run the repeat process.", logger, null);
            return;
        }

        Date updateFromDate = DateUtils.validateParseDate(updateFrom, null, logger);

        List<Planning> plannings = planningService.findPlanningsByUserId(userId, updateFromDate);
        if (plannings.size() == 0){
            ValidationDtoUtils.logAndThrowControllerException("There's no plannings to be updated from" +updateFromDate+ "date, for the user.id= "+userId, logger, null);
            return;
        }
        planningService.applyWorkPlanningsFromDate(userId, workPlannings, updateFromDate);
    }

    @ResponseBody
    @RequestMapping(value = "/clean/{id}", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void cleanWorkPlanningsByUserId(@PathVariable("id") final long userId,
                                           @RequestBody final String cleanFrom
                                           ) {

        Date cleanFromDate = DateUtils.validateParseDate(cleanFrom, null, logger);

        planningService.cleanWorkPlanningsFromDate(userId, cleanFromDate);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void deleteWorkPlanningsByUserId(@PathVariable("id") final long userId) {
        workplanService.deleteByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void deleteWorkPlanningsOfCurrentUser() {
        String currentUsername = UserUtils.getCurrentUser().getUsername();
        workplanService.deleteByUsername(currentUsername);
    }

}
