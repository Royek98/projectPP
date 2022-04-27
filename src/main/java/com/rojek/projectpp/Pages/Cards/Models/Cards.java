package com.rojek.projectpp.Pages.Cards.Models;


import com.rojek.projectpp.Security.Model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Users user;

    @Size(min = 16, max = 16)
    private String number;
    @Column(columnDefinition="Decimal(10,2)")
    private double cash;
    private Currency currency;
    private String fileName;

    public Cards(Users user, String number, double cash, Currency currency, String fileName) {
        this.user = user;
        this.number = number;
        this.cash = cash;
        this.currency = currency;
        this.fileName = fileName;
    }

    public Cards(Users user) {
        this.user = user;
    }
}
