package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final UserService us;
    private final PasswordEncoder passwordEncoder;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    public PawUserDetailsService(final UserService us, final PasswordEncoder passwordEncoder) {
        this.us = us;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = us.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user."));

        final Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole()));

        String password = user.getPassword();
        //migrate users with un-hashed password
        if(! BCRYPT_PATTERN.matcher(password).matches()) {
            // update user password in the db
            us.updatePassword(username, password);
            password = passwordEncoder.encode(password);

        }

        return new org.springframework.security.core.userdetails.User(username, password, roles);
    }
}
