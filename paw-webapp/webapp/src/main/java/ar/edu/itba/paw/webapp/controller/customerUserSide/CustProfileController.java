package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.service.ControllerService;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CustProfileController {
    private UserService us;
    private CustomerService cs;
    private ControllerService controllerService;

    @Autowired
    public CustProfileController(final UserService us, final CustomerService cs, final ControllerService controllerService) {
        this.cs = cs;
        this.us = us;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(Principal principal){
        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("custProfile/CustomerProfile");
        mav.addObject("username", username);
        mav.addObject("customer", customer);

        return mav;
    }

    @RequestMapping(value = "/profile/editName", method = RequestMethod.GET)
    public ModelAndView profileEditName(Principal principal,
                                        @ModelAttribute("editNameForm") final EditNameForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("custProfile/editCustomerName");
        mav.addObject("username", username);
        form.setName(customer.getCustomerName());

        return mav;
    }

    @RequestMapping(value = "/profile/editName", method = RequestMethod.POST)
    public ModelAndView profileEditNamePost(Principal principal,
                                            @ModelAttribute("editNameForm") final EditNameForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditName(principal, form);
        }

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);
        cs.updateCustomerData(customer.getCustomerId(), form.getName(), customer.getPhone(), customer.getMail());

        return new ModelAndView("redirect:/profile");
    }

}
