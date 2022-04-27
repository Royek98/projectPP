package com.rojek.projectpp.Pages.Contacts;

import com.rojek.projectpp.Pages.Contacts.Models.Contacts;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ContactsController {

    private final ContactsService contactsService;

    @GetMapping({"/contacts", "/editContact"})
    public String contactsGet(Model model, Authentication authentication,
                              @RequestParam
                                      (value="id", required=false, defaultValue="-1") Long id) {
        if (id.equals(-1l)) {
            model.addAttribute("newContact", contactsService.newContact(authentication.getName()));
        } else {
            model.addAttribute("newContact", contactsService.findContact(id));
        }
        model.addAttribute("myContacts", contactsService.getMyContacts(authentication.getName()));
        return "pages/contacts";
    }

    @PostMapping(path = "contacts")
    public String contactsPost(@ModelAttribute("newContact") Contacts contacts) {
        contactsService.saveNewContact(contacts);
        return "redirect:/contacts";
    }

    @GetMapping(path = "deleteContact")
    public String deleteContact(@RequestParam(value="id", required=true) Long id) {
        Contacts c = contactsService.findContact(id).get();
        contactsService.deleteCon(c);
        return "redirect:/contacts";
    }
}
