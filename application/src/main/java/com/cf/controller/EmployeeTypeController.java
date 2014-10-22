package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.controller.dto.EmployeeTypeDto;
import com.cf.domain.EmployeeType;
import com.cf.domain.WorkException;
import com.cf.service.EmployeeTypeService;
import com.cf.utils.ValidationDtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping({"/client/employeeTypes"})
public class EmployeeTypeController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeTypeController.class);

    @Inject
    EmployeeTypeService employeeTypeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MimeTypes.JSON)
    List<EmployeeTypeDto> findAll() {

        List<EmployeeType> employeeTypeList = (List<EmployeeType>)employeeTypeService.findAll();

        List<EmployeeTypeDto> employeeTypeDtos = ValidationDtoUtils.getDtoListFromEntityList(employeeTypeList, EmployeeTypeDto.class, logger);

        return employeeTypeDtos;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    EmployeeType findById(@PathVariable("id") final long id) {

        return employeeTypeService.findById(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MimeTypes.JSON)
    EmployeeTypeDto saveUpdate(@RequestBody final EmployeeTypeDto employeeTypeDto) throws ParseException {
        EmployeeType employeeType = new EmployeeType(employeeTypeDto);

        if (employeeType.getWorkExceptions() != null && !employeeType.getWorkExceptions().isEmpty()){
            //Validating employeeType's workExceptions
            for (WorkException we: employeeType.getWorkExceptions()){
                if (!we.isTimeInOutValid()){
                    ValidationDtoUtils.logAndThrowControllerException("Not valid times: " + we.getStartTime() + " is after " + we.getEndTime(), logger, null);
                    return null;
                }
                we.setEmployeeType(employeeType);
            }
        }

        if (employeeType.isNew()) { //CREATE
            employeeTypeService.save(employeeType);
            return new EmployeeTypeDto(employeeType);
        } else { //UPDATE
            boolean updated = employeeTypeService.update(employeeType);
            if (updated) {
                return new EmployeeTypeDto(employeeType);//or return planningDto;
            } else {
                ValidationDtoUtils.logAndThrowControllerException("Unable to update the employeeType.id: " + employeeType.getId(), logger, null);
                return null;
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    public void deleteById(@PathVariable("id") final long id) {

        if (employeeTypeService.exists(id)) {
            employeeTypeService.delete(id);

        } else {
            ValidationDtoUtils.logAndThrowControllerException("Unable to delete employeeType.id= " + id, logger, null);
        }
    }

}
