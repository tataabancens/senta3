package ar.edu.itba.paw.webapp.controller.utilities;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("/error404")
    public ModelAndView error404() {

        return new ModelAndView("error/error404");
    }


    @RequestMapping("/error500")
    public ModelAndView error500() {

        return new ModelAndView("error/error500");
    }

    @RequestMapping("/403")
    public ModelAndView accessDenied() {
        return new ModelAndView("error/403");
    }
}
