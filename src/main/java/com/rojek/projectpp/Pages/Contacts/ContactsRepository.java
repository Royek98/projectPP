package com.rojek.projectpp.Pages.Contacts;

import com.rojek.projectpp.Pages.Contacts.Models.Contacts;
import com.rojek.projectpp.Security.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    List<Contacts> findByUser(Users users);

}
