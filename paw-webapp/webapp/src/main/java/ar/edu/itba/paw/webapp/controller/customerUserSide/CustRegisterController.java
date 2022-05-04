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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CustRegisterController {

    private UserService us;
    private CustomerService cs;
    private ControllerService controllerService;

    @Autowired
    protected AuthenticationManager authenticationManager;


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
                                          final BindingResult errors,
                                          HttpServletRequest request) throws Exception{
        controllerService.longParser(customerIdP);
        long customerId = Long.parseLong(customerIdP);

        if (errors.hasErrors()){
            return userRegister(customerIdP, reservationIdP, form);
        }
        User user = us.create(form.getUsername(), form.getPassword(), Roles.CUSTOMER);
        cs.linkCustomerToUserId(customerId, user.getId());

        authenticateUserAndSetSession(form.getUsername(), form.getPassword(), request, authenticationManager);

        return new ModelAndView("redirect:/menu?reservationId=" +  reservationIdP);
    }



    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView CustomerRegister(@ModelAttribute("customerRegisterForm") final CustomerRegisterForm form){

        return new ModelAndView("CustomerRegister");
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView Profile(Principal principal){


        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("username", principal.getName());

        return mav;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView CustomerRegister_POST(@Valid @ModelAttribute("customerRegisterForm") final CustomerRegisterForm form,
                                              final BindingResult errors,
                                              HttpServletRequest request){
        if (errors.hasErrors()){
            return CustomerRegister(form);
        }
        User user = us.create(form.getUsername(), form.getPassword(), Roles.CUSTOMER);
        Customer customer = cs.create(form.getCustomerName(), form.getPhone(), form.getMail(), user.getId());

        cs.linkCustomerToUserId(customer.getCustomerId(), user.getId());

        authenticateUserAndSetSession(form.getUsername(), form.getPassword(), request, authenticationManager);

        return new ModelAndView("redirect:/" );
    }

    public void authenticateUserAndSetSession(String username, String password, HttpServletRequest request, AuthenticationManager authenticationManager) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}