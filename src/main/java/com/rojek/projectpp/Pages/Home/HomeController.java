package com.rojek.projectpp.Pages.Home;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping(path = "home")
    public String homeGet(Authentication authentication, Model model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        // if user not logged redirect to 'index' page
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }

        String username = authentication.getName();

        if(homeService.userLocked(username)) {
            return "redirect:/details?username="+username;
        }

        model.addAttribute("name", homeService.getName(username));
        model.addAttribute("cards", homeService.getAllCards(username));
        model.addAttribute("inWaiting", homeService.getWaitingTransfers());
        return "pages/home";
    }

}
