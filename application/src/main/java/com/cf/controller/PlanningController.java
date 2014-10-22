package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.controller.dto.PlanningDto;
import com.cf.domain.Planning;
import com.cf.service.PlanningService;
import com.cf.utils.DateUtils;
import com.cf.utils.ValidationDtoUtils;
import com.cf.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping({"/client/plannings"})
public class PlanningController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(PlanningController.class);

    @Inject
    PlanningService planningService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<PlanningDto> findCurrentUserPlannings(@RequestParam(value = "start", required = false) final String startDate,
                                               @RequestParam(value = "end", required = false) final String endDate) {

        List<PlanningDto> planDtoList = new ArrayList<>();

        String userName = UserUtils.getCurrentUser().getUsername();

        List<Planning> planningList = null;

        if (startDate == null && endDate == null) {
            planningList = planningService.findPlanningsByUsername(userName);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            planningList = planningService.findPlanningsByUsername(userName, dateRange.startDate, dateRange.endDate);
        }
        planDtoList.addAll(ValidationDtoUtils.getDtoListFromEntityList(planningList, PlanningDto.class, logger));

        return planDtoList;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<PlanningDto> findUserPlanningsByUserId(@PathVariable("id") final long id,
                                   @RequestParam(value = "start", required = false) final String startDate,
                                   @RequestParam(value = "end", required = false) final String endDate) {

        List<Planning> planningList = null;
        if (startDate == null && endDate == null) {
            planningList = planningService.findPlanningsByUserId(id);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            planningList = planningService.findPlanningsByUserId(id, dateRange.startDate, dateRange.endDate);
        }

        return ValidationDtoUtils.getDtoListFromEntityList(planningList, PlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/hasPlanning/{id}", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public Boolean checkIfUserHasPlanningsFromDate(@PathVariable("id") final long userId,
                                                @RequestBody final String dateFrom) {

        Date dateFromDate = DateUtils.validateParseDate(dateFrom, null, logger);
        //Date dateFromDate = DateUtils.validateParseDate("01/11/2013", null, logger);

        int countPlannings = planningService.countPlanningsByUserId(userId, dateFromDate);

        return countPlannings != 0;
    }

    @ResponseBody
    @RequestMapping(value = "/all/{viewAll}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<PlanningDto> findAllForCurrentInstitution(@PathVariable("viewAll") final boolean viewAll,
                                                   @RequestParam(value = "start", required = false) final String startDate,
                                                   @RequestParam(value = "end", required = false) final String endDate) {
        //Validate current user
        if (UserUtils.isCurrentUserAdmin()) {
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended for a regular user (employee) not for a manager", logger, null);
            return null;
        }
        String userName = UserUtils.getCurrentUser().getUsername();
        //String userName = "bk";

        List<Planning> registrationList = null;

        if (startDate == null && endDate == null) {
            if (viewAll) {//Get plannings for current institution
                registrationList = planningService.findPlanningsOfUserInstitution(userName);
            } else {//Get plannings for current user
                registrationList = planningService.findPlanningsByUsername(userName);
            }
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            if (viewAll) {//Get plannings for current institution
                registrationList = planningService.findPlanningsOfUserInstitution(userName, dateRange.startDate, dateRange.endDate);
            } else {//Get plannings for current user
                registrationList = planningService.findPlanningsByUsername(userName, dateRange.startDate, dateRange.endDate);
            }
        }
        return ValidationDtoUtils.getDtoListFromEntityList(registrationList, PlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/subordinates", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<PlanningDto> findPlanningsOfManagerSubordinates(@RequestParam(value = "start", required = false) final String startDate,
                                                         @RequestParam(value = "end", required = false) final String endDate) {

        if (!UserUtils.isCurrentUserAdmin()) {
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended for a manager, not for regular user (employee)", logger, null);
            return null;
        }
        String managerName = UserUtils.getCurrentUser().getUsername();
        //String managerName = "kmarabet";

        List<Planning> planningList = null;
        if (startDate == null && endDate == null) {
            planningList = planningService.findPlanningsOfManagerSubordinates(managerName);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            planningList = planningService.findPlanningsOfManagerSubordinates(managerName, dateRange.startDate, dateRange.endDate);
        }
        return ValidationDtoUtils.getDtoListFromEntityList(planningList, PlanningDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public PlanningDto saveUpdateCorrections(@RequestBody final PlanningDto planningDto) {

        if (planningDto.isCorrected() == null || !planningDto.isCorrected() ){
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended only to save/update corrections. Plannings are created through /client/workplannings/repeat service url", logger, null);
            return null;
        }

        Planning planning = ValidationDtoUtils.validateDtoAndGetEntity(planningDto, logger);

        if (planning.isNew()) { //CREATE
            planningService.save(planning);
            logger.info("Planning.id: "+planning.getId()+" saved");
            return new PlanningDto(planning);
        } else { //UPDATE
            boolean updated = planningService.update(planning);
            if (updated) {
                logger.info("Planning.id: "+planning.getId()+" saved");
                return new PlanningDto(planning);//or return planningDto;
            } else {
                ValidationDtoUtils.logAndThrowControllerException("Unable to update the planning.id: " + planning.getId(), logger, null);
                return null;
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveList", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public List<PlanningDto> updatePlanning(@RequestBody final PlanningDto[] planDtos) {

        List<PlanningDto> updatedPlanDtos = new ArrayList<>();
        Planning planning;
        for (final PlanningDto planDto : planDtos) {

            planning = ValidationDtoUtils.validateDtoAndGetEntity(planDto, logger);

            if (planning.isNew()) { //CREATE
                planningService.save(planning);
                updatedPlanDtos.add(new PlanningDto(planning));

            } else { //UPDATE
                boolean updated = planningService.update(planning);
                if (updated) {
                    updatedPlanDtos.add(planDto);
                } else {
                    PlanningDto errorDto = new PlanningDto();
                    errorDto.setErrorMessage("Unable to update the planning.id: " + planDto.getId());
                    updatedPlanDtos.add(errorDto);
                }
            }
        }
        if (planDtos.length > 0 && updatedPlanDtos.isEmpty()) {
            ValidationDtoUtils.logAndThrowControllerException("Unable to create/update any planning", logger, null);
        }
        /*if (updatedPlanDtos.size() < planDtos.length){
            logger.error("Could not create/update all plannings");
            throw new TaControllerException(HttpServletResponse.SC_BAD_REQUEST, "Unable to create/update all plannings");
        }*/
        return updatedPlanDtos;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void deletePlanning(@PathVariable("id") final long id/*, @RequestBody ActivationDto request*/) {
        //todo user activation
        /*ActivationDto activation = mobileService.getActivation(request.getDeviceId(), request.getToken(), false);
        if (null == activation) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }*/

        /*if (planningDto.isCorrected() == null || !planningDto.isCorrected() ){
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended only to delete corrections. Plannings are deleted through /client/workplannings/clean service url", logger, null);
            return null;
        }*/
        if (planningService.exists(id)) {
            planningService.delete(id);
            logger.info("");

        } else {
            ValidationDtoUtils.logAndThrowControllerException("Unable to delete planning.id= " + id, logger, null);
        }
    }

    /*@ResponseBody
    @RequestMapping(value = "/deleteList", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void deletePlanning(@RequestBody final long[] ids) {

        int countDeletedIds = 0;

        for (long id : ids) {
            if (planningService.exists(id)) {
                planningService.delete(id);
                countDeletedIds++;
            } else {
                ValidationDtoUtils.logAndThrowControllerException("Unable to delete planning.id= " + id, logger, null);
            }
        }
        if (ids.length != 0 && countDeletedIds == 0) {
            ValidationDtoUtils.logAndThrowControllerException("Unable to delete any planning", logger, null);
        }
        if (countDeletedIds < ids.length) {
            ValidationDtoUtils.logAndThrowControllerException("Unable to delete all plannings", logger, null);
        }
    }*/

}
