package com.Abeer.store.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name="users",
    uniqueConstraints={
        @UniqueConstraint(columnNames="username"),
        @UniqueConstraint(columnNames="email")
    }
)
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Size(max=50)
    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Size(min=6)
    @Column(nullable = false)
    private String password;
    
    @Email
    @NotBlank
    @Size(max=100)
    @Column(nullable=false, length=100)
    private String email;

    @OneToMany(mappedBy="user")
    private List<Order> orders;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    //--- Getters ---
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public java.util.List<Order> getOrders() {
        return orders;
    }

}
