package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.controller.dto.RegistrationDto;
import com.cf.domain.Registration;
import com.cf.service.RegistrationService;
import com.cf.utils.DateUtils;
import com.cf.utils.ValidationDtoUtils;
import com.cf.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import java.util.List;


@Controller
@RequestMapping({"/client/registrations"})
//@RolesAllowed("ADMIN_USER")
public class RegistrationsController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationsController.class);

    @Inject
    RegistrationService registrationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<RegistrationDto> findAllForCurrentUser(@RequestParam(value = "start", required = false) final String startDate,
                                                @RequestParam(value = "end", required = false) final String endDate) {

        String userName = UserUtils.getCurrentUser().getUsername();

        List<Registration> registrationList = null;

        if (startDate == null && endDate == null) {
            registrationList = registrationService.findRegistrationsByUsername(userName);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            registrationList = registrationService.findRegistrationsByUsername(userName, dateRange.startDate, dateRange.endDate);
        }
        return ValidationDtoUtils.getDtoListFromEntityList(registrationList, RegistrationDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<RegistrationDto> findByUserId(@PathVariable("id") final long id,
                                       @RequestParam(value = "start", required = false) final String startDate,
                                       @RequestParam(value = "end", required = false) final String endDate) {

        List<Registration> registrationList = null;

        if (startDate == null && endDate == null) {
            registrationList = registrationService.findRegistrationsByUserId(id);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            registrationList = registrationService.findRegistrationsByUserId(id, dateRange.startDate, dateRange.endDate);
        }

        return ValidationDtoUtils.getDtoListFromEntityList(registrationList, RegistrationDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{viewAll}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<RegistrationDto> findAllForCurrentInstitution(@PathVariable("viewAll") final boolean viewAll,
                                                       @RequestParam(value = "start", required = false) final String startDate,
                                                       @RequestParam(value = "end", required = false) final String endDate) {
        //Validate current user
        if (UserUtils.isCurrentUserAdmin()) {
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended for a regular user (employee) not for a manager", logger, null);
            return null;
        }
        String userName = UserUtils.getCurrentUser().getUsername();
        //String userName = "bk";

        List<Registration> registrationList = null;

        if (startDate == null && endDate == null) {
            if (viewAll) {//Get registrations for current institution
                registrationList = registrationService.findRegistrationsOfUserInstitution(userName);
            } else {//Get registrations for current user
                registrationList = registrationService.findRegistrationsByUsername(userName);
            }
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            if (viewAll) {//Get registrations for current institution
                registrationList = registrationService.findRegistrationsOfUserInstitution(userName, dateRange.startDate, dateRange.endDate);
            } else {//Get registrations for current user
                registrationList = registrationService.findRegistrationsByUsername(userName, dateRange.startDate, dateRange.endDate);
            }
        }
        return ValidationDtoUtils.getDtoListFromEntityList(registrationList, RegistrationDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/subordinates", method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<RegistrationDto> findRegistrationsOfManagerSubordinates(@RequestParam(value = "start", required = false) final String startDate,
                                                                 @RequestParam(value = "end", required = false) final String endDate) {
        //Validate current user
        if (!UserUtils.isCurrentUserAdmin()) {
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended for a manager, not for regular user (employee)", logger, null);
            return null;
        }
        String managerName = UserUtils.getCurrentUser().getUsername();
        //String managerName = "kmarabet";

        List<Registration> planningList = null;
        if (startDate == null && endDate == null) {
            planningList = registrationService.findRegistrationsOfManagerSubordinates(managerName);
        } else {
            DateUtils.DateRange dateRange = DateUtils.validateAndParseDateRange(startDate, endDate, logger);
            planningList = registrationService.findRegistrationsOfManagerSubordinates(managerName, dateRange.startDate, dateRange.endDate);
        }
        return ValidationDtoUtils.getDtoListFromEntityList(planningList, RegistrationDto.class, logger);
    }


}
