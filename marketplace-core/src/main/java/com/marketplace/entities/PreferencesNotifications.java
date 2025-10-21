package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "preferences_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesNotifications implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preference")
    private Long idPreference;
    
    @Column(name = "notif_email_commande")
    private Boolean notifEmailCommande = true;
    
    @Column(name = "notif_email_promotion")
    private Boolean notifEmailPromotion = true;
    
    @Column(name = "notif_email_newsletter")
    private Boolean notifEmailNewsletter = false;
    
    @Column(name = "notif_push_commande")
    private Boolean notifPushCommande = true;
    
    @Column(name = "notif_push_livraison")
    private Boolean notifPushLivraison = true;
    
    @Column(name = "notif_push_promotion")
    private Boolean notifPushPromotion = false;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", unique = true)
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateModification = LocalDateTime.now();
        if (notifEmailCommande == null) notifEmailCommande = true;
        if (notifEmailPromotion == null) notifEmailPromotion = true;
        if (notifEmailNewsletter == null) notifEmailNewsletter = false;
        if (notifPushCommande == null) notifPushCommande = true;
        if (notifPushLivraison == null) notifPushLivraison = true;
        if (notifPushPromotion == null) notifPushPromotion = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}