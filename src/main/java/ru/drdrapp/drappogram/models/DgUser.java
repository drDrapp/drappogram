package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_user_gen")
    @SequenceGenerator(name = "dg_user_gen", sequenceName = "dg_user_seq", initialValue = DgUser.START_SEQ, allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String login;
    private String hashPassword;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dg_user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @Enumerated(value = EnumType.STRING)
    private State state;

    private String email;
    private String activationCode;

}