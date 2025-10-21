package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "statuts_commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatutsCommande implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut")
    private Long idStatut;
    
    @Column(name = "nom_statut", unique = true, nullable = false, length = 50)
    private String nomStatut;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 20)
    private String couleur;
    
    @Column
    private Integer ordre = 0;
    
    @OneToMany(mappedBy = "statut", fetch = FetchType.LAZY)
    private List<Commandes> commandes;
    
    @OneToMany(mappedBy = "statut", fetch = FetchType.LAZY)
    private List<HistoriqueCommandes> historiques;
    
    @PrePersist
    protected void onCreate() {
        if (ordre == null) ordre = 0;
    }
}