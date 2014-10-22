package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.domain.BaseTimeRegistration;
import com.cf.domain.Planning;
import com.cf.domain.Registration;
import com.cf.domain.User;
import com.cf.service.InstitutionService;
import com.cf.service.PlanningService;
import com.cf.service.RegistrationService;
import com.cf.service.UserService;
import com.cf.controller.dto.UserDto;
import com.cf.utils.ValidationDtoUtils;
import com.cf.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;


@Controller
@RequestMapping({"/client/users"})
public class UsersController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Inject
    UserService userService;
    @Inject
    InstitutionService institutionService;
    @Inject
    PlanningService planningService;
    @Inject
    RegistrationService registrationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,  produces = MimeTypes.JSON)
    List<UserDto> findAll() {

        Iterable<User> allUsers = userService.findAll();

        return ValidationDtoUtils.getDtoListFromEntityList(allUsers, UserDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/current", method = RequestMethod.GET,  produces = MimeTypes.JSON)
    UserDto getCurrentUser() {

        User currentUser = userService.findByUsername(UserUtils.getCurrentUser().getUsername());

        return new UserDto(currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,  produces = MimeTypes.JSON)
    UserDto findByUserId(@PathVariable("id") long id) {

        UserDto userDto = new UserDto(userService.findById(id));

        return userDto;
    }

    /**
     * Retrieves the list of subordinates (employees) of the current (logged in) user if he/she is a manager
     * otherwise an empty list is returned
     */
    @ResponseBody
    @RequestMapping(value = "/subordinates", method = RequestMethod.GET,  produces = MimeTypes.JSON)
    List<UserDto> findSubordinatesByManagerId() {

        String userName = UserUtils.getCurrentUser().getUsername();

        Iterable<User> users = userService.findManagersSubodinates(userName);

        List<UserDto> userDtoList = ValidationDtoUtils.getDtoListFromEntityList(users, UserDto.class, logger);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);

        Date today = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

        Date tomorrow = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 0);

        Date startYear = new Date(calendar.getTime().getTime());

        for (UserDto userDto : userDtoList) {
            List<Planning> corrections = planningService.findCorrectionsByUserId(userDto.getId(), startYear, tomorrow);
            List<Registration> registrations = registrationService.findRegistrationsByUserId(userDto.getId(), startYear, tomorrow);
            List<Planning> plannings = planningService.findPlanningsByUserId(userDto.getId(), today, tomorrow);

            userDto.setReportedTime(sumCorrectedTime(corrections) + sumRegisteredTime(registrations) + sumCorrectedTime(plannings));
        }
        return userDtoList;
    }

    private Float sumCorrectedTime(List<Planning> events) {
        Float sum = 0f;
        for (BaseTimeRegistration event : events) {
            sum += calculateTime(event.getTimeIn(), event.getTimeOut());
        }
        return sum;
    }

    private Float sumRegisteredTime(List<Registration> events) {
        Float sum = 0f;
        for (BaseTimeRegistration event : events) {
            sum += calculateTime(event.getTimeIn(), event.getTimeOut());
        }
        return sum;
    }

    private Float calculateTime(Time in, Time out) {
        return new Float((out.getTime() - in.getTime()) / 1000 / 60);
    }

    /**
     * Retrieves the list of subordinates (employees) of the user by its ID if he/she is a manager
     * otherwise an empty list is returned
     */
    @ResponseBody
    @RequestMapping(value = "/subordinates/{id}", method = RequestMethod.GET,  produces = MimeTypes.JSON)
    List<UserDto> findSubordinatesByManagerId(@PathVariable("id") long managerId) {

        Iterable<User> users = userService.findManagersSubodinates(managerId);

        return ValidationDtoUtils.getDtoListFromEntityList(users, UserDto.class, logger);
    }

    @ResponseBody
    @RequestMapping(value = "/colleagues", method = RequestMethod.GET,  produces = MimeTypes.JSON)
    List<UserDto> findEmployeeColleagues() {

        //Validate current user
        if (UserUtils.isCurrentUserAdmin()){
            ValidationDtoUtils.logAndThrowControllerException("This web service is intended for a regular user (employee) not for a manager", logger, null);
            return null;
        }
        List<User> userDtoList = userService.findUserColleagues(UserUtils.getCurrentUser().getUsername());

        return ValidationDtoUtils.getDtoListFromEntityList(userDtoList, UserDto.class, logger);
    }

}
