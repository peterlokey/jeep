package com.getyourjeepdirty.jeep.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;


    private String text;

    private Timestamp dateTime;
}