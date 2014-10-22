package com.cf.controller;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.domain.CorrectionCause;
import com.cf.service.CorrectionCauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping({"/client/correctioncause"})
public class CorrectionCauseController extends GenericController {

    @Autowired
    CorrectionCauseService service;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MimeTypes.JSON)
    Iterable<CorrectionCause> getAllCauses() {
        return service.findAllActive();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MimeTypes.JSON)
    CorrectionCause addCause(@RequestBody String causeName) {
        return service.save(new CorrectionCause(null, causeName));
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MimeTypes.JSON)
    void removeCause(@PathVariable Long id) {
        service.delete(id);
    }

}
