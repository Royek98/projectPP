package com.rojek.projectpp.Pages;

import com.rojek.projectpp.Pages.Details.DetailsRepository;
import com.rojek.projectpp.Pages.Details.DetailsService;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class mainController {

    private final UsersService2 usersService;
    private final DetailsService detailsService;
    private DetailsRepository detailsRepository;

    @GetMapping(path = "/")
    public String index(Authentication authentication, Model model,
                        @CookieValue(name = "mess", defaultValue = "-1") String coockieResult,
                        HttpServletResponse httpServletResponse) {
        authentication = SecurityContextHolder.getContext().getAuthentication();

        // delete cookie
        if (!coockieResult.equals("-1")) {
            model.addAttribute("mess", coockieResult);
            Cookie cookie = new Cookie("mess", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            httpServletResponse.addCookie(cookie);
        }
        //----------------------

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "pages/index";
        }

        return "redirect:/home";
    }

}
