package com.springboot.restapp.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String userpic;
    private String email;
    private String locale;
    private LocalDateTime lastVisit;
}
