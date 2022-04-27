package com.rojek.projectpp.Pages.Details.Models;


import com.rojek.projectpp.Security.Model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Size(min = 3)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @NotEmpty
    @Size(min = 2)
    @Column(nullable = false)
    private String surName;

    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Must contain only numbers")
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Must contain only numbers")
    @Size(min = 9, max = 9)
    private String phone;

    @OneToOne
    private Users user;

    @Embedded
    private Address address;

    public UserDetails(String name, String surName, String pesel, String phone, Users user, Address address) {
        this.name = name;
        this.surName = surName;
        this.pesel = pesel;
        this.phone = phone;
        this.user = user;
        this.address = address;
    }

    public UserDetails(Users user) {
        this.user = user;
    }
}
