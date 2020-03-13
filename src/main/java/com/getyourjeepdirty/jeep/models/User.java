package com.getyourjeepdirty.jeep.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(unique=true)
    @Size(min=2, max=16)
    private String firstName;

    @NotNull
    @Column(unique=true)
    @Size(min=2, max=16)
    private String lastName;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;
}
