package com.rojek.projectpp.Pages.Cards.Repositories;

import com.rojek.projectpp.Pages.Cards.Models.Conversion;
import com.rojek.projectpp.Pages.Cards.Models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    @Query("select c.worth from Conversion c where c.from=?1 and c.to=?2")
    double getWorth(Currency from, Currency to);

}
