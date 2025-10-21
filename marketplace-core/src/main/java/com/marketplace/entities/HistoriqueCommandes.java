package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_commandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueCommandes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique")
    private Long idHistorique;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "date_changement")
    private LocalDateTime dateChangement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_statut")
    private StatutsCommande statut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateChangement = LocalDateTime.now();
    }
}