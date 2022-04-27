package com.rojek.projectpp.Security.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Setter
@Getter
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)//przechowywane w postaci łańcucha znaków
    private Types types;
    @ManyToMany(mappedBy = "roles")//właściciel relacji to roles
    private Set<Users> users;

    public Roles(Types types){
        this.types = types;
    }

    public static enum Types{
        ROLE_ADMIN,
        ROLE_USER
    }

}
