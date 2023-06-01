package ru.drdrapp.drappogram.froms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}