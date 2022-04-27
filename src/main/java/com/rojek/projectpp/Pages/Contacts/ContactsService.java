package com.rojek.projectpp.Pages.Contacts;

import com.rojek.projectpp.Pages.Contacts.Models.Contacts;
import com.rojek.projectpp.Security.Model.Users;
import com.rojek.projectpp.Security.Services.UsersService2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactsService {

    private final UsersService2 usersService2;
    private final ContactsRepository contactsRepository;

    public Contacts newContact(String username) {
        Users u = usersService2.getUserByLogin(username);
        return new Contacts(u);
    }

    public List<Contacts> getMyContacts(String username) {
        Users u = usersService2.getUserByLogin(username);
        return contactsRepository.findByUser(u);
    }

    public void saveNewContact(Contacts contacts) {
        contactsRepository.save(contacts);
    }

    public Optional<Contacts> findContact(Long id) {
        return contactsRepository.findById(id);
    }

    public void deleteCon(Contacts contacts) {
        contactsRepository.delete(contacts);
    }
}
