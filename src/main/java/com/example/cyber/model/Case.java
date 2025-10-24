package com.example.cyber.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cases")
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status = "Submitted";

    private String imageUrl;    // path to uploaded image
    private String location;    // optional location
    private String adminReview; // admin notes

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAdminReview() { return adminReview; }
    public void setAdminReview(String adminReview) { this.adminReview = adminReview; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
