package com.rojek.projectpp.Pages.Transfer;

import com.rojek.projectpp.Pages.Cards.Models.Cards;
import com.rojek.projectpp.Pages.Cards.Services.CardsService;
import com.rojek.projectpp.Pages.Cards.Services.ConversionService;
import com.rojek.projectpp.Pages.Contacts.ContactsService;
import com.rojek.projectpp.Pages.Contacts.Models.Contacts;
import com.rojek.projectpp.Pages.Transfer.Models.Status;
import com.rojek.projectpp.Pages.Transfer.Models.Transfers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final CardsService cardsService;
    private final ConversionService conversionService;
    private final ContactsService contactsService;

    public List<Cards> getUsersCards(String username) {
        return cardsService.getAllCards(username);
    }

    public void saveTransfer(Transfers transfers) {
        transfers.setDate(LocalDateTime.now());
        if (cardsService.findCashByNumber(transfers.getSender()) < transfers.getCash())
            transfers.setStatus(Status.Rejected);
        else transfers.setStatus(Status.Waiting);
        transferRepository.save(transfers);
    }

    public List<Transfers> getWaitingTransfers() {
        return transferRepository.findAllByStatus(Status.Waiting);
    }

    public String changeStatus(Long id) {
        Transfers t = transferRepository.findById(id).get();
        if (!cardsService.findCardByNumber(t.getReceiver()).isPresent()) {
            t.setStatus(Status.Rejected);
            transferRepository.save(t);
            return "redirect:/home";
        }
        Cards from = cardsService.findCardByNumber(t.getSender()).get();
        Cards to = cardsService.findCardByNumber(t.getReceiver()).get();

        double worth = conversionService.getWorth(from.getCurrency(), to.getCurrency());
        double worthResult = t.getCash() * worth;

        to.setCash(to.getCash() + worthResult);
        from.setCash(from.getCash() - t.getCash());

        cardsService.saveCash(from);
        cardsService.saveCash(to);

        t.setStatus(Status.Accepted);
        transferRepository.save(t);

        return "redirect:/home";
    }

    List<Contacts> getMyContacts(String username) {
        return contactsService.getMyContacts(username);
    }

    public List<Transfers> getHistoryOfUser(List<String> c) {
        return transferRepository.findBySenderOrReceiverIn(c);
    }

    public List<Transfers> findByCustomQuery(String phrase, List<String> c) {
        return transferRepository.findByCustomQuery(phrase, c);
    }
    public List<Transfers> findByCustomQuery2(List<Status> statuses, List<String> c) {
        return transferRepository.findByCustomQuery2(statuses, c);
    }
    public List<Transfers> findByCustomQuery3(List<Status> statuses, List<String> c, String phrase) {
        return transferRepository.findByCustomQuery3(statuses, c, phrase);
    }

    public Optional<Transfers> getTranferById(Long id) {
        return transferRepository.findById(id);
    }

    public List<Cards> getAllCardsFromBank() {
        return cardsService.getAllCardsFromBank();
    }

    public String updateCash(Long id, Double cash) {
        cardsService.updateCash(id, cash);
        return "redirect:/transfer";
    }
}
