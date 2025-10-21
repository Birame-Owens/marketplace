package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "listes_souhaits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListesSouhaits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_liste")
    private Long idListe;
    
    @Column(name = "nom_liste", length = 100)
    private String nomListe = "Ma liste";
    
    @Column(name = "est_publique")
    private Boolean estPublique = false;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;
    
    @ManyToMany
    @JoinTable(
        name = "produits_liste_souhaits",
        joinColumns = @JoinColumn(name = "id_liste"),
        inverseJoinColumns = @JoinColumn(name = "id_produit")
    )
    private List<Produits> produits;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (nomListe == null) nomListe = "Ma liste";
        if (estPublique == null) estPublique = false;
    }
}