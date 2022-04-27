package com.rojek.projectpp.Pages.Cards.Repositories;

import com.rojek.projectpp.Pages.Cards.Models.Cards;
import com.rojek.projectpp.Security.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

    List<Cards> findByUser(Users users);
    Optional<Cards> findByNumber(String number);

    @Query("select c.cash from Cards c WHERE c.number=?1")
    double findCashByNumber(String number);


    @Query("select c.number from Cards c WHERE c.user=?1")
    List<String> findAllNumbersAsStringsByUser(Users users);
}
