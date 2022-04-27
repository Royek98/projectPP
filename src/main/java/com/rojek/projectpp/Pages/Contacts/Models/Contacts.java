package com.rojek.projectpp.Pages.Contacts.Models;

import com.rojek.projectpp.Security.Model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;
    private String name;
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Must contain only numbers")
    private String card;
    private String description;


    public Contacts(Users user, String name, String card, String description) {
        this.user = user;
        this.name = name;
        this.card = card;
        this.description = description;
    }

    public Contacts(Users user) {
        this.user = user;
    }
}
