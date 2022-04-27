package com.rojek.projectpp.Security.Services;

import com.rojek.projectpp.Pages.registration.token.ConfirmationToken;
import com.rojek.projectpp.Pages.registration.token.ConfirmationTokenService;
import com.rojek.projectpp.Security.Model.Roles;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService2 {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final RolesService rolesService;

    public boolean userExistsEmail(String email) {
        return usersRepository.findUsersByEmail(email).isPresent();
    }

    public boolean userExistsLogin(String login) {
        return usersRepository.findUsersByLogin(login).isPresent();
    }

    public Users getUserByLogin(String login) {
        return usersRepository.findUsersByLogin(login).get();
    }

    public boolean userLocked(String login) {
        return usersRepository.findUsersByLogin(login).get().isLocked();
    }

    public String saveUser(Users user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(Arrays.asList(rolesService.getRoleUser())));
        usersRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUser(String email) {
        return usersRepository.enableUser(email);
    }

    public void unlockUser(Long id) {
        usersRepository.unlockUser(id);
    }

}
