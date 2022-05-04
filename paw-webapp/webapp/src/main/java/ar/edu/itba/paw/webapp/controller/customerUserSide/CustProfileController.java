package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ControllerService;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.EditEmailForm;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import ar.edu.itba.paw.webapp.form.EditPhoneForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

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

        SecurityContextHolder.getContext().getAuthentication().getCredentials();

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
                                            @Valid @ModelAttribute("editNameForm") final EditNameForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditName(principal, form);
        }
        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);
        cs.updateCustomerData(customer.getCustomerId(), form.getName(), customer.getPhone(), customer.getMail());

        return new ModelAndView("redirect:/profile");
    }
    @RequestMapping(value = "/profile/editPhone", method = RequestMethod.GET)
    public ModelAndView profileEditPhone(Principal principal,
                                        @ModelAttribute("editPhoneForm") final EditPhoneForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("custProfile/editCustomerPhone");
        form.setPhone(customer.getPhone());

        return mav;
    }

    @RequestMapping(value = "/profile/editPhone", method = RequestMethod.POST)
    public ModelAndView profileEditNamePost(Principal principal,
                                            @Valid @ModelAttribute("editPhoneForm") final EditPhoneForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditPhone(principal, form);
        }
        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);
        cs.updateCustomerData(customer.getCustomerId(), customer.getCustomerName(), form.getPhone(), customer.getMail());

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/editMail", method = RequestMethod.GET)
    public ModelAndView profileEditMail(Principal principal,
                                         @ModelAttribute("editMailForm") final EditEmailForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("custProfile/editCustomerMail");
        form.setMail(customer.getMail());

        return mav;
    }

    @RequestMapping(value = "/profile/editMail", method = RequestMethod.POST)
    public ModelAndView profileEditMailPost(Principal principal,
                                            @Valid @ModelAttribute("editMailForm") final EditEmailForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditMail(principal, form);
        }
        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);
        cs.updateCustomerData(customer.getCustomerId(), customer.getCustomerName(), customer.getPhone(), form.getMail());

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/editUsername", method = RequestMethod.GET)
    public ModelAndView profileEditUsername(Principal principal,
                                        @ModelAttribute("editUsernameForm") final EditNameForm form){

        String username = principal.getName();

        ModelAndView mav = new ModelAndView("editUsername");
        form.setName(username);

        return mav;
    }

    @RequestMapping(value = "/profile/editUsername", method = RequestMethod.POST)
    public ModelAndView profileEditUsernamePost(Authentication auth,
                                                Principal principal,
                                                @Valid @ModelAttribute("editNameForm") final EditNameForm form,
                                                final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditUsername(principal, form);
        }

        us.updateUsername(principal.getName(), form.getName());

        return new ModelAndView("redirect:/profile");
    }

}
