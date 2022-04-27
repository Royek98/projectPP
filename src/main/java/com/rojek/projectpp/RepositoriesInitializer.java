package com.rojek.projectpp;

import com.rojek.projectpp.Pages.Cards.Models.Conversion;
import com.rojek.projectpp.Pages.Cards.Models.Currency;
import com.rojek.projectpp.Pages.Cards.Repositories.ConversionRepository;
import com.rojek.projectpp.Pages.Details.DetailsRepository;
import com.rojek.projectpp.Pages.Details.Models.Address;
import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Model.Roles;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Repositories.RolesRepository;
import com.rojek.projectpp.Security.Repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@AllArgsConstructor
public class RepositoriesInitializer {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final DetailsRepository detailsRepository;
    private final ConversionRepository conversionRepository;

    @Bean
    InitializingBean init() {

        return () -> {

            if (rolesRepository.findAll().isEmpty()) {
                rolesRepository.save(new Roles(Roles.Types.ROLE_ADMIN));
                rolesRepository.save(new Roles(Roles.Types.ROLE_USER));
            }

            if (usersRepository.findAll().isEmpty()) {
                usersRepository.save(new Users(
                        "admin",
                        passwordEncoder.encode("admin"),
                        "admin@admin",
                        "admin",
                        new HashSet<>(Arrays.asList(rolesRepository.findById(1l).get())),
                        true,
                        false
                ));

                usersRepository.save(new Users(
                        "user",
                        passwordEncoder.encode("user"),
                        "user@user",
                        "user",
                        new HashSet<>(Arrays.asList(rolesRepository.findById(2l).get())),
                        true,
                        false
                ));
            }

            if (detailsRepository.findAll().isEmpty()) {
                detailsRepository.save(new UserDetails("nameAdmin", "surNameAdmin",
                        "12345678911", "123456789", usersRepository.getById(1l),
                        new Address("00-000", "City", "Street", "15")));
                detailsRepository.save(new UserDetails("nameAdmin", "surNameAdmin",
                        "12345678911", "123456789", usersRepository.getById(2l),
                        new Address("00-000", "City", "Street", "15")));
            }

            if (conversionRepository.findAll().isEmpty()) {
                //PLN
                conversionRepository.save(new Conversion(Currency.PLN, Currency.PLN, 1));
                conversionRepository.save(new Conversion(Currency.PLN, Currency.EUR, 0.22));
                conversionRepository.save(new Conversion(Currency.PLN, Currency.USD, 0.25));

                //EUR
                conversionRepository.save(new Conversion(Currency.EUR, Currency.EUR, 1));
                conversionRepository.save(new Conversion(Currency.EUR, Currency.PLN, 4.58));
                conversionRepository.save(new Conversion(Currency.EUR, Currency.USD, 1.13));

                //USD
                conversionRepository.save(new Conversion(Currency.USD, Currency.USD, 1));
                conversionRepository.save(new Conversion(Currency.USD, Currency.EUR, 0.89));
                conversionRepository.save(new Conversion(Currency.USD, Currency.PLN, 4.07));
            }
        };
    }
}
