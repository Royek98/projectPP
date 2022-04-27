package com.rojek.projectpp.Pages.Details;

import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Model.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailsService {

    private final DetailsRepository detailsRepository;

    public void saveDetails(UserDetails userDetails) {
        detailsRepository.save(userDetails);
    }

    public Optional<UserDetails> getDetailsByUser(Users users) {
        return detailsRepository.findByUser(users);
    }
}
