package com.marketplace.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsPromotionId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idPromotion;
    private Long idProduit;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitsPromotionId that = (ProduitsPromotionId) o;
        return idPromotion.equals(that.idPromotion) && idProduit.equals(that.idProduit);
    }
    
    @Override
    public int hashCode() {
        return idPromotion.hashCode() + idProduit.hashCode();
    }
}