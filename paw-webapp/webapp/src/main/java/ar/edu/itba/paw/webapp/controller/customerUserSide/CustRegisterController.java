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
