package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final UserService us;

    @Autowired
    public PawUserDetailsService(final UserService us) {
        this.us = us;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = us.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user."));

        final Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        // if (user. bla bla) -> roles.add "ROLE_EDITOR" etc.

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
    }
}
