package com.rojek.projectpp.Pages.Details;

import com.rojek.projectpp.Pages.Details.Models.UserDetails;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class DetailsController {

    private final UsersService2 usersService;
    private final DetailsService detailsService;

    @GetMapping(path = "details")
    public String detailsGET(@RequestParam(value="username", required=true)String username,
                             Authentication authentication, Model model) {

        if (!authentication.getName().equals(username)) {
            return "redirect:/";
        }
        Users u = usersService.getUserByLogin(username);
        model.addAttribute("userDetails", new UserDetails(u));
        return "details";
    }

    @PostMapping(path = "details")
    public String detailsPOST(@ModelAttribute("userDetails") @Valid UserDetails userDetails,
                              BindingResult result, Authentication authentication) {

        if(result.hasErrors()){
            return "details";
        }

        if (userDetails.getUser() == null || !userDetails.getUser().getLogin().equals(authentication.getName())) {
            return "redirect:/details?username="+authentication.getName();
        }

        detailsService.saveDetails(userDetails);
        usersService.unlockUser(userDetails.getUser().getId());

        return "redirect:/home";
    }

    @InitBinder("userDetails")
    public void initBinder(WebDataBinder binder){
        binder.addCustomFormatter(new AddressFormatter());
    }

}
