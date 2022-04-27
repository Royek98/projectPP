package com.rojek.projectpp.Pages.Cards.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "from_currency")
    private Currency from;
    @Column(name = "to_currency")
    private Currency to;
    private double worth;

    public Conversion(Currency from, Currency to, double worth) {
        this.from = from;
        this.to = to;
        this.worth = worth;
    }
}
