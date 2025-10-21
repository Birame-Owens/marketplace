package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateurs implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;  // ← LONG, pas int
    
    @Column(nullable = false, length = 100)
    private String nom;
    
    @Column(nullable = false, length = 100)
    private String prenom;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(length = 20)
    private String telephone;
    
    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;
    
    @Column(name = "photo_profil", length = 500)
    private String photoProfil;
    
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;  // ← LocalDate, pas Date
    
    @Column(length = 20)
    private String genre;
    
    @Column(name = "est_actif")
    private Boolean estActif = true;
    
    @Column(name = "est_verifie")
    private Boolean estVerifie = false;
    
    @Column(name = "token_verification", length = 255)
    private String tokenVerification;
    
    @Column(name = "token_reset_password", length = 255)
    private String tokenResetPassword;
    
    @Column(name = "date_derniere_connexion")
    private LocalDateTime dateDerniereConnexion;  // ← LocalDateTime
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;  // ← LocalDateTime
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;  // ← LocalDateTime
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Roles role;
    
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adresses> adresses;
    
    @OneToMany(mappedBy = "gerant", fetch = FetchType.LAZY)
    private List<Boutiques> boutiques;
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<Commandes> commandes;
    
    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Paniers panier;
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<ListesSouhaits> listesSouhaits;
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<AvisProduits> avisProduits;
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<AvisBoutiques> avisBoutiques;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estActif == null) estActif = true;
        if (estVerifie == null) estVerifie = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}