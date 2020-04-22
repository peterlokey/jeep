package com.getyourjeepdirty.jeep.models;

import com.getyourjeepdirty.jeep.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column
    @Size(min=2, max=16)
    private String firstName;

    @NotNull
    @Column
    @Size(min=2, max=16)
    private String lastName;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "creator")
    private List<Event> createdEvents = new ArrayList<Event>();

    @ManyToMany
    @JoinTable(
            name = "User_Event",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private List<Event> attendingEvents = new ArrayList<Event>();


    //getters and setters

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public List<Event> getAttendingEvents() {
        return attendingEvents;
    }

    public void addAttendingEvent(Event event) {
        this.attendingEvents.add(event);
    }
}
