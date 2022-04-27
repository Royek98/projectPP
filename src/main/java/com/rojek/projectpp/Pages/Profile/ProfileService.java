package com.rojek.projectpp.Pages.Profile;

import com.rojek.projectpp.Pages.Details.DetailsService;
import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {

    private final DetailsService detailsService;
    private final UsersService2 usersService;

    public void editProfile(UserDetails userDetails) {
//        Long userId = detailsService.getDetailsByUser(userDetails.getUser()).get();
        Users u = usersService.getUserByLogin(userDetails.getUser().getLogin());
        UserDetails d = detailsService.getDetailsByUser(u).get();
        userDetails.setId(d.getId());
        detailsService.saveDetails(userDetails);
    }

    public UserDetails getUserDetails(String username) {
        return detailsService.getDetailsByUser(
                usersService.getUserByLogin(username)).get();
    }

}
