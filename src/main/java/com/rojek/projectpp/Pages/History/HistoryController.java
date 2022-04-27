package com.rojek.projectpp.Pages.History;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@Controller
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @RequestMapping(value = "history", method = {RequestMethod.GET, RequestMethod.POST})
    public String historyGet(Model model, Authentication authentication,
                             @ModelAttribute("filter") Filter filter) {

        if (filter.isEmpty()) {
            model.addAttribute("transfers", historyService.getHistory(authentication.getName()));
        }
        else if (!filter.getPhrase().isEmpty()){
            model.addAttribute("transfers", historyService.findByCustomQuery(filter.getPhrase(),
                    authentication.getName()));
        } else if (!filter.getStatuses().isEmpty()) {
            model.addAttribute("transfers", historyService.findByCustomQuery2(filter.getStatuses(),
                    authentication.getName()));
        } else {
            model.addAttribute("transfers", historyService.findByCustomQuery3(filter.getStatuses(),
                    authentication.getName(), filter.getPhrase()));
        }

        return "pages/history";
    }

    @GetMapping(path = "print")
    public String print(@RequestParam("id") Long id) throws DocumentException, FileNotFoundException {
        historyService.printPDF(id);
        return "redirect:/history";
    }

}
