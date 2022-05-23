package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
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

@Controller
public class CustRegisterController {

    private final UserService us;
    private final CustomerService cs;

    @Autowired
    protected AuthenticationManager authenticationManager;


    @Autowired
    public CustRegisterController(final UserService us, final CustomerService cs) {
        this.cs = cs;
        this.us = us;
    }

    @RequestMapping(value = "/registerShort/{customerId}/{reservationId}", method = RequestMethod.GET)
    public ModelAndView userRegister(@PathVariable("customerId") final String customerIdP,
                                     @PathVariable("reservationId") final String reservationIdP,
                                     @ModelAttribute("customerRegisterShortForm") final CustomerRegisterShortForm form) throws Exception {

        ControllerUtils.longParser(customerIdP).orElseThrow(() -> new LongParseException(customerIdP));
        Customer customer = cs.getCustomerById(Integer.parseInt(customerIdP)).orElseThrow(CustomerNotFoundException::new);

        form.setMail(customer.getMail());

        ModelAndView mav = new ModelAndView("customerViews/CustomerRegisterShort");
        mav.addObject("customerId", customerIdP);
        mav.addObject("reservationId", reservationIdP);

        return mav;
    }

    @RequestMapping(value = "/registerShort/{customerId}/{reservationId}", method = RequestMethod.POST)
    public ModelAndView userRegister_POST(@PathVariable("customerId") final String customerIdP,
                                          @PathVariable("reservationId") final String reservationIdP,
                                          @Valid @ModelAttribute("customerRegisterShortForm") final CustomerRegisterShortForm form,
                                          final BindingResult errors,
                                          final HttpServletRequest request) throws Exception{
        ControllerUtils.longParser(customerIdP).orElseThrow(() -> new LongParseException(customerIdP));
        long customerId = Long.parseLong(customerIdP);

        if (errors.hasErrors()){
            return userRegister(customerIdP, reservationIdP, form);
        }
        User user = us.create(form.getUsername(), form.getPsPair().getPassword(), Roles.CUSTOMER);
        Customer customer = cs.getCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        cs.linkCustomerToUserId(customer, user);

        authenticateUserAndSetSession(form.getUsername(), form.getPsPair().getPassword(), request, authenticationManager);

        return new ModelAndView("redirect:/menu?reservationId=" +  reservationIdP);
    }



    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView CustomerRegister(@ModelAttribute("customerRegisterForm") final CustomerRegisterForm form){

        return new ModelAndView("customerViews/CustomerRegister");
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView CustomerRegister_POST(@Valid @ModelAttribute("customerRegisterForm") final CustomerRegisterForm form,
                                              final BindingResult errors,
                                              final HttpServletRequest request){
        if (errors.hasErrors()){
            return CustomerRegister(form);
        }
        User user = us.create(form.getUsername(), form.getPsPair().getPassword(), Roles.CUSTOMER);
        Customer customer = cs.create(form.getCustomerName(), form.getPhone(), form.getMail(), user.getId());

        cs.linkCustomerToUserId(customer, user);

        authenticateUserAndSetSession(form.getUsername(), form.getPsPair().getPassword(), request, authenticationManager);

        return new ModelAndView("redirect:/" );
    }

    public void authenticateUserAndSetSession(final String username, final String password, final HttpServletRequest request,
                                              final AuthenticationManager authenticationManager) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
