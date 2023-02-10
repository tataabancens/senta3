//package ar.edu.itba.paw.webapp.controller.deprecated;
//
//import ar.edu.itba.paw.model.Restaurant;
//import ar.edu.itba.paw.service.*;
//import ar.edu.itba.paw.model.exceptions.CustomerNotFoundException;
//import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.security.Principal;
//import java.util.Objects;
//import java.util.Optional;
//
//@Controller
//public class RedirectController {
//
//    private final RestaurantService rs;
//    private final CustomerService cs;
//
//    @Autowired
//    public RedirectController(final RestaurantService rs, CustomerService cs) {
//        this.rs = rs;
//        this.cs = cs;
//    }
//}
