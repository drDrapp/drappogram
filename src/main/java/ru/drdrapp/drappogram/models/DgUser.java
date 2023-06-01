package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dg_user")
public class DgUser {

    public static final int START_SEQ = 10;

    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_user_id_gen")
    @SequenceGenerator(name = "dg_user_id_gen", sequenceName = "dg_user_id_seq", initialValue = DgUser.START_SEQ, allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login", nullable = false)
    @NotBlank(message = "Login may not be blank")
    private String login;

    @Column(name = "hash_password", nullable = false)
    @NotNull
    private String hashPassword;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dg_user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;

    @Column(name = "email")
    private String email;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

}