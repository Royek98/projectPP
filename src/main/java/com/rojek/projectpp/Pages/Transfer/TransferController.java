package com.rojek.projectpp.Pages.Transfer;

import com.rojek.projectpp.Pages.Transfer.Models.Status;
import com.rojek.projectpp.Pages.Transfer.Models.Transfers;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping(path = "transfer")
    public String transferGet(Model model, Authentication
                               authentication) {
        model.addAttribute("cards", transferService.getUsersCards(authentication.getName()));
        model.addAttribute("contacts", transferService.getMyContacts(authentication.getName()));
        model.addAttribute("newTransfer", new Transfers());
        model.addAttribute("allCards", transferService.getAllCardsFromBank());
        return "pages/transfer";
    }

    @PostMapping(path = "transfer")
    public String transferPost(@ModelAttribute("newTransfer") @Valid Transfers transfers,
                               BindingResult result) {

        if (result.hasErrors()) {
            return "pages/transfer";
        }

        if (transfers.getSender() == null) {
            return "redirect:/transfer";
        }

        transferService.saveTransfer(transfers);
        return "redirect:/home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "changeStatus")
    public String changeStatus(@RequestParam(value="id", required=true)Long id) {
        return transferService.changeStatus(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "transferAdmin")
    public String transferAdmin(@RequestParam(value="cardId", required=true)Long id,
                                @RequestParam(value="cash", required=true)Double cash) {

        return transferService.updateCash(id, cash);
    }
}
