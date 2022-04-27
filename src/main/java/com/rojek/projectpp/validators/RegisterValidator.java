package com.rojek.projectpp.validators;


import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
public class RegisterValidator implements Validator {

    private final UsersService2 usersService2;

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var u = (Users) target;
        if (!u.getPasswordConfirm().equals(u.getPassword())) {
            errors.rejectValue("password", "Negative.newUser.password");
        }

        if (usersService2.userExistsEmail(u.getEmail())) {
            errors.rejectValue("email", "Negative.newUser.email");
        }

        if (usersService2.userExistsLogin(u.getLogin())) {
            errors.rejectValue("email", "Negative.newUser.login");
        }
    }
}
