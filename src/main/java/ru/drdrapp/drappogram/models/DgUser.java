package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_user_id_gen")
    @SequenceGenerator(name = "dg_user_id_gen", sequenceName = "dg_user_id_seq", initialValue = DgUser.START_SEQ, allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login", nullable = false)
    @NotBlank(message = "Логин пользователя не должно быть пустым")
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Transient
    @NotBlank
    private String passwordToConfirm;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "dg_user_role", joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="dg_user_role_dg_user_id_fk")))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private State state;

    @Column(name = "email")
    @NotBlank(message = "Электронный адрес обязателен для заполнения")
    @Email(message = "Некорректный адрес")
    private String email;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "active", nullable = false)
    private Boolean active;

}