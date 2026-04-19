package com.Abeer.store.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name="users",
    uniqueConstraints={
        @UniqueConstraint(columnNames="username"),
        @UniqueConstraint(columnNames="email")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor

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

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt=LocalDateTime.now();

}
