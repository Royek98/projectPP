package com.rojek.projectpp.Security.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean locked = true;

    @Column(nullable = false)
    @Size(min=3, max=20)
    @NotBlank
    private String login;

    @Column(nullable = false)
    @Size(min=4, max=60)
    @NotBlank
    private String password;

    @Column(nullable = false)
    private String email;

    @Transient//właściwość nie będzie odzwierciedlona w db
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles;

    public Users(String login, String password, String email,
                 String passwordConfirm, Set<Roles> roles, boolean enabled, boolean locked) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.passwordConfirm = passwordConfirm;
        this.roles = roles;
        this.enabled = enabled;
        this.locked = locked;
    }
}
