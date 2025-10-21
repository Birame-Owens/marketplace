package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifications implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification")
    private Long idNotification;
    
    @Column(name = "type_notification", nullable = false, length = 100)
    private String typeNotification;
    
    @Column(nullable = false, length = 255)
    private String titre;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(length = 500)
    private String lien;
    
    @Column(name = "est_lu")
    private Boolean estLu = false;
    
    @Column(name = "est_archive")
    private Boolean estArchive = false;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estLu == null) estLu = false;
        if (estArchive == null) estArchive = false;
    }
}