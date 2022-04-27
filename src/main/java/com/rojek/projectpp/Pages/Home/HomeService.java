package com.rojek.projectpp.Pages.Home;

import com.rojek.projectpp.Pages.Cards.Models.Cards;
import com.rojek.projectpp.Pages.Cards.Services.CardsService;
import com.rojek.projectpp.Pages.Details.DetailsService;
import com.rojek.projectpp.Pages.Transfer.Models.Transfers;
import com.rojek.projectpp.Pages.Transfer.TransferService;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeService {

    private final UsersService2 usersService;
    private final DetailsService detailsService;
    private final CardsService cardsService;
    private final TransferService transferService;

    public boolean userLocked(String username) {
        return usersService.userLocked(username);
    }

    public String getName(String username) {
        Users u = usersService.getUserByLogin(username);
        return detailsService.getDetailsByUser(u).get().getName();
    }

    public List<Cards> getAllCards(String username) {
        return cardsService.getAllCards(username);
    }

    public List<Transfers> getWaitingTransfers() {
        return transferService.getWaitingTransfers();
    }
}
