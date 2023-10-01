package org.mtravis.microservices.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain model of a user. Not yet used.
 */
@Data
@Builder
public class User {
    public UUID id;
    public String username;
    public String firstname;
    public String lastname;
    public String email;
    public Instant createdDate;

    public User(UUID id, String username, String firstname, String lastname, String email, Instant createdDate) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.createdDate = createdDate;
        if(id == null){
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
