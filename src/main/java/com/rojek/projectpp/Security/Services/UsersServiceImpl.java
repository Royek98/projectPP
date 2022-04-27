package com.rojek.projectpp.Security.Services;

import com.rojek.projectpp.Security.Model.Roles;
import com.rojek.projectpp.Security.ProfileNames;
import com.rojek.projectpp.Security.Repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
@AllArgsConstructor
@Profile(ProfileNames.USERS_IN_DATABASE)
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usersRepository.findUsersByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return convertToUserDetails(user.get());
    }

    private UserDetails convertToUserDetails(com.rojek.projectpp.Security.Model.Users users) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Roles role : users.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getTypes().toString()));
        }

        if (!users.isEnabled())
            throw new UsernameNotFoundException("User is not enabled");

        return new User(users.getLogin(), users.getPassword(), grantedAuthorities);
        //UWAGA: klasa ma też drugi konstruktor – więcej parametrów!!!
    }



}
