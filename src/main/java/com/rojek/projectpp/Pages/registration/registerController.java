package com.rojek.projectpp.Pages.registration;

import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import com.rojek.projectpp.validators.RegisterValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class registerController {

    private final registerService registerService;
    private final UsersService2 usersService2;

    @GetMapping(path = "register")
    public String registerGet(Model model) {
        model.addAttribute("newUser", new Users());
        return "register";
    }

    @PostMapping(path = "register")
    public String registerPost(@ModelAttribute("newUser") @Valid Users u, BindingResult result,
                               HttpServletResponse httpServletResponse) {
        if(result.hasErrors()){
            return "register";
        }

        Cookie success = new Cookie("mess", "success");
        success.setMaxAge(10000);
        success.setSecure(true);
        success.setHttpOnly(true);
        success.setPath("/");
        httpServletResponse.addCookie(success);

        registerService.register(u);
        return "redirect:/";
    }

    @GetMapping(path = "register/confirm")
    public String confirm(@RequestParam("token") String token, HttpServletResponse httpServletResponse) {
        String mess = registerService.confirmToken(token);

        Cookie msg = new Cookie("mess", mess);
        msg.setMaxAge(10000);
        msg.setSecure(true);
        msg.setHttpOnly(true);
        msg.setPath("/");
        httpServletResponse.addCookie(msg);
        return "redirect:/";
    }

    @InitBinder("newUser")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new RegisterValidator(usersService2));
    }

}
