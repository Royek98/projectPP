package com.rojek.projectpp.Pages.Profile;

import com.rojek.projectpp.Pages.Details.AddressFormatter;
import com.rojek.projectpp.Pages.Details.DetailsService;
import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(path = "profile")
    public String profileGet(Model model, Authentication authentication) {
        model.addAttribute("userDetails", profileService.getUserDetails(authentication.getName()));
        return "pages/profile";
    }

    @PostMapping(path = "profile")
    public String profilePost(@Valid @ModelAttribute("userDetails") UserDetails userDetails, BindingResult result,
                              Authentication authentication) {
        if (result.hasErrors()) {
            return "pages/profile";
        }

        if (userDetails.getUser() == null || !userDetails.getUser().getLogin().equals(authentication.getName())) {
            return "redirect:/profile";
        }

        profileService.editProfile(userDetails);

        return "redirect:/profile";
    }


    @InitBinder("userDetails")
    public void initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new AddressFormatter());
    }

}
