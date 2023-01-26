//package ar.edu.itba.paw.webapp.controller.deprecated;
//
//import ar.edu.itba.paw.model.Customer;
//import ar.edu.itba.paw.model.enums.Roles;
//import ar.edu.itba.paw.model.User;
//import ar.edu.itba.paw.service.CustomerService;
//import ar.edu.itba.paw.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@Controller
//public class CustRegisterController {
//
//    private final UserService us;
//    private final CustomerService cs;
//
//    @Autowired
//    protected AuthenticationManager authenticationManager;
//
//
//    @Autowired
//    public CustRegisterController(final UserService us, final CustomerService cs) {
//        this.cs = cs;
//        this.us = us;
//    }
//
//
//
//    public void authenticateUserAndSetSession(final String username, final String password, final HttpServletRequest request,
//                                              final AuthenticationManager authenticationManager) {
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//
//        // generate session if one doesn't exist
//        request.getSession();
//
//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authenticatedUser = authenticationManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//    }
//}
