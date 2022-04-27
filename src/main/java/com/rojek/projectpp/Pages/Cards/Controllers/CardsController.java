package com.rojek.projectpp.Pages.Cards.Controllers;

import com.rojek.projectpp.Pages.Cards.Models.Cards;
import com.rojek.projectpp.Pages.Cards.Services.CardsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class CardsController {

    private final CardsService cardsService;

    @GetMapping(path = "cards")
    public String cardsGet(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("newCard", cardsService.createNewCard(username));
        model.addAttribute("cards", cardsService.getAllCards(username));
        return "pages/cards";
    }

    @PostMapping(path = "cards")
    public String newCard(@ModelAttribute("newCard") Cards cards, Authentication authentication,
                          @RequestParam(value="multipartFile", required=false) MultipartFile multipartFile) {
        if (cards.getUser() == null || !cards.getUser().getLogin().equals(authentication.getName())) {
            return "redirect:/cards";
        }

        if (multipartFile.isEmpty() == false) {
            try {
                cardsService.addImage(cards, multipartFile);
            }
            catch(IOException e){}
        }

        cardsService.saveNewCard(cards);
        return "redirect:/cards";
    }

}
