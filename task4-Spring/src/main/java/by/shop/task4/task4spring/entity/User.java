package by.shop.task4.task4spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String user_name;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "created_at")
    private Date created_at;

    public User() {
    }

    public User(String username, String passwordHash, String email) {
        this.user_name = username;
        this.password = passwordHash;
        this.email = email;
        this.role = UserRole.USER;
    }

    public User(int id, String username, String passwordHash, String email, UserRole role, Date createdAt) {
        this.id = id;
        this.user_name = username;
        this.password = passwordHash;
        this.email = email;
        this.role = role;
        this.created_at = createdAt;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }
}
