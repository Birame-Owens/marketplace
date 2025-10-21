package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversations implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conversation")
    private Long idConversation;
    
    @Column(length = 255)
    private String sujet;
    
    @Column(name = "statut_conversation", length = 50)
    private String statutConversation = "ouverte";
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_derniere_activite")
    private LocalDateTime dateDerniereActivite;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Utilisateurs client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique")
    private Boutiques boutique;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Messages> messages;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateDerniereActivite = LocalDateTime.now();
        if (statutConversation == null) statutConversation = "ouverte";
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateDerniereActivite = LocalDateTime.now();
    }
}