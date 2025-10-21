package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "produits_liste_souhaits")
@IdClass(ProduitsListeSouhaitsId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsListeSouhaits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id_liste")
    private Long idListe;
    
    @Id
    @Column(name = "id_produit")
    private Long idProduit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liste", insertable = false, updatable = false)
    private ListesSouhaits liste;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", insertable = false, updatable = false)
    private Produits produit;
    
    @Column(name = "date_ajout")
    private LocalDateTime dateAjout;
    
    @PrePersist
    protected void onCreate() {
        dateAjout = LocalDateTime.now();
    }
}