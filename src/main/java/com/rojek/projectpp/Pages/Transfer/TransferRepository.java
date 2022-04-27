package com.rojek.projectpp.Pages.Transfer;

import com.rojek.projectpp.Pages.Transfer.Models.Status;
import com.rojek.projectpp.Pages.Transfer.Models.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfers, Long> {

    @Query("select t from Transfers t WHERE t.status=?1")
    List<Transfers> findAllByStatus(Status status);

//    adding to history by sender credit card
//    List<Transfers> findBySenderIn(List<String> sender);
    @Query("select t from Transfers t where t.sender in ?1 or  t.receiver in ?1")
    List<Transfers> findBySenderOrReceiverIn(List<String> cards);

    @Query("select u from Transfers u where " +
            "(u.receiver = ?1 or u.sender = ?1) and" +
            "(u.sender in ?2 or u.receiver in ?2)")
    List<Transfers> findByCustomQuery(String phrase, List<String> cards);

    @Query("select u from Transfers u where " +
            "(u.status in ?1) and" +
            "(u.sender in ?2 or u.receiver in ?2)")
    List<Transfers> findByCustomQuery2(List<Status> statuses, List<String> cards);

    @Query("select u from Transfers u where " +
            "(u.receiver = ?3 or u.sender = ?3) and" +
            "(u.status in ?1) and" +
            "(u.sender in ?2 or u.receiver in ?2)")
    List<Transfers> findByCustomQuery3(List<Status> statuses, List<String> cards, String phrase);
}
