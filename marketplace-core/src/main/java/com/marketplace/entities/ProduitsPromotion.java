package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "produits_promotion")
@IdClass(ProduitsPromotionId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsPromotion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id_promotion")
    private Long idPromotion;
    
    @Id
    @Column(name = "id_produit")
    private Long idProduit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promotion", insertable = false, updatable = false)
    private Promotions promotion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", insertable = false, updatable = false)
    private Produits produit;
    
    @Column(name = "prix_promo", precision = 10, scale = 2)
    private BigDecimal prixPromo;
}