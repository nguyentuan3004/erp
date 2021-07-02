package com.samsung.erp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String passwordRecoverCode;

    private Boolean activated;

    @JsonIgnore
    private String activatedCode;

    private Long createdAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonBackReference
    private Profile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordRecoverCode() {
        return passwordRecoverCode;
    }

    public void setPasswordRecoverCode(String passwordRecoverCode) {
        this.passwordRecoverCode = passwordRecoverCode;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getActivatedCode() {
        return activatedCode;
    }

    public void setActivatedCode(String activatedCode) {
        this.activatedCode = activatedCode;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
