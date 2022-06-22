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
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/customerProfile", method = RequestMethod.GET)
    public ModelAndView profile(final Principal principal){
        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        SecurityContextHolder.getContext().getAuthentication().getCredentials();

        ModelAndView mav = new ModelAndView("customerViews/custProfile/CustomerProfile");
        mav.addObject("username", username);
        mav.addObject("customer", customer);

        return mav;
    }

    @RequestMapping(value = "/profile/editName", method = RequestMethod.GET)
    public ModelAndView profileEditName(final Principal principal,
                                        @ModelAttribute("editNameForm") final EditNameForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/custProfile/editCustomerName");
        mav.addObject("username", username);
        form.setName(customer.getCustomerName());

        return mav;
    }

    @RequestMapping(value = "/profile/editName", method = RequestMethod.POST)
    public ModelAndView profileEditNamePost(final Principal principal,
                                            @Valid @ModelAttribute("editNameForm") final EditNameForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditName(principal, form);
        }
        String username = principal.getName();

        cs.updateCustomerNameByUsername(username, form.getName());

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/editPhone", method = RequestMethod.GET)
    public ModelAndView profileEditPhone(final Principal principal,
                                        @ModelAttribute("editPhoneForm") final EditPhoneForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/custProfile/editCustomerPhone");
        form.setPhone(customer.getPhone());

        return mav;
    }

    @RequestMapping(value = "/profile/editPhone", method = RequestMethod.POST)
    public ModelAndView profileEditNamePost(final Principal principal,
                                            @Valid @ModelAttribute("editPhoneForm") final EditPhoneForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditPhone(principal, form);
        }
        String username = principal.getName();

        cs.updateCustomerPhoneByUsername(username, form.getPhone());

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/profile/editMail", method = RequestMethod.GET)
    public ModelAndView profileEditMail(final Principal principal,
                                         @ModelAttribute("editMailForm") final EditEmailForm form){

        String username = principal.getName();

        Customer customer = cs.getCustomerByUsername(username).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/custProfile/editCustomerMail");
        form.setMail(customer.getMail());

        return mav;
    }

    @RequestMapping(value = "/profile/editMail", method = RequestMethod.POST)
    public ModelAndView profileEditMailPost(final Principal principal,
                                            @Valid @ModelAttribute("editMailForm") final EditEmailForm form,
                                            final BindingResult errors){
        if (errors.hasErrors()) {
            return profileEditMail(principal, form);
        }
        String username = principal.getName();
        cs.updateCustomerMailByUsername(username, form.getMail());

        return new ModelAndView("redirect:/profile");
    }

}
