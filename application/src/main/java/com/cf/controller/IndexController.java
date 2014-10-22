package com.cf.controller;

import com.google.gson.Gson;
import com.cf.domain.User;
import com.cf.service.UserService;
import com.cf.utils.CustomMessageSource;
import com.cf.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
@Lazy
public class IndexController {

    @Autowired
    private CustomMessageSource messageSource;

    @Autowired
    private UserService userService;

    enum PageLabels {
        LOGIN("login"),
        MANAGER_DASHBOARD("m.d"),
        WORK_PLANNING("m.p"),
        USER_DASHBOARD("u.d"),
        COMMON("common"),
        MANAGER_COMMON("m.c");

        private String label;
        PageLabels(String label) {
            this.label = label;
        }
    }

    @RequestMapping({"/", "/login"})
    public String login(Model model) {
        fillProperties(model, PageLabels.LOGIN);
        return "login";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        if (UserUtils.isCurrentUserAdmin()){
            fillModel(model, PageLabels.MANAGER_DASHBOARD, PageLabels.COMMON, PageLabels.MANAGER_COMMON);
            return "manager/dashboard";
        }
        fillModel(model, PageLabels.USER_DASHBOARD, PageLabels.COMMON);
        return "user/dashboard";
    }

    @RequestMapping("/planning")
    public String workPlanning(Model model) {
        fillModel(model, PageLabels.WORK_PLANNING, PageLabels.COMMON, PageLabels.MANAGER_COMMON);
        return "manager/work_planning";
    }

    void fillModel(Model model, PageLabels... labelTags) {
        fillProperties(model, labelTags);
        User user = userService.findByUsername(UserUtils.getCurrentUser().getUsername());
        model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
    }

    void fillProperties(Model model, PageLabels... labelTags) {
        List<String> labels = new ArrayList<>(labelTags.length);
        for (PageLabels pageLabel : labelTags) {
            labels.add(pageLabel.label);
        }
        model.addAttribute("localization", new Gson().toJson(messageSource.getPropertiesByTags(labels)));
        model.addAttribute("locale", Locale.getDefault());
    }
}
