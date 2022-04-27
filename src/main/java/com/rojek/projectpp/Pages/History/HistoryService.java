package com.rojek.projectpp.Pages.History;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.rojek.projectpp.Pages.Cards.Services.CardsService;
import com.rojek.projectpp.Pages.Transfer.Models.Status;
import com.rojek.projectpp.Pages.Transfer.Models.Transfers;
import com.rojek.projectpp.Pages.Transfer.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {

    private final CardsService cardsService;
    private final TransferService transferService;

    public List<Transfers> getHistory(String username) {
        List<String> c = cardsService.getAllCardsAsStrings(username);
        return transferService.getHistoryOfUser(c);
    }


    public List<Transfers> findByCustomQuery(String filter, String username) {
        List<String> c = cardsService.getAllCardsAsStrings(username);
        return transferService.findByCustomQuery(filter, c);
    }

    public List<Transfers> findByCustomQuery2(List<Status> statuses, String username) {
        List<String> c = cardsService.getAllCardsAsStrings(username);
        return transferService.findByCustomQuery2(statuses, c);
    }

    public List<Transfers> findByCustomQuery3(List<Status> statuses, String username, String phrase) {
        List<String> c = cardsService.getAllCardsAsStrings(username);
        return transferService.findByCustomQuery3(statuses, c, phrase);
    }

    public void printPDF(Long id) throws FileNotFoundException, DocumentException {
        if (transferService.getTranferById(id).isPresent()) {
            Transfers t = transferService.getTranferById(id).get();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(
                    t.getSender()+".pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Font font2 = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(String.valueOf(
                 "Sender: " + t.getSender() + "\n" +
                 "Receiver: " + t.getReceiver() + "\n" +
                 "Message: " + t.getMessage() + "\n" +
                 "Cash: " + t.getCash() + "\n" +
                 "Date: " + t.getDate() + "\n" +
                 "Status: " + t.getStatus()
            ),font2));
            document.add(Chunk.NEWLINE);

            document.close();
        }
    }
}
