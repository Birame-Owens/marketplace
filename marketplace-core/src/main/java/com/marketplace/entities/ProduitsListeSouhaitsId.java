package com.marketplace.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitsListeSouhaitsId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idListe;
    private Long idProduit;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitsListeSouhaitsId that = (ProduitsListeSouhaitsId) o;
        return idListe.equals(that.idListe) && idProduit.equals(that.idProduit);
    }
    
    @Override
    public int hashCode() {
        return idListe.hashCode() + idProduit.hashCode();
    }
}