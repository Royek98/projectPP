package com.rojek.projectpp.Pages.Transfer.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Transfers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 16, max = 16)
    private String sender;
    @Size(min = 16, max = 16)
//    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Must contain only numbers")
    private String receiver;
    private LocalDateTime date;
    @Column(columnDefinition="Decimal(10,2)")
    private double cash;
    private String message;
    private Status status;


    public Transfers(String sender, String receiver, LocalDateTime date, double cash, String message, Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.cash = cash;
        this.message = message;
        this.status = status;
    }

    public Transfers(String sender) {
        this.sender = sender;
    }
}
