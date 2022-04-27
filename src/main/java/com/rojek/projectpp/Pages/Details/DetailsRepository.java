package com.rojek.projectpp.Pages.Details;

import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByUser(Users users);
}
