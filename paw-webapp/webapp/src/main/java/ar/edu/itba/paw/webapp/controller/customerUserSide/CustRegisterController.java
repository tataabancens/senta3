package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ControllerService;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.CustomerRegisterForm;
import ar.edu.itba.paw.webapp.form.CustomerRegisterShortForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CustRegisterController {

    private UserService us;
    private CustomerService cs;
    private ControllerService controllerService;

    @Autowired
    public CustRegisterController(final UserService us, final CustomerService cs, final ControllerService controllerService) {
        this.cs = cs;
        this.us = us;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/registerShort/{customerId}/{reservationId}", method = RequestMethod.GET)
    public ModelAndView userRegister(@PathVariable("customerId") final String customerIdP,
                                     @PathVariable("reservationId") final String reservationIdP,
                                     @ModelAttribute("customerRegisterShortForm") final CustomerRegisterShortForm form) throws Exception {

        ModelAndView mav = new ModelAndView("CustomerRegisterShort");
        mav.addObject("customerId", customerIdP);
        mav.addObject("reservationId", reservationIdP);

        return mav;
    }

    @RequestMapping(value = "/registerShort/{customerId}/{reservationId}", method = RequestMethod.POST)
    public ModelAndView userRegister_POST(@PathVariable("customerId") final String customerIdP,
                                          @PathVariable("reservationId") final String reservationIdP,
                                          @Valid @ModelAttribute("customerRegisterShortForm") final CustomerRegisterShortForm form,
                                          final BindingResult errors) throws Exception{
        controllerService.longParser(customerIdP);
        long customerId = Long.parseLong(customerIdP);

        if (errors.hasErrors()){
            return userRegister(customerIdP, reservationIdP, form);
        }
        User user = us.create(form.getUsername(), form.getPassword(), Roles.CUSTOMER);
        cs.linkCustomerToUserId(customerId, user.getId());

        return new ModelAndView("redirect:/menu?reservationId=" +  reservationIdP);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView CustomerRegister(@ModelAttribute("customerRegisterForm") final CustomerRegisterForm form){

        return new ModelAndView("CustomerRegister");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView CustomerRegister_POST(@Valid @ModelAttribute("customerRegisterForm") final CustomerRegisterForm form,
                                              final BindingResult errors){
        if (errors.hasErrors()){
            return CustomerRegister(form);
        }
        User user = us.create(form.getUsername(), form.getPassword(), Roles.CUSTOMER);
        cs.create(form.getCustomerName(), form.getPhone(), form.getMail(), user.getId());


        return new ModelAndView("redirect:/" );
    }
}
