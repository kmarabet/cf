package com.cf.controller;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.controller.dto.UserDto;
import com.cf.domain.Institution;
import com.cf.domain.User;
import com.cf.service.InstitutionService;
import com.cf.service.UserService;
import com.cf.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Lazy
@RequestMapping("/client/settings")
public class SettingsController extends GenericController{

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @ResponseBody
    @RequestMapping(value="/system", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public void saveSystemSettings(@RequestBody final Institution institution) {
        if (institution.getPreference() != null && !institution.getPreference().isEmpty()) {
            try {
                new JsonParser().parse(institution.getPreference());
                User user = userService.findByUsername(UserUtils.getCurrentUser().getUsername());

                Institution institutionBean = institutionService.findByUserId(user.getInstitutionId());

                institutionBean.setPreference(institution.getPreference());

                institutionService.save(institutionBean);
            } catch (JsonParseException e) {
                LOGGER.error("trying to save settings in invalid JSON format");
            }
        }
    }

    @ResponseBody
    @RequestMapping(value="/system", method = RequestMethod.GET, produces = MimeTypes.JSON)
    public String getSystemSettings() {
        User user = userService.findByUsername(UserUtils.getCurrentUser().getUsername());

        Institution institutionBean = institutionService.findByUserId(user.getInstitutionId());

        return institutionBean.getPreference();
    }

    @ResponseBody
    @RequestMapping(value="/user", method = RequestMethod.POST, produces = MimeTypes.JSON)
    public void saveUserSettings(@RequestBody final UserDto userDto) {
        User oldUser = userService.findById(userDto.getId());

        oldUser.setNormTargetTime(userDto.getNormTargetTime());
        oldUser.setEmployeeTypeId(userDto.getEmployeeTypeId());
        oldUser.setColor(userDto.getColor());
        oldUser.setOverUnderTime(userDto.getOverUnderTime());

        userService.save(oldUser);
    }

    @ResponseBody
    @RequestMapping(value="/user/{id}", method = RequestMethod.GET, produces = MimeTypes.JSON)
    public UserDto getUserSettings(@PathVariable final Long userId) {
        return new UserDto(userService.findById(userId));
    }
}
