package com.getyourjeepdirty.jeep.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    @Size(min=0, max=30)
    private String groupName;

    @NotNull
    private Date dateTime;

    @NotNull
    private String location;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user")
    private User creator;

}
