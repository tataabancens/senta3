package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.form.EditEmailForm;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import ar.edu.itba.paw.webapp.form.EditPhoneForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CustProfileController {
    private final UserService us;
    private final CustomerService cs;

    @Autowired
    public CustProfileController(final UserService us, final CustomerService cs) {
        this.cs = cs;
        this.us = us;
    }


}
